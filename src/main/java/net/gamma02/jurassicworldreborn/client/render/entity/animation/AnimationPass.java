package net.gamma02.jurassicworldreborn.client.render.entity.animation;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Animatable;
import net.minecraft.util.Mth;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayDeque;
import java.util.Map;

public class AnimationPass {
    protected final Map<Animation, float[][]> animations;
    protected final PosedCuboid[][] poses;
    protected float[][] rotationIncrements;
    protected float[][] positionIncrements;
    protected float[][] prevRotationIncrements;
    protected float[][] prevPositionIncrements;

    protected float[][] prevPosition;
    protected float[][] prevRotation;
    protected int poseCount;
    protected int poseIndex;
    protected float poseLength;

    protected float animationTick;
    protected float prevTicks;

    protected AdvancedModelBox[] parts;
    protected PosedCuboid[] pose;
    protected ArrayDeque<PosedCuboid[]> prevPose = new ArrayDeque<>();

    protected ArrayDeque<Animation> animation = new ArrayDeque<>();

    protected boolean useInertia;

    protected float inertiaFactor = 1.0f;

    protected float limbSwing;
    protected float limbSwingAmount;

    public AnimationPass(Map<Animation, float[][]> animations, PosedCuboid[][] poses, boolean useInertia) {
        this.animations = animations;
        this.poses = poses;
        this.useInertia = useInertia;
    }

    public void init(AdvancedModelBox[] parts, Animatable entity) {
        this.parts = parts;

        this.prevRotationIncrements = new float[parts.length][3];
        this.prevPositionIncrements = new float[parts.length][3];
        this.rotationIncrements = new float[parts.length][3];
        this.positionIncrements = new float[parts.length][3];
        this.prevRotation = new float[parts.length][3];
        this.prevPosition = new float[parts.length][3];


        this.animation.add(this.getRequestedAnimation(entity));
        this.initPoseModel();
        this.initAnimation(entity, this.getRequestedAnimation(entity));
        this.initAnimationTicks(entity);

        this.prevTicks = 0.0F;
        this.initIncrements(entity);
        this.performAnimations(entity, 0.0F, 0.0F, 0.0F);
    }

    public void initPoseModel() {
        float[][] pose = this.animations.get(this.animation.getFirst());
        if(this.pose != null){
            this.prevPose.add(this.pose);
        }
        if (pose != null) {
            this.poseCount = pose.length;
            this.poseIndex = 0;
            this.pose = this.poses[(int) pose[this.poseIndex][0]];
        }
    }

    protected void initIncrements(Animatable entity) {
        float animationDegree = this.getAnimationDegree(entity);

        for (int partIndex = 0; partIndex < Math.min(this.pose.length, this.parts.length); partIndex++) {
            AdvancedModelBox part = this.parts[partIndex];
            PosedCuboid nextPose = this.pose[partIndex];

            float[] rotationIncrements = this.rotationIncrements[partIndex];
            float[] positionIncrements = this.positionIncrements[partIndex];

            rotationIncrements[0] = (nextPose.rotationX - (part.defaultRotationX + this.prevRotationIncrements[partIndex][0])) * animationDegree;
            rotationIncrements[1] = (nextPose.rotationY - (part.defaultRotationY + this.prevRotationIncrements[partIndex][1])) * animationDegree;
            rotationIncrements[2] = (nextPose.rotationZ - (part.defaultRotationZ + this.prevRotationIncrements[partIndex][2])) * animationDegree;

            positionIncrements[0] = (nextPose.positionX - (part.defaultPositionX + this.prevPositionIncrements[partIndex][0])) * animationDegree;
            positionIncrements[1] = (nextPose.positionY - (part.defaultPositionY + this.prevPositionIncrements[partIndex][1])) * animationDegree;
            positionIncrements[2] = (nextPose.positionZ - (part.defaultPositionZ + this.prevPositionIncrements[partIndex][2])) * animationDegree;

//            System.out.println(Arrays.toString(this.rotationIncrements[partIndex]));
        }
    }

    public void initAnimation(Animatable entity, Animation animation) {
        this.animation.add(animation);

        if (this.animations.get(animation) == null) {
            this.animation.remove(animation);
            this.animation.add( EntityAnimation.IDLE.get() );
        }
    }

    protected float calculateInertiaFactor() {
        float inertiaFactor = this.animationTick / this.poseLength;

        if (this.useInertia && EntityAnimation.getAnimation(this.animation.getFirst()).useInertia()) {
            inertiaFactor = (float) (Math.sin(Math.PI * (inertiaFactor - 0.5D)) * 0.5D + 0.5D);
        }

        return Math.min(1.0F, Math.max(0.0F, inertiaFactor));
    }

    public void performAnimations(Animatable entity, float limbSwing, float limbSwingAmount, float ageInTicks) {
        this.limbSwing = limbSwing;
        this.limbSwingAmount = limbSwingAmount;

        Animation requestedAnimation = this.getRequestedAnimation(entity);

        if (requestedAnimation != this.animation.getFirst()) {
            this.setAnimation(entity, requestedAnimation);
        }

        if (this.poseIndex >= this.poseCount) {
            this.poseIndex = this.poseCount - 1;
        }

//        this.inertiaFactor = this.calculateInertiaFactor();

        if (this.pose == null) {
            Jurassicworldreborn.getLogger().error("Trying to animate to a null pose array");
        } else {
            for (int partIndex = 0; partIndex < Math.min(this.parts.length, this.pose.length); partIndex++) {
                if (this.pose[partIndex] == null) {
                    Jurassicworldreborn.getLogger().error("Part " + partIndex + " in pose is null");
                } else {

                    this.applyRotations(partIndex);
                    this.applyTranslations(partIndex);
                }
            }
        }

        if (this.updateAnimationTick(entity, ageInTicks)) {
            this.onPoseFinish(entity, ageInTicks);
        }

        this.prevTicks = ageInTicks;
    }

    public boolean updateAnimationTick(Animatable entity, float ticks) {
        float incrementAmount = (ticks - this.prevTicks)/*elapsed time in ticks*/ * this.getAnimationSpeed(entity);
        if (this.animationTick < 0.0F) {
            this.animationTick = 0.0F;
        }
        if (!EntityAnimation.getAnimation(this.animation.getFirst()).shouldHold() || this.poseIndex < this.poseCount) {
            this.animationTick += incrementAmount;

            if (this.animationTick >= this.poseLength) {
                this.animationTick = this.poseLength;

                return true;
            }

            return false;
        } else {
            if (this.animationTick < this.poseLength) {
                this.animationTick += incrementAmount;

                if (this.animationTick >= this.poseLength) {
                    this.animationTick = this.poseLength;
                }
            } else {
                this.animationTick = this.poseLength;
            }

            return false;
        }
    }

    protected void applyRotations(int partIndex) {
        AdvancedModelBox part = this.parts[partIndex];

        float[] rotationIncrements = this.rotationIncrements[partIndex];

//        part.rotateAngleX = (rotationIncrements[0] * this.inertiaFactor + this.prevRotationIncrements[partIndex][0]);
//        part.rotateAngleY = (rotationIncrements[1] * this.inertiaFactor + this.prevRotationIncrements[partIndex][1]);
//        part.rotateAngleZ = (rotationIncrements[2] * this.inertiaFactor + this.prevRotationIncrements[partIndex][2]);
        this.prevRotation[partIndex][0] = part.rotateAngleX;
        this.prevRotation[partIndex][1] = part.rotateAngleY;
        this.prevRotation[partIndex][2] = part.rotateAngleZ;

        if(prevPose.isEmpty())
            prevPose.add(this.pose);


        //This sets the part's rotation angles to that of the pose's rotation angles. TODO: INTERPOLATION(either linear, customized bezier, or sine)
        part.rotateAngleX = this.quadrinomialInterpolatePose(this.prevPose.getFirst()[partIndex].rotationX, this.pose[partIndex].rotationX);
        part.rotateAngleY = this.quadrinomialInterpolatePose(this.prevPose.getFirst()[partIndex].rotationY, this.pose[partIndex].rotationY);
        part.rotateAngleZ = this.quadrinomialInterpolatePose(this.prevPose.getFirst()[partIndex].rotationZ, this.pose[partIndex].rotationZ);
    }

    protected void applyTranslations(int partIndex) {
        AdvancedModelBox part = this.parts[partIndex];

        float[] translationIncrements = this.positionIncrements[partIndex];

//        part.rotationPointX += (translationIncrements[0] * this.inertiaFactor + this.prevPositionIncrements[partIndex][0]);
//        part.rotationPointY += (translationIncrements[1] * this.inertiaFactor + this.prevPositionIncrements[partIndex][1]);
//        part.rotationPointZ += (translationIncrements[2] * this.inertiaFactor + this.prevPositionIncrements[partIndex][2]);

        this.prevPosition[partIndex][0] = part.rotationPointX;
        this.prevPosition[partIndex][1] = part.rotationPointY;
        this.prevPosition[partIndex][2] = part.rotationPointZ;

        //This sets the part's rotation points(?) to that of the pose's rotation points(?). TODO: INTERPOLATION(either linear, customized bezier, or sine)
        part.rotationPointX = this.quadrinomialInterpolatePose(this.prevPose.getFirst()[partIndex].positionX, this.pose[partIndex].positionX);
        part.rotationPointY = this.quadrinomialInterpolatePose(this.prevPose.getFirst()[partIndex].positionY, this.pose[partIndex].positionY);
        part.rotationPointZ = this.quadrinomialInterpolatePose(this.prevPose.getFirst()[partIndex].positionZ, this.pose[partIndex].positionZ);
    }

    protected void setPose(int poseIndex) {
        this.poseCount = this.animations.get(this.animation.getFirst()).length;
        this.poseIndex = poseIndex;
        this.prevPose.add(this.pose);
        this.pose = this.poses[(int) this.animations.get(this.animation.getFirst())[this.poseIndex][0]];
    }

    protected void initAnimationTicks(Animatable entity) {
        this.startAnimation(entity);
        if (EntityAnimation.getAnimation(this.animation.getFirst()).shouldHold()) {
            this.poseIndex = this.poseCount - 1;
            this.animationTick = this.animations.get(this.animation.getFirst())[this.poseIndex][1];
        } else {
            this.animationTick = 0;
        }
    }

    protected void startAnimation(Animatable entity) {
        float[][] pose = this.animations.get(this.animation.getFirst());
        if (pose != null) {
//            this.prevPose = this.pose;
            this.pose = this.poses[(int) this.animations.get(this.animation.getFirst())[this.poseIndex][0]];
            this.poseLength = Math.max(1, pose[this.poseIndex][1]);
            this.animationTick = 0;

            this.initIncrements(entity);
        }
    }

    protected void setPose(Animatable entity, float ticks) {
//        this.prevPose = this.pose;
        this.pose = this.poses[(int) this.animations.get(this.animation.getFirst())[this.poseIndex][0]];
        this.poseLength = this.animations.get(this.animation.getFirst())[this.poseIndex][1];
        this.animationTick = 0;
        this.prevTicks = ticks;
        this.initIncrements(entity);
    }

    protected void onPoseFinish(Animatable entity, float ticks) {
        if (this.incrementPose()) {
            this.setAnimation(entity, this.isEntityAnimationDependent() ? EntityAnimation.IDLE.get() : this.getRequestedAnimation(entity));
        } else {
            this.updatePreviousPose();
        }
        this.setPose(entity, ticks);
        this.prevPose.pop();
        this.animation.pop();
        if(this.animation.isEmpty()){
            this.animation.add(EntityAnimation.IDLE.get());
        }
    }

    public boolean incrementPose() {
        this.poseIndex++;
        if (this.poseIndex >= this.poseCount) {
            EntityAnimation animation = EntityAnimation.getAnimation(this.animation.getFirst());
            if (animation != null && animation.shouldHold()) {
                this.poseIndex = this.poseCount - 1;
            } else {
                this.poseIndex = 0;
                return true;
            }
        }
        return false;
    }

    protected void setAnimation(Animatable entity, Animation requestedAnimation) {
        this.updatePreviousPose();

        if (this.animations.get(requestedAnimation) != null && !(this.animation.getFirst() != EntityAnimation.IDLE.get() && this.animation.getFirst() == requestedAnimation && !this.isLooping())) {
            this.animation.add(requestedAnimation);
        } else {
            this.animation.add(EntityAnimation.IDLE.get());
        }

        this.setPose(0);

        this.animationStartTime = System.currentTimeMillis();

        this.startAnimation(entity);
    }

    protected void updatePreviousPose() {
        for (int partIndex = 0; partIndex < this.parts.length; partIndex++) {
            this.prevRotationIncrements[partIndex][0] += this.rotationIncrements[partIndex][0] * this.inertiaFactor;
            this.prevRotationIncrements[partIndex][1] += this.rotationIncrements[partIndex][1] * this.inertiaFactor;
            this.prevRotationIncrements[partIndex][2] += this.rotationIncrements[partIndex][2] * this.inertiaFactor;

            this.prevPositionIncrements[partIndex][0] += this.positionIncrements[partIndex][0] * this.inertiaFactor;
            this.prevPositionIncrements[partIndex][1] += this.positionIncrements[partIndex][1] * this.inertiaFactor;
            this.prevPositionIncrements[partIndex][2] += this.positionIncrements[partIndex][2] * this.inertiaFactor;
        }
    }

    //start interpolation code

    public long animationStartTime = 0;

    /**
     * This interpolates between each pose variable based upon the animation tick, specifically how far
     * through the animation this pass is, conformed to a float percentage between {@code 0.0F} and {@code 1.0F}
     *
     * @param oldPoseVar The old pose's variable to start at
     * @param newPoseVar the new pose's variable to end at
     * @return the result of a linear interpolation between the start and end var
     */
    public float linearInterpolatePose(float oldPoseVar, float newPoseVar){
        return Mth.lerp(conformAnimationTick(),
                oldPoseVar,
                newPoseVar);
    }

    public float quadrinomialInterpolatePose(float oldPoseVar, float newPoseVar){
//        float frameTime = Minecraft.getInstance().getPartialTick();
        long clientTickTime = (this.animationStartTime - getIntegerOverflowInsurance()) + 500 - (System.currentTimeMillis() - getIntegerOverflowInsurance());
        if( clientTickTime < 0 ){
            return newPoseVar;
        }
        float lerpTime = (Math.toIntExact(clientTickTime))/500F;

        if(oldPoseVar == newPoseVar){
//            System.out.println("old == new!");
        }

//        return Mth.lerp(conformQuadranomialTick(lerpTime), oldPoseVar, newPoseVar);
        return Mth.lerp(this.animationTick/this.poseLength, oldPoseVar, newPoseVar);



    }

    /**
     * bounds the animation tick between 0 and 1
     * @return the percentage of the animation that has been done
     */
    public float conformAnimationTick(){
//        System.out.println("Percent tick: " + this.animationTick / this.poseLength);
        return Mth.clamp(
                ( this.animationTick / (this.poseLength) ),
                0,
                1
        );
    }

    /**
     * bounds the animation tick between 0 and 1 using a
     * @return the percentage of the animation that has been done
     */

    public float conformQuadranomialTick(float conformee){

        return (float) Mth.clamp(
                Math.pow(conformee, 4),
                0,
                1
        );
    }

    public long getIntegerOverflowInsurance(){
        return 1000000000;
    }


    protected float getAnimationSpeed(Animatable entity) {
        return 1.0F;
    }

    protected float getAnimationDegree(Animatable entity) {
        return 1.0F;
    }

    protected Animation getRequestedAnimation(Animatable entity) {
        return entity.getAnimation();
    }

    protected boolean isEntityAnimationDependent() {
        return true;
    }

    public boolean isLooping() {
        return false;
    }
}
