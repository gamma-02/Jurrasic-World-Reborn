package net.gamma02.jurassicworldreborn.common.entities.EntityUtils.ai;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurHandler;
import net.minecraft.world.entity.ai.control.JumpControl;

public class DinosaurJumpHelper extends JumpControl {
    private final DinosaurEntity entity;
    private boolean sounded;
    private boolean jumped;
    private float yaw;
    private double speed;
    private int jumpHeight;

    public DinosaurJumpHelper(DinosaurEntity entity) {
        super(entity);
        this.entity = entity;
    }

    @Override
    public void jump() {
        this.jump(1);
    }

    public void jump(int height) {
        this.jump = true;
        this.jumped = true;
        this.jumpHeight = Math.min(entity.getDinosaur().getJumpHeight(), height);
        this.yaw = this.entity.getYRot();
        this.speed = this.entity.getSpeed();
        Animation animation = this.entity.getAnimation();
        if (this.jumpHeight > 1 && animation != EntityAnimation.PREPARE_LEAP.get() && animation != EntityAnimation.LEAP.get() && animation != EntityAnimation.LEAP_LAND.get()) {
            if (!this.entity.isInWater() && !this.entity.inLava() && this.entity.isOnGround()) {
                this.entity.setAnimation(EntityAnimation.PREPARE_LEAP.get());
            }
            this.sounded = false;
        } else {
            this.sounded = true;
        }
    }

    @Override
    public void tick() {
        if (!this.sounded && this.jumpHeight > 1 && this.entity.getAnimation() == EntityAnimation.LEAP.get()) {
            this.entity.playSound(this.entity.getSoundForAnimation(EntityAnimation.ATTACKING.get()), this.entity.getSoundVolume(), this.entity.getVoicePitch());
            this.sounded = true;
        }
        if (this.jump && (this.jumpHeight <= 1 || this.entity.getAnimation() == EntityAnimation.LEAP.get())) {
            this.entity.setJumping(this.jump);
            this.entity.setJumpHeight(this.jumpHeight);
            this.jump = false;
            this.jumpHeight = 0;
        } else {
            if (this.jumped) {
                if (this.entity.getAnimation() == EntityAnimation.LEAP.get() || this.entity.getAnimation() == EntityAnimation.LEAP_LAND.get()) {
                    this.entity.setYRot(this.yaw);
//                    /* legTODO: Make sure works */this.entity.travel(0.0F, (float) this.speed * 0.2F, 0F);
                    this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(0, this.speed*0.2, 0));

                }
            }
            if (this.entity.getAnimation() == EntityAnimation.LEAP_LAND.get()) {
                this.jumped = false;
                this.sounded = false;
            }
            this.entity.setJumping(false);
            this.entity.setJumpHeight(0);
        }
    }
}
