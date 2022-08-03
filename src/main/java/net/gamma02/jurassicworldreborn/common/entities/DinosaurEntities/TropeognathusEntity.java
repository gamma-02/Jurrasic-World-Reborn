package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import mod.reborn.server.entity.FlyingDinosaurEntity;
import com.github.alexthe666.citadel.animation.Animation;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

public class TropeognathusEntity extends FlyingDinosaurEntity
{
    public TropeognathusEntity(Level world)
    {
        super(world);
        this.target(AlligatorGarEntity.class, EntitySquid.class, AlvarezsaurusEntity.class, BeelzebufoEntity.class, ChilesaurusEntity.class, CoelurusEntity.class, CompsognathusEntity.class, DimorphodonEntity.class, DiplocaulusEntity.class, DodoEntity.class, GuanlongEntity.class, HypsilophodonEntity.class, LeaellynasauraEntity.class, LeptictidiumEntity.class, LudodactylusEntity.class, MegapiranhaEntity.class, MicroceratusEntity.class, MicroraptorEntity.class, MoganopterusEntity.class, MussaurusEntity.class, OthnieliaEntity.class, OviraptorEntity.class, ProceratosaurusEntity.class, ProtoceratopsEntity.class, PteranodonEntity.class, SegisaurusEntity.class, TroodonEntity.class, ZhenyuanopterusEntity.class, EntityPlayer.class, EntityAnimal.class, EntityVillager.class);
    }
    @Override
    public SoundEvent getSoundForAnimation(Animation animation)
    {
        switch (EntityAnimation.getAnimation(animation))
        {
            case SPEAK:
                return SoundHandler.TROPEOGNATHUS_LIVING;
            case DYING:
                return SoundHandler.TROPEOGNATHUS_DEATH;
            case INJURED:
                return SoundHandler.TROPEOGNATHUS_HURT;
            case ROARING:
                return SoundHandler.TROPEOGNATHUS_ROAR;
            case BEGGING:
                return SoundHandler.TROPEOGNATHUS_THREAT;
        }

        return null;
    }
}
