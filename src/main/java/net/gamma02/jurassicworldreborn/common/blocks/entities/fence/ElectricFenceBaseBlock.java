package net.gamma02.jurassicworldreborn.common.blocks.entities.fence;

import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Handler;

public class ElectricFenceBaseBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING_BIAS = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty POLE = BooleanProperty.create("pole");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final IntegerProperty CONNECTIONS = IntegerProperty.create("connections", 0, 4);

//    private static final AxisAlignedBB EXTENDED_BOUNDS = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.5, 1.0) what on earth did that do
    private FenceType type;

    public ElectricFenceBaseBlock(FenceType type, BlockBehaviour.Properties properties) {
        super(properties);

        this.registerDefaultState(this.defaultBlockState().setValue(FACING_BIAS, Direction.NORTH).setValue(NORTH, false).setValue(SOUTH, false).setValue(WEST, false).setValue(EAST, false));
        this.type = type;
    }


//    @Override
//    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox,
//                                      List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_) {
//        if (entityIn instanceof DinosaurEntity && !world.isAirBlock(pos.up())) {
//            addCollisionBoxToList(pos, entityBox, collidingBoxes, EXTENDED_BOUNDS);
//        } else {
//            super.addCollisionBoxToList(state, world, pos, entityBox, collidingBoxes, entityIn, p_185477_7_);
//        }
//    }


    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

//    @Override
//    public EnumBlockRenderType getRenderType(IBlockState state) {
//        return EnumBlockRenderType.MODEL;
//    }


//    @Override
//    private boolean cli(BlockGetter world, BlockPos pos, Direction pDirection) {
//        BlockState blockstate = world.getBlockState(pos);
//        BlockEntity wireEntity = world.getBlockEntity(pos.above());
//        if (wireEntity instanceof ElectricFenceWireBlockEntity && ((ElectricFenceWireBlockEntity) wireEntity).isPowered()) {
//            if (entity instanceof DinosaurEntity) {
//                DinosaurEntity dinosaur = (DinosaurEntity) entity;
//                if (dinosaur.getDinosaur().canClimb()) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }todo: make this work with 1.18




//    @Override
//    public boolean isOpaqueCube(IBlockState state) {
//        return false;
//    }
//
//    @Override
//    public boolean isFullCube(IBlockState state) {
//        return false;
//    }



    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING_BIAS, pContext.getPlayer().getDirection().getClockWise(Direction.Axis.Y));

    }




//    public BlockState getActualState(BlockState state, Level world, BlockPos pos) {//again, no clue what this is here for, have fun future devs - gamma02
//        boolean north = this.canConnect(world.getBlockState(pos.relative(Direction.NORTH)));
//        boolean south = this.canConnect(world.getBlockState(pos.relative(Direction.SOUTH)));
//        boolean west = this.canConnect(world.getBlockState(pos.relative(Direction.EAST)));
//        boolean east = this.canConnect(world.getBlockState(pos.relative(Direction.WEST)));
//        int connections = 0;
//        if (north) {
//            connections++;
//        }
//        if (south) {
//            connections++;
//        }
//        if (west) {
//            connections++;
//        }
//        if (east) {
//            connections++;
//        }
//        boolean pole = world.getBlockState(pos.above()).getBlock() instanceof ElectricFencePoleBlock;
//        if (!pole && connections == 0) {
//            connections = 1;
//            switch (state.getValue(FACING_BIAS)) {
//                case NORTH:
//                    north = true;
//                    break;
//                case SOUTH:
//                    south = true;
//                    break;
//                case EAST:
//                    east = true;
//                    break;
//                case WEST:
//                    west = true;
//                    break;
//                default:
//                    break;
//            }
//        }
//        return state.setValue(NORTH, north).setValue(SOUTH, south).setValue(WEST, west).setValue(EAST, east).setValue(POLE, pole).setValue(CONNECTIONS, connections);
//    }

    protected boolean canConnect(BlockState state) {
        return state.getBlock() instanceof ElectricFenceBaseBlock && ((ElectricFenceBaseBlock) state.getBlock()).getType().equals(type);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FACING_BIAS, NORTH, SOUTH, EAST, WEST, POLE, CONNECTIONS);
    }


//    @Override
//    public int getLightOpacity(IBlockState state, IBlockAccess world, BlockPos pos) {
//        return state.getActualState(world, pos).getValue(POLE) ? 3 : super.getLightOpacity(state, world, pos);
//    }idk what this did



    @Override
    public void onNeighborChange(BlockState state, LevelReader world, BlockPos pos, BlockPos fromPos) {
        if (!world.isClientSide()) {
            BlockState poleState = world.getBlockState(pos.above());
            if (poleState.getBlock() instanceof ElectricFencePoleBlock) {
                ((ElectricFencePoleBlock) poleState.getBlock()).updateConnectedWires(world, pos.above());
            }
        }
    }

    public FenceType getType()
    {
        return type;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ElectricFenceBaseBlockEntity(pPos, pState);
    }
}
