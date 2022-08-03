package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import com.github.alexthe666.citadel.animation.Animation;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

public class AlvarezsaurusEntity extends DinosaurEntity {

    public AlvarezsaurusEntity(Level world) {
        super(world);
        this.target(DodoEntity.class, CompsognathusEntity.class, HypsilophodonEntity.class, EntityAnimal.class, LeptictidiumEntity.class, MicroraptorEntity.class, MicroceratusEntity.class, DimorphodonEntity.class, LeaellynasauraEntity.class, ProtoceratopsEntity.class, OviraptorEntity.class, MussaurusEntity.class, OthnieliaEntity.class, TroodonEntity.class, SegisaurusEntity.class);
    }
    @Override
    public SoundEvent getSoundForAnimation(Animation animation) {
        switch (EntityAnimation.getAnimation(animation)) {
            case SPEAK:
                return SoundHandler.ALVAREZSAURUS_LIVING;
            case DYING:
                return SoundHandler.ALVAREZSAURUS_DEATH;
            case INJURED:
                return SoundHandler.ALVAREZSAURUS_HURT;
            case CALLING:
                return SoundHandler.ALVAREZSAURUS_CALL;
            case BEGGING:
                return SoundHandler.ALVAREZSAURUS_THREAT;
        }

        return null;
    }
}
