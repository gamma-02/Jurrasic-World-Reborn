package net.gamma02.jurassicworldreborn.common.entities.EntityUtils;


import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.PoseHandler;
import net.minecraft.world.entity.LivingEntity;

public interface Animatable extends IAnimatedEntity {
    boolean isCarcass();
    boolean isMoving();
    boolean isClimbing();
    boolean inWater();
    boolean isSwimming();
    boolean isRunning();
    boolean inLava();
    boolean canUseGrowthStage(GrowthStage growthStage);
    boolean isMarineCreature();
    boolean shouldUseInertia();
    boolean isSleeping();
    GrowthStage getGrowthStage();
    <ENTITY extends LivingEntity & Animatable> PoseHandler<ENTITY> getPoseHandler();
}