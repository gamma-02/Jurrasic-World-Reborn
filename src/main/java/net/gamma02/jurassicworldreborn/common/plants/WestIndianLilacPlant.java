package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.gamma02.jurassicworldreborn.common.items.Food.FoodHelper;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;

public class WestIndianLilacPlant extends Plant {
    @Override
    public String getName() {
        return "West Indian Lilac";
    }

    @Override
    public Block getBlock() {
        return ModBlocks.WEST_INDIAN_LILAC.get();
    }

    @Override
    public int getHealAmount() {
        return 4000;
    }

    @Override
    public FoodHelper.FoodEffect[] getEffects() {
        return new FoodHelper.FoodEffect[] { new FoodHelper.FoodEffect(MobEffects.POISON, 100) };
    }

    @Override
    public boolean isPrehistoric() {
        return false;
    }
}
