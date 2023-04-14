package net.gamma02.jurassicworldreborn.client.render.entity.animation.entity;

import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import net.gamma02.jurassicworldreborn.client.model.AnimatableModel;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimator;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.SuchomimusEntity;



public class SuchomimusAnimator extends EntityAnimator<SuchomimusEntity> {

    protected void performAnimations(AnimatableModel parModel, SuchomimusEntity entity, float limbSwing, float limbSwingAmount, float ticks, float rotationYaw, float rotationPitch, float scale) {

        AdvancedModelBox tail1 = parModel.getCube("Tail Base");
        AdvancedModelBox tail2 = parModel.getCube("Tail 2");
        AdvancedModelBox tail3 = parModel.getCube("Tail 3");
        AdvancedModelBox tail4 = parModel.getCube("Tail 4");
        AdvancedModelBox tail5 = parModel.getCube("Tail 6");
        AdvancedModelBox neck1 = parModel.getCube("Neck Base");
        AdvancedModelBox head = parModel.getCube("Head");

        AdvancedModelBox[] tail = {tail5, tail4, tail3, tail2, tail1};

        float globalSpeed = 0.5F;
        float globalDegree = 0.5F;

        parModel.chainWave(tail, globalSpeed * 0.5F, globalDegree * 0.05F, 1, limbSwing, limbSwingAmount);

        parModel.faceTarget(rotationYaw, rotationPitch, 2.0F, neck1, head);
        entity.tailBuffer.applyChainSwingBuffer(tail);
    }
}
