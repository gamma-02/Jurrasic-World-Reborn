package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;

/**
 * Created by Codyr on 11/11/2016.
 */
public class HippuritesRadiosusPlant extends Plant {
    @Override
    public String getName() { return "Hippurites Radiosus"; }

    @Override
    public Block getBlock() {
        return ModBlocks.HIPPURITES_RADIOSUS.get();
    }

    @Override
    public int getHealAmount() {
        return 2000;
    }
}
