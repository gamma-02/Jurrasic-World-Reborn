package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import com.github.alexthe666.citadel.animation.Animation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

public class DodoEntity extends DinosaurEntity
{
    public DodoEntity(Level world)
    {
        super(world);
    }

    @Override
    public SoundEvent getSoundForAnimation(Animation animation)
    {
        switch (EntityAnimation.getAnimation(animation))
        {
            case SPEAK:
                return SoundHandler.DODO_LIVING;
            case DYING:
                return SoundHandler.DODO_DEATH;
            case INJURED:
                return SoundHandler.DODO_HURT;
        }

        return null;
    }
}
