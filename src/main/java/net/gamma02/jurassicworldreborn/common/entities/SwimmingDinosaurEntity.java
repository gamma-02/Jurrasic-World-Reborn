package net.gamma02.jurassicworldreborn.common.entities;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.Vec3;

public abstract class SwimmingDinosaurEntity extends DinosaurEntity {
    public SwimmingDinosaurEntity(Level world, EntityType type) {
        super(world, type);
        this.moveControl = new SwimmingDinosaurEntity.SwimmingMoveHelper();
//        this.addTask(1, new MoveUnderwaterEntityAI(this));TODO:AI
//        this.navigator = new PathNavigateSwimmer(this, world);
    }

    @Override
    public void tick() {
        int air = this.getAirSupply();
        super.tick();

        if (this.isAlive() && !this.isInWater()) {
            --air;
            this.setAirSupply(air);

            if (this.getAirSupply() == -20) {
                this.setAirSupply(0);
                this.hurt(DamageSource.DROWN, 2.0F);
            }
        } else {
            this.setAirSupply(300);
        }
    }

//    public boolean isNotColliding()
//    {
//        return this.world.checkNoEntityCollision(this.getEntityBoundingBox(), this);
//    }

//    @Override
//    protected boolean isMovementNoisy() {
//        return false;
//    }



    /*TODO: make sure this works */ //<- lets hope it works

    @Override
    public void travel(Vec3 vec) {
        float strafe = (float)vec.x;
        float vertical = (float)vec.y;
        float forward = (float) vec.z;
        if (!this.level.isClientSide && this.isInWater() && !this.isCarcass()) {
            this.moveRelative(strafe, new Vec3(vertical, forward, 0.1F));
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, 0.7, 0.7));
        } else {
            super.travel(vec);
        }
    }


    class SwimmingMoveHelper extends MoveControl {
        private final SwimmingDinosaurEntity swimmingEntity = SwimmingDinosaurEntity.this;

        public SwimmingMoveHelper() {
            super(SwimmingDinosaurEntity.this);
        }

        @Override
        public void tick() {
            if (this.operation == Operation.MOVE_TO && !this.swimmingEntity.getNavigation().isDone()) {
                double distanceX = this.wantedX - this.swimmingEntity.getX();
                double distanceY = this.wantedX - this.swimmingEntity.getY();
                double distanceZ = this.wantedZ - this.swimmingEntity.getZ();
                double distance = Math.abs(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
                distance = Math.sqrt(distance);
                distanceY /= distance;
                float f = (float) (Math.atan2(distanceZ, distanceX) * 180.0D / Math.PI) - 90.0F;
                this.swimmingEntity.setYRot(this.rotlerp(this.swimmingEntity.getYRot(), f, dinosaur.getRotationAngle()));
                this.swimmingEntity.setSpeed((float) (this.swimmingEntity.getAttribute(Attributes.MOVEMENT_SPEED).getValue() * this.speedModifier));
                this.swimmingEntity.setDeltaMovement(this.swimmingEntity.getDeltaMovement().add(0, (double) this.swimmingEntity.getSpeed() * distanceY * 0.05D, 0));
            } else {
                this.swimmingEntity.setSpeed(0.0F);
            }
        }
    }
}