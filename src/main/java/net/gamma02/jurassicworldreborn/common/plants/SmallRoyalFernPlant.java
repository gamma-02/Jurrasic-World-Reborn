package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;

public class SmallRoyalFernPlant extends Plant {
    @Override
    public String getName() {
        return "Small Royal Fern";
    }

    @Override
    public Block getBlock() {
        return ModBlocks.SMALL_ROYAL_FERN.get();
    }

    @Override
    public int getHealAmount() {
        return 2000;
    }
}
