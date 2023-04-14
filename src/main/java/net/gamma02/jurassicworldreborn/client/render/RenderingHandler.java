package net.gamma02.jurassicworldreborn.client.render;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.client.model.AnimatableModel;
import net.gamma02.jurassicworldreborn.client.render.entity.DinosaurRenderInfo;
import net.gamma02.jurassicworldreborn.client.render.entity.DinosaurRenderer;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimator;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.entity.AchillobatorAnimator;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.AchillobatorEntity;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurHandler;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.GrowthStage;
import net.gamma02.jurassicworldreborn.common.entities.ModEntities;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashMap;

import static net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurHandler.*;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Jurassicworldreborn.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RenderingHandler{

    public static ArrayList<EntityAnimator<? extends LivingEntity>> ANIMATORS = new ArrayList<>();



    @SubscribeEvent
    public void registerEntityRendersEvent(final EntityRenderersEvent.RegisterRenderers event){

        //Registration for the Achillobator. Did this first, all of the others should follow this template of sorts.
        event.registerEntityRenderer(ModEntities.ACHILLOBATOR_ENTITY_TYPE.get(), (ctx) ->
                new DinosaurRenderer(ctx,
                        getDefaultModelFromDinosaur(ACHILLOBATOR, new AchillobatorAnimator()),
                        0.5f,
                        ACHILLOBATOR,
                        new AchillobatorAnimator()));

    }


    private AnimatableModel getDefaultModelFromDinosaur(Dinosaur dino, EntityAnimator<? extends DinosaurEntity> anim){
        return new AnimatableModel(dino.getModelContainer(GrowthStage.ADULT), anim);
    }


}