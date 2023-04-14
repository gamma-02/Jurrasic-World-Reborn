package net.gamma02.jurassicworldreborn.client.render.entity.animation.entity;

import net.gamma02.jurassicworldreborn.client.model.AnimatableModel;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimator;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.VelociraptorDeltaEntity;

@OnlyIn(Dist.CLIENT)

public class VelociraptorDeltaAnimator extends EntityAnimator<VelociraptorDeltaEntity> {

    protected void performAnimations(AnimatableModel parModel, VelociraptorDeltaEntity entity, float limbSwing, float limbSwingAmount, float ticks, float rotationYaw, float rotationPitch, float scale) {
    }
}