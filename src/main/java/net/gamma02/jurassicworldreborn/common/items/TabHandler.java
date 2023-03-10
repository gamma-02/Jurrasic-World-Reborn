package net.gamma02.jurassicworldreborn.common.items;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

public class TabHandler {


    public static HashMap<String, ArrayDeque<Item>> SCROLLING_TAB_ITEMS = new HashMap<>();

    public static final CreativeModeTab ITEMS = makeTab("rebornmod.items", ModItems.APHID_AMBER.get(), ModItems.MOSQUITO_AMBER.get());

    public static final CreativeModeTab BLOCKS = makeTab("rebornmod.blocks", ModItems.GYPSUM_BRICKS.get());

    public static final CreativeModeTab DECORATIONS = makeTab("rebornmod.decorations", Items.AIR/*todo: DisplayBlock*/);

    public static CreativeModeTab makeTab(String name, Item... icon){
        CreativeModeTab tab = new CreativeModeTab(name){

            @Override
            public ItemStack getIconItem() {//this makes the icon scroll/change between the specified items.
                if(!SCROLLING_TAB_ITEMS.containsKey(name)) {
                    return super.getIconItem();
                } else{
                    Calendar c = Calendar.getInstance();
                    if(c.get(Calendar.SECOND) % 3 == 0){
                        Item i = SCROLLING_TAB_ITEMS.get(name).poll();
                        SCROLLING_TAB_ITEMS.get(name).addLast(i);
                        return i.getDefaultInstance();
                    }
                }
                return super.getIconItem();
            }

            @Override
            public ItemStack makeIcon() {
                if(icon.length == 1) {
                    return icon[0].getDefaultInstance();
                }else{
                    SCROLLING_TAB_ITEMS.put(name, Arrays.stream(icon).collect(Collectors.toCollection(ArrayDeque::new)));
                    return icon[0].getDefaultInstance();
                }
            }
        };
        tabs.add(tab);
        return tab;
    }

    public static ArrayList<CreativeModeTab> tabs = new ArrayList<>();
}
