package net.gamma02.jurassicworldreborn.common.items;

import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.items.misc.ActionFigureItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TabHandler {
    public static ArrayList<CreativeModeTab> tabs = new ArrayList<>();


    public static HashMap<String, ArrayDeque<Item>> SCROLLING_TAB_ITEMS = new HashMap<>();

    public static Supplier<ItemStack> currentDisplayFigure = ((Supplier<ItemStack>)() -> {

     var a = ModItems.DISPLAY_BLOCK.get().getDefaultInstance();
     if( a == null)
     {
         return Items.AIR.getDefaultInstance();
     }
     return a;
    });

    public static final CreativeModeTab ITEMS = makeTab("jurassicworldreborn.items", () -> ModItems.APHID_AMBER.get(), () -> ModItems.MOSQUITO_AMBER.get());

    public static final CreativeModeTab BLOCKS = makeTab("jurassicworldreborn.blocks", () -> ModItems.GYPSUM_BRICKS.get());

    public static final CreativeModeTab DECORATIONS = makeTab("jurassicworldreborn.decorations", () -> ModItems.DISPLAY_BLOCK.get());

    public static final CreativeModeTab DNA = makeTab("jurassicworldreborn.dna", () -> ModItems.DNA.get());

    public static final CreativeModeTab FOSSILS = makeTab("jurassicworldreborn.fossils", () -> ModItems.FAUNA_FOSSIL_BLOCK.get());

    public static CreativeModeTab makeTab(String name, Supplier<Item>... icon){
        CreativeModeTab tab = new CreativeModeTab(name){

            @Override
            public ItemStack getIconItem() {//this bit in particular makes the icon scroll/change between the specified items, or registered dinosaur display cases
                if(!SCROLLING_TAB_ITEMS.containsKey(name) && !name.equals("jurassicworldreborn.decorations")) {
                    return super.getIconItem();
                } else if(name.equals("jurassicworldreborn.decorations")) {
                    Calendar c = Calendar.getInstance();
                    if(c.get(Calendar.SECOND) % 3 == 0){
                        int i = Dinosaur.DINOS.indexOf(Dinosaur.getDinosaurByName(ActionFigureItem.getDinosaurID(currentDisplayFigure.get())));
                        ActionFigureItem.setDinosaurID(currentDisplayFigure.get(), Dinosaur.DINOS.get(i).getName());
                    }
                    return currentDisplayFigure.get();
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
                    return icon[0].get().getDefaultInstance();
                }else{
                    SCROLLING_TAB_ITEMS.put(name, Arrays.stream(icon).map(Supplier::get).collect(Collectors.toCollection(ArrayDeque::new)));
                    return icon[0].get().getDefaultInstance();
                }
            }
        };
        tabs.add(tab);
        return tab;
    }

}
