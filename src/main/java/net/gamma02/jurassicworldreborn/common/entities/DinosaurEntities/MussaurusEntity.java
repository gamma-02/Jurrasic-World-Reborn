package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import mod.reborn.server.entity.ai.animations.PeckGroundAnimationAI;
import mod.reborn.server.entity.ai.animations.RearingUpAnimationAI;
import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

public class MussaurusEntity extends DinosaurEntity {
    public MussaurusEntity(Level world){
        super(world);
        this.animationTasks.addTask(3, new PeckGroundAnimationAI(this));
        this.animationTasks.addTask(3, new RearingUpAnimationAI(this));
    }

    @Override
    public SoundEvent getSoundForAnimation(Animation animation) {
        switch (EntityAnimation.getAnimation(animation)) {
            case SPEAK:
                return SoundHandler.MUSSAURUS_LIVING;
            case DYING:
                return SoundHandler.MUSSAURUS_DEATH;
            case INJURED:
                return SoundHandler.MUSSAURUS_HURT;
            case ATTACKING:
                return SoundHandler.MUSSAURUS_ATTACK;
            case CALLING:
                return SoundHandler.MUSSAURUS_MATE_CALL;
		default:
			break;
        }

        return null;
    }
}
