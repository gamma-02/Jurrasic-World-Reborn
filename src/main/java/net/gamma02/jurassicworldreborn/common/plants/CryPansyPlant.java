package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;

public class CryPansyPlant extends Plant {
    @Override
    public String getName() {
        return "Cry Pansy";
    }

    @Override
    public Block getBlock() {
        return ModBlocks.CRY_PANSY.get();
    }

    @Override
    public int getHealAmount() {
        return 250;
    }
}
