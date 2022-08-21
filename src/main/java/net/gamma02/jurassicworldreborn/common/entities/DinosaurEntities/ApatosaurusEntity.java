package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.LegSolver;
import com.github.alexthe666.citadel.animation.LegSolverQuadruped;
import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

import java.util.Locale;

public class ApatosaurusEntity extends DinosaurEntity {
    private static final EntityDataAccessor<Integer> VARIANT= SynchedEntityData.defineId(ApatosaurusEntity.class, EntityDataSerializers.INT);

    private int stepCount = 0;

    public LegSolverQuadruped legSolver;
    public ApatosaurusEntity(Level world) {
        super(world);
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.setVariant(this.getRandom().nextInt(3));
    }
    @Override
    protected LegSolver createLegSolver() {
        return this.legSolver = new LegSolverQuadruped(2.5F, 2.0F, 1.0F, 1.0F);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.onGround && !this.isInWater()) {
            if (this.moveForward > 0 && (this.posX - this.prevPosX > 0 || this.posZ - this.prevPosZ > 0) && this.stepCount <= 0) {
                this.playSound(SoundHandler.STOMP, (float) this.interpolate(0.1F, 1.0F), this.getSoundPitch());
                this.stepCount = 65;
            }
            this.stepCount -= this.moveForward * 9.5;
        }
    }


    @Override
    public SoundEvent getSoundForAnimation(Animation animation)
    {
        switch (EntityAnimation.getAnimation(animation))
        {
            case SPEAK:
                return SoundHandler.APATOSAURUS_LIVING;
            case DYING:
                return SoundHandler.APATOSAURUS_DEATH;
            case INJURED:
                return SoundHandler.APATOSAURUS_HURT;
            case CALLING:
                return SoundHandler.APATOSAURUS_CALL;
            case BEGGING:
                return SoundHandler.APATOSAURUS_THREAT;
            case WALKING:
                return SoundHandler.STOMP;
        }

        return null;
    }
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("Variant", this.entityData.get(VARIANT));
    }


    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.entityData.set(VARIANT, nbt.getInt("Variant"));
    }

    public void setVariant(int value){
        this.entityData.set(VARIANT, value);
    }

    public int getVariant(){
        return this.entityData.get(VARIANT);
    }

    public ResourceLocation getTexture(){
        switch(getVariant()){
            case 0: default: return texture("jw");
            case 1: return texture("steppe");
            case 2: return texture("taiga");
        }
    }
    private ResourceLocation texture(String variant){
        String formattedName = this.dinosaur.getName().toLowerCase(Locale.ENGLISH).replaceAll(" ", "_");
        String baseTextures = "textures/entities/" + formattedName + "/";
        String texture = baseTextures + formattedName;
        return isMale()?new ResourceLocation(Jurassicworldreborn.modid, texture + "_male_" + "adult" + "_" + variant + ".png"):new ResourceLocation(Jurassicworldreborn.modid, texture + "_female_" + "adult" + "_" + variant +".png");
    }
}