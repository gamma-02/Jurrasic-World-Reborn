package net.gamma02.jurassicworldreborn.common.entities.animal;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.AnimationHandler;
import com.google.common.collect.Lists;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.PoseHandler;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Animatable;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.GrowthStage;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.ai.SmartBodyHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;

import javax.annotation.Nullable;

public class GoatEntity extends Animal implements Animatable, IEntityAdditionalSpawnData {
    public static final PoseHandler<GoatEntity> BILLY_POSE_HANDLER = new PoseHandler<>("goat_billy", Lists.newArrayList(GrowthStage.ADULT));
    public static final PoseHandler<GoatEntity> KID_POSE_HANDLER = new PoseHandler<>("goat_kid", Lists.newArrayList(GrowthStage.ADULT));
    public static final PoseHandler<GoatEntity> NANNY_POSE_HANDLER = new PoseHandler<>("goat_nanny", Lists.newArrayList(GrowthStage.ADULT));

    private static final EntityDataAccessor<Boolean> WATCHER_IS_RUNNING = SynchedEntityData.defineId(GoatEntity.class, EntityDataSerializers.BOOLEAN);

    private Animation animation;
    private int animationTick;
    private int animationLength;
    private boolean billy;
    private Variant variant = Variant.JURASSIC_PARK;
    private boolean milked;
    private boolean inLava;

    public GoatEntity(Level world, EntityType<Goat> type/*todo:change this*/) {
        super(type, world);
        //WIDTH, HEIGHT: (0.6F, 1.2F)
        this.maxUpStep = 1.0F;
        this.animationTick = 0;
        this.setAnimation(EntityAnimation.IDLE.get());
    }

    @Override
    protected BodyRotationControl createBodyControl() {
        return new SmartBodyHelper(this);
    }

    protected void initEntityAI() {
//        this.addTask(0, new EntityAISwimming(this));todo:AI
//        this.addTask(1, new EntityAIEatGrass(this));
//        this.addTask(1, new EntityAIPanic(this, 2.0));
//        this.addTask(2, new EntityAIMate(this, 1.0D));
//        this.addTask(3, new EntityAITempt(this, 1.25, false, Sets.newHashSet(FoodHelper.getFoodItems(FoodType.PLANT))));
//        this.addTask(4, new EntityAIFollowParent(this, 1.25));
//        this.addTask(5, new EntityAIWander(this, 1.0));
//        this.addTask(6, new EntityAIWatchClosest(this, Player.class, 6.0F));
//        this.addTask(7, new EntityAILookIdle(this));
//        this.addTask(8, new EntityAIAvoidEntity<>(this, EntityWolf.class, 6.0F, 1.0F, 1.6F));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createLivingAttributes().add(Attributes.MAX_HEALTH, 7.0).add(Attributes.MOVEMENT_SPEED,  0.25);

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(WATCHER_IS_RUNNING, false);
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mate) {
        GoatEntity entity = new GoatEntity(level, /*todo: change this*/ EntityType.GOAT);
        entity.finalizeSpawn(level, level.getCurrentDifficultyAt(this.getOnPos()), MobSpawnType.BREEDING, null, null);
        return entity;
    }

    @Override
    public boolean isCarcass() {
        return false;
    }

    @Override
    public boolean isMoving() {
        float deltaX = (float) (this.getX() - this.xOld);
        float deltaZ = (float) (this.getZ() - this.xOld);
        return deltaX * deltaX + deltaZ * deltaZ > 0.001F;
    }

    @Override
    public boolean isClimbing() {
        return false;
    }

    @Override
    public boolean isSwimming() {
        return (this.isInWater() || this.isInLava()) && !this.onGround;
    }

    @Override
    public boolean isRunning() {
        return this.entityData.get(WATCHER_IS_RUNNING);
    }

    @Override
    public boolean canUseGrowthStage(GrowthStage growthStage) {
        return growthStage == GrowthStage.ADULT;
    }

    @Override
    public boolean isMarineCreature() {
        return false;
    }

    @Override
    public boolean shouldUseInertia() {
        return true;
    }

    @Override
    public boolean isSleeping() {
        return false;
    }

    @Override
    public boolean inWater() {
        return this.isInWater();
    }

    @Override
    public boolean inLava() {
        return this.inLava;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount % 10 == 0) {
            this.inLava = this.isInLava();
        }
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
            this.entityData.set(WATCHER_IS_RUNNING, this.getSpeed() > this.getAttributeValue(Attributes.MOVEMENT_SPEED));
        }
    }

    @Override
    public Animation[] getAnimations() {
        return EntityAnimation.getAnimations();
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
    public int getAnimationTick() {
        return this.animationTick;
    }

    @Override
    public void setAnimationTick(int tick) {
        this.animationTick = tick;
    }

    @Override
    public GrowthStage getGrowthStage() {
        return this.isBaby() ? GrowthStage.INFANT : GrowthStage.ADULT;
    }

    @Override
    public PoseHandler getPoseHandler() {
        return this.isBaby() ? KID_POSE_HANDLER : this.billy ? BILLY_POSE_HANDLER : NANNY_POSE_HANDLER;
    }

    public Type getGoatType() {
        return this.isBaby() ? Type.KID : this.billy ? Type.BILLY : Type.NANNY;
    }

    @Override
    public void playAmbientSound() {
        super.playAmbientSound();
        if (this.getAnimation() == EntityAnimation.IDLE.get()) {
            this.setAnimation(EntityAnimation.SPEAK.get());
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundHandler.GOAT_LIVING;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundHandler.GOAT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundHandler.GOAT_DEATH;
    }

    @Override
    protected float getJumpPower() {
        return 0.62F;
    }

    @Override
    public void ate() {
        super.ate();
        this.milked = false;
        this.setAnimation(EntityAnimation.EATING.get());
    }



    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (itemstack.getItem() == Items.BUCKET && !player.isCreative() && !this.isBaby() && !this.billy)
        {
            player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
            itemstack.shrink(1);

            if (itemstack.isEmpty())
            {
                player.setItemInHand(hand, new ItemStack(Items.MILK_BUCKET));
            }
            else if (!player.getInventory().add(new ItemStack(Items.MILK_BUCKET)))
            {
                player.drop(new ItemStack(Items.MILK_BUCKET), false);
            }
            this.milked = true;

            return InteractionResult.CONSUME;
        }
        else
        {
            return super.mobInteract(player, hand);
        }
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {

        this.billy = this.getRandom().nextBoolean();
        this.variant = Variant.values()[this.getRandom().nextInt(Variant.values().length)];
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Billy", this.billy);
        compound.putByte("Variant", (byte) this.variant.ordinal());
        compound.putBoolean("Milked", this.milked);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.billy = compound.getBoolean("Billy");
        this.variant = Variant.values()[compound.getByte("Variant")];
        this.milked = compound.getBoolean("Milked");
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        buffer.writeBoolean(this.billy);
        buffer.writeByte(this.variant.ordinal());
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        this.billy = additionalData.readBoolean();
        this.variant = Variant.values()[additionalData.readByte()];
    }

//    @Override
//    public boolean isBreedingItem(ItemStack stack) {
//        return stack != null && FoodHelper.isFoodType(stack.getItem(), FoodType.PLANT);
//    }





    @Override
    protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {
        this.spawnAtLocation(Items.LEATHER, this.random.nextInt(2) + 1);
        if (this.random.nextBoolean()) {
            this.spawnAtLocation(new ItemStack(this.random.nextBoolean() ? Blocks.WHITE_WOOL : Blocks.BROWN_WOOL, 1), 0.0F);
        }
//        this.spawnAtLocation(this.isOnFire() ? ItemHandler.GOAT_COOKED : ItemHandler.GOAT_RAW, this.rand.nextInt(2) + 1); TODO: FOODS
    }

    @Override
    protected float getSoundVolume() {
        return super.getSoundVolume() * 0.8F;
    }

    public Variant getVariant() {
        return this.variant;
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 3;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 300;
    }

    @Override
    public boolean canMate(Animal other) {
        if (other == this)
        {
            return false;
        }
        else if (other.getClass() != this.getClass())
        {
            return false;
        }
        else
        {
            if(this.billy != ((GoatEntity) other).billy) {
                return this.isInLove() && other.isInLove();
            }
        }
        return false;
    }

    public enum Type {
        BILLY,
        NANNY,
        KID
    }

    public enum Variant {
        JURASSIC_WORLD,
        JURASSIC_PARK,
        JPOG
    }
}
