package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;

public class RaphaeliaPlant extends Plant {
    @Override
    public String getName() {
        return "Raphaelia";
    }

    @Override
    public Block getBlock() {
        return ModBlocks.RAPHAELIA.get();
    }

    @Override
    public int getHealAmount() {
        return 2000;
    }
}
