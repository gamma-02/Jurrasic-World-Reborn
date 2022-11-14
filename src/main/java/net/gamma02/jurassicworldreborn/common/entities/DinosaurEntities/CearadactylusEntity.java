package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.FlyingDinosaurEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;

public class CearadactylusEntity extends FlyingDinosaurEntity
{
    public CearadactylusEntity(Level world, EntityType type)
    {
        super(world, type);
        this.target(AlligatorGarEntity.class,
                AlvarezsaurusEntity.class,
                BeelzebufoEntity.class,
                CompsognathusEntity.class,
                CoelacanthEntity.class,
                MicroceratusEntity.class,
                MicroraptorEntity.class,
                MegapiranhaEntity.class,
                LeptictidiumEntity.class,
                LudodactylusEntity.class,
                OthnieliaEntity.class,
                OviraptorEntity.class,
                ProtoceratopsEntity.class,
                SegisaurusEntity.class,
                TroodonEntity.class,
                ZhenyuanopterusEntity.class,
                CrassigyrinusEntity.class,
                DiplocaulusEntity.class,
                DimorphodonEntity.class,
                Player.class
                , Animal.class,
                Villager.class);
    }
        @Override
        public SoundEvent getSoundForAnimation(Animation animation)
        {
            switch (EntityAnimation.getAnimation(animation))
            {
                case SPEAK:
                    return SoundHandler.CEARADACTYLUS_LIVING;
                case DYING:
                    return SoundHandler.CEARADACTYLUS_DEATH;
                case INJURED:
                    return SoundHandler.CEARADACTYLUS_HURT;
                case CALLING:
                    return SoundHandler.CEARADACTYLUS_CALL;
                case BEGGING:
                    return SoundHandler.CEARADACTYLUS_THREAT;
            }

            return null;
        }
    }
