package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.items.Food.FoodHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public abstract class Plant implements Comparable<Plant> {
    public abstract String getName();

    public ResourceLocation getId(){
        return PlantHandler.getPlantId(this);
    }

    public abstract Block getBlock();

    public boolean shouldRegister() {
        return true;
    }

    @Override
    public int compareTo(Plant plant) {
        return this.getName().compareTo(plant.getName());
    }

    public abstract int getHealAmount();

    public FoodHelper.FoodEffect[] getEffects() {
        return new FoodHelper.FoodEffect[0];
    }

    public boolean isPrehistoric() {
        return true;
    }

    public boolean isTree() {
        return false;
    }
}