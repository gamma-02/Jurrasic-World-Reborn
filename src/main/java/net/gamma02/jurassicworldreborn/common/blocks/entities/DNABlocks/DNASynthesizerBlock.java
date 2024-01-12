package net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class DNASynthesizerBlock extends HorizontalDirectionalBlock {

    public static DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public static final VoxelShape MODEL_SHAPE_NORTH = Stream.of(
            Block.box(0.5, 0, 0, 12.5, 8, 13),
            Stream.of(
                    Block.box(13, 0, 2.5, 16, 1, 10.5),
                    Shapes.join(Block.box(13.5, 1, 3, 15.5, 4, 5), Block.box(14, 4, 8.5, 15, 5, 9.5), BooleanOp.OR),
                    Shapes.join(Block.box(13.5, 1, 8, 15.5, 4, 10), Block.box(14, 4, 3.5, 15, 5, 4.5), BooleanOp.OR)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Block.box(-0.5, 7.75, 0, 13.5, 14.75, 10)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public final static VoxelShape MODEL_SHAPE_WEST = Stream.of(
            Block.box(0, 0, 3.5, 13, 8, 15.5),
            Stream.of(
                    Block.box(2.5, 0, 0, 10.5, 1, 3),
                    Shapes.join(Block.box(3, 1, 0.5, 5, 4, 2.5), Block.box(8.5, 4, 1, 9.5, 5, 2), BooleanOp.OR),
                    Shapes.join(Block.box(8, 1, 0.5, 10, 4, 2.5), Block.box(3.5, 4, 1, 4.5, 5, 2), BooleanOp.OR)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Block.box(0, 7.75, 2.5, 10, 14.75, 16.5)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public final static VoxelShape MODEL_SHAPE_SOUTH = Stream.of(
            Block.box(3.5, 0, 3, 15.5, 8, 16),
            Stream.of(
                    Block.box(0, 0, 5.5, 3, 1, 13.5),
                    Shapes.join(Block.box(0.5, 1, 11, 2.5, 4, 13), Block.box(1, 4, 6.5, 2, 5, 7.5), BooleanOp.OR),
                    Shapes.join(Block.box(0.5, 1, 6, 2.5, 4, 8), Block.box(1, 4, 11.5, 2, 5, 12.5), BooleanOp.OR)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Block.box(2.5, 7.75, 6, 16.5, 14.75, 15.95)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape MODEL_SHAPE_EAST = Stream.of(
            Block.box(3, 0, 0.5, 16, 8, 12.5),
            Stream.of(
                    Block.box(5.5, 0, 13, 13.5, 1, 16),
                    Shapes.join(Block.box(11, 1, 13.5, 13, 4, 15.5), Block.box(6.5, 4, 14, 7.5, 5, 15), BooleanOp.OR),
                    Shapes.join(Block.box(6, 1, 13.5, 8, 4, 15.5), Block.box(11.5, 4, 14, 12.5, 5, 15), BooleanOp.OR)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Block.box(5, 7.75, -0.5, 15.95, 14.75, 13.5)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public DNASynthesizerBlock(Properties p_52591_) {
        super(p_52591_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        switch (pState.getValue(FACING)){
            case EAST:
                return MODEL_SHAPE_EAST;
            case SOUTH:
                return MODEL_SHAPE_SOUTH;
            case NORTH:
                return MODEL_SHAPE_NORTH;
            case WEST:
                return MODEL_SHAPE_WEST;
            default:
                return Block.box(1, 0, 1, 15, 14, 15);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
        super.createBlockStateDefinition(pBuilder);
    }
}
