package net.gamma02.jurassicworldreborn.common.blocks.entities.cleaner;

import net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.block.entity.BlockEntity;

public class CleanerMenu extends AbstractContainerMenu {


    private Inventory playerInv;

    private CleanerBlockEntity instance;

    public boolean isInstanceNull(){
        return instance == null;
    }


    public CleanerMenu(int pContainerId, Inventory playerInv, BlockEntity temp) {
        super(ModBlockEntities.modScreenTypes.CleanerScreenType.get(), pContainerId);
        if(temp instanceof CleanerBlockEntity instance) {

            for(int i = 0; i < 3; ++i) {
                for(int j = 0; j < 9; ++j) {
                    this.addSlot(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
                }
            }

            for(int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(playerInv, k, 8 + k * 18, 142));
            }

            this.addSlot(new Slot(instance, 0, 56, 17));
            this.addSlot(new Slot(instance, 1, 56, 53));

            this.addSlot(new Slot(instance, 2, 124, 24));
            this.addSlot(new Slot(instance, 3, 124+9, 24));
            this.addSlot(new Slot(instance, 4, 124+18, 24));
            this.addSlot(new Slot(instance, 5, 124, 24+9));
            this.addSlot(new Slot(instance, 6, 124+9, 24+9));
            this.addSlot(new Slot(instance, 7, 124+18, 24+9));

            this.instance = instance;
            this.playerInv = playerInv;
        }

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


        super(ModBlockEntities.modScreenTypes.CleanerScreenType.get(), id);
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
