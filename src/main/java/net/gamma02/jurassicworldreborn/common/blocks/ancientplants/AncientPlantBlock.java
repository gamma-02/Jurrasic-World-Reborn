package net.gamma02.jurassicworldreborn.common.blocks.ancientplants;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class AncientPlantBlock extends BushBlock {

    private static final int DENSITY_PER_AREA = 4;
    private static final int SPREAD_RADIUS = 6;


    public AncientPlantBlock(Properties properties) {
        super(properties);
        Jurassicworldreborn.renderlayers.put(this, RenderType.cutout());
    }

    public AncientPlantBlock(){
        this(ModBlocks.defaultPlant());
    }

    @Override
    public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
        //the LevelReader should always be a Level, so this should be safe:
        Level world = (Level)level;
        Random rand = world.getRandom();
//            if (GameRuleHandler.PLANT_SPREADING.getBoolean(world)) {todo SOON: gamerules
                int light = world.getLightEmission(pos);

                if (light >= 5) {
                    if (rand.nextInt((75 - light) / 2 + 10) == 0) {
                        int allowedInArea = DENSITY_PER_AREA;

                        BlockPos nextPos = null;
                        int placementAttempts = 3;

                        while (nextPos == null && placementAttempts > 0) {
                            int doubleRadius = SPREAD_RADIUS * 2;
                            BlockPos tmp = pos.offset(rand.nextInt(doubleRadius) - SPREAD_RADIUS, SPREAD_RADIUS, rand.nextInt(doubleRadius) - SPREAD_RADIUS);
                            nextPos = this.findGround(world, tmp);
                            placementAttempts--;
                        }

                        if (nextPos != null) {
//                            for (BlockPos neighbourPos : BlockPos.getAllInBoxMutable(nextPos.add(-2, -3, -2), nextPos.add(2, 3, 2))) {//B RUH
                            for (int x = -2; x <= 2; x++) {
                                for (int y = -3; y <= 3; y++) {
                                    for (int z = -2; z <= 2; z++) {
                                        BlockPos neighbourPos = nextPos.offset(x, y, z);
                                        if (world.getBlockState(neighbourPos).getBlock() instanceof BushBlock) {
                                            allowedInArea--;

                                            if (allowedInArea <= 0) {
                                                return;
                                            }
                                        }
                                    }
                                }
                            }

                            if (this.isNearWater(world, pos)) {
                                this.spread(world, nextPos);
                            }
                        }
                    }
                }
//            }

    }
    protected boolean isNearWater(Level world, BlockPos nextPos) {
//        for (BlockPos neighbourPos : BlockPos.getAllInBoxMutable(nextPos.add(-8, -3, -8), nextPos.add(8, 3, 8))) {//w h a t  -gamma
            //I need to make a museum of all of the weird stuff I see in this - gamma
        //this loops through all of the blockPoses in a 17x7x17 box centered around nextPos
        for (int x = -8; x <= 8; x++) {
            for (int y = -3; y <= 3; y++) {
                for (int z = -8; z <= 8; z++) {
                    BlockPos neighbourPos = nextPos.offset(x, y, z);
                    Block neighbourState = world.getBlockState(neighbourPos).getBlock();

                    if (neighbourState == Blocks.WATER || neighbourState == Blocks.WATER) {
                        if (neighbourPos.distSqr(nextPos) < 9) {//this is weird code, why do you need this here? - gamma_02
                            return true;
                        }
                    }

                }
            }

        }

        return false;
    }

    protected void spread(Level world, BlockPos position) {
        if(!world.isClientSide) {
            world.setBlock(position, this.defaultBlockState(), 1);
        }
    }

    protected BlockPos findGround(Level world, BlockPos start) {
        BlockPos pos = start;

        BlockState down = world.getBlockState(pos.below());
        BlockState here = world.getBlockState(pos);
        BlockState up = world.getBlockState(pos.above());

        for (int i = 0; i < 8; ++i) {
            if (this.canPlace(down, here, pos.below(), world)) {
                return pos.below();
            }

            down = here;
            here = up;
            pos = pos.above();
            up = world.getBlockState(pos);
        }

        return null;
    }
    protected boolean canPlace(BlockState down, BlockState here, BlockPos below, Level world) {//bruh
        return down.canSustainPlant(world, below,  Direction.UP, this) && here.getBlock() == Blocks.AIR;
    }
}
