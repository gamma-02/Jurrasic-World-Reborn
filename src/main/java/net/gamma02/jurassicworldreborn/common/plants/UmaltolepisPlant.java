package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;

public class UmaltolepisPlant extends Plant {
    @Override
    public String getName() {
        return "Umaltolepis";
    }

    @Override
    public Block getBlock() {
        return ModBlocks.UMALTOLEPIS.get();
    }

    @Override
    public int getHealAmount() {
        return 4000;
    }
}
