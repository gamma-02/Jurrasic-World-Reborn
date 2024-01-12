package net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNAExtractor;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class DNAExtractorBlock extends BaseEntityBlock {

    public static DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public static final VoxelShape MODEL_SHAPE_NORTH = Block.box(0, 0, 1.5, 16, 13, 14);
    public static final VoxelShape MODEL_SHAPE_WEST = Block.box(1.5, 0, 0, 14, 13, 16);
    public static final VoxelShape MODEL_SHAPE_SOUTH = Block.box(0, 0, 2, 16, 13, 14.5);
    public static final VoxelShape MODEL_SHAPE_EAST = Block.box(2, 0, 0, 14.5, 13, 16);


    public DNAExtractorBlock(Properties p_52591_) {
        super(p_52591_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
        Jurassicworldreborn.setRenderType(this, RenderType.cutoutMipped());
    }

//    @Nullable
//    @Override
//    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
//        return (world1, pos, state1, instance) -> {
//            if (instance instanceof DNACombinatorHybridizerBlockEntity) {
//                ((DNACombinatorHybridizerBlockEntity) instance).tick(world1, pos, state1, (DNACombinatorHybridizerBlockEntity) instance);
//            } else {
//                super.getTicker(world, state, type).tick(world1, pos, state1, instance);
//            }
//        };
//    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new DNAExtractorBlockEntity(pPos, pState);
    }
    public RenderShape getRenderShape(BlockState state){return RenderShape.ENTITYBLOCK_ANIMATED;}

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
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }
}
