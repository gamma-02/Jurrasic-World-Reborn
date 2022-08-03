package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import mod.reborn.server.entity.ai.RaptorLeapEntityAI;
import mod.reborn.server.entity.ai.animations.PeckGroundAnimationAI;
import com.github.alexthe666.citadel.animation.Animation;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

public class OrnithomimusEntity extends DinosaurEntity
{
    public OrnithomimusEntity(Level world)
    {
        super(world);
        this.animationTasks.addTask(3, new PeckGroundAnimationAI(this));
        this.tasks.addTask(1, new RaptorLeapEntityAI(this));
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

    @Override
    public SoundEvent getSoundForAnimation(Animation animation) {
        switch (EntityAnimation.getAnimation(animation)) {
            case SPEAK:
                return SoundHandler.ORNITHOMIMUS_LIVING;
            case CALLING:
                return SoundHandler.ORNITHOMIMUS_LIVING;
            case DYING:
                return SoundHandler.ORNITHOMIMUS_DEATH;
            case INJURED:
                return SoundHandler.ORNITHOMIMUS_HURT;
            default:
                break;
        }

        return null;
    }
}
