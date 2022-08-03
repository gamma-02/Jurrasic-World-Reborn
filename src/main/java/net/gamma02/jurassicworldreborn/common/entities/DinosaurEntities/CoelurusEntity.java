package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import com.github.alexthe666.citadel.animation.Animation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

public class CoelurusEntity extends DinosaurEntity
{
    public CoelurusEntity(Level world)
    {
        super(world);
        this.target(AlligatorGarEntity.class, SmilodonEntity.class, AlvarezsaurusEntity.class, BeelzebufoEntity.class, CompsognathusEntity.class, DodoEntity.class, CrassigyrinusEntity.class, DiplocaulusEntity.class, SegisaurusEntity.class, HypsilophodonEntity.class, OthnieliaEntity.class, MussaurusEntity.class, TroodonEntity.class, DimorphodonEntity.class, ChilesaurusEntity.class, MicroraptorEntity.class, LeptictidiumEntity.class, OviraptorEntity.class, ProtoceratopsEntity.class, GuanlongEntity.class, HyaenodonEntity.class, LeaellynasauraEntity.class, ProceratosaurusEntity.class, MicroceratusEntity.class, EntityPlayer.class, EntityAnimal.class, EntityVillager.class);
    }
    @Override
    public SoundEvent getSoundForAnimation(Animation animation)
    {
        switch (EntityAnimation.getAnimation(animation))
        {
            case SPEAK:
                return SoundHandler.COELURUS_LIVING;
            case DYING:
                return SoundHandler.COELURUS_DEATH;
            case INJURED:
                return SoundHandler.COELURUS_HURT;
            case BEGGING:
                return SoundHandler.COELURUS_THREAT;
        }

        return null;
    }
}

