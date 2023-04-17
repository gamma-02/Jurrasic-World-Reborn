package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.FlyingDinosaurEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;

public class DimorphodonEntity extends FlyingDinosaurEntity
{
    public DimorphodonEntity(Level world, EntityType<DimorphodonEntity> type)
    {
        super(world, type);
        this.target(AlvarezsaurusEntity.class, Player.class
, Animal.class, Villager.class, CompsognathusEntity.class, DodoEntity.class, LeaellynasauraEntity.class, LeptictidiumEntity.class, MicroceratusEntity.class, MicroraptorEntity.class, OthnieliaEntity.class, SegisaurusEntity.class);
    }

    @Override
    protected void doTarget(){
        this.target(LeptictidiumEntity.class, MicroraptorEntity.class, MicroceratusEntity.class, CompsognathusEntity.class);
    }
    @Override
    public SoundEvent getSoundForAnimation(Animation animation)
    {
        switch (EntityAnimation.getAnimation(animation))
        {
            case SPEAK:
                return SoundHandler.DIMORPHODON_LIVING;
            case DYING:
                return SoundHandler.DIMORPHODON_DEATH;
            case INJURED:
                return SoundHandler.DIMORPHODON_HURT;
            case CALLING:
                return SoundHandler.DIMORPHODON_CALL;
            case BEGGING:
                return SoundHandler.DIMORPHODON_THREAT;
        }

        return null;
    }
}
