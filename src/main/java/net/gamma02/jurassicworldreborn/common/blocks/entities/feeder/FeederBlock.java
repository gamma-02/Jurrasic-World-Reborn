package net.gamma02.jurassicworldreborn.common.blocks.entities.feeder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class FeederBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

    public static DirectionProperty FACING = DirectionalBlock.FACING;

    public static final VoxelShape MODEL_SHAPE_NORTH = Shapes.join(Block.box(0, 0, 0, 16, 3, 16), Stream.of(
            Block.box(1.5, 2.45, 1.5, 14.5, 4.45, 14.5),
            Stream.of(
                    Block.box(13.75, 2, 1.5, 15.25, 3.75, 14.5),
                    Block.box(13.95, 2.25, 1.5, 14.95, 4.075, 14.5),
                    Block.box(13.45, 2, 1.5, 15.55, 3.45, 14.5)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
            Block.box(1.5, 2, 0.75, 14.5, 3.75, 1.95),
            Block.box(1.5, 2.25, 1.05, 14.5, 4.075, 1.95),
            Block.box(1.5, 2, 0.45, 14.5, 3.45, 1.95)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
            Block.box(0.75, 2, 1.5, 1.95, 3.75, 14.5),
            Block.box(1.05, 2.25, 1.5, 1.95, 4.075, 14.5),
            Block.box(0.45, 2, 1.5, 1.975, 3.45, 14.5)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
            Block.box(1.5, 2, 14.05, 14.5, 3.75, 15.25),
            Block.box(1.5, 2.25, 14.05, 14.5, 4.075, 14.95),
            Block.box(1.5, 2, 14.05, 14.5, 3.45, 15.55)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
            Block.box(3, 2.45, 3, 13, 11.45, 13),
            Stream.of(
            Block.box(2, 5.45, 2, 14, 6.45, 14),
            Block.box(2, 9.45, 2, 14, 10.45, 14),
            Block.box(2, 7.45, 2, 14, 8.45, 14)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
            Block.box(3.5, 10.95, 3.5, 12.5, 13.95, 12.5),
            Block.box(4, 13.45, 4, 12, 14.45, 8),
            Block.box(4, 13.45, 8, 12, 14.45, 12)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()
                    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(), BooleanOp.OR);



    public FeederBlock(Properties p_49224_) {
        super(p_49224_);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.UP).setValue(WATERLOGGED, false));
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return (world1, pos, state1, instance) -> {
            if (instance instanceof FeederBlockEntity instance1) {
                instance1.tick(world1, pos, state1, instance1);
            } else {
                super.getTicker(world, state, type).tick(world1, pos, state1, instance);
            }
        };
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return null;
    }

    public RenderShape getRenderShape(BlockState state){return RenderShape.MODEL;}

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction direction = pContext.getHorizontalDirection().getOpposite();
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        return this.defaultBlockState().setValue(FACING, direction).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, WATERLOGGED);
    }
    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        switch (pState.getValue(FACING)){
            case EAST, WEST, NORTH, SOUTH:
                return MODEL_SHAPE_NORTH;
            default:
                return Block.box(1, 0, 1, 15, 14, 15);
        }
    }

}
