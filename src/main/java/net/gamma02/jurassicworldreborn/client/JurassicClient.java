package net.gamma02.jurassicworldreborn.client;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.client.render.block.DisplayBlockRendererWithoutLevel;
import net.gamma02.jurassicworldreborn.common.network.Network;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;

@Mod.EventBusSubscriber(modid = Jurassicworldreborn.MODID)
public class JurassicClient {
    public static DisplayBlockRendererWithoutLevel displayBlockRendererWithoutLevel;

    @SubscribeEvent
    public void clientTickEvent(TickEvent.ClientTickEvent evt){
        Level level = Minecraft.getInstance().level;

        HashMap<BlockPos, Int2ObjectArrayMap<ItemStack>> newSlotMap = new HashMap<>();

        Network.slotMap.forEach(((blockPos, map) -> {
            if(level != null) {
                if (level.getBlockEntity(blockPos) != null) {
                    newSlotMap.put(blockPos, map);
                }
            }
        }));

        Network.slotMap = newSlotMap;
    }



}
