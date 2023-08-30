package net.gamma02.jurassicworldreborn.common.worldgen.tree.petrified;

import com.mojang.serialization.Codec;
import net.gamma02.jurassicworldreborn.common.blocks.wood.DynamicWoodTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.function.Predicate;

import static net.gamma02.jurassicworldreborn.common.blocks.wood.DynamicWoodTypeRegistry.woodTypes;

public class PetrifiedTreeGenerator extends Feature<PetrifiedTreeConfig> {

    public PetrifiedTreeGenerator(Codec<PetrifiedTreeConfig> p_65786_) {
        super(p_65786_);
    }

    @Override
    public boolean place(FeaturePlaceContext<PetrifiedTreeConfig> context) {
        RandomSource random = context.random();
        float chance = random.nextInt(100);
        chance = Float.parseFloat(Float.toString(chance).substring(4));
//        if(context.config().chance >= chance) {
            WorldGenLevel world = context.level();

            BlockPos pos = context.origin();

            System.out.println("PLACING AT: " + pos);

            int randPosX = world.getChunk(pos).getPos().x + random.nextInt(16) + 8;
            int randPosZ = world.getChunk(pos).getPos().z + random.nextInt(16) + 8;
            int randPosY = random.nextInt(Math.max(1, world.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, pos).getY() - 10));
            ArrayList<WoodType> types = new ArrayList<WoodType>(woodTypes);
            Collections.shuffle(types);
            this.generatePetrifiedTree(world, types.get(0), randPosX, randPosY, randPosZ, random, context.config());

            if(world.getBlockState(pos).getBlock() instanceof RotatedPillarBlock) {
                System.out.println("COMPLETED GENERATION");
                return true;
            }else {
                return false;
            }
//        }else{
//            return false;
//        }

    }


    private void generatePetrifiedTree(WorldGenLevel world, WoodType treeType, int x, int y, int z, RandomSource rand, PetrifiedTreeConfig config) {
        Predicate<BlockState> predicate = (state) ->  Feature.isReplaceable(BlockTags.FEATURES_CANNOT_REPLACE).test(state) || state.isAir();
        float rotX = (float) (rand.nextDouble() * 360.0F);
        float rotY = (float) (rand.nextDouble() * 360.0F) - 180.0F;

        BlockState state = DynamicWoodTypeRegistry.getProductFromWoodType(treeType, DynamicWoodTypeRegistry.ProductType.PETRIFIED_LOG).defaultBlockState();

        float horizontal = Mth.cos(rotX * (float) Math.PI / 180.0F);
        float vertical = Mth.sin(rotX * (float) Math.PI / 180.0F);

        float xOffset = -Mth.sin(rotY * (float) Math.PI / 180.0F) * horizontal;
        float yOffset = Mth.cos(rotY * (float) Math.PI / 180.0F) * horizontal;

        for (int i = 0; i < rand.nextInt(config.size) + 2; i++) {

            int blockX = x + Math.round(xOffset * i);
            int blockY = y + Math.round(vertical * i);
            int blockZ = z + Math.round(yOffset * i);
            if (blockY > world.getMinBuildHeight() && blockY < world.getMaxBuildHeight()) {
                BlockPos pos = new BlockPos(blockX, blockY, blockZ);

                if(world.isAreaLoaded(pos, 20))
                    this.safeSetBlock(world, pos, state, predicate);

            }
        }

    }

}
