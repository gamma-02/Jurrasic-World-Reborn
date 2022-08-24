package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;

public class CarnotaurusEntity extends DinosaurEntity
{
    public CarnotaurusEntity(Level world)
    {
        super(world);
        this.target(AchillobatorEntity.class, SpinoraptorEntity.class, TitanisEntity.class, SmilodonEntity.class, MegatheriumEntity.class, ElasmotheriumEntity.class, ArsinoitheriumEntity.class, AlligatorGarEntity.class, AlvarezsaurusEntity.class, BaryonyxEntity.class, BeelzebufoEntity.class, VelociraptorBlueEntity.class, CeratosaurusEntity.class, VelociraptorCharlieEntity.class, ChasmosaurusEntity.class, ChilesaurusEntity.class, CoelurusEntity.class, CompsognathusEntity.class, CorythosaurusEntity.class, VelociraptorDeltaEntity.class, DilophosaurusEntity.class, DimorphodonEntity.class, DiplocaulusEntity.class, DodoEntity.class, VelociraptorEchoEntity.class, EdmontosaurusEntity.class, GallimimusEntity.class, GuanlongEntity.class, HerrerasaurusEntity.class, HyaenodonEntity.class, HypsilophodonEntity.class, LambeosaurusEntity.class, LeaellynasauraEntity.class, LeptictidiumEntity.class, LudodactylusEntity.class, MajungasaurusEntity.class, MammothEntity.class, MetriacanthosaurusEntity.class, MicroceratusEntity.class, MicroraptorEntity.class, MussaurusEntity.class, OrnithomimusEntity.class, OthnieliaEntity.class, OviraptorEntity.class, PachycephalosaurusEntity.class, ParasaurolophusEntity.class, PostosuchusEntity.class, ProceratosaurusEntity.class, ProtoceratopsEntity.class, RugopsEntity.class, SegisaurusEntity.class, StyracosaurusEntity.class, TroodonEntity.class, VelociraptorEntity.class, Animal.class, Villager.class);
//        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, Player.class
//, TyrannosaurusEntity.class, GiganotosaurusEntity.class, SpinosaurusEntity.class)); TODO:AI
    }

    @Override
    public SoundEvent getSoundForAnimation(Animation animation)
    {
        switch (EntityAnimation.getAnimation(animation))
        {
            case SPEAK:
                return SoundHandler.CARNOTAURUS_LIVING;
            case CALLING:
                return SoundHandler.CARNOTAURUS_LIVING;
            case DYING:
                return SoundHandler.CARNOTAURUS_DEATH;
            case INJURED:
                return SoundHandler.CARNOTAURUS_HURT;
        }

        return null;
    }
}
