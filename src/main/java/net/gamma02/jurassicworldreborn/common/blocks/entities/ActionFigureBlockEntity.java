package net.gamma02.jurassicworldreborn.common.blocks.entities;

import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ActionFigureBlockEntity extends BlockEntity {
    private DinosaurEntity entity;
    private int rotation;
    private boolean isFossile;
    private boolean isMale;
    private byte variant;
    private boolean isSkeleton;

    private SerializedData serializedData = new InvalidData();

    public ActionFigureBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.DISPLAY_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
    }


    public void setDinosaur(String dinosaurId, boolean isMale, boolean isSkeleton) {
        this.isMale = isMale;
        this.isSkeleton = isSkeleton;
        this.variant = variant;
        this.isFossile = isFossile;
        try {
            Dinosaur dinosaur = Dinosaur.getDinosaurByName(dinosaurId);
            this.entity = dinosaur.getDinosaurClass().getDeclaredConstructor(Level.class).newInstance(this.level);
            this.initializeEntity(this.entity);
        } catch (Exception e) {
        }
        this.setChanged();
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);

        if (nbt.contains("DinosaurId")) {
            this.serializedData = new LegacyId();
        } else if (nbt.contains("DinosaurTag")) {
            this.serializedData = new TagData();
        }

        this.entity = null;

        this.serializedData.deserialize(nbt);

        this.rotation = nbt.getInt("Rotation");
        this.isMale = !nbt.contains("IsMale") || nbt.getBoolean("IsMale");
        this.isSkeleton = nbt.getBoolean("IsSkeleton");
        this.variant = nbt.getByte("Variant");
        this.isFossile = nbt.getBoolean("IsFossile");
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = super.serializeNBT();

        if (this.entity != null) {
            CompoundTag tag = this.entity.serializeNBT();
            nbt.put("DinosaurTag", tag);
        } else if (this.serializedData != null) {
            this.serializedData.serialize(nbt);
        }

        nbt.putInt("Rotation", this.rotation);
        nbt.putBoolean("IsMale", this.isMale);
        nbt.putBoolean("IsSkeleton", this.isSkeleton);
        nbt.putByte("Variant", this.variant);
        nbt.putBoolean("IsFossile", this.isFossile);

        return nbt;
    }



    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }



    @Override
    public CompoundTag getUpdateTag() {
        return this.serializeNBT();
    }



    @Override
    @OnlyIn(Dist.CLIENT)
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
        super.onDataPacket(net, pkt);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public AABB getRenderBoundingBox() {
        if (this.isSkeleton && this.entity != null) {
            return this.entity.getBoundingBoxForCulling().inflate(3.0).move(this.worldPosition);
        }
        return super.getRenderBoundingBox();
    }


    public boolean isMale() {
        return this.isMale;
    }

    public boolean isSkeleton() {
        return this.isSkeleton;
    }

    public boolean isFossile() {
        return this.isFossile;
    }

    public byte getVariant() {
        return this.variant;
    }

    public int getRot() {
        return this.rotation;
    }

    public void setRot(int rotation) {
        this.setChanged();
        this.rotation = rotation;
    }

    public DinosaurEntity getEntity() {
        if (this.entity == null && this.serializedData != null) {
            return this.createEntity();
        }
        return this.entity;
    }

    private DinosaurEntity createEntity() {
        DinosaurEntity entity = this.serializedData.create(this.level);
        if (entity != null) {
            this.serializedData = null;
            this.initializeEntity(entity);
            this.entity = entity;
            return entity;
        } else {
            return new InvalidData().create(this.level);
        }
    }

    private void initializeEntity(DinosaurEntity entity) {
        entity.setupDisplay(this.isMale);
        entity.setSkeleton(this.isSkeleton);
        entity.setSkeletonVariant(this.variant);
        entity.setIsFossile(this.isFossile);
        entity.setAnimation(EntityAnimation.IDLE.get());
    }

    private interface SerializedData {
        void serialize(CompoundTag compound);

        void deserialize(CompoundTag compound);

        DinosaurEntity create(Level world);
    }

    private class LegacyId implements SerializedData {
        protected String dinosaurId;

        @Override
        public void serialize(CompoundTag compound) {
            compound.putString("DinosaurId", this.dinosaurId);
        }

        @Override
        public void deserialize(CompoundTag compound) {
            this.dinosaurId = compound.getString("DinosaurId");
        }

        @Override
        public DinosaurEntity create(Level world) {
            try {
                Dinosaur dinosaur = Dinosaur.getDinosaurByName(this.dinosaurId);
                return dinosaur.getDinosaurClass().getDeclaredConstructor(Level.class).newInstance(world);
            } catch (Exception e) {
                return null;
            }
        }
    }

    private class TagData implements SerializedData {
        protected CompoundTag data;

        @Override
        public void serialize(CompoundTag compound) {
            compound.put("DinosaurTag", this.data);
        }

        @Override
        public void deserialize(CompoundTag compound) {
            this.data = compound.getCompound("DinosaurTag");
        }

        @Override
        public DinosaurEntity create(Level world) {
            Entity entity = EntityType.by(this.data).orElse(EntityType.AXOLOTL/*literally does not matter in this case*/).create(world);
            if (entity instanceof DinosaurEntity) {
                return (DinosaurEntity) entity;
            }
            return null;
        }
    }

    private class InvalidData extends LegacyId {
        @Override
        public void deserialize(CompoundTag compound) {
            this.dinosaurId = Dinosaur.getDinosaurByName("None").getName();
        }
    }
}
