package net.gamma02.jurassicworldreborn.common.blocks.entities.grinder;

import net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities;
import net.gamma02.jurassicworldreborn.common.items.misc.GrindableItem;
import net.gamma02.jurassicworldreborn.common.util.slot.CustomSlot;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FossilGrinderMenu extends AbstractContainerMenu {

    private Container fossilGrinder;

    private ContainerData fossilGrinderData;

    public FossilGrinderMenu(int pContainerId, Inventory playerInv) {
        this(pContainerId, playerInv, new SimpleContainer(12), new SimpleContainerData(1));
    }


    public FossilGrinderMenu(int pContainerId, Inventory playerInventory, Container fossilGrinder, ContainerData grinderData) {
        super(ModBlockEntities.modScreenTypes.FOSSIL_GRINDER_MENU_TYPE.get(), pContainerId);

        this.fossilGrinder = fossilGrinder;
        this.fossilGrinderData = grinderData;




        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 2; column++) {
                this.addSlot(new GrindableItemSlot(fossilGrinder, row + (column * 3), row * 18 + 23, column * 18 + 26));
            }
        }

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 2; column++) {
                this.addSlot(new CustomSlot(this.fossilGrinder, row + (column * 3) + 6, row * 18 + 93 + 15, column * 18 + 26, stack -> false));
            }
        }

        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInventory, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
            }
        }

        for (int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(playerInventory, column, 8 + column * 18, 142));
        }

        this.addDataSlots(grinderData);
    }

    public int getField(int index){
        return this.fossilGrinderData.get(index);
    }
    public void setField(int index, int value){
        this.fossilGrinderData.set(index, value);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int slotIndex) {
        ItemStack transferred = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);

        int otherSlots = this.slots.size() - 36;

        if (slot.hasItem()) {
            ItemStack current = slot.getItem();
            transferred = current.copy();

            if (slotIndex < otherSlots) {
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
    public boolean stillValid(@NotNull Player pPlayer) {
        return this.fossilGrinder.stillValid(pPlayer);
    }



    public static class GrindableItemSlot extends Slot {
        public GrindableItemSlot(Container inventory, int slotIndex, int xPosition, int yPosition) {
            super(inventory, slotIndex, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(@NotNull ItemStack stack) {
            GrindableItem grindableItem = GrindableItem.getGrindableItem(stack);
            return grindableItem != null && grindableItem.isGrindable(stack);
        }

    }
}
