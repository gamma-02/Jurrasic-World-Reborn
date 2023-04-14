package net.gamma02.jurassicworldreborn.client.render.entity.animation.entity;

import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import net.gamma02.jurassicworldreborn.client.model.AnimatableModel;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimator;
import mod.reborn.server.entity.animal.EntityCrab;
import mod.reborn.server.entity.animal.EntityShark;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SharkAnimator extends EntityAnimator<EntityShark> {//todo: shark
    @Override
    protected void performAnimations(AnimatableModel model, EntityShark entity, float f, float f1, float ticks, float rotationYaw, float rotationPitch, float scale) {
        AdvancedModelBox leftFlipper = model.getCube("LeftFin1");
        AdvancedModelBox rightFlipper = model.getCube("RightFin1");
        AdvancedModelBox tail5 = model.getCube("Tail5");
        AdvancedModelBox tail4 = model.getCube("Tail4");
        AdvancedModelBox tail3 = model.getCube("Tail3");
        AdvancedModelBox tail2 = model.getCube("Tail2");
        AdvancedModelBox tail1 = model.getCube("Tail1");
        AdvancedModelBox body1 = model.getCube("Body1");

        AdvancedModelBox[] body = new AdvancedModelBox[] { tail5, tail4, tail3, tail2, tail1, body1 };

        model.chainSwing(body, 1F, 0.3514F, -3, f, f1);

        model.walk(rightFlipper, 0.25F, 0.2F, false, 0F, 0F, ticks, 0.005F);
        model.walk(leftFlipper, 0.25F, 0.2F, false, 0F, 0F, ticks, 0.005F);
    }
}
