package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import mod.reborn.server.entity.ai.LeapingMeleeEntityAI;
import mod.reborn.server.entity.ai.RaptorLeapEntityAI;
import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Locale;

public class IndoraptorEntity extends DinosaurEntity {
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(IndoraptorEntity.class, EntityDataSerializers.INT);
    private static final Class[] targets = {EntityLivingBase.class, Player.class
};

    public IndoraptorEntity(Level world) {
        super(world);
        this.setVariant(this.getRandom().nextInt(2));
        this.target(EntityLivingBase.class, Player.class
);
        this.tasks.addTask(0, new LeapingMeleeEntityAI(this, this.dinosaur.getAttackSpeed()));
        this.tasks.addTask(1, new RaptorLeapEntityAI(this));
        this.target(targets);
        for(Class entity : targets) {
            this.tasks.addTask(0, new EntityAINearestAttackableTarget<EntityLivingBase>(this, entity, true, false));
            this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<EntityLivingBase>(this, entity, false));
        }
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, Player.class
, TyrannosaurusEntity.class, GiganotosaurusEntity.class, SpinosaurusEntity.class));
    }
    @Override
    public SoundEvent getSoundForAnimation(Animation animation) {
        switch (EntityAnimation.getAnimation(animation)) {
            case SPEAK:
                return SoundHandler.INDORAPTOR_LIVING;
            case CALLING:
                return SoundHandler.INDORAPTOR_CALLING;
            case DYING:
                return SoundHandler.INDORAPTOR_DEATH;
            case BEGGING:
                return SoundHandler.INDORAPTOR_THREAT;
            case INJURED:
                return SoundHandler.INDORAPTOR_HURT;
            case ROARING:
                return SoundHandler.INDORAPTOR_ROAR;
            case MATING:
                return SoundHandler.INDORAPTOR_MATING;
            default:
                return null;
        }
    }
    @Override
    public SoundEvent getBreathingSound()
    {
        return SoundHandler.INDORAPTOR_BREATHING;
    }

    @Override
    public EntityAIBase getAttackAI() {
        return new RaptorLeapEntityAI(this);
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
        if (this.getAnimation() != EntityAnimation.LEAP_LAND.get()) {
            super.fall(distance, damageMultiplier);
        }
    }
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
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
            case 0: default: return texture("black");
            case 1: return texture("white");
        }
    }
    private ResourceLocation texture(String variant){
        String formattedName = this.dinosaur.getName().toLowerCase(Locale.ENGLISH).replaceAll(" ", "_");
        String baseTextures = "textures/entities/" + formattedName + "/";
        String texture = baseTextures + formattedName;
        return isMale()?new ResourceLocation(Jurassicworldreborn.modid, texture + "_male_" + "adult" + "_" + variant + ".png"):new ResourceLocation(Jurassicworldreborn.modid, texture + "_female_" + "adult" + "_" + variant +".png");
    }
}

