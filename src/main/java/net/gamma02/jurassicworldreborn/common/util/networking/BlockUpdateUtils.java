package net.gamma02.jurassicworldreborn.common.util.networking;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import org.checkerframework.checker.units.qual.A;

public class BlockUpdateUtils {
    public static void broadcastBlockEntity(ServerLevel pLevel, BlockPos pPos) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        if (blockentity != null) {
            Packet<?> packet = blockentity.getUpdatePacket();
            if (packet != null) {
                broadcast(packet, pLevel, new AABB(pPos.subtract(new Vec3i(50, 50, 50)), pPos.offset(new Vec3i(50, 50, 50))));
            }
        }

    }

    public static void broadcast(Packet<?> pPacket, ServerLevel world, AABB bounds) {
        world.getPlayers((player) -> bounds.contains(player.getEyePosition())).forEach((p_140062_) -> {
            p_140062_.connection.send(pPacket);
        });
    }
}
