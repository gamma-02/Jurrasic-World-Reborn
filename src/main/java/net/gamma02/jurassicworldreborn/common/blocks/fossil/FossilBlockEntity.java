package net.gamma02.jurassicworldreborn.common.blocks.fossil;

import net.gamma02.jurassicworldreborn.common.blocks.machines.modBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class FossilBlockEntity extends BlockEntity {

    public int yValue = 0;

    public FossilBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
        this.yValue = pWorldPosition.getY();
    }

    public FossilBlockEntity(BlockPos pos, BlockState state){
        super(modBlockEntities.FOSSIL_BLOCK_ENTITY.get(), pos, state);
        this.yValue = pos.getY();
    }


    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.putInt("TimePeriod", yValue);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        this.yValue = pTag.getInt("TimePeriod");
        super.load(pTag);
    }
}
