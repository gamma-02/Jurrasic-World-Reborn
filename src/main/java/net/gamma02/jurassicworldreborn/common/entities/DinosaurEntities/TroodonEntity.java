package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import mod.reborn.server.damage.DinosaurDamageSource;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import mod.reborn.server.entity.ai.LeapingMeleeEntityAI;
import mod.reborn.server.entity.ai.RaptorLeapEntityAI;
import mod.reborn.server.entity.animal.GoatEntity;
import com.github.alexthe666.citadel.animation.Animation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

public class TroodonEntity extends DinosaurEntity
{
    private static final Class[] targets = {CompsognathusEntity.class, HyaenodonEntity.class, Player.class
, DilophosaurusEntity.class, DimorphodonEntity.class, DodoEntity.class, LeaellynasauraEntity.class, HypsilophodonEntity.class, StegosaurusEntity.class, ProtoceratopsEntity.class, OthnieliaEntity.class, MicroceratusEntity.class};

    public TroodonEntity(Level world)
    {
        super(world);
        this.target(AlligatorGarEntity.class, AchillobatorEntity.class, AlvarezsaurusEntity.class, BeelzebufoEntity.class, VelociraptorBlueEntity.class, VelociraptorCharlieEntity.class, VelociraptorDeltaEntity.class, VelociraptorEchoEntity.class, ChilesaurusEntity.class, CoelurusEntity.class, CompsognathusEntity.class, CrassigyrinusEntity.class, Player.class
, DilophosaurusEntity.class, DimorphodonEntity.class, DiplocaulusEntity.class, DodoEntity.class, GallimimusEntity.class, GuanlongEntity.class, HyaenodonEntity.class, HypsilophodonEntity.class, LeaellynasauraEntity.class, LeptictidiumEntity.class, MicroceratusEntity.class, MussaurusEntity.class, MicroraptorEntity.class, OrnithomimusEntity.class, OthnieliaEntity.class, OviraptorEntity.class, ProceratosaurusEntity.class, ProtoceratopsEntity.class, SegisaurusEntity.class, ZhenyuanopterusEntity.class, MoganopterusEntity.class, Player.class
, Animal.class, Villager.class, GoatEntity.class);
        this.tasks.addTask(1, new LeapingMeleeEntityAI(this, this.dinosaur.getAttackSpeed()));
        this.target(targets);
        for(Class entity : targets) {
            this.tasks.addTask(0, new EntityAINearestAttackableTarget<EntityLivingBase>(this, entity, true, false));
            this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<EntityLivingBase>(this, entity, false));
        }
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, Player.class
, TyrannosaurusEntity.class, GiganotosaurusEntity.class, SpinosaurusEntity.class));
    }
    @Override
    public EntityAIBase getAttackAI() {
        return new RaptorLeapEntityAI(this);
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
        if (this.getAnimation() != EntityAnimation.LEAP_LAND.get()) {
            super.fall(distance, damageMultiplier);
        }
    }

    public boolean attackEntityAsMob(Entity entity){
        float damage = (float) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
        if (entity.hurt(new DinosaurDamageSource("mob", this), damage)) {
            if (entity instanceof EntityLivingBase) {
                int i = rand.nextInt(3);
                if(i == 0) {
                    ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.POISON, 15, 10));
                } else if(i == 1) {
                    ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 15, 10));
                }else if(i == 2) {
                    ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.WITHER, 15, 10));
                }
                return true;
            }
        }
        return super.attackEntityAsMob(entity);
    }
    @Override
    public SoundEvent getSoundForAnimation(Animation animation) {
        switch (EntityAnimation.getAnimation(animation)) {
            case SPEAK:
                return SoundHandler.TROODON_LIVING;
            case DYING:
                return SoundHandler.TROODON_DEATH;
            case INJURED:
                return SoundHandler.TROODON_HURT;
            case CALLING:
                return SoundHandler.TROODON_CALL;
            case BEGGING:
                return SoundHandler.TROODON_THREAT;
        }

        return null;
    }
}
