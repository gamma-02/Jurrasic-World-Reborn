package net.gamma02.jurassicworldreborn.common.blocks.entities.Embryoncis.EmbryoCalcificationMachine;

import com.google.common.primitives.Ints;
import net.gamma02.jurassicworldreborn.common.blocks.entities.MachineBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurHandler;
import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.gamma02.jurassicworldreborn.common.items.genetics.DinosaurEggItem;
import net.gamma02.jurassicworldreborn.common.items.genetics.SyringeItem;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EmbryoCalcificationMachineBlockEntity extends MachineBlockEntity<EmbryoCalcificationMachineBlockEntity> {
    public static final int STACK_PROCESS_TIME = 200;
    private static final int[] INPUTS = new int[]{0, 1};
    private static final int[] OUTPUTS = new int[]{2};
    private int processTime = 0;

    private NonNullList<ItemStack> inventory = NonNullList.withSize(3, ItemStack.EMPTY);

    private final ContainerData EmbryoCalcificationMachineData = new ContainerData() {
        @Override
        public int get(int pIndex) {
            if (pIndex == 0) {
                return EmbryoCalcificationMachineBlockEntity.this.processTime;
            }
            return 0;
        }

        @Override
        public void set(int pIndex, int pValue) {
            if (pIndex == 0) {
                EmbryoCalcificationMachineBlockEntity.this.processTime = pValue;
            }
        }

        @Override
        public int getCount() {
            return 1;
        }
    };

    public EmbryoCalcificationMachineBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.EMBRYO_CALCIFICATION_MACHINE_BLOCK_ENTITY_TYPE.get(), pPos, pBlockState);
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
    public int @NotNull [] getSlotsForFace(Direction pSide) {
        return switch (pSide) {
            case UP -> INPUTS;
            case DOWN -> OUTPUTS;
            case NORTH, SOUTH, EAST, WEST -> new int[]{1};
        };
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, @NotNull ItemStack pItemStack, @Nullable Direction pDirection) {
        int ind = Ints.asList(this.getSlotsForFace(pDirection)).indexOf(pIndex);
        return ind != -1 && this.canPlaceItem(ind, pItemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, @NotNull ItemStack pStack, @NotNull Direction pDirection) {
        return Ints.asList(this.getSlotsForFace(pDirection)).contains(pIndex);
    }

    @Override
    public boolean canPlaceItem(int slotID, @NotNull ItemStack itemstack) {
        if (Ints.asList(INPUTS).contains(slotID)) {
            return slotID == 0 && itemstack.getItem() instanceof SyringeItem syringe && syringe.getDinosaur(itemstack).getBirthType() == Dinosaur.BirthType.EGG_LAYING
                    || slotID == 1 && itemstack.getItem() == Items.EGG;
        }

        return false;
    }

    @Override
    protected @NotNull NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void setItems(@NotNull NonNullList<ItemStack> pItemStacks) {
        inventory = pItemStacks;
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("container.embryo_calcification_machine");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory) {
        return new EmbryoCalcificationMachineMenu(pContainerId, this, this.EmbryoCalcificationMachineData, pInventory);
    }

    @Override
    public int getContainerSize() {
        return this.inventory.size();
    }

    public boolean canProcess(ItemStack... stacks) {
        ItemStack input = stacks[0];
        ItemStack egg = stacks[1];
        ItemStack outputStack = stacks[2];

        if (!input.isEmpty() && input.getItem() instanceof SyringeItem syringe && !egg.isEmpty() && egg.getItem() == Items.EGG) {
            Dinosaur dino = syringe.getDinosaur(input);

            if (dino.getBirthType() == Dinosaur.BirthType.EGG_LAYING && (!dino.isMarineCreature() || dino == DinosaurHandler.BEELZEBUFO)) {

                if(!outputStack.isEmpty()) {
                    //assume that the output is a DinosaurEggItem
                    DinosaurEggItem outputItem = (DinosaurEggItem) outputStack.getItem();

                    return outputItem.equals(ModItems.dinoEggs.get(dino).get());
                }else{
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public @NotNull List<ItemStack> processItem(ItemStack... stacks) {
        ItemStack input = stacks[0];
        ItemStack egg = stacks[1];
        ItemStack outputStack = stacks[2];
        if (!input.isEmpty() && input.getItem() instanceof SyringeItem syringe) {
//            ItemStack output = new ItemStack(ItemHandler.EGG, 1, this.slots.get(0).getItemDamage());
            ItemStack output = ModItems.dinoEggs.get(syringe.getDinosaur(input)).get().getDefaultInstance();
            output.setTag(input.getTag());
            return List.of(output);

        }
        return List.of(ItemStack.EMPTY);
    }

    @Override
    public ItemStack[] collectInputs(int... flags) {
        return new ItemStack[]{this.inventory.get(0), // syringe stack
                this.inventory.get(1), // egg stack
                this.inventory.get(2)}; // output stack
    }

    @Override
    public void tick(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, @NotNull EmbryoCalcificationMachineBlockEntity pBlockEntity) {
        if (pLevel.isClientSide)
            return;

        ItemStack[] inputs = this.collectInputs();

        if (this.processTime >= STACK_PROCESS_TIME && this.canProcess(inputs)) {

            ItemStack output = this.processItem(inputs).get(0);
            this.mergeStack(2, output);

            this.processTime = 0;

            inputs = this.collectInputs();

        }

        if (this.canProcess(inputs)) {
            this.processTime++;
        } else {
            this.processTime = 0;
        }
    }
}
