package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.animal.GoatEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class OviraptorEntity extends DinosaurEntity
{
    public OviraptorEntity(Level world)
    {
        super(world);
        this.target(CompsognathusEntity.class, LeptictidiumEntity.class, OthnieliaEntity.class, Player.class
, Animal.class, Villager.class, GoatEntity.class);
        doesEatEggs(true);
        //        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false)); TODO:AI

    }
    @Override
    public SoundEvent getSoundForAnimation(Animation animation)
    {
        switch (EntityAnimation.getAnimation(animation))
        {
            case SPEAK:
                return SoundHandler.OVIRAPTOR_LIVING;
            case DYING:
                return SoundHandler.OVIRAPTOR_DEATH;
            case INJURED:
                return SoundHandler.OVIRAPTOR_HURT;
            case BEGGING:
                return SoundHandler.OVIRAPTOR_THREAT;
        }

        return null;
    }
}
