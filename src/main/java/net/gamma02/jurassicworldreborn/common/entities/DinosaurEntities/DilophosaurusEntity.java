package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.VenomEntity;
import net.gamma02.jurassicworldreborn.common.entities.animal.GoatEntity;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public class DilophosaurusEntity extends DinosaurEntity implements RangedAttackMob {

    private static final EntityDataAccessor<Boolean> WATCHER_HAS_TARGET = SynchedEntityData.defineId(DinosaurEntity.class, EntityDataSerializers.BOOLEAN);
    private int targetCooldown;

    public DilophosaurusEntity(Level world, EntityType<DilophosaurusEntity> type) {
        super(world, type);
        this.target(GoatEntity.class, SmilodonEntity.class, MegatheriumEntity.class, ArsinoitheriumEntity.class, SpinoraptorEntity.class, Player.class
, Villager.class, Animal.class, AchillobatorEntity.class, AlligatorGarEntity.class, AlvarezsaurusEntity.class, BeelzebufoEntity.class, VelociraptorBlueEntity.class, VelociraptorCharlieEntity.class, ChasmosaurusEntity.class, ChilesaurusEntity.class, CoelurusEntity.class, CompsognathusEntity.class, CrassigyrinusEntity.class, VelociraptorDeltaEntity.class, DodoEntity.class, DiplocaulusEntity.class, VelociraptorEchoEntity.class, GallimimusEntity.class, GuanlongEntity.class, HyaenodonEntity.class, HypsilophodonEntity.class, LeaellynasauraEntity.class, LeptictidiumEntity.class, MegapiranhaEntity.class, MetriacanthosaurusEntity.class, MicroceratusEntity.class, MicroraptorEntity.class, MussaurusEntity.class, OrnithomimusEntity.class, OthnieliaEntity.class, OviraptorEntity.class, PostosuchusEntity.class, ProceratosaurusEntity.class, ProtoceratopsEntity.class, SegisaurusEntity.class, TroodonEntity.class, VelociraptorEntity.class, PachycephalosaurusEntity.class);
//        this.addTask(1, new DilophosaurusMeleeEntityAI(this, this.dinosaur.getAttackSpeed()));
    }

    @Override
    public void performRangedAttack(LivingEntity target, float distance) {
	if(target instanceof Player && ((Player)target).isCreative()) {
	    return;
	}
        VenomEntity venom = new VenomEntity(this.level, this);
        double deltaX = target.getX() - venom.getX();
        double deltaY = target.getY() + (double) target.getEyeHeight() - 1.1D - venom.getY();
        double deltaZ = target.getZ() - venom.getZ();
        float yOffset = Mth.sqrt((float) (deltaX * deltaX + deltaZ * deltaZ)) * 0.2F;
        venom.shoot(deltaX, deltaY + (double) yOffset, deltaZ, 1.5F, 0F);
        this.level.addFreshEntity(venom);
    }



//    @Override//unknown what this does, don't really know why it's here - gamma_02
//    public void setSwingingArms(boolean swingingArms)
//    {
//
//    }

//    @Override
//    public EntityAIBase getAttackAI() { TODO:AI
//        return new DilophosaurusSpitEntityAI(this, this.dinosaur.getAttackSpeed(), 40, 10);
//    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(WATCHER_HAS_TARGET, false);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level.isClientSide) {
            LivingEntity target = this.getAttackTarget();
            if (target != null && !target.isDeadOrDying() && this.targetCooldown < 50) {
                this.targetCooldown = 50 + this.getRandom().nextInt(30);
            } else if (this.targetCooldown > 0) {
                this.targetCooldown--;
            }

            this.entityData.set(WATCHER_HAS_TARGET, this.hasTarget());
        }
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        if (super.doHurtTarget(entity)) {
            if (entity instanceof LivingEntity entity1) {
                ( entity1).addEffect(new MobEffectInstance(MobEffects.POISON, 300, 1, false, false));
            }

            return true;
        }

        return false;
    }

    public boolean hasTarget() {
        if (!this.isCarcass() && !this.isSleeping()) {
            if (this.level.isClientSide) {
                return this.entityData.get(WATCHER_HAS_TARGET);
            } else {
                LivingEntity target = this.getAttackTarget();
                return (target != null && !target.isDeadOrDying()) || this.targetCooldown > 0;
            }
        }
        return false;
    }

    @Override
    public SoundEvent getSoundForAnimation(Animation animation) {
        switch (EntityAnimation.getAnimation(animation)) {
            case SPEAK:
                return SoundHandler.DILOPHOSAURUS_LIVING;
            case CALLING:
                return SoundHandler.DILOPHOSAURUS_LIVING;
            case DYING:
                return SoundHandler.DILOPHOSAURUS_DEATH;
            case INJURED:
                return SoundHandler.DILOPHOSAURUS_HURT;
            case ATTACKING:
                return SoundHandler.DILOPHOSAURUS_SPIT;
		default:
			break;
        }

        return null;
    }
}
