package net.gamma02.jurassicworldreborn.common.blocks;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

public class ClearGlassBlock extends GlassBlock {

    public static BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static BooleanProperty UP = BlockStateProperties.UP;

    public static BooleanProperty EAST = BlockStateProperties.EAST;

    public static BooleanProperty WEST = BlockStateProperties.WEST;

        public static BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static BooleanProperty NORTH = BlockStateProperties.NORTH;


    public ClearGlassBlock(Properties p_53640_) {
        super(p_53640_);
        this.registerDefaultState(this.getStateDefinition().any().setValue(DOWN, false).setValue(UP, false).setValue(EAST, false).setValue(WEST, false).setValue(SOUTH, false).setValue(NORTH, false));
        Jurassicworldreborn.setRenderType(this, RenderType.translucent());
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {

        Level world = pContext.getLevel();

        BlockPos pos = pContext.getClickedPos();

        return this.defaultBlockState().setValue(NORTH, this.canConnectTo(world, pos.north())).setValue(EAST, this.canConnectTo(world, pos.east())).setValue(SOUTH, this.canConnectTo(world, pos.south())).setValue(WEST, this.canConnectTo(world, pos.west())).setValue(UP, this.canConnectTo(world, pos.above())).setValue(DOWN, this.canConnectTo(world, pos.below()));
    }

    public boolean canConnectTo(Level world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() instanceof ClearGlassBlock;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(UP, DOWN, NORTH, SOUTH, EAST, WEST);
    }

}
