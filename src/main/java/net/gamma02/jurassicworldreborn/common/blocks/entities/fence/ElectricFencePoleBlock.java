package net.gamma02.jurassicworldreborn.common.blocks.entities.fence;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;

public class ElectricFencePoleBlock extends BaseEntityBlock {
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

//    private static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0.3425, 0.0, 0.3425, 0.6575, 1.0, 0.6575); goes to (5.48, 0.0, 5.48, 10.52, 16, 10.52)
    private static final VoxelShape BOUNDS = Block.box(5.48, 0.0, 5.48, 10.52, 16, 10.52);
    private FenceType type;

    public ElectricFencePoleBlock(FenceType type, BlockBehaviour.Properties properties) {
        super(properties);
        this.type = type;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return BOUNDS;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return getShape(pState, pLevel, pPos, pContext);
    }

//    @Override idk
//    public BlockRenderLayer getBlockLayer() {
//        return BlockRenderLayer.CUTOUT;
//    }


    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

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
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ElectricFencePoleBlockEntity(pPos, pState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(ACTIVE, NORTH, SOUTH, WEST, EAST);
    }

//    public BlockState getActualState(BlockState state, Level access, BlockPos pos) {//this is a removed function that was removed for reasons beyond me. wtf. anyway. - gamma02
//        BlockPos northPos = pos.relative(Direction.NORTH);
//        BlockPos southPos = pos.relative(Direction.SOUTH);
//        BlockPos eastPos = pos.relative(Direction.EAST);
//        BlockPos westPos = pos.relative(Direction.WEST);
//        BlockState northBlock = access.getBlockState(northPos);
//        BlockState southBlock = access.getBlockState(southPos);
//        BlockState westBlock = access.getBlockState(westPos);
//        BlockState eastBlock = access.getBlockState(eastPos);
//        boolean north = this.canConnect(access, northPos, Direction.NORTH, northBlock);
//        boolean south = this.canConnect(access, southPos, Direction.SOUTH, southBlock);
//        boolean west = this.canConnect(access, westPos, Direction.WEST, westBlock);
//        boolean east = this.canConnect(access, eastPos, Direction.EAST, eastBlock);
//        boolean powered = false;
//        BlockPos downPos = pos.below();
//        BlockState down = access.getBlockState(downPos);
//        if ((this.isBlockPowered(access, downPos) && down.getBlock() instanceof ElectricFenceBaseBlock) || (down.getBlock() instanceof ElectricFencePoleBlock && down.getActualState(access, downPos).getValue(ACTIVE))) {
//            powered = true;
//        }
//        return state.setValue(NORTH, north).setValue(SOUTH, south).setValue(WEST, west).setValue(EAST, east).setValue(ACTIVE, powered);
//    }


    public int getSignal(BlockState state, BlockGetter access, BlockPos pos, Direction facing) {
//        BlockState state = access.getBlockState(pos);
        if (state.getBlock().shouldCheckWeakPower(state, (LevelReader)access, pos, facing)) {
            return this.getStrongPower((LevelReader)access, pos);
        } else {
            return state.getSignal(access, pos, facing);
        }
    }



    private int getStrongPower(LevelReader access, BlockPos pos) {
        int highest = 0;
        for (Direction facing : Direction.values()) {
            highest = Math.max(highest, access.getDirectSignal(pos.relative(facing), facing));
            if (highest >= 15) {
                return highest;
            }
        }
        return highest;
    }

    private boolean isBlockPowered(Level access, BlockPos pos) {
        for (Direction facing : Direction.values()) {
            if (this.getSignal(access.getBlockState(pos.relative(facing)), access, pos.relative(facing), facing) > 0) {
                return true;
            }
        }
        return false;
    }

    protected boolean canConnect(Level world, BlockPos pos, Direction direction, BlockState state) {
        if ((state.getBlock() instanceof ElectricFenceWireBlock && ((ElectricFenceWireBlock) state.getBlock()).getType().equals(type)) || (state.getBlock() instanceof ElectricFencePoleBlock) && ((ElectricFencePoleBlock) state.getBlock()).getType().equals(type)) {
            return true;
        } else {
            BlockState down = world.getBlockState(pos.below());
            if (down.getBlock() instanceof ElectricFenceWireBlock && down.getValue(ElectricFenceWireBlock.UP_DIRECTION).getOpposite() == direction) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
        if (!level.isClientSide()) {
            this.updateConnectedWires((Level)level, pos);
        }
        super.onNeighborChange(state, level, pos, neighbor);
    }



    public void updateConnectedWires(LevelReader world, BlockPos pos) {
        boolean powered = hasNeighborSignal(world, pos.below());
        HashSet<BlockPos> wires = new HashSet<>();
        HashSet<BlockPos> bases = new HashSet<>();
        this.processConnectedWires(bases, wires, world, pos.below(), pos.below());
        for (BlockPos wirePos : wires) {
            BlockEntity tile = world.getBlockEntity(wirePos);
            if (tile instanceof ElectricFenceWireBlockEntity) {
                ElectricFenceWireBlockEntity wire = (ElectricFenceWireBlockEntity) tile;
                wire.power(pos, powered);
            }
        }
    }

    public static boolean hasNeighborSignal(LevelReader world, BlockPos pPos) {
        if (world.getDirectSignal(pPos.below(), Direction.DOWN) > 0) {
            return true;
        } else if (world.getDirectSignal(pPos.above(), Direction.UP) > 0) {
            return true;
        } else if (world.getDirectSignal(pPos.north(), Direction.NORTH) > 0) {
            return true;
        } else if (world.getDirectSignal(pPos.south(), Direction.SOUTH) > 0) {
            return true;
        } else if (world.getDirectSignal(pPos.west(), Direction.WEST) > 0) {
            return true;
        } else {
            return world.getDirectSignal(pPos.east(), Direction.EAST) > 0;
        }
    }

    public FenceType getType()
    {
        return type;
    }

    protected void processConnectedWires(HashSet<BlockPos> bases, HashSet<BlockPos> wires, LevelReader world, BlockPos origin, BlockPos current) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (!((x == z || x == -z) && y != 0)) {
                        BlockPos offset = current.offset(x, y, z);
                        if (!bases.contains(offset)) {
                            bases.add(offset);
                            BlockState state = world.getBlockState(offset);
                            Block block = state.getBlock();
                            if (block instanceof ElectricFenceBaseBlock) {
                                int wireX = offset.getX();
                                int wireZ = offset.getZ();
                                int deltaX = wireX - origin.getX();
                                int deltaZ = wireZ - origin.getZ();
                                ChunkAccess chunk = world.getChunk(offset);
                                double delta = (deltaX * deltaX) + (deltaZ * deltaZ);
                                if (delta <= 64) {
                                    int currentY = offset.getY();
                                    while (chunk.getBlockState(new BlockPos(wireX, ++currentY, wireZ)).getBlock() instanceof ElectricFenceWireBlock) {
                                        BlockPos wirePos = new BlockPos(wireX, currentY, wireZ);
                                        if (!wires.contains(wirePos)) {
                                            wires.add(wirePos);
                                        }
                                    }
                                    this.processConnectedWires(bases, wires, world, origin, offset);
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}
