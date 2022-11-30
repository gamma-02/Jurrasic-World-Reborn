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
import net.minecraft.world.entity.EntityType;

public class MetriacanthosaurusEntity extends DinosaurEntity
{
    public MetriacanthosaurusEntity(Level world, EntityType<MetriacanthosaurusEntity> type)
    {
        super(world, type);
        this.target(AchillobatorEntity.class, SpinoraptorEntity.class, TitanisEntity.class, SmilodonEntity.class, MegatheriumEntity.class, ArsinoitheriumEntity.class, SinoceratopsEntity.class, AlligatorGarEntity.class, AlvarezsaurusEntity.class, BeelzebufoEntity.class, VelociraptorBlueEntity.class, CarnotaurusEntity.class, CeratosaurusEntity.class, VelociraptorCharlieEntity.class, ChasmosaurusEntity.class, ChilesaurusEntity.class, CoelurusEntity.class, CompsognathusEntity.class, CorythosaurusEntity.class, CrassigyrinusEntity.class, VelociraptorDeltaEntity.class, DilophosaurusEntity.class, DimorphodonEntity.class, DiplocaulusEntity.class, DodoEntity.class, VelociraptorEchoEntity.class, GallimimusEntity.class, GuanlongEntity.class, HyaenodonEntity.class, HypsilophodonEntity.class, LeaellynasauraEntity.class, LeptictidiumEntity.class, MegapiranhaEntity.class, MicroceratusEntity.class, MicroraptorEntity.class, MussaurusEntity.class, OrnithomimusEntity.class, OthnieliaEntity.class, OviraptorEntity.class, PachycephalosaurusEntity.class, ParasaurolophusEntity.class, PostosuchusEntity.class, ProceratosaurusEntity.class, ProtoceratopsEntity.class, RugopsEntity.class, SegisaurusEntity.class, StyracosaurusEntity.class, TriceratopsEntity.class, TroodonEntity.class, VelociraptorEntity.class, Player.class
, Animal.class, Villager.class, GoatEntity.class);
//        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, Player.class
//, TyrannosaurusEntity.class, GiganotosaurusEntity.class, SpinosaurusEntity.class));TODO:AI
    }

    @Override
    public SoundEvent getSoundForAnimation(Animation animation)
    {
        switch (EntityAnimation.getAnimation(animation))
        {
            case SPEAK:
                return SoundHandler.METRIACANTHOSAURUS_LIVING;
            case DYING:
                return SoundHandler.METRIACANTHOSAURUS_DEATH;
            case INJURED:
                return SoundHandler.METRIACANTHOSAURUS_HURT;
            case CALLING:
                return SoundHandler.METRIACANTHOSAURUS_CALL;
            case ROARING:
                return SoundHandler.METRIACANTHOSAURUS_ROAR;
            case BEGGING:
                return SoundHandler.METRIACANTHOSAURUS_THREAT;
        }

        return null;
    }
}

