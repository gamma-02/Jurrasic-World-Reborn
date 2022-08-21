package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.common.entities.SwimmingDinosaurEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.level.Level;

public class CoelacanthEntity extends SwimmingDinosaurEntity {
    public CoelacanthEntity(Level world) {
        super(world);
        this.target(Squid.class, MegapiranhaEntity.class);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
    }


    @Override
    public SoundEvent getSoundForAnimation(Animation animation) {
        return null;
    }
}