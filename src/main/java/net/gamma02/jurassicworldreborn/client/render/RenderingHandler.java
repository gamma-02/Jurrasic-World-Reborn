package net.gamma02.jurassicworldreborn.client.render;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.client.model.AnimatableModel;
import net.gamma02.jurassicworldreborn.client.render.entity.DinosaurRenderer;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimator;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.entity.AchillobatorAnimator;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.ModEntities;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashMap;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Jurassicworldreborn.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RenderingHandler{

    public static ArrayList<EntityAnimator<? extends LivingEntity>> ANIMATORS = new ArrayList<>();



    @SubscribeEvent
    public void registerEntityRendersEvent(final EntityRenderersEvent.RegisterRenderers event){


        event.registerEntityRenderer(ModEntities.ACHILLOBATOR_ENTITY_TYPE.get(), );

    }


    private AnimatableModel getDefaultModelFromDinosaur(Dinosaur dino){
        return
    }


}