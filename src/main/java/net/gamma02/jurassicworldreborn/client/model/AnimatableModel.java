package net.gamma02.jurassicworldreborn.client.model;

import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.ITabulaModelAnimator;
import com.github.alexthe666.citadel.client.model.container.TabulaModelContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Animatable;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;
import java.util.Set;

@OnlyIn(Dist.CLIENT)
public class AnimatableModel extends FixedTabulaModel {
    public AnimatableModel(TabulaModelContainer model) {
        this(model, null);
    }

    public AnimatableModel(TabulaModelContainer model, ITabulaModelAnimator animator) {
        super(model, animator);
    }



    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch) {
        Animatable animatable = (Animatable) entity;

        if (animatable.isCarcass()) {
            this.setMovementScale(0.0F);
        } else {
            this.setMovementScale(animatable.isSleeping() ? 0.5F : 1.0F);
        }
        if (this.tabulaAnimator != null) {
            this.tabulaAnimator.setRotationAngles(this, entity, limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, 1.0F);
        }
        //Was not a nessicary call and was erroring
        //this IS a necessary call and IS erroring, see
//        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch);
    }

    public String[] getCubeIdentifierArray() {
        String[] cubeIdentifiers = new String[this.identifierMap.size()];
        int index = 0;

        Set<String> identifiers = this.identifierMap.keySet();

        for (String identifier : identifiers) {
            cubeIdentifiers[index] = identifier;
            index++;
        }

        return cubeIdentifiers;
    }

    @Override
    public void renderToBuffer(PoseStack p_103013_, VertexConsumer p_103014_, int p_103015_, int p_103016_, float p_103017_, float p_103018_, float p_103019_, float p_103020_) {

//        Jurassicworldreborn.debugHook();
        super.renderToBuffer(p_103013_, p_103014_, p_103015_, p_103016_, p_103017_, p_103018_, p_103019_, p_103020_);
    }

    public String[] getCubeNames() {
        String[] cubeNames = new String[this.cubes.size()];
        int index = 0;

        Set<String> names = this.cubes.keySet();

        for (String identifier : names) {
            cubeNames[index] = identifier;
            index++;
        }

        return cubeNames;
    }

    public Map<String, AdvancedModelBox> getIdentifierCubes() {
        return this.identifierMap;
    }

    @Override
    public void faceTarget(float yaw, float pitch, float rotationDivisor, AdvancedModelBox... boxes) {


        float actualRotationDivisor = rotationDivisor * boxes.length;


//        float yawAmount = Mth.clamp(Mth.wrapDegrees(yaw), -45.0F, 45.0F) / (180.0F / (float) Math.PI) / actualRotationDivisor;


        float pitchAmount = Mth.wrapDegrees(pitch) / (180.0F / (float) Math.PI) / actualRotationDivisor;

        for (AdvancedModelBox box : boxes) {
            box.rotateAngleY += yaw;
            box.rotateAngleX += pitchAmount;
        }
    }
}