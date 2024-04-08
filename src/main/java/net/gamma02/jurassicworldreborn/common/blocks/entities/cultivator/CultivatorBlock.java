package net.gamma02.jurassicworldreborn.common.blocks.entities.cultivator;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.gamma02.jurassicworldreborn.common.blocks.base.BaseMachineBlock;
import net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNACombinatorHybridizer.DNACombinatorHybridizerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import software.bernie.shadowed.fasterxml.jackson.databind.ser.Serializers;

import static net.minecraft.world.level.block.DoublePlantBlock.copyWaterloggedFrom;

public class CultivatorBlock extends BaseMachineBlock {
    public static final EnumProperty<DyeColor> COLOR = EnumProperty.create("color", DyeColor.class);
    public final boolean half;//true = top, false = bottom



    public CultivatorBlock(Properties p_52591_, boolean blockHalf) {
        super(p_52591_);
        half = blockHalf;

        this.registerDefaultState(this.getSetDefaultValues().setValue(COLOR, DyeColor.WHITE));

    }

    public static void placeBottomAt(LevelAccessor pLevel, BlockState pState, BlockPos pPos, int pFlags) {
        BlockPos blockpos = pPos.above();
        pLevel.setBlock(pPos, copyWaterloggedFrom(pLevel, pPos, ModBlocks.CULTIVATOR_BOTTOM.get().defaultBlockState()), pFlags);
        pLevel.setBlock(blockpos, copyWaterloggedFrom(pLevel, blockpos, ModBlocks.CULTIVATOR_BOTTOM.get().defaultBlockState()), pFlags);
    }



    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if(pLevel.isClientSide)
            return;

        if(half){
            pPos = pPos.below();
        }

        if(!(pLevel.getBlockEntity(pPos) instanceof CultivatorBlockEntity entity))
            return;


        Containers.dropContents(pLevel, pPos, entity);

        if(!half)
            pLevel.destroyBlock(pPos.above(), false);
    }

    @Override
    public MaterialColor getMapColor(BlockState state, BlockGetter level, BlockPos pos, MaterialColor defaultColor) {
        return state.getValue(COLOR).getMaterialColor();
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
//            MenuProvider menuprovider = this.getMenuProvider(pState, pLevel, pPos);
            if (pLevel.getBlockEntity(pPos) instanceof CultivatorBlockEntity e ) {
                pPlayer.openMenu(e);
            }else if(pLevel.getBlockEntity(pPos) instanceof CultivatorTopBlockEntity e ){
                pPlayer.openMenu(e.bottomEntity);
            }

            return InteractionResult.CONSUME;
        }
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos.above()).isAir();
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if(pLevel.isClientSide)
            return;
        //todo: both halves place
//        BlockPos otherHalf = pPos.above();
//        boolean ourHalf = ((CultivatorBlock) pState.getBlock()).half;
//
//        if(ourHalf){
//            otherHalf = pPos.below();
//        }
//
//        if(pLevel.getBlockState(otherHalf).isAir()){
//            pLevel.setBlock(pPos, (ourHalf ? ModBlocks.CULTIVATOR_BOTTOM.get().defaultBlockState() : ModBlocks.CULTIVATOR_TOP.get().defaultBlockState()), 3);
//        }
        if(half)
            return;
        pLevel.setBlock(pPos.above(), ModBlocks.CULTIVATOR_TOP.get().defaultBlockState().setValue(COLOR, pState.getValue(COLOR)), 3);
    }

    public boolean areBothHalvesValid(BlockState state, BlockPos pos, Level pLevel){
        if(!(state.getBlock() instanceof CultivatorBlock cultivator)){
            return false;
        }

        if(cultivator.half){
            pos = pos.below();
        }else{
            pos = pos.above();
        }

        if(!(pLevel.getBlockState(pos).getBlock() instanceof CultivatorBlock otherHalf))
            return false;

        if(cultivator.half == otherHalf.half){
            return false;
        }

        return true;


    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(COLOR);
    }
}
