package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;

/**
 * Created by Codyr on 29/10/2016.
 */
public class RhacophytonPlant extends Plant {
    @Override
    public String getName() {
        return "Rhacophyton";
    }

    @Override
    public Block getBlock() { return ModBlocks.RHACOPHYTON.get(); }
    

    @Override
    public int getHealAmount() {
        return 4000;
    }
}
