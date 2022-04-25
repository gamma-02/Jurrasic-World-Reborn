package net.gamma02.jurrasicworldreborn.common.worldgen.tree;

import com.mojang.serialization.Codec;
import net.gamma02.jurrasicworldreborn.common.CommonRegistries;
import net.gamma02.jurrasicworldreborn.common.blocks.wood.DynamicWoodTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.Random;

public class CalamitesTreeGenerator extends Feature<NoneFeatureConfiguration> {
    public CalamitesTreeGenerator(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockState log = DynamicWoodTypeRegistry.getProductFromWoodType(CommonRegistries.CalamitesType, DynamicWoodTypeRegistry.ProductType.LOG).defaultBlockState();
        BlockState leaves = DynamicWoodTypeRegistry.getProductFromWoodType(CommonRegistries.CalamitesType, DynamicWoodTypeRegistry.ProductType.LEAVES).defaultBlockState();
        WorldGenLevel world = context.level();
        Random rand = context.random();
        BlockPos position = context.origin();

        this.setBlockState(world, position, log);

        int height = rand.nextInt(10) + 10;
        int branchIndex = 0;

        int halfDistance = height / 2;

        for (int y = 0; y < height; y++) {
            BlockPos logPos = position.above(y);
            this.setBlockState(world, logPos, log);

            boolean upperHalf = y > halfDistance;

            branchIndex++;

            if (branchIndex > (upperHalf ? 2 : 3)) {
                branchIndex = 0;
            }

            boolean branch = upperHalf ? branchIndex >= 2 : branchIndex >= 3;

            if (branch) {
                for (Direction facing : Direction.Plane.HORIZONTAL) {

                    BlockPos branchPos = logPos.relative(facing);
                    BlockState facingLog = log.setValue(RotatedPillarBlock.AXIS, facing.getAxis());

                    this.setBlockState(world, branchPos, facingLog);
                    this.setBlockState(world, branchPos.above(), leaves);

                    int leaveOut = Math.max(1, (upperHalf ? -(halfDistance - y) : (halfDistance - y) + halfDistance) / 2) + (rand.nextInt(2) - 1);

                    for (int i = 0; i < leaveOut; i++) {
                        BlockPos leavePos = branchPos.relative(facing, i + 1).above(i / 2 + 1);

                        this.setBlockState(world, leavePos, leaves);

                        if (!upperHalf) {
                            if (i < leaveOut / 4 || height < 12) {
                                this.setBlockState(world, leavePos.above(), leaves);
                            }

                            if (i < leaveOut - 2) {
                                this.setBlockState(world, leavePos.below(), leaves);
                                this.setBlockState(world, leavePos.relative(facing.getCounterClockWise()), leaves);
                                this.setBlockState(world, leavePos.relative(facing.getClockWise()), leaves);
                            } else if (i >= leaveOut - 2) {
                                this.setBlockState(world, leavePos.above(), leaves);
                            }
                        } else if (i >= leaveOut - 1) {
                            this.setBlockState(world, leavePos.above(), leaves);
                            this.setBlockState(world, leavePos.above(1).relative(facing), leaves);
                        }
                    }

                    if (!upperHalf) {
                        this.setBlockState(world, branchPos.relative(facing).above(), facingLog);
                        this.setBlockState(world, branchPos.relative(facing), leaves);
                    } else {
                        this.setBlockState(world, branchPos.relative(facing).above(2), leaves);
                    }
                }
            }
        }

        for (int i = 0; i < height / 4 + 1; i++) {
            this.setBlockState(world, position.above(height + i), leaves);
        }

        return true;
    }

    private void setBlockState(WorldGenLevel world, BlockPos pos, BlockState state) {
        Block block = world.getBlockState(pos).getBlock();
        if (AraucariaTreeGenerator.isReplaceablePlant(world, pos) || block instanceof LeavesBlock || block instanceof SaplingBlock || block instanceof RotatedPillarBlock || block == Blocks.AIR) {
            world.setBlock(pos, state, 19);
        }
    }
}
