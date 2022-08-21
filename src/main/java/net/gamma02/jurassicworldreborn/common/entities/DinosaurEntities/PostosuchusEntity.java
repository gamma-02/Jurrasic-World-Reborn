package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

public class PostosuchusEntity extends DinosaurEntity {

    public PostosuchusEntity(Level world) {
        super(world);
//        this.target(AchillobatorEntity.class, SpinoraptorEntity.class, TitanisEntity.class, SmilodonEntity.class, MegatheriumEntity.class, ArsinoitheriumEntity.class, AlligatorGarEntity.class, AlvarezsaurusEntity.class, BeelzebufoEntity.class, VelociraptorBlueEntity.class, VelociraptorCharlieEntity.class, ChilesaurusEntity.class, CoelurusEntity.class, CompsognathusEntity.class, CrassigyrinusEntity.class, DimorphodonEntity.class, DiplocaulusEntity.class, DodoEntity.class, HypsilophodonEntity.class, LeaellynasauraEntity.class, LeptictidiumEntity.class, MegapiranhaEntity.class, MicroceratusEntity.class, MicroraptorEntity.class, MussaurusEntity.class, OthnieliaEntity.class, OviraptorEntity.class, ProtoceratopsEntity.class, SegisaurusEntity.class, TroodonEntity.class, VelociraptorEchoEntity.class, DilophosaurusEntity.class, VelociraptorDeltaEntity.class, HyaenodonEntity.class, OrnithomimusEntity.class, GuanlongEntity.class, MetriacanthosaurusEntity.class, ProceratosaurusEntity.class, RugopsEntity.class, VelociraptorEntity.class, Player.class, Animal.class, Villager.class/*, GoatEntity.class todo: non dinosaur entities*/); TODO: ai
    }

    @Override
    public SoundEvent getSoundForAnimation(Animation animation) {
        switch (EntityAnimation.getAnimation(animation)) {
            case CALLING:
                return SoundHandler.POSTOSUCHUS_CALL;
            case DYING:
                return SoundHandler.POSTOSUCHUS_DEATH;
            case INJURED:
                return SoundHandler.POSTOSUCHUS_ATTACK;
            case ATTACKING:
                return SoundHandler.POSTOSUCHUS_ATTACK;
            default:
                return null;
        }
    }
}
