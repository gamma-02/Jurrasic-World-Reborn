package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import mod.reborn.server.entity.SwimmingDinosaurEntity;
import com.github.alexthe666.citadel.animation.Animation;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

public class CoelacanthEntity extends SwimmingDinosaurEntity {
    public CoelacanthEntity(Level world) {
        super(world);
        this.target(EntitySquid.class, MegapiranhaEntity.class);
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