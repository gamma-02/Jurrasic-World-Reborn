package net.gamma02.jurassicworldreborn.common.blocks.entities.Embryoncis.EmbryonicMachine;

import net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNASequencer.DNASequencerMenu;
import net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities;
import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.gamma02.jurassicworldreborn.common.items.genetics.DNAItem;
import net.gamma02.jurassicworldreborn.common.items.genetics.PlantDNAItem;
import net.gamma02.jurassicworldreborn.common.util.slot.CustomSlot;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

public class EmbryonicMachineMenu extends AbstractContainerMenu {
    private Container owner;
    private ContainerData ownerData;
    public EmbryonicMachineMenu(int pContainerId, Inventory playerInv) {
        this(pContainerId, new SimpleContainer(7), new SimpleContainerData(1), playerInv);
    }

    public EmbryonicMachineMenu(int containerId, Container owner, ContainerData ownerData, Inventory playerInv){
        super(ModBlockEntities.ModScreenTypes.EMBRYONIC_MACHINE_MENU_TYPE.get(), containerId);
        this.owner = owner;
        this.ownerData = ownerData;

        this.addSlot(new CustomSlot(this.owner, 0, 24, 49, (stack) -> stack.getItem() instanceof DNAItem || stack.getItem() instanceof PlantDNAItem));
        this.addSlot(new CustomSlot(this.owner, 1, 50, 49, (stack) -> stack.getItem() == ModItems.PLANT_CELLS_PETRI_DISH.get() || stack.getItem() == ModItems.PETRI_DISH.get()));
        this.addSlot(new CustomSlot(this.owner, 2, 50, 13, stack -> stack.getItem() == ModItems.EMPTY_SYRINGE.get()));

        this.addSlot(new CustomSlot(this.owner, 3, 119, 26, stack -> false));
        this.addSlot(new CustomSlot(this.owner, 5, 119, 44, stack -> false));
        this.addSlot(new CustomSlot(this.owner, 4, 137, 26, stack -> false));
        this.addSlot(new CustomSlot(this.owner, 6, 137, 44, stack -> false));

        DNASequencerMenu.addPlayerInventory(playerInv, this::addSlot);

        this.addDataSlots(this.ownerData);

    }

    public int getField(int i){
        return this.ownerData.get(i);
    }

    public void setFiled(int i, int v){
        this.ownerData.set(i, v);
    }



    @Override
    public boolean stillValid(Player pPlayer) {
        return this.owner.stillValid(pPlayer);
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
}
