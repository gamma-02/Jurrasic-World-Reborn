package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.common.entities.animal.GoatEntity;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;

public class ProceratosaurusEntity extends DinosaurEntity {

    public ProceratosaurusEntity(Level world, EntityType<ProceratosaurusEntity> type) {
        super(world, type);
        this.target(AlligatorGarEntity.class, SmilodonEntity.class, AchillobatorEntity.class, AlvarezsaurusEntity.class, BeelzebufoEntity.class, VelociraptorBlueEntity.class, VelociraptorCharlieEntity.class, ChilesaurusEntity.class, CoelurusEntity.class, CompsognathusEntity.class, CrassigyrinusEntity.class, VelociraptorDeltaEntity.class, DilophosaurusEntity.class, DimorphodonEntity.class, DiplocaulusEntity.class, DodoEntity.class, VelociraptorEchoEntity.class, GuanlongEntity.class, HyaenodonEntity.class, HypsilophodonEntity.class, LeaellynasauraEntity.class, LeptictidiumEntity.class, MegapiranhaEntity.class, MicroceratusEntity.class, MicroraptorEntity.class, MussaurusEntity.class, OthnieliaEntity.class, OviraptorEntity.class, ProtoceratopsEntity.class, SegisaurusEntity.class, TroodonEntity.class, Player.class
, Animal.class, Villager.class, GoatEntity.class, VelociraptorEntity.class);
    }

    public SoundEvent getSoundForAnimation(Animation animation) {
        switch (EntityAnimation.getAnimation(animation)) {
            case SPEAK:
                return SoundHandler.PROCERATOSAURUS_LIVING;
            case CALLING:
                return SoundHandler.PROCERATOSAURUS_MATE_CALL;
            case DYING:
                return SoundHandler.PROCERATOSAURUS_DEATH;
            case INJURED:
                return SoundHandler.PROCERATOSAURUS_HURT;
            case ATTACKING:
                return SoundHandler.PROCERATOSAURUS_ATTACK;
            case BEGGING:
                return SoundHandler.PROCERATOSAURUS_THREAT;
            default:
                return null;
        }
    }
}
