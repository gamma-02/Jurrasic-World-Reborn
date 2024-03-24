package net.gamma02.jurassicworldreborn.common.items.genetics;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.genetics.DinoDNA;
import net.gamma02.jurassicworldreborn.common.genetics.GeneticsHelper;
import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.gamma02.jurassicworldreborn.common.util.LangUtil;
import net.gamma02.jurassicworldreborn.common.util.api.DinosaurItem;
import net.gamma02.jurassicworldreborn.common.util.api.SequencableItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class SoftTissueItem extends Item implements SequencableItem, DinosaurItem {
    protected final Dinosaur dino;
    public SoftTissueItem(Properties pProperties, Dinosaur dino) {
        super(pProperties);
        this.dino = dino;
    }

    @Override
    public Component getName(ItemStack pStack) {
        return LangUtil.replaceWithDinoName(this.dino, "item.jurassicworldreborn.soft_tissue");
    }

    @Override
    public Dinosaur getDinosaur(ItemStack stack) {
        if(stack.getItem() == this) {
            return this.dino;
        }
        if(stack.getItem() instanceof SoftTissueItem i) {
            return i.getDinosaur(stack);
        }
        if(stack.getItem() instanceof DinosaurItem i){
            return i.getDinosaur(stack);
        }

        return Dinosaur.EMPTY;
    }



    @Override
    public List<Pair<Float, ItemStack>> getChancedOutputs(ItemStack inputItem) {

        List<Pair<Float, ItemStack>> list = Lists.newArrayList();
        CompoundTag nbt = new CompoundTag();

        DinoDNA dna = new DinoDNA(getDinosaur(inputItem), -1, "");
        dna.writeToNBT(nbt);

        ItemStack output = new ItemStack(ModItems.STORAGE_DISC.get(), 1, nbt);

        list.add(Pair.of(100F, output));
        return list;
    }

    @Override
    public boolean isSequencable(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getSequenceOutput(ItemStack stack, RandomSource random) {
        CompoundTag nbt = stack.getTag();

        if (nbt == null) {
            nbt = new CompoundTag();
            initDnaCompound(stack, random, nbt);
         /*}else if (!nbt.contains("Dinosaur")) {//THIS SHOULD ALWAYS CONTAIN A DINOSAUR TAG -- MAKING THIS REDUNDANT
            nbt.putString("Dinosaur", getDinosaur(stack).getName().toLowerCase());
        }*/
        }else if(nbt.contains("Dinosaur") && !nbt.contains("DinoDna")){
            initDnaCompound(stack, random, nbt);
        }

        ItemStack output = new ItemStack(ModItems.STORAGE_DISC.get(), 1);
        output.setTag(nbt);

        return output;
    }

//    @Override
//    public void fillItemCategory(CreativeModeTab pCategory, NonNullList<ItemStack> pItems) {
//        DinosaurItem.setDino(ModItems.SOFT_TISSUE.get(dino).get().getDefaultInstance(), dino);
//        super.fillItemCategory(pCategory, pItems);
//    }

    private void initDnaCompound(ItemStack stack, RandomSource random, CompoundTag nbt) {
        int quality = Math.abs((SequencableItem.randomQuality(random))/2);
        DinoDNA dna = new DinoDNA(getDinosaur(stack), quality, GeneticsHelper.randomGenetics(random));
        dna.writeToNBT(nbt);
    }
}
