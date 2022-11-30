package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.LegSolver;
import com.github.alexthe666.citadel.animation.LegSolverQuadruped;
import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;

public class StegosaurusEntity extends DinosaurEntity {
    public LegSolverQuadruped legSolver;

    public StegosaurusEntity(Level world, EntityType<StegosaurusEntity> type) {
        super(world, type);
        //        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false)); TODO:AI

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

