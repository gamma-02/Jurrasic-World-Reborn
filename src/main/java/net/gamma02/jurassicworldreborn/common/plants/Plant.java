package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.items.Food.FoodHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;

import java.util.Locale;

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

    public String getFormattedName(){
        return this.getName().toLowerCase(Locale.ROOT).replace(' ', '_');
    }

    /**
     * This is the default implimentation of Plant -- The instance of this in {@link PlantHandler}({@link PlantHandler#EMPTY}
     * will be returned to prevent nulls from being returned in refrence to plants.
     *
     * @see Dinosaur#EMPTY
     * @see Dinosaur.EmptyDinosaur
     *
      */
    public static class EmptyPlant extends Plant{

        @Override
        public String getName() {
            return "None";
        }

        @Override
        public Block getBlock() {
            return Blocks.AIR;
        }

        @Override
        public boolean shouldRegister() {
            return false;
        }

        @Override
        public int getHealAmount() {
            return 0;
        }
    }
}