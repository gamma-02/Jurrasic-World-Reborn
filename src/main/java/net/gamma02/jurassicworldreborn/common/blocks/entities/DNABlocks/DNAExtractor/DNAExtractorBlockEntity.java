package net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNAExtractor;

import net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNACombinatorHybridizer.DNACombinatorHybridizerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
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
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Arrays;

import static net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities.DNA_EXTRACTOR_BLOCK_ENTITY;

public class DNAExtractorBlockEntity extends RandomizableContainerBlockEntity implements BlockEntityTicker<DNACombinatorHybridizerBlockEntity>, WorldlyContainer, IAnimatable {

    private static final int[] INPUTS = new int[]{0, 1};
    private static final int[] OUTPUTS = new int[]{2, 3, 4, 5};
    public static final int PROCESS_TIME = 2000;

    private int processTime = 0;




    private NonNullList<ItemStack> inventory = NonNullList.withSize(6, ItemStack.EMPTY);

    public ContainerData DNAExtractorData = new ContainerData() {
        @Override
        public int get(int pIndex) {
            return 0;
        }

        @Override
        public void set(int pIndex, int pValue) {

        }

        @Override
        public int getCount() {
            return 0;
        }
    };


    public DNAExtractorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(DNA_EXTRACTOR_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public int[] getSlotsForFace(Direction pSide) {
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
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        return Arrays.binarySearch(INPUTS, pIndex) >= 0;
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        return Arrays.binarySearch(OUTPUTS, pIndex) >= 0;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> pItemStacks) {
        this.inventory = pItemStacks;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("title.dna_extractor.name");
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return null;
    }

    @Override
    public int getContainerSize() {
        return 6;
    }

    @Override
    public void tick(Level pLevel, BlockPos pPos, BlockState pState, DNACombinatorHybridizerBlockEntity pBlockEntity) {

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
}
