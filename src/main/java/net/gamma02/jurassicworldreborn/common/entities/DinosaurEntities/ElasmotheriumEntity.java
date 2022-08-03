package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;


import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import com.github.alexthe666.citadel.animation.Animation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

public class ElasmotheriumEntity extends DinosaurEntity
{
    public ElasmotheriumEntity(Level world) { super(world); }
    public SoundEvent getSoundForAnimation(Animation animation) {

        switch (EntityAnimation.getAnimation(animation)) {
            case SPEAK:
                return SoundHandler.ELASMOTHERIUM_LIVING;
            case CALLING:
                return SoundHandler.ELASMOTHERIUM_LIVING;
            case MATING:
                return SoundHandler.ELASMOTHERIUM_THREAT;
            case DYING:
                return SoundHandler.ELASMOTHERIUM_DEATH;
            case INJURED:
                return SoundHandler.ELASMOTHERIUM_HURT;
            case BEGGING:
                return SoundHandler.ELASMOTHERIUM_THREAT;
            default:
                return null;
        }
    }
}
