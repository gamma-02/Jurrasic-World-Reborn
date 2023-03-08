package net.gamma02.jurassicworldreborn.common.blocks.entities.fence;

import com.mojang.math.Constants;
import net.gamma02.jurassicworldreborn.common.blocks.entities.modBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.network.chat.NbtComponent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ElectricFenceWireBlockEntity extends BlockEntity implements BlockEntityTicker {
    private Set<BlockPos> poweringPoles = new HashSet<>();
    private byte ticks;

    public ElectricFenceWireBlockEntity( BlockPos pWorldPosition, BlockState pBlockState) {
        super(modBlockEntities.WIRE_FENCE_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
    }

    public void tick() {
        if (++this.ticks >= 20) {
            this.removeInvalidPoles();
            this.ticks = 0;
        }
    }

    private void removeInvalidPoles() {
        List<BlockPos> invalid = this.getInvalidPoles();
        for (BlockPos remove : invalid) {
            this.power(remove, false);
        }
    }


    private List<BlockPos> getInvalidPoles() {
        List<BlockPos> invalid = new ArrayList<>(this.poweringPoles.size());
        for (BlockPos pole : this.poweringPoles) {
            BlockState state = this.level.getBlockState(pole);
            boolean isInvalid = true;
            if (state.getBlock() instanceof ElectricFencePoleBlock && this.level.getBestNeighborSignal(pole.below()) != 0) {
                isInvalid = false;
            }
            if (isInvalid) {
                invalid.add(pole);
            }
        }
        return invalid;
    }

    public void checkDisconnect() {
        this.removeInvalidPoles();
        for (BlockPos pole : this.poweringPoles) {
            BlockState state = this.level.getBlockState(pole);
            Block block = state.getBlock();
            if (block instanceof ElectricFencePoleBlock) {
                ((ElectricFencePoleBlock) block).updateConnectedWires(this.level, pole);
            }
        }
    }

    public void power(BlockPos pole, boolean powered) {
        if (powered) {
            if (!this.poweringPoles.contains(pole)) {
                this.poweringPoles.add(pole);
            }
        } else {
            this.poweringPoles.remove(pole);
        }
    }

    public boolean isPowered() {
        return this.poweringPoles.size() > 0;
    }



    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compound = super.serializeNBT();
        ListTag poweringList = new ListTag();
        for (BlockPos pole : this.poweringPoles) {
            poweringList.add(LongTag.valueOf(pole.asLong()));
        }
        compound.put("Powering", poweringList);
        return compound;
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.poweringPoles.clear();
        ListTag poweringList = compound.getList("Powering", CompoundTag.TAG_LONG);
        for (int i = 0; i < poweringList.size(); i++) {
            this.poweringPoles.add(BlockPos.of(((LongTag) poweringList.get(i)).getAsLong()));
        }
    }

    @Override
    public void tick(Level pLevel, BlockPos pPos, BlockState pState, BlockEntity pBlockEntity) {
        ((ElectricFenceWireBlockEntity)pBlockEntity).tick(pLevel, pPos, pState, pBlockEntity);
    }
}
