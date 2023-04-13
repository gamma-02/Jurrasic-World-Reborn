package net.gamma02.jurassicworldreborn.client.render.entity.animation.entity;

import net.gamma02.jurassicworldreborn.client.model.AnimatableModel;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimator;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import mod.reborn.server.entity.dinosaur.TroodonEntity;

@OnlyIn(Dist.CLIENT)
public class TroodonAnimator extends EntityAnimator<TroodonEntity>
{
    @Override
    protected void performAnimations(AnimatableModel model, TroodonEntity entity, float f, float f1, float ticks, float rotationYaw, float rotationPitch, float scale)
    {
        AdvancedModelBox head = model.getCube("head UPPER");
        AdvancedModelBox neck1 = model.getCube("neck1");
        AdvancedModelBox neck2 = model.getCube("neck2");
        AdvancedModelBox neck3 = model.getCube("neck3");
        AdvancedModelBox neck4 = model.getCube("neck4");

        AdvancedModelBox lowerJaw = model.getCube("Jaw LOWER");

        AdvancedModelBox waist = model.getCube("body3");
        AdvancedModelBox chest = model.getCube("body2");
        AdvancedModelBox shoulders = model.getCube("body1");

        AdvancedModelBox tail1 = model.getCube("tail1");
        AdvancedModelBox tail2 = model.getCube("tail2");
        AdvancedModelBox tail3 = model.getCube("tail3");
        AdvancedModelBox tail4 = model.getCube("tail4");
        AdvancedModelBox tail5 = model.getCube("tail5");

        AdvancedModelBox upperArmR = model.getCube("Right arm");
        AdvancedModelBox upperArmL = model.getCube("Left arm");

        AdvancedModelBox lowerArmR = model.getCube("Right forearm");
        AdvancedModelBox lowerArmL = model.getCube("Left forearm");

        AdvancedModelBox handR = model.getCube("Right hand");
        AdvancedModelBox handL = model.getCube("Left hand");

        AdvancedModelBox thighR = model.getCube("Right thigh");
        AdvancedModelBox thighL = model.getCube("Left thigh");

        AdvancedModelBox lowerThighR = model.getCube("Right shin");
        AdvancedModelBox lowerThighL = model.getCube("Left shin");

        AdvancedModelBox upperFootR = model.getCube("Right upper foot");
        AdvancedModelBox upperFootL = model.getCube("Left upper foot");

        AdvancedModelBox footR = model.getCube("Right foot");
        AdvancedModelBox footL = model.getCube("Left foot");

        AdvancedModelBox[] rightArmParts = new AdvancedModelBox[] { handR, lowerArmR, upperArmR };
        AdvancedModelBox[] leftArmParts = new AdvancedModelBox[] { handL, lowerArmL, upperArmL };
        AdvancedModelBox[] tailParts = new AdvancedModelBox[] { tail5, tail4, tail3, tail2, tail1 };
        AdvancedModelBox[] bodyParts = new AdvancedModelBox[] { waist, chest, shoulders, neck4, neck3, neck2, neck1, head };

        float globalSpeed = 1.0F;
        float globalHeight = 2F * f1;

        model.bob(thighL, 1F * globalSpeed, 0.3f * globalHeight, false, f, f1);
        model.bob(thighR, 1F * globalSpeed, 0.3f * globalHeight, false, f, f1);
     //   model.walk(shoulders, 1F * globalSpeed, 0.2F, true, 1, 0, f, f1);
     //   model.walk(chest, 1F * globalSpeed, 0.2F, false, 0.5F, 0, f, f1);

     //   model.walk(thighL, 0.5F * globalSpeed, 0.7F, false, 3.14F, 0.2F, f, f1);
    //    model.walk(lowerThighL, 0.5F * globalSpeed, 0.1F, false, 1.5F, 0.3F, f, f1);
   //     model.walk(upperFootL, 0.5F * globalSpeed, 0.8F, false, -1F, -0.1F, f, f1);
   //     model.walk(footL, 0.5F * globalSpeed, 1.5F, true, -1F, 1F, f, f1);

   //     model.walk(thighR, 0.5F * globalSpeed, 0.7F, true, 3.14F, 0.2F, f, f1);
     //   model.walk(lowerThighR, 0.5F * globalSpeed, 0.1F, true, 1.5F, 0.3F, f, f1);
    //    model.walk(upperFootR, 0.5F * globalSpeed, 0.8F, true, -1F, -0.1F, f, f1);
    //    model.walk(footR, 0.5F * globalSpeed, 1.5F, false, -1F, 1F, f, f1);


        model.chainSwing(tailParts, 0.5F * globalSpeed, -0.1F, 2, f, f1);
  //      model.chainWave(tailParts, 1F * globalSpeed, -0.1F, 2.5F, f, f1);
 //       model.chainWave(bodyParts, 1F * globalSpeed, -0.1F, 4, f, f1);

    //    model.chainWave(rightArmParts, 1F * globalSpeed, -0.3F, 4, f, f1);
   //     model.chainWave(leftArmParts, 1F * globalSpeed, -0.3F, 4, f, f1);

        model.chainWave(tailParts, 0.2F, 0.05F, 2, ticks, 0.25F);
   //     model.chainWave(bodyParts, 0.2F, -0.03F, 5, ticks, 0.25F);
        model.chainWave(rightArmParts, 0.2F, -0.1F, 4, ticks, 0.25F);
        model.chainWave(leftArmParts, 0.2F, -0.1F, 4, ticks, 0.25F);

        entity.tailBuffer.applyChainSwingBuffer(tailParts);
    }
}
