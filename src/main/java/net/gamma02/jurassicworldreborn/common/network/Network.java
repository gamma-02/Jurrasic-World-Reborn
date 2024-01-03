package net.gamma02.jurassicworldreborn.common.network;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.common.blocks.entities.cleaner.CleanerBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.RunningOnDifferentThreadException;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.*;

import static net.gamma02.jurassicworldreborn.Jurassicworldreborn.resource;

public class Network {
    private Network(){
        this.channel.registerMessage(id++, OpenContainerScreenGuiS2CPacket.class,
                OpenContainerScreenGuiS2CPacket::write,
                OpenContainerScreenGuiS2CPacket::read,
                OpenContainerScreenGuiS2CPacket::handle,
                Optional.of( NetworkDirection.PLAY_TO_CLIENT));
        this.channel.registerMessage(id++, FetchSlotContentsServerboundPacket.class,
                FetchSlotContentsServerboundPacket::write,
                FetchSlotContentsServerboundPacket::read,
                FetchSlotContentsServerboundPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));
        this.channel.registerMessage(id++, FetchSlotContentsClientboundPacket.class,
                FetchSlotContentsClientboundPacket::write,
                FetchSlotContentsClientboundPacket::read,
                FetchSlotContentsClientboundPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        this.channel.registerMessage(id++, SwitchHybridizerCombinatorMode.class,
                SwitchHybridizerCombinatorMode::write,
                SwitchHybridizerCombinatorMode::read,
                SwitchHybridizerCombinatorMode::handle);
    }

    public static Network INSTANCE;
    public static final String version = "1";
    private final SimpleChannel channel = NetworkRegistry.ChannelBuilder
            .named(resource("main"))
            .serverAcceptedVersions((v) -> version.equals(v) || NetworkRegistry.ABSENT.equals(v) || NetworkRegistry.ACCEPTVANILLA.equals(v))
            .clientAcceptedVersions((v) -> version.equals(v) || NetworkRegistry.ABSENT.equals(v) || NetworkRegistry.ACCEPTVANILLA.equals(v))
            .networkProtocolVersion(() -> "1")
            .simpleChannel();
//    public static final SimpleChannel forge_channel = NetworkRegistry.newSimpleChannel(
//            resource("main_1"),
//            () -> version,
//            version::equals,
//            version::equals
//    );
    public static void init(){
        INSTANCE = new Network();
    }

    public static int id = 0;

    static{

    }

//    @OnlyIn(Dist.DEDICATED_SERVER)
    public <B extends BlockEntity> void sendOpenPacket(BlockPos entityPos, int containerID, Component title, MenuType<?> type, BlockEntityType<B> intendedType, ServerPlayer target){
        channel.sendTo(new OpenContainerScreenGuiS2CPacket<B>(entityPos, containerID, type, title, intendedType), target.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
    }

    public <T extends ModPacket<T>>void ensureRunningOnSameThread(T packet, Connection connection, NetworkEvent.Context ctx, BlockableEventLoop<?> pExecutor) throws RunningOnDifferentThreadException {
        if (!pExecutor.isSameThread()) {
            pExecutor.executeIfPossible(() -> {
                if (connection.isConnected()) {
                    try {
                        packet.handleOnRenderThread(packet, () -> ctx);
                    } catch (Exception exception) {
//                        if (.shouldPropagateHandlingExceptions()) {
//                            throw exception;
//                        }

                        Jurassicworldreborn.getLogger().error("Failed to handle packet {}, suppressing error", packet, exception);
                    }
                } else {
                    Jurassicworldreborn.getLogger().debug("Ignoring packet due to disconnection: {}", (Object)packet);
                }

            });
            throw RunningOnDifferentThreadException.RUNNING_ON_DIFFERENT_THREAD;
        }
    }

    public OptionalInt openMenu(@javax.annotation.Nullable MenuProvider pMenu, ServerPlayer player, BlockPos pPos, CleanerBlockEntity e) {
        if (pMenu == null) {
            return OptionalInt.empty();
        } else {
            if (player.containerMenu != player.inventoryMenu) {
                player.closeContainer();
            }

            player.nextContainerCounter();
            AbstractContainerMenu abstractcontainermenu = pMenu.createMenu(player.containerCounter, player.getInventory(), player);
            if (abstractcontainermenu == null) {
                if (player.isSpectator()) {
                    player.displayClientMessage(Component.translatable("container.spectatorCantOpen").withStyle(ChatFormatting.RED), true);
                }

                return OptionalInt.empty();
            } else {
//                player.connection.send(new ClientboundOpenScreenPacket(abstractcontainermenu.containerId, abstractcontainermenu.getType(), pMenu.getDisplayName()));
                this.sendOpenPacket(pPos, player.containerCounter, e.getDisplayName(), abstractcontainermenu.getType(), e.getType(), player);

                player.initMenu(abstractcontainermenu);
                player.containerMenu = abstractcontainermenu;
                net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.player.PlayerContainerEvent.Open(player, player.containerMenu));
                return OptionalInt.of(player.containerCounter);
            }
        }
    }



//    public void registerMessage(ModPacket<? extends ModPacket> packet, ){
//        this.channel.registerMessage(id++, packet.getClass(),
//                OpenContainerScreenGuiS2CPacket::write,
//                OpenContainerScreenGuiS2CPacket::read,
//                OpenContainerScreenGuiS2CPacket::handle,
//                Optional.of( NetworkDirection.PLAY_TO_CLIENT));
//    }

    @OnlyIn(Dist.CLIENT)
    public static <B extends BlockEntity> void sendSlotRequest(B entity, int slotRequestIndex){

        if(!pendingSlots.contains(entity.getBlockPos())) {
            INSTANCE.channel.sendToServer(new FetchSlotContentsServerboundPacket(entity.getBlockPos(), slotRequestIndex, entity.getLevel().dimension(), Minecraft.getInstance().player.getUUID()));
//            pendingSlots.add(entity.getBlockPos());
        }
    }

    public void sendSlotRequestRepsonse(UUID player, ServerLevel world, int slotIndex, BlockPos pos, ItemStack contents){

        this.channel.sendTo(new FetchSlotContentsClientboundPacket(world, slotIndex, pos, contents), ((ServerPlayer)world.getPlayerByUUID(player)).connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);

//        this.channel.send(, );
    }

    public void sendSlotRequestRepsonse(UUID player, ServerLevel world, int slotIndex, BlockPos pos){

        this.channel.sendTo(new FetchSlotContentsClientboundPacket(world, slotIndex, pos, null), ((ServerPlayer)world.getPlayerByUUID(player)).connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);

//        this.channel.send(, );
    }




    public static ArrayList<BlockPos> pendingSlots = new ArrayList<>();

    public static ArrayList<BlockEntity> ENTITIES = new ArrayList<>();
    public static HashMap<BlockPos, Int2ObjectArrayMap<ItemStack>> slotMap = new HashMap<>();

    public static void setSlotContents(BlockPos entity, int slot, ItemStack contents){
        boolean yes = slotMap.containsKey(entity);
        if(contents != null) {
            if (yes) {
//                System.out.println(contents.toString());
                slotMap.get(entity).put(slot, contents);
            } else if (!yes) {
                var tempMap = new Int2ObjectArrayMap<ItemStack>();
                tempMap.put(slot, contents);
                slotMap.put(entity, tempMap);
            }
        }
    }

    public static Container getMyContainerAtPos(BlockPos pos){
        for(BlockEntity e : ENTITIES){
            if(e.getLevel() == null){
                ENTITIES.remove(e);
                continue;
            }
            if(e.getBlockPos().equals(pos)){
                return (Container) e;
            }
        }
        return null;
    }
    public static ItemStack getSlotContents(BlockPos pos, int slotIndex){
        if(slotMap.containsKey(pos)){
            return slotMap.get(pos).get(slotIndex);
        }else{

            return ItemStack.EMPTY;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void switchHybridizerCombinerMode(boolean mode, BlockPos pos, ResourceKey<Level> dimension){
        INSTANCE.channel.sendToServer(new SwitchHybridizerCombinatorMode(mode, pos, dimension));
    }


}
