package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import mod.reborn.server.entity.LegSolverBiped;
import mod.reborn.server.entity.ai.RaptorLeapEntityAI;
import mod.reborn.server.entity.ai.animations.PeckGroundAnimationAI;
import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

public class GallimimusEntity extends DinosaurEntity {
    public LegSolverBiped legSolver;

    public GallimimusEntity(Level world) {
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
    protected LegSolverBiped createLegSolver() {
        return this.legSolver = new LegSolverBiped(-0.05F, 0.25F, 0.5F);
    }

    @Override
    public SoundEvent getSoundForAnimation(Animation animation) {
        switch (EntityAnimation.getAnimation(animation)) {
            case SPEAK:
                return SoundHandler.GALLIMIMUS_LIVING;
            case CALLING:
                return SoundHandler.GALLIMIMUS_LIVING;
            case DYING:
                return SoundHandler.GALLIMIMUS_DEATH;
            case INJURED:
                return SoundHandler.GALLIMIMUS_HURT;
		default:
			break;
        }

        return null;
    }
}
