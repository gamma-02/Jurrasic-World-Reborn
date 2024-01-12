package net.gamma02.jurassicworldreborn.common.items.genetics;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.util.LangUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;


public class HatchedEggItem extends DNAContainerItem {
    public HatchedEggItem(Properties properties) {
        super(properties);
    }

//    @Override
//    public String getItemStackDisplayName(ItemStack stack) {
//
//        return LangUtils.translate(dinosaur.givesDirectBirth() ? "item.gestated.name" :"item.hatched_egg.name").replace("{dino}", LangUtils.getDinoName(dinosaur));
//    }

    @Override
    public Component getName(ItemStack pStack) {
        Dinosaur dinosaur = this.getDinosaur(pStack);

        return Component.literal(Component.translatable((dinosaur.givesDirectBirth() ? "item.gestated.name" : "item.hatched_egg.name") + ".name").getString().replace("{dino}", LangUtil.getDinoName(this.getDinosaur(pStack)).getString()));
    }

    public Dinosaur getDinosaur(ItemStack stack) {
        Dinosaur dinosaur = Dinosaur.getDinosaurByName(stack.getTag() != null ? stack.getTag().getString("DinosaurName") : null);

        if (dinosaur == null) {
            dinosaur = Dinosaur.EMPTY;
        }

        return dinosaur;
    }



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
        return Dinosaur.DINOS.indexOf(this.getDinosaur(stack));
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

            Dinosaur dinosaur = this.getDinosaur(stack);

            try {
                DinosaurEntity entity = dinosaur.getDinosaurClass().getDeclaredConstructor(Level.class).newInstance();

                entity.setPos(pos.getX() + hitX, pos.getY(), pos.getZ() + hitZ);
                entity.setAge(0);
                entity.setGenetics(this.getGeneticCode(player, stack));
                entity.setDNAQuality(this.getDNAQuality(player, stack));
                entity.setMale(this.getGender(player, stack));
                if (!player.isCrouching()) {
                    entity.setOwner(player);
                }

                level.addFreshEntity(entity);

                if (!player.isCreative()) {
                    stack.shrink(1);
                }
            } catch (ReflectiveOperationException e) {
                Jurassicworldreborn.getLogger().warn("Failed to spawn dinosaur from hatched egg", e);
            }
        }

        return InteractionResult.SUCCESS;


    }
}
