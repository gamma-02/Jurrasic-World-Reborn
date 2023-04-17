package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.LegSolverBiped;
import net.gamma02.jurassicworldreborn.common.entities.ai.PeckGroundAnimationAI;
import net.gamma02.jurassicworldreborn.common.entities.ai.RaptorLeapEntityAI;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;

public class GallimimusEntity extends DinosaurEntity {
    public LegSolverBiped legSolver;


    public GallimimusEntity(Level world, EntityType<GallimimusEntity> type) {
        super(world, type);
        this.addTask(3, new PeckGroundAnimationAI(this));//TODO: Animation AI
        this.addTask(1, new RaptorLeapEntityAI(this));
    }

//    @Override
//    public EntityAIBase getAttackAI() {
//        return new RaptorLeapEntityAI(this);
//    }

    @Override
    public int calculateFallDamage(float distance, float damageMultiplier) {
        if (this.getAnimation() != EntityAnimation.LEAP_LAND.get()) {
            super.calculateFallDamage(distance, damageMultiplier); } return 0;
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
