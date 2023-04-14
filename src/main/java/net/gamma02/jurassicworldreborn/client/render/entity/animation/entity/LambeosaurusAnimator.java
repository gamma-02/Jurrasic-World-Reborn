package net.gamma02.jurassicworldreborn.client.render.entity.animation.entity;

import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import net.gamma02.jurassicworldreborn.client.model.AnimatableModel;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimator;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.LambeosaurusEntity;
@OnlyIn(Dist.CLIENT)
public class LambeosaurusAnimator extends EntityAnimator<LambeosaurusEntity>
{
    @Override
    protected void performAnimations(AnimatableModel model, LambeosaurusEntity entity, float f, float f1, float ticks, float rotationYaw, float rotationPitch, float scale)
    {
        AdvancedModelBox head = model.getCube("Head");
        AdvancedModelBox neck1 = model.getCube("Neck");
        AdvancedModelBox neck2 = model.getCube("Neck 2");

        // body parts
        AdvancedModelBox stomach = model.getCube("Body 1");
        AdvancedModelBox shoulders = model.getCube("Body 2");
        AdvancedModelBox waist = model.getCube("Body 3");

        // tail parts
        AdvancedModelBox tail1 = model.getCube("Tail 1");
        AdvancedModelBox tail2 = model.getCube("Tail 2");
        AdvancedModelBox tail3 = model.getCube("Tail 3");
        AdvancedModelBox tail4 = model.getCube("Tail 4");
        AdvancedModelBox tail5 = model.getCube("Tail 5");
        AdvancedModelBox tail6 = model.getCube("Tail 6");

        // left foot
        AdvancedModelBox leftThigh = model.getCube("Left Thigh");
        AdvancedModelBox leftCalf = model.getCube("Left Calf 1");
        AdvancedModelBox leftUpperFoot = model.getCube("Left Upper Foot");
        AdvancedModelBox leftFoot = model.getCube("Foot Left");

        // right foot
        AdvancedModelBox rightThigh = model.getCube("Right Thigh");
        AdvancedModelBox rightCalf = model.getCube("Right Calf 1");
        AdvancedModelBox rightUpperFoot = model.getCube("Right Upper Foot");
        AdvancedModelBox rightFoot = model.getCube("Foot Right");

        // right arm
        AdvancedModelBox upperArmRight = model.getCube("Upper Arm Right");
        AdvancedModelBox lowerArmRight = model.getCube("Lower Arm Right");
        AdvancedModelBox rightHand = model.getCube("Right Hand");
        AdvancedModelBox rightFingers = model.getCube("Right Fingers");

        // left arm
        AdvancedModelBox upperArmLeft = model.getCube("Upper Arm Left");
        AdvancedModelBox lowerArmLeft = model.getCube("Lower Arm Left");
        AdvancedModelBox leftHand = model.getCube("Left Hand");
        AdvancedModelBox leftFingers = model.getCube("Left Fingers");

        AdvancedModelBox jaw = model.getCube("Jaw");

        AdvancedModelBox[] tail = new AdvancedModelBox[] { tail6, tail5, tail4, tail3, tail2, tail1 };

        float scaleFactor = 0.6F;
        float height = 2F;

      //  model.bob(waist, 1F * scaleFactor, 0.2F * height, false, f, f1);
     //   model.bob(leftThigh, 1F * scaleFactor, 1F * height, false, f, f1);
     //   model.bob(rightThigh, 1F * scaleFactor, 1F * height, false, f, f1);

     //   model.walk(neck1, 1F * scaleFactor, 0.15F * height, false, 1F, 0F, f, f1);
      //  model.walk(head, 1F * scaleFactor, 0.15F * height, true, 1F, 0F, f, f1);

     //   model.walk(leftThigh, 0.5F * scaleFactor, 0.5F, false, 0F, 0.3F, f, f1);
     //   model.walk(leftCalf, 0.5F * scaleFactor, 0.5F, true, 2F, 0F, f, f1);
     //   model.walk(leftUpperFoot, 0.5F * scaleFactor, 0.7F, false, 0F, -0.4F, f, f1);
     //   model.walk(leftFoot, 0.5F * scaleFactor, 1F, true, 0.5F, 1F, f, f1);

      //  model.walk(rightThigh, 0.5F * scaleFactor, 0.5F, true, 0F, 0.3F, f, f1);
      //  model.walk(rightCalf, 0.5F * scaleFactor, 0.5F, false, 2F, 0F, f, f1);
      //  model.walk(rightUpperFoot, 0.5F * scaleFactor, 0.7F, true, 0F, -0.4F, f, f1);
      //  model.walk(rightFoot, 0.5F * scaleFactor, 1F, false, 0.5F, 1F, f, f1);

        float frontOffset = 1.1F;
     //   model.walk(upperArmLeft, 0.5F * scaleFactor, 1F, false, -0.5F - frontOffset, 0F, f, f1);
     //   model.walk(lowerArmLeft, 0.5F * scaleFactor, 1F, true, -1F - frontOffset, 0F, f, f1);
     //   model.walk(leftHand, 0.5F * scaleFactor, 0.5F, false, -1F - frontOffset, 0F, f, f1);

       // model.walk(upperArmRight, 0.5F * scaleFactor, 1F, true, -0.5F - frontOffset, 0F, f, f1);
     //   model.walk(lowerArmRight, 0.5F * scaleFactor, 1F, false, -1F - frontOffset, 0F, f, f1);
     //   model.walk(rightHand, 0.5F * scaleFactor, 0.5F, true, -1F - frontOffset, 0F, f, f1);

        model.chainWave(tail, 1F * scaleFactor, -0.1F, 2, f, f1);
        model.chainSwing(tail, 0.5F * scaleFactor, 0.1F, 2, f, f1);

     //   model.walk(neck1, 0.1F, 0.07F, false, -1F, 0F, ticks, 0.25F);
     //   model.walk(head, 0.1F, 0.07F, true, 0F, 0F, ticks, 0.25F);
        model.walk(waist, 0.1F, 0.02F, false, 0F, 0F, ticks, 0.25F);
      //  model.walk(upperArmRight, 0.1F, 0.1F, false, -1F, 0F, ticks, 0.25F);
      //  model.walk(upperArmLeft, 0.1F, 0.1F, false, -1F, 0F, ticks, 0.25F);
      //  model.walk(lowerArmRight, 0.1F, 0.1F, true, -1.5F, 0F, ticks, 0.25F);
       // model.walk(lowerArmLeft, 0.1F, 0.1F, true, -1.5F, 0F, ticks, 0.25F);
      //  model.walk(rightHand, 0.1F, 0.1F, false, -2F, 0F, ticks, 0.25F);
       // model.walk(leftHand, 0.1F, 0.1F, false, -2F, 0F, ticks, 0.25F);

        model.chainWave(tail, 0.1F, -0.02F, 2, ticks, 1F);

        entity.tailBuffer.applyChainSwingBuffer(tail);
    }
}