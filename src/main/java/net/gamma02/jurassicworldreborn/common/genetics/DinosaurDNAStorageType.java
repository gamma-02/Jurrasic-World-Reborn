package net.gamma02.jurassicworldreborn.common.genetics;

import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class DinosaurDNAStorageType implements StorageType {
    private DinoDNA dna;

    @Override
    public ItemStack createItem() {
        ItemStack output = new ItemStack(ModItems.DNA.get(), 1);
        CompoundTag compound = new CompoundTag();
        this.dna.writeToNBT(compound);
        output.setTag(compound);
        return output;
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        this.dna.writeToNBT(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        this.dna = DinoDNA.readFromNBT(nbt);
    }

    @Override
    public void addInformation(ItemStack stack, List<Component> tooltip) {
        this.dna.addInformation(stack, tooltip);
    }

    @Override
    public int getMetadata() {
        return this.dna.getMetadata();
    }

    public String getDinoName(){
        return this.dna.getDinoName();
    }
}