package net.gamma02.jurassicworldreborn.common.blocks.entities.grinder;

import net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities;
import net.gamma02.jurassicworldreborn.common.util.api.GrindableItem;
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

    private final Container fossilGrinder;

    private final ContainerData fossilGrinderData;

    public FossilGrinderMenu(int pContainerId, Inventory playerInv) {
        this(pContainerId, playerInv, new SimpleContainer(12), new SimpleContainerData(1));
    }


    public FossilGrinderMenu(int pContainerId, Inventory playerInventory, Container fossilGrinder, ContainerData grinderData) {
        super(ModBlockEntities.ModScreenTypes.FOSSIL_GRINDER_MENU_TYPE.get(), pContainerId);

        this.fossilGrinder = fossilGrinder;
        this.fossilGrinderData = grinderData;


        this.addSlot(new GrindableItemSlot(fossilGrinder, 0, 23, 26));
        this.addSlot(new GrindableItemSlot(fossilGrinder, 3, 23, 44));
        this.addSlot(new GrindableItemSlot(fossilGrinder, 1, 41, 26));
        this.addSlot(new GrindableItemSlot(fossilGrinder, 4, 41, 44));
        this.addSlot(new GrindableItemSlot(fossilGrinder, 2, 59, 26));
        this.addSlot(new GrindableItemSlot(fossilGrinder, 5, 59, 44));

        this.addSlot(new CustomSlot(fossilGrinder, 6, 108, 26, stack -> false));
        this.addSlot(new CustomSlot(fossilGrinder, 9, 108, 44, stack -> false));
        this.addSlot(new CustomSlot(fossilGrinder, 7, 126, 26, stack -> false));
        this.addSlot(new CustomSlot(fossilGrinder, 10, 126, 44, stack -> false));
        this.addSlot(new CustomSlot(fossilGrinder, 8, 144, 26, stack -> false));
        this.addSlot(new CustomSlot(fossilGrinder, 11, 144, 44, stack -> false));

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
