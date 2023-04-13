package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;

public class EdmontosaurusEntity extends DinosaurEntity
{
    public EdmontosaurusEntity(Level world, EntityType<EdmontosaurusEntity> type)
    {
        super(world, type);
    }
    @Override
    public SoundEvent getSoundForAnimation(Animation animation)
    {
        switch (EntityAnimation.getAnimation(animation))
        {
            case SPEAK:
                return SoundHandler.EDMONTOSAURUS_LIVING;
            case DYING:
                return SoundHandler.EDMONTOSAURUS_DEATH;
            case INJURED:
                return SoundHandler.EDMONTOSAURUS_HURT;
            case CALLING:
                return SoundHandler.EDMONTOSAURUS_CALL;
            case BEGGING:
                return SoundHandler.EDMONTOSAURUS_THREAT;
        }

        return null;
    }
}
