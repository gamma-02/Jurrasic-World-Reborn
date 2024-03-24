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
        CompoundTag compound = new CompoundTag();
        this.dna.writeToNBT(compound);

        ItemStack output = new ItemStack(ModItems.DINOSAUR_DNA.get(dna.getDinosaur()).get(), 1);
        output.setTag(compound);
        return output;
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        this.dna.writeToNBT(nbt);
    }

    @Override
    public DNA load(CompoundTag nbt) {
        this.dna = DinoDNA.readFromNBT(nbt);
        return this.dna;
    }

    @Override
    public void addInformation(ItemStack stack, List<Component> tooltip) {
        this.dna.addInformation(stack, tooltip);
    }

    public String getDinoName(){
        return this.dna.getDinoName();
    }
}