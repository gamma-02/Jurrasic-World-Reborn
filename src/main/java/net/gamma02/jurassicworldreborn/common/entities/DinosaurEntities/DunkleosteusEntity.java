package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import mod.reborn.server.entity.SwimmingDinosaurEntity;
import com.github.alexthe666.citadel.animation.Animation;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

public class DunkleosteusEntity extends SwimmingDinosaurEntity
{
    public DunkleosteusEntity(Level world)
    {
        super(world);
        this.target(AlligatorGarEntity.class, MawsoniaEntity.class, EntitySquid.class, BeelzebufoEntity.class, CrassigyrinusEntity.class, DiplocaulusEntity.class, EntityPlayer.class, MegapiranhaEntity.class, EntityVillager.class, EntityAnimal.class, EntityMob.class);
    }
    @Override
    public SoundEvent getSoundForAnimation(Animation animation)
    {
        switch (EntityAnimation.getAnimation(animation))
        {
            case SPEAK:
                return SoundHandler.DUNKLEOSTEUS_LIVING;
            case DYING:
                return SoundHandler.DUNKLEOSTEUS_DEATH;
            case INJURED:
                return SoundHandler.DUNKLEOSTEUS_HURT;
            case BEGGING:
                return SoundHandler.DUNKLEOSTEUS_THREAT;
        }

        return null;
    }
}
