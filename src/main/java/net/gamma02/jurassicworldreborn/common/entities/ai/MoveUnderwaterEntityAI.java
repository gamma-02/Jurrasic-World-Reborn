package net.gamma02.jurassicworldreborn.common.entities.ai;

import net.gamma02.jurassicworldreborn.common.entities.SwimmingDinosaurEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class MoveUnderwaterEntityAI extends Goal {
    private SwimmingDinosaurEntity swimmingEntity;
    private double xPosition;
    private double yPosition;
    private double zPosition;

    public MoveUnderwaterEntityAI(SwimmingDinosaurEntity entity) {
        this.swimmingEntity = entity;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.swimmingEntity.getRandom().nextFloat() < 0.50) {
            return false;
        }
        Vec3 target = DefaultRandomPos.getPos(this.swimmingEntity, 6, 2);
        if (target == null) {
            return false;
        } else {
            this.xPosition = target.x;
            this.yPosition = target.y;
            this.zPosition = target.z;
            return true;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return !this.swimmingEntity.getNavigation().isDone();
    }

    @Override
    public void start() {
        this.swimmingEntity.getNavigation().moveTo(this.xPosition, this.yPosition, this.zPosition, 1.0D);
    }
}