package net.gamma02.jurassicworldreborn.common.entities;

import com.mojang.math.Vector3d;
import net.gamma02.jurassicworldreborn.common.util.ai.OnionTraverser;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public abstract class FlyingDinosaurEntity extends DinosaurEntity implements EntityFlying, Animal {

    private int ticksOnFloor;
    private int ticksInAir;
    private boolean takingOff;
    private int feederSearchTick;
    private BlockPos closestFeeder;
    public boolean shouldLand;

    public FlyingDinosaurEntity(Level world) {
        super(world);
        this.moveHelper = new FlyingDinosaurEntity.FlyingMoveHelper();
        this.tasks.addTask(1, new FlyingDinosaurEntity.AIFlyLand());
        this.tasks.addTask(2, new FlyingDinosaurEntity.AIStartFlying());
        this.tasks.addTask(0, new FlyingDinosaurEntity.AIRandomFly());
        this.tasks.addTask(0, new FlyingDinosaurEntity.AIWander());
        this.tasks.addTask(2, new AILookAround());
        this.tasks.addTask(0, new DinosaurAttackMeleeEntityAI(this,1,true));
        this.doesEatEggs(true);
        this.doTarget();
    }

    protected void doTarget(){
        this.target(LeptictidiumEntity.class, HypsilophodonEntity.class, MicroraptorEntity.class, MicroceratusEntity.class, CompsognathusEntity.class);
    }

    @Override
    public void onEntityUpdate() {
        if(!this.onGround && this.getAnimation() == EntityAnimation.SLEEPING.get()) {
            this.setMoveVertical(-5);
        }

        if(this.getMetabolism().isStarving() || this.getMetabolism().isThirsty()) {
            this.shouldLand = true;
        }
        if(isOnGround()) {
            ticksInAir = 0;
            ticksOnFloor++;
        } else {
            ticksInAir++;
            ticksOnFloor = 0;
        }

        if(ticksInAir > 150) {
            this.takingOff = false;
        }

        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch);
        super.onEntityUpdate();
    }

    public boolean isOnGround() {
        return !this.world.getCollisionBoxes(this, this.getEntityBoundingBox().grow(0.24d)).isEmpty() && !takingOff || this.isDead || this.isCarcass() || this.isInWater();
    }

    public void startTakeOff() {
        takingOff = true;
    }



    @Override
    public int calculateFallDamage(float distance, float damageMultiplier) {
        if(!this.isOnGround()) {
            return super.calculateFallDamage(distance, damageMultiplier);
        }
        return 0;
    }


    @Override
    public void travel(Vec3 vec) {
        float strafe = Float.parseFloat(Double.toString(vec.x));
        float vertical = Float.parseFloat(Double.toString(vec.z));
        float forward = Float.parseFloat(Double.toString(vec.y));
        if(!this.tranqed && !isOnGround()) {
            if (this.inWater()) {
                this.moveRelative(strafe, new Vec3(forward, 0.02F, 0F));
                this.move(MoverType.SELF, this.getDeltaMovement());

                this.setDeltaMovement(this.getDeltaMovement().multiply(0.8, 0.8, 0.8));
                return;
            } else if (this.inLava()) {
                this.moveRelative(strafe, new Vec3(forward, 0.02F, 0F));
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.5, 0.5, 0.5));

                return;
            } else {
                float friction = 0.91F;

                if (this.onGround) {
                    friction = this.level.getBlockState(new BlockPos(Mth.floor(this.getX()), Mth.floor(this.getBoundingBox().minY) - 1, Mth.floor(this.getZ()))).getBlock().getFriction() * 0.91F;
                }

                float f3 = 0.16277136F / (friction * friction * friction);
                this.moveRelative(strafe, new Vec3(forward, this.onGround ? f3 * 0.1F : 0.02F, 0F));
                friction = 0.91F;

                if (this.onGround) {
                    friction = this.level.getBlockState(new BlockPos(Mth.floor(this.getX()), Mth.floor(this.getBoundingBox().minY) - 1, Mth.floor(this.getZ()))).getBlock().getFriction() * 0.91F;
                }

                this.move(MoverType.SELF, this.getDeltaMovement());
                this.setDeltaMovement(this.getDeltaMovement().multiply(friction, friction, friction));
            }

            this.animationSpeedOld = this.animationSpeed;
            double moveX = this.getX() - this.xo;
            double moveZ = this.getZ() - this.zo;
            float dist = Mth.sqrt((float) (moveX * moveX + moveZ * moveZ)) * 4.0F;

            if (dist > 1.0F) {
                dist = 1.0F;
            }

            this.animationSpeed += (dist - this.animationSpeed) * 0.4F;
            this.animationPosition += this.animationSpeed;
            return;
        }
        super.travel(vec);
    }

    @Override
    public boolean isOnLadder() {
        return false;
    }

    private boolean isCourseTraversable(Vec3 loc) {
        double distance = this.position().distanceTo(loc);
        distance++;
        double d4 = (loc.x - this.getX()) / distance;
        double d5 = (loc.y - this.getY()) / distance;
        double d6 = (loc.z - this.getZ()) / distance;
        AABB axisalignedbb = this.getBoundingBox();

        for(int i = 1; (double)i < distance; ++i) {
            axisalignedbb.move(d4, d5, d6);
            if(!this.level.collidesWithSuffocatingBlock(this, axisalignedbb)) {
                return false;
            }
        }

        return true;
    }

//    class AIStartFlying extends EntityAIBase {todo: ai
//        private final FlyingDinosaurEntity dino = FlyingDinosaurEntity.this;
//
//
//        public AIStartFlying() {
//            this.setMutexBits(1);
//        }
//
//        @Override
//        public boolean shouldExecute() {
//            return dino.ticksOnFloor >= 220/*TODO: config this ?*/ && dino.isOnGround() && this.dino.rand.nextFloat() < 0.03F; //TODO: config this value <- I think I should add this to config
//        }
//
//        @Override
//        public boolean shouldContinueExecuting() {
//            return false;
//        }
//
//        @Override
//        public void startExecuting() {
//            this.dino.startTakeOff();
//            this.dino.setAnimation(EntityAnimation.FLYING.get());
//            this.dino.getMoveHelper().setMoveTo(this.dino.getX() + rand.nextFloat(), this.dino.getY() + (rand.nextFloat()*5), this.dino.getZ() + rand.nextFloat(), 2D);
//        }
//
//
//    }
//
//    class AIRandomFly extends EntityAIBase {
//        private final FlyingDinosaurEntity dino = FlyingDinosaurEntity.this;
//
//        public AIRandomFly() {
//            this.setMutexBits(1);
//        }
//
//        @Override
//        public boolean shouldExecute() {
//            if(dino.isOnGround()) {
//                return false;
//            }
//            EntityMoveHelper moveHelper = this.dino.getMoveHelper();
//            if (!moveHelper.isUpdating()) {
//                return true;
//            } else {
//                double moveX = moveHelper.getX() - this.dino.getX();
//                double moveY = moveHelper.getY() - this.dino.getY();
//                double moveZ = moveHelper.getZ() - this.dino.getZ();
//                double distance = moveX * moveX + moveY * moveY + moveZ * moveZ;
//                return distance < 3.0D || distance > 3600.0D;
//            }
//        }
//
//        @Override
//        public boolean shouldContinueExecuting() {
//            return false;
//        }
//
//        @Override
//        public void startExecuting() {//TODO: Fix cosine issues. Not sure why they're happening. Maybe clash with diffrent ai or the task being run multiple times??
//            Vec3d lookVec = new Vec3d(dino.getLookVec().x * 10D, dino.getLookVec().y * 10D, dino.getLookVec().z * 10D).add(new Vec3d(getPosition()));
//            Random random = this.dino.getRNG();
//            for(int i = 0; i < 100; i++) {
//                double destinationX = this.dino.getX() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
//                double destinationY = this.dino.getY() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 8.0F);
//                double destinationZ = this.dino.getZ() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
//                Vec3d vecPos = new Vec3d(destinationX, destinationY, destinationZ);
//                if(dino.isCourseTraversable(vecPos) && Math.abs(MathUtils.cosineFromPoints(vecPos, lookVec, new Vec3d(getPosition()))) < 45D)
//                {
//                    this.dino.getLookHelper().setLookPosition(destinationX, destinationY, destinationZ, this.dino.getHorizontalFaceSpeed(), this.dino.getVerticalFaceSpeed());
//                    this.dino.setAnimation(EntityAnimation.FLYING.get());
//                    this.dino.getMoveHelper().setMoveTo(destinationX, destinationY, destinationZ, 2D);
//                    return;
//                }
//            }
//        }
//    }
//
//    class AIFlyLand extends EntityAIBase {
//        private final FlyingDinosaurEntity dino = FlyingDinosaurEntity.this;
//
//        public AIFlyLand() {
//            this.setMutexBits(1);
//        }
//
//        @Override
//        public boolean shouldExecute() {
//            if(dino.ticksInAir <= 150 && dino.isOnGround() && this.dino.isInWater() && this.dino.isOverWater()) {
//                return false;
//            }
//            EntityMoveHelper moveHelper = this.dino.getMoveHelper();
//            if (!moveHelper.isUpdating() && dino.rand.nextFloat() < 0.1f || !moveHelper.isUpdating() && this.dino.shouldLand) {
//                return true;
//            } else {
//                double moveX = moveHelper.getX() - this.dino.getX();
//                double moveY = moveHelper.getY() - this.dino.getY();
//                double moveZ = moveHelper.getZ() - this.dino.getZ();
//                double distance = moveX * moveX + moveY * moveY + moveZ * moveZ;
//                if(distance < 1.0D || distance > 3600.0D) {
//                    return this.dino.world.getBlockState(this.dino.getPosition().down()).getMaterial() == Material.AIR && this.dino.getRNG().nextFloat() < 0.01f;//TODO: change float value
//                }
//            }
//            return false;
//        }
//
//        @Override
//        public boolean shouldContinueExecuting() {
//            return false;
//        }
//
//        @Override
//        public void startExecuting() {
//            Random random = this.dino.getRNG();
//            double destinationX = this.dino.getX() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
//            double destinationZ = this.dino.getZ() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
//            double destinationY = this.dino.world.getTopSolidOrLiquidBlock(new BlockPos(destinationX, 100, destinationZ)).getY();
//            if(world.getBlockState(new BlockPos(destinationX, destinationY - 1, destinationZ)).getMaterial() != Material.AIR) {
//                this.dino.getMoveHelper().setMoveTo(destinationX, destinationY - 1.8D, destinationZ, 2.0D);
//                this.dino.setAnimation(EntityAnimation.ON_LAND.get());
//            }
//        }
//    }
//
//    class FlyingMoveHelper extends DinosaurMoveHelper {
//        private final FlyingDinosaurEntity parentEntity = FlyingDinosaurEntity.this;
//        private int timer;
//
//        public FlyingMoveHelper() {
//            super(FlyingDinosaurEntity.this);
//        }
//
//
//        @Override
//        public void onUpdateMoveHelper() {
//            if(parentEntity.isOnGround()) {
//                super.onUpdateMoveHelper();
//                return;
//            }
//            if (this.action == EntityMoveHelper.Action.MOVE_TO) {
//                double distanceX = this.getX() - this.parentEntity.getX();
//                double distanceY = this.getY() - this.parentEntity.getY();
//                double distanceZ = this.getZ() - this.parentEntity.getZ();
//                double distance = distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ;
//
//                if (this.timer-- <= 0) {
//                    this.timer += this.parentEntity.getRNG().nextInt(5) + 2;
//                    distance = Mth.sqrt(distance);
//
//                    if (this.isNotColliding(this.getX(), this.getY(), this.getZ(), distance)) {
//                        this.parentEntity.motionX += distanceX / distance * this.speed * 0.1D;
//                        this.parentEntity.motionY += distanceY / distance * this.speed * 0.1D;
//                        this.parentEntity.motionZ += distanceZ / distance * this.speed * 0.1D;
//                    } else {
//                        this.action = EntityMoveHelper.Action.WAIT;
//                    }
//                }
//                if (distance < 2.5E-07) {
//                    this.entity.setMoveForward(0.0F);
//                    return;
//                }
//            }
//
//        }
//
//        private boolean isNotColliding(double x, double y, double z, double distance) {
//            double d0 = (x - this.parentEntity.getX()) / distance;
//            double d1 = (y - this.parentEntity.getY()) / distance;
//            double d2 = (z - this.parentEntity.getZ()) / distance;
//            AABB bounds = this.parentEntity.getBoundingBox();
//
//            for (int i = 1; (double) i < distance; ++i) {
//                bounds = bounds.move(d0, d1, d2);
//
//                if (!this.parentEntity.level.getCollisionBoxes(this.parentEntity, bounds).isEmpty()) {
//                    return false;
//                }
//            }
//
//            return true;
//        }
//    }

    public BlockPos getClosestFeeder() {
        if (this.tickCount - this.feederSearchTick > 200) {
            this.feederSearchTick = this.tickCount;
            OnionTraverser traverser = new OnionTraverser(this.getOnPos(), 32);
            for (BlockPos pos : traverser) {
                BlockState state = this.level.getBlockState(pos);
//                if (state.getBlock() instanceof FeederBlock) {todo:FeederBlockEntities and food
//                    TileEntity tile = this.world.getTileEntity(pos);
//                    if (tile instanceof FeederBlockEntity) {
//                        FeederBlockEntity feeder = (FeederBlockEntity) tile;
//                        if (feeder.canFeedDinosaur(this) && feeder.getFeeding() == null && feeder.openAnimation == 0 && this.isOnGround()) {
//                            Path path = this.getNavigation().createPath(pos, 1);
//                            if (path != null && path.getCurrentPathLength() != 0) {
//                                return this.closestFeeder = pos;
//                            }
//                        }
//                    }
//                }
            }
        }
        return this.closestFeeder;
    }

//    class AILookAround extends EntityAIBase {TODO: entityAI and modernization; goals
//        private final FlyingDinosaurEntity dino = FlyingDinosaurEntity.this;
//
//        public AILookAround() {
//            this.setMutexBits(2);
//        }
//
//        @Override
//        public boolean shouldExecute() {
//            return true;
//        }
//
//        @Override
//        public void updateTask() {
//            if (this.dino.getAttackTarget() == null) {
//                this.dino.renderYawOffset = this.dino.rotationYaw = -((float) Math.atan2(this.dino.motionX, this.dino.motionZ)) * 180.0F / (float) Math.PI;
//            } else {
//                EntityLivingBase attackTarget = this.dino.getAttackTarget();
//                double maxDistance = 64.0D;
//
//                if (attackTarget.getDistance(this.dino) < maxDistance * maxDistance) {
//                    double diffX = attackTarget.getX() - this.dino.getX();
//                    double diffZ = attackTarget.getZ() - this.dino.getZ();
//                    this.dino.renderYawOffset = this.dino.rotationYaw = -((float) Math.atan2(diffX, diffZ)) * 180.0F / (float) Math.PI;
//                }
//            }
//        }
//    }
//
//    class AIWander extends DinosaurWanderEntityAI {
//
//        private final FlyingDinosaurEntity dino = FlyingDinosaurEntity.this;
//
//        public AIWander() {
//            super(FlyingDinosaurEntity.this, 0.8D, 10, RebornConfig.ENTITIES.dinosaurWalkingRadius);
//        }
//
//        @Override
//        public boolean shouldExecute()
//        {
//            if(!this.dino.isOnGround()) {
//                return false;
//            }
//            return super.shouldExecute();
//        }
//
//    }
}

