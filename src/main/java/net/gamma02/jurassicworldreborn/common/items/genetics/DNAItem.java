package net.gamma02.jurassicworldreborn.common.items.genetics;

import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.gamma02.jurassicworldreborn.common.items.TabHandler;
import net.gamma02.jurassicworldreborn.common.util.LangUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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

    @Override
    public void fillItemCategory(CreativeModeTab pCategory, NonNullList<ItemStack> pItems) {
        if((pCategory == TabHandler.DNA || pCategory == CreativeModeTab.TAB_SEARCH)) {
            if(pItems.stream().anyMatch((stack) -> stack.is(this)))
                return;



            var eggItem = ModItems.DINOSAUR_DNA.get(dinosaur);
            if (eggItem != null) {
                ItemStack defaultDNAItem = eggItem.get().getDefaultInstance();

                defaultDNAItem.getOrCreateTag().putBoolean("isCreative", true);


                pItems.add(defaultDNAItem);
            }

        }else {

            super.fillItemCategory(pCategory, pItems);
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> lore, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, lore, pIsAdvanced);
    }
}