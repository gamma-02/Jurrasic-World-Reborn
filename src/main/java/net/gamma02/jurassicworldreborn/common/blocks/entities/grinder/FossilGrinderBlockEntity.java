package net.gamma02.jurassicworldreborn.common.blocks.entities.grinder;

import com.google.common.primitives.Ints;
import net.gamma02.jurassicworldreborn.common.blocks.entities.MachineBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities;
import net.gamma02.jurassicworldreborn.common.util.api.GrindableItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class FossilGrinderBlockEntity extends MachineBlockEntity<FossilGrinderBlockEntity> implements WorldlyContainer {
    public static final int[] INPUTS = new int[] { 0, 1, 2, 3, 4, 5 };
    public static final int[] OUTPUTS = new int[] { 6, 7, 8, 9, 10, 11 };
    public static final int PROCESS_TIME = 200;

    public ContainerData GrinderData = new ContainerData() {
        @Override
        public int get(int pIndex) {
            if(pIndex == 0)
                return FossilGrinderBlockEntity.this.grindTime;
            return 0;
        }

        @Override
        public void set(int pIndex, int pValue) {
            if(pIndex == 0){
                FossilGrinderBlockEntity.this.grindTime = pValue;
            }
        }

        @Override
        public int getCount() {
            return 1;
        }
    };



    private int grindTime = 0;
    private NonNullList<ItemStack> inventory = NonNullList.withSize(12, ItemStack.EMPTY);

    public FossilGrinderBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.FOSSIL_GRINDER_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public Tag getMachineData() {
        CompoundTag data = new CompoundTag();
        data.putInt("GrindTime", this.grindTime);
        return data;
    }

    @Override
    public void readMachineData(Tag machineData) {
        if(machineData instanceof CompoundTag dataTag)
            this.grindTime = dataTag.getInt("GrindTime");
        else
            this.grindTime = 0;
    }


    protected boolean shouldGrind() {
        for (int inputIndex = 0; inputIndex < 6; inputIndex++) {
            ItemStack input = this.inventory.get(inputIndex);

            GrindableItem grindableItem = GrindableItem.getGrindableItem(input);

            if (grindableItem != null && grindableItem.isGrindable(input)) {
                for (int outputIndex = 6; outputIndex < 12; outputIndex++) {
                    if (this.inventory.get(outputIndex).isEmpty()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }


    protected void doGrindStep() {
        Random rand = new Random();

        ItemStack input = ItemStack.EMPTY;
        int index = 0;

        for (int inputIndex = 0; inputIndex < 6; inputIndex++) {
            input = this.inventory.get(inputIndex);

            if (!input.isEmpty()) {
                index = inputIndex;
                break;
            }
        }

        if (!input.isEmpty()) {
            GrindableItem grindableItem = GrindableItem.getGrindableItem(input);


            ItemStack output = grindableItem.getGroundItem(input, rand);

            int emptySlot = this.getOutputSlot(output);
            if (emptySlot != -1) {
                this.mergeStack(emptySlot, output);
                this.decreaseStackSize(index);
            }
        }
    }



    @Override
    public int @NotNull [] getSlotsForFace(Direction pSide) {//what is that godforsaken annotation good lord - gamma
        if(pSide.equals(Direction.DOWN))
            return OUTPUTS;
        else
            return INPUTS;
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, @NotNull ItemStack pItemStack, @Nullable Direction pDirection) {
        return canPlaceItem(pIndex, pItemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, @NotNull ItemStack pStack, @NotNull Direction pDirection) {
        return Ints.asList(OUTPUTS).contains(pIndex);
    }

    @Override
    public boolean canPlaceItem(int slot, @NotNull ItemStack stack) {
        if (Ints.asList(INPUTS).contains(slot)) {
            if (GrindableItem.getGrindableItem(stack) != null && GrindableItem.getGrindableItem(stack).isGrindable(stack)) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected @NotNull NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    public @NotNull ItemStack getItem(int pIndex) {
        return this.inventory.get(pIndex);
    }

    @Override
    protected void setItems(@NotNull NonNullList<ItemStack> pItemStacks) {

        this.inventory = pItemStacks;
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("container.fossil_grinder");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory) {
        return new FossilGrinderMenu(pContainerId, pInventory, this, this.GrinderData);
    }

    @Override
    public int getContainerSize() {
        return 12;
    }

    @Override
    public void tick(Level pLevel, BlockPos pPos, BlockState pState, FossilGrinderBlockEntity pBlockEntity) {
        boolean isWorking = this.grindTime > 0;

        boolean hasInputs = this.hasInputs();

        if(pLevel.isClientSide){
            return;
        }

        if(!hasInputs && isWorking){//if we are working but we don't have inputs, we want to set the progress to 0
            this.grindTime = 0;
            this.setChanged();
            return;
        }

        if(hasInputs && this.shouldGrind()){
            this.grindTime++;

            if(this.grindTime >= PROCESS_TIME){
                this.grindTime = 0;
//                int total = 0;
//                for( int i : INPUTS){
//                    var stack = this.inventory.get(i);
//
//                    if(stack.isEmpty()){
//
//                    }
//
//                }
                this.doGrindStep();
            }

        }











//        super.tick(pLevel, pPos, pState, (MachineBlockEntity) pBlockEntity);
    }


    public boolean hasInputs(){
        for(int i : INPUTS){
            if(!this.inventory.get(i).isEmpty()){
                return true;
            }
        }
        return false;
    }

    protected void mergeStack(int slot, ItemStack stack) {
        NonNullList<ItemStack> slots = this.inventory;

        ItemStack previous = slots.get(slot);
        if (previous.isEmpty()) {
            slots.set(slot, stack);
        } else if (ItemStack.isSame(previous, stack) && ItemStack.isSame(previous, stack)) {
            previous.setCount(previous.getCount() + stack.getCount());
        }
    }

    protected void decreaseStackSize(int slot) {
        NonNullList<ItemStack> slots = this.inventory;

        slots.get(slot).shrink(1);

        if (slots.get(slot).getCount() <= 0) {
            slots.set(slot, ItemStack.EMPTY);
        }
    }

    public int getOutputSlot(ItemStack output) {
        NonNullList<ItemStack> slots = this.inventory;
        for (int slot : OUTPUTS) {
            ItemStack stack = slots.get(slot);
            if (stack.isEmpty() || ((ItemStack.isSame(stack, output) && stack.getCount() + output.getCount() <= stack.getMaxStackSize()) && stack.getItem() == output.getItem() && stack.getDamageValue() == output.getDamageValue())) {
                return slot;
            }
        }
        return -1;
    }


}
