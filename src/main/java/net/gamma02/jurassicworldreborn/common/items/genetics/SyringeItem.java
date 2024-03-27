package net.gamma02.jurassicworldreborn.common.items.genetics;

import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.util.LangUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class SyringeItem extends DNAContainerItem {

    private final Dinosaur dino;

    public SyringeItem(Properties pProperties, Dinosaur dinosaur) {
        super(pProperties);
        this.dino = dinosaur;
        this.appendTooltip = false;
    }


    @Override
    public Component getName(ItemStack pStack) {
        return LangUtil.replaceWithDinoName(this.getDinosaur(pStack), "item.jurassicworldreborn.syringe");
    }

    public Dinosaur getDinosaur(ItemStack stack){
        if(stack.getItem() == this){
            return this.dino;
        }

        if(stack.getItem() instanceof SyringeItem i){
            return i.getDinosaur(stack);
        }

        return Dinosaur.EMPTY;
    }
}
