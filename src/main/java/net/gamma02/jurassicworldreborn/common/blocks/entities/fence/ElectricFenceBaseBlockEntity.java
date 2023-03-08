package net.gamma02.jurassicworldreborn.common.blocks.entities.fence;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import net.gamma02.jurassicworldreborn.common.blocks.entities.modBlockEntities;

public class ElectricFenceBaseBlockEntity extends BlockEntity {
    public ElectricFenceBaseBlockEntity(BlockPos pPos, BlockState pState) {
        super(modBlockEntities.BASE_FENCE_BLOCK_ENTITY.get(), pPos, pState);
    }
}
