package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import com.github.alexthe666.citadel.animation.Animation;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

public class GuanlongEntity extends DinosaurEntity {

    public GuanlongEntity(Level world) {
        super(world);
        this.target(AlvarezsaurusEntity.class, TitanisEntity.class, SmilodonEntity.class, ArsinoitheriumEntity.class, ChilesaurusEntity.class, CoelurusEntity.class, CompsognathusEntity.class, DodoEntity.class, GallimimusEntity.class, HyaenodonEntity.class, HypsilophodonEntity.class, LeaellynasauraEntity.class, LeptictidiumEntity.class, MicroceratusEntity.class, MicroraptorEntity.class, MussaurusEntity.class, OrnithomimusEntity.class, OthnieliaEntity.class, OviraptorEntity.class, SegisaurusEntity.class, TroodonEntity.class, EntityPlayer.class, ProtoceratopsEntity.class, EntityAnimal.class, EntityVillager.class);
    }

    public SoundEvent getSoundForAnimation(Animation animation) {
        switch (EntityAnimation.getAnimation(animation)) {
            case SPEAK:
                return SoundHandler.GUANLONG_LIVING;
            case DYING:
                return SoundHandler.GUANLONG_DEATH;
            case INJURED:
                return SoundHandler.GUANLONG_HURT;
            default:
                return null;
        }
    }
}
