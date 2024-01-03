package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;

public class WoollyStalkedBegoniaPlant extends Plant {
    @Override
    public String getName() { return "Woolly Stalked Begonia"; }

    @Override
    public Block getBlock() {
        return ModBlocks.WOOLLY_STALKED_BEGONIA.get();
    }

    @Override
    public int getHealAmount() {
        return 2000;
    }
}
