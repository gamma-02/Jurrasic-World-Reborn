package net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNAExtractor;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.common.blocks.entities.MachineBlockEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurHandler;
import net.gamma02.jurassicworldreborn.common.genetics.DinoDNA;
import net.gamma02.jurassicworldreborn.common.genetics.GeneticsHelper;
import net.gamma02.jurassicworldreborn.common.genetics.PlantDNA;
import net.gamma02.jurassicworldreborn.common.items.Food.DinosaurMeatItem;
import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.gamma02.jurassicworldreborn.common.network.Network;
import net.gamma02.jurassicworldreborn.common.plants.Plant;
import net.gamma02.jurassicworldreborn.common.plants.PlantHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities.DNA_EXTRACTOR_BLOCK_ENTITY;

public class DNAExtractorBlockEntity extends MachineBlockEntity<DNAExtractorBlockEntity> implements BlockEntityTicker<DNAExtractorBlockEntity>, WorldlyContainer, IAnimatable {

    private static final int[] INPUTS = new int[]{0, 1};
    private static final int[] OUTPUTS = new int[]{2, 3, 4, 5};
    public static final int PROCESS_TIME = 2000;

    public final ContainerData ExtractorData = new ContainerData() {
        @Override
        public int get(int pIndex) {
            if(pIndex == 0){
                return DNAExtractorBlockEntity.this.extractionTime;
            }
            return -1;
        }

        @Override
        public void set(int pIndex, int pValue) {
            if(pIndex == 0){
                DNAExtractorBlockEntity.this.extractionTime = pValue;
            }
        }

        @Override
        public int getCount() {
            return 1;
        }
    };

    private int extractionTime = 0;

    private NonNullList<ItemStack> inventory = NonNullList.withSize(6, ItemStack.EMPTY);



    public DNAExtractorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(DNA_EXTRACTOR_BLOCK_ENTITY.get(), pPos, pBlockState);

        Network.ENTITIES.add(this);

    }

    @Override
    public int @NotNull [] getSlotsForFace(Direction pSide) {
        switch(pSide){
            case DOWN -> {
                return OUTPUTS;
            } case UP ->{
                return new int[]{0};
            } case EAST -> {
                return new int[]{1};
            } case WEST -> {
                return new int[]{1};
            } case NORTH -> {
                return new int[]{1};
            } case SOUTH -> {
                return new int[]{1};
            } default -> {
                return new int[0];
            }
        }

    }



    @Override
    public boolean canPlaceItemThroughFace(int pIndex, @NotNull ItemStack pItemStack, @Nullable Direction pDirection) {
        return Arrays.binarySearch(INPUTS, pIndex) >= 0;
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, @NotNull ItemStack pStack, @NotNull Direction pDirection) {
        return Arrays.binarySearch(OUTPUTS, pIndex) >= 0;
    }

    @Override
    protected @NotNull NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void setItems(@NotNull NonNullList<ItemStack> pItemStacks) {
        this.inventory = pItemStacks;
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("block.jurassicworldreborn.dna_extractor");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory) {
        return new DNAExtractorMenu(pContainerId, this, this.ExtractorData, pInventory);
    }

    @Override
    public @NotNull ItemStack getItem(int pIndex) {
        if(pIndex < this.inventory.size() && pIndex >= 0)
            return this.inventory.get(pIndex);
        return ItemStack.EMPTY;
    }

    @Override
    public int getContainerSize() {
        return 6;
    }


    //this should be made to be complient with the docs listed in the superclass at another time but not rn

    public boolean canProcess(ItemStack... inputs){
        return !this.getItem(0).isEmpty() && !this.getItem(1).isEmpty() && this.hasSpace();
    }

    public boolean hasSpace(){

        for(int i : OUTPUTS){
            if(this.getItem(i).isEmpty())
                return true;
        }
        return false;
    }



    @Override
    public void tick(Level pLevel, BlockPos pPos, BlockState pState, DNAExtractorBlockEntity pBlockEntity) {
        super.tick(pLevel, pPos, pState, pBlockEntity);
        if(pLevel.isClientSide){
            return;
        }
//        for (int input = 0; input < INPUTS.length; input++) {


        if(!canProcess(this.getItem(0))){
            this.extractionTime = 0;
            return;
        }else{
            this.extractionTime++;
        }

        if(this.extractionTime >= 2000){
            this.processItem(this.getItem(0));
            this.extractionTime = 0;

        }




    }
    //this should be made to be complient with the docs listed in the superclass at SOME POINT:tm: but im not doing that rn

    public @NotNull List<ItemStack> processItem(ItemStack... input) {
        RandomSource rand = Objects.requireNonNull(this.level).getRandom();
        Item item = input[0].getItem();

//        this.mergeStack(process + 6, SequencableItem.getSequencableItem(sequencableStack).getSequenceOutput(sequencableStack, rand));
//        ItemStack output = SequencableItem.getSequencableItem(item).getSequenceOutput( tissue, rand );
//        this.setItem(OUTPUTS[(input+1)/2], output);
//        tissue.shrink(1);
//        this.setItem(input, tissue);
//        ItemStack disc = this.getItem(input + 1);
//        disc.shrink(1);
//        this.setItem(input + 1, disc);
        ItemStack disc = ItemStack.EMPTY;
        if (item == ModItems.MOSQUITO_AMBER.get() || item == ModItems.SEA_LAMPREY.get()) {
                List<Dinosaur> possibleDinos = item == ModItems.MOSQUITO_AMBER.get() ? DinosaurHandler.getDinosaursFromAmber() : DinosaurHandler.getMarineCreatures();

                Dinosaur dino = possibleDinos.get(rand.nextIntBetweenInclusive(0, possibleDinos.size()));

                disc = ModItems.STORAGE_DISC.get().getDefaultInstance();

                int quality = 50 + (rand.nextInt(50));

                DinoDNA dna = new DinoDNA(dino, quality, GeneticsHelper.randomGenetics(rand));

                CompoundTag nbt = new CompoundTag();
                dna.writeToNBT(nbt);

                disc.setTag(nbt);

        } else if (item == ModItems.APHID_AMBER.get()) {
            List<Plant> possiblePlants = PlantHandler.getPrehistoricPlantsAndTrees();

            Plant plant = possiblePlants.get(rand.nextIntBetweenInclusive(0, possiblePlants.size() - 1));



            disc = ModItems.STORAGE_DISC.get().getDefaultInstance();

            int quality = 50 + (rand.nextInt(50));

            PlantDNA dna = new PlantDNA(Jurassicworldreborn.resource(plant.getName().toLowerCase(Locale.ROOT).replace(" ", "_")), quality);

            CompoundTag nbt = new CompoundTag();
            dna.writeToNBT(nbt);


            disc.setTag(nbt);
        }
        if (item == ModItems.MOSQUITO_AMBER.get() || item == ModItems.FROZEN_LEECH_ITEM.get()) {

                List<Dinosaur> possibleDinos = item == ModItems.MOSQUITO_AMBER.get() ? DinosaurHandler.getDinosaursFromAmber() : DinosaurHandler.getMammalCreatures();

                Dinosaur dino = possibleDinos.get(rand.nextIntBetweenInclusive(0, possibleDinos.size()));

                disc = ModItems.STORAGE_DISC.get().getDefaultInstance();
                int quality = 50 + (rand.nextInt(50));

                DinoDNA dna = new DinoDNA(dino, quality, GeneticsHelper.randomGenetics(rand));

                CompoundTag nbt = new CompoundTag();
                dna.writeToNBT(nbt);

                disc.setTag(nbt);

        } else if (item instanceof DinosaurMeatItem meat) {

            Dinosaur dino = meat.getDinosaur();
            disc = ModItems.STORAGE_DISC.get().getDefaultInstance();
            DinoDNA dna = new DinoDNA(dino, 100, GeneticsHelper.randomGenetics(rand));

            CompoundTag nbt = new CompoundTag();
            dna.writeToNBT(nbt);

            disc.setTag(nbt);

        }
        if(!disc.isEmpty()) {
            input[0].shrink(1);
            this.setItem(0, input[0]);
            ItemStack input_disc = this.getItem(1);
            input_disc.shrink(1);
            this.setItem(1, input_disc);

            this.setItem(getOpenSlot(), disc);

        }
        return List.of(ItemStack.EMPTY);
    }

    public int getOpenSlot(){
        for(int i = 2; i <= 6; i++){
            if(this.getItem(i).isEmpty()){
                return i;
            }
        }
        return -1;
    }


    //model stuff
    protected static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("animation.dna_extractor.idle");
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "idle", 0, this::idleController));
    }

    protected <E extends DNAExtractorBlockEntity> PlayState idleController(final AnimationEvent<E> event){
        event.getController().setAnimation(IDLE);

        return PlayState.STOP;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public Tag getMachineData() {
        CompoundTag data = new CompoundTag();
        data.putInt("ExtractionTime", extractionTime);


        return data;
    }

    @Override
    public void readMachineData(Tag tag){
        CompoundTag machineData = (CompoundTag) tag;
        this.extractionTime = machineData.getInt("ExtractionTime");
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        Network.ENTITIES.remove(this);
    }
}
