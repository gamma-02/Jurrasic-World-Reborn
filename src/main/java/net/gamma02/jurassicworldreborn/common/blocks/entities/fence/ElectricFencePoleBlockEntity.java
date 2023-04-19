package net.gamma02.jurassicworldreborn.common.blocks.entities.fence;

import net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ElectricFencePoleBlockEntity extends BlockEntity {
    public ElectricFencePoleBlockEntity(BlockPos pPos, BlockState pState) {
        super(ModBlockEntities.POLE_FENCE_BLOCK_ENTITY.get(), pPos, pState);
    }
}
