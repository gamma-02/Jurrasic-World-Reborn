package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

public class LambeosaurusEntity extends DinosaurEntity
{
    public LambeosaurusEntity(Level world)
    {
        super(world);
    }
    @Override
    public SoundEvent getSoundForAnimation(Animation animation)
    {
        switch (EntityAnimation.getAnimation(animation))
        {
            case SPEAK:
                return SoundHandler.LAMBEOSAURUS_LIVING;
            case DYING:
                return SoundHandler.LAMBEOSAURUS_DEATH;
            case INJURED:
                return SoundHandler.LAMBEOSAURUS_HURT;
            case CALLING:
                return SoundHandler.LAMBEOSAURUS_CALL;
            case BEGGING:
                return SoundHandler.LAMBEOSAURUS_THREAT;
        }

        return null;
    }
}
