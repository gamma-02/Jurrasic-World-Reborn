package net.gamma02.jurassicworldreborn.client.render.entity.animation.entity;

import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import net.gamma02.jurassicworldreborn.client.model.AnimatableModel;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimator;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.ProtoceratopsEntity;

@OnlyIn(Dist.CLIENT)
public class ProtoceratopsAnimator extends EntityAnimator<ProtoceratopsEntity>
{
    @Override
    protected void performAnimations(AnimatableModel model, ProtoceratopsEntity entity, float f, float f1, float ticks, float rotationYaw, float rotationPitch, float scale)
    {
        float globalSpeed = 0.5F;
        float globalHeight = 0.5F;
        float globalDegree = 0.5F;

        float frontOffset = 1.0F;

        AdvancedModelBox head = model.getCube("Head");

        AdvancedModelBox lowerJaw = model.getCube("Lower jaw");

        AdvancedModelBox neck1 = model.getCube("Neck #1");
        AdvancedModelBox neck2 = model.getCube("Neck #2");
        AdvancedModelBox neck3 = model.getCube("Neck #3");

        AdvancedModelBox body = model.getCube("Body MAIN");

        AdvancedModelBox tail1 = model.getCube("Tail #1");
        AdvancedModelBox tail2 = model.getCube("Tail #2");
        AdvancedModelBox tail3 = model.getCube("Tail #3");
        AdvancedModelBox tail4 = model.getCube("Tail #4");

        AdvancedModelBox armUpperRight = model.getCube("Arm Top RIGHT");
        AdvancedModelBox armLowerRight = model.getCube("Arm Mid RIGHT");
        AdvancedModelBox handRight = model.getCube("Arm Hand RIGHT");

        AdvancedModelBox armUpperLeft = model.getCube("Arm Top LEFT");
        AdvancedModelBox armLowerLeft = model.getCube("Arm Mid LEFT");
        AdvancedModelBox handLeft = model.getCube("Arm Hand LEFT");

        AdvancedModelBox thighLeft = model.getCube("Leg Top LEFT");
        AdvancedModelBox thighRight = model.getCube("Leg Top RIGHT");

        AdvancedModelBox thighLowerLeft = model.getCube("Leg Mid LEFT");
        AdvancedModelBox thighLowerRight = model.getCube("Leg Mid RIGHT");

        AdvancedModelBox footUpperLeft = model.getCube("Leg Bot LEFT");
        AdvancedModelBox footUpperRight = model.getCube("Leg Bot RIGHT");

        AdvancedModelBox footLeft = model.getCube("Leg Foot LEFT");
        AdvancedModelBox footRight = model.getCube("Leg Foot RIGHT");

        AdvancedModelBox[] tail = new AdvancedModelBox[] { tail4, tail3, tail2, tail1 };
        AdvancedModelBox[] neck = new AdvancedModelBox[] { head, neck3, neck2, neck1 };

        model.bob(body, globalSpeed * 1.0F, globalHeight * 1.0F, false, f, f1);
        model.bob(thighLeft, globalSpeed * 1.0F, globalHeight * 0.9F, false, f, f1);
        model.bob(thighRight, globalSpeed * 1.0F, globalHeight * 1.0F, false, f, f1);

        model.chainWave(tail, globalSpeed * 1.0F, globalHeight * 0.25F, 3, f, f1);
        model.chainSwing(tail, globalSpeed * 0.5F, globalHeight * 0.25F, 3, f, f1);
        model.chainWave(neck, globalSpeed * 1.0F, globalHeight * 0.25F, -3, f, f1);

      //  model.walk(thighLeft, 1F * globalSpeed, 0.7F * globalDegree, false, 0F, -0.4F, f, f1);
      //  model.walk(thighLowerLeft, 1F * globalSpeed, 0.5F * globalDegree, true, 1F, 0.5F, f, f1);
      //  model.walk(footUpperLeft, 1F * globalSpeed, 0.5F * globalDegree, false, -1.5F, 0.85F, f, f1);

    //   model.walk(thighRight, 1F * globalSpeed, 0.7F * globalDegree, true, 0F, -0.4F, f, f1);
   //     model.walk(thighLowerRight, 1F * globalSpeed, 0.6F * globalDegree, false, 1F, 0.5F, f, f1);
   //    model.walk(footUpperRight, 1F * globalSpeed, 0.6F * globalDegree, true, -1.5F, 0.85F, f, f1);

    //    model.walk(armUpperLeft, 1F * globalSpeed, 0.7F * globalDegree, true, frontOffset + 0F, -0.2F, f, f1);
     //   model.walk(armLowerLeft, 1F * globalSpeed, 0.5F * globalDegree, true, frontOffset + 1F, -0.1F, f, f1);
    //    model.walk(handLeft, 1F * globalSpeed, 0.6F * globalDegree, false, frontOffset + 2F, 0.8F, f, f1);

    //   model.walk(armUpperRight, 1F * globalSpeed, 0.7F * globalDegree, false, frontOffset + 0F, -0.2F, f, f1);
    //    model.walk(armLowerRight, 1F * globalSpeed, 0.5F * globalDegree, false, frontOffset + 1F, -0.1F, f, f1);
    //   model.walk(handRight, 1F * globalSpeed, 0.6F * globalDegree, true, frontOffset + 2F, 0.8F, f, f1);

        model.chainWave(tail, globalSpeed * 0.25F, globalHeight * 1.0F, 3, ticks, 0.025F);
        model.chainWave(neck, globalSpeed * 0.25F, globalHeight * 1.0F, -3, ticks, 0.025F);

        entity.tailBuffer.applyChainSwingBuffer(tail);
    }
}
