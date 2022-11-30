package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;

public class HypsilophodonEntity extends DinosaurEntity
{
    public HypsilophodonEntity(Level world, EntityType<HypsilophodonEntity> type)
    {
        super(world, type);
    }

    @Override
    public SoundEvent getSoundForAnimation(Animation animation)
    {
        switch (EntityAnimation.getAnimation(animation))
        {
            case SPEAK:
                return SoundHandler.HYPSILOPHODON_LIVING;
            case DYING:
                return SoundHandler.HYPSILOPHODON_HURT;
            case INJURED:
                return SoundHandler.HYPSILOPHODON_HURT;
        }

        return null;
    }

    @Override
    protected float getJumpUpwardsMotion() {
        return 0.62F;
    }
}
