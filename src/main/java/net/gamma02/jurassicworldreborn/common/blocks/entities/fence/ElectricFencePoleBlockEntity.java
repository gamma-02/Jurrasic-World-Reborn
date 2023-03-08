package net.gamma02.jurassicworldreborn.common.blocks.entities.fence;

import net.gamma02.jurassicworldreborn.common.blocks.entities.modBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ElectricFencePoleBlockEntity extends BlockEntity {
    public ElectricFencePoleBlockEntity(BlockPos pPos, BlockState pState) {
        super(modBlockEntities.POLE_FENCE_BLOCK_ENTITY.get(), pPos, pState);
    }
}
