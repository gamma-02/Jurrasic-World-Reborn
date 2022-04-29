package net.gamma02.jurassicworldreborn.common.blocks.machines.cleaner;

import net.gamma02.jurassicworldreborn.common.blocks.machines.modBlockEntities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;

import static net.gamma02.jurassicworldreborn.Jurassicworldreborn.resource;

public class CleanerMenu extends AbstractContainerMenu {


    private Inventory playerInv;

    private CleanerBlockEntity instance;

    public boolean isInstanceNull(){
        return instance == null;
    }


    public CleanerMenu(int pContainerId, Inventory playerInv, CleanerBlockEntity instance) {
        super(modBlockEntities.modScreenTypes.CleanerScreenType.get(), pContainerId);
        for(int l = 0; l < 3; ++l) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(playerInv, k + l * 9 + 9, 8 + k * 18, l * 18 + 51));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInv, i1, 8 + i1 * 18, 109));
        }

        this.addSlot(new Slot(instance, 0, 54, 16));
        this.addSlot(new Slot(instance, 1, 54, 51));
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 3; j++){
                this.addSlot(new Slot(instance, i+j+2, 124+9*j, 24+9*i));
            }
        }
        this.instance = instance;
        this.playerInv = playerInv;

    }

    public int getAmountOfFluid(){
        if(this.instance != null)
            return this.instance.fluid.getAmount();
        return 0;
    }
    public boolean isCleaning(){
        if(this.instance != null)
            return this.instance.isCleaning();
        return false;
    }

    public int getProgress(){
        if(this.instance != null)
            return this.instance.getProgress()/22/*or 24 idk which but if it doesn't work that's why lol*/;
        return 0;
    }

    public CleanerMenu(int id, Inventory inventory) {


        super(modBlockEntities.modScreenTypes.CleanerScreenType.get(), id);
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
        }


        this.playerInv = inventory;
    }


    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    public void setInstance(CleanerBlockEntity entity){
        this.instance = entity;
    }
}
