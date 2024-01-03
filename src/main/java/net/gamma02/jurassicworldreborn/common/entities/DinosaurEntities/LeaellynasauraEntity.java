package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurHandler;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;

public class LeaellynasauraEntity extends DinosaurEntity
{
    public LeaellynasauraEntity(Level world, EntityType<LeaellynasauraEntity> type)
    {
        super(world, type, DinosaurHandler.LEAELLYNASAURA);
    }
    @Override
    public SoundEvent getSoundForAnimation(Animation animation)
    {
        switch (EntityAnimation.getAnimation(animation))
        {
            case SPEAK:
                return SoundHandler.LEAELLYNASAURA_LIVING;
            case DYING:
                return SoundHandler.LEAELLYNASAURA_DEATH;
            case INJURED:
                return SoundHandler.LEAELLYNASAURA_HURT;
        }

        return null;
    }
}

