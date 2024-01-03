package net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNACombinatorHybridizer;

import com.google.common.primitives.Ints;
import io.netty.buffer.ByteBuf;
import net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Hybrid;
import net.gamma02.jurassicworldreborn.common.genetics.DinoDNA;
import net.gamma02.jurassicworldreborn.common.genetics.GeneticsHelper;
import net.gamma02.jurassicworldreborn.common.genetics.PlantDNA;
import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.gamma02.jurassicworldreborn.common.network.Network;
import net.gamma02.jurassicworldreborn.common.util.networking.Syncable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DNACombinatorHybridizerBlockEntity extends RandomizableContainerBlockEntity implements Syncable, BlockEntityTicker<DNACombinatorHybridizerBlockEntity>, WorldlyContainer {
    public static final int[] HYBRIDIZER_INPUTS = new int[] { 0, 1, 2, 3, 4, 5, 6, 7 };
    public static final int[] COMBINATOR_INPUTS = new int[] { 8, 9 };
    public static final int[] HYBRIDIZER_OUTPUTS = new int[] { 10 };
    public static final int[] COMBINATOR_OUTPUTS = new int[] { 11 };

    public int processTime;

    private NonNullList<ItemStack> inventory = NonNullList.of(ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY);

//    private boolean getMode();

    private ContainerData data = new ContainerData() {
        BlockPos pos = DNACombinatorHybridizerBlockEntity.this.getBlockPos();
        @Override
        public int get(int pIndex) {
            if(pIndex >= 2){
                if(pIndex == 2) {
                    return DNACombinatorHybridizerBlockEntity.this.getMode() ? 1 : 0;
                }else if(pIndex == 3){
                    return DNACombinatorHybridizerBlockEntity.this.getBlockPos().getX();
                }else if(pIndex == 4){
                    return DNACombinatorHybridizerBlockEntity.this.getBlockPos().getY();
                }else if(pIndex == 5){
                    return DNACombinatorHybridizerBlockEntity.this.getBlockPos().getZ();
                }
                return 0;
            }else if(pIndex == 0){
                return DNACombinatorHybridizerBlockEntity.this.processTime;
            }else if(pIndex == 1){
                return DNACombinatorHybridizerBlockEntity.this.getTotalProcessTime();
            }
            return 0;
        }

        @Override
        public void set(int pIndex, int pValue) {
            if(pIndex >= 2){
                if(pIndex == 2 && pValue < 2 && pValue > -1){
                    DNACombinatorHybridizerBlockEntity.this.setMode(pValue == 1);
                }
            }
        }

        @Override
        public int getCount() {
            return 6;
        }
    };



    public DNACombinatorHybridizerBlockEntity( BlockPos pPos, BlockState pBlockState) {
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

            return data.getDNAQuality() == 100 ? data.getDinosaur() : null;
        } else {
            return null;
        }
    }

    protected boolean canProcess(int process) {
        if (this.getMode()) {
            return this.inventory.get(10).isEmpty() && this.getHybrid() != null;
        } else {
            final ItemStack left = this.inventory.get(8);
            final ItemStack right = this.inventory.get(9);
            if (!left.isEmpty() && left.getItem() == ModItems.STORAGE_DISC.get() && !right.isEmpty() && right.getItem() == ModItems.STORAGE_DISC.get()) {
                final String leftID = left.getTag().getString("StorageId");
                final String rightID = right.getTag().getString("StorageId");
                if (left.getTag() != null && right.getTag() != null && this.inventory.get(11).isEmpty() && left.getDamageValue() == right.getDamageValue() && leftID.equals(rightID)) {
                    if(leftID.equals("DinoDNA")) {
                        DinoDNA dna1 = DinoDNA.readFromNBT(left.getTag());
                        DinoDNA dna2 = DinoDNA.readFromNBT(right.getTag());
                        if(dna1.getDinosaur() == dna2.getDinosaur())
                            return true;

                    }else if(leftID.equals("PlantDNA")) {
                        PlantDNA dna1 = PlantDNA.readFromNBT(left.getTag());
                        PlantDNA dna2 = PlantDNA.readFromNBT(right.getTag());
                        if(dna1.getPlant() == dna2.getPlant())
                            return true;
                    }
                    return false;
                }
            }

            return false;
        }
    }


    protected void processItem(int process) {
        if (this.canProcess(process)) {
            if (this.getMode()) {
                Dinosaur hybrid = this.getHybrid();

                CompoundTag nbt = new CompoundTag();
                DinoDNA dna = new DinoDNA(getHybrid(), 100, GeneticsHelper.randomGenetics(level.random));
                try {
                    dna = new DinoDNA(hybrid, 100, this.inventory.get(0).getTag().getString("Genetics"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dna.writeToNBT(nbt);

                ItemStack output = new ItemStack(ModItems.STORAGE_DISC.get());
                output.setTag(nbt);

                this.mergeStack(this.getOutputSlot(output), output);
            } else {
                ItemStack output = new ItemStack(ModItems.STORAGE_DISC.get());

                String storageId = this.inventory.get(8).getTag().getString("StorageId");

                if (storageId.equals("DinoDNA")) {
                    DinoDNA dna1 = DinoDNA.readFromNBT(this.inventory.get(8).getTag());
                    DinoDNA dna2 = DinoDNA.readFromNBT(this.inventory.get(9).getTag());
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
        NonNullList<ItemStack> slots = this.getInventory();

        slots.get(slot).shrink(1);

        if (slots.get(slot).getCount() <= 0) {
            slots.set(slot, ItemStack.EMPTY);
        }
    }

    protected int getMainOutput() {
        return this.getMode() ? 10 : 11;
    }

    protected int getStackProcessTime(ItemStack stack) {
        return 1000;
    }

    public int getTotalProcessTime() {
        return 1000;
    }

    protected int getProcessCount() {
        return 1;
    }

    protected int[] getInputs() {
        return this.getMode() ? HYBRIDIZER_INPUTS : COMBINATOR_INPUTS;
    }

    protected int[] getInputs(int process) {
        return this.getInputs();
    }

    protected int[] getOutputs() {
        return this.getMode() ? HYBRIDIZER_OUTPUTS : COMBINATOR_OUTPUTS;
    }

    protected NonNullList<ItemStack> getInventory() {
        return this.inventory;
    }

    protected void setInventory(NonNullList<ItemStack> inventory) {
        this.inventory = inventory;
    }

//    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
//        return new DNACombinatorHybridizerContainer(playerInventory, this);
//    }




//    public String getGuiID() {
//        return Jurassicworldreborn.MODID + ":dna_comb_hybrid";
//    }

    @Override
    public Component getName() {
        return this.hasCustomName() ? (this.getCustomName()) : Component.translatable("container.dna_combinator_hybridizer");
    }

    public void readFromNBT(CompoundTag nbt) {
//        super.readFromNBT(nbt);

//        this.getMode() = nbt.getBoolean("HybridizerMode");
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);

        readFromNBT(compound);
    }

    public CompoundTag writeToNBT(CompoundTag nbt) {
//        nbt = super.writeToNBT(nbt);

//        nbt.putBoolean("HybridizerMode", this.getMode());

        return nbt;
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        this.writeToNBT(compound);
    }

    @Override
    public int[] getSlotsForFace(Direction pSide) {
        return new int[0];
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int slotID, ItemStack itemstack, Direction side) {
        return Ints.asList(this.getMode() ? HYBRIDIZER_OUTPUTS : COMBINATOR_OUTPUTS).contains(slotID);
    }

    @Override
    public boolean canPlaceItem(int slotID, ItemStack itemstack) {
        if (Ints.asList(this.getMode() ? HYBRIDIZER_INPUTS : COMBINATOR_INPUTS).contains(slotID)) {
            if (itemstack != null && itemstack.getItem() == ModItems.STORAGE_DISC.get() && this.getItem(slotID).getCount() == 0 && (itemstack.getTag() != null && itemstack.getTag().contains("DNAQuality"))) {
                return true;
            }
        }

        return false;
    }


    public boolean getMode() {
        return this.getLevel().getBlockState(this.getBlockPos()).getValue(DNACombinatorHybridizerBlock.MODE);
    }

    public void setMode(boolean mode) {
        Network.switchHybridizerCombinerMode(mode, this.getBlockPos(), this.getLevel().dimension());
        this.processTime = 0;
        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState().setValue(DNACombinatorHybridizerBlock.MODE, mode), 0);
    }

    @Override
    public Component getDisplayName() {
        return this.hasCustomName() ? this.getName() : Component.translatable(this.getMode() ? "container.dna_hybridizer" : "container.dna_combinator");
    }

    @Override
    protected Component getDefaultName() {
        return null;
    }

    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new DNACombinatorHybridizerMenu(pContainerId, pInventory, this, this.data);
    }

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
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
    public void setItem(int index, ItemStack stack) {
        NonNullList<ItemStack> slots = this.getInventory();

        boolean stacksEqual = !stack.isEmpty() && stack.is(slots.get(index).getItem()) && ItemStack.isSame(stack, slots.get(index));
        slots.set(index, stack);

        if (!stack.isEmpty() && stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }

        if (!stacksEqual) {
            int process = this.getProcess(index);
            if (process >= 0 && process < this.getProcessCount()) {
//                this.getTotalProcessTime() = this.getStackProcessTime(stack);
                if (!this.canProcess(process)) {
                    this.processTime = 0;
                }
                this.setChanged();
            }
//            this.onSlotUpdate();
        }
    }

    public int getOutputSlot(ItemStack output) {
        NonNullList<ItemStack> slots = this.getInventory();
        int[] outputs = this.getOutputs();
        for (int slot : outputs) {
            ItemStack stack = slots.get(slot);
            if (stack.isEmpty() || ((ItemStack.isSame(stack, output) && stack.getCount() + output.getCount() <= stack.getMaxStackSize()) && stack.getItem() == output.getItem() && stack.getDamageValue() == output.getDamageValue())) {
                return slot;
            }
        }
        return -1;
    }

    @Override
    public NonNullList<Integer> getSyncFields(NonNullList fields) {
        NonNullList<Integer> actualList;
        if(!fields.isEmpty() && !(fields.get(0) instanceof Integer) || fields.isEmpty()){
            actualList = NonNullList.of(0);
        }else{
            actualList = fields;
        }
        actualList.add(processTime);
        actualList.add(this.getMode() ? 1 : 0);
        return null;
    }

    @Override
    public void packetDataHandler(ByteBuf fields) {

    }

    @Override
    public void tick(Level level, BlockPos pPos, BlockState pState, DNACombinatorHybridizerBlockEntity pBlockEntity) {
        NonNullList<ItemStack> slots = this.getInventory();

        if(!level.isClientSide) {
            for (int process = 0; process < this.getProcessCount(); process++) {
                boolean flag = this.isProcessing();
                boolean dirty = false;

                boolean hasInput = false;

                for (int input : this.getInputs(process)) {
                    if (slots.size() > input && !slots.get(input).isEmpty()) {//added catch for index out of bounds
                        hasInput = true;
                        break;
                    }
                }

                if (hasInput && this.canProcess(process)) {
                    this.processTime++;

                    if (this.processTime >= this.getTotalProcessTime()) {
                        this.processTime = 0;
                        //this updates total process time; however; total process time is a constant for this machine so this is unnessacary. removing.
//                        int total = 0;
//                        for (int input : this.getInputs()) {
//                            ItemStack stack = slots.get(input);
//                            if (!stack.isEmpty()) {
//                                total = this.getStackProcessTime(stack);
//                                break;
//                            }
                        //}
//                        this.totalProcessTime[process] = total;
                        if (!level.isClientSide)
                        {
                            this.processItem(process);
//                            this.onSlotUpdate();
                        }

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

                if (dirty && !level.isClientSide) {
                    this.setChanged();
                }
            }
        }
    }
    protected boolean shouldResetProgress() {
        return true;
    }
}
