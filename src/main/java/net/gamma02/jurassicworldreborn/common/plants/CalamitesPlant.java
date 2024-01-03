package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.CommonRegistries;
import net.gamma02.jurassicworldreborn.common.blocks.wood.DynamicWoodTypeRegistry;
import net.gamma02.jurassicworldreborn.common.items.Food.FoodHelper;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;

public class CalamitesPlant extends Plant {
    @Override
    public String getName() {
        return "Calamites";
    }

    @Override
    public Block getBlock() {
        return DynamicWoodTypeRegistry.getSaplingForType(CommonRegistries.CalamitesType);
    }

    @Override
    public int getHealAmount() {
        return 1000;
    }

    @Override
    public FoodHelper.FoodEffect[] getEffects() {
        return new FoodHelper.FoodEffect[] { new FoodHelper.FoodEffect(MobEffects.POISON, 5) };
    }

    @Override
    public boolean isTree() {
        return true;
    }
}
