package net.gamma02.jurassicworldreborn.common.blocks.entities.cleaner;

import net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities;
import net.gamma02.jurassicworldreborn.common.util.api.CleanableItem;
import net.gamma02.jurassicworldreborn.common.util.slot.CustomSlot;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;

public class CleanerMenu extends AbstractContainerMenu {


    private Inventory playerInv;

    private CleanerBlockEntity instance;

    private final Container cleaningStation;
    private final ContainerData cleaningStationData;

    public static final int INPUT_SLOT = 0;
    public static final int BUCKET_SLOT = 1;
    public static final int OUTPUT_SLOT_END = 5;

    public boolean isInstanceNull(){
        return instance == null;
    }


    public CleanerMenu(int pContainerId, Inventory inventory, Container cleaningStation, ContainerData stationData) {

        super(ModBlockEntities.ModScreenTypes.CleanerMenuType.get(), pContainerId);


        this.cleaningStation = cleaningStation;
        this.cleaningStationData = stationData;

        this.addSlot(new CustomSlot(cleaningStation, 0, 56, 17, (stack) -> stack.getItem() instanceof CleanableItem));
        this.addSlot(new FluidSlot(cleaningStation, 1, 56, 53));

        int slotIncrement = 18;

        this.addSlot(new Slot(cleaningStation, 2, 108, 26));
        this.addSlot(new Slot(cleaningStation, 3, 108+slotIncrement, 26));
        this.addSlot(new Slot(cleaningStation, 4, 108+slotIncrement*2, 26));
        this.addSlot(new Slot(cleaningStation, 5, 108, 26+slotIncrement));
        this.addSlot(new Slot(cleaningStation, 6, 108+slotIncrement, 26+slotIncrement));
        this.addSlot(new Slot(cleaningStation, 7, 108+slotIncrement*2, 26+slotIncrement));



        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
        }


        this.addDataSlots(this.cleaningStationData);

        this.playerInv = inventory;

    }

    public int getAmountOfFluid(){
        return this.cleaningStationData.get(0);

//        return 0;
    }
    public boolean isCleaning(){
        if(this.instance != null)
            return this.instance.isCleaning();
        else if(this.cleaningStationData.get(1) != 0){
            return true;
        }
        return false;
    }

    public int getProgress(){
        if(this.instance != null)
            return (int) Math.ceil((this.instance.getProgress() * ( 22.0 / 200 ) ));/*or 24 idk which but if it doesn't work that's why lol*/
        else{
            return (int) Math.ceil((this.cleaningStationData.get(1) * ( 22.0 / 200 ) ));
        }
    }

    public CleanerMenu(int id, Inventory inventory) {
        this(id, inventory, new SimpleContainer(8), new SimpleContainerData(2));
    }


    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex < this.cleaningStation.getContainerSize()) {
                if (!this.moveItemStackTo(itemstack1, this.cleaningStation.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.cleaningStation.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    public void setInstance(CleanerBlockEntity entity){
        this.instance = entity;
    }


    public static class FluidSlot extends Slot{

        public FluidSlot(Container pContainer, int pSlot, int pX, int pY) {
            super(pContainer, pSlot, pX, pY);
        }

        @Override
        public boolean mayPlace(ItemStack pStack) {
            return pStack.getItem() instanceof BucketItem;
        }


    }


    public static class EmptyFluidSlot extends Slot{

        public EmptyFluidSlot(Container pContainer, int pSlot, int pX, int pY) {
            super(pContainer, pSlot, pX, pY);
        }

        @Override
        public boolean mayPlace(ItemStack pStack) {
            return false;
        }


    }
}
