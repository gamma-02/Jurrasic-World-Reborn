package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;

public class WildPotatoPlant extends Plant {
    @Override
    public String getName() {
        return "Wild Potato";
    }

    @Override
    public Block getBlock() {
        return ModBlocks.WILD_POTATO_PLANT.get();
    }

    @Override
    public int getHealAmount() {
        return 3000;
    }
}
