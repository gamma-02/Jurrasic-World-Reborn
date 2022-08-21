package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

public class ProtoceratopsEntity extends DinosaurEntity
{
    public ProtoceratopsEntity(Level world)
    {
        super(world);
    }
    @Override
    public SoundEvent getSoundForAnimation(Animation animation)
    {
        switch (EntityAnimation.getAnimation(animation))
        {
            case SPEAK:
                return SoundHandler.PROTOCERATOPS_LIVING;
            case DYING:
                return SoundHandler.PROTOCERATOPS_DEATH;
            case INJURED:
                return SoundHandler.PROTOCERATOPS_HURT;
            case BEGGING:
                return SoundHandler.PROTOCERATOPS_THREAT;
        }

        return null;
    }
}
