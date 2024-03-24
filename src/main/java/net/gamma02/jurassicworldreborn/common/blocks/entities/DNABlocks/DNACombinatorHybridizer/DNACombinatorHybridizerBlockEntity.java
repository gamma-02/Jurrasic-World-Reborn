package net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNACombinatorHybridizer;

import com.google.common.primitives.Ints;
import net.gamma02.jurassicworldreborn.common.blocks.entities.MachineBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Hybrid;
import net.gamma02.jurassicworldreborn.common.genetics.DinoDNA;
import net.gamma02.jurassicworldreborn.common.genetics.GeneticsHelper;
import net.gamma02.jurassicworldreborn.common.genetics.PlantDNA;
import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.gamma02.jurassicworldreborn.common.network.Network;
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
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DNACombinatorHybridizerBlockEntity extends MachineBlockEntity<DNACombinatorHybridizerBlockEntity> implements BlockEntityTicker<DNACombinatorHybridizerBlockEntity>, WorldlyContainer {
    public static final int[] HYBRIDIZER_INPUTS = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
    public static final int[] COMBINATOR_INPUTS = new int[]{8, 9};
    public static final int[] HYBRIDIZER_OUTPUTS = new int[]{10};
    public static final int[] COMBINATOR_OUTPUTS = new int[]{11};

    public int processTime;

    private NonNullList<ItemStack> inventory = NonNullList.of(ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY);


    private ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            if (pIndex >= 2) {
                if (pIndex == 2) {
                    return DNACombinatorHybridizerBlockEntity.this.getMode() ? 1 : 0;
                } else if (pIndex == 3) {
                    return DNACombinatorHybridizerBlockEntity.this.getBlockPos().getX();
                } else if (pIndex == 4) {
                    return DNACombinatorHybridizerBlockEntity.this.getBlockPos().getY();
                } else if (pIndex == 5) {
                    return DNACombinatorHybridizerBlockEntity.this.getBlockPos().getZ();
                }
                return 0;
            } else if (pIndex == 0) {
                return DNACombinatorHybridizerBlockEntity.this.processTime;
            } else if (pIndex == 1) {
                return DNACombinatorHybridizerBlockEntity.this.getTotalProcessTime();
            }
            return 0;
        }

        @Override
        public void set(int pIndex, int pValue) {
            if (pIndex >= 2) {
                if (pIndex == 2 && pValue < 2 && pValue > -1) {
                    DNACombinatorHybridizerBlockEntity.this.setMode(pValue == 1);
                }
            }
        }

        @Override
        public int getCount() {
            return 6;
        }
    };


    public DNACombinatorHybridizerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.DNA_COMBINATOR_HYBRIDIZER.get(), pPos, pBlockState);
//        this.getMode() = pBlockState.getValue(DNACombinatorHybridizerBlock.MODE);
    }

    protected int getProcess(int slot) {
        return 0;
    }

    public boolean isProcessing() {
        return this.processTime > 0;
    }

    private Dinosaur getHybrid() {
        return this.getHybrid(this.inventory.get(0), this.inventory.get(1), this.inventory.get(2), this.inventory.get(3), this.inventory.get(4), this.inventory.get(5), this.inventory.get(6), this.inventory.get(7));
    }

    private Dinosaur getHybrid(ItemStack... discs) {
        Dinosaur hybrid = null;

        Dinosaur[] dinosaurs = new Dinosaur[discs.length];

        for (int i = 0; i < dinosaurs.length; i++) {
            dinosaurs[i] = this.getDino(discs[i]);
        }

        for (Dinosaur dino : Dinosaur.DINOS) {
            if (dino instanceof Hybrid) {
                Hybrid dinoHybrid = (Hybrid) dino;

                int count = 0;
                boolean extra = false;

                List<Class<? extends Dinosaur>> usedGenes = new ArrayList<>();

                for (Dinosaur discDinosaur : dinosaurs) {
                    Class match = null;

                    for (Class clazz : dinoHybrid.getDinosaurs()) {
                        if (clazz.isInstance(discDinosaur) && !usedGenes.contains(clazz)) {
                            match = clazz;
                        }
                    }

                    if (match != null && match.isInstance(discDinosaur)) {

                        usedGenes.add(match);
                        count++;
                    } else if (discDinosaur != null) {
                        extra = true;
                    }
                }

                if (!extra && count == dinoHybrid.getDinosaurs().length) {
                    hybrid = dino;

                    break;
                }
            }
        }
        return hybrid;
    }

    private Dinosaur getDino(ItemStack disc) {
        if (!disc.isEmpty() && disc.hasTag()) {
            DinoDNA data = DinoDNA.readFromNBT(disc.getTag());

            if (data == null) {
                return Dinosaur.EMPTY;
            }

            return data.getDNAQuality() == 100 ? data.getDinosaur() : null;
        } else {
            return null;
        }
    }

    protected boolean canProcess() {
        if (!this.getMode()) {
            return this.inventory.get(10).isEmpty() && this.getHybrid() != null;
        } else {
            final ItemStack left = this.inventory.get(8);
            final ItemStack right = this.inventory.get(9);

            if (!left.isEmpty() && left.getItem() == ModItems.STORAGE_DISC.get() && !right.isEmpty() && right.getItem() == ModItems.STORAGE_DISC.get()) {
                if (left.getTag() != null && right.getTag() != null && this.inventory.get(11).isEmpty()) {
                    //this is causing issues! I changed how DNA storage works so that it's in it's own DNA tag!
                    final String leftID = left.getTag().getCompound("DNA").getString("StorageId");
                    final String rightID = right.getTag().getCompound("DNA").getString("StorageId");
                    if(!leftID.equals(rightID))
                        return false;

                    if (leftID.equals("DinoDNA")) {
                        DinoDNA dna1 = DinoDNA.readFromNBT(left.getTag());
                        DinoDNA dna2 = DinoDNA.readFromNBT(right.getTag());
                        if (dna1 == null || dna2 == null) {
                            return false;
                        }

                        return dna1.getDinosaur() == dna2.getDinosaur();

                    } else if (leftID.equals("PlantDNA")) {
                        PlantDNA dna1 = PlantDNA.readFromNBT(left.getTag());
                        PlantDNA dna2 = PlantDNA.readFromNBT(right.getTag());
                        return dna1.getPlant().equals(dna2.getPlant());
                    }
                    return false;
                }
            }

            return false;
        }
    }


    protected void processItem() {
        if(this.level == null)
            return;

        if (this.canProcess()) {
            if (!this.getMode()) {
                Dinosaur hybrid = this.getHybrid();

                CompoundTag nbt = new CompoundTag();
                DinoDNA dna = new DinoDNA(getHybrid(), 100, GeneticsHelper.randomGenetics(level.random));

                DinoDNA firstDNA = DinoDNA.readFromNBT(this.inventory.get(0).getTag());

                if(firstDNA != null)
                    dna = new DinoDNA(hybrid, 100, firstDNA.getGenetics());

                dna.writeToNBT(nbt);

                ItemStack output = new ItemStack(ModItems.STORAGE_DISC.get());
                output.setTag(nbt);

                this.mergeStack(this.getOutputSlot(output), output);
            } else {
                ItemStack output = new ItemStack(ModItems.STORAGE_DISC.get());

                String storageId = this.inventory.get(8).getOrCreateTag().getCompound("DNA").getString("StorageId");

                if (storageId.equals("DinoDNA")) {
                    DinoDNA dna1 = DinoDNA.readFromNBT(this.inventory.get(8).getTag());
                    DinoDNA dna2 = DinoDNA.readFromNBT(this.inventory.get(9).getTag());

                    if(dna1 == null || dna2 == null)//this shouldn't happen but the game shouldn't crash if it does
                        return;

                    int newQuality = dna1.getDNAQuality() + dna2.getDNAQuality();

                    if (newQuality > 100) {
                        newQuality = 100;
                    }

                    DinoDNA newDNA = new DinoDNA(dna1.getDinosaur(), newQuality, dna1.getGenetics());

                    CompoundTag outputTag = new CompoundTag();
                    newDNA.writeToNBT(outputTag);
                    output.setTag(outputTag);

                } else if (storageId.equals("PlantDNA")) {
                    PlantDNA dna1 = PlantDNA.readFromNBT(this.inventory.get(8).getTag());
                    PlantDNA dna2 = PlantDNA.readFromNBT(this.inventory.get(9).getTag());

                    if(dna1 == null || dna2 == null)//this shouldn't happen but the game shouldn't crash if it does
                        return;

                    int newQuality = dna1.getDNAQuality() + dna2.getDNAQuality();

                    if (newQuality > 100) {
                        newQuality = 100;
                    }

                    PlantDNA newDNA = new PlantDNA(dna1.getPlant(), newQuality);

                    CompoundTag outputTag = new CompoundTag();
                    newDNA.writeToNBT(outputTag);
                    output.setTag(outputTag);
                }

                this.mergeStack(11, output);

                this.decreaseStackSize(8);
                this.decreaseStackSize(9);
            }
        }
    }

    protected void mergeStack(int slot, ItemStack stack) {
        NonNullList<ItemStack> slots = this.getInventory();

        ItemStack previous = slots.get(slot);
        if (previous.isEmpty()) {
            slots.set(slot, stack);
        } else if (ItemStack.isSame(previous, stack) && ItemStack.isSame(previous, stack)) {
            previous.setCount(previous.getCount() + stack.getCount());
        }
    }


    protected void decreaseStackSize(int slot) {
        var stack = this.inventory.get(slot);
        stack.shrink(1);
        this.setItem(slot, stack);
    }


    public int getTotalProcessTime() {
        return 1000;
    }



    protected int[] getInputs() {
        return this.getMode() ? COMBINATOR_INPUTS : HYBRIDIZER_INPUTS;
    }

    protected int[] getOutputs() {
        return this.getMode() ? COMBINATOR_OUTPUTS : HYBRIDIZER_OUTPUTS;
    }

    protected NonNullList<ItemStack> getInventory() {
        return this.inventory;
    }

//    protected void setInventory(NonNullList<ItemStack> inventory) {
//        this.inventory = inventory;
//    }

//    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
//        return new DNACombinatorHybridizerContainer(playerInventory, this);
//    }


//    public String getGuiID() {
//        return Jurassicworldreborn.MODID + ":dna_comb_hybrid";
//    }

    @Override
    public @NotNull Component getName() {
        Component name = this.getCustomName();

        return (this.hasCustomName() && name != null) ? (name) : Component.translatable("container.dna_combinator_hybridizer");
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
    public int @NotNull [] getSlotsForFace(@NotNull Direction pSide) {
        return new int[0];
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, @NotNull ItemStack pItemStack, @Nullable Direction pDirection) {
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int slotID, @NotNull ItemStack itemstack, @NotNull Direction side) {
        return Ints.asList(this.getMode() ? HYBRIDIZER_OUTPUTS : COMBINATOR_OUTPUTS).contains(slotID);
    }

    @Override
    public boolean canPlaceItem(int slotID, @NotNull ItemStack itemstack) {
        if (Ints.asList(this.getMode() ? HYBRIDIZER_INPUTS : COMBINATOR_INPUTS).contains(slotID)) {
            return itemstack.getItem() == ModItems.STORAGE_DISC.get() && this.getItem(slotID).getCount() == 0 && (itemstack.getTag() != null && itemstack.getTag().contains("DNA"));
        }

        return false;
    }


    public boolean getMode() {
        if(this.getLevel() == null){
            return false;
        }

        return this.getLevel().getBlockState(this.getBlockPos()).getValue(DNACombinatorHybridizerBlock.MODE);
    }

    public void setMode(boolean mode) {

        if(this.getLevel() == null)
            return;

        Network.switchHybridizerCombinerMode(mode, this.getBlockPos(), this.getLevel().dimension());
        this.processTime = 0;

        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState().setValue(DNACombinatorHybridizerBlock.MODE, mode), 0);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return this.hasCustomName() ? this.getName() : Component.translatable((!this.getMode()) ? "container.dna_hybridizer" : "container.dna_combinator");
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable((!this.getMode()) ? "container.dna_hybridizer" : "container.dna_combinator");
    }

    @Override
    public @NotNull AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory) {
        return new DNACombinatorHybridizerMenu(pContainerId, pInventory, this, this.data);
    }

    @Override
    public int getContainerSize() {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack stack : this.inventory) {
            if (!stack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected @NotNull NonNullList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    protected void setItems(@NotNull NonNullList<ItemStack> pItemStacks) {
        this.inventory = pItemStacks;
    }

    @Override
    public void setItem(int index, @NotNull ItemStack stack) {
        this.inventory.set(index, stack);//girl help what the HECK is below
//        NonNullList<ItemStack> slots = this.getInventory();
//
//        boolean stacksEqual = !stack.isEmpty() && stack.is(slots.get(index).getItem()) && ItemStack.isSame(stack, slots.get(index));
//        slots.set(index, stack);
//
//        if (!stack.isEmpty() && stack.getCount() > this.getMaxStackSize()) {
//            stack.setCount(this.getMaxStackSize());
//        }
//
//        if (!stacksEqual) { //yeah what on earth is this......
//            int process = this.getProcess(index);
//            if (process >= 0 && process < this.getProcessCount()) {
////                this.getTotalProcessTime() = this.getStackProcessTime(stack);
//                if (!this.canProcess()) {
//                    this.processTime = 0;
//                }
//                this.setChanged();
//            }
////            this.onSlotUpdate();
//        }
    }

    public int getOutputSlot(ItemStack output) {
        NonNullList<ItemStack> slots = this.getInventory();
        int[] outputs = this.getOutputs();
        for (int slot : outputs) {
            ItemStack stack = slots.get(slot);
            //if the slot is empty or contains a stack that can combine with ours, return the slot
            if (stack.isEmpty() || ((ItemStack.isSame(stack, output) && stack.getCount() + output.getCount() <= stack.getMaxStackSize()))) {
                return slot;
            }
        }
        return -1;
    }

//    @Override
//    public NonNullList<Integer> getSyncFields(NonNullList fields) {
//        NonNullList<Integer> actualList;
//        if (!fields.isEmpty() && !(fields.get(0) instanceof Integer) || fields.isEmpty()) {
//            actualList = NonNullList.of(0);
//        } else {
//            actualList = fields;
//        }
//        actualList.add(processTime);
//        actualList.add(this.getMode() ? 1 : 0);
//        return null;
//    }
//
//    @Override
//    public void packetDataHandler(ByteBuf fields) {
//
//    }

    @Override
    public void tick(Level level, @NotNull BlockPos pPos, @NotNull BlockState pState, @NotNull DNACombinatorHybridizerBlockEntity pBlockEntity) {
        NonNullList<ItemStack> slots = this.getInventory();
        if (level.isClientSide)
            return;

        boolean flag = this.isProcessing();
        boolean dirty = false;

        boolean hasInput = false;

        for (int input : this.getInputs()) {
            if (slots.size() > input && !slots.get(input).isEmpty()) {//added catch for index out of bounds
                hasInput = true;
                break;
            }
        }

        if (hasInput && this.canProcess()) {
                this.processTime++;

            if (this.processTime >= this.getTotalProcessTime()) {
                this.processItem();

                this.processTime = 0;


            }

            dirty = true;
        } else if (this.isProcessing()) {
            if (this.shouldResetProgress()) {
                this.processTime = 0;
            } else if (this.processTime > 0) {
                this.processTime--;
            }

            dirty = true;
        }

        if (flag != this.isProcessing()) {
            dirty = true;
        }

        if (dirty)
            this.setChanged();



    }

    protected boolean shouldResetProgress() {
        return true;
    }
}
