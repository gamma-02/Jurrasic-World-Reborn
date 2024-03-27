package net.gamma02.jurassicworldreborn.common.blocks.entities.Embryoncis.EmbryonicMachine;

import net.gamma02.jurassicworldreborn.common.blocks.entities.MachineBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities;
import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.gamma02.jurassicworldreborn.common.items.genetics.DNAItem;
import net.gamma02.jurassicworldreborn.common.items.genetics.PlantDNAItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class EmbryonicMachineBlockEntity extends MachineBlockEntity<EmbryonicMachineBlockEntity> {

    private static final int[] INPUTS = new int[] { 0, 1, 2 };
    private static final int[] OUTPUTS = new int[] { 3, 4, 5, 6 };
    public static final int STACK_PROCESS_TIME = 200;
    protected int processTime = 0;

    public final ContainerData EmbryonicMachineData = new ContainerData() {
        @Override
        public int get(int index) {
            if(index == 0)
                return EmbryonicMachineBlockEntity.this.processTime;

            return -1;
        }

        @Override
        public void set(int index, int value) {
            if(index == 0)
                EmbryonicMachineBlockEntity.this.processTime = value;
        }

        @Override
        public int getCount() {
            return 1;
        }
    };

    private NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(7, ItemStack.EMPTY);

    public EmbryonicMachineBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.EMBRYONIC_MACHINE_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public Tag getMachineData() {
        CompoundTag data = new CompoundTag();
        data.putInt("ProcessTime", this.processTime);
        return data;
    }

    @Override
    public void readMachineData(Tag data) {
        CompoundTag machineData = (CompoundTag) data;

        this.processTime = machineData.getInt("ProcessTime");
        
    }

    @Override
    public int[] getSlotsForFace(Direction pSide) {
        return switch(pSide){
            case DOWN -> OUTPUTS;
            case UP -> new int[]{2};
            case NORTH, WEST, EAST, SOUTH -> INPUTS;
        };
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, @NotNull ItemStack pItemStack, @Nullable Direction pDirection) {
        return Arrays.stream(this.getSlotsForFace(pDirection)).anyMatch((index) -> index == pIndex );
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, @NotNull ItemStack pStack, @NotNull Direction pDirection) {
        return Arrays.stream(this.getSlotsForFace(pDirection)).anyMatch((index) -> index == pIndex );
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> pItemStacks) {
        this.inventory = pItemStacks;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.embryonic_machine");
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new EmbryonicMachineMenu(pContainerId, this, this.EmbryonicMachineData, pInventory);
    }

    @Override
    public int getContainerSize() {
        return this.inventory.size();
    }
    
    public boolean canProcess(ItemStack... stacks){
        ItemStack dna = this.inventory.get(0);
        ItemStack petridish = this.inventory.get(1);
        ItemStack syringe = this.inventory.get(2);

        if (syringe.getItem() == ModItems.EMPTY_SYRINGE.get()) {
            ItemStack output = null;

            if (petridish.getItem() == ModItems.PETRI_DISH.get() && dna.getItem() instanceof DNAItem dnaItem) {
                output = new ItemStack(ModItems.SYRINGES.get(dnaItem.dinosaur).get(), 1);

                output.setTag(dna.getTag());
            } else if (petridish.getItem() == ModItems.PLANT_CELLS_PETRI_DISH.get() && dna.getItem() instanceof PlantDNAItem) {
                output = new ItemStack(ModItems.PLANT_CALLUS.get());
                output.setTag(dna.getTag());
            }

            return output != null && this.hasOutputSlot(output);
        }

        return false;
        
    }

    public boolean hasOutputSlot(ItemStack output) {
        return this.getOutputSlot(output) != -1;
    }

    public int getOutputSlot(ItemStack output) {
        NonNullList<ItemStack> slots = this.inventory;
        for (int slot : OUTPUTS) {
            ItemStack stack = slots.get(slot);
            if (stack.isEmpty() || ((ItemStack.isSameItemSameTags(stack, output) && stack.getCount() + output.getCount() <= stack.getMaxStackSize()) && stack.getItem() == output.getItem())) {
                return slot;
            }
        }
        return -1;
    }

    @NotNull
    public List<ItemStack> processItem(ItemStack... inputs) {
        if (this.canProcess()) {
            ItemStack dna = this.inventory.get(0);
            ItemStack output = null;

            if (this.inventory.get(0).getItem() instanceof DNAItem dinoDna && this.inventory.get(1).getItem() == ModItems.PETRI_DISH.get()) {
                output = new ItemStack(ModItems.SYRINGES.get(dinoDna.dinosaur).get(), 1);

            } else if (this.inventory.get(0).getItem() instanceof PlantDNAItem && this.inventory.get(1).getItem() == ModItems.PLANT_CELLS_PETRI_DISH.get()) {
                output = new ItemStack(ModItems.PLANT_CALLUS.get(), 1);

            }


            output.setTag(dna.getTag());

            int emptySlot = this.getOutputSlot(output);

            if (emptySlot != -1) {
                this.mergeStack(emptySlot, output);

                this.decreaseStackSize(0);
                this.decreaseStackSize(1);
                this.decreaseStackSize(2);
            }
        }
        return List.of(ItemStack.EMPTY);
    }

    protected void mergeStack(int slot, ItemStack stack) {
        NonNullList<ItemStack> slots = this.inventory;

        ItemStack previous = slots.get(slot);
        if (previous.isEmpty()) {
            slots.set(slot, stack);
        } else if ( ItemStack.isSameItemSameTags(previous, stack)) {
            previous.setCount(previous.getCount() + stack.getCount());
        }
    }


    @Override
    public void tick(Level pLevel, BlockPos pPos, BlockState pState, EmbryonicMachineBlockEntity pBlockEntity) {
        if(pLevel.isClientSide)
            return;


        if(this.processTime >= STACK_PROCESS_TIME){

            this.processItem();

            this.processTime = 0;

        }

        if(!this.canProcess()){
            this.processTime = 0;
        }else{
            this.processTime++;
        }




    }
}
