package net.gamma02.jurassicworldreborn.common.entities.ai;

import com.mojang.math.Vector3d;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.ai.Herd;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.Vector;
import java.util.stream.Collectors;

public class DinosaurWanderEntityAI extends Goal
{
    protected DinosaurEntity entity;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private final double speed;
    protected int executionChance;
    private boolean mustUpdate;
    private final int walkradius;
    private final Herd herd;


    public DinosaurWanderEntityAI(DinosaurEntity creatureIn, double speedIn, int chance, int walkradius)
    {
        this.entity = creatureIn;
        this.herd = entity.herd;
        this.speed = speedIn;
        this.executionChance = chance;
        this.walkradius = walkradius;
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
            overlist://this entire section is so weird wtf - gamma_02
            for(int i = 0; i < 100; i++) {
                Vec3 vec = getWanderPosition();
                if (vec != null) {
                    for (BlockPos pos : BlockPos.betweenClosedStream(new BlockPos(vec.add(0d, 1d, 0d)), new BlockPos(vec.add(1, 2, 1))).collect(Collectors.toList())) {
                        if (this.entity.level.getBlockState(pos).isAir()) {
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

    protected boolean innerShouldStopExcecuting() {//not mine, don't know whos... whelp - gamma_02 //TODO: merge into one
        return this.entity.getRandom().nextInt(this.executionChance) != 0 && !(this.entity.getOrder() == DinosaurEntity.Order.FOLLOW);
    }

    protected boolean outterShouldExecute() {
        return this.entity.getNavigation().isDone() && this.entity.getAttackTarget() == null;
    }

    protected Vec3 getWanderPosition() {
        return LandRandomPos.getPos(this.entity, walkradius + 5, walkradius);
    }

    @Override
    public boolean canContinueToUse()
    {
        return !this.entity.getNavigation().isDone() && !this.entity.isInWater();
    }

    @Override
    public void start()
    {
        if(herd != null) {
            for(DinosaurEntity entity : herd.members) {
                entity.getNavigation().moveTo(this.xPosition + (entity.getRandom().nextDouble()*2), this.yPosition, this.zPosition + (entity.getRandom()
                        .nextDouble()*2), this.speed);
            }
        } else {
            this.entity.getNavigation().moveTo(this.xPosition, this.yPosition, this.zPosition, this.speed);
        }
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
