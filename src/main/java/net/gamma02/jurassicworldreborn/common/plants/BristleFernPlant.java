package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;

public class BristleFernPlant extends Plant {
    @Override
    public String getName() {
        return "Bristle Fern";
    }

    @Override
    public Block getBlock() {
        return ModBlocks.BRISTLE_FERN.get();
    }

    @Override
    public int getHealAmount() {
        return 2000;
    }
}
