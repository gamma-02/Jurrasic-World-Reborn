package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.common.entities.animal.GoatEntity;
import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;

public class SegisaurusEntity extends DinosaurEntity
{
    public SegisaurusEntity(Level world, EntityType type)
    {
        super(world, type);
        this.target(MicroraptorEntity.class, MicroceratusEntity.class, AlligatorGarEntity.class, DodoEntity.class, HypsilophodonEntity.class, CompsognathusEntity.class, AlvarezsaurusEntity.class, Player.class
, Animal.class, Villager.class, GoatEntity.class);
        this.doesEatEggs(true);
    }
    @Override
    public SoundEvent getSoundForAnimation(Animation animation)
    {
        switch (EntityAnimation.getAnimation(animation))
        {
            case SPEAK:
                return SoundHandler.SEGISAURUS_LIVING;
            case DYING:
                return SoundHandler.SEGISAURUS_DEATH;
            case INJURED:
                return SoundHandler.SEGISAURUS_HURT;
            case BEGGING:
                return SoundHandler.SEGISAURUS_THREAT;
        }

        return null;
    }
}
