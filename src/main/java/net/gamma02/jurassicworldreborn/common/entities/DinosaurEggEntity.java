package net.gamma02.jurassicworldreborn.common.entities;

import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;


public class DinosaurEggEntity extends Entity implements IEntityAdditionalSpawnData {
    private DinosaurEntity entity;

    private double motionX;
    private double motionY;
    private double motionZ;
    private UUID parent;
    private Dinosaur dinosaur;
    private int hatchTime;

    public DinosaurEggEntity(Level world, DinosaurEntity entity, DinosaurEntity parent) {
        this(world);
        this.entity = entity;
        this.dinosaur = entity.getDinosaur();
        this.parent = parent.getUUID();
    }

    public DinosaurEggEntity(Level world) {
        super((EntityType<DinosaurEntity>) null, world);
//        this.setSize(0.3F, 0.5F); todo: modernization
        this.hatchTime = this.random(5000, 6000);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public void tick() {
        super.tick();
        this.motionX = this.getDeltaMovement().x;
        this.motionY = this.getDeltaMovement().y;
        this.motionZ = this.getDeltaMovement().z;

        if(dinosaur == null) {
            Optional<Entity> parentEntity =  (this.level.isClientSide ? Optional.empty() : ((ServerLevel) level).getEntity(this.parent) == null ? Optional.empty() : Optional.of(((ServerLevel) level).getEntity(this.parent)));
            if(parentEntity.isPresent() && parentEntity.get() instanceof DinosaurEntity) {
                this.dinosaur = ((DinosaurEntity)parentEntity.get()).getDinosaur();
            }
        }

        if (!this.level.isClientSide) {
            if (this.entity == null) {
                this.kill();
            }

            this.hatchTime--;

            if (this.hatchTime <= 0) {
                this.hatch();
            }

            if (!this.onGround) {
//                this.motionY -= 0.035D; todo: why is this here
            }
            //***what***
            this.motionX *= 0.85;
            this.motionY *= 0.85;
            this.motionZ *= 0.85;

            this.move(MoverType.SELF, new Vec3(this.motionX, this.motionY, this.motionZ));
        }
        this.setDeltaMovement(this.motionX, this.motionY, this.motionZ);
    }





    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return null;
    }


    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {


        if (this.entity != null && !this.level.isClientSide) {
//            ItemStack eggStack = new ItemStack(ItemHandler.EGG, 1, EntityHandler.getDinosaurId(this.entity.getDinosaur()));
            CompoundTag nbt = new CompoundTag();
            nbt.putInt("DNAQuality", this.entity.getDNAQuality());
            nbt.putString("Genetics", this.entity.getGenetics());
//            eggStack.setTagCompound(nbt);
//            this.spawnAtLocation(eggStack);
            this.kill();
        }

        return super.interact(pPlayer, pHand);
    }

    public void hatch() {
        if(dinosaur != null && this.entity != null) {
            try {
                this.entity.setPos(this.getX(), this.getY(), this.getZ());
                this.level.addFreshEntity(this.entity);
                this.entity.playAmbientSound();
                this.kill();
                if(dinosaur == null) {
                    Optional<Entity> parentEntity =  (this.level.isClientSide ? Optional.empty() : ((ServerLevel) level).getEntity(this.parent) == null ? Optional.empty() : Optional.of(((ServerLevel) level).getEntity(this.parent)));
                    if(parentEntity.isPresent() && parentEntity.get() instanceof DinosaurEntity && this.dinosaur.shouldDefendOffspring() && ((DinosaurEntity) parentEntity.get()).family != null) {
                        ((DinosaurEntity) parentEntity.get()).family.addChild(this.entity.getUUID());
                    }
                }

//                for (Entity loadedEntity : this.level.loadedEntityList) {
//                    if (loadedEntity instanceof DinosaurEntity && loadedEntity.getUniqueID().equals(this.parent)) {
//                        DinosaurEntity parent = (DinosaurEntity) loadedEntity;
//                        if (parent.family != null && this.dinosaur.shouldDefendOffspring()) {
//                            parent.family.addChild(this.entity.getUniqueID());
//                        }
//                        break;
//                    }
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int random(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        this.hatchTime = compound.getInt("HatchTime");
        CompoundTag entityTag = compound.getCompound("Hatchling");
//        this.entity = (DinosaurEntity) EntityList.createEntityFromNBT(entityTag, this.level);
        this.entity = null; //TODO: agh
        this.parent = compound.getUUID("Parent");
        this.dinosaur = null;//EntityHandler.getDinosaurById(compound.getInteger("DinosaurID")); todo: more agh
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("HatchTime", this.hatchTime);
        if (this.entity != null) {
            compound.put("Hatchling", this.entity.serializeNBT());
        }
        compound.putUUID("Parent", this.parent);
        compound.putInt("DinosaurID", 1/*EntityHandler.getDinosaurId(dinosaur) todo: EntityHandler*/);
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        buffer.writeInt(1/*EntityHandler.getDinosaurId(this.entity != null ? this.entity.getDinosaur() : EntityHandler.getDinosaurById(0))  todo: more EntityHandler*/);
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        this.dinosaur = null;//EntityHandler.getDinosaurById(additionalData.readInt());
    }

    @Nullable
    public Dinosaur getDinosaur() {
        return this.dinosaur;
    }
}