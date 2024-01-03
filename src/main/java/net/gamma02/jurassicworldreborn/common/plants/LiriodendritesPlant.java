package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;

public class LiriodendritesPlant extends Plant {
    @Override
    public String getName() {
        return "Liriodendrites";
    }

    @Override
    public Block getBlock() {
        return ModBlocks.LIRIODENDRITES.get();
    }

    @Override
    public int getHealAmount() {
        return 4000;
    }
}
