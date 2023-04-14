package net.gamma02.jurassicworldreborn.client.render.entity.animation.entity;

import net.gamma02.jurassicworldreborn.client.model.AnimatableModel;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimator;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.SinoceratopsEntity;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SinoceratopsAnimator extends EntityAnimator<SinoceratopsEntity> {

    @Override
    protected void performAnimations(AnimatableModel model, SinoceratopsEntity entity, float limbSwing, float limbSwingAmount, float ticks, float rotationYaw, float rotationPitch, float scale) {
    }
}
