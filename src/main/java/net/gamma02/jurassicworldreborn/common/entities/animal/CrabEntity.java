package net.gamma02.jurassicworldreborn.common.entities.animal;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.AnimationHandler;
import com.google.common.collect.Lists;
import com.mojang.math.Constants;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.PoseHandler;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Animatable;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.GrowthStage;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.ai.SmartBodyHelper;
import net.gamma02.jurassicworldreborn.common.entities.ModEntities;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import org.antlr.v4.codegen.model.Sync;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.CallbackI;

public class CrabEntity extends Animal implements Animatable, IEntityAdditionalSpawnData {

    private static final PoseHandler<CrabEntity> CRAB_POSE_HANDLER = new PoseHandler<>("crab", Lists.newArrayList(GrowthStage.ADULT));

    public static final EntityDataAccessor<Boolean> CRAB_IS_RUNNING = SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.BOOLEAN);

    private Animation animation;
    private int animationTick;
    private int animationLength;
    private boolean alternative;

    public CrabEntity(EntityType<? extends Animal> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
        this.maxUpStep = 1.0f;
        this.animationTick = 0;
        this.setAnimation(EntityAnimation.IDLE.get());
    }


    @Override
    public boolean isCarcass() {
        return false;
    }

    @Override
    public boolean isMoving() {
        double powx = this.getDeltaMovement().x;
        powx *= powx;
        double powz = this.getDeltaMovement().z;
        powz *= powz;
        double powy = this.getDeltaMovement().y;
        powy *= powy;
        return powx + powz + powy > 0.001F;
    }


    @Override
    public void tick() {
        super.tick();
        if (this.animation != null && this.animation != EntityAnimation.IDLE.get()) {
            boolean shouldHold = EntityAnimation.getAnimation(this.animation).shouldHold();
            if (this.animationTick < this.animationLength) {
                this.animationTick++;
            } else if (!shouldHold) {
                this.animationTick = 0;
                this.setAnimation(EntityAnimation.IDLE.get());
            } else {
                this.animationTick = this.animationLength - 1;
            }
        }
        if (!this.level.isClientSide) {
            this.entityData.set(CRAB_IS_RUNNING, this.getSpeed() > this.getAttributeValue(Attributes.MOVEMENT_SPEED));
        }
    }

    @Override
    public boolean isClimbing() {
        return false;
    }





    @Override
    protected BodyRotationControl createBodyControl() {
        return new SmartBodyHelper(this);
    }

    @Override
    public boolean inWater() {
        return this.isInWater();
    }

    @Override
    public boolean isRunning() {
        return this.entityData.get(CRAB_IS_RUNNING);
    }

    @Override//why. just why. In legacy code, this was a self-referential thing that stores a variable for the tick. just ***why***. - gamma
    public boolean inLava() {
        return this.isInLava();
    }

    @Override
    public boolean canUseGrowthStage(GrowthStage growthStage) {
        return growthStage.equals(GrowthStage.ADULT);
    }

    @Override
    public boolean isMarineCreature() {
        return true;
    }

    @Override
    public boolean shouldUseInertia() {
        return true;
    }

    @Override
    public GrowthStage getGrowthStage() {
        return GrowthStage.ADULT;
    }

    @Override
    public PoseHandler<CrabEntity> getPoseHandler() {//if this errors, run for your goddamn life. - gamma
        return CRAB_POSE_HANDLER;
    }

    @Override
    public int getAnimationTick() {
        return this.animationTick;
    }

    @Override
    public void setAnimationTick(int i) {
        this.animationTick = i;
    }

    @Override
    public Animation getAnimation() {
        return this.animation;
    }

    @Override
    public void setAnimation(Animation newAnimation) {
        Animation oldAnimation = this.animation;
        this.animation = newAnimation;
        if (oldAnimation != newAnimation) {
            this.animationTick = 0;
            this.animationLength = (int) this.getPoseHandler().getAnimationLength(this.animation, this.getGrowthStage());
            AnimationHandler.INSTANCE.sendAnimationMessage(this, newAnimation);
        }
    }

    @Override
    public Animation[] getAnimations() {
        return EntityAnimation.getAnimations();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return new CrabEntity(ModEntities.CRAB_ENTITY_TYPE.get(), p_146743_);
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {

    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {

    }

    @Override
    public void baseTick() {
        int i = this.getAirSupply();
        super.baseTick();

        if (this.isAlive() && !this.isInWater()) {
            --i;
            this.setAirSupply(i);
        } else {
            this.setAirSupply(300);
        }
    }

    @Override
    public void moveRelative(float friction, Vec3 rel) {
        double up = rel.y;
        double forward = rel.z;
        double strafe = rel.x;
        float f = (float) (strafe * strafe + up * up + forward * forward);
        if (f >= 1.0E-4F)
        {
            f = Mth.sqrt(f);
            if (f < 1.0F) f = 1.0F;
            f = friction / f;
            strafe = strafe * f;
            up = up * f;
            forward = forward * f;
            float f1 = Mth.sin(this.getYRot() * Constants.DEG_TO_RAD);
            float f2 = Mth.cos(this.getYRot() * Constants.DEG_TO_RAD);
            double deltaMotionX = (double)(strafe * f2 - forward * f1);
            double deltaMotionY = (double)up;
            double deltaMotionZ = (double)(forward * f2 + strafe * f1);
            Vec3 deltaMotion = new Vec3(deltaMotionX, deltaMotionY, deltaMotionZ);
            this.setDeltaMovement(this.getDeltaMovement().add(deltaMotion));
        }
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }


    public Type getCrabType() {
        return this.alternative ? Type.ALTERNATIVE : Type.CRAB;
    }

    public enum Type {
        CRAB,
        ALTERNATIVE
    }
}
