package net.gamma02.jurassicworldreborn.common.blocks.entities.Embryoncis.EmbryoCalcificationMachine;

import net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNASequencer.DNASequencerMenu;
import net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.items.genetics.SyringeItem;
import net.gamma02.jurassicworldreborn.common.util.slot.CustomSlot;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class EmbryoCalcificationMachineMenu extends AbstractContainerMenu {

    private Container owner;

    private ContainerData ownerData;

    public EmbryoCalcificationMachineMenu(int containerID, Inventory playerInventory){
        this(containerID, new SimpleContainer(3), new SimpleContainerData(1), playerInventory);
    }

    public EmbryoCalcificationMachineMenu(int containerID, Container owner, ContainerData ownerData, Inventory playerInventory){
        super(ModBlockEntities.ModScreenTypes.EMBRYO_CALCIFICATION_MACHINE_MENU_TYPE.get(), containerID);

        this.owner = owner;

        this.ownerData = ownerData;

        this.addSlot(new CustomSlot(this.owner, 0, 34, 14, (stack) -> stack.getItem() instanceof SyringeItem syringe && syringe.getDinosaur(stack).getBirthType() == Dinosaur.BirthType.EGG_LAYING));
        this.addSlot(new CustomSlot(this.owner, 1, 34, 50, stack -> stack.getItem() == Items.EGG));
        this.addSlot(new CustomSlot(this.owner, 2, 97, 32, stack -> false));

        this.addDataSlots(this.ownerData);

        DNASequencerMenu.addPlayerInventory(playerInventory, this::addSlot);




    }

    public int getField(int index){
        return this.ownerData.get(index);
    }

    public void setField(int index, int v){
        this.ownerData.set(index, v);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack transferred = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        int otherSlots = this.slots.size() - 36;

        if (slot.hasItem()) {
            ItemStack current = slot.getItem();
            transferred = current.copy();

            if (index < otherSlots) {
                if (!this.moveItemStackTo(current, otherSlots, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(current, 0, otherSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (current.getCount() == 0) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return transferred;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return this.owner.stillValid(pPlayer);
    }
}
