package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;

public class TempskyaPlant extends Plant {
    @Override
    public String getName() {
        return "Tempskya";
    }

    @Override
    public Block getBlock() {
        return ModBlocks.TEMPSKYA.get();
    }

    @Override
    public int getHealAmount() {
        return 4000;
    }
}
