package net.gamma02.jurassicworldreborn.common.util.api;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.Random;

public interface SynthesizableItem extends RebornIngredientItem {
    static SynthesizableItem getSynthesizableItem(ItemStack stack) {
        if (stack != null) {
            Item item = stack.getItem();

            if (item instanceof BlockItem) {
                Block block = ((BlockItem) item).getBlock();

                if (block instanceof SynthesizableItem) {
                    return (SynthesizableItem) block;
                }
            } else if (item instanceof SynthesizableItem) {
                return (SynthesizableItem) item;
            }
        }

        return null;
    }

    static boolean isSynthesizableItem(ItemStack stack) {
        return getSynthesizableItem(stack) != null;
    }

    boolean isSynthesizable(ItemStack stack);

    ItemStack getSynthesizedItem(ItemStack stack, Random/*RandomSource*/ random);
}
