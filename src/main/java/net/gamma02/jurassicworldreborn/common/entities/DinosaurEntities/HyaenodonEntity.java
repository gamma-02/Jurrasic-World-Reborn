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

public class HyaenodonEntity extends DinosaurEntity {


    public HyaenodonEntity(Level world, EntityType type) {
        super(world, type);
        this.target(GoatEntity.class, SmilodonEntity.class, TitanisEntity.class, Player.class
, ArsinoitheriumEntity.class, Animal.class, Villager.class, AchillobatorEntity.class, AlligatorGarEntity.class, AlvarezsaurusEntity.class, BeelzebufoEntity.class, VelociraptorBlueEntity.class, VelociraptorCharlieEntity.class, ChilesaurusEntity.class, CoelurusEntity.class, CompsognathusEntity.class, CrassigyrinusEntity.class, VelociraptorDeltaEntity.class, DodoEntity.class, DiplocaulusEntity.class, VelociraptorEchoEntity.class, GallimimusEntity.class, GuanlongEntity.class, HypsilophodonEntity.class, LeaellynasauraEntity.class, LeptictidiumEntity.class, MicroceratusEntity.class, MicroraptorEntity.class, MussaurusEntity.class, OthnieliaEntity.class, OviraptorEntity.class, ProceratosaurusEntity.class, ProtoceratopsEntity.class, SegisaurusEntity.class, TroodonEntity.class, VelociraptorEntity.class);
//        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, Player.class
//, TyrannosaurusEntity.class, GiganotosaurusEntity.class, SpinosaurusEntity.class));TODO:AI
    }
    @Override
    public SoundEvent getSoundForAnimation(Animation animation)
    {
        switch (EntityAnimation.getAnimation(animation))
        {
            case SPEAK:
                return SoundHandler.HYAENODON_LIVING;
            case CALLING:
                return SoundHandler.HYAENODON_CALL;
            case DYING:
                return SoundHandler.HYAENODON_DEATH;
            case INJURED:
                return SoundHandler.HYAENODON_HURT;
        }

        return null;
    }
}
