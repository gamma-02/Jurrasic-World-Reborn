package net.gamma02.jurassicworldreborn.common.entities.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class WanderAroundWaterAI extends Goal
{
    protected Animal entity;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private double speed;
    protected int executionChance;
    private boolean mustUpdate;
    private int walkradius;


    public WanderAroundWaterAI(Animal creatureIn, double speedIn, int chance, int walkradius) {
        this.entity = creatureIn;
        this.speed = speedIn;
        this.executionChance = chance;
        this.walkradius = walkradius;
//        this.setMutexBits(Mutex.MOVEMENT);
        this.setFlags(EnumSet.of(Flag.MOVE));
    }


    @Override
    public boolean canUse()
    {
        if (!this.mustUpdate)
        {
            if (innerShouldStopExcecuting())
            {
                return false;
            }
        }

        if (this.outterShouldExecute())
        {
            overlist:
            for(int i = 0; i < 100; i++) {
                Vec3 vec = getWanderPosition();
                if (vec != null) {
                    for (BlockPos pos : BlockPos.betweenClosed(new BlockPos(vec.add(0, 1, 0)), new BlockPos(vec.add(1, 1, 1)))) {
                        if (this.entity.level.getBlockState(pos).getMaterial() != Material.AIR) {
                            continue overlist;
                        }
                    }
                    this.xPosition = vec.x;
                    this.yPosition = vec.y;
                    this.zPosition = vec.z;
                    this.mustUpdate = false;
                    return true;
                }
            }
        }

        return false;
    }

    protected boolean innerShouldStopExcecuting() { //TODO: merge into one
        return this.entity.getRandom().nextInt(this.executionChance) != 0;
    }

    protected boolean outterShouldExecute() {
        return this.entity.getNavigation().isDone() && this.entity.getTarget() == null && this.entity.level.isDay() && this.entity.getAirSupply() != 0;
    }

    protected Vec3 getWanderPosition() {
        return DefaultRandomPos.getPos(this.entity, walkradius, walkradius);
    }

    @Override
    public boolean canContinueToUse()
    {
        return !this.entity.getNavigation().isDone();
    }

    @Override
    public void start()
    {
        this.entity.getNavigation().moveTo(this.xPosition, this.yPosition, this.zPosition, this.speed);
    }


    public void makeUpdate()
    {
        this.mustUpdate = true;
    }

    public void setExecutionChance(int chance)
    {
        this.executionChance = chance;
    }
}