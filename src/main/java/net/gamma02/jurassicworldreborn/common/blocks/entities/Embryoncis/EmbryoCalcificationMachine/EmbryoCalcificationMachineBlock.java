package net.gamma02.jurassicworldreborn.common.blocks.entities.Embryoncis.EmbryoCalcificationMachine;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class EmbryoCalcificationMachineBlock extends Block {

    public static DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static BooleanProperty EGG = BooleanProperty.create("egg");

    public static final VoxelShape NORTH_SHAPE = Shapes.join(Block.box(1.5, 0, 4, 14.5, 7, 16), Block.box(0.5, 0, 5, 15.5, 8, 15), BooleanOp.OR);
    public static final VoxelShape WEST_SHAPE = Shapes.join(Block.box(4, 0, 1.5, 16, 7, 14.5), Block.box(5, 0, 0.5, 15, 8, 15.5), BooleanOp.OR);
    public static final VoxelShape SOUTH_SHAPE = Shapes.join(Block.box(1.5, 0, 0, 14.5, 7, 12), Block.box(0.5, 0, 1, 15.5, 8, 11), BooleanOp.OR);
    public static final VoxelShape EAST_SHAPE = Shapes.join(Block.box(0, 0, 1.5, 12, 7, 14.5), Block.box(1, 0, 0.5, 11, 8, 15.5), BooleanOp.OR);


    public EmbryoCalcificationMachineBlock(Properties p_52591_) {
        super(p_52591_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(EGG, false));
        Jurassicworldreborn.setRenderType(this, RenderType.cutoutMipped());
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
        pBuilder.add(EGG);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        switch (pState.getValue(FACING)){
            case EAST:
                return EAST_SHAPE;
            case SOUTH:
                return SOUTH_SHAPE;
            case NORTH:
                return NORTH_SHAPE;
            case WEST:
                return WEST_SHAPE;
            default:
                return Block.box(1, 0, 1, 15, 14, 15);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }
}
