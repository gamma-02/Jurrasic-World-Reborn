package net.gamma02.jurassicworldreborn.common.blocks.entities.feeder;

import net.gamma02.jurassicworldreborn.common.blocks.entities.cleaner.CleanerBlockEntity;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class FeederBlock extends BaseEntityBlock {


    protected FeederBlock(Properties p_49224_) {
        super(p_49224_);
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
}
