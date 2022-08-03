package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import mod.reborn.server.conf.RebornConfig;
import mod.reborn.server.entity.AmfibianDinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import mod.reborn.server.entity.SwimmingDinosaurEntity;
import mod.reborn.server.entity.ai.DinosaurWanderEntityAI;
import mod.reborn.server.entity.ai.LeapingMeleeEntityAI;
import mod.reborn.server.entity.ai.RaptorLeapEntityAI;
import mod.reborn.server.entity.animal.ai.EntityAIWanderNearWater;
import com.github.alexthe666.citadel.animation.Animation;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

public class BeelzebufoEntity extends AmfibianDinosaurEntity {

    public BeelzebufoEntity(Level world) {
        super(world);
        this.target(AlvarezsaurusEntity.class, CompsognathusEntity.class, LeptictidiumEntity.class, OthnieliaEntity.class, MicroraptorEntity.class, MussaurusEntity.class, GuanlongEntity.class, GallimimusEntity.class);
        this.tasks.addTask(0, new LeapingMeleeEntityAI(this, getAIMoveSpeed()));
        this.tasks.addTask(0, new DinosaurWanderEntityAI(this, getAIMoveSpeed(), 10, RebornConfig.ENTITIES.dinosaurWalkingRadius));
    }

    @Override
    public SoundEvent getSoundForAnimation(Animation animation) {
        switch (EntityAnimation.getAnimation(animation)) {
            case SPEAK:
                return SoundHandler.BEELZEBUFO_CROAK;
            case CALLING:
                return SoundHandler.BEELZEBUFO_LONG_DISTANCE_CROAK;
            case DYING:
                return SoundHandler.BEELZEBUFO_HURT_THREAT;
            case INJURED:
                return SoundHandler.BEELZEBUFO_HURT;
            case BEGGING:
                return SoundHandler.BEELZEBUFO_THREAT;
            default:
                return null;
        }
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

}

