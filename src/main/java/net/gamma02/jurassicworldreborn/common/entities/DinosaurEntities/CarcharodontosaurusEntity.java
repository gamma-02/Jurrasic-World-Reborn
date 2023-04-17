package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;

public class CarcharodontosaurusEntity extends DinosaurEntity {

    public CarcharodontosaurusEntity(Level world, EntityType<CarcharodontosaurusEntity> type) {
        super(world, type);
        this.target(AchillobatorEntity.class, CamarasaurusEntity.class, AnkylodocusEntity.class, DiplodocusEntity.class, SpinoraptorEntity.class, RaphusrexEntity.class, TitanisEntity.class, SmilodonEntity.class, MegatheriumEntity.class, ElasmotheriumEntity.class, DeinotheriumEntity.class, ArsinoitheriumEntity.class, SinoceratopsEntity.class, AlligatorGarEntity.class, AllosaurusEntity.class, AlvarezsaurusEntity.class, AnkylosaurusEntity.class, ApatosaurusEntity.class, BaryonyxEntity.class, BeelzebufoEntity.class, VelociraptorBlueEntity.class, VelociraptorCharlieEntity.class, VelociraptorDeltaEntity.class, VelociraptorEchoEntity.class, CarnotaurusEntity.class, CeratosaurusEntity.class, CearadactylusEntity.class, ChasmosaurusEntity.class, ChilesaurusEntity.class, CoelacanthEntity.class, CoelurusEntity.class, CompsognathusEntity.class, CorythosaurusEntity.class, CrassigyrinusEntity.class, DilophosaurusEntity.class, DimorphodonEntity.class, DiplocaulusEntity.class, DodoEntity.class, GuanlongEntity.class, HyaenodonEntity.class, HypsilophodonEntity.class, IndominusEntity.class, LambeosaurusEntity.class, LeaellynasauraEntity.class, LeptictidiumEntity.class, LudodactylusEntity.class, EdmontosaurusEntity.class, GallimimusEntity.class, GiganotosaurusEntity.class, HerrerasaurusEntity.class, MajungasaurusEntity.class, MetriacanthosaurusEntity.class, MammothEntity.class, MicroceratusEntity.class, MicroraptorEntity.class, MoganopterusEntity.class, MussaurusEntity.class, OrnithomimusEntity.class, OthnieliaEntity.class, OviraptorEntity.class, PachycephalosaurusEntity.class, ParasaurolophusEntity.class, PostosuchusEntity.class, ProceratosaurusEntity.class, ProtoceratopsEntity.class, PteranodonEntity.class, RugopsEntity.class, QuetzalEntity.class, SegisaurusEntity.class, SpinosaurusEntity.class, StegosaurusEntity.class, StyracosaurusEntity.class, SuchomimusEntity.class, TherizinosaurusEntity.class, TriceratopsEntity.class, TyrannosaurusEntity.class, TroodonEntity.class, TropeognathusEntity.class, VelociraptorEntity.class, ZhenyuanopterusEntity.class, Player.class
, Animal.class, Villager.class);
    }

    public SoundEvent getSoundForAnimation(Animation animation) {
        switch (EntityAnimation.getAnimation(animation)) {
            case SPEAK:
                return SoundHandler.CARCHARODONTOSAURUS_LIVING;
            case HISSING:
                return SoundHandler.CARCHARODONTOSAURUS_HISS;
            case DYING:
                return SoundHandler.CARCHARODONTOSAURUS_HURT;
            case INJURED:
                return SoundHandler.CARCHARODONTOSAURUS_HURT;
            case ATTACKING:
                return SoundHandler.CARCHARODONTOSAURUS_GROWL;
            case ROARING:
                return SoundHandler.CARCHARODONTOSAURUS_ROAR;
            default:
                return null;
        }
    }
}
