package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;

public class TherizinosaurusEntity extends DinosaurEntity
{
    public TherizinosaurusEntity(Level world, EntityType<TherizinosaurusEntity> type)
    {
        super(world, type);
        this.target(TyrannosaurusEntity.class);
                this.addTask(1, new HurtByTargetGoal(this));
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
