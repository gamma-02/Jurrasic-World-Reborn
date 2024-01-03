package net.gamma02.jurassicworldreborn.common.entities.ai;

import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurHandler;
import net.minecraft.world.entity.LivingEntity;

import java.util.EnumSet;

public class LeapingMeleeEntityAI extends DinosaurAttackMeleeEntityAI {
    public LeapingMeleeEntityAI(DinosaurEntity entity, double speed) {
        super(entity, speed, false);
        this.setFlags(EnumSet.of(Flag.TARGET));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.attacker.getAttackTarget();

        return super.canUse() && target != null && this.isInRange(target);
    }

    @Override
    public boolean canContinueToUse() {
        return this.canUse() && super.canContinueToUse();
    }

    private boolean isInRange(LivingEntity target) {
        float distance = this.attacker.distanceTo(target);
        float range = this.attacker.getBbWidth() * 6.0F;
        return distance < range - 1.0F || distance > range;
    }
}