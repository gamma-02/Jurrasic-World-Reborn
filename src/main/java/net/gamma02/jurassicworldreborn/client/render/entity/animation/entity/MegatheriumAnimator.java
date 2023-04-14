package net.gamma02.jurassicworldreborn.client.render.entity.animation.entity;

import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import net.gamma02.jurassicworldreborn.client.model.AnimatableModel;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimator;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.ArsinoitheriumEntity;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.MammothEntity;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.MegatheriumEntity;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MegatheriumAnimator extends EntityAnimator<MegatheriumEntity>
{
    @Override
    protected void performAnimations(AnimatableModel model, MegatheriumEntity entity, float f, float f1, float ticks, float rotationYaw, float rotationPitch, float scale)
    {
        AdvancedModelBox neck1 = model.getCube("neck1");
        AdvancedModelBox neck2 = model.getCube("neck2");
        AdvancedModelBox neck3 = model.getCube("neck3");
        AdvancedModelBox neck4 = model.getCube("neck4");
        AdvancedModelBox throat3 = model.getCube("throat3");
        AdvancedModelBox throat4 = model.getCube("throat4");

        AdvancedModelBox[] bodyParts = new AdvancedModelBox[] { throat4,throat3,neck1,neck2,neck3,neck4 };
    }
}
