package net.gamma02.jurassicworldreborn.common.entities;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.AnimationHandler;
import com.github.alexthe666.citadel.animation.LegSolver;
import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.FixedChainBuffer;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.PoseHandler;
import net.gamma02.jurassicworldreborn.common.blocks.entities.feeder.FeederBlock;
import net.gamma02.jurassicworldreborn.common.blocks.entities.feeder.FeederBlockEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.InventoryDinosaur;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.*;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.ai.*;
import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.gamma02.jurassicworldreborn.common.util.ai.OnionTraverser;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.Consumer;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class DinosaurEntity extends PathfinderMob implements IEntityAdditionalSpawnData, Animatable {
    private static final Logger LOGGER = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static final EntityDataAccessor<Boolean> WATCHER_IS_CARCASS = SynchedEntityData.defineId(DinosaurEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> WATCHER_AGE = SynchedEntityData.defineId(DinosaurEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> WATCHER_IS_SLEEPING = SynchedEntityData.defineId(DinosaurEntity.class, EntityDataSerializers.BOOLEAN);
    // private static final EntityDataAccessor<Boolean> WATCHER_HAS_TRACKER = EntityentityData.createKey(DinosaurEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<String> WATCHER_OWNER_IDENTIFIER = SynchedEntityData.defineId(DinosaurEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Byte> WATCHER_CURRENT_ORDER = SynchedEntityData.defineId(DinosaurEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Boolean> WATCHER_IS_RUNNING = SynchedEntityData.defineId(DinosaurEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> WATCHER_WAS_FED = SynchedEntityData.defineId(DinosaurEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> WATCHER_WAS_MOVED = SynchedEntityData.defineId(DinosaurEntity.class, EntityDataSerializers.BOOLEAN);

    public HashMap<Animation, Byte> variants = new HashMap<>();
    private InventoryDinosaur inventory;
    private MetabolismContainer metabolism;
    protected Dinosaur dinosaur;
//    protected EntityAITasks animationTasks;
    protected Order order = Order.WANDER;
    private boolean isCarcass;
    private boolean wasMoved;
    private boolean blocked;
    private boolean isMale;
    //private boolean hasTracker;
    private boolean isSleeping;
    private boolean useInertialTweens;
    private boolean eatsEggs = false;
    private int carcassHealth;
    private int geneticsQuality;
    private int tranquilizerTicks;
    private int stayAwakeTime;
    private int growthSpeedOffset;
    protected int dinosaurAge;
    protected int prevAge;
    private UUID owner;
    private List<Class<? extends LivingEntity>> attackTargets = new ArrayList<>();
    private String genetics;
    public boolean tranqed;

    private boolean deserializing;

    private int ticksUntilDeath;

    private int attackCooldown;

    @Nullable
    private TaskHelper taskHelper;

    @OnlyIn(Dist.CLIENT)
    public FixedChainBuffer tailBuffer;

    public Herd herd;
    public Family family;
    public Set<Relationship> relationships = new HashSet<>();

    public int wireTicks;
    public int disableHerdingTicks;

    private boolean isSittingNaturally;

    private Animation animation;
    private int animationTick;
    private int animationLength;

//    private DinosaurLookHelper lookHelper;

    private BlockPos closestFeeder;
    private int feederSearchTick;

    private boolean inLava;

    private DinosaurAttributes attributes;

    private int breedCooldown;

    private DinosaurEntity breeding;
    private Set<DinosaurEntity> children = new HashSet<>();
    private int pregnantTime;
    private int jumpHeight;

    private final LegSolver legSolver;

    private boolean isSkeleton;

    private byte skeletonVariant;

    private boolean isFossile;

    public boolean isRendered;

    private int moveTicks = -5;

    private int messageTick = 0;

    public DinosaurEntity(Level world, EntityType<? extends DinosaurEntity> type, Dinosaur dino) {
        super(type, world);// todo

        this.dinosaur = dino;
        blocked = false;



        //Necessary to set the bounding box, rather than having NULL_BOX
        setSize(1, 1);

        this.moveControl = new DinosaurMoveHelper(this);
        this.jumpControl = new DinosaurJumpHelper(this);

        this.setFullyGrown();
        this.metabolism = new MetabolismContainer(this);
        this.inventory = new InventoryDinosaur(this);

        this.setPathfindingMalus(BlockPathTypes.DOOR_WOOD_CLOSED, 0);
        this.setPathfindingMalus(BlockPathTypes.DOOR_IRON_CLOSED, 0);

//        this.navigation = new DinosaurPathNavigate(this, this.level);
//        ((DinosaurPathNavigate) this.navigator).setCanSwim(true);
//        this.lookControl = new DinosaurLookHelper(this);
        this.legSolver = this.level == null || !this.level.isClientSide ? null : this.createLegSolver();


//        this.genetics = GeneticsHelper.randomGenetics(this.random); todo:genetics and big gay
        this.isMale = this.random.nextBoolean();

        this.resetAttackCooldown();

        this.animationTick = 0;
        this.setAnimation(EntityAnimation.IDLE.get());

        this.setUseInertialTweens(true);

//        this.animationTasks = new EntityAITasks(world.profiler); todo: more research here


//        if (!dinosaur.isMarineCreature()) {TODO: FINISH AI
//            this.addTask(0, new AdvancedSwimEntityAI(this));
//        }
//        this.addTask(0, new DinosaurWanderEntityAI(this, 0.8D, 2, 10));
//        this.addTask(0, new DinosaurWanderAvoidWater(this, 0.8D, 10));
//        if (dinosaur.getDiet().canEat(this, FoodType.PLANT)) {
//            this.addTask(1, new GrazeEntityAI(this));
//        }
//        if (dinosaur.getDiet().canEat(this, FoodType.MEAT)) {
//            this.addTask(1, new TargetCarcassEntityAI(this));
//        }
//        if (dinosaur.shouldDefendOwner()) {
//            this.addTask(2, new DefendOwnerEntityAI(this));
//            this.addTask(2, new AssistOwnerEntityAI(this));
//        }
//        if (dinosaur.shouldFlee()) {
//            this.addTask(2, new FleeEntityAI(this));
//        }
//        this.addTask(0, new EscapeWireEntityAI(this));
//        this.addTask(1, new RespondToAttackEntityAI(this));
//        this.addTask(1, new EntityAIPanic(this, 1.25D));
//        this.addTask(2, new ProtectInfantEntityAI<>(this));
//        this.addTask(3, new FollowOwnerEntityAI(this));
//        this.addTask(3, new DinosaurAttackMeleeEntityAI(this,1.0F, false));
//        this.addTask(4, new EntityAILookIdle(this));
//        this.addTask(4, new EntityAIWatchClosest(this, LivingEntity.class, 6.0F));
//        this.animationTasks.addTask(0, new SleepEntityAI(this));
//        this.animationTasks.addTask(1, new DrinkEntityAI(this));
//        this.animationTasks.addTask(1, new MateEntityAI(this));
//        this.animationTasks.addTask(1, new EatFoodItemEntityAI(this));
//        this.animationTasks.addTask(1, new FeederEntityAI(this));
//        this.animationTasks.addTask(3, new CallAnimationAI(this));
//        this.animationTasks.addTask(3, new RoarAnimationAI(this));
//        this.animationTasks.addTask(3, new LookAnimationAI(this));
//        this.animationTasks.addTask(3, new HeadCockAnimationAI(this));
        if (level.isClientSide) {
            this.initClient();
        }

        this.noCulling = true;
        this.setSkeleton(false);
        this.attributes = DinosaurAttributes.create(this);
        this.updateAttributes();

    }

    @Nullable
    protected LegSolver createLegSolver() {
        return null;
    }



    private void eatEggs() {
        if(!level.isClientSide) {
            List<Entity> eggs = new ArrayList<>();
            ((ServerLevel) level).getEntities().get(EntityTypeTest.forClass(DinosaurEggEntity.class), new Consumer<DinosaurEggEntity>() {
                @Override
                public void accept(DinosaurEggEntity entity) {
                    eggs.add(entity);
                }
            });
            for (Entity egg : eggs)
                if (egg instanceof DinosaurEggEntity) {
                    if ((egg.getEyePosition().distanceTo(this.position())) < 0.5) {
                        egg.kill();
                        this.getMetabolism().setEnergy((int) (this.getMetabolism().getEnergy() + ((DinosaurEggEntity) egg).getDinosaur().getAdultHealth() * 0.4));
                    }
                }
        }
    }

    public InventoryDinosaur getInventory() {
        return inventory;
    }

    protected boolean getDoesEatEggs() {
        return this.eatsEggs;
    }

    protected void doesEatEggs(boolean eatsEggs) {
        this.eatsEggs = eatsEggs;
    }

//    @Override
//    protected EntityBodyHelper createBodyHelper() {
//        return new SmartBodyHelper(this);
//    }

//    @Override
//    public EntityLookHelper getLookHelper() {
//        return this.lookHelper;
//    }

    private void initClient() {
        this.tailBuffer = new FixedChainBuffer();
    }

    public boolean shouldSleep() {
        if (this.metabolism.isDehydrated() || this.metabolism.isStarving()) {
            return false;
        }
        SleepTime sleepTime = this.dinosaur.getSleepTime();
        return sleepTime.shouldSleep() && this.getDinosaurTime() > sleepTime.getAwakeTime() && !this.hasPredators() && (this.herd == null || this.herd.enemies.isEmpty());
    }

    private boolean hasPredators() {
        for (LivingEntity predator : this.level.getEntitiesOfClass(LivingEntity.class, new AABB(this.getX() - 10F, this.getY() - 5F, this.getZ() - 10F, this.getX() + 10F, this.getY() + 5F, this.getZ() + 10F), e -> e != DinosaurEntity.this)) {
            boolean hasDinosaurPredator = false;

            if (predator instanceof DinosaurEntity) {
                DinosaurEntity dinosaur = (DinosaurEntity) predator;

                if (!dinosaur.isCarcass() || dinosaur.isSleeping) {
                    for (Class<?> target : dinosaur.getAttackTargets()) {
                        if (target.isAssignableFrom(this.getClass())) {
                            hasDinosaurPredator = true;
                            break;
                        }
                    }
                }
            }

            if (this.getLastHurtByMob() == predator || hasDinosaurPredator) {
                return true;
            }
        }

        return false;
    }

    public int getDinosaurTime() {
        SleepTime sleepTime = this.dinosaur.getSleepTime();

        long time = (this.level.getGameTime() % 24000) - sleepTime.getWakeUpTime();

        if (time < 0) {
            time += 24000;
        }

        return (int) time;
    }

    //public boolean hasTracker() {
    //    return this.entityData.get(WATCHER_HAS_TRACKER);
    // }

    // public void setHasTracker(boolean hasTracker) {
    //     this.hasTracker = hasTracker;
    //     if (!this.level.isClientSide) {
    //        this.entityData.set(WATCHER_HAS_TRACKER, hasTracker);
    //     }
    // }

    public UUID getOwner() {
        return this.owner;
    }

    public void setOwner(Player player) {
        if (this.dinosaur.isImprintable()) {
            UUID prevOwner = this.owner;
            this.owner = player.getUUID();

            if (!this.owner.equals(prevOwner)) {
                ArrayList<String> vowels = buildArray("a", "e", "i", "o", "u");
                boolean hasvowel = false;
                for(String vowel : vowels) {
                    if(dinosaur.getName().toLowerCase().startsWith(vowel)) {
                        hasvowel = true;
                        break;
                    }
                }
//                if(hasvowel) {
//                    player.sendMessage(new TextComponentString(LangUtils.translate(LangUtils.TAME).replace("{dinosaur}", LangUtils.getDinoName(this.dinosaur))));
//                } else {
//                    player.sendMessage(new TextComponentString(LangUtils.translate(LangUtils.TAME).replace("{dinosaur}", LangUtils.getDinoName(this.dinosaur)).replace("an", "a")));
//                }
            }
        }
    }

    public ArrayList<String> buildArray(String... strings) {
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, strings);
        return list;
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if (entity instanceof DinosaurEntity && ((DinosaurEntity) entity).isCarcass() && this.canEatEntity(entity)) {
            this.setAnimation(EntityAnimation.EATING.get());
        } else {
            this.setAnimation(EntityAnimation.ATTACKING.get());
        }

        while (entity.getControllingPassenger() != null) {
            entity = entity.getControllingPassenger();
        }

        float damage = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);

        if (entity.hurt(new DinosaurDamageSource("mob", this), damage)) {
            if (entity instanceof DinosaurEntity && ((DinosaurEntity) entity).isCarcass()) {
                DinosaurEntity dinosaur = (DinosaurEntity) entity;
                if (dinosaur.herd != null && this.herd != null && dinosaur.herd.fleeing && dinosaur.herd.enemies.contains(this)) {
                    this.herd.enemies.removeAll(dinosaur.herd.members);
                    for (DinosaurEntity member : this.herd) {
                        if (member.getAttackTarget() != null && dinosaur.herd.members.contains(member.getAttackTarget())) {
                            member.setTarget(null);
                        }
                    }
                    this.herd.state = Herd.State.IDLE;
                }
            }
            return true;
        }
        return false;
    }


    public LivingEntity getAttackTarget() {
        if(super.getTarget() != null && super.getTarget().isDeadOrDying()) {
            this.setTarget(null);
            return null;
        } else {
            return super.getTarget();
        }
    }

//    private boolean canEatEntity(DinosaurEntity entity) {todo: diet
//        boolean isMarine = entity.getDinosaur().isMarineCreature();
//        if(!isMarine) return entity.dinosaur.getDiet().canEat(entity, FoodType.MEAT);
//        else return entity.dinosaur.getDiet().canEat(entity, FoodType.FISH);
//    }

    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        boolean canHarmInCreative = damageSource.isBypassInvul();
        Entity attacker = damageSource.getEntity();

        if (!this.isCarcass()) {
            if (this.getHealth() - amount <= 0.0F) {
                if (!canHarmInCreative) {
                    this.playSound(this.getSoundForAnimation(EntityAnimation.DYING.get()), this.getSoundVolume(), this.getVoicePitch());
                    this.setHealth(this.getMaxHealth());
                    this.setCarcass(true);
                    return true;
                }

                if (attacker instanceof DinosaurEntity) {
                    this.getRelationship(attacker, true).onAttacked(amount);
                }

                return super.hurt(damageSource, amount);
            } else {
                if (this.getAnimation() == EntityAnimation.RESTING.get() && !this.level.isClientSide) {
                    this.setAnimation(EntityAnimation.IDLE.get());
                    this.isSittingNaturally = false;
                }

                if (!this.level.isClientSide) {
                    if (!((float)this.invulnerableTime > (float)this.invulnerableDuration / 2.0F))
                        this.setAnimation(EntityAnimation.INJURED.get());
                }

                if (this.shouldSleep()) {
                    this.disturbSleep();
                }

                if(attacker instanceof LivingEntity) {
                    this.respondToAttack((LivingEntity)attacker);
                }

                return super.hurt(damageSource, amount);
            }
        } else if (!this.level.isClientSide) {

            if(!(((float)this.invulnerableTime > (float)this.invulnerableDuration / 2.0F))) {
                boolean carcassAllowed = /*RebornConfig.ENTITIES.allowCarcass todo: config*/true;
                if(!carcassAllowed) {
                    this.dropMeat(attacker);
                    this.hurt(damageSource, Float.MAX_VALUE);

                }

                if (damageSource != DamageSource.DROWN) {
                    if (!this.dead && this.carcassHealth >= 0 && this.level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                        this.dropMeat(attacker);
                    }

                    if (this.carcassHealth <= 0) {
                        this.hurt(damageSource, Float.MAX_VALUE);

                    }

                    this.carcassHealth--;
                }

                if (canHarmInCreative) {
                    return super.hurt(damageSource, amount);
                }

                if (this.invulnerableTime <= this.invulnerableDuration / 2.0F) {
                    this.hurtTime = this.hurtDuration = 10;
                }
            }
        }

        return false;
    }

    private Relationship getRelationship(Entity entity, boolean create) {
        for (Relationship relationship : this.relationships) {
            if (relationship.getUUID().equals(entity.getUUID())) {
                return relationship;
            }
        }
        if (create) {
            Relationship relationship = new Relationship(entity.getUUID(), (short) 0);
            this.relationships.add(relationship);
            return relationship;
        }
        return null;
    }

    private void dropMeat(Entity attacker) {
        int fortune = 0;
        if (attacker instanceof LivingEntity) {
            fortune = EnchantmentHelper.getMobLooting((LivingEntity) attacker);
        }

        int count = this.random.nextInt(2) + 1 + fortune;

        boolean burning = this.isOnFire();

        for (int i = 0; i < count; ++i) {
//            int meta = EntityHandler.getDinosaurId(this.dinosaur);
            Item dinoMeat = ModItems.getMeatForDinosaur((EntityType<DinosaurEntity>) this.getType());//if this isn't true something *really* wrong is happening...


            if (burning) {
                this.spawnAtLocation(new ItemStack(dinoMeat, 1), 0.0F);
            } else {
                this.dropStackWithGenetics(new ItemStack(dinoMeat, 1));
            }
        }
    }

    @Override
    public boolean isPushable() {
        return super.isPushable() && !this.isCarcass() && !this.isSleeping();
    }

    @Override
    public ItemEntity spawnAtLocation(ItemStack stack, float offsetY) {
        if (stack.getCount() != 0 && stack.getItem() != null) {
            Random rand = new Random();

            ItemEntity item = new ItemEntity(this.level, this.getX() + ((rand.nextFloat() * this.getBbWidth()) - this.getBbWidth() / 2), this.getY() + (double) offsetY, this.getZ() + ((rand.nextFloat() * this.getBbWidth()) - this.getBbWidth() / 2), stack);
            item.setDefaultPickUpDelay();

            if (this.shouldDropExperience()) {
                this.captureDrops().add(item);
            } else {
                this.level.addFreshEntity(item);
            }

            return item;
        } else {
            return null;
        }
    }

    public AttributeInstance getAttribute(Attribute attr){

        return this.getAttributes().getInstance(attr);
    }


//    @Override <--- handled in vanilla now I believe - gamma
//    public void knockback(Entity entity, float p_70653_2_, double motionX, double motionZ) {
//        if (this.random.nextDouble() >= this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).getValue()) {
//            this.hasImpulse = true;
//            float distance = Float.parseFloat(Double.toString(Math.sqrt(motionX * motionX + motionZ * motionZ)));
//            float multiplier = 0.4F;
//
//            this.setDeltaMovement(new Vec3(this.getDeltaMovement().x/2, this.getDeltaMovement().y, this.getDeltaMovement().z/2));
//            this.setDeltaMovement(this.getDeltaMovement().x - motionX / distance * multiplier, this.getDeltaMovement().y, this.getDeltaMovement().z-motionZ / distance * multiplier);
//
//
//            // TODO We should make knockback bigger and into air if dino is much smaller than attacking dino
//        }
//    }

    @Override
    public void die(DamageSource cause) {
        super.die(cause);

        if (this.herd != null) {
            if (this.herd.leader == this) {
                this.herd.updateLeader();
            }

            this.herd.members.remove(this);
        }

        if (this.family != null) {
            UUID head = this.family.getHead();
            if (head == null || head.equals(this.getUUID())) {
                this.family.update(this);
            }
        }

        if (cause.getEntity() instanceof LivingEntity) {
            this.respondToAttack((LivingEntity) cause.getEntity());
        }
    }

    @Override
    public void playAmbientSound() {
        if (this.getAnimation() == EntityAnimation.IDLE.get()) {
            this.setAnimation(EntityAnimation.SPEAK.get());
            super.playAmbientSound();
        }
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();

        this.entityData.define(WATCHER_IS_CARCASS, this.isCarcass);
        this.entityData.define(WATCHER_AGE, this.dinosaurAge);
        this.entityData.define(WATCHER_IS_SLEEPING, this.isSleeping);
        //this.entityData.register(WATCHER_HAS_TRACKER, this.hasTracker);
        this.entityData.define(WATCHER_OWNER_IDENTIFIER, "");
        this.entityData.define(WATCHER_CURRENT_ORDER, (byte) 0);
        this.entityData.define(WATCHER_IS_RUNNING, false);
        this.entityData.define(WATCHER_WAS_FED, false);
        this.entityData.define(WATCHER_WAS_MOVED, this.wasMoved);
    }

//    @Override todo: modernize attributes
//    protected void applyEntityAttributes() {
//        super.applyEntityAttributes();
//
//        this.dinosaur = EntityHandler.getDinosaurByClass(this.getClass());
//
//
//        this.getAttributes().getInstance(Attributes.ATTACK_DAMAGE);
//    }

    public void updateAttributes() {
        double prevHealth = this.getMaxHealth();
        double newHealth = Math.max(1.0F, this.interpolate(dinosaur.getBabyHealth(), dinosaur.getAdultHealth()) * this.attributes.getHealthModifier());
        double speed = this.interpolate(dinosaur.getBabySpeed(), dinosaur.getAdultSpeed()) * this.attributes.getSpeedModifier();
        double strength = this.getAttackDamage() * this.attributes.getDamageModifier();

        this.getAttributes().getInstance(Attributes.MAX_HEALTH).setBaseValue(newHealth);
        this.getAttributes().getInstance(Attributes.MOVEMENT_SPEED).setBaseValue(speed);

        this.getAttributes().getInstance(Attributes.ATTACK_DAMAGE).setBaseValue(strength);

        this.getAttributes().getInstance(Attributes.FOLLOW_RANGE).setBaseValue(64.0D);


        if (prevHealth != newHealth) {
            this.heal((float) (newHealth - prevHealth));
        }
    }

    public static AttributeSupplier.Builder createAttributes(){
        return LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH).add(Attributes.MOVEMENT_SPEED).add(Attributes.ATTACK_KNOCKBACK).add(Attributes.FOLLOW_RANGE).add(Attributes.ATTACK_DAMAGE);
    }

    private void updateBounds() {
        float scale = this.attributes.getScaleModifier();
        float width = Mth.clamp((float) this.interpolate(dinosaur.getBabySizeX(), dinosaur.getAdultSizeX()) * scale, 0.3F, 4.0F);
        float height = Mth.clamp((float) this.interpolate(dinosaur.getBabySizeY(), dinosaur.getAdultSizeY()) * scale, 0.3F, 4.0F);

        this.maxUpStep = Math.max(1.0F, (float) (Math.ceil(height / 2.0F) / 2.0F));

        if (this.isCarcass) {
            this.setSize(Math.min(5.0F, height), width);
        } else {
            this.setSize(width, height);
        }
    }

    public double getRotationAngle() {
        return interpolate(dinosaur.getBabyrotation(), dinosaur.getRotationAngle());
    }

    public double interpolate(double baby, double adult) {
        int dinosaurAge = this.dinosaurAge;
        int maxAge = this.dinosaur.getMaximumAge();
        if (dinosaurAge > maxAge) {
            dinosaurAge = maxAge;
        }
        return (adult - baby) / maxAge * dinosaurAge + baby;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }

    public void setupDisplay(boolean isMale) {
        this.setFullyGrown();
        this.setMale(isMale);



        this.tickCount = 4;//wtf why - gamma_02 | gamma from the future: whyyyy
    }

    @Override
    public int getAmbientSoundInterval() {
        return 200;
    }

    @Override
    public float getVoicePitch() {
        return (float) this.interpolate(2.5F, 1.0F) + ((this.random.nextFloat() - 0.5F) * 0.125F);
    }

    @Override
    public float getSoundVolume() {
        return (this.isCarcass() || this.isSleeping) ? 0.0F : (2.0F * ((float) this.interpolate(0.2F, 1.0F)));
    }

    public String getGenetics() {
        return this.genetics;
    }

    public void setGenetics(String genetics) {
        this.genetics = genetics;
    }

    public boolean isEntityFreindly(Entity entity) {
        return this.getClass().isAssignableFrom(entity.getClass());
    }

    public boolean canEatEntity(Entity entity) {
        if(entity instanceof Player && (((Player)entity).isCreative() || ((Player)entity).isSpectator())) {
            return false;
        }
        return !isEntityFreindly(entity);
    }

    @Override
    public void aiStep() {
        super.aiStep();


        if(/*!RebornConfig.ENTITIES.allowCarcass && todo:config*/ this.isCarcass) {
            this.remove(RemovalReason.DISCARDED);
        }
        if(this.getAttackTarget() instanceof DinosaurEntity) {
            DinosaurEntity entity = (DinosaurEntity) this.getAttackTarget();
            if(entity != null && entity.isCarcass) {
                this.setTarget(null);
            }
        }

        if(/*!GameRuleHandler.DINO_METABOLISM.getBoolean(this.level) todo: gamerules*/true) {
            if(this.getMetabolism().getEnergy() < this.getMetabolism().getMaxEnergy()) {
                this.getMetabolism().setEnergy(this.getMetabolism().getMaxEnergy());
            }

            if(this.getMetabolism().getWater() < this.getMetabolism().getMaxWater()) {
                this.getMetabolism().setWater(this.getMetabolism().getMaxWater());
            }
        }

        //    if(this.inventory.getSizeInventory() > 0 && !hasTracker) {
        //       if (this.inventory.contains(ItemHandler.TRACKER)) {
        //          this.setHasTracker(true);
        //      }
        //  }

        if(this.animation != null && EntityAnimation.getAnimation(this.animation).doesBlockMovement()) {
            this.blocked = true;
        } else {
            this.blocked = false;
        }
//        if (!this.level.isClientSide && this instanceof TyrannosaurusEntity) {todo: other entity classes
//            if (this.moveTicks > 0) {
//                this.moveTicks--;
//                this.motionX = 0;
//                this.motionZ = 0;
//                this.motionX += Mth.sin(-(float) Math.toRadians(this.rotationYaw - 90)) * 0.03;
//                this.motionZ += Mth.cos((float) Math.toRadians(this.rotationYaw - 90)) * 0.03;
//                this.motionX *= 6.3;
//                this.motionZ *= 6.3;
//            }
//            if (this.moveTicks > -5) {
//                this.moveTicks--;
//
//                if (this.moveTicks == -4) {
//                    this.wasMoved = true;
//                }
//            }
//        }
        if(this.isCarcass() /*&& (!(this instanceof TyrannosaurusEntity) || this.wasMoved) && !(this instanceof MicroraptorEntity)todo: other entity classes*/){
            this.setDeltaMovement(0, this.getDeltaMovement().y, 0);
        }

        if (this.breedCooldown > 0) {
            this.breedCooldown--;
        }

//        if(!this.level.isClientSide && dinosaur.getDiet().canEat(this, FoodType.MEAT) && this.getMetabolism().isHungry()) {TODO: diets
//            level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(10, 10, 10), this::canEatEntity).stream().findAny().ifPresent(this::setAttackTarget);
//        }

        if (!this.isMale() && !this.level.isClientSide && !this.dinosaur.isHybrid) {
            if (this.isPregnant()) {
                if (--this.pregnantTime <= 0) {
                    this.navigation.stop();
                    this.setAnimation(dinosaur.givesDirectBirth() ? EntityAnimation.GIVING_BIRTH.get() : EntityAnimation.LAYING_EGG.get());
                    if(this.family != null) {
                        this.family.setHome(this.getOnPos(), 6000);
                    }
                }
            }
            if ((this.getAnimation() == EntityAnimation.LAYING_EGG.get() || this.getAnimation() == EntityAnimation.GIVING_BIRTH.get()) && this.animationTick == this.getAnimationLength() / 2) {
                for (DinosaurEntity child : this.children) {
                    Entity entity;
                    if (dinosaur.givesDirectBirth()) {
                        entity = child;
                        child.setAge(0);
                        if(this.family != null) {
                            this.family.addChild(entity.getUUID());
                        }
                        //two concerns here: first the assumption that the entity is a mammoth, and second wtf this is the *Dinosaur*Entity class... a mammoth isnt a dino lol... not even sure they were around then
//                        if(child instanceof MammothEntity){
//                            ((MammothEntity) child).setVariant(((MammothEntity)this).getVariant());
//                        }
                    } else {
                        entity = new DinosaurEggEntity(this.level, child, this);
                    }
                    entity.setPos(this.getX() + (this.random.nextFloat() - 0.5F), this.getY() + 0.5F, this.getZ() + (this.random.nextFloat() - 0.5F));
                    this.level.addFreshEntity(entity);
                }
            }
        }

        if (this.breeding != null) {
            if (this.tickCount % 10 == 0) {
                this.getNavigation().moveTo(this.breeding, 1.0);
            }
            boolean dead = this.breeding.dead || this.breeding.isCarcass();
            if (dead || this.getBoundingBox().intersects(this.breeding.getBoundingBox().inflate(3))) {
                if (!dead) {
                    this.breedCooldown = dinosaur.getBreedCooldown();
                    if (!this.isMale()) {
                        int minClutch = dinosaur.getMinClutch();
                        int maxClutch = dinosaur.getMaxClutch();
                        for (int i = 0; i < this.random.nextInt(maxClutch - minClutch) + minClutch; i++) {
                            try {
                                DinosaurEntity child = this.getClass().getConstructor(Level.class).newInstance(this.level);
                                child.setAge(0);
                                child.setMale(this.random.nextDouble() > 0.5);
                                child.setDNAQuality(100);
//                                DinosaurAttributes attributes = DinosaurAttributes.combine(this, (DinosaurAttributes) this.getAttributes(), (DinosaurAttributes)/*should hope this is a dinosaur... lol - gamma*/ this.breeding.getAttributes());
                                StringBuilder genetics = new StringBuilder();
                                for (int c = 0; c < this.genetics.length(); c++) {
                                    if (this.random.nextBoolean()) {
                                        genetics.append(this.genetics.charAt(i));
                                    } else {
                                        genetics.append(this.breeding.genetics.charAt(i));
                                    }
                                }
                                child.setGenetics(genetics.toString());
                                child.setAttributes(attributes);
                                this.children.add(child);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        this.pregnantTime = 9600;
                    }
                }
                this.breeding = null;
            }
        }

        if (this.tickCount % 10 == 0) {
            this.inLava = this.isInLava();
        }

        if (this.isClimbing()) {
            this.animationSpeedOld = this.animationSpeed;
            double deltaY = (this.getY() - this.yOld) * 4.0F;
            if (deltaY > 1.0F) {
                deltaY = 1.0F;
            }
            this.animationSpeed += (deltaY - this.animationSpeed) * 0.4F;
            this.animationPosition += this.animationSpeed;
        }

        if (!this.isCarcass) {
            if (this.firstTick) {
                this.updateAttributes();
            }

            this.updateGrowth();

//            if (!this.level.isClientSide) {TODO: FoodHelper
//                if (this.metabolism.isHungry()) {
//                    List<ItemEntity> entitiesWithinAABB = this.level.getEntitiesOfClass(ItemEntity.class, this.getBoundingBox().inflate(1.0));
//                    for (ItemEntity itemEntity : entitiesWithinAABB) {
//                        Item item = itemEntity.getItem().getItem();
//                        if (FoodHelper.isEdible(this, dinosaur.getDiet(), item)) {
//                            this.setAnimation(EntityAnimation.EATING.get());
//
//                            if (itemEntity.getItem().getCount() > 1) {
//                                itemEntity.getItem().shrink(1);
//                            } else {
//                                itemEntity.kill();
//                            }
//
//                            this.getMetabolism().eat(FoodHelper.getHealAmount(item));
//                            FoodHelper.applyEatEffects(this, item);
//                            this.heal(10.0F);
//
//                            break;
//                        }
//                    }
//                }
//
//                this.metabolism.update();
//            }

            if (this.tickCount % 62 == 0) {
                this.playSound(this.getBreathingSound(), this.getSoundVolume(), this.getVoicePitch());
            }

            if (!dinosaur.isMarineCreature()) {
                if (this.isInWater() || (this.getNavigation().isDone() && this.inWater() || this.inLava())) {
                    this.getJumpControl().jump();
                } else {
//                    if (this.isSwimming()) { TODO: AI
//                        Path path = this.getNavigation().getPath();
//                        if (path != null) {
//                            AxisAlignedBB detectionBox = this.getEntityBoundingBox().expand(0.5, 0.5, 0.5);
//                            if (this.level.collidesWithAnyBlock(detectionBox)) {
//                                List<AABB> colliding = this.level.getCollisionBoxes(this.getAttack(), detectionBox);
//                                boolean swimUp = false;
//                                for (AABB bound : colliding) {
//                                    if (bound.maxY > this.getEntityBoundingBox().minY) {
//                                        swimUp = true;
//                                        break;
//                                    }
//                                }
//                                if (swimUp) {
//                                    this.getJumpHelper().setJumping();
//                                }
//                            }
//                        }
//                    }
                }
            }

            if (this.herd == null) {
                this.herd = new Herd(this);
            }

            if (!this.level.isClientSide) {
                if (this.order == Order.WANDER) {
                    if (this.herd.state == Herd.State.IDLE && this.getAttackTarget() == null && !this.metabolism.isThirsty() && !this.metabolism.isHungry() && this.getNavigation().isDone()) {
                        if (!this.isSleeping && this.onGround && !this.isInWater() && this.getAnimation() == EntityAnimation.IDLE.get() && this.random.nextInt(800) == 0) {
                            this.setAnimation(EntityAnimation.RESTING.get());
                            this.isSittingNaturally = true;
                        }
                    } else if (this.getAnimation() == EntityAnimation.RESTING.get()) {
                        this.setAnimation(EntityAnimation.IDLE.get());
                        this.isSittingNaturally = false;
                    }
                }

                if (this == this.herd.leader && !this.dinosaur.isMarineCreature()) {
                    this.herd.update();
                    //this.addTask(0, new DinosaurHerdWanderEntityAI(this.herd, 0.8D, 2, 25));
                } else {
                    //this.tasks.removeTask(new DinosaurWanderEntityAI(this, 0.8D, 2, 10));
                    //this.tasks.removeTask(new DinosaurHerdWanderEntityAI(this.herd, 0.8D, 2, 25));
                }

                if (this.tickCount % 10 == 0) {
                    if (this.family != null && (this.family.getHead() == null || this.family.getHead().equals(this.getUUID()))) {
                        if (this.family.update(this)) {
                            this.family = null;
                        }
                    } else if (this.family == null && this.getAttackTarget() == null) {
                        if (this.relationships.size() > 0 && this.random.nextDouble() > 0.9) {
                            DinosaurEntity chosen = null;
                            Relationship chosenRelationship = null;
                            for (Relationship relationship : this.relationships) {
                                if (relationship.getScore() > Relationship.MAX_SCORE * 0.9) {
                                    DinosaurEntity entity = relationship.get(this);
                                    if (entity != null && this.isMale != entity.isMale) {
                                        chosen = entity;
                                        chosenRelationship = relationship;
                                        break;
                                    }
                                }
                            }
                            if (chosen != null) {
                                this.family = new Family(this.getUUID(), chosen.getUUID());
                                chosenRelationship.setFamily();
                                this.breedCooldown = this.random.nextInt(1000) + 1000;
                                chosen.breedCooldown = this.breedCooldown;
                            }
                        }
                    }
                    if (this.herd != null) {
                        for (DinosaurEntity herdMember : this.herd.members) {
                            if (herdMember != this) {
                                Relationship relationship = this.getRelationship(herdMember, true);
                                relationship.updateHerd(this);
                            }
                        }
                        for (LivingEntity enemy : this.herd.enemies) {
                            if (enemy instanceof DinosaurEntity) {
                                Relationship relationship = new Relationship(enemy.getUUID(), (short) -30);
                                if (!this.relationships.contains(relationship)) {
                                    this.relationships.add(relationship);
                                }
                            }
                        }
                    }
                    if (this.relationships.size() > 0) {
                        Set<Relationship> removal = new HashSet<>();
                        for (Relationship relationship : this.relationships) {
                            if (relationship.update(this)) {
                                removal.add(relationship);
                            }
                        }
                        this.relationships.removeAll(removal);
                    }
                }

                if (!this.getNavigation().isDone()) {
                    if (this.isSittingNaturally && this.getAnimation() == EntityAnimation.RESTING.get()) {
                        this.setAnimation(EntityAnimation.IDLE.get());
                        this.isSittingNaturally = false;
                    }
                }
            }
        }

        if (!this.level.isClientSide) {
//            this.lookHelper.onUpdateLook();
        }

        if(this.getDoesEatEggs()){
            eatEggs();
        }

        if(!this.level.isClientSide && this.tickCount % 20 == 0)
            this.entityData.set(WATCHER_WAS_FED, false);
    }

    private void updateGrowth() {
        if (!this.dead && this.tickCount % 8 == 0 && !this.level.isClientSide) {
            if (/*GameRuleHandler.DINO_GROWTH.getBoolean(this.level) todo: gamerules*/true) {
                this.dinosaurAge += Math.min(this.growthSpeedOffset, 960) + 1;
                this.metabolism.decreaseEnergy((int) ((Math.min(this.growthSpeedOffset, 960) + 1) * 0.1));
            }

            if (this.growthSpeedOffset > 0) {
                this.growthSpeedOffset -= 10;

                if (this.growthSpeedOffset < 0) {
                    this.growthSpeedOffset = 0;
                }
            }
        }
    }

    private boolean ticked = false;
    @Override
    public void tick() {
        super.tick();
        if(!ticked) {


            ticked = true;
        }
        if(this.level.isClientSide && this.entityData.get(WATCHER_WAS_FED)) {
            this.level.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getX() + (double)(this.random.nextFloat() * this.getBbWidth() * 2.0F) - (double)this.getBbWidth(), this.getY() + 0.5D + (double)(this.random.nextFloat() * this.getBbHeight()), this.getZ() + (double)(this.random.nextFloat() * this.getBbWidth() * 2.0F) - (double)this.getBbWidth(), 0.0D, 0.0D, 0.0D);
        }
        if(this.ticksUntilDeath > 0) {
            if(--this.ticksUntilDeath == 0) {
                this.playSound(this.getSoundForAnimation(EntityAnimation.DYING.get()), this.getSoundVolume(), this.getVoicePitch());
                this.setHealth(this.getMaxHealth());
                this.setCarcass(true);
            }
        }

        if (this.attackCooldown > 0) {
            this.attackCooldown--;
        }

        if (!this.level.isClientSide) {
            if (this.animation == EntityAnimation.LEAP.get()) {
                if (this.getDeltaMovement().y < 0) {
                    this.setAnimation(EntityAnimation.LEAP_LAND.get());
                }
            } else if (this.animation == EntityAnimation.LEAP_LAND.get() && (this.onGround || this.isSwimming())) {
                this.setAnimation(EntityAnimation.IDLE.get());
            }
        }

        if (this.animation != null && this.animation != EntityAnimation.IDLE.get()) {
            boolean shouldHold = EntityAnimation.getAnimation(this.animation).shouldHold();

            if (this.animationTick < this.animationLength) {
                this.animationTick++;
            } else if (!shouldHold) {
                this.animationTick = 0;
                if (this.animation == EntityAnimation.PREPARE_LEAP.get()) {
                    this.setAnimation(EntityAnimation.LEAP.get());
                } else {
                    this.setAnimation(EntityAnimation.IDLE.get());
                }
            } else {
                this.animationTick = this.animationLength - 1;
            }
        }

        if (!this.level.isClientSide) {
            this.entityData.set(WATCHER_WAS_MOVED, this.wasMoved);
            this.entityData.set(WATCHER_AGE, this.dinosaurAge);
            this.entityData.set(WATCHER_IS_SLEEPING, this.isSleeping);
            this.entityData.set(WATCHER_IS_CARCASS, this.isCarcass);
            //  this.entityData.set(WATCHER_HAS_TRACKER, this.hasTracker);
            this.entityData.set(WATCHER_CURRENT_ORDER, (byte) this.order.ordinal());
            this.entityData.set(WATCHER_OWNER_IDENTIFIER, this.owner != null ? this.owner.toString() : "");
            this.entityData.set(WATCHER_IS_RUNNING, this.getSpeed() > this.getAttributeBaseValue(Attributes.MOVEMENT_SPEED));
        } else {
            this.updateTailBuffer();
            this.wasMoved = this.entityData.get(WATCHER_WAS_MOVED);
            this.dinosaurAge = this.entityData.get(WATCHER_AGE);
            this.isSleeping = this.entityData.get(WATCHER_IS_SLEEPING);
            this.isCarcass = this.entityData.get(WATCHER_IS_CARCASS);
            //   this.hasTracker = this.entityData.get(WATCHER_HAS_TRACKER);
            String owner = this.entityData.get(WATCHER_OWNER_IDENTIFIER);
            this.order = Order.values()[this.entityData.get(WATCHER_CURRENT_ORDER)];

            if (owner.length() > 0 && (this.owner == null || !owner.equals(this.owner.toString()))) {
                this.owner = UUID.fromString(owner);
            } else if (owner.length() == 0) {
                this.owner = null;
            }
        }



        if (this.tickCount % 20 == 0) {
            this.updateAttributes();
            this.updateBounds();
        }

        if (this.isCarcass) {
            this.yBodyRot = this.getYRot();
            this.yHeadRot = this.getYRot();
        }

        if (this.isSleeping) {
            if (this.getAnimation() != EntityAnimation.SLEEPING.get()) {
                this.setAnimation(EntityAnimation.SLEEPING.get());
            }
        } else if (this.getAnimation() == EntityAnimation.SLEEPING.get()) {
            this.setAnimation(EntityAnimation.IDLE.get());
        }

        if (!this.level.isClientSide) {
            if (this.isCarcass) {
                if (this.getAnimation() != EntityAnimation.DYING.get()) {
                    this.setAnimation(EntityAnimation.DYING.get());
                }

                if (this.tickCount % 1000 == 0) {
                    this.hurt(DamageSource.GENERIC, 1.0F);
                }
            } else {
                if (this.isSleeping) {
                    if (this.tickCount % 20 == 0) {
                        if (this.stayAwakeTime <= 0 && this.hasPredators()) {
                            this.disturbSleep();
                        }
                    }

                    if (!this.shouldSleep() && !this.level.isClientSide && tranquilizerTicks-- <= 0) {
                        this.isSleeping = false;
                        this.tranquilizerTicks = 0;
                        this.tranqed = false;
                    }
                } else if (this.getAnimation() == EntityAnimation.SLEEPING.get()) {
                    this.setAnimation(EntityAnimation.IDLE.get());
                }

                if (!this.isSleeping) {
                    if (this.order == Order.SIT) {
                        if (this.getAnimation() != EntityAnimation.RESTING.get()) {
                            this.setAnimation(EntityAnimation.RESTING.get());
                        }
                    } else if (!this.isSittingNaturally && this.getAnimation() == EntityAnimation.RESTING.get() && !this.level.isClientSide) {
                        this.setAnimation(EntityAnimation.IDLE.get());
                    }
                }
            }
        }

        if (!this.shouldSleep() && !this.isSleeping) {
            this.stayAwakeTime = 0;
        }



        if (this.stayAwakeTime > 0) {
            this.stayAwakeTime--;
        }
        if (this.wireTicks > 0) {
            this.wireTicks--;
        }
        if (this.disableHerdingTicks > 0) {
            this.disableHerdingTicks--;
        }

        if (this.legSolver != null) {
            double msc = dinosaur.getScaleInfant() / dinosaur.getScaleAdult();
            this.legSolver.update(this, (float) this.interpolate(msc, 1.0) * this.attributes.getScaleModifier());
        }


        this.prevAge = this.dinosaurAge;
    }

    private void updateTailBuffer() {
        this.tailBuffer.calculateChainSwingBuffer(68.0F, 3, 7.0F, this);
    }

    @Override
    public boolean isImmobile() {
        return this.isCarcass() || this.isSleeping() || blocked;
    }

    @Override
    protected float tickHeadTurn(float angle, float distance) {
        if (!this.isImmobile()) {
            return super.tickHeadTurn(angle, distance);
        }
        return distance;
    }

    public int getDaysExisted() {
        return (int) Math.floor((this.dinosaurAge * 8.0F) / 24000.0F);
    }

    public void setFullyGrown() {
        this.setAge(this.dinosaur.getMaximumAge());
    }

    public Dinosaur getDinosaur() {
        return this.dinosaur;
    }

    @Override
    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }

    @Override
    public boolean canChangeDimensions() {
        return true;
    }

    public int getDinosaurAge() {
        return this.dinosaurAge;
    }

    public void setAge(int age) {
        this.dinosaurAge = age;
        if (!this.level.isClientSide) {
            this.entityData.set(WATCHER_AGE, this.dinosaurAge);
        }
    }





    @Override
    protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {

        if(pSource.isBypassInvul() && pSource.isBypassArmor()){
            return;
        }

        for (String bone : this.dinosaur.getBones()) {
            if (this.random.nextInt(10) != 0) {
                this.dropStackWithGenetics(new ItemStack(ModItems.getBoneForDinosaur((EntityType<DinosaurEntity>) this.getType()), 1));
            }
        }
    }

    private void dropStackWithGenetics(ItemStack stack) {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("DNAQuality", this.geneticsQuality);
//        nbt.putInt("Dinosaur", EntityHandler.getDinosaurId(this.dinosaur)); legacy, reimpliment below:
        nbt.putString("Dinosaur", this.getType().toString());
        nbt.putString("Genetics", this.genetics);
        stack.setTag(stack.getTag() != null ? stack.getTag().merge(nbt) : nbt);

        this.spawnAtLocation(stack, 0.0F);
    }

    @Override
    public boolean isCarcass() {
        return this.isCarcass;
    }

    public void setCarcass(boolean carcass) {
        if (!this.level.isClientSide && carcass != this.isCarcass && !this.wasMoved) {
            this.moveTicks = 18;
        }
        this.isCarcass = carcass;

        boolean carcassAllowed = /*RebornConfig.ENTITIES.allowCarcass*/true;
        if (!this.level.isClientSide) {
            this.entityData.set(WATCHER_IS_CARCASS, this.isCarcass);
        }
        if (carcass && carcassAllowed) {
            this.setAnimation(EntityAnimation.DYING.get());
            this.carcassHealth = Mth.clamp(Math.max(1, (int) Math.sqrt(this.getBbWidth() * this.getBbHeight())), 0, 8);
            this.tickCount = 0;
            this.inventory.dropItems(this.level, this.random);
        } else if (carcass) {
            this.setAnimation(EntityAnimation.DYING.get());
            this.carcassHealth = 0;
            this.inventory.dropItems(this.level, this.random);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.isCrouching() && hand == InteractionHand.MAIN_HAND) {
            if (this.isOwner(player)) {
                if (this.getAgePercentage() > 75) {
//                    player.(this.inventory); todo: inventories
                     } else {
                    if (this.level.isClientSide) {
//                        TranslatableContents denied = new TranslatableContents("message.too_young.name");todo: translations
//                        denied.getStyle().applyFormat(ChatFormatting.RED);
//                        Minecraft.getInstance().player.sendMessage(denied, null);
                    }
                }
            } else {
                if (this.level.isClientSide) {
//                    TranslatableContents denied = new TranslatableContents("message.not_owned.name");todo: translations
//                    denied.getStyle().applyFormat(ChatFormatting.RED);
//                    Minecraft.getInstance().player.sendMessage(denied, null);
                }
            }
        } else {
            if (stack.isEmpty() && hand == InteractionHand.MAIN_HAND && this.level.isClientSide) {
                if (this.isOwner(player)) {
//                    RebornMod.NETWORK_WRAPPER.sendToServer(new BiPacketOrder(this)); todo: networking
                } else {
//                    TranslatableContents denied = new TranslatableContents(/*"message.not_owned.name"*/"");todo: translations
//                    denied.getStyle().applyFormat(ChatFormatting.RED);
//                    Minecraft.getInstance().player.sendMessage(denied, null);
                }
            } else if (!stack.isEmpty()&& (this.metabolism.isThirsty() || this.metabolism.isHungry())) {
//                if (!this.level.isClientSide) {
//                    Item item = stack.getItem();
//                    boolean fed = false;
//                    if (item == Items.POTION) {
//                        fed = true;
//                        this.metabolism.increaseWater(1000);
//                        this.setAnimation(EntityAnimation.DRINKING.get());
//                    } else if (FoodHelper.isEdible(this, this.dinosaur.getDiet(), item)) {
//                        fed = true;
//                        this.metabolism.eat(FoodHelper.getHealAmount(item));
//                        this.setAnimation(EntityAnimation.EATING.get());
//                        FoodHelper.applyEatEffects(this, item);
//                    }
//                    if (fed) {
//                        this.entityData.set(WATCHER_WAS_FED, true);
//                        if (!player.capabilities.isCreativeMode) {
//                            stack.shrink(1);
//                            if (item == Items.POTIONITEM) {
//                                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
//                            }
//                        }
//                        if (!this.isOwner(player)) {
//                            if (this.rand.nextFloat() < 0.30) {
//                                if (this.dinosaur.getDinosaurType() == Dinosaur.DinosaurType.AGGRESSIVE) {
//                                    if (this.rand.nextFloat() * 4.0F < (float) this.herd.members.size() / this.dinosaur.getMaxHerdSize()) {
//                                        this.herd.enemies.add(player);
//                                    } else {
//                                        this.attackEntityAsMob(player);
//                                    }
//                                } else if (this.dinosaur.getDinosaurType() == Dinosaur.DinosaurType.SCARED) {
//                                    this.herd.fleeing = true;
//                                    this.herd.enemies.add(player);
//                                }
//                            }
//                        }
//                        player.swingArm(hand);
//                    }
//                }TODO: feeding
            }
        }

        return InteractionResult.PASS;
    }

    public boolean isOwner(Player player) {
        return player.getUUID().equals(this.getOwner());
    }

    @Override
    public boolean canBeLeashed(Player pPlayer) {
        return !this.isLeashed() && (this.getBbWidth() < 1.5) && super.canBeLeashed(pPlayer);
    }

    public int getDNAQuality() {
        return this.geneticsQuality;
    }

    public void setDNAQuality(int quality) {
        this.geneticsQuality = quality;
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
        if (this.isSleeping()) {
            newAnimation = EntityAnimation.SLEEPING.get();
        }

        if (this.isCarcass()) {
            newAnimation = EntityAnimation.DYING.get();
        }
        Animation oldAnimation = this.animation;
        this.animation = newAnimation;
        if (oldAnimation != newAnimation) {
            this.animationTick = 0;
            this.animationLength = (int) this.dinosaur.getPoseHandler().getAnimationLength(newAnimation, this.getGrowthStage());
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

    public boolean isBusy() {
        return !this.isAlive() ||
                this.getAnimation() == EntityAnimation.IDLE.get() ||
                (this.herd != null && this.herd.isBusy()) ||
                this.getAttackTarget() != null ||
                this.isSwimming() ||
                this.shouldSleep();
    }

    public boolean isAlive() {
        return !this.isCarcass && !this.dead;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return this.getSoundForAnimation(EntityAnimation.SPEAK.get());
    }


    @Override
    public SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return this.getSoundForAnimation(EntityAnimation.INJURED.get());
    }

    @Override
    public SoundEvent getDeathSound() {
        return this.getSoundForAnimation(EntityAnimation.DYING.get());
    }

    public SoundEvent getSoundForAnimation(Animation animation) {
        return null;
    }

    public SoundEvent getBreathingSound() {
        return null;
    }

    public double getAttackDamage() {
        return this.interpolate(dinosaur.getBabyStrength(), dinosaur.getAdultStrength());
    }

    public boolean isMale() {
        return this.isMale;
    }

    public boolean isPregnant() {
        return !this.isMale() && this.pregnantTime > 0;
    }

    public void setMale(boolean male) {
        this.isMale = male;
    }

    public int getAgePercentage() {
        int age = this.getDinosaurAge();
        return age != 0 ? age * 100 / this.dinosaur.getMaximumAge() : 0;
    }

    @Override
    public GrowthStage getGrowthStage() {

        if (this.isSkeleton) {
            return GrowthStage.SKELETON;
        }
        int percent = this.getAgePercentage();
        return percent > 75 ? GrowthStage.ADULT : percent > 50 ? GrowthStage.ADOLESCENT : percent > 25 ? GrowthStage.JUVENILE : GrowthStage.INFANT;
    }

    public void increaseGrowthSpeed() {
        this.growthSpeedOffset += 240;
    }

    public int getBreedCooldown() {
        return this.breedCooldown;
    }

    public void breed(DinosaurEntity partner) {
        this.breeding = partner;
    }

    @Override
    public boolean isSwimming() {
        return (this.isInWater() || this.inLava()) && !this.onGround;
    }



    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);

        nbt.putInt("DinosaurAge", this.dinosaurAge);
        nbt.putBoolean("IsCarcass", this.isCarcass);
        nbt.putInt("DNAQuality", this.geneticsQuality);
        nbt.putString("Genetics", this.genetics);
        nbt.putBoolean("IsMale", this.isMale);
        nbt.putInt("GrowthSpeedOffset", this.growthSpeedOffset);
        nbt.putInt("StayAwakeTime", this.stayAwakeTime);
        nbt.putBoolean("IsSleeping", this.isSleeping);
        nbt.putByte("Order", (byte) this.order.ordinal());
        nbt.putInt("CarcassHealth", this.carcassHealth);
        nbt.putInt("BreedCooldown", this.breedCooldown);
        nbt.putInt("PregnantTime", this.pregnantTime);
        nbt.putBoolean("WasMoved", this.wasMoved);

        this.metabolism.writeToNBT(nbt);

        if (this.owner != null) {
            nbt.putString("OwnerUUID", this.owner.toString());
        }

        this.inventory.writeToNBT(nbt);

        if (this.family != null && (this.family.getHead() == null || this.family.getHead().equals(this.getUUID()))) {
            CompoundTag familyTag = new CompoundTag();
            this.family.writeToNBT(familyTag);
            nbt.put("Family", familyTag);
        }

        ListTag relationshipList = new ListTag();

        for (Relationship relationship : this.relationships) {
            CompoundTag compound = new CompoundTag();
            relationship.writeToNBT(compound);
            relationshipList.add(compound);
        }

        nbt.put("Relationships", relationshipList);

        CompoundTag attributes = new CompoundTag();
        this.attributes.writeToNBT(attributes);
        nbt.put("GeneticAttributes", attributes);

        if (this.children.size() > 0) {
            ListTag children = new ListTag();
            for (DinosaurEntity child : this.children) {
                if (child != null) {
                    CompoundTag temp = new CompoundTag();
                    child.save(temp);
                    children.add(temp);
                }
            }
            nbt.put("Children", children);
        }

        nbt.putInt("TranquilizerTicks", tranquilizerTicks);
        nbt.putInt("TicksUntilDeath", ticksUntilDeath);
    }



    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        this.deserializing = true;

        super.readAdditionalSaveData(nbt);
        this.wasMoved = nbt.getBoolean("WasMoved");
        this.setAge(nbt.getInt("DinosaurAge"));
        this.setCarcass(nbt.getBoolean("IsCarcass"));
        this.geneticsQuality = nbt.getInt("DNAQuality");
        this.genetics = nbt.getString("Genetics");
        this.isMale = nbt.getBoolean("IsMale");
        this.growthSpeedOffset = nbt.getInt("GrowthSpeedOffset");
        this.stayAwakeTime = nbt.getInt("StayAwakeTime");
        this.setSleeping(nbt.getBoolean("IsSleeping"));
        this.carcassHealth = nbt.getInt("CarcassHealth");
        this.order = Order.values()[nbt.getByte("Order")];
        this.breedCooldown = nbt.getInt("BreedCooldown");
        this.pregnantTime = nbt.getInt("PregnantTime");
        this.metabolism.readFromNBT(nbt);

        String ownerUUID = nbt.getString("OwnerUUID");

        if (ownerUUID.length() > 0) {
            this.owner = UUID.fromString(ownerUUID);
        }

        if (nbt.contains("Family")) {
            CompoundTag familyTag = nbt.getCompound("Family");
            this.family = Family.readFromNBT(familyTag);
        }

        this.inventory.readFromNBT(nbt);

        ListTag relationships = nbt.getList("Relationships", Tag.TAG_COMPOUND);

        for (int i = 0; i < relationships.size(); i++) {
            CompoundTag compound = relationships.getCompound(i);
            this.relationships.add(Relationship.readFromNBT(compound));
        }

        if (nbt.contains("GeneticAttributes")) {
            CompoundTag attributes = nbt.getCompound("GeneticAttributes");
            this.attributes = DinosaurAttributes.from(attributes);
        }

        if (nbt.contains("Children")) {
            ListTag children = nbt.getList("Children", Tag.TAG_COMPOUND);
            for (int i = 0; i < children.size(); i++) {
                CompoundTag childTag = children.getCompound(i);
                Entity entity = EntityType.loadEntityRecursive(childTag, level, (entity1) -> {
                    return entity1;
                });
                if (entity instanceof DinosaurEntity) {
                    this.children.add((DinosaurEntity) entity);
                }
            }
        }

        tranquilizerTicks = nbt.getInt("TranquilizerTicks");
        ticksUntilDeath = nbt.getInt("TicksUntilDeath");

        this.updateAttributes();
        this.updateBounds();

        this.deserializing = false;
    }

    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        buffer.writeInt(this.dinosaurAge);
        buffer.writeBoolean(this.isCarcass);
        buffer.writeInt(this.geneticsQuality);
        buffer.writeBoolean(this.isMale);
        buffer.writeInt(this.growthSpeedOffset);
        this.attributes.write(buffer);
    }

    @Override
    public void readSpawnData(FriendlyByteBuf additionalData) {
        this.dinosaurAge = additionalData.readInt();
        this.isCarcass = additionalData.readBoolean();
        this.geneticsQuality = additionalData.readInt();
        this.isMale = additionalData.readBoolean();
        this.growthSpeedOffset = additionalData.readInt();
        this.attributes = DinosaurAttributes.from(additionalData);

        if (this.isCarcass) {
            this.setAnimation(EntityAnimation.DYING.get());
        }

        this.updateAttributes();
        this.updateBounds();
    }


    public MetabolismContainer getMetabolism() {
        return this.metabolism;
    }

    public boolean setSleepLocation(BlockPos sleepLocation, boolean moveTo) {
        return !moveTo || this.getNavigation().moveTo(sleepLocation.getX(), sleepLocation.getY(), sleepLocation.getZ(), 1.0);
    }

    @Override
    public boolean isSleeping() {
        return this.isSleeping;
    }

    public void setSleeping(boolean sleeping) {
        this.isSleeping = sleeping;
        if (!this.level.isClientSide) {
            this.entityData.set(WATCHER_IS_SLEEPING, this.isSleeping);
        }
    }

    public void tranquilize(int ticks) {
        tranquilizerTicks = 50 + ticks + this.random.nextInt(50);
        setSleeping(true);
        this.tranqed = true;
    }

    public int getStayAwakeTime() {
        return this.stayAwakeTime;
    }

    public void disturbSleep() {
        if(tranquilizerTicks == 0) {
            this.isSleeping = false;
            this.stayAwakeTime = 400;
        }
    }

    public void writeStatsToLog() {
        LOGGER.info(this.toString());
    }

    @Override
    public String toString() {
        return "DinosaurEntity{ " +
                this.dinosaur.getName() +
                ", id=" + this.getId() +
                ", remote=" + this.getLevel().isClientSide +
                ", isDead=" + this.isDeadOrDying() +
                ", isCarcass=" + this.isCarcass +
                ", isSleeping=" + this.isSleeping +
                ", stayAwakeTime=" + this.stayAwakeTime +
                "\n    " +
                ", dinosaurAge=" + this.dinosaurAge +
                ", prevAge=" + this.prevAge +
                ", maxAge" + this.dinosaur.getMaximumAge() +
                ", ticksExisted=" + this.tickCount +
                ", entityAge=" + this.noActionTime +
                ", isMale=" + this.isMale +
                ", growthSpeedOffset=" + this.growthSpeedOffset +
                "\n    " +
                ", food=" + this.metabolism.getEnergy() + " / " + this.metabolism.getMaxEnergy() + " (" + this.metabolism.getMaxEnergy() * 0.875 + ")" +
                ", water=" + this.metabolism.getWater() + " / " + this.metabolism.getMaxWater() + " (" + this.metabolism.getMaxWater() * 0.875 + ")" +
                ", digestingFood=" + this.metabolism.getDigestingFood() + " / " + MetabolismContainer.MAX_DIGESTION_AMOUNT +
                ", health=" + this.getHealth() + " / " + this.getMaxHealth() +
                "\n    " +
                ", pos=" + this.getEyePosition() +
                ", eyePos=" + this.getHeadPos() +
                ", eyeHeight=" + this.getEyeHeight() +
                ", lookX=" + this.getLookAngle().x + ", lookY=" + this.getLookAngle().y + ", lookZ=" + this.getLookAngle().z +
                "\n    " +
                ", width=" + this.getBbWidth() +
                ", bb=" + this.getBoundingBox() +
//                "\n    " +
//                ", anim=" + animation + (animation != null ? ", duration" + animation.duration : "" ) +

//                "dinosaur=" + dinosaur +
//                ", genetics=" + genetics +
//                ", geneticsQuality=" + geneticsQuality +
//                ", currentAnim=" + currentAnim +
//                ", animation=" + animation +
//                ", animTick=" + animTick +
//                ", hasTracker=" + hasTracker +
//                ", tailBuffer=" + tailBuffer +
                ", owner=" + owner +
                ", inventory=" + inventory +
//                ", metabolism=" + metabolism +
                " }";
    }

    public Vector3d getHeadPos() {
        double scale = this.interpolate(dinosaur.getScaleInfant(), dinosaur.getScaleAdult());

        double[] headPos = this.dinosaur.getHeadPosition(this.getGrowthStage(), ((360 - this.yHeadRot)) % 360 - 180);

        double headX = ((headPos[0] * 0.0625F) - dinosaur.getOffsetX()) * scale;
        double headY = (((24 - headPos[1]) * 0.0625F) - dinosaur.getOffsetY()) * scale;
        double headZ = ((headPos[2] * 0.0625F) - dinosaur.getOffsetZ()) * scale;

        return new Vector3d(this.getX() + headX, this.getY() + (headY), this.getZ() + headZ);
    }

    public boolean areEyelidsClosed() {
        return this.getDinosaurAge() != 4 && !this.dinosaur.isMarineCreature() && ((this.isCarcass || this.isSleeping) || this.tickCount % 100 < 4);
    }

    @Override
    public boolean shouldUseInertia() {
        return this.useInertialTweens;
    }

    public void setUseInertialTweens(boolean parUseInertialTweens) {
        this.useInertialTweens = parUseInertialTweens;
    }

//    @Override
//    public ItemStack getPickedResult(RayTraceResult target) {
//        return new ItemStack(ItemHandler.SPAWN_EGG, 1, EntityHandler.getDinosaurId(this.dinosaur));
//    }


    @org.jetbrains.annotations.Nullable
    @Override
    public ItemStack getPickResult() {
        return null;//TODO: mob eggs
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.9F;
    }

//    @Override
//    public void moveRelative(float strafe, float up, float forward, float friction) {
//        if(this.inWater() && !this.canDinoSwim()) {
//            friction *= 10;//times by 5, but as friction is divided by 2 when in water do 5 * 2 instead - legacy comment /shrug
//        }
//        super.moveRelative(strafe, up, forward, friction);
//    }

    public void giveBirth() {
        pregnantTime = 1;
    }

    public void setDeathIn(int ticks) { // :(
        this.ticksUntilDeath = ticks;

        this.addEffect(new MobEffectInstance(MobEffects.POISON, ticks));

    }



//    public void (Entity entity) { todo: weak; disturb sleep
//        super.collideWithEntity(entity);
//
//        if (this.isSleeping && !this.isRidingSameEntity(entity)) {
//            if (!entity.noClip && !this.noClip) {
//                if (entity.getClass() != this.getClass()) {
//                    this.disturbSleep();
//                }
//            }
//        }
//    }


    protected void setSize(float width, float height) {
        if (width != this.getBbWidth() || height != this.getBbHeight()) {
            float prevWidth = this.getBbWidth();
            float tempWidth = width;
            float tempHeight = height;
            if (!this.deserializing) {
                AABB bounds = this.getBoundingBox();
                AABB newBounds = new AABB(bounds.minX, bounds.minY, bounds.minZ, bounds.minX + tempWidth, bounds.minY + tempHeight, bounds.minZ + tempWidth);
                if (!this.level.noCollision(newBounds)) {
                    this.setBoundingBox(newBounds);
                    if (this.getBbWidth() > prevWidth && !this.firstTick && !this.level.isClientSide) {
                        this.move(MoverType.SELF, new Vec3(prevWidth - this.getBbWidth(), 0.0F, prevWidth - this.getBbWidth()));
                    }
                }
            } else {
                float halfWidth = this.getBbWidth() / 2.0F;
                this.setBoundingBox(new AABB(this.getX() - halfWidth, this.getY(), this.getZ() - halfWidth, this.getX() + halfWidth, this.getY() + this.getBbHeight(), this.getZ() + halfWidth));
            }
        }
    }

    public Order getOrder() {
        return this.order;
    }

    public void setFieldOrder(Order order) {

        this.order = order;
        this.entityData.set(WATCHER_CURRENT_ORDER, (byte) order.ordinal());

    }

//    public void setOrder(Order order) { todo: networking
//
//        if (this.level.isClientSide) {
//            if (this.owner != null) {
//                Player player = this.level.getPlayerEntityByUUID(this.owner);
//
//                if (player != null) {
//                    TextComponentString change = new TextComponentString(LangUtils.translate(LangUtils.SET_ORDER).replace("{order}", LangUtils.translate(LangUtils.ORDER_VALUE.get(order.name().toLowerCase(Locale.ENGLISH)))));
//                    change.getStyle().setColor(TextFormatting.GOLD);
//                    ClientProxy.MC.ingameGUI.addChatMessage(ChatType.GAME_INFO, change);
//                }
//            }
//
//            RebornMod.NETWORK_WRAPPER.sendToServer(new SetOrderMessage(this));
//        }
//    }

//    @SafeVarargs todo: legacy, goals
//    public final void target(Class<? extends LivingEntity>... targets) {
//        this.targetSelector.addGoal(1, new TargetGoal(this, targets) {
//        });
//
//        this.attackTargets.addAll(Lists.newArrayList((Class<? extends LivingEntity>) targets));
//    }

//    public EntityAIBase getAttackAI() { todo:legacy, goals
//        return new DinosaurAttackMeleeEntityAI(this, this.dinosaur.getAttackSpeed(), false);
//    }

    public List<Class<? extends LivingEntity>> getAttackTargets() {
        return this.attackTargets;
    }

//    @Override
//    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData data) {
//        this.metabolism.setEnergy(this.metabolism.getMaxEnergy());
//        this.metabolism.setWater(this.metabolism.getMaxWater());
//        this.genetics = GeneticsHelper.randomGenetics(this.rand);
//        this.setFullyGrown();
//        this.setMale(this.rand.nextBoolean());
//        this.setDNAQuality(100);
//
//        // Assure that the width and height attributes have proper values
//        this.updateBounds();
//        return data;
//    }


    public int getAttackCooldown() {
        return this.attackCooldown;
    }

    public void resetAttackCooldown() {
        this.attackCooldown = 50 + this.getRandom().nextInt(20);
    }

    public void respondToAttack(LivingEntity attacker) {
        if (attacker != null && !attacker.isDeadOrDying() && !(attacker instanceof Player && ((Player) attacker).isCreative())) {
            List<LivingEntity> enemies = new LinkedList<>();

            if (attacker instanceof DinosaurEntity) {
                DinosaurEntity enemyDinosaur = (DinosaurEntity) attacker;

                if (enemyDinosaur.herd != null) {
                    enemies.addAll(enemyDinosaur.herd.members);
                }
            } else {
                enemies.add(attacker);
            }

            if (enemies.size() > 0) {
                Herd herd = this.herd;

                if (herd != null) {
                    herd.fleeing = !herd.shouldDefend(enemies) || this.dinosaur.shouldFlee();

                    for (LivingEntity entity : enemies) {
                        if (!herd.enemies.contains(entity)) {
                            herd.enemies.add(entity);
                        }
                    }
                } else {
                    this.setTarget(enemies.get(this.getRandom().nextInt(enemies.size())));
                }
            }
        }
    }

    public int getAnimationLength() {
        return this.animationLength;
    }

    @Override
    public boolean isRunning() {
        return this.entityData.get(WATCHER_IS_RUNNING);
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader pLevel) {
        if(!pLevel.isClientSide())
            return super.checkSpawnObstruction(pLevel) && this.level.dimensionType().equals(getServer().overworld().dimensionType());
        return false;
    }

    public boolean shouldEscapeWaterFast() {
        return true;
    }//so many things in this mod just make me go ***why*** - gamma_02



    public void target(Class<? extends LivingEntity>... entities){
        if(this.taskHelper == null) {//don't want to initalize it twice
            this.taskHelper = new TaskHelper(this.getClass());
        }
        this.taskHelper.addGoal(new NearestAttackableTargetGoal<>(this, LivingEntity.class, 5, false, false, livingEntity -> entityPredicate(livingEntity, entities)), 3/*hope this randomly assigned 3 doesn't mess anything up*/);
    }

    public static boolean entityPredicate(LivingEntity input, Class<? extends LivingEntity>... entities){
        return Arrays.stream(entities).anyMatch((clazz) -> clazz == input.getClass());
    }

    public void addTask(int priority, Goal goal){
        if(this.taskHelper == null){//jesus christ
            this.taskHelper = new TaskHelper(this.getClass());
        }
        this.taskHelper.addGoal(goal, priority);
    }

    protected void registerGoals(){
        if(this.taskHelper == null){
//            LOGGER.warning("NO TASK HELPER FOUND, " + this.getName().getString() + " HAS NO GOALS");
            this.taskHelper = new TaskHelper(this.goalSelector, this.targetSelector, this.getClass());
        }
        this.taskHelper.setSelectorsAndRegisterGoals(this.goalSelector, this.targetSelector);
    }


    /**
     *
     * @author gamma_02
     * @return the closest feeder to the entity
     * @deprecated I'm moving this to the {@link net.gamma02.jurassicworldreborn.common.blocks.entities.feeder.FeederBlockEntity FeederBlockEntity} to make tick loops faster and ensure that the entities can pathfind to the feeder.
     */
    @Deprecated(since = "port", forRemoval = true)
    public BlockPos getClosestFeeder() {

        if (this.tickCount - this.feederSearchTick > 200) {
            this.feederSearchTick = this.tickCount;
            OnionTraverser traverser = new OnionTraverser(this.getOnPos(), 32);
            for (BlockPos pos : traverser) {
                BlockState state = this.level.getBlockState(pos);
                if (state.getBlock() instanceof FeederBlock) {
                    BlockEntity tile = this.level.getBlockEntity(pos);
                    if (tile instanceof FeederBlockEntity feeder) {
                        if (feeder.canFeedDinosaur(this) && feeder.getFeeding() == null && feeder.openAnimation == 0) {
                            Path path = this.getNavigation().createPath(pos, 1);
                            if (path != null && path.getDistToTarget() != 0) {
                                return this.closestFeeder = pos;
                            }
                        }
                    }
                }
            }
        }
        return this.closestFeeder;
    }


    @Override
    public boolean isClimbing() {
        return false;
    }

    @Override
    public boolean isMoving() {
        float deltaX = (float) (this.getX() - this.xOld);
        float deltaZ = (float) (this.getZ() - this.zOld);
        return deltaX * deltaX + deltaZ * deltaZ > 0.001F;
    }

    @Override
    public boolean canUseGrowthStage(GrowthStage growthStage) {
        return this.dinosaur.doesSupportGrowthStage(growthStage);
    }

    @Override
    public boolean isMarineCreature() {
        return this.dinosaur.isMarineCreature();
    }

    @Override
    public <ENTITY extends LivingEntity & Animatable> PoseHandler<ENTITY> getPoseHandler() {
        return (PoseHandler<ENTITY>) this.dinosaur.getPoseHandler();
    }

    @Override
    public boolean inWater() {
        return this.isInWater();
    }

    @Override
    public boolean inLava() {
        return this.inLava;
    }

    public DinosaurAttributes getLegacyAttributes() {
        return this.attributes;
    }

    public boolean isBreeding() {
        return this.breeding != null;
    }

    public void setAttributes(DinosaurAttributes attributes) {
        this.attributes = attributes;
    }

    public void setJumpHeight(int jumpHeight) {
        this.jumpHeight = jumpHeight;
    }

//    @Override
    protected float getJumpUpwardsMotion() {
        return (float) Math.sqrt((this.jumpHeight + 0.2) * 0.27);
    }

    public boolean isSkeleton() {
        return this.getGrowthStage() == GrowthStage.SKELETON;
    }

    public void setSkeleton(boolean isSkeleton) {
        this.isSkeleton = isSkeleton;
    }

    public void setSkeletonVariant(byte variant) {
        this.skeletonVariant = variant;
    }

    public byte getSkeletonVariant() {
        return this.skeletonVariant;
    }

    public void setIsFossile(boolean isFossile) {
        this.isFossile = isFossile;
    }

    public boolean getIsFossile() {
        return this.isFossile;
    }

    public boolean canDinoSwim() {
        return true;
    }

    public Vector3f getDinosaurCultivatorRotation() {///...what????
        this.setAnimation(EntityAnimation.GESTATED.get());
        return new Vector3f();
    }




//    Vector3f@Override
//    protected void wa() {
//        if(this.canDinoSwim()) {
//            super.handleJumpWater();
//        }
//    }

    public static class FieldGuideInfo {
        public int hunger;
        public int thirst;
        public boolean flocking;
        public boolean scared;
        public boolean hungry;
        public boolean thirsty;
        public boolean poisoned;

        public static FieldGuideInfo deserialize(FriendlyByteBuf buf) {
            FieldGuideInfo info = new FieldGuideInfo();
            info.flocking = buf.readBoolean();
            info.scared = buf.readBoolean();
            info.hunger = buf.readInt();
            info.thirst = buf.readInt();
            info.hungry = buf.readBoolean();
            info.thirsty = buf.readBoolean();
            info.poisoned = buf.readBoolean();
            return info;
        }

        public static FieldGuideInfo serialize(FriendlyByteBuf buf, DinosaurEntity entity) {
            MetabolismContainer metabolism = entity.getMetabolism();
            Herd herd = entity.herd;
            FieldGuideInfo info = new FieldGuideInfo();
            info.flocking = herd != null && herd.members.size() > 1 && herd.state == Herd.State.MOVING;
            info.scared = herd != null && herd.fleeing;
            info.hunger = metabolism.getEnergy();
            info.thirst = metabolism.getWater();
            info.hungry = metabolism.isHungry();
            info.thirsty = metabolism.isThirsty();
            info.poisoned = entity.hasEffect(MobEffects.POISON);
            buf.writeBoolean(info.flocking);
            buf.writeBoolean(info.scared);
            buf.writeInt(info.hunger);
            buf.writeInt(info.thirst);
            buf.writeBoolean(info.hungry);
            buf.writeBoolean(info.thirsty);
            buf.writeBoolean(info.poisoned);
            return info;
        }
    }

    public enum Order {
        WANDER, FOLLOW, SIT
    }


}