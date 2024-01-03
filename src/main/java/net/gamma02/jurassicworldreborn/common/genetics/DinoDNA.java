package net.gamma02.jurassicworldreborn.common.genetics;

import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.util.LangUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class/* Bingo! */ DinoDNA {
    private int quality;
    private String genetics;
    private Dinosaur dinosaur;

    public DinoDNA(Dinosaur dinosaur, int quality, String genetics) {
        this.quality = quality;
        this.genetics = genetics;
        this.dinosaur = dinosaur;
    }

    public static DinoDNA fromStack(ItemStack stack) {
        return readFromNBT(stack.getTag());
    }

    public static DinoDNA readFromNBT(CompoundTag nbt) {
        return nbt == null ? null : new DinoDNA(Dinosaur.getDinosaurByName(nbt.getString("Dinosaur")), nbt.getInt("DNAQuality"), nbt.getString("Genetics"));
    }

    public void writeToNBT(CompoundTag nbt) {
        nbt.putInt("DNAQuality", this.quality);
        nbt.putString("Genetics", this.genetics);
        nbt.putString("StorageId", "DinoDNA");
        nbt.putString("Dinosaur", this.dinosaur.getName());
    }

    public int getDNAQuality() {
        return this.quality;
    }

    public String getGenetics() {
        return this.genetics;
    }

    public void addInformation(ItemStack stack, List<Component> tooltip) {
        tooltip.add( Component.literal(Component.translatable("lore.dinosaur.name").getString().replace("{dino}", LangUtil.getDinoName(this.dinosaur).getString())).withStyle(ChatFormatting.DARK_AQUA));

        ChatFormatting colour;

        if (this.quality > 75) {
            colour = ChatFormatting.GREEN;
        } else if (this.quality > 50) {
            colour = ChatFormatting.YELLOW;
        } else if (this.quality > 25) {
            colour = ChatFormatting.GOLD;
        } else if(this.quality == -1) {
            colour = ChatFormatting.AQUA;
        } else {
            colour = ChatFormatting.RED;
        }
//        tooltip.add(colour + Component.translatable("lore.dna_quality.name").getString().replace("{quality}", LangUtils.getFormattedQuality(this.quality)));
        String qualityString = Component.translatable("lore.dna_quality.name").getString();
        String[] splitQuality = qualityString.split("\\{[a-z]*\\}");//regex go bRRRRRRRRRRRRRR
        Component quality = LangUtil.getFormattedQuality(this.quality);
        tooltip.add(Component.literal(splitQuality[0]).append(quality).append(splitQuality[1]).withStyle(colour));

//        tooltip.add(ChatFormatting.BLUE + LangUtils.translate(LangUtils.LORE.get("genetic_code")).replace("{code}", LangUtils.getFormattedGenetics(this.genetics)));

        String geneticString = Component.translatable("lore.genetic_code.name").getString();
        String[] splitGenetics = geneticString.split("\\{[a-z]*\\}");
        Component genetics = LangUtil.getFormattedGenetics(this.genetics);
        Component formattedQuality;
        if(splitGenetics.length > 1){
            formattedQuality = Component.literal(splitQuality[0]).append(genetics).append(splitGenetics[1]).withStyle(ChatFormatting.BLUE);
        }else{
            formattedQuality = Component.literal(splitQuality[0]).append(genetics).withStyle(ChatFormatting.BLUE);
        }
        tooltip.add(formattedQuality);



    }

    public Dinosaur getDinosaur() {
        return this.dinosaur;
    }

    public int getMetadata() {//fool! old system! do not use this!
        return -1;
    }

    public String getDinoName(){
        return this.dinosaur.getName();
    }
}