package net.gamma02.jurassicworldreborn.common.worldgen.tree;

import com.mojang.serialization.Codec;
import net.gamma02.jurassicworldreborn.common.CommonRegistries;
import net.gamma02.jurassicworldreborn.common.blocks.wood.DynamicWoodTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Material;

import java.util.Random;

public class PhoenixTreeGenerator extends Feature<NoneFeatureConfiguration> {
    public PhoenixTreeGenerator(Codec<NoneFeatureConfiguration> p_65786_) {
        super(p_65786_);

    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        BlockState log = DynamicWoodTypeRegistry.getProductFromWoodType(CommonRegistries.PhoenixType, DynamicWoodTypeRegistry.ProductType.LOG).defaultBlockState();
        BlockState leaves = DynamicWoodTypeRegistry.getProductFromWoodType(CommonRegistries.PhoenixType, DynamicWoodTypeRegistry.ProductType.LEAVES).defaultBlockState();;
        
        WorldGenLevel world = ctx.level();
        RandomSource rand = ctx.random();
        BlockPos position = ctx.origin();
        this.setBlockState(world, position, log);

        int height = rand.nextInt(6) + 7;

        for (int y = 0; y < height; y++) {
            this.setBlockState(world, position.above(y), log);
        }

        for (int palmX = -1; palmX < 2; palmX++) {
            for (int palmY = -1; palmY < 1; palmY++) {
                for (int palmZ = -1; palmZ < 2; palmZ++) {
                    this.setBlockState(world, position.offset(palmX, height + 1 + palmY, palmZ), leaves);
                }
            }
        }

        this.setBlockState(world, position.above(height + 2), leaves);
        this.setBlockState(world, position.above(height + 3), leaves);
        this.setBlockState(world, position.above(height + 4), leaves);

        this.setBlockState(world, position.offset(1, height + 2, 0), leaves);
        this.setBlockState(world, position.offset(-1, height + 2, 0), leaves);
        this.setBlockState(world, position.offset(0, height + 2, 1), leaves);
        this.setBlockState(world, position.offset(0, height + 2, -1), leaves);

        this.setBlockState(world, position.offset(2, height + 2, 0), leaves);
        this.setBlockState(world, position.offset(-2, height + 2, 0), leaves);
        this.setBlockState(world, position.offset(0, height + 2, 2), leaves);
        this.setBlockState(world, position.offset(0, height + 2, -2), leaves);

        this.setBlockState(world, position.offset(-2, height + 3, 0), leaves);
        this.setBlockState(world, position.offset(-3, height + 3, 0), leaves);
        this.setBlockState(world, position.offset(-4, height + 2, 0), leaves);

        this.setBlockState(world, position.offset(2, height + 3, 0), leaves);
        this.setBlockState(world, position.offset(3, height + 3, 0), leaves);
        this.setBlockState(world, position.offset(4, height + 2, 0), leaves);

        this.setBlockState(world, position.offset(0, height + 3, 2), leaves);
        this.setBlockState(world, position.offset(0, height + 3, 3), leaves);
        this.setBlockState(world, position.offset(0, height + 2, 4), leaves);

        this.setBlockState(world, position.offset(0, height + 3, -2), leaves);
        this.setBlockState(world, position.offset(0, height + 3, -3), leaves);
        this.setBlockState(world, position.offset(0, height + 2, -4), leaves);

        this.setBlockState(world, position.offset(2, height, 0), leaves);
        this.setBlockState(world, position.offset(-2, height, 0), leaves);
        this.setBlockState(world, position.offset(0, height, 2), leaves);
        this.setBlockState(world, position.offset(0, height, -2), leaves);

        this.setBlockState(world, position.offset(3, height - 1, 0), leaves);
        this.setBlockState(world, position.offset(-3, height - 1, 0), leaves);
        this.setBlockState(world, position.offset(0, height - 1, 3), leaves);
        this.setBlockState(world, position.offset(0, height - 1, -3), leaves);
        this.setBlockState(world, position.offset(3, height - 2, 0), leaves);
        this.setBlockState(world, position.offset(-3, height - 2, 0), leaves);
        this.setBlockState(world, position.offset(0, height - 2, 3), leaves);
        this.setBlockState(world, position.offset(0, height - 2, -3), leaves);

        if (rand.nextBoolean()) {
            this.setBlockState(world, position.offset(3, height - 3, 0), leaves);
            this.setBlockState(world, position.offset(-3, height - 3, 0), leaves);
            this.setBlockState(world, position.offset(0, height - 3, 3), leaves);
            this.setBlockState(world, position.offset(0, height - 3, -3), leaves);
        }

        this.setBlockState(world, position.offset(2, height + 1, 2), leaves);
        this.setBlockState(world, position.offset(2, height + 2, 2), leaves);
        this.setBlockState(world, position.offset(-2, height + 1, 2), leaves);
        this.setBlockState(world, position.offset(-2, height + 2, 2), leaves);
        this.setBlockState(world, position.offset(2, height + 1, -2), leaves);
        this.setBlockState(world, position.offset(2, height + 2, -2), leaves);
        this.setBlockState(world, position.offset(-2, height + 1, -2), leaves);
        this.setBlockState(world, position.offset(-2, height + 2, -2), leaves);

        this.setBlockState(world, position.offset(3, height - 1, 3), leaves);
        this.setBlockState(world, position.offset(3, height, 3), leaves);
        this.setBlockState(world, position.offset(3, height + 1, 3), leaves);

        this.setBlockState(world, position.offset(-3, height - 1, 3), leaves);
        this.setBlockState(world, position.offset(-3, height, 3), leaves);
        this.setBlockState(world, position.offset(-3, height + 1, 3), leaves);

        this.setBlockState(world, position.offset(3, height - 1, -3), leaves);
        this.setBlockState(world, position.offset(3, height, -3), leaves);
        this.setBlockState(world, position.offset(3, height + 1, -3), leaves);

        this.setBlockState(world, position.offset(-3, height - 1, -3), leaves);
        this.setBlockState(world, position.offset(-3, height, -3), leaves);
        this.setBlockState(world, position.offset(-3, height + 1, -3), leaves);

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
