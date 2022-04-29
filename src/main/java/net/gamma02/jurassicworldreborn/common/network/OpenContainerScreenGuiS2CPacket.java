package net.gamma02.jurassicworldreborn.common.network;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.ForgeRegistries;

public class OpenContainerScreenGuiS2CPacket<B extends BlockEntity> {

    public BlockPos entityPos;
     public int containerID;

     public ResourceLocation menuId;

    public Component title;

    public ResourceLocation indendedTypeLocation;

    public OpenContainerScreenGuiS2CPacket(int x, int y, int z, int pContainerId, MenuType<?> pMenuType, Component pTitle, BlockEntityType<B> intendedType){

        entityPos = new BlockPos(x, y, z);
        containerID = pContainerId;
        menuId = ForgeRegistries.CONTAINERS.getKey(pMenuType);
        title = pTitle;
        indendedTypeLocation = ForgeRegistries.BLOCK_ENTITIES.getKey(intendedType);

    }

    public OpenContainerScreenGuiS2CPacket(BlockPos pos, int pContainerId, MenuType<?> pMenuType, Component pTitle, BlockEntityType<B> intendedType){

        entityPos = pos;
        containerID = pContainerId;
        menuId = ForgeRegistries.CONTAINERS.getKey(pMenuType);
        title = pTitle;
        indendedTypeLocation = ForgeRegistries.BLOCK_ENTITIES.getKey(intendedType);

    }


    public void write(FriendlyByteBuf pBuffer) {
        pBuffer.writeBlockPos(entityPos).writeVarInt(containerID).writeResourceLocation(menuId).writeComponent(title).writeResourceLocation(indendedTypeLocation);
    }

    public static <T extends BlockEntity> OpenContainerScreenGuiS2CPacket<T> read(FriendlyByteBuf pBuffer) {
        BlockEntityType<T> type = (BlockEntityType<T>) ForgeRegistries.BLOCK_ENTITIES.getValue(pBuffer.readResourceLocation());
        Component title = pBuffer.readComponent();
        MenuType<?> menu = ForgeRegistries.CONTAINERS.getValue(pBuffer.readResourceLocation());
        int id = pBuffer.readVarInt();
        BlockPos pos = pBuffer.readBlockPos();
        return new OpenContainerScreenGuiS2CPacket<T>(pos, id, menu, title, type);
    }
}
