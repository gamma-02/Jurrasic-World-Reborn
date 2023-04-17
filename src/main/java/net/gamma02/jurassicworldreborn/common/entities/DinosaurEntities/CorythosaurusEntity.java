package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;

public class CorythosaurusEntity extends DinosaurEntity
{
    public CorythosaurusEntity(Level world, EntityType<CorythosaurusEntity> type)
    {
        super(world, type);
    }
    @Override
    public SoundEvent getSoundForAnimation(Animation animation)
    {
        switch (EntityAnimation.getAnimation(animation))
        {
            case SPEAK:
                return SoundHandler.CORYTHOSAURUS_LIVING;
            case DYING:
                return SoundHandler.CORYTHOSAURUS_DEATH;
            case INJURED:
                return SoundHandler.CORYTHOSAURUS_HURT;
            case CALLING:
                return SoundHandler.CORYTHOSAURUS_CALL;
            case BEGGING:
                return SoundHandler.CORYTHOSAURUS_THREAT;
        }

        return null;
    }
}

