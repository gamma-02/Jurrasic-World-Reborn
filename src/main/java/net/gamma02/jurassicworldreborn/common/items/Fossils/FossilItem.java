package net.gamma02.jurassicworldreborn.common.items.Fossils;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Hybrid;
import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.gamma02.jurassicworldreborn.common.util.LangUtil;
import net.gamma02.jurassicworldreborn.common.util.api.DinosaurItem;
import net.gamma02.jurassicworldreborn.common.util.api.GrindableItem;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class FossilItem extends Item implements GrindableItem {
    public static Map<String, List<Dinosaur>> fossilDinosaurs = new HashMap<>();
    public static Map<String, List<Dinosaur>> freshFossilDinosaurs = new HashMap<>();
    private String type;
    private boolean fresh;
    private final Dinosaur dino;

    public FossilItem(Properties properties, String type, boolean fresh, Dinosaur dino ) {
        super(properties);
        this.type = type.toLowerCase(Locale.ENGLISH).replaceAll(" ", "_");
        this.fresh = fresh;
        this.dino = dino;

//        this.setHasSubtypes(true);
//
//        this.setCreativeTab(TabHandler.FOSSILS);
    }


    //wtf is this??
    public static void init() {
        for (boolean fresh : new boolean[] { true, false }) {
            for (Dinosaur dinosaur : Dinosaur.DINOS) {
                if(!fresh && dinosaur instanceof Hybrid) {
                    continue;
                }
                Map<String, List<Dinosaur>> map = fresh ? freshFossilDinosaurs : fossilDinosaurs;
                String[] boneTypes = dinosaur.getBones();
                for (String boneType : boneTypes) {
                    List<Dinosaur> dinosaursWithType = map.get(boneType);

                    if (dinosaursWithType == null) {
                        dinosaursWithType = new ArrayList<>();
                    }
                    if(!dinosaur.getName().isEmpty() || dinosaur == Dinosaur.EMPTY) {
                        dinosaursWithType.add(dinosaur);
                    }
                    map.put(boneType, dinosaursWithType);
                }
            }
        }
    }

//    @Override
//    public String getItemStackDisplayName(ItemStack stack) {
//        Dinosaur dinosaur = this.getDinosaur(stack);
//
//        if (dinosaur != null) {
//            return LangUtils.translate(this.getUnlocalizedName() + ".name").replace("{dino}", LangUtils.getDinoName(dinosaur));
//        }
//
//        return super.getItemStackDisplayName(stack);
//    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack pStack) {
//        Dinosaur dino = this.getDinosaur(pStack);
//        if(dino != null){
//            return LangUtil.replaceInKey(dino::getName, "{dino}", "item.jurassicworldreborn." + (this.isFresh() ? "fresh_" : "" ) + this.type);
//        }
        return LangUtil.replaceWithDinoName(dino, "item.jurassicworldreborn." + this.type + (this.isFresh() ? "_fresh" : ""));
    }

    @Nullable
    public Dinosaur getDinosaur(@NotNull ItemStack stack) {
        if(stack.getItem() instanceof FossilItem item) {
            return item.dino;
        }else{
            return null;
        }
    }

//    @Override
//    @SideOnly(Side.CLIENT)
//    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subtypes) {
//        List<Dinosaur> dinosaurs = new ArrayList<>(EntityHandler.getRegisteredDinosaurs());
//
//        Collections.sort(dinosaurs);
//
//        List<Dinosaur> dinosaursForType = this.getMap().get(this.type);
//        if(this.isInCreativeTab(tab))
//            for (Dinosaur dinosaur : dinosaurs) {
//                if (dinosaursForType.contains(dinosaur) && !(!this.fresh && dinosaur instanceof Hybrid)) {
//                    subtypes.add(new ItemStack(this, 1, EntityHandler.getDinosaurId(dinosaur)));
//                }
//            }
//    }

//    @Override
//    public void addInformation(ItemStack stack, Level worldIn, List<String> lore, ITooltipFlag flagIn) {
//        NBTTagCompound nbt = stack.getTagCompound();
//
//        if (nbt != null && nbt.hasKey("Genetics") && nbt.hasKey("DNAQuality")) {
//            int quality = nbt.getInteger("DNAQuality");
//
//            TextFormatting colour;
//
//            if (quality > 75) {
//                colour = TextFormatting.GREEN;
//            } else if (quality > 50) {
//                colour = TextFormatting.YELLOW;
//            } else if (quality > 25) {
//                colour = TextFormatting.GOLD;
//            } else {
//                colour = TextFormatting.RED;
//            }
//
//
//            lore.add(colour + LangUtils.translate(LangUtils.LORE.get("dna_quality")).replace("%1$s", LangUtils.getFormattedQuality(quality)));
//            lore.add(TextFormatting.BLUE + LangUtils.translate(LangUtils.LORE.get("genetic_code")).replace("%1$s", LangUtils.getFormattedGenetics(nbt.getString("Genetics"))));
//        }
//    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable net.minecraft.world.level.Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        CompoundTag nbt = stack.getTag();

        if (nbt != null && nbt.contains("Genetics") && nbt.contains("DNAQuality")) {
            int quality = nbt.getInt("DNAQuality");

            ChatFormatting colour;//BRIT(derogatory)

            if (quality > 75) {
                colour = ChatFormatting.GREEN;
            } else if (quality > 50) {
                colour = ChatFormatting.YELLOW;
            } else if (quality > 25) {
                colour = ChatFormatting.GOLD;
            } else {
                colour = ChatFormatting.RED;
            }

            pTooltipComponents.add(Component.literal(Component.translatable(LangUtil.LORE.formatted("dna_quality")).getString().replace("%1$s", LangUtil.getFormattedQuality(quality).getString())).withStyle(colour));
            pTooltipComponents.add(LangUtil.replaceInKey(() -> LangUtil.getFormattedGenetics(nbt.getString("Genetics")).getString(), "%1$s", Component.translatable(LangUtil.LORE.formatted("genetic_code")).getString()).withStyle(ChatFormatting.BLUE));
        }
        super.appendHoverText(stack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public boolean isGrindable(ItemStack stack) {
        return true;
    }

    public boolean isFresh() {
        return this.fresh;
    }

    public String getBoneType(){
        return type;
    }

    @Override
    public ItemStack getGroundItem(ItemStack stack, Random random) {
        CompoundTag tag = stack.getTag();

        int outputType = random.nextInt(6);

        if (outputType == 5 || this.fresh) {
            ItemStack output = DinosaurItem.setDino(new ItemStack(ModItems.SOFT_TISSUE.get(this.getDinosaur(stack)).get(), 1, tag), this.getDinosaur(stack));
            return output;
        } else if (outputType < 3) {
            return Items.WHITE_DYE.getDefaultInstance();
//            return new ItemStack(Items.DYE, 1, 15);
        }

        return new ItemStack(Items.FLINT);
    }


//    @Override
//    public List<ItemStack> getJEIRecipeTypes() {
//        List<ItemStack> list = Lists.newArrayList();
//        this.getMap().get(this.type).forEach(dino -> list.add(new ItemStack(this, 1, EntityHandler.getDinosaurId(dino))));
//        return list;
//    }

    @Override
    public List<Pair<Float, ItemStack>> getChancedOutputs(ItemStack inputItem) {
        float single = 100F/6F;
        CompoundTag tag = inputItem.getTag();
        ItemStack output = new ItemStack(ModItems.SOFT_TISSUE.get(this.getDinosaur(inputItem)).get(), 1, tag);
        if(this.fresh) {
            return Lists.newArrayList(Pair.of(100F, output));
        }
        return Lists.newArrayList(Pair.of(single, output), Pair.of(50f, Items.BONE_MEAL.getDefaultInstance()), Pair.of(single*2f, new ItemStack(Items.FLINT)));
    }

    public Map<String, List<Dinosaur>> getMap() {
        return this.fresh ? freshFossilDinosaurs : fossilDinosaurs;
    }
}
