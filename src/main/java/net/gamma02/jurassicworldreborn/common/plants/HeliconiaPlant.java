package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;

/**
 * Created by Codyr on 22/01/2017.
 */
public class HeliconiaPlant extends Plant {
    @Override
    public String getName() {
        return "Heliconia";
    }

    @Override
    public Block getBlock() {
        return ModBlocks.HELICONIA.get();
    }

    @Override
    public int getHealAmount() {
        return 4000;
    }

    @Override
    public boolean isPrehistoric() {
        return false;
    }
}
