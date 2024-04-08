package net.gamma02.jurassicworldreborn.common.blocks.entities.cultivator;

import net.gamma02.jurassicworldreborn.common.blocks.base.BaseMachineBlock;
import net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNACombinatorHybridizer.DNACombinatorHybridizerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.Nullable;

public class CultivatorBottomBlock extends CultivatorBlock {

    public CultivatorBottomBlock(Properties p_52591_) {
        super(p_52591_, false);
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CultivatorBlockEntity(pPos, pState);
    }

    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, net.minecraft.world.level.block.state.BlockState state, BlockEntityType<T> type) {
        return (world1, pos, state1, instance) -> {
            if (instance instanceof CultivatorBlockEntity dna) {
                ( dna).tick(world1, pos, state1, dna);
            } else {
                super.getTicker(world, state, type).tick(world1, pos, state1, instance);
            }
        };
    }
}
