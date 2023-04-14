package net.gamma02.jurassicworldreborn.client.render.entity.animation.entity;

import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import net.gamma02.jurassicworldreborn.client.model.AnimatableModel;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimator;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.DunkleosteusEntity;

@OnlyIn(Dist.CLIENT)
public class DunkleosteusAnimator extends EntityAnimator<DunkleosteusEntity>
{
    @Override
    protected void performAnimations(AnimatableModel model, DunkleosteusEntity entity, float f, float f1, float ticks, float rotationYaw, float rotationPitch, float scale)
    {
        // tail
        AdvancedModelBox tail1 = model.getCube("Tail Section 1");
        AdvancedModelBox tail2 = model.getCube("Tail Section 2");
        AdvancedModelBox tail3 = model.getCube("Tail Section 3");
        AdvancedModelBox tail4 = model.getCube("Tail Section 4");
        AdvancedModelBox tail5 = model.getCube("Tail Section 5");
        AdvancedModelBox tail6 = model.getCube("Tail Section 6");

        // head stoof
        AdvancedModelBox head = model.getCube("Main head");

        // flipper
        AdvancedModelBox rightFlipper = model.getCube("Right Front Flipper");
        AdvancedModelBox leftFlipper = model.getCube("Left Front Flipper");

        // body
        AdvancedModelBox body2 = model.getCube("Body Section 2");
        AdvancedModelBox body3 = model.getCube("Body Section 3");

        AdvancedModelBox[] tail = new AdvancedModelBox[] { tail6, tail5, tail4, tail3, tail2, tail1, body3, body2, head };

        head.rotationPointX -= -1 * f1 * Math.sin((f + 1) * 0.6);
        model.chainSwing(tail, 0.3F, 0.2F, 3.0D, f, f1);

        model.walk(leftFlipper, 0.6F, 0.6F, false, 0.0F, 0.8F, f, f1);
        model.walk(rightFlipper, 0.6F, 0.6F, false, 0.0F, 0.8F, f, f1);

        model.flap(leftFlipper, 0.6F, 0.6F, false, 0.0F, 0.8F, f, f1);
        model.flap(rightFlipper, 0.6F, 0.6F, true, 0.0F, -0.8F, f, f1);

        model.bob(head, 0.04F, 2.0F, false, ticks, 0.25F);
        model.walk(leftFlipper, 0.2F, 0.25F, false, 1.0F, 0.1F, ticks, 0.25F);
        model.walk(rightFlipper, 0.2F, 0.25F, false, 1.0F, 0.1F, ticks, 0.25F);
        model.chainSwing(tail, 0.05F, -0.075F, 1.5D, ticks, 0.25F);
    }
}
