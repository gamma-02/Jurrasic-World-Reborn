package net.gamma02.jurassicworldreborn.client.render.entity.animation.entity;

import net.gamma02.jurassicworldreborn.client.model.AnimatableModel;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimator;
import mod.reborn.server.entity.dinosaur.DiplodocusEntity;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import mod.reborn.server.entity.dinosaur.AchillobatorEntity;

@OnlyIn(Dist.CLIENT)

public class DiplodocusAnimator extends EntityAnimator<DiplodocusEntity> {

    protected void performAnimations(AnimatableModel parModel, DiplodocusEntity entity, float limbSwing, float limbSwingAmount, float ticks, float rotationYaw, float rotationPitch, float scale) {
    }
}