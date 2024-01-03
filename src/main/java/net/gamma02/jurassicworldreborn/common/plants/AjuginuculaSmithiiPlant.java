package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.gamma02.jurassicworldreborn.common.items.Food.FoodHelper;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;

public class AjuginuculaSmithiiPlant extends Plant {
    @Override
    public String getName() {
        return "Ajuginucula Smithii";
    }

    @Override
    public Block getBlock() {
        return ModBlocks.AJUGINUCULA_SMITHII.get();
    }

    @Override
    public int getHealAmount() {
        return 2000;
    }

    @Override
    public FoodHelper.FoodEffect[] getEffects() {
        return new FoodHelper.FoodEffect[] { new FoodHelper.FoodEffect(MobEffects.MOVEMENT_SPEED, 100) };
    }
}
