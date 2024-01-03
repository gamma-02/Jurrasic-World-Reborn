package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurHandler;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;

public class OviraptorEntity extends DinosaurEntity
{
    public OviraptorEntity(Level world, EntityType<OviraptorEntity> type)
    {
        super(world, type, DinosaurHandler.OVIRAPTOR);
        this.target(CompsognathusEntity.class, LeptictidiumEntity.class, OthnieliaEntity.class, Player.class
, Animal.class, Villager.class, Goat.class);
        doesEatEggs(true);
                this.addTask(1, new HurtByTargetGoal(this));
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

