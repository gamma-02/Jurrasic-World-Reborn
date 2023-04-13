package net.gamma02.jurassicworldreborn.client.render.entity.animation.entity;

import net.gamma02.jurassicworldreborn.client.model.AnimatableModel;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimator;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import mod.reborn.server.entity.dinosaur.TriceratopsEntity;

@OnlyIn(Dist.CLIENT)
public class TriceratopsAnimator extends EntityAnimator<TriceratopsEntity> {
    @Override
    protected void performAnimations(AnimatableModel model, TriceratopsEntity entity, float f, float f1, float ticks, float rotationYaw, float rotationPitch, float scale) {
        AdvancedModelBox head = model.getCube("Head");
        AdvancedModelBox neck3 = model.getCube("Neck 3");
        AdvancedModelBox neck2 = model.getCube("Neck 2");
        AdvancedModelBox neck1 = model.getCube("Neck 1");
        AdvancedModelBox shoulders = model.getCube("Body shoulders");
        AdvancedModelBox main = model.getCube("Body MAIN");
        AdvancedModelBox waist = model.getCube("Body hips");
        AdvancedModelBox tail1 = model.getCube("Tail 1");
        AdvancedModelBox tail2 = model.getCube("Tail 2");
        AdvancedModelBox tail3 = model.getCube("Tail 3");
        AdvancedModelBox tail4 = model.getCube("Tail 4");
        AdvancedModelBox tail5 = model.getCube("Tail 5");
        AdvancedModelBox tail6 = model.getCube("Tail 6");

        AdvancedModelBox frontLeftThigh = model.getCube("FrontLeg Upper Left");
        AdvancedModelBox frontLeftCalf = model.getCube("FrontLeg MID Left");
        AdvancedModelBox frontLeftFoot = model.getCube("FrontLeg FOOT Left");
        AdvancedModelBox frontRightThigh = model.getCube("FrontLeg Upper Right");
        AdvancedModelBox frontRightCalf = model.getCube("FrontLeg MID Right");
        AdvancedModelBox frontRightFoot = model.getCube("FrontLeg FOOT Right");
        AdvancedModelBox backLeftThigh = model.getCube("RearLeg Upper Left");
        AdvancedModelBox backRightThigh = model.getCube("RearLeg Upper Right");
        AdvancedModelBox backLeftCalf = model.getCube("RearLeg Middle Left");
        AdvancedModelBox backRightCalf = model.getCube("RearLeg Middle Right");

        AdvancedModelBox[] tail = new AdvancedModelBox[] { tail6, tail5, tail4, tail3, tail2, tail1 };
        AdvancedModelBox[] body = new AdvancedModelBox[] { head, neck3, neck2, neck1, shoulders, main, waist };

        float globalSpeed = 1.0F;
        float globalDegree = 0.8F;

        model.bob(waist, globalSpeed * 0.5F, globalDegree * 1.5F, false, f, f1);
        model.bob(backRightThigh, globalSpeed * 0.5F, globalDegree * 1.5F, false, f, f1);
        model.bob(backLeftThigh, globalSpeed * 0.5F, globalDegree * 1.5F, false, f, f1);

        model.chainWave(tail, globalSpeed * 0.25F, globalDegree * 0.1F, 1, f, f1);
        model.chainSwing(tail, globalSpeed * 0.25F, globalDegree * 0.25F, 2, f, f1);
        model.chainWave(body, globalSpeed * 0.25F, globalDegree * 0.05F, 3, f, f1);

        model.walk(neck1, 0.1F, 0.07F, false, -1F, 0F, ticks, 1F);
        model.walk(head, 0.1F, 0.07F, true, 0F, 0F, ticks, 1F);
        model.walk(waist, 0.1F, 0.025F, false, 0F, 0F, ticks, 1F);

        float inverseKinematicsConstant = 0.3F;
        model.walk(frontRightThigh, 0.1F, 0.1F * inverseKinematicsConstant, false, 0F, 0F, ticks, 0.25F);
        model.walk(frontRightCalf, 0.1F, 0.3F * inverseKinematicsConstant, true, 0F, 0F, ticks, 0.25F);
        model.walk(frontRightFoot, 0.1F, 0.175F * inverseKinematicsConstant, false, 0F, 0F, ticks, 0.25F);
        model.walk(frontLeftThigh, 0.1F, 0.1F * inverseKinematicsConstant, false, 0F, 0F, ticks, 0.25F);
        model.walk(frontLeftCalf, 0.1F, 0.3F * inverseKinematicsConstant, true, 0F, 0F, ticks, 0.25F);
        model.walk(frontLeftFoot, 0.1F, 0.175F * inverseKinematicsConstant, false, 0F, 0F, ticks, 0.25F);
        frontRightThigh.rotationPointZ -= 0.5 * Math.cos(ticks * 0.025F);
        frontLeftThigh.rotationPointZ -= 0.5 * Math.cos(ticks * 0.025F);

        float delta = Minecraft.getMinecraft().getRenderPartialTicks();
        LegArticulator.articulateQuadruped(entity, entity.legSolver, waist, neck1,
                backLeftThigh, backLeftCalf, backRightThigh, backRightCalf, frontLeftThigh, frontLeftCalf, frontRightThigh, frontRightCalf,
                0.5F, 0.8F, -0.6F, -1.1F,
                delta
        );

        model.chainSwing(tail, 0.1F, 0.05F, 2, ticks, 0.25F);
        model.chainWave(tail, 0.1F, -0.05F, 1, ticks, 0.25F);

        model.faceTarget(rotationYaw, rotationPitch, 1.0F, neck1, neck2, neck3, head);

        entity.tailBuffer.applyChainSwingBuffer(tail);
    }
}
