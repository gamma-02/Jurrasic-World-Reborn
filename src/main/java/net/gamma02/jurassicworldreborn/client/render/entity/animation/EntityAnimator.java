package net.gamma02.jurassicworldreborn.client.render.entity.animation;

import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.ITabulaModelAnimator;
import com.github.alexthe666.citadel.client.model.TabulaModel;
import net.gamma02.jurassicworldreborn.client.model.AnimatableModel;
import net.gamma02.jurassicworldreborn.client.render.RenderingHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Animatable;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.GrowthStage;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.EnumMap;
import java.util.Map;
import java.util.WeakHashMap;

@OnlyIn(Dist.CLIENT)
public abstract class EntityAnimator<ENTITY extends LivingEntity & Animatable> implements ITabulaModelAnimator<ENTITY> {
    protected EnumMap<GrowthStage, Map<ENTITY, JabelarAnimationHandler<ENTITY>>> animationHandlers = new EnumMap<>(GrowthStage.class);//what is this. why would you do this. my eyes hurt. - gamma

    public EntityAnimator(){
        RenderingHandler.ANIMATORS.add(this);
    }

    private JabelarAnimationHandler<ENTITY> getAnimationHelper(ENTITY entity, AnimatableModel model, boolean useInertialTweens) {
        GrowthStage growth = entity.getGrowthStage();
        Map<ENTITY, JabelarAnimationHandler<ENTITY>> growthToRender = this.animationHandlers.get(growth);

        if (growthToRender == null) {
            growthToRender = new WeakHashMap<>();//WHAT THE FUCK IS A WEAK HASH MAP - gamma
            this.animationHandlers.put(growth, growthToRender);
        }

        JabelarAnimationHandler<ENTITY> render = growthToRender.get(entity);

        if (render == null) {
            render = entity.<ENTITY>getPoseHandler().createAnimationHandler(entity, model, growth, useInertialTweens);
            growthToRender.put(entity, render);
        }

        return render;
    }

    @Override
    public final void setRotationAngles(TabulaModel model, ENTITY entity, float limbSwing, float limbSwingAmount, float ticks, float rotationYaw, float rotationPitch, float scale) {
        this.getAnimationHelper(entity, (AnimatableModel) model, entity.shouldUseInertia()).performAnimations(entity, limbSwing, limbSwingAmount, ticks);
        for(int i = 0;true;i++) {
            AdvancedModelBox cube = model.getCube("neck" + i++);
            if(cube == null) {
                cube = model.getCube("throat" + i++);
            }
            float j = 1 - (i * 0.00001F);
            if(cube != null ) {
                cube.scaleX *= j;
                cube.scaleY *= j;
                cube.scaleZ *= j;

            }
            break;
        }
        this.performAnimations((AnimatableModel) model, entity, limbSwing, limbSwingAmount, ticks, rotationYaw, rotationPitch, scale);
    }

    protected void performAnimations(AnimatableModel parModel, ENTITY entity, float limbSwing, float limbSwingAmount, float ticks, float rotationYaw, float rotationPitch, float scale) {
    }
}