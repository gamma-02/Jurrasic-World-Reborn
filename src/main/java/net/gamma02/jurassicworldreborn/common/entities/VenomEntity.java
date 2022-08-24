package net.gamma02.jurassicworldreborn.common.entities;

import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.DilophosaurusEntity;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class VenomEntity extends ThrowableProjectile {

    public VenomEntity(Level world) {
        super(ENTITY_TYPE, world);

        if (world.isClientSide) {
            this.spawnParticles();
        }
    }

    public VenomEntity(Level world, DilophosaurusEntity entity) {
        super(ENTITY_TYPE, world, entity);

        if (world.isClientSide) {
            this.spawnParticles();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity thrower = this.getOwner();

        if (thrower instanceof DilophosaurusEntity spitter) {

            if (result.getEntity() instanceof LivingEntity && result.getEntity() != spitter && (result.getEntity() == spitter.getAttackTarget() || !(result.getEntity() instanceof DilophosaurusEntity))) {
                LivingEntity entityHit = (LivingEntity) result.getEntity();

                entityHit.hurt(DamageSource.thrown(this, this.getOwner()), 4.0F);
                entityHit.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 300, 1, false, false));
                entityHit.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 300, 1, false, false));

                if (!this.level.isClientSide) {
                    this.kill();
                }
            }
        }
    }

    private void spawnParticles() {
//        ClientProxy.spawnVenomParticles(this);todo:particles
    }

    @Override
    protected void defineSynchedData() {
        //must have this for some reason, don't think I need to do anything here - gamma_02
    }
}