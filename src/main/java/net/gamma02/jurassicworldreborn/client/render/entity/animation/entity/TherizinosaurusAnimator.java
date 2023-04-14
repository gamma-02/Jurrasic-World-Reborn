package net.gamma02.jurassicworldreborn.client.render.entity.animation.entity;

import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import net.gamma02.jurassicworldreborn.client.model.AnimatableModel;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimator;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.TherizinosaurusEntity;

@OnlyIn(Dist.CLIENT)
public class TherizinosaurusAnimator extends EntityAnimator<TherizinosaurusEntity>
{
    @Override
    protected void performAnimations(AnimatableModel model, TherizinosaurusEntity entity, float f, float f1, float ticks, float rotationYaw, float rotationPitch, float scale)
    {
        AdvancedModelBox rightThigh = model.getCube("Right Thigh");
        AdvancedModelBox bodyHips = model.getCube("Body hips");
        AdvancedModelBox rightCalf1 = model.getCube("Right Calf 1");
        AdvancedModelBox rightCalf2 = model.getCube("Right Calf 2");
        AdvancedModelBox footRight = model.getCube("Foot Right");
        AdvancedModelBox tail1 = model.getCube("Tail 1");
        AdvancedModelBox bodyMain = model.getCube("Body main");
        AdvancedModelBox tail2 = model.getCube("Tail 2");
        AdvancedModelBox tail3 = model.getCube("Tail 3");
        AdvancedModelBox tail2Feathers = model.getCube("Tail 2 feathers");
        AdvancedModelBox tail4 = model.getCube("Tail 4");
        AdvancedModelBox tail3Feathers = model.getCube("Tail 3 feathers");
        AdvancedModelBox tail5 = model.getCube("Tail 5");
        AdvancedModelBox tail4Feathers = model.getCube("Tail 4 feathers");
        AdvancedModelBox tail6 = model.getCube("Tail 6");
        AdvancedModelBox tail5Feathers = model.getCube("Tail 5 feathers");
        AdvancedModelBox tail6Feathers = model.getCube("Tail 6 feathers");
        AdvancedModelBox tail6FeathersR = model.getCube("Tail 6 feathers r");
        AdvancedModelBox tail6FeathersL = model.getCube("Tail 6 feathers l");
        AdvancedModelBox tail5FeathersR = model.getCube("Tail 5 feathers r");
        AdvancedModelBox tail5FeathersL = model.getCube("Tail 5 feathers l");
        AdvancedModelBox tail4FeathersR = model.getCube("Tail 4 feathers r");
        AdvancedModelBox tail4FeathersL = model.getCube("Tail 4 feathers l");
        AdvancedModelBox tail3FeathersR = model.getCube("Tail 3 feathers r");
        AdvancedModelBox tail3FeathersL = model.getCube("Tail 3 feathers l");
        AdvancedModelBox bodyShoulders = model.getCube("Body shoulders");
        AdvancedModelBox bodyMain1 = model.getCube("Body main 1");
        AdvancedModelBox neckBase = model.getCube("Neck base");
        AdvancedModelBox neck1 = model.getCube("Neck 1");
        AdvancedModelBox neck2 = model.getCube("Neck 2");
        AdvancedModelBox neck3 = model.getCube("Neck 3");
        AdvancedModelBox neck4 = model.getCube("Neck 4");
        AdvancedModelBox neck5 = model.getCube("Neck 5");
        AdvancedModelBox neck4feathers = model.getCube("Neck 4 feathers");
        AdvancedModelBox neck6 = model.getCube("Neck 6");
        AdvancedModelBox neck5Feathers = model.getCube("Neck 5 feathers");
        AdvancedModelBox neck7 = model.getCube("Neck 7");
        AdvancedModelBox neck6Feathers = model.getCube("Neck 6 feathers");
        AdvancedModelBox head = model.getCube("Head");
        AdvancedModelBox neck7Feathers = model.getCube("Neck 7 feathers");
        AdvancedModelBox snout = model.getCube("Snout");
        AdvancedModelBox lowerJaw = model.getCube("Lower Jaw");
        AdvancedModelBox snoutRoof = model.getCube("Snout roof");
        AdvancedModelBox upperJaw = model.getCube("Upper Jaw");
        AdvancedModelBox neck7FeathersR = model.getCube("Neck 7 feathers r");
        AdvancedModelBox neck7FeathersL = model.getCube("Neck 7 feathers l");
        AdvancedModelBox neck6FeathersR = model.getCube("Neck 6 feathers r");
        AdvancedModelBox neck6FeathersL = model.getCube("Neck 6 feathers l");
        AdvancedModelBox neck5FeathersR = model.getCube("Neck 5 feathers r");
        AdvancedModelBox neck5FeathersL = model.getCube("Neck 5 feathers l");
        AdvancedModelBox neck4FeathersR = model.getCube("Neck 4 feathers r");
        AdvancedModelBox neck4FeathersL = model.getCube("Neck 4 feathers l");
        AdvancedModelBox lowerArmRight = model.getCube("Lower Arm Right");
        AdvancedModelBox lowerArmRight1 = model.getCube("Lower Arm Right 1");
        AdvancedModelBox rightHand = model.getCube("Right hand");
        AdvancedModelBox armRightFeathers = model.getCube("Arm right feathers");
        AdvancedModelBox rightFinger1 = model.getCube("Right finger 1");
        AdvancedModelBox rightFinger2 = model.getCube("Right finger 2");
        AdvancedModelBox righFinger3 = model.getCube("Right finger 3");
        AdvancedModelBox rF1mid = model.getCube("RF1 mid");
        AdvancedModelBox rF1end = model.getCube("RF1 end");
        AdvancedModelBox rF2mid = model.getCube("RF2 mid");
        AdvancedModelBox rF2end = model.getCube("RF2 end");
        AdvancedModelBox rF3mid = model.getCube("RF3 mid");
        AdvancedModelBox rF3end = model.getCube("RF3 end");
        AdvancedModelBox lowerArmLeft = model.getCube("Lower Arm LEFT");
        AdvancedModelBox lowerArmLeft1 = model.getCube("Lower Arm LEFT 1");
        AdvancedModelBox leftHand = model.getCube("Left hand");
        AdvancedModelBox armLeftFeathers = model.getCube("Arm left feathers");
        AdvancedModelBox leftfinger1 = model.getCube("Left finger 1");
        AdvancedModelBox leftfinger2 = model.getCube("Left finger 2");
        AdvancedModelBox leftfinger3 = model.getCube("Left finger 3");
        AdvancedModelBox lF1mid = model.getCube("LF1 mid");
        AdvancedModelBox lF1end = model.getCube("LF1 end");
        AdvancedModelBox lF2mid = model.getCube("LF1 end");
        AdvancedModelBox lF2end = model.getCube("LF2 end");
        AdvancedModelBox lF3mid = model.getCube("LF3 mid");
        AdvancedModelBox lF3end = model.getCube("LF3 mid");
        AdvancedModelBox leftThigh = model.getCube("Left Thigh");
        AdvancedModelBox leftCalf1 = model.getCube("Left Calf 1");
        AdvancedModelBox leftCalf2 = model.getCube("Left Calf 2");
        AdvancedModelBox footLeft = model.getCube("Foot Left");

        AdvancedModelBox[] neck = new AdvancedModelBox[] { head, neck7, neck6, neck5, neck4, neck3, neck2, neck1, neckBase, bodyShoulders };
        AdvancedModelBox[] tail = new AdvancedModelBox[] { tail1, tail2, tail3, tail4, tail5, tail6 };
        AdvancedModelBox[] armLeft = new AdvancedModelBox[] { lowerArmLeft, lowerArmLeft1, leftHand };
        AdvancedModelBox[] armRight = new AdvancedModelBox[] { lowerArmRight, lowerArmRight1, rightHand };

        // The tail must always be up when the neck is down

     //   model.bob(bodyHips, 1F * speed, height, false, f, f1);
      //  model.flap(bodyHips, 0.5F * speed, 0.5F, false, 0, 0, f, f1);
      //  model.flap(bodyMain, 0.5F * speed, 0.1F, true, 0, 0, f, f1);
      //  model.flap(bodyShoulders, 0.5F * speed, 0.4F, true, 0, 0, f, f1);
      //  model.flap(tail1, 0.5F * speed, 0.2F, true, 0, 0, f, f1);
      //  model.flap(tail3, 0.5F * speed, 0.2F, true, 0, 0, f, f1);
      //  model.flap(tail5, 0.5F * speed, 0.1F, true, 0, 0, f, f1);
      //  model.bob(leftThigh, 1F * speed, height, false, f, f1);
     //   model.bob(rightThigh, 1F * speed, height, false, f, f1);
   //     model.walk(bodyShoulders, 1F * speed, 0.2F, true, 1, 0, f, f1);
     //   model.walk(bodyMain1, 1F * speed, 0.2F, false, 0.5F, 0, f, f1);

     //   model.walk(leftThigh, 0.5F * speed, 0.7F, false, 3.14F, 0.2F, f, f1);
    //    model.walk(leftCalf1, 0.5F * speed, 0.6F, false, 1.5F, 0.3F, f, f1);
    //    model.walk(leftCalf2, 0.5F * speed, 0.8F, false, -1F, -0.1F, f, f1);
    //    model.walk(footLeft, 0.5F * speed, 1.5F, true, -1F, 1F, f, f1);

     //   model.walk(rightThigh, 0.5F * speed, 0.7F, true, 3.14F, 0.2F, f, f1);
      //  model.walk(rightCalf1, 0.5F * speed, 0.6F, true, 1.5F, 0.3F, f, f1);
     //   model.walk(rightCalf2, 0.5F * speed, 0.8F, true, -1F, -0.1F, f, f1);
       // model.walk(footRight, 0.5F * speed, 1.5F, false, -1F, 1F, f, f1);

   //     model.chainSwing(tail, 0.5F * speed, -0.02F, 2, f, f1);
   //     model.chainWave(tail, 1F * speed, -0.02F, 2.5F, f, f1);
    //    model.chainSwing(neck, 0.5F * speed, 0.02F, 2, f, f1);
    //    model.chainWave(neck, 1.0F * speed, 0.02F, 0.5F, f, f1);

    //    model.chainWave(armRight, 1F * speed, -0.3F, 4, f, f1);
    //    model.chainWave(armLeft, 1F * speed, -0.3F, 4, f, f1);

     //   model.chainWave(tail, 0.1F, 0.02F, 2, ticks, 0.25F);
      //  model.chainWave(neck, 0.1F, 0.02F, 2, ticks, 0.25F);
      //  model.chainWave(armRight, 0.1F, -0.1F, 4, ticks, 0.25F);
     //   model.chainWave(armLeft, 0.1F, -0.1F, 4, ticks, 0.25F);

        entity.tailBuffer.applyChainSwingBuffer(tail);
    }
}
