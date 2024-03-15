package net.gamma02.jurassicworldreborn.common.items.genetics;

import net.gamma02.jurassicworldreborn.common.genetics.GeneticsHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DNAContainerItem extends Item {
    public DNAContainerItem(Properties pProperties) {
        super(pProperties);
    }

    public int getContainerId(ItemStack stack) {//todo:what
        return 0;
    }


    public static int getDNAQuality(boolean creative, ItemStack stack) {
        int quality = creative ? 100 : 0;

        CompoundTag nbt = stack.getOrCreateTag();


        if (nbt.contains("DNAQuality")) {
            quality = nbt.getInt("DNAQuality");
        } else {
            nbt.putInt("DNAQuality", quality);
        }

        stack.setTag(nbt);

        return quality;
    }

    public static String getGeneticCode(RandomSource player, ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();

        String genetics = GeneticsHelper.randomGenetics(player);

        if (nbt.contains("Genetics")) {
            genetics = nbt.getString("Genetics");
        } else {
            nbt.putString("Genetics", genetics);
        }

        stack.setTag(nbt);

        return genetics;
    }

    public static boolean hasGeneticCode(ItemStack stack){
        return stack.getOrCreateTag().contains("Genetics");
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> lore, TooltipFlag pIsAdvanced) {
        ChatFormatting colour;

        boolean fromCreativeMenu = false;

        if(pStack.getOrCreateTag().contains("isCreative"))
            fromCreativeMenu = pStack.getOrCreateTag().getBoolean("isCreative");

        int quality = getDNAQuality(fromCreativeMenu, pStack);

        RandomSource rand = RandomSource.create();
        if(pLevel != null)
            rand = pLevel.getRandom();

        if (quality > 75) {
            colour = ChatFormatting.GREEN;
        } else if (quality > 50) {
            colour = ChatFormatting.YELLOW;
        } else if (quality > 25) {
            colour = ChatFormatting.GOLD;
        } else {
            colour = ChatFormatting.RED;
        }
        lore.add(Component.literal(Component.translatable("lore.dna_quality").getString().formatted( Integer.toString(quality), "%")).withStyle(colour));
        if(hasGeneticCode(pStack))
            lore.add(Component.literal(Component.translatable("lore.genetic_code").getString().formatted(getGeneticCode(rand, pStack))).withStyle(ChatFormatting.BLUE));
    }

}
