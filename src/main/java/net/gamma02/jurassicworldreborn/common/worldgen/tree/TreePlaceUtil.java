package net.gamma02.jurassicworldreborn.common.worldgen.tree;

import net.gamma02.jurassicworldreborn.common.blocks.wood.ModSaplingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.TreeFeature;

public class TreePlaceUtil {

    public static boolean validTreePos(WorldGenLevel level, BlockPos pos){
        return (TreeFeature.validTreePos(level, pos) || level.getBlockState(pos).getBlock() instanceof ModSaplingBlock);
    }
}
