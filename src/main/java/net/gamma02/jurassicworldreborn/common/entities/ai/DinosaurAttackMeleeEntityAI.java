package net.gamma02.jurassicworldreborn.common.entities.ai;

import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;

import java.util.EnumSet;

public class DinosaurAttackMeleeEntityAI extends Goal {
    Level world;
    protected DinosaurEntity attacker;
    /** An amount of decrementing ticks that allows the entity to attack once the tick reaches 0. */
    protected int attackTick;
    /** The speed with which the mob will approach the target */
    double speedTowardsTarget;
    /** When true, the mob will continue chasing its target, even if it can't find a path to them right now. */
    boolean longMemory;
    /** The PathEntity of our entity. */
    Path path;
    private int delayCounter;
    private double targetX;
    private double targetY;
    private double targetZ;
    protected final int attackInterval = 15;
    private int failedPathFindingPenalty = 0;
    private final boolean canPenalize = false;

    public DinosaurAttackMeleeEntityAI(DinosaurEntity creature, double speedIn, boolean useLongMemory)
    {
        this.attacker = creature;
        this.world = creature.level;
        this.speedTowardsTarget = speedIn;
        this.longMemory = useLongMemory;
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean canUse()
    {
        LivingEntity entitylivingbase = this.attacker.getAttackTarget();

        if (entitylivingbase == null)
        {
            return false;
        }
        else if (!entitylivingbase.isAlive())
        {
            return false;
        }
        else {
            double folllowRange = this.attacker.getAttributeValue(Attributes.FOLLOW_RANGE);
            if (canPenalize) {
                if (--this.delayCounter <= 0) {
                    this.path = this.attacker.getNavigation().createPath(entitylivingbase, (int)folllowRange/*cast this in refrences and not in the definition for  f l e x i b i l i t y*/);
                    this.delayCounter = 4 + this.attacker.getRandom().nextInt(7);
                    return this.path != null;
                } else {
                    return true;
                }
            }
            this.path = this.attacker.getNavigation().createPath(entitylivingbase, (int)folllowRange);

            if (this.path != null) {
                return true;
            } else {
                return this.getAttackReachSqr(entitylivingbase) >= this.attacker.distanceToSqr(entitylivingbase.position());
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse()
    {
        LivingEntity entitylivingbase = this.attacker.getAttackTarget();

        if (entitylivingbase == null)
        {
            return false;
        }
        else if(this.attacker != null && entitylivingbase instanceof DinosaurEntity && ((DinosaurEntity) entitylivingbase).isCarcass() && entitylivingbase.isDeadOrDying()) {
            return false;
        }
        else if (!entitylivingbase.isAlive())
        {
            return false;
        }
        else if (!this.longMemory)
        {
            return !this.attacker.getNavigation().isDone();
        }
        else if (!this.attacker.isWithinRestriction(new BlockPos(entitylivingbase.position())))
        {
            return false;
        }
        else
        {
            return !(entitylivingbase instanceof Player) || !((Player)entitylivingbase).isSpectator() && !((Player)entitylivingbase).isCreative();
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start()
    {
        this.attacker.getNavigation().moveTo(this.path, this.speedTowardsTarget);
        this.delayCounter = 0;
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop()
    {
        LivingEntity entitylivingbase = this.attacker.getAttackTarget();

        if (entitylivingbase instanceof Player && (((Player)entitylivingbase).isSpectator() || ((Player)entitylivingbase).isCreative()))
        {
            this.attacker.setTarget(null);
        }

        this.attacker.getNavigation().stop();
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick()//why were these called "update____" before? it was a bit more descriptive, but tick is more internally consistant... - gamma_02
    {
        LivingEntity entitylivingbase = this.attacker.getAttackTarget();
        if(entitylivingbase != null) {
            this.attacker.getLookControl().setLookAt(entitylivingbase, 30.0F, 30.0F);
            double d0 = this.attacker.distanceToSqr(entitylivingbase.position());
            --this.delayCounter;

            if ((this.longMemory || this.attacker.getSensing().hasLineOfSight(entitylivingbase)) && this.delayCounter <= 0 && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || entitylivingbase.distanceToSqr(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.attacker.getRandom().nextFloat() < 0.05F)) {
                this.targetX = entitylivingbase.getX();
                this.targetY = entitylivingbase.getY();//this was set to the minimum Y of the bounding box, but those aren't the same anymore. hope this doesn't cause an issue. - gamma_02
                this.targetZ = entitylivingbase.getZ();
                this.delayCounter = 4 + this.attacker.getRandom().nextInt(7);

                if (this.canPenalize) {
                    this.delayCounter += failedPathFindingPenalty;
                    if (this.attacker.getNavigation().getPath() != null) {
                        net.minecraft.world.level.pathfinder.Node finalPathPoint = this.attacker.getNavigation().getPath().getEndNode();
                        if (finalPathPoint != null && entitylivingbase.distanceToSqr(finalPathPoint.x, finalPathPoint.y, finalPathPoint.z) < 1)
                            failedPathFindingPenalty = 0;
                        else
                            failedPathFindingPenalty += 10;
                    } else {
                        failedPathFindingPenalty += 10;
                    }
                }

                if (d0 > 1024.0D) {
                    this.delayCounter += 10;
                } else if (d0 > 256.0D) {
                    this.delayCounter += 5;
                }

                if (!this.attacker.getNavigation().moveTo(entitylivingbase, this.speedTowardsTarget)) {
                    this.delayCounter += 15;
                }
            }

            this.attackTick = Math.max(this.attackTick - 1, 0);
            this.checkAndPerformAttack(entitylivingbase, d0);
        }
    }

    protected void checkAndPerformAttack(LivingEntity p_190102_1_, double p_190102_2_)
    {
        double d0 = this.getAttackReachSqr(p_190102_1_);

        if (p_190102_2_ <= d0 && this.attackTick <= 0)
        {
            this.attackTick = 20;
            this.attacker.swing(InteractionHand.MAIN_HAND);
            this.attacker.doHurtTarget(p_190102_1_);
        }
    }

    protected double getAttackReachSqr(LivingEntity attackTarget) {
        if (attackTarget.getBoundingBox().intersects(this.attacker.getBoundingBox().inflate(1))) {
            return 1024.0;
        }
        double grownWidth = this.attacker.getBbWidth();
        return grownWidth * grownWidth;
    }


}
