package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import mod.reborn.server.entity.VenomEntity;
import mod.reborn.server.entity.ai.DilophosaurusMeleeEntityAI;
import mod.reborn.server.entity.ai.DilophosaurusSpitEntityAI;
import mod.reborn.server.entity.animal.GoatEntity;
import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.MobEffects;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.potion.PotionEffect;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class DilophosaurusEntity extends DinosaurEntity implements IRangedAttackMob {

    private static final EntityDataAccessor<Boolean> WATCHER_HAS_TARGET = SynchedEntityData.defineId(DinosaurEntity.class, EntityDataSerializers.BOOLEAN);
    private int targetCooldown;

    public DilophosaurusEntity(Level world) {
        super(world);
        this.target(GoatEntity.class, SmilodonEntity.class, MegatheriumEntity.class, ArsinoitheriumEntity.class, SpinoraptorEntity.class, Player.class
, Villager.class, Animal.class, AchillobatorEntity.class, AlligatorGarEntity.class, AlvarezsaurusEntity.class, BeelzebufoEntity.class, VelociraptorBlueEntity.class, VelociraptorCharlieEntity.class, ChasmosaurusEntity.class, ChilesaurusEntity.class, CoelurusEntity.class, CompsognathusEntity.class, CrassigyrinusEntity.class, VelociraptorDeltaEntity.class, DodoEntity.class, DiplocaulusEntity.class, VelociraptorEchoEntity.class, GallimimusEntity.class, GuanlongEntity.class, HyaenodonEntity.class, HypsilophodonEntity.class, LeaellynasauraEntity.class, LeptictidiumEntity.class, MegapiranhaEntity.class, MetriacanthosaurusEntity.class, MicroceratusEntity.class, MicroraptorEntity.class, MussaurusEntity.class, OrnithomimusEntity.class, OthnieliaEntity.class, OviraptorEntity.class, PostosuchusEntity.class, ProceratosaurusEntity.class, ProtoceratopsEntity.class, SegisaurusEntity.class, TroodonEntity.class, VelociraptorEntity.class, PachycephalosaurusEntity.class);
        this.tasks.addTask(1, new DilophosaurusMeleeEntityAI(this, this.dinosaur.getAttackSpeed()));
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distance) {
	if(target instanceof Player && ((Player)target).isCreative()) {
	    return;
	}
        VenomEntity venom = new VenomEntity(this.world, this);
        double deltaX = target.posX - venom.posX;
        double deltaY = target.posY + (double) target.getEyeHeight() - 1.100000023841858D - venom.posY;
        double deltaZ = target.posZ - venom.posZ;
        float yOffset = Mth.sqrt(deltaX * deltaX + deltaZ * deltaZ) * 0.2F;
        venom.shoot(deltaX, deltaY + (double) yOffset, deltaZ, 1.5F, 0F);
        this.world.spawnEntity(venom);
    }


    //TODO: ????
    @Override
    public void setSwingingArms(boolean swingingArms)
    {

    }

    @Override
    public EntityAIBase getAttackAI() {
        return new DilophosaurusSpitEntityAI(this, this.dinosaur.getAttackSpeed(), 40, 10);
    }

    @Override
    public void entityInit() {
        super.entityInit();

        this.dataManager.register(WATCHER_HAS_TARGET, false);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!this.world.isRemote) {
            EntityLivingBase target = this.getAttackTarget();
            if (target != null && !target.isDead && this.targetCooldown < 50) {
                this.targetCooldown = 50 + this.getRandom().nextInt(30);
            } else if (this.targetCooldown > 0) {
                this.targetCooldown--;
            }

            this.dataManager.set(WATCHER_HAS_TARGET, this.hasTarget());
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        if (super.attackEntityAsMob(entity)) {
            if (entity instanceof EntityLivingBase) {
                ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.POISON, 300, 1, false, false));
            }

            return true;
        }

        return false;
    }

    public boolean hasTarget() {
        if (!this.isCarcass() && !this.isSleeping()) {
            if (this.world.isRemote) {
                return this.dataManager.get(WATCHER_HAS_TARGET);
            } else {
                EntityLivingBase target = this.getAttackTarget();
                return (target != null && !target.isDead) || this.targetCooldown > 0;
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