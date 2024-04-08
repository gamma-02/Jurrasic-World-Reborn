package net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNASynthesizer;

import net.gamma02.jurassicworldreborn.common.blocks.entities.MachineBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities;
import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.gamma02.jurassicworldreborn.common.util.api.SynthesizableItem;
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
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class DNASynthesizerBlockEntity extends MachineBlockEntity<DNASynthesizerBlockEntity> {

    private static final int[] INPUTS = new int[] { 0, 1, 2 };
    private static final int[] OUTPUTS = new int[] { 3, 4, 5, 6 };

    private int synthesizeTime = 0;

    private NonNullList<ItemStack> inventory = NonNullList.withSize(7, ItemStack.EMPTY);

    public ContainerData SynthesizerData = new ContainerData() {
        @Override
        public int get(int pIndex) {
            if(pIndex == 0){
                return DNASynthesizerBlockEntity.this.synthesizeTime;
            }
            return -1;
        }

        @Override
        public void set(int pIndex, int pValue) {
            if(pIndex == 0){
                DNASynthesizerBlockEntity.this.synthesizeTime = pValue;
            }
        }

        @Override
        public int getCount() {
            return 1;
        }
    };

    public DNASynthesizerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.DNA_SYNTHESIZER_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public Tag getMachineData() {
        CompoundTag machineData = new CompoundTag();
        machineData.putInt("SynthesizeTime", synthesizeTime);
        return machineData;
    }

    @Override
    public void readMachineData(Tag data) {
        CompoundTag machineData = (CompoundTag) data;
        this.synthesizeTime = machineData.getInt("SynthesizeTime");
    }

    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction pSide) {
        return switch(pSide){
            case DOWN -> OUTPUTS;
            case UP -> new int[]{1, 2};
            case NORTH, SOUTH, WEST, EAST -> new int[]{0};
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
    protected @NotNull NonNullList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    protected void setItems(@NotNull NonNullList<ItemStack> pItemStacks) {
        this.inventory = pItemStacks;
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("block.jurassicworldreborn.dna_synthesizer");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory) {
        return new DNASynthesizerMenu(pContainerId, this, this.SynthesizerData, pInventory);
    }

    @Override
    public int getContainerSize() {
        return 7;
    }

    @Override
    public @NotNull ItemStack getItem(int pIndex) {
        return this.inventory.get(pIndex);
    }

    public int getOpenOutput(ItemStack output){
        for(int i : OUTPUTS){
            if(this.getItem(i).isEmpty() || (ItemStack.isSameItemSameTags(output, this.getItem(i)) && ItemStack.isSameItemSameTags(this.getItem(i), output))){
                return i;
            }
        }
        return -1;
    }



    public boolean hasSpace(){
        for(int i : OUTPUTS){
            if(this.getItem(i).isEmpty()){
                return true;
            }
        }
        return false;
    }


    //should be made complient with superclass docs but not rn - gamma
    public @NotNull List<ItemStack> processItem(ItemStack... inputs){
        ItemStack storageDisc = this.inventory.get(0);

        ItemStack output = SynthesizableItem.getSynthesizableItem(storageDisc).getSynthesizedItem(storageDisc, this.level.getRandom());

        int outputSlot = this.getOpenOutput(output);

        if (outputSlot != -1) {
//            this.setItem(outputSlot, output);
            this.mergeStack(outputSlot, output);

            ItemStack tube = this.getItem(1);
            ItemStack dnaBaseMaterial = this.getItem(2);
            tube.shrink(1);
            dnaBaseMaterial.shrink(1);
            this.setItem(1, tube);
            this.setItem(2, dnaBaseMaterial);

        }
        return List.of(ItemStack.EMPTY);
    }

    @Override
    public void tick(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, @NotNull DNASynthesizerBlockEntity pBlockEntity) {
        super.tick(pLevel, pPos, pState, pBlockEntity);

        if(pLevel.isClientSide)
            return;

        if(!canProcess()){
            this.synthesizeTime = 0;
            return;
        }else{
            this.synthesizeTime++;
        }

        if(this.synthesizeTime >= 2000){
            this.processItem();
            this.synthesizeTime = 0;

        }
    }

    //SHOuld be made compliant with superclass docs but not rn
    public boolean canProcess(ItemStack... inputs) {
        return this.getItem(0).getItem() == ModItems.STORAGE_DISC.get() && this.getItem(1).getItem() == ModItems.EMPTY_TEST_TUBE.get()
                && this.getItem(2).getItem() == ModItems.DNA_NUCLEOTIDES.get();

    }
}
