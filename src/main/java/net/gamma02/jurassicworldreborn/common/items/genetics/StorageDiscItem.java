package net.gamma02.jurassicworldreborn.common.items.genetics;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.genetics.DinoDNA;
import net.gamma02.jurassicworldreborn.common.genetics.PlantDNA;
import net.gamma02.jurassicworldreborn.common.genetics.StorageType;
import net.gamma02.jurassicworldreborn.common.genetics.StorageTypeRegistry;
import net.gamma02.jurassicworldreborn.common.plants.PlantHandler;
import net.gamma02.jurassicworldreborn.common.util.api.SynthesizableItem;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;


import java.util.List;
import java.util.Random;

public class StorageDiscItem extends Item implements SynthesizableItem {
    public StorageDiscItem(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level pLevel, List<Component> toolTip, TooltipFlag pIsAdvanced) {

        CompoundTag tag = stack.getTag();
        if(tag == null) {
            toolTip.add(Component.translatable("cage.empty").withStyle(ChatFormatting.RED));
            return;
        }else if(!tag.contains("DNA")){
            toolTip.add(Component.translatable("cage.empty").withStyle(ChatFormatting.RED));
            return;
        }

        CompoundTag dna = tag.getCompound("DNA");

        String storageId = dna.getString("StorageId");
        StorageType type = StorageTypeRegistry.getStorageType(storageId);
        if (type != null) {
            type.load(tag);
            type.addInformation(stack, toolTip);
        }

        super.appendHoverText(stack, pLevel, toolTip, pIsAdvanced);
    }

    @Override
    public boolean isSynthesizable(ItemStack stack) {
        CompoundTag tagCompound = stack.getTag();
        return tagCompound != null && tagCompound.contains("DNAQuality") && tagCompound.getInt("DNAQuality") == 100;
    }

    @Override
    public ItemStack getSynthesizedItem(ItemStack stack, Random random) {
        CompoundTag tag = stack.getTag();
        StorageType type = StorageTypeRegistry.getStorageType(tag.getString("StorageId"));
        type.load(tag);
        return type.createItem();
    }

    @Override
    public List<Pair<Float, ItemStack>> getChancedOutputs(ItemStack inputItem) {
        CompoundTag tag = inputItem.getTag();
        StorageType type = StorageTypeRegistry.getStorageType(tag.getString("StorageId"));
        type.load(tag);
        return Lists.newArrayList(new Pair<>(100F, type.createItem()));
    }

    @Override
    public List<ItemStack> getJEIRecipeTypes() {
        List<ItemStack> list = Lists.newArrayList();

        Dinosaur.DINOS.forEach(dino -> {
            DinoDNA dna = new DinoDNA(dino, -1, "");
            ItemStack stack = new ItemStack(this);
            CompoundTag nbt = new CompoundTag();
            dna.writeToNBT(nbt);
            stack.setTag(nbt);
            list.add(stack);
        });

        PlantHandler.getPlants().forEach((location, plant) -> {
            PlantDNA dna = new PlantDNA(location, -1);
            ItemStack stack = new ItemStack(this);
            CompoundTag nbt = new CompoundTag();
            dna.writeToNBT(nbt);
            stack.setTag(nbt);
            list.add(stack);

        });
        return list;
    }
}
