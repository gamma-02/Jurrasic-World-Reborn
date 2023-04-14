package net.gamma02.jurassicworldreborn.client.render.entity.animation.entity;

import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import net.gamma02.jurassicworldreborn.client.model.AnimatableModel;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimator;
import net.gamma02.jurassicworldreborn.common.entities.animal.CrabEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CrabAnimator extends EntityAnimator<CrabEntity> {//todo: crab???????????
    @Override
    protected void performAnimations(AnimatableModel model, CrabEntity entity, float limbSwing, float limbSwingAmount, float ticks, float rotationYaw, float rotationPitch, float scale) {
        AdvancedModelBox head = model.getCube("bodyTop");
        AdvancedModelBox[] neck = new AdvancedModelBox[] { head };

        model.chainWave(neck, 0.125F, 1.0F, 3, ticks, 0.025F);
    }
}
