package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.CommonRegistries;
import net.gamma02.jurassicworldreborn.common.blocks.wood.DynamicWoodTypeRegistry;
import net.minecraft.world.level.block.Block;

public class AraucariaPlant extends Plant {
    @Override
    public String getName() {
        return "Araucaria";
    }

    @Override
    public Block getBlock() {
        return DynamicWoodTypeRegistry.getSaplingForType(CommonRegistries.AraucariaType);
    }

    @Override
    public int getHealAmount() {
        return 1000;
    }

    @Override
    public boolean isTree() {
        return true;
    }
}
