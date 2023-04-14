package net.gamma02.jurassicworldreborn.client.render.entity.animation.entity;

import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import net.gamma02.jurassicworldreborn.client.model.AnimatableModel;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimator;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.DilophosaurusEntity;

@OnlyIn(Dist.CLIENT)
public class DilophosaurusAnimator extends EntityAnimator<DilophosaurusEntity> {
    @Override
    protected void performAnimations(AnimatableModel model, DilophosaurusEntity entity, float limbSwing, float limbSwingAmount, float ticks, float rotationYaw, float rotationPitch, float scale) {
        AdvancedModelBox frillLeftBottom = model.getCube("Frill Lower Left");
        AdvancedModelBox frillLeftTop = model.getCube("Frill Upper Left");

        AdvancedModelBox frillRightBottom = model.getCube("Frill Lower Right");
        AdvancedModelBox frillRightTop = model.getCube("Frill Upper Right");

        boolean hasTarget = entity.hasTarget() && !entity.isCarcass();

        frillLeftBottom.showModel = hasTarget;
        frillLeftTop.showModel = hasTarget;
        frillRightBottom.showModel = hasTarget;
        frillRightTop.showModel = hasTarget;

        AdvancedModelBox head = model.getCube("Head");

        AdvancedModelBox neck1 = model.getCube("Neck Base");
        AdvancedModelBox neck2 = model.getCube("Neck 2");
        AdvancedModelBox neck3 = model.getCube("Neck 3");
        AdvancedModelBox neck4 = model.getCube("Neck 4");
        AdvancedModelBox neck5 = model.getCube("Neck 5");
        AdvancedModelBox neck6 = model.getCube("Neck 6");

        AdvancedModelBox body1 = model.getCube("Body FRONT");
        AdvancedModelBox body2 = model.getCube("Body MIDDLE");
        AdvancedModelBox body3 = model.getCube("Body REAR");

        AdvancedModelBox tail1 = model.getCube("Tail BASE");
        AdvancedModelBox tail2 = model.getCube("Tail 2");
        AdvancedModelBox tail3 = model.getCube("Tail 3");
        AdvancedModelBox tail4 = model.getCube("Tail 4");
        AdvancedModelBox tail5 = model.getCube("Tail 5");
        AdvancedModelBox tail6 = model.getCube("Tail 6");

        AdvancedModelBox rightThigh = model.getCube("Leg Right UPPER");
        AdvancedModelBox leftThigh = model.getCube("Leg Left UPPER");

        AdvancedModelBox upperArmRight = model.getCube("Right arm");
        AdvancedModelBox upperArmLeft = model.getCube("Left arm");

        AdvancedModelBox lowerArmRight = model.getCube("Right forearm");
        AdvancedModelBox lowerArmLeft = model.getCube("Left forearm");

        AdvancedModelBox handRight = model.getCube("Right hand");
        AdvancedModelBox handLeft = model.getCube("Left hand");

        AdvancedModelBox[] bodyParts = new AdvancedModelBox[] { head, neck6, neck5, neck4, neck3, neck2, neck1, body1, body2, body3 };
        AdvancedModelBox[] tailParts = new AdvancedModelBox[] { tail6, tail5, tail4, tail3, tail2, tail1 };

        AdvancedModelBox[] armRight = new AdvancedModelBox[] { handRight, lowerArmRight, upperArmRight };
        AdvancedModelBox[] armLeft = new AdvancedModelBox[] { handLeft, lowerArmLeft, upperArmLeft };

        float globalSpeed = 1.0F;
        float globalDegree = 1.0F;

        model.bob(body3, globalSpeed * 0.5F, globalDegree * 1.0F, false, limbSwing, limbSwingAmount);
        model.bob(rightThigh, globalSpeed * 0.5F, globalDegree * 1.0F, false, limbSwing, limbSwingAmount);
        model.bob(leftThigh, globalSpeed * 0.5F, globalDegree * 1.0F, false, limbSwing, limbSwingAmount);

        model.chainWave(tailParts, globalSpeed * 0.5F, globalDegree * 0.05F, 1, limbSwing, limbSwingAmount);
        model.chainSwing(tailParts, globalSpeed * 0.5F, globalDegree * 0.1F, 2, limbSwing, limbSwingAmount);
        model.chainWave(bodyParts, globalSpeed * 0.5F, globalDegree * 0.025F, 3, limbSwing, limbSwingAmount);

        model.chainWave(tailParts, 0.15F, -0.03F, 2, ticks, 0.25F);
        model.chainWave(bodyParts, 0.15F, 0.03F, 3.5F, ticks, 0.25F);
        model.chainWave(armRight, 0.15F, -0.1F, 4, ticks, 0.25F);
        model.chainWave(armLeft, 0.15F, -0.1F, 4, ticks, 0.25F);
        model.chainSwing(tailParts, 0.15F, -0.1F, 3, ticks, 0.25F);

        model.faceTarget(rotationYaw, rotationPitch, 1.0F, neck1, head);

        entity.tailBuffer.applyChainSwingBuffer(tailParts);
    }
}
