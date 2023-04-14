package net.gamma02.jurassicworldreborn.client.render.entity.animation.entity;

import net.gamma02.jurassicworldreborn.client.model.AnimatableModel;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimator;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.DreadnoughtusEntity;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DreadnoughtusAnimator extends EntityAnimator<DreadnoughtusEntity> {

    protected void performAnimations(AnimatableModel parModel, DreadnoughtusEntity entity, float limbSwing, float limbSwingAmount, float ticks, float rotationYaw, float rotationPitch, float scale) {
    }
}
