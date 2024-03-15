package net.gamma02.jurassicworldreborn.common.util.api;

import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface DinosaurItem {
    default Dinosaur getDinosaur(ItemStack stack) {
        return Dinosaur.getDinosaurByName(stack.getTag().getString("Dinosaur"));
    }
     static ItemStack setDino(ItemStack pStack, Dinosaur dino){
        CompoundTag tag = pStack.getOrCreateTag();
        tag.putString("Dinosaur", dino.getName());
        pStack.setTag(tag);
        return pStack;
    }
}
