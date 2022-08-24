package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.SwimmingDinosaurEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class DunkleosteusEntity extends SwimmingDinosaurEntity
{
    public DunkleosteusEntity(Level world)
    {
        super(world);
        this.target(AlligatorGarEntity.class, MawsoniaEntity.class, Squid.class, BeelzebufoEntity.class, CrassigyrinusEntity.class, DiplocaulusEntity.class, Player.class
, MegapiranhaEntity.class, Villager.class, Animal.class, Mob.class);
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
