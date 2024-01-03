package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurHandler;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;

public class ProtoceratopsEntity extends DinosaurEntity
{
    public ProtoceratopsEntity(Level world, EntityType<ProtoceratopsEntity> type)
    {
        super(world, type, DinosaurHandler.PROTOCERATOPS);
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

