package net.gamma02.jurassicworldreborn.common.plants;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;

/**
 * Created by Codyr on 26/10/2016.
 */
public class LargestipuleLeatherRootPlant extends Plant{
    @Override
    public String getName() { return "Largestipule Leather Root"; }

    @Override
    public Block getBlock() { return ModBlocks.LARGESTIPULE_LEATHER_ROOT.get(); }

        @Override
        public int getHealAmount () {
            return 2000;
        }

    }
