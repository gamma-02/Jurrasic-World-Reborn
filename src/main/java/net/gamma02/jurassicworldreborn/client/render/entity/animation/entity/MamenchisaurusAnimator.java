package net.gamma02.jurassicworldreborn.client.render.entity.animation.entity;

import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import net.gamma02.jurassicworldreborn.client.model.AnimatableModel;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimator;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.MamenchisaurusEntity;

@OnlyIn(Dist.CLIENT)
public class MamenchisaurusAnimator extends EntityAnimator<MamenchisaurusEntity>
{
    @Override
    protected void performAnimations(AnimatableModel model, MamenchisaurusEntity entity, float f, float f1, float ticks, float rotationYaw, float rotationPitch, float scale)
    {
        AdvancedModelBox head = model.getCube("Head");

        AdvancedModelBox neck1 = model.getCube("neck1");
        AdvancedModelBox neck2 = model.getCube("neck2");
        AdvancedModelBox neck3 = model.getCube("neck3");
        AdvancedModelBox neck4 = model.getCube("neck4");
        AdvancedModelBox neck5 = model.getCube("neck5");
        AdvancedModelBox neck6 = model.getCube("neck6");

        AdvancedModelBox waist = model.getCube("hips");
        AdvancedModelBox tail1 = model.getCube("tail1");
        AdvancedModelBox tail2 = model.getCube("tail2");
        AdvancedModelBox tail3 = model.getCube("tail3");
        AdvancedModelBox tail4 = model.getCube("tail4");
        AdvancedModelBox tail5 = model.getCube("tail5");


        AdvancedModelBox lowerThighLeft = model.getCube("bottom front left leg");
        AdvancedModelBox lowerThighRight = model.getCube("bottom front right leg");

        AdvancedModelBox footLeft = model.getCube("left back foot");
        AdvancedModelBox footRight = model.getCube("left right foot");

        AdvancedModelBox armRight = model.getCube("front right top leg");
        AdvancedModelBox armLeft = model.getCube("front left top leg");

        AdvancedModelBox lowerArmRight = model.getCube("bottom front right leg");
        AdvancedModelBox lowerArmLeft = model.getCube("bottom front left leg");

        AdvancedModelBox handRight = model.getCube("front right foot");
        AdvancedModelBox handLeft = model.getCube("front left foot");

        AdvancedModelBox backLeftCalf = model.getCube("bottom leg left");
        AdvancedModelBox backLeftThigh = model.getCube("top leg left");

        AdvancedModelBox backRightThigh = model.getCube("top leg right");

        AdvancedModelBox backRightCalf = model.getCube("bottom leg right");

        AdvancedModelBox stomach = model.getCube("Stomach");
        AdvancedModelBox body = model.getCube("body");

        AdvancedModelBox[] neckParts = new AdvancedModelBox[] { head, neck6, neck5, neck4, neck3, neck2, neck1, body };
        AdvancedModelBox[] tailParts = new AdvancedModelBox[] { tail5, tail4, tail3, tail2, tail1 };
        float delta = Minecraft.getInstance().getDeltaFrameTime();

        LegArticulator.articulateQuadruped(entity, entity.legSolver, waist, neck1,
                backLeftThigh, backLeftCalf, backRightThigh, backRightCalf, armLeft, lowerArmLeft, armRight, lowerArmRight,
                0.25F, 0.4F, -0.2F, -0.3F,
                delta
        );


        float globalSpeed = 0.5F;
        float globalHeight = 0.5F;
        float globalDegree = 0.5F;

        float frontOffset = 1.0F;

        model.bob(waist, globalSpeed * 1.0F, globalHeight * 4.0F, false, f, f1);
        model.bob(backLeftThigh, globalSpeed * 1.0F, globalHeight * 1.0F, false, f, f1);
        model.bob(backRightThigh, globalSpeed * 1.0F, globalHeight * 1.0F, false, f, f1);

        model.chainWave(tailParts, globalSpeed * 1.0F, globalHeight * 0.25F, 3, f, f1);
        model.chainSwing(tailParts, globalSpeed * 0.5F, globalHeight * 0.5F, 3, f, f1);
     //   model.chainWave(neckParts, globalSpeed * 1.0F, globalHeight * 0.125F, -4, f, f1);

    //    model.walk(backLeftThigh, 1F * globalSpeed, 0.7F * globalDegree, false, 0F, -0.4F, f, f1);
    //    model.walk(lowerThighLeft, 1F * globalSpeed, 0.6F * globalDegree, true, 1F, 0.5F, f, f1);
    //    model.walk(footLeft, 1F * globalSpeed, 0.6F * globalDegree, false, -1.5F, 0.85F, f, f1);

    //    model.walk(backRightThigh, 1F * globalSpeed, 0.7F * globalDegree, true, 0F, -0.4F, f, f1);
    //    model.walk(lowerThighRight, 1F * globalSpeed, 0.6F * globalDegree, false, 1F, 0.5F, f, f1);
   //     model.walk(footRight, 1F * globalSpeed, 0.6F * globalDegree, true, -1.5F, 0.85F, f, f1);

    //    model.walk(armLeft, 1F * globalSpeed, 0.7F * globalDegree, true, frontOffset + 0F, -0.2F, f, f1);
    //    model.walk(lowerArmLeft, 1F * globalSpeed, 0.6F * globalDegree, true, frontOffset + 1F, -0.2F, f, f1);
     //   model.walk(handLeft, 1F * globalSpeed, 0.6F * globalDegree, false, frontOffset + 2F, 0.8F, f, f1);

     //   model.walk(armRight, 1F * globalSpeed, 0.7F * globalDegree, false, frontOffset + 0F, -0.2F, f, f1);
     //   model.walk(lowerArmRight, 1F * globalSpeed, 0.6F * globalDegree, false, frontOffset + 1F, -0.2F, f, f1);
    //    model.walk(handRight, 1F * globalSpeed, 0.6F * globalDegree, true, frontOffset + 2F, 0.8F, f, f1);

        model.chainWave(tailParts, globalSpeed * 0.25F, globalHeight * 1.0F, 3, ticks, 0.025F);
        model.chainWave(neckParts, globalSpeed * 0.25F, globalHeight * 0.25F, -4, ticks, 0.025F);

        entity.tailBuffer.applyChainSwingBuffer(tailParts);
    }
}
