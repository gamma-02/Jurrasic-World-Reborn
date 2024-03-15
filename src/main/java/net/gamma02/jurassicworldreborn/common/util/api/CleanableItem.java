package net.gamma02.jurassicworldreborn.common.util.api;

import net.gamma02.jurassicworldreborn.common.items.misc.RebornIngredientItem;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface CleanableItem extends RebornIngredientItem {

    static CleanableItem getCleanableItem(ItemStack stack) {
        if (stack != null) {
            Item item = stack.getItem();

//            if (item instanceof BlockItem blockItem) { ...Why was this used? it would be way better to jsut use the item...
//                Block block = blockItem.getBlock();
//
//                if (block instanceof CleanableItem) {
//                    return (CleanableItem) block;
//                }
//            }
            if (item instanceof CleanableItem i) {
                return i;
            }
        }

        return null;
    }

    static boolean isCleanableItem(ItemStack stack) {
        return getCleanableItem(stack) != null;
    }

    boolean isCleanable(ItemStack stack);

    ItemStack getCleanedItem(ItemStack stack, RandomSource random);

}
