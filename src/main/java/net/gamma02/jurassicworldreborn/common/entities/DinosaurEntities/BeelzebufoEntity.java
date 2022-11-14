package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.AmfibianDinosaurEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;

;

public class BeelzebufoEntity extends AmfibianDinosaurEntity {

    public BeelzebufoEntity(Level world, EntityType type) {
        super(world, type);
        this.target(AlvarezsaurusEntity.class, CompsognathusEntity.class, LeptictidiumEntity.class, OthnieliaEntity.class, MicroraptorEntity.class, MussaurusEntity.class, GuanlongEntity.class, GallimimusEntity.class);
//        this.tasks.addTask(0, new LeapingMeleeEntityAI(this, getAIMoveSpeed()));TODO:AI
//        this.tasks.addTask(0, new DinosaurWanderEntityAI(this, getAIMoveSpeed(), 10, RebornConfig.ENTITIES.dinosaurWalkingRadius));
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

//    @Override
//    public EntityAIBase getAttackAI() {
//        return new RaptorLeapEntityAI(this);
//    }

    @Override
    public int calculateFallDamage(float distance, float damageMultiplier) {
        if (this.getAnimation() != EntityAnimation.LEAP_LAND.get()) {
            return super.calculateFallDamage(distance, damageMultiplier);
        }
        else
            return 0;
    }
    protected void applyEntityAttributes()
    {
//        super.applyEntityAttributes();
        this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(35.0D);
    }

}

