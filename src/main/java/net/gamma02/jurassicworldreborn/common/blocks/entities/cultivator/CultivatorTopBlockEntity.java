package net.gamma02.jurassicworldreborn.common.blocks.entities.cultivator;

import net.gamma02.jurassicworldreborn.common.blocks.entities.MachineBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities;
import net.gamma02.jurassicworldreborn.common.util.block.TemperatureControl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CultivatorTopBlockEntity extends MachineBlockEntity<CultivatorBlockEntity> implements IFluidTank, TemperatureControl {
    public CultivatorBlockEntity bottomEntity;
    public CultivatorTopBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CULTIVATOR_TOP_BLOCK_ENTITY_TYPE.get(), pPos, pBlockState);

    }

    @Override
    public void onLoad() {
        if(this.level != null && this.level.getBlockEntity(this.getBlockPos().below()) instanceof CultivatorBlockEntity bottom){
            bottomEntity = bottom;
        }
    }

    public void setBottomEntity(CultivatorBlockEntity bottomEntity) {
        this.bottomEntity = bottomEntity;
    }

    @Override
    public Tag getMachineData() {
        return null;
    }

    @Override
    public void readMachineData(Tag machineData) {
        if(bottomEntity != null)
            bottomEntity.readMachineData(machineData);
    }

    @Override
    public boolean canProcess(ItemStack... inputs) {
        if(bottomEntity != null)
            return bottomEntity.canProcess(inputs);
        return false;
    }

    @Override
    public @NotNull List<ItemStack> processItem(ItemStack... inputs) {
        if(bottomEntity != null)
            return bottomEntity.processItem(inputs);
        return new ArrayList<ItemStack>();
    }

    @Override
    public void setTemperature(int index, int value) {
        if(bottomEntity != null)
            bottomEntity.setTemperature(index, value);
    }

    @Override
    public int getTemperature(int index) {
        if(bottomEntity != null)
            return bottomEntity.getTemperature(index);
        return 0;
    }

    @Override
    public int getTemperatureCount() {
        if(bottomEntity != null)
            return bottomEntity.getTemperatureCount();
        return 0;
    }

    @Override
    public int[] getSlotsForFace(Direction pSide) {
        if(bottomEntity != null)
            return bottomEntity.getSlotsForFace(pSide);
        return new int[0];
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        if(bottomEntity != null)
            return bottomEntity.canPlaceItemThroughFace(pIndex, pItemStack, pDirection);
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        if(bottomEntity != null)
            return bottomEntity.canTakeItemThroughFace(pIndex, pStack, pDirection);
        return false;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        if(bottomEntity == null){
            return NonNullList.create();
        }
        return bottomEntity.getItems();
    }

    @Override
    protected void setItems(NonNullList<ItemStack> pItemStacks) {
        if(bottomEntity != null)
            bottomEntity.setItems(pItemStacks);

    }

    @Override
    protected Component getDefaultName() {
        if(bottomEntity != null)
            return bottomEntity.getDefaultName();
        return Component.translatable("container.cultivator");
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return bottomEntity.createMenu(pContainerId, pInventory);
    }

    @Override
    public int getContainerSize() {
        if(bottomEntity != null)
            return bottomEntity.getContainerSize();
        return 0;
    }

    @Override
    public @NotNull FluidStack getFluid() {
        if(bottomEntity != null)
            return bottomEntity.getFluid();
        return FluidStack.EMPTY;
    }

    @Override
    public int getFluidAmount() {
        if(bottomEntity != null)
            return bottomEntity.getFluidAmount();
        return 0;
    }

    @Override
    public int getCapacity() {
        if(bottomEntity != null)
            return bottomEntity.getCapacity();
        return 0;
    }

    @Override
    public boolean isFluidValid(FluidStack stack) {
        if(bottomEntity != null)
            return bottomEntity.isFluidValid(stack);
        return false;
    }

    @Override
    public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
        if(bottomEntity != null)
            return bottomEntity.fill(resource, action);
        return 0;
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
        if(bottomEntity != null)
            return bottomEntity.drain(maxDrain, action);
        return FluidStack.EMPTY;
    }

    @Override
    public @NotNull FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
        if(bottomEntity != null)
            return bottomEntity.drain(resource, action);
        return FluidStack.EMPTY;
    }
}
