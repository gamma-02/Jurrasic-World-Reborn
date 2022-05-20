package net.gamma02.jurassicworldreborn.client.model.animation;

import com.github.alexthe666.citadel.Citadel;
import com.github.alexthe666.citadel.ClientProxy;

import com.github.alexthe666.citadel.client.model.TabulaModelRenderUtils;
import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @author LLibrary
 * @version 1.0
 */
@OnlyIn(Dist.CLIENT)
public class FixedChainBuffer {
    private int yawTimer;
    private float yawVariation;
    private float pitchVariation;
    private float prevYawVariation;
    private float prevPitchVariation;
    private float prevPartialTicks;

    /**
     * Calculates the swing amounts for the given entity (Y axis)
     *
     * @param maxAngle       the furthest this ChainBuffer can swing
     * @param bufferTime     the time it takes to swing this buffer in ticks
     * @param angleDecrement the angle to decrement by for each model piece
     * @param entity         the entity with this ChainBuffer
     */
    public void calculateChainSwingBuffer(float maxAngle, int bufferTime, float angleDecrement, float divisor, LivingEntity entity) {
        this.prevYawVariation = this.yawVariation;
        if (entity.yBodyRot != entity.yBodyRotO && Mth.abs(this.yawVariation) < maxAngle) {
            this.yawVariation += (entity.yBodyRotO - entity.yBodyRot) / divisor;
        }
        if (this.yawVariation > 0.7F * angleDecrement) {
            if (this.yawTimer > bufferTime) {
                this.yawVariation -= angleDecrement;
                if (Mth.abs(this.yawVariation) < angleDecrement) {
                    this.yawVariation = 0.0F;
                    this.yawTimer = 0;
                }
            } else {
                this.yawTimer++;
            }
        } else if (this.yawVariation < -0.7F * angleDecrement) {
            if (this.yawTimer > bufferTime) {
                this.yawVariation += angleDecrement;
                if (Mth.abs(this.yawVariation) < angleDecrement) {
                    this.yawVariation = 0.0F;
                    this.yawTimer = 0;
                }
            } else {
                this.yawTimer++;
            }
        }
    }

    /**
     * Calculates the wave amounts for the given entity (X axis)
     *
     * @param maxAngle       the furthest this ChainBuffer can wave
     * @param bufferTime     the time it takes to wave this buffer in ticks
     * @param angleDecrement the angle to decrement by for each model piece
     * @param entity         the entity with this ChainBuffer
     */
    public void calculateChainSwingBuffer(float maxAngle, int bufferTime, float angleDecrement, LivingEntity entity) {
        this.calculateChainSwingBuffer(maxAngle, bufferTime, angleDecrement, 1.0F, entity);
    }

    /**
     * Applies this buffer on the Y axis to the given array of model boxes.
     *
     * @param boxes the box array
     */
    public void applyChainSwingBuffer(ModelPart... boxes) {
        float rotateAmount;
        if(Minecraft.getInstance().isPaused()) {
            rotateAmount = 0.01745329251F * interpolate(this.prevYawVariation, this.yawVariation, this.prevPartialTicks) / boxes.length;
        }else {
            rotateAmount = 0.01745329251F * interpolate(this.prevYawVariation, this.yawVariation, Minecraft.getInstance().getDeltaFrameTime()) / boxes.length;
            this.prevPartialTicks = Minecraft.getInstance().getDeltaFrameTime();
        }
        for (ModelPart box : boxes) {
            box.yRot += rotateAmount;
        }
    }



    public static float interpolate(float prev, float current, float partialTicks) {
        return prev + partialTicks * (current - prev);
    }

    public static float interpolateRotation(float prev, float current, float partialTicks) {
        float shortest = ((current - prev) % 360 + 540) % 360 - 180;
        return prev + shortest * partialTicks;
    }


}