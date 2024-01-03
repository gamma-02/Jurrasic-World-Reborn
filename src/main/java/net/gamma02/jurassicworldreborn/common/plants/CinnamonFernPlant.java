package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;

public class CinnamonFernPlant extends Plant {
    @Override
    public String getName() {
        return "Cinnamon Fern";
    }

    @Override
    public Block getBlock() {
        return ModBlocks.CINNAMON_FERN.get();
    }

    @Override
    public int getHealAmount() {
        return 2000;
    }
}
