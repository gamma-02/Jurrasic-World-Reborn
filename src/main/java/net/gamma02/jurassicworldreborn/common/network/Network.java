package net.gamma02.jurassicworldreborn.common.network;

import net.gamma02.jurassicworldreborn.client.screens.ModScreens;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Optional;

import static net.gamma02.jurassicworldreborn.Jurassicworldreborn.resource;

public class Network {
    private static final String version = "1";
    private static final SimpleChannel channel = NetworkRegistry.ChannelBuilder
            .named(resource("main"))
            .serverAcceptedVersions((v) -> version.equals(v) || NetworkRegistry.ABSENT.equals(v) || NetworkRegistry.ACCEPTVANILLA.equals(v))
            .clientAcceptedVersions((v) -> version.equals(v) || NetworkRegistry.ABSENT.equals(v) || NetworkRegistry.ACCEPTVANILLA.equals(v))
            .networkProtocolVersion(() -> "1")
            .simpleChannel();
    public static void init(){

    }

    static{
        channel.registerMessage(0, OpenContainerScreenGuiS2CPacket.class, OpenContainerScreenGuiS2CPacket::write, OpenContainerScreenGuiS2CPacket::read,
                OpenContainerScreenGuiS2CPacket::handle,
                Optional.of( NetworkDirection.PLAY_TO_CLIENT));
    }

    public static <B extends BlockEntity> void sendOpenPacket(BlockPos entityPos, int containerID, Component title, MenuType<?> type, BlockEntityType<B> intendedType, ServerPlayer target){
        channel.sendTo(new OpenContainerScreenGuiS2CPacket<B>(entityPos, containerID, type, title, intendedType), target.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
    }


}
