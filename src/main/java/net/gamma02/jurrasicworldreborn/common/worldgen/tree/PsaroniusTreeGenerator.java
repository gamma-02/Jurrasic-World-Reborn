package net.gamma02.jurrasicworldreborn.common.worldgen.tree;

import com.mojang.serialization.Codec;
import net.gamma02.jurrasicworldreborn.common.CommonRegistries;
import net.gamma02.jurrasicworldreborn.common.blocks.wood.DynamicWoodTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Material;

import java.util.Random;

public class PsaroniusTreeGenerator extends Feature<NoneFeatureConfiguration> {

    public PsaroniusTreeGenerator(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockState log = DynamicWoodTypeRegistry.getProductFromWoodType(CommonRegistries.PsaroniusType, DynamicWoodTypeRegistry.ProductType.LOG).defaultBlockState();
        BlockState leaves = DynamicWoodTypeRegistry.getProductFromWoodType(CommonRegistries.PsaroniusType, DynamicWoodTypeRegistry.ProductType.LEAVES).defaultBlockState();

        WorldGenLevel world = context.level();
        Random rand = context.random();
        BlockPos position = context.origin();
        
        
        
        int scale = rand.nextInt(1) + 1;
        int height = scale + 6 + rand.nextInt(2);
        BlockPos topPosition = position.above(height);
        for (int y = 0; y < height; y++) {
            BlockPos logPosition = position.above(y);
            this.setBlockState(world, logPosition, log);
        }
        int leafScale = scale + 2;
        for (int x = -leafScale; x <= leafScale; x++) {
            this.setBlockState(world, topPosition.offset(x, 0, 0), leaves);
        }
        for (int z = -leafScale; z <= leafScale; z++) {
            this.setBlockState(world, topPosition.offset(0, 0, z), leaves);
        }
        this.setBlockState(world, topPosition.offset(-leafScale - 1, -1, 0), leaves);
        this.setBlockState(world, topPosition.offset(leafScale + 1, -1, 0), leaves);
        this.setBlockState(world, topPosition.offset(0, -1, -leafScale - 1), leaves);
        this.setBlockState(world, topPosition.offset(0, -1, leafScale + 1), leaves);
        this.setBlockState(world, topPosition.offset(-2, -1, -2), leaves);
        this.setBlockState(world, topPosition.offset(-2, -1, 2), leaves);
        this.setBlockState(world, topPosition.offset(2, -1, 2), leaves);
        this.setBlockState(world, topPosition.offset(2, -1, -2), leaves);
        this.generateClump(world, topPosition, 2.5, leaves);
        return true;
    }

    private void generateClump(WorldGenLevel world, BlockPos pos, double size, BlockState state) {
        int blockRadius = (int) Math.ceil(size);
        for (int x = -blockRadius; x < blockRadius; x++) {
            for (int y = -blockRadius; y < blockRadius; y++) {
                for (int z = -blockRadius; z < blockRadius; z++) {
                    if (Math.abs(x * x + y * y + z * z) <= size) {
                        BlockPos leafPos = pos.offset(x, y, z);
                        if (world.getBlockState(leafPos).isAir()) {
                            this.setBlockState(world, leafPos, state);
                        }
                    }
                }
            }
        }
    }

    private void setBlockState(WorldGenLevel world, BlockPos pos, BlockState state) {
        Block block = world.getBlockState(pos).getBlock();
        if (isReplaceablePlant(world, pos) || block instanceof LeavesBlock || block instanceof SaplingBlock || block instanceof RotatedPillarBlock) {
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
