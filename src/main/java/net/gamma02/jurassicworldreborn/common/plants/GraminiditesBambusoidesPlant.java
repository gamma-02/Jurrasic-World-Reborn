package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;

/**
 * Created by Codyr on 29/10/2016.
 */
public class    GraminiditesBambusoidesPlant extends Plant {
    @Override
    public String getName() {
        return "Graminidites Bambusoides";
    }

    @Override
    public Block getBlock() { return ModBlocks.GRAMINIDITES_BAMBUSOIDES.get(); }


    @Override
    public int getHealAmount() {
        return 4000;
    }
}

