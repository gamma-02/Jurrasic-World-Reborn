package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;

public class SpinosaurusEntity extends DinosaurEntity
{
    private int stepCount = 0;

    public SpinosaurusEntity(Level world, EntityType type)
    {
        super(world, type);
        this.target(AchillobatorEntity.class, ApatosaurusEntity.class, CamarasaurusEntity.class, AnkylodocusEntity.class, DiplodocusEntity.class, SpinoraptorEntity.class, TitanisEntity.class, RaphusrexEntity.class, SmilodonEntity.class, MegatheriumEntity.class, ElasmotheriumEntity.class, DeinotheriumEntity.class, ArsinoitheriumEntity.class, SinoceratopsEntity.class, AlligatorGarEntity.class, AllosaurusEntity.class, AlvarezsaurusEntity.class, BaryonyxEntity.class, BeelzebufoEntity.class, VelociraptorBlueEntity.class, MawsoniaEntity.class, CarnotaurusEntity.class, CearadactylusEntity.class, CeratosaurusEntity.class, VelociraptorCharlieEntity.class, ChasmosaurusEntity.class, ChilesaurusEntity.class, CoelurusEntity.class, CompsognathusEntity.class, CorythosaurusEntity.class, VelociraptorDeltaEntity.class, DilophosaurusEntity.class, DimorphodonEntity.class, HyaenodonEntity.class, VelociraptorEchoEntity.class, EdmontosaurusEntity.class, GallimimusEntity.class, GiganotosaurusEntity.class, GuanlongEntity.class, HerrerasaurusEntity.class, DodoEntity.class, HypsilophodonEntity.class, Player.class
, LambeosaurusEntity.class, LeaellynasauraEntity.class, LeptictidiumEntity.class, LudodactylusEntity.class, MajungasaurusEntity.class, MammothEntity.class, MetriacanthosaurusEntity.class, MicroceratusEntity.class, MicroraptorEntity.class, MoganopterusEntity.class, MussaurusEntity.class, OrnithomimusEntity.class, OthnieliaEntity.class, OviraptorEntity.class, PachycephalosaurusEntity.class, ParasaurolophusEntity.class, ProceratosaurusEntity.class, PostosuchusEntity.class, ProtoceratopsEntity.class, PteranodonEntity.class, QuetzalEntity.class, RugopsEntity.class, SegisaurusEntity.class, StegosaurusEntity.class, StyracosaurusEntity.class, TherizinosaurusEntity.class, TriceratopsEntity.class, TroodonEntity.class, TropeognathusEntity.class, TyrannosaurusEntity.class, VelociraptorEntity.class, ZhenyuanopterusEntity.class, CoelacanthEntity.class, MegapiranhaEntity.class, AlligatorGarEntity.class, Player.class
, Animal.class, Villager.class);
        //        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false)); TODO:AI

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
                return SoundHandler.SPINOSAURUS_LIVING;
            case DYING:
                return SoundHandler.SPINOSAURUS_DEATH;
            case INJURED:
                return SoundHandler.SPINOSAURUS_HURT;
            case CALLING:
                return SoundHandler.SPINOSAURUS_CALL;
            case ROARING:
                return SoundHandler.SPINOSAURUS_ROAR;
            case BEGGING:
                return SoundHandler.SPINOSAURUS_THREAT;
        }

        return null;
    }
    @Override
    public SoundEvent getBreathingSound()
    {
        return SoundHandler.SPINOSAURUS_BREATHING;
    }
}
