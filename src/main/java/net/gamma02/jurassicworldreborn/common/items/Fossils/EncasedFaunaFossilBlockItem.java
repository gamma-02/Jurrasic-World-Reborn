package net.gamma02.jurassicworldreborn.common.items.Fossils;

import com.mojang.datafixers.util.Pair;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.gamma02.jurassicworldreborn.common.items.TabHandler;
import net.gamma02.jurassicworldreborn.common.util.ItemsUtil;
import net.gamma02.jurassicworldreborn.common.util.api.CleanableItem;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class EncasedFaunaFossilBlockItem extends FossilBlockItem implements CleanableItem {
    public EncasedFaunaFossilBlockItem(Block block, Item.Properties properties) {
        super(block, properties);
    }

    @Override
    public boolean isCleanable(ItemStack stack) {
        return true;
    }
    public static Dinosaur getDino(ItemStack pStack){
        Dinosaur dinosaur = Dinosaur.getDinosaurByName(pStack.getTag() != null ? pStack.getTag().getCompound("BlockEntityTag").getString("Dinosaur") : Dinosaur.EMPTY.getName());
        return dinosaur;
    }
    public static ItemStack setDino(ItemStack pStack, Dinosaur dino){
        CompoundTag tag = pStack.getOrCreateTag();
        CompoundTag blockEntityTag = tag.getCompound("BlockEntityTag");
        blockEntityTag.putString("Dinosaur", dino.getName());
        tag.put("BlockEntityTag", blockEntityTag);
        pStack.setTag(tag);
        return pStack;
    }

//    @Override
//    public ItemStack getCleanedItem(ItemStack stack, Random random) {
//        int dinosaurId = BlockHandler.getDinosaurId((EncasedFossilBlock) Block.getBlockFromItem(stack.getItem()), stack.getItemDamage());
//        String[] bones = EntityHandler.getDinosaurById(dinosaurId).getBones();
//        return new ItemStack(ItemHandler.FOSSILS.get(bones[bones.length>1?random.nextInt(bones.length):0]), 1, dinosaurId);
//    }


    @Override
    public ItemStack getCleanedItem(ItemStack stack, RandomSource random) {


        String[] bones = getDino(stack).getBones();
        LinkedHashMap<String, RegistryObject<Item>> sansUndertale = ModItems.BONES.get(getDino(stack));//*megalovania starst playing*
        if(sansUndertale == null){
            return ItemStack.EMPTY;
        }
        Item bone = sansUndertale.get(bones[bones.length>1?random.nextInt(bones.length):0]).get();
        if(bone == null){
            return ItemStack.EMPTY;
        }
        return new ItemStack(bone, 1);


    }

//    @Override
//    public List<Pair<Float, ItemStack>> getChancedOutputs(ItemStack inputItem) {
//        int dinosaurId = BlockHandler.getDinosaurId((EncasedFossilBlock) Block.getBlockFromItem(inputItem.getItem()), inputItem.getItemDamage());
//        String[] bones = EntityHandler.getDinosaurById(dinosaurId).getBones();
//        float single = 100F / bones.length;
//
//        List<Pair<Float, ItemStack>> list = Lists.newArrayList();
//        for(String bone : bones) {
//            list.add(Pair.of(single, new ItemStack(ItemHandler.FOSSILS.get(bone), 1, dinosaurId)));
//        }
//        return list;
//    }

    @Override
    public List<Pair<Float, ItemStack>> getChancedOutputs(ItemStack inputItem) {
        Dinosaur dino = FossilBlockItem.getDino(inputItem);//See above for legacy code. No wonder it was so laggy.
        String[] bones = dino.getBones();
        float single = 100f / bones.length;
        ArrayList<Pair<Float, ItemStack>> list = new ArrayList<>();

        for(String bone : bones){

            Item boneItem = ItemsUtil.getFossilDinosaurBone(dino, bone);
            if(boneItem == null)
                continue;

            list.add(Pair.of(single, boneItem.getDefaultInstance()));
        }
        return list;
    }

    @Override
    public void fillItemCategory(CreativeModeTab pGroup, NonNullList<ItemStack> pItems) {
        if(pGroup != TabHandler.FOSSILS && pGroup != CreativeModeTab.TAB_SEARCH)
            return;

        NonNullList<ItemStack> FOSSILS = NonNullList.create();
        NonNullList<ItemStack> ENCASED_FOSSILS = NonNullList.create();

        for (Dinosaur dino : Dinosaur.DINOS) {


            FOSSILS.add(FaunaFossilBlockItem.setDino(ModItems.FAUNA_FOSSIL_BLOCK.get().getDefaultInstance(), dino));
            ENCASED_FOSSILS.add( EncasedFaunaFossilBlockItem.setDino(ModItems.ENCASED_FAUNA_FOSSIL.get().getDefaultInstance(), dino));
        }

        pItems.addAll(FOSSILS);
        pItems.addAll(ENCASED_FOSSILS);

    }
}
