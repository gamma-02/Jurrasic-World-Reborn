package net.gamma02.jurassicworldreborn.common.items.genetics;

import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.gamma02.jurassicworldreborn.common.items.TabHandler;
import net.gamma02.jurassicworldreborn.common.util.LangUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;


public class HatchedEggItem extends DNAContainerItem {

    private final Dinosaur dino;
    public HatchedEggItem(Properties properties, Dinosaur dino) {
        super(properties);
        this.dino = dino;
    }

//    @Override
//    public String getItemStackDisplayName(ItemStack stack) {
//
//        return LangUtils.translate(dinosaur.givesDirectBirth() ? "item.gestated.name" :"item.hatched_egg.name").replace("{dino}", LangUtils.getDinoName(dinosaur));
//    }

    @Override
    public Component getName(ItemStack pStack) {
//        Dinosaur dinosaur = this.getDinosaur(pStack);

        return Component.literal(Component.translatable((dino.givesDirectBirth() ? "item.jurassicworldreborn.gestated" : "item.jurassicworldreborn.hatched_egg")).getString().replace("{dino}", LangUtil.getDinoName(this.dino).getString()));
    }

//    public Dinosaur getDinosaur(ItemStack stack) {
//        Dinosaur dinosaur = Dinosaur.getDinosaurByName(stack.getTag() != null ? stack.getTag().getString("DinosaurName") : null);
//
//        if (dinosaur == null) {
//            dinosaur = Dinosaur.EMPTY;
//        }
//
//        return dinosaur;
//    }



    public boolean getGender(Player player, ItemStack stack) {
        CompoundTag nbt = stack.getTag();

        boolean gender = player.level.random.nextBoolean();

        if (nbt == null) {
            nbt = new CompoundTag();
        }

        if (nbt.contains("Gender")) {
            gender = nbt.getBoolean("Gender");
        } else {
            nbt.putBoolean("Gender", gender);
        }

        stack.setTag(nbt);

        return gender;
    }

    @Override
    public void fillItemCategory(CreativeModeTab pCategory, NonNullList<ItemStack> pItems) {
        if((pCategory == TabHandler.DNA || pCategory == CreativeModeTab.TAB_SEARCH)) {
            if(pItems.stream().anyMatch((stack) -> stack.is(this)))
                return;


            var eggItem = ModItems.hatchedDinoEggs.get(dino);
            if (eggItem != null) {
                ItemStack defaultDNAItem = eggItem.get().getDefaultInstance();

                defaultDNAItem.getOrCreateTag().putBoolean("isCreative", true);


                pItems.add(defaultDNAItem);
            }

        }else {

            super.fillItemCategory(pCategory, pItems);
        }
    }

    //    @Override
//    @SideOnly(Side.CLIENT)
//    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subtypes) {
//        List<Dinosaur> dinosaurs = new LinkedList<>(EntityHandler.getDinosaurs().values());
//        Collections.sort(dinosaurs);
//        if(this.isInCreativeTab(tab)) {
//            for (Dinosaur dinosaur : dinosaurs) {
//                if (dinosaur.shouldRegister()) {
//                    subtypes.add(new ItemStack(this, 1, EntityHandler.getDinosaurId(dinosaur)));
//                }
//            }
//        }
//    }

    @Override
    public int getContainerId(ItemStack stack) {
        return Dinosaur.DINOS.indexOf(dino);
    }

//    @Override
//    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
//
//    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {

        Level level = context.getLevel();
        if (level.isClientSide)
            return InteractionResult.PASS;

        BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
        Direction side = context.getClickedFace();
        double hitX = context.getClickLocation().x;
        double hitY = context.getClickLocation().y;
        double hitZ = context.getClickLocation().z;
        Player player = context.getPlayer();

//        ItemStack stack = context.getPlayer().getItemInHand(context.getHand());

        if (side == Direction.EAST || side == Direction.WEST) {
            hitX = 1.0F - hitX;
        } else if (side == Direction.NORTH || side == Direction.SOUTH) {
            hitZ = 1.0F - hitZ;
        }



        if (level.isInWorldBounds(pos)/*todo: add more stringent placing requirements here*/) {

            Dinosaur dinosaur = dino;


//                DinosaurEntity entity = dinosaur.getDinosaurClass().getDeclaredConstructor(Level.class).newInstance();
            DinosaurEntity entity = DinosaurEntity.CLASS_TYPE_LIST.get(dinosaur.getDinosaurClass()).get().create(level);

            if(entity == null){
                return InteractionResult.PASS;
            }

            entity.setPos(pos.getX(), pos.getY(), pos.getZ());
            entity.setAge(0);
            entity.setGenetics(this.getGeneticCode(player.getRandom(), stack));
            entity.setDNAQuality(this.getDNAQuality(player.isCreative(), stack));
            entity.setMale(this.getGender(player, stack));
            if (!player.isCrouching()) {
                entity.setOwner(player);
            }

            level.addFreshEntity(entity);

            if (!player.isCreative()) {
                stack.shrink(1);
            }

        }

        return InteractionResult.SUCCESS;


    }
}
