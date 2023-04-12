package net.gamma02.jurassicworldreborn.common.items;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.items.misc.ActionFigureItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

public class TabHandler {


    public static HashMap<String, ArrayDeque<Item>> SCROLLING_TAB_ITEMS = new HashMap<>();

    public static ItemStack currentDisplayFigure = ModItems.DISPLAY_BLOCK.get().getDefaultInstance();

    public static final CreativeModeTab ITEMS = makeTab("rebornmod.items", ModItems.APHID_AMBER.get(), ModItems.MOSQUITO_AMBER.get());

    public static final CreativeModeTab BLOCKS = makeTab("rebornmod.blocks", ModItems.GYPSUM_BRICKS.get());

    public static final CreativeModeTab DECORATIONS = makeTab("rebornmod.decorations", ModItems.DISPLAY_BLOCK.get());

    public static CreativeModeTab makeTab(String name, Item... icon){
        CreativeModeTab tab = new CreativeModeTab(name){

            @Override
            public ItemStack getIconItem() {//this bit in particular makes the icon scroll/change between the specified items, or registered dinosaur display cases
                if(!SCROLLING_TAB_ITEMS.containsKey(name) && !name.equals("rebornmod.decorations")) {
                    return super.getIconItem();
                } else if(name.equals("rebornmod.decorations")) {
                    Calendar c = Calendar.getInstance();
                    if(c.get(Calendar.SECOND) % 3 == 0){
                        int i = Dinosaur.DINOS.indexOf(Dinosaur.getDinosaurByName(ActionFigureItem.getDinosaurID(currentDisplayFigure)));
                        ActionFigureItem.setDinosaurID(currentDisplayFigure, Dinosaur.DINOS.get(i).getName());
                    }
                    return currentDisplayFigure;
                } else{
                    Calendar c = Calendar.getInstance();
                    Item i = SCROLLING_TAB_ITEMS.get(name).getFirst();
                    if(c.get(Calendar.SECOND) % 3 == 0){
                        i = SCROLLING_TAB_ITEMS.get(name).poll();
                        SCROLLING_TAB_ITEMS.get(name).addLast(i);
                    }
//                    SCROLLING_TAB_ITEMS.get(name).addLast(i);
                    return i.getDefaultInstance();
                }
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
