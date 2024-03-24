package net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNASynthesizer;

import net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities;
import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.gamma02.jurassicworldreborn.common.util.api.SynthesizableItem;
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

import static net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNASequencer.DNASequencerMenu.addPlayerInventory;

public class DNASynthesizerMenu extends AbstractContainerMenu {

    Container DNASynthesizer;
    ContainerData SynthesizerData;

    public DNASynthesizerMenu(int pContainerId, Inventory playerInventory) {
        this(pContainerId, new SimpleContainer(7), new SimpleContainerData(1), playerInventory);
    }
    public DNASynthesizerMenu(int containerId, Container dnaSequencer, ContainerData sequencerData, Inventory playerInventory){
        super(ModBlockEntities.modScreenTypes.DNA_SYNTHESIZER_MENU_TYPE.get(), containerId);

        this.DNASynthesizer = dnaSequencer;
        this.SynthesizerData = sequencerData;


        this.addSlot(new CustomSlot(this.DNASynthesizer, 0, 38, 22, stack -> (SynthesizableItem.getSynthesizableItem(stack) != null && SynthesizableItem.getSynthesizableItem(stack).isSynthesizable(stack))));
        this.addSlot(new CustomSlot(this.DNASynthesizer, 1, 24, 49, stack -> stack.getItem() == ModItems.EMPTY_TEST_TUBE.get()));
        this.addSlot(new CustomSlot(this.DNASynthesizer, 2, 50, 49, stack -> stack.getItem() == ModItems.DNA_NUCLEOTIDES.get()));


        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                this.addSlot(new CustomSlot(this.DNASynthesizer, i + (j * 2) + 3, i * 18 + 119, j * 18 + 26, stack -> false));
            }
        }

        addPlayerInventory(playerInventory, this::addSlot);


        this.addDataSlots(this.SynthesizerData);


    }

    public int getField(int index){
        return this.SynthesizerData.get(index);
    }

    public void setField(int index, int value){
        this.SynthesizerData.set(index, value);
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
        return this.DNASynthesizer.stillValid(pPlayer);
    }
}
