package net.gamma02.jurassicworldreborn.common.items.genetics;

import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.util.LangUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class DNAItem extends DNAContainerItem {

    public final Dinosaur dinosaur;

    public DNAItem(Properties pProperties, Dinosaur dino) {
        super(pProperties);
        this.dinosaur = dino;
    }

    @Override
    public Component getName(ItemStack pStack) {
        return LangUtil.replaceWithDinoName(this.dinosaur, "item.jurassicworldreborn.dna");
    }

    public Dinosaur getDinosaur(ItemStack stack) {
        Dinosaur dinosaur = null;

        if(stack.getItem() instanceof DNAItem dna){
            dinosaur = dna.dinosaur;
        }

        if (dinosaur == null) {
            dinosaur = Dinosaur.EMPTY;
        }

        return dinosaur;
    }

    @Override
    public int getContainerId(ItemStack stack) {
        return Dinosaur.DINOS.indexOf(this.getDinosaur(stack));
    }


}