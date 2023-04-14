package net.gamma02.jurassicworldreborn.client.render.entity.animation.entity;

import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import net.gamma02.jurassicworldreborn.client.model.AnimatableModel;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimator;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.MicroceratusEntity;

@OnlyIn(Dist.CLIENT)
public class MicroceratusAnimator extends EntityAnimator<MicroceratusEntity>
{
    @Override
    protected void performAnimations(AnimatableModel parModel, MicroceratusEntity entity, float f, float f1, float ticks, float rotationYaw, float rotationPitch, float scale)
    {
        AdvancedModelBox body = parModel.getCube("Body MAIN");

        AdvancedModelBox tail1 = parModel.getCube("Tail #1");
        AdvancedModelBox tail2 = parModel.getCube("Tail #2");
        AdvancedModelBox tail3 = parModel.getCube("Tail #3");
        AdvancedModelBox tail4 = parModel.getCube("Tail #4");
        AdvancedModelBox tail5 = parModel.getCube("Tail #5");

        AdvancedModelBox neck1 = parModel.getCube("Neck #1");
        AdvancedModelBox neck2 = parModel.getCube("Neck #2");

        AdvancedModelBox head = parModel.getCube("Head");

        AdvancedModelBox thighLeft = parModel.getCube("Leg Top LEFT");
        AdvancedModelBox thighRight = parModel.getCube("Leg Top RIGHT");

        AdvancedModelBox thighMidLeft = parModel.getCube("Leg Mid LEFT");
        AdvancedModelBox thighMidRight = parModel.getCube("Leg Mid RIGHT");

        AdvancedModelBox upperFootLeft = parModel.getCube("Leg Bot LEFT");
        AdvancedModelBox upperFootRight = parModel.getCube("Leg Bot RIGHT");

        AdvancedModelBox footLeft = parModel.getCube("Leg Foot LEFT");
        AdvancedModelBox footRight = parModel.getCube("Leg Foot RIGHT");

        AdvancedModelBox armTopLeft = parModel.getCube("Arm Top LEFT");
        AdvancedModelBox armTopRight = parModel.getCube("Arm Top RIGHT");

        AdvancedModelBox armMidLeft = parModel.getCube("Arm Mid LEFT");
        AdvancedModelBox armMidRight = parModel.getCube("Arm Mid RIGHT");

        AdvancedModelBox handLeft = parModel.getCube("Arm Hand LEFT");
        AdvancedModelBox handRight = parModel.getCube("Arm Hand RIGHT");

        AdvancedModelBox[] tail = new AdvancedModelBox[] { tail5, tail4, tail3, tail2, tail1 };
        AdvancedModelBox[] neck = new AdvancedModelBox[] { head, neck2, neck1, body };

        AdvancedModelBox[] armLeft = new AdvancedModelBox[] { handLeft, armMidLeft, armTopLeft };
        AdvancedModelBox[] armRight = new AdvancedModelBox[] { handRight, armMidRight, armTopRight };

        // f = ticks;
        // f1 = 0.5F;

        float globalSpeed = 0.8F;
        float globalDegree = 0.5F;
        float globalHeight = 1.0F;

        parModel.bob(body, globalSpeed * 1.0F, globalHeight * 1.0F, false, f, f1);
        parModel.bob(thighLeft, globalSpeed * 1.0F, globalHeight * 1.0F, false, f, f1);
        parModel.bob(thighRight, globalSpeed * 1.0F, globalHeight * 1.0F, false, f, f1);

        parModel.chainWave(tail, globalSpeed * 1.0F, globalHeight * 0.1F, 2, f, f1);
        parModel.chainWave(neck, globalSpeed * 1.0F, globalHeight * 0.1F, 3, f, f1);

        parModel.chainWave(armLeft, globalSpeed * 1.0F, globalHeight * 0.2F, 3, f, f1);
        parModel.chainWave(armRight, globalSpeed * 1.0F, globalHeight * -0.2F, 3, f, f1);

        parModel.walk(thighLeft, globalSpeed * 1.0F, globalDegree * 1.0F, true, 0.0F, 0.0F, f, f1);
        parModel.walk(thighMidLeft, globalSpeed * 1.0F, globalDegree * 1.0F, true, 1.0F, 0.2F, f, f1);
        parModel.walk(footLeft, globalSpeed * 1.0F, globalDegree * 1.0F, false, -0.25F, -0.2F, f, f1);

        parModel.walk(thighRight, globalSpeed * 1.0F, globalDegree * 1.0F, false, 0.0F, 0.0F, f, f1);
        parModel.walk(thighMidRight, globalSpeed * 1.0F, globalDegree * 1.0F, false, 1.0F, 0.2F, f, f1);
        parModel.walk(footRight, globalSpeed * 1.0F, globalDegree * 1.0F, true, -0.25F, -0.2F, f, f1);

        parModel.chainWave(tail, globalSpeed * 0.2F, globalHeight * 0.05F, 2, ticks, 0.25F);
        parModel.chainWave(neck, globalSpeed * 0.2F, globalHeight * 0.05F, 3, ticks, 0.25F);

        entity.tailBuffer.applyChainSwingBuffer(tail);
    }
}
