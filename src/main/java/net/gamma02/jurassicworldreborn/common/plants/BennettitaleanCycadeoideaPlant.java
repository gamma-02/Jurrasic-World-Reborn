package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;

public class BennettitaleanCycadeoideaPlant extends Plant {
    @Override
    public String getName() {
        return "Bennettitalean Cycadeoidea";
    }

    @Override
    public Block getBlock() {
        return ModBlocks.CYCADEOIDEA.get();
    }

    @Override
    public int getHealAmount() {
        return 2000;
    }
}
