package net.gamma02.jurassicworldreborn.common.blocks.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

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
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);


        CompoundTag machineData = new CompoundTag();
        machineData.put("Data", this.getMachineData());

        ContainerHelper.saveAllItems(machineData, this.getItems());


        pTag.put("MachineData", machineData);

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


        this.readMachineData(data.get("Data"));




    }

    @Override
    public void tick(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, @NotNull MachineBlockEntity pBlockEntity) {

    }

    protected void decreaseStackSize(int index, int amount) {
        var stack = this.getItem(index);
        stack.shrink(amount);
        this.setItem(index, stack);
    }

    protected void decreaseStackSize(int index){
        this.decreaseStackSize(index, 1);
    }
}
