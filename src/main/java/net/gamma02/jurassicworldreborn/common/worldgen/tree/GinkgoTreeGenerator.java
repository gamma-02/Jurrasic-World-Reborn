package net.gamma02.jurassicworldreborn.common.worldgen.tree;

import com.mojang.serialization.Codec;
import net.gamma02.jurassicworldreborn.common.CommonRegistries;
import net.gamma02.jurassicworldreborn.common.blocks.wood.DynamicWoodTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Material;

import java.util.Iterator;
import java.util.Random;

public class GinkgoTreeGenerator extends Feature<NoneFeatureConfiguration> {

    public GinkgoTreeGenerator(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockState log = DynamicWoodTypeRegistry.getProductFromWoodType(CommonRegistries.GinkgoType, DynamicWoodTypeRegistry.ProductType.LOG).defaultBlockState();
        BlockState leaves = DynamicWoodTypeRegistry.getProductFromWoodType(CommonRegistries.GinkgoType, DynamicWoodTypeRegistry.ProductType.LEAVES).defaultBlockState();
        Random rand = context.random();
        BlockPos position = context.origin();
        WorldGenLevel world = context.level();

        this.setBlockState(world, position, log);

        int height = rand.nextInt(16) + 4;

        for (int y = 0; y < height; y++) {
            BlockPos logPos = position.above(y);
            this.setBlockState(world, logPos, log);

            int branchLength = Math.max(1, (height - y) / 3);

            if (y >= 2) {
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        if (x != 0 || z != 0) {
                            this.setBlockState(world, logPos.offset(x, 0, z), leaves);
                        }
                    }
                }

                int bushSize = (int) (branchLength * 0.8);

                for (int x = -bushSize; x <= bushSize; x++) {
                    for (int z = -bushSize; z <= bushSize; z++) {
                        if ((x != 0 || z != 0) && Math.sqrt(x * x + z * z) < bushSize) {
                            this.setBlockState(world, logPos.offset(x, 0, z), leaves);
                        }
                    }
                }
            }

            if (y % 3 == 2) {
                for (Direction facing : Direction.Plane.HORIZONTAL) {
                    BlockPos branchPos = logPos.offset(facing.getNormal());
                    BlockState facingLog = log.setValue(RotatedPillarBlock.AXIS, facing.getAxis());

                    this.setBlockState(world, branchPos, facingLog);

                    this.setBlockState(world, branchPos.above(2), leaves);
                    this.setBlockState(world, branchPos.below(), leaves);
                    this.setBlockState(world, branchPos.relative(facing.getClockWise(), 2), leaves);
                    this.setBlockState(world, branchPos.relative(facing.getCounterClockWise(), 2), leaves);

                    for (int i = 0; i < branchLength; i++) {
                        BlockPos pos = branchPos.relative(facing, i + 1).above(i / 2 + 1);

                        this.setBlockState(world, pos, facingLog);
                        this.setBlockState(world, pos.above(), leaves);
                        this.setBlockState(world, pos.below(), leaves);
                        this.setBlockState(world, pos.relative(facing.getClockWise()), leaves);
                        this.setBlockState(world, pos.relative(facing.getCounterClockWise()), leaves);

                        if (i >= branchLength - 1) {
                            this.setBlockState(world, pos.relative(facing), leaves);
                        }
                    }
                }
            }
        }

        this.setBlockState(world, position.above(height), leaves);
        this.setBlockState(world, position.above(height).north(), leaves);
        this.setBlockState(world, position.above(height).south(), leaves);
        this.setBlockState(world, position.above(height).west(), leaves);
        this.setBlockState(world, position.above(height).east(), leaves);
        this.setBlockState(world, position.above(height + 1), leaves);

        return true;
    }

    private void setBlockState(WorldGenLevel world, BlockPos pos, BlockState state) {
        Block block = world.getBlockState(pos).getBlock();
        if (isReplaceablePlant(world, pos) || block instanceof LeavesBlock || block instanceof SaplingBlock || block instanceof RotatedPillarBlock || block == Blocks.AIR) {
            world.setBlock(pos, state, 19);
        }
    }
    static boolean isReplaceablePlant(WorldGenLevel p_67289_, BlockPos p_67290_) {
        return p_67289_.isStateAtPosition(p_67290_, (p_160551_) -> {
            Material material = p_160551_.getMaterial();
            return material == Material.REPLACEABLE_PLANT;
        });
    }
}
