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

        ContainerHelper.saveAllItems(pTag, this.getItems());


        pTag.put("MachineData", this.getMachineData());

    }

    /**
     * This handles loading machine data.
     * @param pTag Input tag provided my Minecraft.
     */

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);


        if (pTag.contains("Items")){
            ListTag itemTag = pTag.getList("Items", 10);
            NonNullList<ItemStack> containerInventory = NonNullList.withSize(itemTag.size(), ItemStack.EMPTY);
            ContainerHelper.loadAllItems(pTag, containerInventory);
        }

        Tag data = pTag.get("MachineData");
        this.readMachineData(data);




    }

    @Override
    public void tick(Level pLevel, BlockPos pPos, BlockState pState, T pBlockEntity) {

    }

}
