package net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNACombinatorHybridizer;

import net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities;
import net.gamma02.jurassicworldreborn.common.util.slot.CustomSlot;
import net.gamma02.jurassicworldreborn.common.util.slot.StorageSlot;
import net.gamma02.jurassicworldreborn.common.util.slot.ToggleableSlot;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DNACombinatorHybridizerMenu extends AbstractContainerMenu {

    private final Container owner;
    private final ContainerData ownerData;

    private final Inventory playerInventory;

    private ArrayList<Slot> combinatorSlots = new ArrayList<>();
    private ArrayList<Slot> hybridizerSlots = new ArrayList<>();

    public static int HYBRIDIZER_INPUT_Y = 17;

    public static int HYBRIDIZER_OUTPUT_Y = 52;

    public static int COMBINATOR_INPUT_Y = 13;

    public static int COMBINATOR_OUTPUT_Y = 60;

    public DNACombinatorHybridizerMenu(int pContainerId, Inventory playerInv) {
        this(pContainerId, playerInv, new SimpleContainer(12), new SimpleContainerData(6));
    }

    public DNACombinatorHybridizerMenu(int pContainerId, Inventory playerInv, Container hybridizer, ContainerData hybridizerData){
        super(ModBlockEntities.modScreenTypes.COMBINATOR_MENU_TYPE.get(), pContainerId);

        this.owner = hybridizer;
        this.ownerData = hybridizerData;
        boolean mode = ownerData.get(2) == 1;


        this.hybridizerSlots.add(this.addSlot(new StorageSlot(this.owner, 0, 10, 17, true).activeBuilder(!mode)));
        this.hybridizerSlots.add(this.addSlot(new StorageSlot(this.owner, 1, 30, 17, true).activeBuilder(!mode)));
        this.hybridizerSlots.add(this.addSlot(new StorageSlot(this.owner, 2, 50, 17, true).activeBuilder(!mode)));
        this.hybridizerSlots.add(this.addSlot(new StorageSlot(this.owner, 3, 70, 17, true).activeBuilder(!mode)));
        this.hybridizerSlots.add(this.addSlot(new StorageSlot(this.owner, 4, 90, 17, true).activeBuilder(!mode)));
        this.hybridizerSlots.add(this.addSlot(new StorageSlot(this.owner, 5, 110, 17, true).activeBuilder(!mode)));
        this.hybridizerSlots.add(this.addSlot(new StorageSlot(this.owner, 6, 130, 17, true).activeBuilder(!mode)));
        this.hybridizerSlots.add( this.addSlot(new StorageSlot(this.owner, 7, 150, 17, true).activeBuilder(!mode)));
        this.hybridizerSlots.add(this.addSlot(new CustomSlot(this.owner, 10, 80, 56, stack -> false).activeBuilder(!mode)));

        this.combinatorSlots.add(this.addSlot(new StorageSlot(this.owner, 8, 55, 13, true).activeBuilder(mode)));
        this.combinatorSlots.add(this.addSlot(new StorageSlot(this.owner, 9, 105, 13, true).activeBuilder(mode)));
        this.combinatorSlots.add(this.addSlot(new CustomSlot(this.owner, 11, 81, 60, stack -> false).activeBuilder(mode)));



        this.updateSlots(mode);


        int i;

        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInv, i, 8 + i * 18, 142));
        }

        this.addDataSlots(this.ownerData);

        this.playerInventory = playerInv;




    }

    public List<ToggleableSlot> getHybridizerCombinatorSlots(boolean mode){
        ArrayList<ToggleableSlot> slots = new ArrayList<>();
        if(mode){
            for(Slot slot : this.combinatorSlots){
                slots.add(slot instanceof ToggleableSlot ? (ToggleableSlot) slot : null );
            }
        }else{
            for(Slot slot : this.hybridizerSlots){
                slots.add(slot instanceof ToggleableSlot ? (ToggleableSlot) slot : null );
            }
        }
        return slots;
    }

    public ArrayList<Slot> getHybridizerSlots(){
        return this.hybridizerSlots;
    }

    public ArrayList<Slot> getCombinatorSlots() {
        return this.combinatorSlots;
    }

    public void updateSlots(boolean mode) {
//        this.slots.clear();

        List<ToggleableSlot> slotsForMode = this.getHybridizerCombinatorSlots(mode);
        List<ToggleableSlot> otherSlots = this.getHybridizerCombinatorSlots(!mode);

        slotsForMode.parallelStream().forEach((slot) -> slot.setActive(true));
        otherSlots.parallelStream().forEach((slot) -> slot.setActive(false));


    }



    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        ItemStack transferred = ItemStack.EMPTY;
        Slot slot = this.slots.get(slotIndex);

        int otherSlots = this.slots.size() - 36;

        if (slot != null && slot.hasItem()) {
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

    public int getField(int index){
        return this.ownerData.get(index);
    }
    public void setField(int index, int value){
        this.ownerData.set(index, value);
    }



    @Override
    public boolean stillValid(Player pPlayer) {
        return this.owner.stillValid(pPlayer);
    }


    public boolean getMode() {
        return this.ownerData.get(2) == 1;
    }

    public void setMode(boolean mode) {
        this.ownerData.set(2, mode ? 1 : 0);
    }

    @Override
    public void broadcastChanges() {
//        System.out.println("called");
        super.broadcastChanges();
    }
}
