package net.gamma02.jurassicworldreborn.common.entities.ai;

import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.MicroraptorEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.Random;

public class RaptorClimbTreeAI extends Goal {//this is a lot more complicated than i thought it would be, no glitches *ever* came out of this - gamma_02
    private static final int CLIMB_INTERVAL = 1200;
    private static final int MAX_TREE_HEIGHT = 14;

    private final MicroraptorEntity entity;
    private final double movementSpeed;
    private final Level world;

    private Path path;

    private BlockPos targetTrunk;
    private Direction approachSide;

    private double targetX;
    private double targetY;
    private double targetZ;

    private boolean gliding;
    private boolean active;
    private boolean reachedTarget;

    private int lastActive = -CLIMB_INTERVAL;

    public RaptorClimbTreeAI(MicroraptorEntity entity, double speed) {
        this.entity = entity;
        this.movementSpeed = speed;
        this.world = entity.level;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));//animation flags + movement, but not twice
    }

    @Override
    public boolean canUse() {
        if (this.active || (this.entity.tickCount - this.lastActive) < 1) {//this could use some comments/neu - gamma_02
            return false;
        }
        BlockPos pos = new BlockPos(this.entity.getX(), this.entity.getBoundingBox().minY, this.entity.getZ());
        Random rand = this.entity.getRNG();
        for (int i = 0; i < 20; ++i) {
            BlockPos target = pos.offset(rand.nextInt(14) - 7, -5, rand.nextInt(14) - 7);
            for (int iteration = 0; iteration <= 15; iteration++) {
                target = target.above();
                BlockState state = this.world.getBlockState(target);
                if (state.getMaterial() == Material.LEAVES || state.getMaterial() == Material.WOOD) {//if the material is a part of a tree
                    for (Direction direction : Direction.Plane.HORIZONTAL) {
                        BlockPos offsetTarget = target.relative(direction);
                        if (!this.world.getBlockState(offsetTarget).entityCanStandOnFace(world, offsetTarget, this.entity, Direction.DOWN)) {
                            boolean canTravel = true;
                            boolean woodFound = false;
                            int height = 0;//why is this out here and not in the loop? this seems like a decompiler artifact - gamma_02
                            for (; height < MAX_TREE_HEIGHT; height++) {
                                BlockPos trunkPos = target.above(height);
                                BlockPos climbPos = offsetTarget.above(height);
                                BlockState climbState = this.world.getBlockState(climbPos);
                                if (!climbState.isAir() && !(climbState.getBlock() instanceof LeavesBlock)) {
                                    canTravel = false;
                                    break;
                                }
                                BlockState trunkState = this.world.getBlockState(trunkPos);
                                if (!(trunkState.getMaterial() == Material.WOOD)) {
                                    break;
                                }
                                woodFound = true;
                            }
                            if (canTravel && woodFound) {
                                float offsetX = direction.getStepX() * (this.entity.getBbWidth() + 0.25F) + 0.1F;
                                float offsetZ = direction.getStepZ() * (this.entity.getBbWidth() + 0.25F) + 0.1F;
                                this.targetTrunk = target;
                                this.targetX = target.getX() + 0.5F + offsetX;
                                this.targetY = target.getY();
                                this.targetZ = target.getZ() + 0.5F + offsetZ;
                                this.approachSide = direction;
                                AABB bounds = this.getBoundsAtPos(this.targetX, this.targetY, this.targetZ);
                                if (!this.entity.level.noCollision(bounds)) {
                                    this.path = this.entity.getNavigation().createPath(this.targetX, this.targetY, this.targetZ, 0);
                                    if (this.path != null) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                } else if (state.isAir()) {
                    break;
                }
            }
        }
        return false;
    }

    private AABB getBoundsAtPos(double targetX, double targetY, double targetZ) {
        float widthOffset = this.entity.getBbWidth() / 2.0F;
        return new AABB(targetX - widthOffset, targetY, targetZ - widthOffset, targetX + widthOffset, targetY + this.entity.getBbHeight(), targetZ + widthOffset);
    }

    @Override
    public void start() {
        this.entity.getNavigation().moveTo(this.path, this.movementSpeed);
        this.active = true;
        this.gliding = false;
    }

    @Override
    public void tick() {
        if (this.reachedTarget) {
            BlockPos currentTrunk = new BlockPos(this.targetTrunk.getX(), this.entity.getY(), this.targetTrunk.getZ());
            if (!this.gliding && this.world.getBlockState(currentTrunk).isAir()) {
                Random random = this.entity.getRNG();
                if(random.nextFloat() < 0.3f) {
                    this.entity.push(-this.approachSide.getStepX() * 0.1F, 0.2F, -this.approachSide.getStepZ() * 0.1F);
                } else {
                    Vec3 pos = null;
                    for(int i = 0; i < 100; i++) {
                        double x = (random.nextFloat() - 0.5) * 45;
                        double z = (random.nextFloat() - 0.5) * 45;
                        Vec3 vec = this.entity.position().add(x, 0, z);
                        BlockPos position = new BlockPos(vec);
                        vec = vec.add(0.5D, -vec.y + getTopNonCollidingPos(position).getY() + 0.5D, 0.5D);
                        BlockState targetState = this.entity.level.getBlockState(position);
                        if(this.entity.position().distanceTo(vec) > 20D && targetState.getBlock() instanceof LeavesBlock) {
                            pos = vec;
                            break;
                        }
                    }
                    if(pos == null) {
                        for(int i = 0; i < 100; i++) {
                            double x = (random.nextFloat() - 0.5) * 45;
                            double z = (random.nextFloat() - 0.5) * 45;
                            Vec3 vec = this.entity.position().add(x, 0, z);
                            BlockPos position = new BlockPos(vec);
                            vec = vec.add(0.5D, -vec.y + getTopNonCollidingPos(position).getY() + 0.5D, 0.5D);
                            BlockState targetState = this.entity.level.getBlockState(position);
                            if(this.entity.position().distanceTo(vec) > 20D && !targetState.getMaterial().isLiquid()) {
                                pos = vec;
                                break;
                            }
                        }
                    }
                    if(pos != null) {
                        this.entity.setGlidingTo(pos);
//                        this.world.setBlockState(new BlockPos(pos), Blocks.STONE.getDefaultState());
                        this.entity.push((pos.x - this.entity.getX()) * 0.02, 0.2F, (pos.z - this.entity.getZ()) * 0.02);

                        this.gliding = true;
                        this.entity.setAnimation(EntityAnimation.GLIDING.get());
                    }
                }
                this.active = false;
            } else {
                BlockPos testTrunk = targetTrunk;
                boolean accepted = true;
                do {
                    if(world.getBlockState(testTrunk).entityCanStandOnFace(world, testTrunk, this.entity, Direction.DOWN)) {
                        accepted = false;
                    }
                    BlockState state = world.getBlockState(testTrunk);
                    if (state.getMaterial() != Material.LEAVES && state.getMaterial() != Material.WOOD) {
                        accepted = false;
                    }
                    testTrunk = testTrunk.above();
                } while(world.getBlockState(testTrunk).entityCanStandOnFace(world, testTrunk, this.entity, Direction.DOWN));
                if(accepted) {
                    this.entity.getMoveControl().setWantedPosition(this.targetX, this.entity.getY(), this.targetZ, this.movementSpeed);
                    this.entity.setAnimation(EntityAnimation.CLIMBING.get());
                    if (this.entity.horizontalCollision || world.getBlockState(testTrunk).entityCanStandOnFace(world, testTrunk, this.entity, Direction.DOWN)) {
                        this.entity.setDeltaMovement(this.entity.getDeltaMovement().subtract(0, (this.entity.getDeltaMovement().y()-0.3), 0));
                        this.entity.setPos(this.targetX, this.entity.getY(), this.targetZ);
                        if (this.entity.distanceToSqr(new Vec3(currentTrunk.getX(), currentTrunk.getY(), currentTrunk.getZ())) > 2.0) {
                            this.active = false;
                        }
                    }
                }
                if (this.entity.verticalCollision && !this.gliding) {
                    BlockPos top = new BlockPos(this.entity.getX(), this.entity.getBoundingBox().maxY + 0.1, this.entity.getZ());
                    if (this.isBlockLeaves(top)) {
                        if (this.world.getBlockState(top.above()).isAir()) {
                            this.entity.setPos(this.targetX, Mth.ceil(this.entity.getY() + 1) + 0.2, this.targetZ);
                        } else {
                            this.entity.setPos(this.targetX, this.entity.getY() + 0.2, this.targetZ);
                        }
                    }
                }
                this.entity.setOnGround(true);
            }
        } else {
            if (this.path.isDone()) {
                this.entity.setAnimation(EntityAnimation.START_CLIMBING.get());
                this.reachedTarget = true;
                Vec3 origin = this.entity.position().add(0.0, this.entity.getEyeHeight(), 0.0);
                BlockHitResult traceResult = this.world.clip(new ClipContext(origin, new Vec3(this.targetX, this.targetY, this.targetZ), ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, this.entity));
                if (traceResult != null && traceResult.getType() == BlockHitResult.Type.BLOCK) {
                    this.path = null;
                }
            }
        }
    }

    private boolean isBlockLeaves(BlockPos pos) {
        BlockState state = this.world.getBlockState(pos);
        return state.getMaterial() == Material.LEAVES;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.path == null || (this.world.noCollision(this.entity) && !this.isBlockLeaves(this.entity.getOnPos()))) {
            return false;
        }
        if (!this.reachedTarget) {
            if (!this.path.isDone() && !this.path.sameAs(this.entity.getNavigation().getPath())) {
                return false;
            }
        }
        return this.active && !this.gliding;
    }

    @Override
    public void stop() {
        this.lastActive = this.entity.tickCount;
        this.path = null;
        this.targetTrunk = null;
        this.active = false;
        this.reachedTarget = false;
    }
    private BlockPos getTopNonCollidingPos(BlockPos pos) {//yoinked from mojang code - gamma_02
        int pX = pos.getX(); int pZ = pos.getZ(); Level pLevel = this.world;
        int i = pLevel.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pX, pZ);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(pX, i, pZ);
        if (pLevel.dimensionType().hasCeiling()) {
            do {
                blockpos$mutableblockpos.move(Direction.DOWN);
            } while(!pLevel.getBlockState(blockpos$mutableblockpos).isAir());

            do {
                blockpos$mutableblockpos.move(Direction.DOWN);
            } while(pLevel.getBlockState(blockpos$mutableblockpos).isAir() && blockpos$mutableblockpos.getY() > pLevel.getMinBuildHeight());
        }


        BlockPos blockpos = blockpos$mutableblockpos.below();
        if (pLevel.getBlockState(blockpos).isPathfindable(pLevel, blockpos, PathComputationType.LAND)) {
            return blockpos;
        }


        return blockpos$mutableblockpos.immutable();
    }
}
