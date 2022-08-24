package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import com.mojang.math.Vector3f;
import net.gamma02.jurassicworldreborn.common.entities.SwimmingDinosaurEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.level.Level;



public class AlligatorGarEntity extends SwimmingDinosaurEntity
{
    public AlligatorGarEntity(Level world) {
        super(world);
        this.target(Squid.class);
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