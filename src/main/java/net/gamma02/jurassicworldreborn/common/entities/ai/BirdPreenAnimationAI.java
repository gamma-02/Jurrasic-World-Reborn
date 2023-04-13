package net.gamma02.jurassicworldreborn.common.entities.ai;

import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class BirdPreenAnimationAI extends Goal {
    protected DinosaurEntity dinosaur;

    public BirdPreenAnimationAI(IAnimatedEntity entity) {//WHY THOUGH - gamma_02
        super();
        this.dinosaur = (DinosaurEntity) entity;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        return !this.dinosaur.isBusy() && this.dinosaur.getRandom().nextDouble() < 0.005;
    }

    @Override
    public void start() {
        this.dinosaur.setAnimation(EntityAnimation.PREENING.get());
    }

    @Override
    public boolean canContinueToUse() {
        return false;
    }
}