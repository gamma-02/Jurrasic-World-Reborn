package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import com.github.alexthe666.citadel.animation.Animation;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

public class TherizinosaurusEntity extends DinosaurEntity
{
    public TherizinosaurusEntity(Level world)
    {
        super(world);
        this.target(TyrannosaurusEntity.class);
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
    }
    @Override
    public SoundEvent getSoundForAnimation(Animation animation)
    {
        switch (EntityAnimation.getAnimation(animation))
        {
            case SPEAK:
                return SoundHandler.THERIZINOSAURUS_LIVING;
            case DYING:
                return SoundHandler.THERIZINOSAURUS_DEATH;
            case INJURED:
                return SoundHandler.THERIZINOSAURUS_HURT;
            case CALLING:
                return SoundHandler.THERIZINOSAURUS_LIVING;
            case ROARING:
                return SoundHandler.THERIZINOSAURUS_ROAR;
            case BEGGING:
                return SoundHandler.THERIZINOSAURUS_THREAT;
        }

        return null;
    }
}
