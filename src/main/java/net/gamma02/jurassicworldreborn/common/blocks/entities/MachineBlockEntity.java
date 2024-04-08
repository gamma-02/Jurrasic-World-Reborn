package net.gamma02.jurassicworldreborn.common.blocks.entities;

import net.gamma02.jurassicworldreborn.common.util.networking.BlockUpdateUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


/**
 * Base class for all(read: most) of our block entities.
 * @param <T> our block entity, can't remember why I did this but whatever it works
 */
public abstract class MachineBlockEntity<T extends MachineBlockEntity<T>> extends RandomizableContainerBlockEntity implements WorldlyContainer, BlockEntityTicker<T> {
    protected MachineBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    /**
     * This gets saved data OTHER than the machine's inventory, I.E. process time or a list of other data relating to each slot
     * @return A NBT Tag holding your data. It is strongly recommended that this be a {@link ListTag} or {@link CompoundTag}.
     * @see MachineBlockEntity#readMachineData(Tag)
     */
    public abstract Tag getMachineData();

    /**
     * This is the method that handles loading in saved data.
     * @param machineData Saved NBT data OTHER than the machine's inventory.
     * @see MachineBlockEntity#getMachineData()
     */
    public abstract void readMachineData(Tag machineData);


    /**
     * This handles saving machine data.
     * @param pTag Input tag provided by Minecraft
     */
    @Override
    public void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);


        CompoundTag machineData = new CompoundTag();
        Tag tag = this.getMachineData();
        if(tag != null)
            machineData.put("Data", tag);

        ContainerHelper.saveAllItems(machineData, this.getItems());


        pTag.put("MachineData", machineData);

    }

    @Override
    public void tick(Level world, BlockPos pPos, BlockState pState, T pBlockEntity) {
        if(!world.isClientSide && world instanceof ServerLevel svlvl){
            BlockUpdateUtils.broadcastBlockEntity(svlvl, this.getBlockPos());
        }
    }

    /**
     * This handles loading machine data.
     * @param pTag Input tag provided by Minecraft.
     */

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        CompoundTag data = pTag.getCompound("MachineData");

        if (data.contains("Items")){
//            ListTag itemTag = pTag.getList("Items", 10);
            NonNullList<ItemStack> containerInventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
            ContainerHelper.loadAllItems(data, containerInventory);
            this.setItems(containerInventory);

        }

        if(data.contains("Data"))
            this.readMachineData(data.get("Data"));




    }


    //that javadoc took a hot second... care about you future devs :DDDDDD - gamma_02
    /**
     * This method should return weather or not the machine block entity should process the inputs given in the {@code ItemStack... inputs} param.
     * The way I've thought about this is that this method should be given an ordered list of item stacks with all inputs in an order
     * the coder devises. <br><br>
     * For example input #1 is a DNA syringe, input #2 is an egg, this method should return {@code true} if the machine is
     * an embryo calcification machine, the dino referenced from the DNA syringe lays an egg, AND input #2 is an egg.
     * @param inputs A given list of inputs from a machine. This should be for an individual input/alt. input set in a multi-processed machine.
     * @return true IF AND ONLY IF the machine is supposed to produce an output for the given list of inputs.
     */
    public abstract boolean canProcess(ItemStack... inputs);


    /**
     * This method should return the result of processing the given inputs, in itemstack form. However, handling placing these
     * items should be handled by the tick function. I also suggest handing off decreasing the item counts in the container
     * to the tick function, but that's less important: Do What Works.
     * @param inputs A given list of inputs from a machine. This should be for an individual input/alt. input set in a multi-processed machine.
     * @return Unordered list of output items to be handled by the tick function.
     */
    @NotNull
    public abstract List<ItemStack> processItem(ItemStack... inputs);

    /**
     * Template method so you don't have to get all the inputs again and again. Use this please please please please im begging you
     * @return the ordered list of inputs that you use in the {@link MachineBlockEntity#canProcess(ItemStack...)} and {@link MachineBlockEntity#processItem(ItemStack...)}
     * @see MachineBlockEntity#canProcess(ItemStack...)
     * @see MachineBlockEntity#processItem(ItemStack...)
     */
    public ItemStack[] collectInputs(int... flags){
        return null;
    }



    protected void mergeStack(int slot, ItemStack stack) {
        NonNullList<ItemStack> slots = this.getItems();

        ItemStack previous = slots.get(slot);
        if (previous.isEmpty()) {
            slots.set(slot, stack);
        } else if ( ItemStack.isSameItemSameTags(previous, stack) ) {
            previous.setCount(previous.getCount() + stack.getCount());
        }
    }

    protected void decreaseStackSize(int index, int amount) {
        var stack = this.getItem(index);
        stack.shrink(amount);
        this.setItem(index, stack);
    }

    protected void decreaseStackSize(int index){
        this.decreaseStackSize(index, 1);
    }

//    @Override
//    public CompoundTag getUpdateTag() {
//        return super.getUpdateTag();
//    }


    /**
     * This method impliments a Packet to sync our BlockEntity's inventory with the client! this replaces the system I have in Network
     *
     * @return The packet that syncs our inevntory with the client's
     */
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {

        return ClientboundBlockEntityDataPacket.create(this, (entity) -> {
            CompoundTag entityData = new CompoundTag();
            entity.saveAdditional(entityData);
            return entityData;
        });
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        CompoundTag data = pkt.getTag();

        this.load(data);
    }

    /**
     * Might want to figure this out right now, just make it private final and forget about it lmao
     */
    private final Packet<ClientGamePacketListener> SYNC_PACKET = new Packet<ClientGamePacketListener>() {
        @Override
        public void write(@NotNull FriendlyByteBuf pBuffer) {
            //Write our
            for(ItemStack s : MachineBlockEntity.this.getItems()){
                pBuffer.writeItemStack(s, false);
            }
        }

        @Override
        public void handle(@NotNull ClientGamePacketListener pHandler) {
//            NonNullList<ItemStack> recievedInventory = NonNullList.withSize(MachineBlockEntity.this.getItems().size(), ItemStack.EMPTY);
//            for (int i = 0; i < recievedInventory.size(); i++) {//assume they're the same size
//                ItemStack stack = pHandler.
//            }
        }
    };
}
