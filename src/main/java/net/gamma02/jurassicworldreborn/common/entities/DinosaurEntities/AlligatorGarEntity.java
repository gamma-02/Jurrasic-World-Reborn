package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import mod.reborn.server.entity.SwimmingDinosaurEntity;

import com.github.alexthe666.citadel.animation.Animation;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

import javax.vecmath.Vector3f;

public class AlligatorGarEntity extends SwimmingDinosaurEntity
{
    public AlligatorGarEntity(Level world) {
        super(world);
        this.target(EntitySquid.class);
    }

    @Override
    public SoundEvent getSoundForAnimation(Animation animation) {
        return null;
    }

    @Override
    public Vector3f getDinosaurCultivatorRotation() {
        return new Vector3f(-90F, 0, 0);
    }
}