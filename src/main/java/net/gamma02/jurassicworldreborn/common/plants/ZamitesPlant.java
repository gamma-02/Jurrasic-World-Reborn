package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;

public class ZamitesPlant extends Plant {
    @Override
    public String getName() {
        return "Cycad Zamites";
    }

    @Override
    public Block getBlock() {
        return ModBlocks.ZAMITES.get();
    }

    @Override
    public int getHealAmount() {
        return 4000;
    }
}
