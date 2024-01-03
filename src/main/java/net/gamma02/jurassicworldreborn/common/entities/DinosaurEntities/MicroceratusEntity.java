package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurHandler;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;

public class MicroceratusEntity extends DinosaurEntity
{
    public MicroceratusEntity(Level world, EntityType<MicroceratusEntity> type)
    {
        super(world, type, DinosaurHandler.MICROCERATUS);
    }
    @Override
    public SoundEvent getSoundForAnimation(Animation animation)
    {
        switch (EntityAnimation.getAnimation(animation))
        {
            case SPEAK:
                return SoundHandler.MICROCERATUS_LIVING;
            case DYING:
                return SoundHandler.MICROCERATUS_DEATH;
            case INJURED:
                return SoundHandler.MICROCERATUS_HURT;
            case BEGGING:
                return SoundHandler.MICROCERATUS_THREAT;
        }

        return null;
    }
}

