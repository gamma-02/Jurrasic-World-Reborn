package net.gamma02.jurassicworldreborn.client.render.entity.animation.entity;

import net.gamma02.jurassicworldreborn.client.model.AnimatableModel;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimator;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.VelociraptorCharlieEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)

public class VelociraptorCharlieAnimator extends EntityAnimator<VelociraptorCharlieEntity> {

    protected void performAnimations(AnimatableModel parModel, VelociraptorCharlieEntity entity, float limbSwing, float limbSwingAmount, float ticks, float rotationYaw, float rotationPitch, float scale) {
    }
}