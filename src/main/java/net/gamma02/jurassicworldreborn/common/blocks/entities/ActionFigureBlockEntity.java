package net.gamma02.jurassicworldreborn.common.blocks.entities;

import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurHandler;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.ModEntities;
import net.gamma02.jurassicworldreborn.common.util.networking.BlockUpdateUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ActionFigureBlockEntity extends BlockEntity {


    private Dinosaur dinosaur = DinosaurHandler.VELOCIRAPTOR;
    private DinosaurEntity entity;
    private EntityType<? extends DinosaurEntity> entityType;
    private int rotation;
    private boolean isFossile;
    private boolean isMale;
    private byte variant;
    private boolean isSkeleton;

//    private SerializedData serializedData = new InvalidData();

    public ActionFigureBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.DISPLAY_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
    }

    public void setDinosaur(Dinosaur dino, boolean gender, boolean isSkeleton){
        if(this.level == null)//I JUST REALIZED THAT I DON'T NEED A BLOCK ENTITY ITEM RENDERER HAHAHAHHAHAHfslfjKLSDFJLKSDFJLKSDJFL
            return;

        this.dinosaur = dino;

        this.isMale = gender;
        this.isSkeleton = isSkeleton;

        loadEntityType();


//        this.entityType = DinosaurEntity.CLASS_TYPE_LIST.get(dino.getDinosaurClass()).get();

        this.entity = this.entityType.create(this.level);

        this.initializeEntity(entity);

    }

    public void setDinosaur(Dinosaur dino, boolean gender, boolean isSkeleton, CompoundTag entityTag){
        if(this.level == null)//I JUST REALIZED THAT I DON'T NEED A BLOCK ENTITY ITEM RENDERER HAHAHAHHAHAHfslfjKLSDFJLKSDFJLKSDJFL
            return;

        this.dinosaur = dino;

        this.isMale = gender;
        this.isSkeleton = isSkeleton;

        loadEntityType();


//        this.entityType = DinosaurEntity.CLASS_TYPE_LIST.get(dino.getDinosaurClass()).get();

        this.entity = this.entityType.create(this.level);

        this.initializeEntity(entity, entityTag);

    }

    public void loadEntityType(){
        this.entityType = DinosaurEntity.CLASS_TYPE_LIST.get(this.dinosaur.getDinosaurClass()).get();
    }

    public void checkAndLoadEntity(){
        if((this.dinosaur == null || this.entityType != null) && this.entity != null)
            return;

        loadEntityType();

        if(this.entity == null) {

            this.entity = this.entityType.create(this.level);

            this.initializeEntity(entity);

        }
    }


    //these were SUCH a bad way of doing this i have no clue how it worked
//    public void setDinosaur(String dinosaurId, boolean isMale, boolean isSkeleton) {
//        this.isMale = isMale;
//        this.isSkeleton = isSkeleton;
//        try {
//            Dinosaur dinosaur = Dinosaur.getDinosaurByName(dinosaurId);
//            this.entity = dinosaur.getDinosaurClass().getDeclaredConstructor(Level.class).newInstance(this.level);
//            this.initializeEntity(this.entity);
//        } catch (Exception e) {
//        }
//        this.setChanged();
//    }
//    public void setDinosaur(String dinosaurId, boolean isMale, boolean isSkeleton, byte variant, boolean isFossile) {
//        this.isMale = isMale;
//        this.isSkeleton = isSkeleton;
//        this.variant = variant;
//        this.isFossile = isFossile;
//
//        try {
//            Dinosaur dinosaur = Dinosaur.getDinosaurByName(dinosaurId);
//            this.entity = dinosaur.getDinosaurClass().getDeclaredConstructor(Level.class).newInstance(this.level);
//            this.initializeEntity(this.entity);
//        } catch (Exception e) {
//        }
//        this.setChanged();
//    }


    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);

        String dinoName = nbt.getString("Dinosaur");


        var dinosaur = Dinosaur.getDinosaurByName(dinoName);

        boolean isMale = !nbt.contains("IsMale") || nbt.getBoolean("IsMale");
        boolean isSkeleton = nbt.getBoolean("IsSkeleton");


        this.setDinosaur(dinosaur, isMale, isSkeleton, nbt.getCompound("DinosaurTag"));

        this.dinosaur = dinosaur;

        this.rotation = nbt.getInt("Rotation");
        this.variant = nbt.getByte("Variant");
        this.isFossile = nbt.getBoolean("IsFossile");


    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbt) {
        if (this.entity != null) {
            CompoundTag tag = this.entity.serializeNBT();
            nbt.put("DinosaurTag", tag);
        }

        nbt.putString("Dinosaur", this.dinosaur.getName());


        nbt.putInt("Rotation", this.rotation);
        nbt.putBoolean("IsMale", this.isMale);
        nbt.putBoolean("IsSkeleton", this.isSkeleton);
        nbt.putByte("Variant", this.variant);
        nbt.putBoolean("IsFossile", this.isFossile);
    }

//    @Override
//    public CompoundTag serializeNBT() {
//        CompoundTag nbt = super.serializeNBT();
//
//        if (this.entity != null) {
//            CompoundTag tag = this.entity.serializeNBT();
//            nbt.put("DinosaurTag", tag);
//        } else if (this.serializedData != null) {
//            this.serializedData.serialize(nbt);
//        }
//
//        nbt.putInt("Rotation", this.rotation);
//        nbt.putBoolean("IsMale", this.isMale);
//        nbt.putBoolean("IsSkeleton", this.isSkeleton);
//        nbt.putByte("Variant", this.variant);
//        nbt.putBoolean("IsFossile", this.isFossile);
//
//        return nbt;
//    }




    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {

        return ClientboundBlockEntityDataPacket.create(this, (entity) -> {
            CompoundTag entityData = new CompoundTag();
            entity.saveAdditional(entityData);
            return entityData;
        });
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        CompoundTag data = pkt.getTag();

        this.load(data);
    }

//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public AABB getRenderBoundingBox() {
//        if (this.isSkeleton && this.entity != null) {
//            return this.entity.getBoundingBoxForCulling().inflate(3.0).move(this.worldPosition);
//        }
//        return super.getRenderBoundingBox();
//    }


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
        if(this.entity == null){
            this.entity = this.createEntity();
        }
        return this.entity;
    }

    private DinosaurEntity createEntity() {
//        DinosaurEntity entity = this.serializedData.create(this.level);
        this.checkAndLoadEntity();
        DinosaurEntity entity = this.entityType.create(this.level);
        if (entity != null) {
            //this.serializedData = null;  wait WHAT???? why???????????
            this.initializeEntity(entity);
            this.entity = entity;
            return entity;
        } else {
            return null;
        }
    }

    private void initializeEntity(DinosaurEntity entity) {
        entity.setupDisplay(this.isMale);
        entity.setSkeleton(this.isSkeleton);
        entity.setSkeletonVariant(this.variant);
        entity.setIsFossile(this.isFossile);
        entity.setAnimation(EntityAnimation.IDLE.get());
        entity.setNoAi(true);
    }

    private void initializeEntity(DinosaurEntity entity, CompoundTag entityTag) {
        entity.load(entityTag);
        entity.setupDisplay(this.isMale);
        entity.setSkeleton(this.isSkeleton);
        entity.setSkeletonVariant(this.variant);
        entity.setIsFossile(this.isFossile);
        entity.setAnimation(EntityAnimation.IDLE.get());
        entity.setNoAi(true);

    }
//    private abstract class SerializedData {
//        public SerializedData withInitialData(EntityType<?> type){
//            return this;
//        }
//        abstract void serialize(CompoundTag compound);
//
//        abstract void deserialize(CompoundTag compound);
//
//        abstract DinosaurEntity create(Level world);
//    }
//
//    private class LegacyId extends SerializedData {
//        protected EntityType<?> dinosaurId;
//
//        public LegacyId withInitialData(EntityType<?> type) {
//            this.dinosaurId = type;
//            return this;
//        }
//
//
//        @Override
//        public void serialize(CompoundTag compound) {
//            if(this.dinosaurId == null)
//                this.dinosaurId = DinosaurEntity.CLASS_TYPE_LIST.get(DinosaurHandler.VELOCIRAPTOR.getDinosaurClass()).get();
//            compound.putString("DinosaurId", Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(this.dinosaurId)).toString());
//        }
//
//        @Override
//        public void deserialize(CompoundTag compound) {
//            this.dinosaurId = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(compound.getString("DinosaurId")));
//        }
//
//        @Override
//        public DinosaurEntity create(Level world) {
//            if(this.dinosaurId == null)
//                this.dinosaurId = ModEntities.VELOCIRAPTOR_ENTITY_TYPE.get();
//            Entity dino = dinosaurId.create(world);
//            if(dino instanceof DinosaurEntity d)
//                return d;
//            return null;
//        }
//    }
//
//    private class TagData extends SerializedData {
//        protected CompoundTag data;
//
//        TagData(){}
//
//        public TagData(EntityType<?> type) {
//            CompoundTag data = new CompoundTag();
//            data.putString("id", Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(type)).toString());
//
//            this.data = data;
//
//        }
//
//        public TagData withInitialData(EntityType<?> type) {
//            CompoundTag data = new CompoundTag();
//            data.putString("id", Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(type)).toString());
//
//            this.data = data;
//            return this;
//        }
//
//        @Override
//        public void serialize(CompoundTag compound) {
//            compound.put("DinosaurIdTag", this.data);
//        }
//
//        @Override
//        public void deserialize(CompoundTag compound) {
//            this.data = compound.getCompound("DinosaurIdTag");
//        }
//
//        @Override
//        public DinosaurEntity create(Level world) {
//            Entity entity = EntityType.by(this.data).orElse(EntityType.AXOLOTL/*literally does not matter in this case*/).create(world);
//            if (entity instanceof DinosaurEntity) {
//                return (DinosaurEntity) entity;
//            }
//            return null;
//        }
//    }
//
//    private class InvalidData extends LegacyId {
//        public SerializedData withInitialData() {
//            return new LegacyId().withInitialData(ModEntities.VELOCIRAPTOR_ENTITY_TYPE.get());
//        }
//
//        @Override
//        public void deserialize(CompoundTag compound) {
//            this.dinosaurId = ModEntities.VELOCIRAPTOR_ENTITY_TYPE.get();
//        }
//    }
//
//    public void setEntity(DinosaurEntity entity) {
//        this.entity = entity;
//    }


    public void tick(Level world, BlockPos pPos, BlockState pState, ActionFigureBlockEntity pBlockEntity) {
        if(!world.isClientSide && world instanceof ServerLevel svlvl){
            BlockUpdateUtils.broadcastBlockEntity(svlvl, this.getBlockPos());
        }
    }

    public DinosaurEntity peekEntity(){
        return this.entity;
    }
}
