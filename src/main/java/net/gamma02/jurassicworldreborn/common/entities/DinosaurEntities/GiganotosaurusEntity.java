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

public class GiganotosaurusEntity extends DinosaurEntity
{
    private int stepCount = 0;//why it step? - gamma_02

    public GiganotosaurusEntity(Level world, EntityType<GiganotosaurusEntity> type)
    {
        super(world, type);
        this.target(AchillobatorEntity.class, ApatosaurusEntity.class, CamarasaurusEntity.class, AnkylodocusEntity.class, DiplodocusEntity.class, SpinoraptorEntity.class, TitanisEntity.class, RaphusrexEntity.class, SmilodonEntity.class, MegatheriumEntity.class, ElasmotheriumEntity.class, DeinotheriumEntity.class, ArsinoitheriumEntity.class, SinoceratopsEntity.class, AlligatorGarEntity.class, AllosaurusEntity.class, AnkylosaurusEntity.class, AlvarezsaurusEntity.class, GuanlongEntity.class, BaryonyxEntity.class, BeelzebufoEntity.class, CarcharodontosaurusEntity.class, CarnotaurusEntity.class, CeratosaurusEntity.class, VelociraptorEntity.class, VelociraptorBlueEntity.class, VelociraptorCharlieEntity.class, VelociraptorDeltaEntity.class, VelociraptorEchoEntity.class, ChasmosaurusEntity.class, CoelurusEntity.class, CorythosaurusEntity.class, CompsognathusEntity.class, DilophosaurusEntity.class, DimorphodonEntity.class, DodoEntity.class, EdmontosaurusEntity.class, GallimimusEntity.class, HerrerasaurusEntity.class, HyaenodonEntity.class, HypsilophodonEntity.class, IndominusEntity.class, LambeosaurusEntity.class, LeaellynasauraEntity.class, LeptictidiumEntity.class, LudodactylusEntity.class, MajungasaurusEntity.class, MammothEntity.class, MetriacanthosaurusEntity.class, MicroceratusEntity.class, MicroraptorEntity.class, MoganopterusEntity.class, MussaurusEntity.class, OrnithomimusEntity.class, OthnieliaEntity.class, OviraptorEntity.class, PachycephalosaurusEntity.class, ParasaurolophusEntity.class, PostosuchusEntity.class, ProceratosaurusEntity.class, ProtoceratopsEntity.class, PteranodonEntity.class, QuetzalEntity.class, RugopsEntity.class, SegisaurusEntity.class, SpinosaurusEntity.class, StegosaurusEntity.class, StyracosaurusEntity.class, SuchomimusEntity.class, TherizinosaurusEntity.class, TriceratopsEntity.class, TroodonEntity.class, TropeognathusEntity.class, TyrannosaurusEntity.class, ZhenyuanopterusEntity.class, Player.class
, Animal.class, Villager.class,  GoatEntity.class);
    }

    @Override
    public void tick()
    {
        super.tick();

        if (this.zza > 0 && this.stepCount <= 0)
        {
            this.playSound(SoundHandler.STOMP, (float) interpolate(0.1F, 1.0F), this.getVoicePitch());
            stepCount = 65;
        }

        this.stepCount -= this.zza * 9.5;
    }
    @Override
    public SoundEvent getSoundForAnimation(Animation animation)
    {
        switch (EntityAnimation.getAnimation(animation))
        {
            case SPEAK:
                return SoundHandler.GIGANOTOSAURUS_LIVING;
            case DYING:
                return SoundHandler.GIGANOTOSAURUS_DEATH;
            case INJURED:
                return SoundHandler.GIGANOTOSAURUS_HURT;
            case CALLING:
                return SoundHandler.GIGANOTOSAURUS_CALL;
            case ROARING:
                return SoundHandler.GIGANOTOSAURUS_ROAR;
            case BEGGING:
                return SoundHandler.GIGANOTOSAURUS_THREAT;
        }

        return null;
    }
}
