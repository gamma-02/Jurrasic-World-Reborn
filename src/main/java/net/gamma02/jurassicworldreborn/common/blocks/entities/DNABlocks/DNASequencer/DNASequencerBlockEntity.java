package net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNASequencer;

import net.gamma02.jurassicworldreborn.common.blocks.entities.MachineBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities;
import net.gamma02.jurassicworldreborn.common.network.Network;
import net.gamma02.jurassicworldreborn.common.util.api.SequencableItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

public class DNASequencerBlockEntity extends MachineBlockEntity<DNASequencerBlockEntity> {

    private static final int[] INPUTS = new int[] { 0, 1, 2, 3, 4, 5 };
    private static final int[] INPUTS_PROCESS_1 = new int[] { 0, 1 };
    private static final int[] INPUTS_PROCESS_2 = new int[] { 2, 3 };
    private static final int[] INPUTS_PROCESS_3 = new int[] { 4, 5 };

    private static final int[] DISCS_INPUT = new int[]{1, 3, 5};
    public static final int[] DNA_INPUT = new int[]{0, 2, 4};

    private static final int[] OUTPUTS = new int[] { 6, 7, 8 };

    private int[] sequencingTime = new int[3];



    private ContainerData sequencerData = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return DNASequencerBlockEntity.this.sequencingTime[pIndex];
        }

        @Override
        public void set(int pIndex, int pValue) {
            DNASequencerBlockEntity.this.sequencingTime[pIndex] = pValue;
        }

        @Override
        public int getCount() {
            return 3;
        }
    };

    private NonNullList<ItemStack> inventory = NonNullList.withSize(9, ItemStack.EMPTY);

    public DNASequencerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.DNA_SEQUENCER_BLOCK_ENTITY.get(), pPos, pBlockState);

        Network.ENTITIES.add(this);

    }

    @Override
    public Tag getMachineData() {
        CompoundTag machineData = new CompoundTag();
        machineData.putIntArray("SequencingTime", sequencingTime);


        return machineData;
    }

    @Override
    public void readMachineData(Tag data) {
        CompoundTag machineData = (CompoundTag) data;

        this.sequencingTime = machineData.getIntArray("SequencingTime");
    }

    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction pSide) {
        switch (pSide){
            case UP: return DNA_INPUT;
            case DOWN: return OUTPUTS;
            default: return DISCS_INPUT;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, @NotNull ItemStack pItemStack, @Nullable Direction pDirection) {
        if(pDirection != null)
            return Arrays.stream(this.getSlotsForFace(pDirection)).anyMatch((i) -> i == pIndex);

        return true;
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, @NotNull ItemStack pStack, @NotNull Direction pDirection) {


        return Arrays.stream(this.getSlotsForFace(pDirection)).anyMatch((i) -> i == pIndex);
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
        return Component.translatable("block.jurassicworldreborn.dna_sequencer");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory) {
        return new DNASequencerMenu(pContainerId, this, this.sequencerData, pInventory);
    }

    @Override
    public int getContainerSize() {
        return 9;
    }

    protected void sequenceItem(ItemStack tissue, int input){
        RandomSource rand = Objects.requireNonNull(this.level).getRandom();

//        this.mergeStack(process + 6, SequencableItem.getSequencableItem(sequencableStack).getSequenceOutput(sequencableStack, rand));
        ItemStack output = SequencableItem.getSequencableItem(tissue).getSequenceOutput( tissue, rand );
        this.setItem(OUTPUTS[(input+1)/2], output);
        tissue.shrink(1);
        this.setItem(input, tissue);
        ItemStack disc = this.getItem(input + 1);
        disc.shrink(1);
        this.setItem(input + 1, disc);
//        BlockPos pos = this.pos;
    }

    public boolean canProcess(int slot){
        return !this.getItem(slot).isEmpty() && !this.getItem(slot + 1).isEmpty() && this.getItem(OUTPUTS[(slot+1)/2]).isEmpty();
    }



    @Override
    public void tick(Level pLevel, BlockPos pPos, BlockState pState, DNASequencerBlockEntity pBlockEntity) {
        if(pLevel.isClientSide){
            return;
        }
        for (int input = 0; input < DNA_INPUT.length; input++) {
            int time = this.sequencingTime[input];
            int dna = DNA_INPUT[input];

            if(!canProcess(dna)){
                this.sequencingTime[input] = 0;
                continue;
            }else{
                time++;
                this.sequencingTime[input] = time;
            }

            if(time >= 2000){
                this.sequenceItem(this.getItem(dna), dna);
                time = 0;
                this.sequencingTime[input] = time;

            }



        }



    }
}
