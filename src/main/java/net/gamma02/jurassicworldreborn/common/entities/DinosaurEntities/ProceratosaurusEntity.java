package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import mod.reborn.server.entity.animal.GoatEntity;
import com.github.alexthe666.citadel.animation.Animation;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

public class ProceratosaurusEntity extends DinosaurEntity {

    public ProceratosaurusEntity(Level world) {
        super(world);
        this.target(AlligatorGarEntity.class, SmilodonEntity.class, AchillobatorEntity.class, AlvarezsaurusEntity.class, BeelzebufoEntity.class, VelociraptorBlueEntity.class, VelociraptorCharlieEntity.class, ChilesaurusEntity.class, CoelurusEntity.class, CompsognathusEntity.class, CrassigyrinusEntity.class, VelociraptorDeltaEntity.class, DilophosaurusEntity.class, DimorphodonEntity.class, DiplocaulusEntity.class, DodoEntity.class, VelociraptorEchoEntity.class, GuanlongEntity.class, HyaenodonEntity.class, HypsilophodonEntity.class, LeaellynasauraEntity.class, LeptictidiumEntity.class, MegapiranhaEntity.class, MicroceratusEntity.class, MicroraptorEntity.class, MussaurusEntity.class, OthnieliaEntity.class, OviraptorEntity.class, ProtoceratopsEntity.class, SegisaurusEntity.class, TroodonEntity.class, EntityPlayer.class, EntityAnimal.class, EntityVillager.class, GoatEntity.class, VelociraptorEntity.class);
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
