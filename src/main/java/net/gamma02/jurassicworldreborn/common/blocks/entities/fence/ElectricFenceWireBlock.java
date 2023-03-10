package net.gamma02.jurassicworldreborn.common.blocks.entities.fence;

import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.sensing.Sensing;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public class ElectricFenceWireBlock extends BaseEntityBlock {
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final DirectionProperty UP_DIRECTION = DirectionProperty.create("up");

    private FenceType type;

    public ElectricFenceWireBlock(FenceType type, BlockBehaviour.Properties properties) {
        super(properties);
        this.type = type;
    }

//    @Override
//    public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) {
//        return entity instanceof EntityPlayer || (entity instanceof DinosaurEntity && ((DinosaurEntity) entity).getDinosaur().canClimb());
//    }//todo: tags!
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        boolean north = state.getValue(ElectricFenceWireBlock.NORTH);
        boolean south = state.getValue(ElectricFenceWireBlock.SOUTH);
        boolean west = state.getValue(ElectricFenceWireBlock.WEST);
        boolean east = state.getValue(ElectricFenceWireBlock.EAST);
        if (!north && !south && !west && !east) {
            north = true;
            south = true;
            west = true;
            east = true;
        }
        double minX = 6.4, minZ = 6.4;//*16 added to convert from 1.12 shapes to 1.18 shapes
        double maxX = 9.6, maxZ = 9.6;
        if (north) {
            minZ = 0.0;
        }
        if (south) {
            maxZ = 16.0;
        }
        if (west) {
            minX = 0.0;
        }
        if (east) {
            maxX = 16.0;
        }
        return Block.box(minX, 0.0, minZ, maxX, 16.0, maxZ);
    }



    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (int i = 4; i < stackTrace.length; i++) {
            StackTraceElement element = stackTrace[i];
            if (element.getClassName().equals(Sensing.class.getName())) {
                return Shapes.empty();
            }
        }

        return super.getCollisionShape(state, pLevel, pPos, pContext);
    }

//    @Override
//    public BlockRenderLayer getBlockLayer() {
//        return BlockRenderLayer.CUTOUT;
//    }
//
//    @Override
//    public EnumBlockRenderType getRenderType(IBlockState state) {
//        return EnumBlockRenderType.MODEL;
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


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(NORTH, SOUTH, WEST, EAST, UP_DIRECTION);
    }



//    public BlockState getActualState(BlockState state, BlockGetter world, BlockPos pos) {
//        boolean north = false;
//        boolean south = false;
//        boolean east = false;
//        boolean west = false;
//        Direction up = Direction.DOWN;
//        for (Iterator<Direction> it = Direction.Plane.HORIZONTAL.stream().iterator(); it.hasNext(); ) {
//            Direction facing = it.next();
//            BlockPos offset = pos.relative(facing);
//            if (this.canConnect(world, pos, offset, world.getBlockState(offset))) {
//                switch (facing) {
//                    case NORTH:
//                        north = true;
//                        break;
//                    case SOUTH:
//                        south = true;
//                        break;
//                    case WEST:
//                        west = true;
//                        break;
//                    case EAST:
//                        east = true;
//                        break;
//                    default:
//                        break;
//                }
//            }
//        }
//        BlockPos.MutableBlockPos basePos = pos.mutable();
//        this.findBase(world, basePos);
//        if (world.getBlockState(basePos).getBlock() instanceof ElectricFenceBaseBlock) {
//            for (Iterator<Direction> it = Direction.Plane.HORIZONTAL.stream().iterator(); it.hasNext(); ) {
//                Direction facing = it.next();
//                BlockPos offset = basePos.relative(facing);
//                BlockPos topOffset = offset.above();
//                topOffset = topOffset.above(this.getFenceHeight(world.getBlockState(topOffset), world, topOffset.mutable()) + 1);
//                if (this.canConnect(world.getBlockState(topOffset))) {
//                    BlockPos.MutableBlockPos connectBasePos = topOffset.mutable();
//                    this.findBase(world, connectBasePos);
//                    if (connectBasePos.getY() < basePos.getY()) {
//                        continue;
//                    }
//                    if (world.getBlockState(connectBasePos).getBlock() instanceof ElectricFenceBaseBlock && !(world.getBlockState(offset).getBlock() instanceof ElectricFenceBaseBlock)) {
//                        up = facing;
//                        break;
//                    }
//                }
//            }
//        }
//        if (!west && !north && !south && !east) {
//            west = north = south = east = true;
//        }
//        return state.setValue(NORTH, north).setValue(SOUTH, south).setValue(WEST, west).setValue(EAST, east).setValue(UP_DIRECTION, up);
//    }todo: the fuck does this do and how do I fix it :) - gamma02

    private void findBase(BlockGetter world, BlockPos.MutableBlockPos basePos) {
        BlockState downState;
        int moved = 0;
        while (basePos.getY() > 1 && !((downState = world.getBlockState(basePos)).getBlock() instanceof ElectricFenceBaseBlock) && moved < 5) {
            basePos.setY(basePos.getY() - 1);
            if (downState.getBlock() instanceof ElectricFenceWireBlock) {
                moved = 0;
            } else {
                moved++;
            }
        }
    }



    private int getFenceHeight(BlockState state, BlockGetter world, BlockPos pos1) {
        int height = 0;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos().set(pos1);
        BlockPos.MutableBlockPos start = new BlockPos(pos).mutable();
        if (world.getBlockState(pos).getBlock() instanceof ElectricFenceWireBlock) {
            while (pos.getY() < 255 && state.getBlock() instanceof ElectricFenceWireBlock) {
                pos.setY(pos.getY() + 1);
                state = world.getBlockState(pos);
                height++;
            }
            pos.set(start);
            while (pos.getY() > 1 && state.getBlock() instanceof ElectricFenceWireBlock) {
                pos.setY(pos.getY() - 1);
                state = world.getBlockState(pos);
                height++;
            }
        }
        return height;
    }

    @Override
    public boolean collisionExtendsVertically(BlockState state, BlockGetter level, BlockPos pos, Entity collidingEntity) {
        return true;
    }

    protected boolean canConnect(BlockState state) {
        return ((state.getBlock() instanceof ElectricFenceWireBlock && ((ElectricFenceWireBlock) state.getBlock()).getType().equals(type))) || state.getBlock() instanceof ElectricFencePoleBlock;
    }

    protected boolean canConnect(BlockGetter world, BlockPos current, BlockPos pos, BlockState state) {
        if (this.canConnect(state)) {
            return true;
        } else {
            BlockState down = world.getBlockState(pos.below());
            if (down.getBlock() instanceof ElectricFenceWireBlock && Math.abs(current.getY() - pos.below().getY()) == 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onNeighborChange(BlockState state, LevelReader world, BlockPos pos, BlockPos neighbor) {
        super.onNeighborChange(state, world, pos, neighbor);
        if (!world.isClientSide()) {
            BlockEntity tile = world.getBlockEntity(pos);
            if (tile instanceof ElectricFenceWireBlockEntity) {
                ((ElectricFenceWireBlockEntity) tile).checkDisconnect();
            }
        }
    }



    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        super.entityInside(state, world, pos, entity);
        if (!world.isClientSide && entity.isAlive() && entity instanceof LivingEntity) {
            BlockEntity tile = world.getBlockEntity(pos);
            ElectricFenceWireBlock block = (ElectricFenceWireBlock) state.getBlock();
            if (tile instanceof ElectricFenceWireBlockEntity && ((ElectricFenceWireBlockEntity) tile).isPowered()) {
                if(block.getType().equals(FenceType.LOW))
                    entity.hurt(DamageSource.LIGHTNING_BOLT, 1.0F);
                if(block.getType().equals(FenceType.MED))
                    entity.hurt(DamageSource.LIGHTNING_BOLT, 2.0F);
                if(block.getType().equals(FenceType.HIGH))
                    entity.hurt(DamageSource.LIGHTNING_BOLT, 3.0F);
                if (entity instanceof DinosaurEntity dinosaur) {
                    if (dinosaur.wireTicks < 2) {
                        dinosaur.wireTicks++;
                        dinosaur.disableHerdingTicks = 200;
                    }
                }
                if (entity.tickCount % 10 == 0) {
                    world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundHandler.FENCE_SHOCK, SoundSource.BLOCKS, 0.25F, 1.0F);
                }
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
        return new ElectricFenceWireBlockEntity(pPos, pState);
    }
}
