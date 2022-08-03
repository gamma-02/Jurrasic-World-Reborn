package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import mod.reborn.server.entity.animal.GoatEntity;
import com.github.alexthe666.citadel.animation.Animation;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

public class MegatheriumEntity extends DinosaurEntity
{
    public MegatheriumEntity(Level world) { super(world); }
    public SoundEvent getSoundForAnimation(Animation animation) {

        switch (EntityAnimation.getAnimation(animation)) {
            case SPEAK:
                return SoundHandler.MEGATHERIUM_LIVING;
            case CALLING:
                return SoundHandler.MEGATHERIUM_LIVING;
            case MATING:
                return SoundHandler.MEGATHERIUM_THREAT;
            case DYING:
                return SoundHandler.MEGATHERIUM_DEATH;
            case INJURED:
                return SoundHandler.MEGATHERIUM_HURT;
            case BEGGING:
                return SoundHandler.MEGATHERIUM_THREAT;
            default:
                return null;
        }
    }
}
