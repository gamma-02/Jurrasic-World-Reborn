package net.gamma02.jurassicworldreborn.common.util.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.NonNullList;

public interface Syncable
{

    NonNullList getSyncFields(NonNullList fields);

    void packetDataHandler(ByteBuf fields);

}