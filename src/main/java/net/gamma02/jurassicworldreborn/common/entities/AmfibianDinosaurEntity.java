package net.gamma02.jurassicworldreborn.common.entities;

import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.ai.DinosaurMoveHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class AmfibianDinosaurEntity extends DinosaurEntity {
    private boolean getOut = false;
    private boolean getInWater = false;
//    private PathNavigate navigateSwimmer = new PathNavigateSwimmer(this,world);TODO:AI
//    private PathNavigate navigateLand = new PathNavigateGround(this, world);
    private int waterTicks;
    private int landTicks;
    public AmfibianDinosaurEntity(Level world) {
        super(world);
//        this.tasks.removeTask(new DinosaurWanderEntityAI(this, 0.8D, 2, 10));TODO:AI
//        this.tasks.addTask(10, new EntityAIFindWater(this, 1, 2, 30));
//        this.tasks.addTask(10, new Wander(this,2, 10, 2));
//        this.tasks.addTask(5, new MoveUnderwaterEntityAI(this));
        this.moveControl = new AmfibianDinosaurEntity.SwimmingMoveHelper();
    }

    @Override
    public void tick() {
        int air = this.getAirSupply();
        if(this.isAlive()) {
            if (this.isInWater()) {
                waterTicks++;
//                this.navigation = navigateSwimmer;
                if (waterTicks >= 120) {
                    this.getOut = true;
                }
                getInWater = false;
                this.setAirSupply(300);
            } else {
                --air;
                this.setAirSupply(air);
                if(this.getAirSupply() <= 40) {
                    getInWater = true;
                }

                if (this.getAirSupply() <= -20) {
                    this.setAirSupply(0);
                    this.hurt(DamageSource.DROWN, 2.0F);
                }
                waterTicks = 0;
                getOut = false;
            }

//            if(this.getOut) {
//                this.navigator = navigateLand;
//            }

            if(!this.isInWater()) {
                landTicks++;
                if(landTicks > 50) {
                    this.getInWater = true;
                }
            } else {
                landTicks=0;
            }
        }



        super.tick();
    }

    @Override
    public void travel(Vec3 vec) {
        float strafe = (float) vec.x;
        float vertical = (float) vec.y;
        float forward = (float) vec.z;
        if (this.isEffectiveAi() && this.isInWater() && !this.isCarcass() && !this.getOut) {
            this.moveRelative(strafe, new Vec3(vertical, forward, 0.1F));
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, 0.7, 0.7));
        } else {
            super.travel(new Vec3(strafe, vertical, forward));
        }
    }

    class SwimmingMoveHelper extends DinosaurMoveHelper {
        private final AmfibianDinosaurEntity swimmingEntity = AmfibianDinosaurEntity.this;

        public SwimmingMoveHelper() {
            super(AmfibianDinosaurEntity.this);
        }

        @Override
        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO && !this.swimmingEntity.getNavigation().isDone() && this.swimmingEntity.isInWater() && this.swimmingEntity.getOut) {
                double distanceX = this.wantedX - this.swimmingEntity.getX();
                double distanceY = this.wantedY - this.swimmingEntity.getY();
                double distanceZ = this.wantedZ - this.swimmingEntity.getZ();
                double distance = Math.abs(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
                distance = Mth.sqrt((float) distance);
                distanceY /= distance;
                float f = (float) (Math.atan2(distanceZ, distanceX) * 180.0D / Math.PI) - 90.0F;
                this.swimmingEntity.setYRot(this.rotlerp(this.swimmingEntity.getYRot(), f, 30.0F));
                this.swimmingEntity.setSpeed((float) (this.swimmingEntity.getAttributeValue(Attributes.MOVEMENT_SPEED) * this.speedModifier));
                this.swimmingEntity.setDeltaMovement( this.swimmingEntity.getDeltaMovement().add(0, (double) this.swimmingEntity.getSpeed() * distanceY * 0.05D, 0));
            } else {
                super.tick();
            }
        }
    }

//    class MoveUnderwaterEntityAI extends EntityAIBase {
//        private AmfibianDinosaurEntity swimmingEntity;
//        private double xPosition;
//        private double yPosition;
//        private double zPosition;
//
//        public MoveUnderwaterEntityAI(AmfibianDinosaurEntity entity) {
//            this.swimmingEntity = entity;
//            this.setMutexBits(Mutex.MOVEMENT);
//        }
//
//        @Override
//        public boolean shouldExecute() {
//            if (this.swimmingEntity.getRandom().nextFloat() < 0.50 && this.swimmingEntity.isBusy()) {
//                return false;
//            }
//            Vec3d target;
//            if(swimmingEntity.getOut) {
//                target = RandomPositionGenerator.getLandPos(this.swimmingEntity, 6, 6);
//            } else {
//                target = RandomPositionGenerator.findRandomTarget(this.swimmingEntity, 6, 6);
//            }
//
//            if (target == null) {
//                return false;
//            } else {
//                this.xPosition = target.x;
//                this.yPosition = target.y;
//                this.zPosition = target.z;
//                return true;
//            }
//        }
//
//        @Override
//        public boolean shouldContinueExecuting() {
//            return !this.swimmingEntity.getNavigator().noPath();
//        }
//
//        @Override
//        public void startExecuting() {
//            this.swimmingEntity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, 1.0D);
//        }
//
//        @Override
//        public boolean isInterruptible() {
//            return false;
//        }
//    }

//    class Wander extends EntityAIWanderNearWater {
//        public final AmfibianDinosaurEntity creature;
//        public Wander(AmfibianDinosaurEntity creatureIn, double speedIn, int chance, int walkradius) {
//            super(creatureIn, speedIn, chance, walkradius);
//            this.creature = creatureIn;
//        }
//
//        @Override
//        public boolean shouldExecute() {
//            if(creature.getInWater) {
//                return false;
//            }
//            return super.shouldExecute();
//        }
//    }
}
