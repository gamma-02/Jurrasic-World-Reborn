package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.gamma02.jurassicworldreborn.common.items.Food.FoodHelper;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;

public class OrontiumMackiiPlant extends Plant {
    @Override
    public String getName() {
        return "Orontium Mackii";
    }

    @Override
    public Block getBlock() {
        return ModBlocks.ORONTIUM_MACKII.get();
    }

    @Override
    public int getHealAmount() {
        return 1500;
    }

    @Override
    public FoodHelper.FoodEffect[] getEffects() {
        return new FoodHelper.FoodEffect[] { new FoodHelper.FoodEffect(MobEffects.WEAKNESS, 50) };
    }
}
