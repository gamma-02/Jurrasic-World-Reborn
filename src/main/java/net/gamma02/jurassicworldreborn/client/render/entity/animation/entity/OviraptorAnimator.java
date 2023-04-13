package net.gamma02.jurassicworldreborn.client.render.entity.animation.entity;

import net.gamma02.jurassicworldreborn.client.model.AnimatableModel;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimator;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import mod.reborn.server.entity.dinosaur.OviraptorEntity;

@OnlyIn(Dist.CLIENT)
public class OviraptorAnimator extends EntityAnimator<OviraptorEntity>
{
    @Override
    protected void performAnimations(AnimatableModel model, OviraptorEntity entity, float f, float f1, float ticks, float rotationYaw, float rotationPitch, float scale)
    {
        AdvancedModelBox body = model.getCube("Body ALL");
        AdvancedModelBox tail1 = model.getCube("Tail Base");
        AdvancedModelBox tail2 = model.getCube("Tail Mid 1");
        AdvancedModelBox tail3 = model.getCube("Tail Mid 2");
        AdvancedModelBox tail4 = model.getCube("Tail Tip");
        AdvancedModelBox leftArm1 = model.getCube("Arm Top Left");
        AdvancedModelBox leftArm2 = model.getCube("Arm Mid Left");
        AdvancedModelBox leftArm3 = model.getCube("Arm Feather Left");
        AdvancedModelBox rightArm1 = model.getCube("Arm Top Right");
        AdvancedModelBox rightArm2 = model.getCube("Arm Mid Right");
        AdvancedModelBox rightArm3 = model.getCube("Arm Feather Right");
        AdvancedModelBox leftLeg1 = model.getCube("Leg Top Left");
        AdvancedModelBox rightLeg1 = model.getCube("Leg Top Right");
        AdvancedModelBox neck1 = model.getCube("Neck Base");
        AdvancedModelBox head = model.getCube("Upper Head");

        AdvancedModelBox[] tail = {tail4, tail3, tail2, tail1};
        AdvancedModelBox[] leftArm = {leftArm3, leftArm2, leftArm1};
        AdvancedModelBox[] rightArm = {rightArm3, rightArm2, rightArm1};
        AdvancedModelBox[] mainBody = {body, neck1, head};

        float globalSpeed = 1.0F;
        float globalDegree = 1.0F;

        model.bob(body,  globalSpeed * 0.1F,  globalDegree * 0.3F, false, f,f1);
     //   model.walk(rightLeg1, globalSpeed * 0.5F, globalDegree * 1.0F, false, (float)Math.PI, 1, f, f1);
     //   model.walk(leftLeg1, globalSpeed * 0.5F, globalDegree * 1.0F, true, (float)Math.PI,1, f, f1);

        model.chainWave(tail, 0.13F,0.45F,Math.PI - 1, f + ticks, f1 + 0.25F);
        model.chainWave(mainBody, 0.1F, -0.1F, Math.PI + 2, ticks, 0.25F);
        model.chainWave(rightArm, 0.1F, -0.1F, Math.PI + 1, ticks, 0.25F);
        model.chainWave(leftArm, 0.1F, -0.1F, Math.PI + 1, ticks, 0.25F);

        model.faceTarget(rotationYaw, rotationPitch, 2.0F, neck1, head);
        entity.tailBuffer.applyChainSwingBuffer(tail);
    }
}
