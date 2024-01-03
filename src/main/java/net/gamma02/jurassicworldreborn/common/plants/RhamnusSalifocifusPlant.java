package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;

public class RhamnusSalifocifusPlant extends Plant {
    @Override
    public String getName() {
        return "Rhamnus Salifocifus";
    }

    @Override
    public Block getBlock() {
        return ModBlocks.RHAMNUS_SALICIFOLIUS_PLANT.get();
    }

    @Override
    public int getHealAmount() {
        return 3000;
    }
}
