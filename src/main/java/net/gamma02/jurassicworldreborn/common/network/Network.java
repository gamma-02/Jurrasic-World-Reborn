package net.gamma02.jurassicworldreborn.common.network;

import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

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
    static void init(){

    }

    static{
        channel.registerMessage(0, OpenContainerScreenGuiS2CPacket.class, OpenContainerScreenGuiS2CPacket::write, OpenContainerScreenGuiS2CPacket::read,
                (packet, ctx) -> /* DO STUF HERE*/System.out.println("got packet"), Optional.of( NetworkDirection.PLAY_TO_CLIENT));
    }
}
