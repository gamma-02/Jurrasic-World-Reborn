package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

public class SinoceratopsEntity extends DinosaurEntity
{
    public SinoceratopsEntity(Level world) {
        super(world);
        //        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false)); TODO:AI

    }

        @Override
        public SoundEvent getSoundForAnimation(Animation animation) {
        switch (EntityAnimation.getAnimation(animation)) {
            case SPEAK:
                return SoundHandler.SINOCERATOPS_LIVING;
            case CALLING:
                return SoundHandler.SINOCERATOPS_CALLING;
            case DYING:
                return SoundHandler.SINOCERATOPS_DEATH;
            case BEGGING:
                return SoundHandler.SINOCERATOPS_THREAT;
            case INJURED:
                return SoundHandler.SINOCERATOPS_HURT;
            case MATING:
                return SoundHandler.SINOCERATOPS_MATING;
            default:
                return null;
        }
    }
}
