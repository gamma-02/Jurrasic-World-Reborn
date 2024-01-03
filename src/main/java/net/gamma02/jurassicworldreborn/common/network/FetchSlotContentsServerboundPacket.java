package net.gamma02.jurassicworldreborn.common.network;

import net.minecraft.core.BlockPos;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

public class FetchSlotContentsServerboundPacket {

    private BlockPos pos;

    private int slotIndex;

    private ResourceKey<Level> dimension;

    private UUID player;

    public FetchSlotContentsServerboundPacket(BlockPos pos, int slotIndex, ResourceKey<Level> dim, UUID player){
        this.dimension = dim;
        this.slotIndex = slotIndex;
        this.pos = pos;
        this.player = player;
    }


    public static void handle(FetchSlotContentsServerboundPacket packet, Supplier<NetworkEvent.Context> ctx){
        ServerLevel world = Objects.requireNonNull(ctx.get().getSender()).getLevel();
        ServerLevel posWorld = world.getServer().getLevel(packet.dimension);
        Network.INSTANCE.sendSlotRequestRepsonse(packet.player, posWorld, packet.slotIndex, packet.pos);
    }

    public void write(FriendlyByteBuf buf){
        buf.writeResourceKey(dimension);
        buf.writeInt(slotIndex);
        buf.writeBlockPos(pos);
        buf.writeUUID(this.player);
    }

    public static FetchSlotContentsServerboundPacket read(FriendlyByteBuf buf){
        var level = buf.readResourceKey(DefaultedRegistry.DIMENSION_REGISTRY);
        var index = buf.readInt();
        var pos = buf.readBlockPos();
        var player = buf.readUUID();
        return new FetchSlotContentsServerboundPacket(pos, index, level, player);
    }



}
