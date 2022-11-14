package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Iterator;

import static net.minecraft.world.InteractionHand.MAIN_HAND;

public class MicroraptorEntity extends DinosaurEntity {
    private int flyTime;
    private int groundHeight;
    private Vec3 glidingPos;

    public MicroraptorEntity(Level world, EntityType type) {
        super(world, type);
        this.target(Chicken.class, Rabbit.class, CompsognathusEntity.class, HypsilophodonEntity.class, LeptictidiumEntity.class, MicroceratusEntity.class, OthnieliaEntity.class);
//        this.tasks.addTask(1, new LeapingMeleeEntityAI(this, this.dinosaur.getAttackSpeed()));TODO:AI
//        this.tasks.addTask(2, new RaptorClimbTreeAI(this, 1.0f));
//        this.animationTasks.addTask(3, new BirdPreenAnimationAI(this));
//        this.animationTasks.addTask(3, new TailDisplayAnimationAI(this));
    }

    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        return damageSource != DamageSource.FLY_INTO_WALL && super.hurt(damageSource, amount);
    }

//    protected void applyEntityAttributes()
//    {
//        super.applyEntityAttributes();
//        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
//    }


    @Override
    public void tick() {
        super.tick();
        Animation curAni = this.getAnimation();
        boolean climbing = curAni == EntityAnimation.CLIMBING.get() || curAni == EntityAnimation.START_CLIMBING.get();

        if (climbing) {
            BlockPos trunk = new BlockPos(this.getX(), this.getBoundingBox().minY, this.getZ());
            for (Iterator<Direction> it = Direction.Plane.HORIZONTAL.iterator(); it.hasNext(); ) {
                Direction facing = it.next();
                if (!level.getBlockState(trunk.relative(facing)).isAir() && this.level.getBlockState(trunk.relative(facing)).isCollisionShapeFullBlock(level, trunk.relative(facing))) {
                    this.yHeadRot = this.yHeadRotO = this.yRotO = facing.toYRot();
                    this.setYRot(this.yHeadRot);
                    this.yBodyRot = this.yBodyRotO = this.getYRot();
                    this.lerpSteps = 0;
                    break;
                }
            }
        }
    }
    
    @Override
    public void travel(Vec3 vec) {
        float strafe = (float)vec.x;
        float vertical = (float)vec.y;
        float forward = (float) vec.z;
	    float prevRot = this.getXRot();
        if(this.getAnimation() == EntityAnimation.GLIDING.get() && glidingPos != null) {
            this.setXRot((float) -Math.toDegrees(Math.asin((this.glidingPos.y - this.getY()) / glidingPos.distanceTo(this.position()))));
        }
        super.travel(new Vec3(strafe, vertical, forward));
        this.setXRot( prevRot);
    }



    @Override
    public void aiStep() {
        
        super.aiStep();

        
        if (this.level.isClientSide) {
            this.updateClientControls();
        }
        Animation curAni = this.getAnimation();
        boolean landing = curAni == EntityAnimation.LEAP_LAND.get();
        boolean gliding = curAni == EntityAnimation.GLIDING.get();
        boolean climbing = curAni == EntityAnimation.CLIMBING.get() || curAni == EntityAnimation.START_CLIMBING.get();
        boolean leaping = curAni == EntityAnimation.LEAP.get();

        if (this.onGround || this.inWater() || this.inLava() || this.isSwimming()) {
            this.flyTime = 0;
            if (gliding || landing) {
                this.setAnimation(EntityAnimation.IDLE.get());
                this.setSharedFlag(7, false);
            }
        } else {
            this.flyTime++;
            if (this.flyTime > 4 && !leaping) {
                if (!landing) {
                    if (!gliding) {
                        if (!climbing) {
                            this.setAnimation(EntityAnimation.GLIDING.get());
                        }
                    } else if (!this.level.getBlockState(this.getOnPos().below()).isAir()) {
                        this.setAnimation(EntityAnimation.LEAP_LAND.get());
                    }
                }
                if (gliding) {
                    this.setSharedFlag(7, true);
                }
            }
        }

        if (this.isFallFlying()) {
            this.groundHeight = 0;
            BlockPos pos = this.getOnPos();
            while (this.groundHeight <= 10) {
                if (this.level.getBlockState(pos).isFaceSturdy(level, pos, Direction.UP)) {
                    break;
                }
                pos = pos.below();
                this.groundHeight++;
            }
        }

        if (!level.isClientSide()) {
            this.getLookControl().tick();
        }
    }

    @Override
    public LookControl getLookControl() {
        return super.getLookControl();
    }

    @Override
    public Vec3 getLookAngle() {
        if (this.getAnimation() == EntityAnimation.GLIDING.get() && glidingPos != null) {
            double distance = glidingPos.distanceTo(this.getEyePosition());
            return new Vec3((glidingPos.x - this.getX()) / distance, (this.glidingPos.y - this.getY()) / distance, (this.glidingPos.z - this.getZ()) / distance);
        }
        return super.getLookAngle();
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.isCrouching() && hand == MAIN_HAND) {
            if (this.isOwner(player) && this.order == Order.SIT && player.getPassengers() != null && player.getPassengers().size() < 2) {
                return player.startRiding(this, true) ? InteractionResult.SUCCESS : InteractionResult.FAIL;
            }
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) {
        if (this.getAnimation() == EntityAnimation.GLIDING.get() && slot == EquipmentSlot.CHEST) {
            return new ItemStack(Items.ELYTRA);
        }
        return super.getItemBySlot(slot);
    }

//    @Override TODO: more AI
//    public EntityAIBase getAttackAI() {
//        return new RaptorLeapEntityAI(this);
//    }

    @Override
    public int calculateFallDamage(float distance, float damageMultiplier) {
        if (this.getAnimation() != EntityAnimation.LEAP_LAND.get()) {
            return super.calculateFallDamage(distance / 2.0F, damageMultiplier);
        }
        return super.calculateFallDamage(distance, damageMultiplier);
    }



//    @Override TODO:AI
//    protected PathNavigation createNavigation(Level worldIn) {
//        return new PathNavigateClimber(this, worldIn);
//    }

    @Override
    public SoundEvent getSoundForAnimation(Animation animation) {
        switch (EntityAnimation.getAnimation(animation)) {
            case SPEAK:
                return SoundHandler.MICRORAPTOR_LIVING;
            case DYING:
                return SoundHandler.MICRORAPTOR_DEATH;
            case INJURED:
                return SoundHandler.MICRORAPTOR_HURT;
            case ATTACKING:
                return SoundHandler.MICRORAPTOR_ATTACK;
            case CALLING:
                return SoundHandler.MICRORAPTOR_LIVING;
		default:
			break;
        }

        return null;
    }

    @Override
    public int getMaxFallDistance() {
        return 100;
    }

    @Override
    public void rideTick() {
        Entity entity = this.getVehicle();
        if (this.isPassenger() && !entity.isAlive()) {
            this.stopRiding();
        } else {
            this.setDeltaMovement(new Vec3(0, 0, 0));
            this.tick();
            if (this.isPassenger()) {
                this.updateRiding(entity);
            }
        }
    }

    private void updateRiding(Entity riding) {
        if (riding.isPassenger() && riding instanceof Player) {
            Player player = (Player) riding;
            int ridingIndex = riding.getPassengers().indexOf(this);
            float radius = (ridingIndex == 2 ? 0.0F : 0.35F) + (player.isFallFlying() ? 2 : 0);
            float renderYawOffset = player.yBodyRot;
            float angle = (float) Math.toRadians(renderYawOffset + (ridingIndex == 1 ? -90 : ridingIndex == 0 ? 90 : 0));
            double offsetX = (double) (radius * Mth.sin((float) (Math.PI + angle)));
            double offsetZ = (double) (radius * Mth.cos(angle));
            double offsetY = (riding.isCrouching() ? 1.2 : 1.38) + (ridingIndex == 2 ? 0.4 : 0.0);
            this.setYBodyRot(player.yBodyRot);
            this.setYHeadRot(player.yHeadRot);
            this.yHeadRotO = player.yHeadRot;
            this.setPos(riding.getX() + offsetX, riding.getY() + offsetY, riding.getZ() + offsetZ);
            this.setAnimation(EntityAnimation.IDLE.get());
            if (player.isFallFlying()) {
                player.stopRiding();
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    protected void updateClientControls() {
        Minecraft mc = Minecraft.getInstance();
        if (this.getControllingPassenger() != null && this.getControllingPassenger() == mc.player) {
//            if (ClientProxy.getKeyHandler().MICRORAPTOR_DISMOUNT.isKeyDown()) {
//                RebornMod.NETWORK_WRAPPER.sendToServer(new MicroraptorDismountMessage(this.getEntityId()));
//            }TODO: networking
        }
    }
    
    @Override
    public boolean canDinoSwim() {
//        if(RebornConfig.ENTITIES.featheredDinosaurSwimming == true) { TODO: config
//            return true;
//        } else {
//        return false;
        return false;
    }
    
    @Override
    public boolean shouldEscapeWaterFast() {
	int radiusXZ = 4;
	
        for(BlockPos pos : BlockPos.betweenClosed(Mth.floor(this.getX() - radiusXZ), Mth.floor(this.getY()), Mth.floor(this.getZ() - radiusXZ), Mth.ceil(this.getX() + radiusXZ), Mth.ceil(this.getY()), Mth.ceil(this.getZ() + radiusXZ))) {
            if(!level.getBlockState(pos).getMaterial().isLiquid()) {
                return false;
            }
        }
        return false;
    }
    
    public void setGlidingTo(Vec3 glidingPos) {
	this.glidingPos = glidingPos;
    }
}
