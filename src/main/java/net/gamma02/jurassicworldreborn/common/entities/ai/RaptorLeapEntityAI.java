package net.gamma02.jurassicworldreborn.common.entities.ai;

import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;

import java.util.EnumSet;

public class RaptorLeapEntityAI extends Goal {
    private DinosaurEntity entity;
    private LivingEntity target;

    private int prevTick;
    private EntityAnimation animation;

    private double targetPrevPosX;
    private double targetPrevPosZ;

    private boolean ticked = false;

    public RaptorLeapEntityAI(DinosaurEntity entity) {
        this.entity = entity;
        this.setFlags(EnumSet.of(Flag.TARGET));//weaktodo: were the mutex bits of
    }

    @Override
    public boolean canUse() {
        if (this.entity.herd != null && this.entity.herd.fleeing) {
            return false;
        }

        LivingEntity target = this.entity.getAttackTarget();

        if (target != null && target.isAlive() && !(target instanceof DinosaurEntity && ((DinosaurEntity) target).isCarcass())) {
            float distance = this.entity.distanceTo(target);

            if (distance >= 5 && distance <= 6 && this.entity.isOnGround()) {
                BlockHitResult result = this.entity.level.clip(new ClipContext(this.entity.position().add(0.0, 1.0, 0.0), target.position(), ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, null));
                if (result == null || result.getType() != BlockHitResult.Type.BLOCK) {
                    this.target = target;
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void start() {
        this.animation = EntityAnimation.PREPARE_LEAP;
        this.entity.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
        this.ticked = false;
    }

    @Override
    public void tick() {
        int tick = this.entity.getAnimationTick();

        if (this.animation == EntityAnimation.PREPARE_LEAP && tick < this.prevTick) {
            this.animation = EntityAnimation.LEAP;

            this.entity.playSound(this.entity.getSoundForAnimation(EntityAnimation.ATTACKING.get()), this.entity.getSoundVolume(), this.entity.getVoicePitch());

            double targetSpeedX = this.target.getX() - (!this.ticked ? this.target.xOld : this.targetPrevPosX);
            double targetSpeedZ = this.target.getZ() - (!this.ticked ? this.target.zOld : this.targetPrevPosZ);

            double length = this.entity.getBbWidth() * 6.0F;

            double destX = this.target.getX() + targetSpeedX * length;
            double destZ = this.target.getZ() + targetSpeedZ * length;

            double delta = Math.sqrt((destX - this.entity.getX()) * (destX - this.entity.getX()) + (destZ - this.entity.getZ()) * (destZ - this.entity.getZ()));
            double angle = Math.atan2(destZ - this.entity.getZ(), destX - this.entity.getX());

            double motionX = delta / length * Math.cos(angle);
            double motionZ = (delta / length * Math.sin(angle));

            double motionY = Math.min(0.3, Math.max(0, (this.target.getY() - this.entity.getY()) * 0.1)) + 0.6;

            this.entity.setDeltaMovement(motionX, motionY, motionZ);
        } else if (this.animation == EntityAnimation.LEAP && this.entity.getDeltaMovement().x < 0) {
            this.animation = EntityAnimation.LEAP_LAND;
        } else if (this.animation == EntityAnimation.LEAP_LAND && (this.entity.isOnGround() || this.entity.isSwimming())) {
            this.animation = EntityAnimation.IDLE;

            if (this.entity.getBoundingBox() != null && this.target.getBoundingBox() != null && this.entity.getBoundingBox().intersects(this.target.getBoundingBox().inflate(2.0, 2.0, 2.0))) {
                this.entity.doHurtTarget(this.target);
            }
        }

        this.targetPrevPosX = this.target.getX();
        this.targetPrevPosZ = this.target.getZ();
        this.ticked = true;

        if (this.entity.getAnimation() != this.animation.get()) {
            this.entity.setAnimation(this.animation.get());
            this.entity.setAnimationTick(this.prevTick + 1);
        }

        this.prevTick = tick;
    }

    @Override
    public void stop() {
        this.entity.setAnimation(EntityAnimation.IDLE.get());
    }

    @Override
    public boolean canContinueToUse() {
        return !this.target.isDeadOrDying() && !(this.target instanceof DinosaurEntity && ((DinosaurEntity) this.target).isCarcass()) && this.animation != EntityAnimation.IDLE;
    }
}