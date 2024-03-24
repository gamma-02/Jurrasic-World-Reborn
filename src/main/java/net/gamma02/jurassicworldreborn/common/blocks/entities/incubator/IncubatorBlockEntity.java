package net.gamma02.jurassicworldreborn.common.blocks.entities.incubator;

import com.google.common.primitives.Ints;
import net.gamma02.jurassicworldreborn.common.blocks.entities.MachineBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities;
import net.gamma02.jurassicworldreborn.common.items.IncubatorEnvironmentItem;
import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.gamma02.jurassicworldreborn.common.items.genetics.DinosaurEggItem;
import net.gamma02.jurassicworldreborn.common.util.block.TemperatureControl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Arrays;

public class IncubatorBlockEntity extends MachineBlockEntity<IncubatorBlockEntity> implements WorldlyContainer, TemperatureControl, IAnimatable {


    public static final int[] INPUTS = new int[] { 0, 1, 2, 3, 4 };
    public static final int[] ENVIRONMENT = new int[] { 5 };

    public static final int PROCESS_TIME = 8000;

    private int[] temperature = new int[5];

    private int[] eggIncubationTime = new int[5];

    private NonNullList<ItemStack> inventory = NonNullList.withSize(6, ItemStack.EMPTY);

    private ContainerData data = new ContainerData() {
        @Override
        public int get(int pIndex) {
            if(pIndex < 5){
                return IncubatorBlockEntity.this.temperature[pIndex];
            }else if(pIndex < 10){
                return IncubatorBlockEntity.this.eggIncubationTime[pIndex-5];
            }
            return -1;
        }

        @Override
        public void set(int pIndex, int pValue) {
            if(pIndex < 5){
                IncubatorBlockEntity.this.temperature[pIndex] = pValue;
            }else if(pIndex < 10){
                IncubatorBlockEntity.this.eggIncubationTime[pIndex - 5] = pValue;
            }
        }

        @Override
        public int getCount() {
            return 10;
        }
    };



    public IncubatorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.INCUBATOR_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public Tag getMachineData() {
        ListTag dataList = new ListTag();

        for (int i = 0; i < 5; i++) {
            CompoundTag dataEntry = new CompoundTag();
            dataEntry.putInt("temperature", this.temperature[i]);
            dataEntry.putInt("incubationTime", this.eggIncubationTime[i]);


            dataList.add(dataEntry);
        }

        return dataList;
    }

    @Override
    public void readMachineData(Tag machineData) {
        ListTag dataList = (ListTag) machineData;
        for (int i = 0; i < 5; i++) {
            CompoundTag dataEntry = dataList.getCompound(i);
            this.temperature[i] = dataEntry.getInt("temperature");
            this.eggIncubationTime[i] = dataEntry.getInt("incubationTime");
        }
    }


    @Override
    public boolean canPlaceItem(int slotID, ItemStack itemstack) {
        if (Ints.asList(INPUTS).contains(slotID)) {
            if (itemstack != null && itemstack.getItem() instanceof DinosaurEggItem) {
                return true;
            }
        }else if (isEnvironment(slotID, itemstack.getItem())) return true;

        return false;
    }

    private static boolean isEnvironment(int slotID, Item item) {
        if(Ints.asList(ENVIRONMENT).contains(slotID)) {
            if ( item instanceof IncubatorEnvironmentItem || (item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof IncubatorEnvironmentItem)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setTemperature(int index, int value) {
        this.temperature[index] = value;
    }

    @Override
    public int getTemperature(int index) {
        return this.temperature[index];
    }

    @Override
    public int getTemperatureCount() {
        return 5;
    }

    public boolean isProcessing(){
        return Arrays.stream(this.eggIncubationTime).anyMatch((i) -> i != 0);
    }


    @Override
    public int[] getSlotsForFace(Direction pSide) {
        return new int[0];
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        return true;
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        return true;
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
        return Component.translatable("tile.incubator.name");
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new IncubatorMenu(pContainerId, pInventory, this, this.data);
    }

    @Override
    public int getContainerSize() {
        return 5;
    }
    protected void hatchItem(int process) {
        if (this.canHatchEgg(process) && !this.level.isClientSide) {
            ItemStack egg = this.inventory.get(process);
            DinosaurEggItem dinoEgg = (DinosaurEggItem)(egg.getItem());
            ItemStack incubatedEgg = new ItemStack(ModItems.hatchedDinoEggs.get(dinoEgg.getDino()).get(), 1);
            CompoundTag compound = new CompoundTag();
            compound.putBoolean("Gender", this.temperature[process] > 50);

            if (egg.getTag() != null) {
                compound.putString("Genetics", egg.getTag().getCompound("DNA").getString("Genetics"));
                compound.putInt("DNAQuality", egg.getTag().getCompound("DNA").getInt("DNAQuality"));
            }

            incubatedEgg.setTag(compound);

            this.decreaseStackSize(5);
            this.setItem(process, incubatedEgg);
        }
    }

    protected boolean canHatchEgg(int slot) {
        ItemStack environment = this.inventory.get(ENVIRONMENT[0]);
        boolean hasEnvironment = false;

        if (!environment.isEmpty()) {
            Item item = environment.getItem();

            if (isEnvironment(0, item)) {
                hasEnvironment = true;
            }
        }

        return hasEnvironment && !this.inventory.get(slot).isEmpty() && this.inventory.get(slot).getCount() > 0 && this.inventory.get(slot).getItem() instanceof DinosaurEggItem;
    }

    @Override
    public void tick(@NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, @NotNull MachineBlockEntity pBlockEntity) {
        super.tick(pLevel, pPos, pState, pBlockEntity);
    }

    //model stuff
    protected static final AnimationBuilder INACTIVE = new AnimationBuilder().addAnimation("animation.incubator.inactive");

    protected static final AnimationBuilder TRANSITION_ACTIVE = new AnimationBuilder().addAnimation("animation.incubator.transition_active");
    protected static final AnimationBuilder TRANSITION_ACTIVE_LOOP = new AnimationBuilder().addAnimation("animation.incubator.transition_active", ILoopType.EDefaultLoopTypes.LOOP);

    protected static final AnimationBuilder ACTIVE = new AnimationBuilder().addAnimation("animation.incubator.active");
    protected static final AnimationBuilder TRANSITION_INACTIVE = new AnimationBuilder().addAnimation("animation.incubator.transition_inactive");
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 16, this::controller));
    }

    protected <E extends IncubatorBlockEntity> PlayState controller(final AnimationEvent<E> event){
//        event.getController().setAnimation(INACTIVE);
        AnimationController controller = event.getController();

        if(controller.getCurrentAnimation() == null){
            controller.setAnimation(INACTIVE);
            return PlayState.CONTINUE;
        }



        if(this.isProcessing() && controller.getCurrentAnimation().animationName.equals("animation.incubator.inactive")){//we are processing but the animation is inactive

            //transition to active
            controller.setAnimation(TRANSITION_ACTIVE.addAnimation("animation.incubator.active"));

        }
        else if(!this.isProcessing() && controller.getCurrentAnimation().animationName.equals("animation.incubator.active")){//we are not processing but the animation is active

            controller.setAnimation(TRANSITION_INACTIVE.addAnimation("animation.incubator.inactive"));

        }
        else
            controller.setAnimation(INACTIVE);



        return PlayState.CONTINUE;
    }



    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }


}
