package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;

/**
 * Created by Codyr on 30/10/2016.
 */
public class EnallheliaPlant extends Plant {
    @Override
    public String getName() { return "Enallhelia"; }

    @Override
    public Block getBlock() {
        return ModBlocks.ENALLHELIA.get();
    }

    @Override
    public int getHealAmount() {
        return 2000;
    }
}
