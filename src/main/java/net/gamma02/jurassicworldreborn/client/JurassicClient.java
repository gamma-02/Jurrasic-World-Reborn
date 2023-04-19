package net.gamma02.jurassicworldreborn.client;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.client.render.block.DisplayBlockRendererWithoutLevel;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Jurassicworldreborn.MODID)
public class JurassicClient {
    public static DisplayBlockRendererWithoutLevel displayBlockRendererWithoutLevel;



}
