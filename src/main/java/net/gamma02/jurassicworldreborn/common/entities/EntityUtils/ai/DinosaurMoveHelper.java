package net.gamma02.jurassicworldreborn.common.entities.EntityUtils.ai;

import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.NodeEvaluator;

public class DinosaurMoveHelper extends MoveControl {
    private DinosaurEntity dinosaur;

    public DinosaurMoveHelper(DinosaurEntity entity) {
        super(entity);
        this.dinosaur = entity;
    }

    @Override
    public void tick() {
        float speedAttribute = (float) this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED);

        if (this.operation == Operation.STRAFE) {
            float moveSpeed = (float) (this.speedModifier * speedAttribute);
            float forward = this.strafeForwards;
            float strafe = this.strafeRight;
            float moveDistance = Mth.sqrt(forward * forward + strafe * strafe);

            moveDistance = moveSpeed / moveDistance;
            forward = forward * moveDistance;
            strafe = strafe * moveDistance;
            float rotationX = Mth.sin(this.mob.getYRot() * 0.017453292F);
            float rotationZ = Mth.cos(this.mob.getYRot() * 0.017453292F);
            float moveX = forward * rotationZ - strafe * rotationX;
            float moveZ = strafe * rotationZ + forward * rotationX;
            PathNavigation navigator = this.mob.getNavigation();

            NodeEvaluator nodeProcessor = navigator.getNodeEvaluator();

            if (nodeProcessor.getBlockPathType(this.mob.level, Mth.floor(this.mob.getX() + moveX), Mth.floor(this.mob.getY()), Mth.floor(this.mob.getZ() + moveZ)) != BlockPathTypes.WALKABLE) {
                this.strafeForwards = 0.9F;
                this.strafeRight = 0.0F;
                moveSpeed = speedAttribute;
            }

            this.mob.setSpeed(moveSpeed);
            this.mob.setZza(this.strafeForwards);
            this.mob.setXxa(this.strafeRight);
            this.operation = Operation.WAIT;
        } else if (this.operation == Operation.MOVE_TO) {
            this.operation = MoveControl.Operation.WAIT;
            double deltaX = this.getWantedX() - this.mob.getX();
            double deltaZ = this.getWantedZ() - this.mob.getZ();
            double deltaY = this.getWantedY() - this.mob.getY();
            double delta = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;

            if (delta < 2.5E-07) {
                this.mob.setSpeed(0.0F);
                return;
            }

            float movementDirection = (float) ((Mth.atan2(deltaZ, deltaX) * (180D / Math.PI)) - 90.0F);
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), movementDirection, (float) dinosaur.getRotationAngle()));
            this.mob.setSpeed((float) (this.speedModifier * speedAttribute));

            if (deltaY > this.mob.maxUpStep && deltaX * deltaX + deltaZ * deltaZ < Math.max(1.0F, this.mob.getBbWidth() + (deltaY * deltaY))) {
                JumpControl jumpHelper = this.mob.getJumpControl();
                if (jumpHelper instanceof DinosaurJumpHelper && !this.mob.isInLava() && !this.mob.isInWater()) {
                    ((DinosaurJumpHelper) jumpHelper).jump((int) Math.ceil(deltaY));
                } else {
                    jumpHelper.jump();
                }
            }
        } else {
            this.mob.setZza(0.0F);
        }
    }
}
