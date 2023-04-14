package net.gamma02.jurassicworldreborn.client.render.entity.animation.entity;

import net.gamma02.jurassicworldreborn.client.model.AnimatableModel;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimator;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.SpinosaurusEntity;

@OnlyIn(Dist.CLIENT)
public class SpinosaurusAnimator extends EntityAnimator<SpinosaurusEntity>
{
    protected void performAnimations(AnimatableModel parModel, SpinosaurusEntity entity, float limbSwing, float limbSwingAmount, float ticks, float rotationYaw, float rotationPitch, float scale) {
    }
}
