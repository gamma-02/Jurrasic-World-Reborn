package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import mod.reborn.server.entity.LegSolver;
import mod.reborn.server.entity.LegSolverQuadruped;
import com.github.alexthe666.citadel.animation.Animation;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

public class StegosaurusEntity extends DinosaurEntity {
    public LegSolverQuadruped legSolver;

    public StegosaurusEntity(Level world) {
        super(world);
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    }

    @Override
    protected LegSolver createLegSolver() {
        return this.legSolver = new LegSolverQuadruped(0.2F, 1.2F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public SoundEvent getSoundForAnimation(Animation animation) {
        switch (EntityAnimation.getAnimation(animation)) {
            case SPEAK:
                return SoundHandler.STEGOSAURUS_LIVING;
            case CALLING:
                return SoundHandler.STEGOSAURUS_LIVING;
            case DYING:
                return SoundHandler.STEGOSAURUS_DEATH;
            case INJURED:
                return SoundHandler.STEGOSAURUS_HURT;
		default:
			break;
        }

        return null;
    }
}

