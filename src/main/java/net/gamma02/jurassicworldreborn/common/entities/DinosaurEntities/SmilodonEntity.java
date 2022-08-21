package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import mod.reborn.server.entity.ai.LeapingMeleeEntityAI;
import mod.reborn.server.entity.ai.RaptorLeapEntityAI;
import mod.reborn.server.entity.animal.GoatEntity;
import com.github.alexthe666.citadel.animation.Animation;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.*;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

import java.util.Locale;

public class SmilodonEntity extends DinosaurEntity {
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(SmilodonEntity.class, EntityDataSerializers.INT);
    private static final Class[] targets = {CompsognathusEntity.class, MammothEntity.class, VelociraptorEntity.class, VelociraptorBlueEntity.class, VelociraptorCharlieEntity.class, VelociraptorDeltaEntity.class, VelociraptorEchoEntity.class, MegatheriumEntity.class, ElasmotheriumEntity.class, ArsinoitheriumEntity.class, Player.class
, DilophosaurusEntity.class, DimorphodonEntity.class, DodoEntity.class, LeaellynasauraEntity.class, HypsilophodonEntity.class, StegosaurusEntity.class, ProtoceratopsEntity.class, OthnieliaEntity.class, MicroceratusEntity.class};

    public SmilodonEntity(Level world) {
        super(world);
        this.setVariant(this.getRandom().nextInt(3));
        this.target(GoatEntity.class, Player.class
, TitanisEntity.class, Animal.class, Villager.class, AchillobatorEntity.class, AlligatorGarEntity.class, AlvarezsaurusEntity.class, BeelzebufoEntity.class, CearadactylusEntity.class, ChilesaurusEntity.class, CoelurusEntity.class, CompsognathusEntity.class, DilophosaurusEntity.class, DimorphodonEntity.class, GallimimusEntity.class, ProceratosaurusEntity.class, GuanlongEntity.class, DodoEntity.class, HypsilophodonEntity.class, LeaellynasauraEntity.class, LeptictidiumEntity.class, MicroraptorEntity.class, MussaurusEntity.class, MicroceratusEntity.class, OrnithomimusEntity.class, OthnieliaEntity.class, OviraptorEntity.class, PostosuchusEntity.class, ProtoceratopsEntity.class, SegisaurusEntity.class, TroodonEntity.class);
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
    public SoundEvent getSoundForAnimation(Animation animation)
    {
        switch (EntityAnimation.getAnimation(animation))
        {
            case SPEAK:
                return SoundHandler.SMILODON_LIVING;
            case DYING:
                return SoundHandler.SMILODON_DEATH;
            case INJURED:
                return SoundHandler.SMILODON_HURT;
            case CALLING:
                return SoundHandler.SMILODON_LIVING;
            case ROARING:
                return SoundHandler.SMILODON_ROAR;
            case BEGGING:
                return SoundHandler.SMILODON_HURT;
        }

        return null;
    }
    @Override
    public SoundEvent getBreathingSound()
    {
        return SoundHandler.SMILODON_BREATHING;
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
            case 0: default: return texture("orange");
            case 1: return texture("jw");
            case 2: return texture("gray");
        }
    }
    private ResourceLocation texture(String variant){
        String formattedName = this.dinosaur.getName().toLowerCase(Locale.ENGLISH).replaceAll(" ", "_");
        String baseTextures = "textures/entities/" + formattedName + "/";
        String texture = baseTextures + formattedName;
        return isMale()?new ResourceLocation(Jurassicworldreborn.modid, texture + "_male_" + "adult" + "_" + variant + ".png"):new ResourceLocation(Jurassicworldreborn.modid, texture + "_female_" + "adult" + "_" + variant +".png");
    }
}

