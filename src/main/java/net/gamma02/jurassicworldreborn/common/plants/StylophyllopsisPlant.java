package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;

/**
 * Created by Codyr on 11/11/2016.
 */
public class StylophyllopsisPlant extends Plant {
    @Override
    public String getName() { return "Stylophyllopsis"; }

    @Override
    public Block getBlock() {
        return ModBlocks.STYLOPHYLLOPSIS.get();
    }

    @Override
    public int getHealAmount() {
        return 2000;
    }
}
