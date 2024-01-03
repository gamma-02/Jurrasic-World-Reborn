package net.gamma02.jurassicworldreborn.common.items.genetics;

import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.util.LangUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class DNAItem extends DNAContainerItem {


    public DNAItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Component getName(ItemStack pStack) {
        return Component.literal(Component.translatable(ForgeRegistries.ITEMS.getKey(this).getPath() + ".name").getString().replace("{dino}", LangUtil.getDinoName(this.getDinosaur(pStack)).getString()));
    }

    public Dinosaur getDinosaur(ItemStack stack) {
        Dinosaur dinosaur = Dinosaur.getDinosaurByName(stack.getTag() != null ? stack.getTag().getString("DinosaurName") : null);

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