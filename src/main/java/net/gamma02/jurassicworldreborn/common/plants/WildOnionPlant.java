package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;

public class WildOnionPlant extends Plant {
    @Override
    public String getName() {
        return "Wild Onion";
    }

    @Override
    public Block getBlock() {
        return ModBlocks.WILD_ONION.get();
    }

    @Override
    public int getHealAmount() {
        return 3000;
    }
}
