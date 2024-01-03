package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.CommonRegistries;
import net.gamma02.jurassicworldreborn.common.blocks.wood.DynamicWoodTypeRegistry;
import net.minecraft.world.level.block.Block;

public class GinkgoPlant extends Plant {
    @Override
    public String getName() {
        return "Ginkgo";
    }

    @Override
    public Block getBlock() {
        return DynamicWoodTypeRegistry.getSaplingForType(CommonRegistries.GinkgoType);
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
