package net.gamma02.jurassicworldreborn.common.blocks.parkBlocks;

import net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

public class TourRailBlockEntity extends BlockEntity {
    private TourRailBlock.EnumRailDirection direction;

    public TourRailBlockEntity(BlockEntityType<?> pType, BlockPos pWorldPosition, BlockState pBlockState) {
        super(pType, pWorldPosition, pBlockState);
    }
    public TourRailBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TOUR_RAIL_BLOCK_ENTITY.get(), pos, state);//TODO!!!!!!!

    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
//        int metadata = getBlockMetadata();
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(Objects.requireNonNull(pkt.getTag()));
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = this.serializeNBT();
        return nbt;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        this.load(tag);
    }


    @Override
    public void saveAdditional(CompoundTag compound) {
        checkNonNull();
        compound.putInt("RailDirection", direction.ordinal());
        super.saveAdditional(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        direction = TourRailBlock.EnumRailDirection.values()[compound.getInt("RailDirection")];
    }

    public TourRailBlock.EnumRailDirection getDirection() {
        checkNonNull();
        return direction;
    }

    private void checkNonNull() {
        if(direction == null) {
            direction = TourRailBlock.EnumRailDirection.NORTH_SOUTH;
        }
    }

    public void setDirection(TourRailBlock.EnumRailDirection direction) {
        this.direction = direction;
        checkNonNull();
    }
}
