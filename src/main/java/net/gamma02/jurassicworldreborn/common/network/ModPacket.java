package net.gamma02.jurassicworldreborn.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public interface ModPacket<T extends ModPacket<T>> {


    void handleOnRenderThread(T packet, Supplier<NetworkEvent.Context> context);

    void write(FriendlyByteBuf buffer);


}
