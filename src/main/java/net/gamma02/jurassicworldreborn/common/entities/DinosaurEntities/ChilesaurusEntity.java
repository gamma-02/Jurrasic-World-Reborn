package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;

public class ChilesaurusEntity extends DinosaurEntity {

    public ChilesaurusEntity(Level world, EntityType<ChilesaurusEntity> type) {
        super(world, type);
//        this.addTask(1, new RaptorLeapEntityAI(this));todo:ai
    }

    @Override
    public SoundEvent getSoundForAnimation(Animation animation)
    {
        switch (EntityAnimation.getAnimation(animation))
        {
            case SPEAK:
                return SoundHandler.CHILESAURUS_LIVING;
            case DYING:
                return SoundHandler.CHILESAURUS_DEATH;
            case INJURED:
                return SoundHandler.CHILESAURUS_HURT;
            case CALLING:
                return SoundHandler.CHILESAURUS_LIVING;
        }

        return null;
    }
}
