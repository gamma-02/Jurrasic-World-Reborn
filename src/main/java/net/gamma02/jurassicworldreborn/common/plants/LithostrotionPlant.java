package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;

/**
 * Created by Codyr on 11/11/2016.
 */
public class LithostrotionPlant extends Plant {
    @Override
    public String getName() { return "Lithostrotion"; }

    @Override
    public Block getBlock() {
        return ModBlocks.LITHOSTROTION.get();
    }

    @Override
    public int getHealAmount() {
        return 2000;
    }
}

