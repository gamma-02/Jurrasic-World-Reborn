package net.gamma02.jurassicworldreborn.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class FetchSlotContentsClientboundPacket implements ModPacket<FetchSlotContentsClientboundPacket> {

    @Nullable
    private Level world;
    private ItemStack contents;
    private int slotIndex;

    private BlockPos blockEntityPos;

    public FetchSlotContentsClientboundPacket(Level world, int slotIndex, BlockPos entityPos, ItemStack Contents){
        this.world = world;
        this.slotIndex = slotIndex;
        this.blockEntityPos = entityPos;
        if(Contents == null && this.world != null && world.getBlockEntity(entityPos) instanceof Container c){
            this.contents = c.getItem(slotIndex);
        }
    }
    public FetchSlotContentsClientboundPacket(int slotIndex, BlockPos entityPos, ItemStack Contents) {
        this(null, slotIndex, entityPos, Contents);
    }



        @Override
    public void handleOnRenderThread(FetchSlotContentsClientboundPacket packet, Supplier<NetworkEvent.Context> context) {


//        ItemStack contents = c.getItem(packet.slotIndex);





    }

    public void write(FriendlyByteBuf pBuffer) {
        ItemStack itemInSlot = ItemStack.EMPTY;
        BlockEntity entity = world.getBlockEntity(this.blockEntityPos);
        if(entity == null){
            entity = world.getServer().getLevel(Level.OVERWORLD).getBlockEntity(this.blockEntityPos);
        }

        if(entity == null){
            itemInSlot = (Network.getMyContainerAtPos(blockEntityPos)).getItem(slotIndex);
        }

        if((this.contents == null || this.contents == ItemStack.EMPTY) && this.world != null && entity != null){
            this.contents = ((Container) entity).getItem(this.slotIndex);
        }
        pBuffer.writeItemStack(itemInSlot, false);
        pBuffer.writeInt(this.slotIndex);
        pBuffer.writeBlockPos(this.blockEntityPos);



    }

//    public void handle(FetchSlotContentsClientboundPacket packet, Supplier<NetworkEvent.Context> context) {
//
//        Network.INSTANCE.ensureRunningOnSameThread(packet, context.get().getNetworkManager(), context.get(), Minecraft.getInstance());
//
//
//    }
    public static void handle(FetchSlotContentsClientboundPacket packet, Supplier<NetworkEvent.Context> ctx) {
        Network.setSlotContents(packet.blockEntityPos, packet.slotIndex, packet.contents);
//        Network.pendingSlots.remove(packet.blockEntityPos);
        Network.INSTANCE.ensureRunningOnSameThread(packet, ctx.get().getNetworkManager(), ctx.get(), Minecraft.getInstance());
    }

    public static FetchSlotContentsClientboundPacket read(FriendlyByteBuf pBuffer) {
        ItemStack contents = pBuffer.readItem();
        int slotIndex = pBuffer.readInt();
        BlockPos pos = pBuffer.readBlockPos();

        Network.setSlotContents(pos, slotIndex, contents);

        return new FetchSlotContentsClientboundPacket(slotIndex, pos, contents);
    }

}
