package net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNAExtractor;

import net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNASequencer.DNASequencerMenu;
import net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities;
import net.gamma02.jurassicworldreborn.common.items.Food.DinosaurMeatItem;
import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.gamma02.jurassicworldreborn.common.util.slot.CustomSlot;
import net.gamma02.jurassicworldreborn.common.util.slot.StorageSlot;
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

public class DNAExtractorMenu extends AbstractContainerMenu {

    private final Container dnaExtractor;
    private final ContainerData dnaExtractorData;
    public DNAExtractorMenu(int pContainerId, Inventory playerInventory) {
        this(pContainerId, new SimpleContainer(6), new SimpleContainerData(1), playerInventory);
    }
    public DNAExtractorMenu(int containerId, Container dnaSequencer, ContainerData sequencerData, Inventory playerInventory){
        super(ModBlockEntities.modScreenTypes.DNA_EXTRACTOR_MENU_TYPE.get(), containerId);

        this.dnaExtractor = dnaSequencer;
        this.dnaExtractorData = sequencerData;

        this.addSlot(new CustomSlot(this.dnaExtractor, 0, 55, 26, stack -> stack.getItem() == ModItems.MOSQUITO_AMBER.get() || stack.getItem() == ModItems.APHID_AMBER.get() || stack.getItem() == ModItems.SEA_LAMPREY.get() ||stack.getItem() == ModItems.FROZEN_LEECH_ITEM.get() || (stack.getItem() instanceof DinosaurMeatItem)));
        this.addSlot(new StorageSlot(this.dnaExtractor, 1, 55, 47, false));
        this.addSlot(new CustomSlot(this.dnaExtractor, 2, 108, 28, stack -> false));
        this.addSlot(new CustomSlot(this.dnaExtractor, 3, 126, 28, stack -> false));
        this.addSlot(new CustomSlot(this.dnaExtractor, 4, 108, 46, stack -> false));
        this.addSlot(new CustomSlot(this.dnaExtractor, 5, 126, 46, stack -> false));

        DNASequencerMenu.addPlayerInventory(playerInventory, this::addSlot);


        this.addDataSlots(this.dnaExtractorData);


    }

    public int getField(int index){
        return this.dnaExtractorData.get(index);
    }

    public void setField(int index, int value){
        this.dnaExtractorData.set(index, value);
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
        return this.dnaExtractor.stillValid(pPlayer);
    }
}
