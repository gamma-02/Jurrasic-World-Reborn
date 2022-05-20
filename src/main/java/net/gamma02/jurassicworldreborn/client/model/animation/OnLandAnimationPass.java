package net.gamma02.jurassicworldreborn.client.model.animation;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Animatable;

import java.util.Map;

public class OnLandAnimationPass extends AnimationPass {
    public OnLandAnimationPass(Map<Animation, float[][]> poseSequences, PosedCuboid[][] poses, boolean useInertialTweens) {
        super(poseSequences, poses, useInertialTweens);
    }

    @Override
    protected boolean isEntityAnimationDependent() {
        return false;
    }

    @Override
    protected Animation getRequestedAnimation(Animatable entity) {
        if (!entity.isCarcass() && !entity.inWater()) {
            return EntityAnimation.ON_LAND.get();
        }
        return EntityAnimation.IDLE.get();
    }

    @Override
    public boolean isLooping() {
        return true;
    }
}