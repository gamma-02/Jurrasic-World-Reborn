package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;

public class DicroidiumZuberiPlant extends Plant {
    @Override
    public String getName() {
        return "Dicroidium Zuberi";
    }

    @Override
    public Block getBlock() {
        return ModBlocks.DICROIDIUM_ZUBERI.get();
    }

    @Override
    public int getHealAmount() {
        return 4000;
    }
}
