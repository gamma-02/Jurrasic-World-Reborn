package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;

/**
 * Created by Codyr on 10/11/2016.
 */
public class AuloporaPlant extends Plant {
    @Override
    public String getName() { return "Aulopora"; }

    @Override
    public Block getBlock() {
        return ModBlocks.AULOPORA.get();
    }

    @Override
    public int getHealAmount() {
        return 2000;
    }
}
