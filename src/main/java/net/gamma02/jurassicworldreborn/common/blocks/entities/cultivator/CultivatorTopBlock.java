package net.gamma02.jurassicworldreborn.common.blocks.entities.cultivator;

import net.gamma02.jurassicworldreborn.common.blocks.base.BaseMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CultivatorTopBlock extends CultivatorBlock {
    public CultivatorTopBlock(Properties p_52591_) {
        super(p_52591_, true);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CultivatorTopBlockEntity(pPos, pState);
    }
}
