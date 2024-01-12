package net.gamma02.jurassicworldreborn.common.items;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.gamma02.jurassicworldreborn.common.blocks.ancientplants.AncientPlantBlock;
import net.gamma02.jurassicworldreborn.common.blocks.entities.paleobale.PaleoBaleBlock;
import net.gamma02.jurassicworldreborn.common.blocks.fossil.AncientCoralBlock;
import net.gamma02.jurassicworldreborn.common.blocks.wood.DynamicWoodTypeRegistry;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.items.genetics.*;
import net.gamma02.jurassicworldreborn.common.items.misc.ActionFigureItem;
import net.gamma02.jurassicworldreborn.common.items.misc.SwarmItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.function.Supplier;

public class ModItems {
    public static void init(){

    }

    public static DeferredRegister<Item> modItems = DeferredRegister.create(ForgeRegistries.ITEMS, Jurassicworldreborn.modid);
    public static DeferredRegister<Item> modBlockItems = modItems;



//    public static RegistryObject<Item> ARAUCARIA_SAPLING = modBlockItems.register("araucaria_sapling", () -> new BlockItem(ModBlocks.AraucariaSapling.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

//    public static RegistryObject<Item> GINKGO_SAPLING = modBlockItems.register("ginkgo_sapling", () -> new BlockItem(ModBlocks.GinkgoSapling.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

//    public static RegistryObject<Item> CALAMITES_SAPLING = modBlockItems.register("calamites_sapling", () -> new BlockItem(ModBlocks.CalamitesSapling.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

//    public static RegistryObject<Item> PHOENIX_SAPLING = modBlockItems.register("phoenix_sapling", () -> new BlockItem(ModBlocks.PhoenixSapling.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

//    public static RegistryObject<Item> PSARONIUS_SAPLING = modBlockItems.register("psarons_sapling", () -> new BlockItem(ModBlocks.PsaroniusSapling.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));
//
    public static RegistryObject<Item> PLASTER_AND_BANDAGE = modItems.register("plaster_and_bandage", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));

    public static RegistryObject<Item> MOSQUITO_AMBER = modItems.register("mosquito_amber", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));
    public static RegistryObject<Item> APHID_AMBER = modItems.register("aphid_amber", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));

    public static RegistryObject<Item> SEA_LAMPREY = modItems.register("sea_lamprey", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));

    public static RegistryObject<Item> ENCASED_FAUNA_FOSSIL = modItems.register("encased_fauna_item", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));

    public static RegistryObject<Item> DEFAULT_BONE = modItems.register("missing_bone", () -> new Item(new Item.Properties()));

    public static final FoodProperties SHARK_MEAT_RAW_PROP = new FoodProperties.Builder().meat().nutrition(5).saturationMod(0.6F).build();
    public static final FoodProperties SHARK_MEAT_COOKED_PROP = new FoodProperties.Builder().meat().nutrition(10).saturationMod(1.2F).build();
    public static final FoodProperties CRAB_MEAT_RAW_PROP = new FoodProperties.Builder().meat().nutrition(1).saturationMod(0.3F).build();
    public static final FoodProperties CRAB_MEAT_COOKED_PROP = new FoodProperties.Builder().meat().nutrition(5).saturationMod(0.6F).build();

    public static final RegistryObject<Item> SHARK_MEAT_RAW = modItems.register("raw_shark_meat", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(SHARK_MEAT_RAW_PROP)));
    public static final RegistryObject<Item> SHARK_MEAT_COOKED = modItems.register("cooked_shark_meat", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(SHARK_MEAT_COOKED_PROP)));
    public static final RegistryObject<Item> CRAB_MEAT_RAW = modItems.register("raw_crab_meat", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(CRAB_MEAT_RAW_PROP)));
    public static final RegistryObject<Item> CRAB_MEAT_COOKED = modItems.register("cooked_crab_meat", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(CRAB_MEAT_COOKED_PROP)));

    public static final FoodProperties GOAT_RAW_PROP = new FoodProperties.Builder().meat().nutrition(3).saturationMod(0.3F).build();
    public static final FoodProperties GOAT_COOKED_PROP = new FoodProperties.Builder().meat().nutrition(6).saturationMod(10.3F).build();
    public static final RegistryObject<Item> GOAT_RAW = modItems.register("raw_goat_meat", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(GOAT_RAW_PROP)));
    public static final RegistryObject<Item> GOAT_COOKED = modItems.register("cooked_goat_meat", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(GOAT_COOKED_PROP)));


    public static final RegistryObject<SwarmItem> PLANKTON = modBlockItems.register("plankton", () -> new SwarmItem(ModBlocks.PLANKTON_SWARM.get(), new Item.Properties().tab(TabHandler.ITEMS)));
    public static final RegistryObject<SwarmItem> KRILL = modBlockItems.register("krill", () -> new SwarmItem(ModBlocks.KRILL_SWARM.get(), new Item.Properties().tab(TabHandler.ITEMS)));

    public static final RegistryObject<RecordItem> JURASSICRAFT_THEME_DISC = modBlockItems.register("disc_jurassicraft_theme", () -> new RecordItem(101/*dont ask*/, () -> SoundHandler.JURASSICRAFT_THEME, new Item.Properties().tab(TabHandler.ITEMS), 4740));
    public static final RegistryObject<RecordItem> TROODONS_AND_RAPTORS_DISC = modBlockItems.register("disc_troodons_and_raptors", () -> new RecordItem(102, () -> SoundHandler.TROODONS_AND_RAPTORS, new Item.Properties().tab(TabHandler.ITEMS), 1760));
    public static final RegistryObject<RecordItem> DONT_MOVE_A_MUSCLE_DISC = modBlockItems.register("disc_dont_move_a_muscle", () -> new RecordItem(103, () -> SoundHandler.DONT_MOVE_A_MUSCLE, new Item.Properties().tab(TabHandler.ITEMS), 2040));

    public static final RegistryObject<DNAItem> DNA = modItems.register("dna", () -> new DNAItem(new Item.Properties().tab(TabHandler.ITEMS)));
    public static final RegistryObject<PlantDNAItem> PLANT_DNA = modItems.register("plant_dna", () -> new PlantDNAItem(new Item.Properties().tab(TabHandler.ITEMS)));

    public static final RegistryObject<StorageDiscItem> STORAGE_DISC = modItems.register("storage_disc", () -> new StorageDiscItem(new Item.Properties().tab(TabHandler.ITEMS)));

    public static final RegistryObject<BlockItem> GYPSUM_BRICKS = registerBlockItem("gypsum_bricks", ModBlocks.GYPSUM_BRICKS);

    public static final RegistryObject<ActionFigureItem> DISPLAY_BLOCK = modItems.register("display_block_item", () -> new ActionFigureItem(new Item.Properties().tab(TabHandler.DECORATIONS)));



    static{
//
        for(WoodType woodType : DynamicWoodTypeRegistry.woodTypes){
            for(DynamicWoodTypeRegistry.ProductType type : DynamicWoodTypeRegistry.ProductType.vals()){
    //            Block block = DynamicWoodTypeRegistry.getProductFromWoodType(woodType, type);
                if(type == DynamicWoodTypeRegistry.ProductType.WALL_SIGN){
                    continue;
                }else if(type == DynamicWoodTypeRegistry.ProductType.SIGN){
                    modItems.register(woodType.name() + "_" + type.getProductName(), () -> type.signItemFunction.apply(DynamicWoodTypeRegistry.getProductFromWoodType(woodType, DynamicWoodTypeRegistry.ProductType.SIGN), DynamicWoodTypeRegistry.getProductFromWoodType(woodType, DynamicWoodTypeRegistry.ProductType.WALL_SIGN)));
                    continue;
                }
                modItems.register(woodType.name() + "_" + type.getProductName(), () -> type.itemFunction.apply(DynamicWoodTypeRegistry.getProductFromWoodType(woodType, type)));

            }
        }
    }

    public static final ArrayList<RegistryObject<BlockItem>> modBlocks = new ArrayList<>();

    public static final ArrayList<RegistryObject<DinosaurEggItem>> dinoEggs = new ArrayList<>();
    public static final ArrayList<RegistryObject<HatchedEggItem>> hatchedDinoEggs = new ArrayList<>();




    public static HashMap<String, RegistryObject<Item>> BONES = new HashMap<>();
    public static HashMap<String, RegistryObject<Item>> MEATS = new HashMap<>();
    public static HashMap<String, RegistryObject<Item>> STEAKS = new HashMap<>();

    public static void register(IEventBus bus) {

        //automatically register all the blocks :)

        for(var/*auto*/ a : ModBlocks.modBlocks.getEntries()){
//            System.out.println(a.getId());
            ResourceLocation location = a.getId();
            if(compare(location)){
                continue;
            }
            String name = correct(location.getPath());
//            String name = location.getPath().contains("block") ? location.getPath().replace("block", "item") : location.getPath().concat("_item");
//            System.out.println(name);

            modBlocks.add(registerBlockItem(location.getPath(), a));
        }

        for(var a : Dinosaur.DINOS){

            String dinoName = a.getName();

            String path = dinoName.toLowerCase(Locale.ROOT).replaceAll(" ", "_") + "_egg_item";



            RegistryObject<DinosaurEggItem> egg = modItems.register(path, () -> new DinosaurEggItem(new Item.Properties().tab(TabHandler.DNA)));
            RegistryObject<HatchedEggItem> hatchedEgg = modItems.register("hatched_" + path, () -> new HatchedEggItem(new Item.Properties().tab(TabHandler.DNA)));


            dinoEggs.add(egg);
            hatchedDinoEggs.add(hatchedEgg);


        }

        modItems.register(bus);

//        System.out.println("Registering blocks");




//        modBlockItems.register(bus);
    }

    private static String correct(String path) {//wtf - gamma
        return path;
    }

    private static boolean compare(ResourceLocation location) {
        return location.getPath().equals("display_block") || location.getPath().equals("gypsum_bricks");
    }


    public static void registerBone(String name, Supplier<Item> sup, String dino){
        BONES.put(dino.indexOf(':') >= 0 ? dino  : "jurassicworldreborn:" + dino, modItems.register(name, sup));//this is going to error. Need arrayList of bones, this will do for now
    }
    public static void registerMeat(String name, Supplier<Item> sup, String dino){
        String key = dino.indexOf(':') >= 0 ? dino : "jurassicworldreborn:" + dino;
        MEATS.put(key, modItems.register(name, sup));
        STEAKS.put(key, modItems.register(name, sup));
    }

    public static Item getBoneForDinosaur(EntityType<DinosaurEntity> type){
        return BONES.get(type.toString()).orElse(DEFAULT_BONE.get());
    }
    public static Item getMeatForDinosaur(EntityType<DinosaurEntity> type){
        return MEATS.get(type.toString()).orElse(DEFAULT_BONE.get());
    }

    public static RegistryObject<BlockItem> registerBlockItem(String name, Block block){

        final CreativeModeTab tab;
        if( block instanceof AncientPlantBlock || block instanceof AncientCoralBlock || block instanceof PaleoBaleBlock){
            tab = CreativeModeTab.TAB_DECORATIONS;
        }else{
            tab = CreativeModeTab.TAB_BUILDING_BLOCKS;
        }

        return modItems.register(name, () -> new BlockItem(block, new Item.Properties().tab(tab)));
    }

    public static RegistryObject<BlockItem> registerBlockItem(String name, Supplier<Block> block){

        final CreativeModeTab tab;
        if( block instanceof AncientPlantBlock || block instanceof AncientCoralBlock || block instanceof PaleoBaleBlock){
            tab = CreativeModeTab.TAB_DECORATIONS;
        }else{
            tab = TabHandler.BLOCKS;
        }

        return modItems.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }


}
