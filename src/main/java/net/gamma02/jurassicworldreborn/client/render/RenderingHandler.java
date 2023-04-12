package net.gamma02.jurassicworldreborn.client.render;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Jurassicworldreborn.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RenderingHandler{



    @SubscribeEvent
    public void registerEntityRendersEvent(final EntityRenderersEvent.RegisterRenderers event){

    }




}