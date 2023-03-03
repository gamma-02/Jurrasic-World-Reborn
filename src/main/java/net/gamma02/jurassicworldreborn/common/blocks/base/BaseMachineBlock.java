package net.gamma02.jurassicworldreborn.common.blocks.base;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BaseMachineBlock extends HorizontalDirectionalBlock implements EntityBlock {

    //todo: mechanics of this lol
    public BaseMachineBlock(Properties p_52591_) {
        super(p_52591_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return null;
    }
}
