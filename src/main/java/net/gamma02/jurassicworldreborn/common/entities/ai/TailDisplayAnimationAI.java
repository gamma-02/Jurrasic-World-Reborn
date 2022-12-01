package net.gamma02.jurassicworldreborn.common.entities.ai;

import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class TailDisplayAnimationAI extends Goal {
    protected DinosaurEntity entity;

    public TailDisplayAnimationAI(DinosaurEntity entity) {
        super();
        this.entity = entity;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        return !this.entity.isBusy() && this.entity.getRandom().nextDouble() < 0.01;
    }

    @Override
    public void start() {
        this.entity.setAnimation(EntityAnimation.TAIL_DISPLAY.get());
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }
}
