package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;

public class DicksoniaPlant extends Plant {
    @Override
    public String getName() {
        return "Dicksonia";
    }

    @Override
    public Block getBlock() {
        return ModBlocks.DICKSONIA.get();
    }

    @Override
    public int getHealAmount() {
        return 4000;
    }
}
