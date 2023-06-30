package net.gamma02.jurassicworldreborn.common.blocks;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;

public class ClearGlassBlock extends GlassBlock {

    public static BooleanProperty DOWN = BlockStateProperties.DOWN;
    public static BooleanProperty UP = BlockStateProperties.UP;
    public static BooleanProperty EAST = BlockStateProperties.EAST;
    public static BooleanProperty WEST = BlockStateProperties.WEST;
    public static BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static BooleanProperty NORTH = BlockStateProperties.NORTH;


    public ClearGlassBlock(Properties p_53640_) {
        super(p_53640_.noOcclusion().isValidSpawn(Jurassicworldreborn::never).isRedstoneConductor(Jurassicworldreborn::never).isSuffocating(Jurassicworldreborn::never).isViewBlocking(Jurassicworldreborn::never));
        this.registerDefaultState(this.getStateDefinition().any().setValue(DOWN, false).setValue(UP, false).setValue(EAST, false).setValue(WEST, false).setValue(SOUTH, false).setValue(NORTH, false));
        Jurassicworldreborn.setRenderType(this, RenderType.translucent());
    }

    @Override
    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return true;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {

        Level world = pContext.getLevel();

        BlockPos pos = pContext.getClickedPos();

        return this.defaultBlockState().setValue(NORTH, this.canConnectTo(world, pos.north())).setValue(EAST, this.canConnectTo(world, pos.east())).setValue(SOUTH, this.canConnectTo(world, pos.south())).setValue(WEST, this.canConnectTo(world, pos.west())).setValue(UP, this.canConnectTo(world, pos.above())).setValue(DOWN, this.canConnectTo(world, pos.below()));
    }

//    @Override
//    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
//        pLevel.blockEvent(pPos, this, 0, 0);
//        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
//    }

    public boolean canConnectTo(Level world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() instanceof ClearGlassBlock;
    }

//    public boolean canConnectTo(LevelReader world, BlockPos pos) {
//        return world.getBlockState(pos).getBlock() instanceof ClearGlassBlock;
//    }
    public void updateConnections(Level world, BlockPos pos){
//        world.getBlockState(pos).setValue(NORTH, this.canConnectTo(world, pos.north())).setValue(EAST, this.canConnectTo(world, pos.east())).setValue(SOUTH, this.canConnectTo(world, pos.south())).setValue(WEST, this.canConnectTo(world, pos.west())).setValue(UP, this.canConnectTo(world, pos.above())).setValue(DOWN, this.canConnectTo(world, pos.below()));


        BlockState state = this.defaultBlockState();
        ArrayList<BooleanProperty> trueProps = new ArrayList<>();
        for(Direction d : Direction.values()){
            BooleanProperty dir = getStateForDirection(d);

            if(this.canConnectTo(world, pos.relative(d))){
//                trueProps.add(dir);
                state = state.setValue(dir, true);
//                System.out.println(dir.getName() + " true");
//                world.blockEvent(pos.offset(d.getNormal()), this, 0, ++iterID);
            }

        }
//        trueProps.forEach((p) -> state.setValue(p, true));
//        printState(state);
        world.setBlock(pos, state, 3);
//        return state;
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor world, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        System.out.println(pDirection.getName() + ", " + pCurrentPos.toShortString() + ", " + pNeighborPos.toShortString());

        updateConnections((Level)world, pCurrentPos);


        return super.updateShape(pState, pDirection, pNeighborState, world, pCurrentPos, pNeighborPos);
    }

//    @Override
//    public void neighborChanged(BlockState pState, Level world, BlockPos pPos, Block pBlock, BlockPos neighbor, boolean pIsMoving) {
////        if(world.getBlockState(neighbor).is(ModBlocks.CLEAR_GLASS.get()))
////            world.blockEvent(pPos, this, 0, 0);
//        System.out.println(pPos.toShortString() + ", " + neighbor.toShortString());
//        super.neighborChanged(pState, world, pPos, pBlock, neighbor, pIsMoving);
//    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(UP, DOWN, NORTH, SOUTH, EAST, WEST);
    }

    public static BooleanProperty getStateForDirection(Direction direction){
        return switch (direction) {
            case UP -> ClearGlassBlock.UP;
            case DOWN -> ClearGlassBlock.DOWN;
            case SOUTH -> ClearGlassBlock.SOUTH;
            case NORTH -> ClearGlassBlock.NORTH;
            case EAST -> ClearGlassBlock.EAST;
            case WEST -> ClearGlassBlock.WEST;
        };
    }

//    @Override
//    public boolean triggerEvent(BlockState pState, Level world, BlockPos pPos, int pId, int pParam) {
//
//        if(pId == 0){
//            BlockState updatedState = updateConnections(world, pPos);
//            world.setBlock(pPos, updatedState, 1);
//        }
//
//
//        return super.triggerEvent(pState, world, pPos, pId, pParam);
//    }

    public static void printState(BlockState state){
        System.out.print(" Block: " + Objects.requireNonNull(state.getBlock().getRegistryName()) + ", Properties: ");
        for(Property<?> p : state.getProperties()){
            System.out.print(p.getName() + ": " + state.getValue(p) + " ");
        }
        System.out.println();
    }
}
