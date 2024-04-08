package net.gamma02.jurassicworldreborn.common.blocks.entities.cultivator;

import net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNACombinatorHybridizer.DNACombinatorHybridizerMenu;
import net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNASequencer.DNASequencerMenu;
import net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNASynthesizer.DNASynthesizerMenu;
import net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities;
import net.gamma02.jurassicworldreborn.common.blocks.entities.cleaner.CleanerMenu;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.items.genetics.SyringeItem;
import net.gamma02.jurassicworldreborn.common.util.slot.CustomSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class CultivatorMenu extends AbstractContainerMenu {

    private Container owner;
    private ContainerData ownerData;

    public CultivatorMenu(int pContainerId, Inventory playerInventory) {
        this(pContainerId, new SimpleContainer(5), new SimpleContainerData(10), playerInventory);
    }

    public CultivatorMenu(int containerID, Container owner, ContainerData ownerData, Inventory playerInventory){
        super(ModBlockEntities.ModScreenTypes.CULTIVATOR_MENU_TYPE.get(), containerID);
        this.owner = owner;
        this.ownerData = ownerData;

        this.addSlot(new CustomSlot(this.owner, 0, 122, 44, ((pStack) -> pStack.getItem() instanceof SyringeItem syringe && syringe.getDinosaur(pStack).getBirthType() == Dinosaur.BirthType.LIVE_BIRTH), 1));
        this.addSlot(new CustomSlot(this.owner, 1, 208, 20, ((stack) -> CultivatorBlockEntity.FoodNutrients.NUTRIENTS.containsKey(stack.getItem()))));
        this.addSlot(new CleanerMenu.FluidSlot(this.owner, 2, 12, 20));
        this.addSlot(new CustomSlot(this.owner, 3, 12, 68, stack -> false));


        addPlayerInventory(playerInventory, this::addSlot);

        this.addDataSlots(this.ownerData);

    }

    public static void addPlayerInventory(Inventory pPlayerInventory, Function<Slot, Slot> adderFunc){

        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                adderFunc.apply(new Slot(pPlayerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 106 + l * 18));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            adderFunc.apply((new Slot(pPlayerInventory, i1, 8 + i1 * 18, 164)));
        }
    }


    public int getField(int index){
        return this.ownerData.get(index);
    }

    public BlockPos getBlockPos(){
        return new BlockPos(this.getField(7), this.getField(8), this.getField(9));
    }

    public void setField(int index, int value){
        this.ownerData.set(index, value);
    }



    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
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
    public boolean stillValid(@NotNull Player pPlayer) {
        return owner.stillValid(pPlayer);
    }
}
