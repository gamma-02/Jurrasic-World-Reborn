package net.gamma02.jurassicworldreborn.common.entities.EntityUtils.ai;

import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.UUID;

public class Relationship {
    public static final int MAX_SCORE = 1000;

    private final UUID entity;
    private short score;

    public Relationship(UUID entity, short score) {
        this.entity = entity;
        this.score = score;
    }

    public boolean update(DinosaurEntity owner) {
        DinosaurEntity entity = this.get(owner);
        if (entity == null) {
            return true;
        }
        boolean isPreoccupied = owner.getNavigation().isDone() && owner.getAttackTarget() == null;
        double scaleScore = this.score / (double) MAX_SCORE;
        Dinosaur.DinosaurType dinosaurType = owner.getDinosaur().getDinosaurType();
        if (this.score < 0) {
            if (!isPreoccupied && dinosaurType != Dinosaur.DinosaurType.SCARED && owner.getRandom().nextDouble() * scaleScore > 0.3) {
                owner.setTarget(entity);
            }
        } else if (this.score > 0) {
            if ((dinosaurType == Dinosaur.DinosaurType.AGGRESSIVE || dinosaurType == Dinosaur.DinosaurType.NEUTRAL) && entity.getAttackTarget() != null && owner.getRandom().nextDouble() * scaleScore > 0.3) {
                owner.setTarget(entity.getTarget());
            } else if (owner.family == null && !isPreoccupied && owner.getRandom().nextDouble() * scaleScore > 0.6) {
                owner.getNavigation().moveTo(entity, 0.8);
            }
        }
        LivingEntity lastAttacker = owner.getTarget();
        if (lastAttacker != null && (lastAttacker.isDeadOrDying() || (lastAttacker instanceof DinosaurEntity && ((DinosaurEntity) lastAttacker).isCarcass()))) {
            LivingEntity lastAttackerKiller = lastAttacker.getKillCredit();
            if (lastAttackerKiller != null && lastAttackerKiller.getUUID().equals(this.entity)) {
                this.score += 100;
            }
        }
        if (this.score > MAX_SCORE) {
            this.score = MAX_SCORE;
        } else if (this.score < -MAX_SCORE) {
            this.score = -MAX_SCORE;
        }
        return entity == owner;
    }

    public DinosaurEntity get(DinosaurEntity owner) {
        if(!owner.level.isClientSide){
            return (DinosaurEntity) ((ServerLevel) owner.level).getEntity(this.entity);
        }
        return null;
    }

    public void updateHerd(DinosaurEntity owner) {
        if (owner.family == null) {
            DinosaurEntity entity = this.get(owner);
            if (entity != null) {
                double distance = entity.distanceToSqr(owner);
                if (distance < 32) {
                    this.score += 2;
                } else if (this.score > 0 && owner.getRandom().nextDouble() > 0.8) {
                    this.score--;
                }
            }
        }
    }

    public void onAttacked(double damage) {
        this.score -= damage;
    }

    public void setFamily() {
        this.score = MAX_SCORE;
    }

    @Override
    public int hashCode() {
        return this.entity.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Relationship && ((Relationship) obj).entity.equals(this.entity);
    }

    public void writeToNBT(CompoundTag compound) {
        compound.putUUID("UUID", this.entity);
        compound.putShort("Score", this.score);
    }

    public static Relationship readFromNBT(CompoundTag compound) {
        UUID uuid = compound.getUUID("UUID");
        short score = compound.getShort("Score");
        return new Relationship(uuid, score);
    }

    public UUID getUUID() {
        return this.entity;
    }

    public short getScore() {
        return this.score;
    }
}