package net.gamma02.jurassicworldreborn.common.genetics;

import net.gamma02.jurassicworldreborn.common.util.LangUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class PlantDNA {
    private ResourceLocation plant;
    private int quality;

    public PlantDNA(ResourceLocation plant, int quality) {
        this.plant = plant;
        this.quality = quality;
    }

    public static PlantDNA fromStack(ItemStack stack) {
        return readFromNBT(stack.getTag());
    }

    public static PlantDNA readFromNBT(CompoundTag tag) {
        if(tag == null)
            return null;
        if(!tag.contains("DNA"))
            return null;
        CompoundTag nbt = tag.getCompound("DNA");

        return new PlantDNA(new ResourceLocation(nbt.getString("Plant")), nbt.getInt("DNAQuality"));
    }

    public void writeToNBT(CompoundTag tag) {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("DNAQuality", this.quality);

//        CompoundTag plant = new CompoundTag();
//        plant.putString("id", );
        nbt.putString("Plant", this.plant.toString());
        nbt.putString("StorageId", "PlantDNA");
        tag.put("DNA", tag);
    }

    public int getDNAQuality() {
        return this.quality;
    }

    public ResourceLocation getPlant() {
        return this.plant;
    }

    public void addInformation(ItemStack stack, List<Component> tooltip) {
        tooltip.add( Component.literal(Component.translatable("lore.plant.name").getString().replace("{plant}", ForgeRegistries.BLOCKS.getDelegateOrThrow(this.plant).get().getName().getString())).withStyle(ChatFormatting.DARK_AQUA));

        ChatFormatting formatting;

        if (this.quality > 75) {
            formatting = ChatFormatting.GREEN;
        } else if (this.quality > 50) {
            formatting = ChatFormatting.YELLOW;
        } else if (this.quality > 25) {
            formatting = ChatFormatting.GOLD;
        } else if (this.quality == -1) {
            formatting = ChatFormatting.AQUA;
        } else {
            formatting = ChatFormatting.RED;
        }

        String qualityString = Component.translatable("lore.dna_quality.name").getString();
        String[] splitQuality = qualityString.split("\\{[a-z]*\\}");//regex go bRRRRRRRRRRRRRR
        Component quality = LangUtil.getFormattedQuality(this.quality);
        tooltip.add(Component.literal(splitQuality[0]).append(quality).append(splitQuality[1]).withStyle(formatting));
    }

//    public int getMetadata() {
//        return this.plant;
//    }
}