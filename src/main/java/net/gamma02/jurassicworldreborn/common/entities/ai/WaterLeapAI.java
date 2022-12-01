package net.gamma02.jurassicworldreborn.common.entities.ai;

import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.common.entities.SwimmingDinosaurEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Blocks;

import java.util.EnumSet;

public class WaterLeapAI extends Goal {
    private static final int[] OFFSET_MULTIPLIERS = new int[]{0, 1, 4, 5, 6, 7};
    private final SwimmingDinosaurEntity entity;
    private final int chance;
    private final float yeetheight;//sjdlsjkdsj this is hillarious - gamma_02
    private float jumpheight;
    private boolean launched = false;
    private int prevTick;
    private EntityAnimation animation;
    public WaterLeapAI(SwimmingDinosaurEntity entity, int chance, float yeetpower) {
        this.entity = entity;
        this.chance = chance;
        this.yeetheight = yeetpower;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP, Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        if(entity.getRandom().nextInt(chance) == 0 && this.entity.isBusy()) {
            return false;
        } else {
            this.launched = false;
            Direction direction = this.entity.getDirection();
            int i = direction.getStepX();
            int j = direction.getStepZ();
            BlockPos blockPos = this.entity.getOnPos();
            for (int k : OFFSET_MULTIPLIERS) {
                if (!this.entity.isInWater() || !this.isAirAbove(blockPos, i, j, k)) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return !this.entity.isInWater() || launched;
    }

    @Override
    public void start() {
        this.animation = EntityAnimation.PREPARE_LEAP;
    }

    @Override
    public void tick() {
        int tick = this.entity.getAnimationTick();

        if (this.animation == EntityAnimation.PREPARE_LEAP && tick < this.prevTick) {
            this.entity.setAnimation(EntityAnimation.LEAP.get());
            this.entity.playSound(this.entity.getSoundForAnimation(EntityAnimation.LEAP.get()), this.entity.getSoundVolume(), this.entity.getVoicePitch());
            double motionX = 2F;
            double motionY = this.jumpheight;
            double motionZ = 2F;
            this.entity.setDeltaMovement(motionX, motionY, motionZ);
        } else if (this.animation == EntityAnimation.LEAP && this.entity.getDeltaMovement().y < 0) {
            this.animation = EntityAnimation.LEAP_LAND;
            this.launched = true;
        } else if (this.animation == EntityAnimation.LEAP_LAND && (this.entity.isOnGround() || this.entity.isSwimming())) {
            this.animation = EntityAnimation.IDLE;
        }

        if (this.entity.getAnimation() != this.animation.get()) {
            this.entity.setAnimation(this.animation.get());
            this.entity.setAnimationTick(this.prevTick + 1);
        }

        this.prevTick = tick;
    }

    private boolean isAirAbove(BlockPos pos, int xOffset, int zOffset, int multiplier) {
        for(float i = 1; i != 5; i++) {
            if (this.entity.level.getBlockState(pos.offset(xOffset * multiplier, i, zOffset * multiplier)).getBlock() == Blocks.AIR) {
                this.jumpheight = yeetheight + ((i/2)-0.5f);
                return true;
            }
        }
        return false;
    }
}
