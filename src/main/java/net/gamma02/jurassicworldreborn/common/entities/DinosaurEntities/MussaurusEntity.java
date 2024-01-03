package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurHandler;
import net.gamma02.jurassicworldreborn.common.entities.ai.PeckGroundAnimationAI;
import net.gamma02.jurassicworldreborn.common.entities.ai.RearingUpAnimationAI;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;

public class MussaurusEntity extends DinosaurEntity {
    public MussaurusEntity(Level world, EntityType<MussaurusEntity> type){
        super(world, type, DinosaurHandler.MUSSAURUS);
        this.addTask(3, new PeckGroundAnimationAI(this));
        this.addTask(3, new RearingUpAnimationAI(this));
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

