package net.gamma02.jurassicworldreborn.common.genetics;

import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class PlantDNAStorageType implements StorageType {
    private PlantDNA dna;

    @Override
    public ItemStack createItem() {
        ItemStack output = new ItemStack(ModItems.PLANT_DNA.get(), 1);
        CompoundTag compound = new CompoundTag();
        this.dna.writeToNBT(compound);
        output.setTag(compound);
        return output;
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        this.dna.writeToNBT(nbt);
//        nbt.putString("DNA", dna.getPlant().toString()); what????
    }

    @Override
    public DNA load(CompoundTag nbt) {
        this.dna = PlantDNA.readFromNBT(nbt);
        return this.dna;
    }

    @Override
    public void addInformation(ItemStack stack, List<Component> tooltip) {
        this.dna.addInformation(stack, tooltip);
    }

//    @Override
//    public int getMetadata() {
//        return this.dna.get();
//    }
}