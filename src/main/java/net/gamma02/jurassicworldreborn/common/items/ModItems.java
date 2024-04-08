package net.gamma02.jurassicworldreborn.common.items;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.gamma02.jurassicworldreborn.common.blocks.ancientplants.AncientPlantBlock;
import net.gamma02.jurassicworldreborn.common.blocks.entities.incubator.IncubatorBlock;
import net.gamma02.jurassicworldreborn.common.blocks.entities.paleobale.PaleoBaleBlock;
import net.gamma02.jurassicworldreborn.common.blocks.fossil.AncientCoralBlock;
import net.gamma02.jurassicworldreborn.common.blocks.wood.DynamicWoodTypeRegistry;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurHandler;
import net.gamma02.jurassicworldreborn.common.items.Food.DinosaurMeatItem;
import net.gamma02.jurassicworldreborn.common.items.Fossils.EncasedFaunaFossilBlockItem;
import net.gamma02.jurassicworldreborn.common.items.Fossils.FaunaFossilBlockItem;
import net.gamma02.jurassicworldreborn.common.items.Fossils.FossilItem;
import net.gamma02.jurassicworldreborn.common.items.Fossils.PlasterAndBandageItem;
import net.gamma02.jurassicworldreborn.common.items.genetics.*;
import net.gamma02.jurassicworldreborn.common.items.misc.ActionFigureItem;
import net.gamma02.jurassicworldreborn.common.items.misc.CultivatorItem;
import net.gamma02.jurassicworldreborn.common.items.misc.SwarmItem;
import net.gamma02.jurassicworldreborn.common.items.misc.UseOnEntityItem;
import net.gamma02.jurassicworldreborn.common.plants.Plant;
import net.gamma02.jurassicworldreborn.common.plants.PlantHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import javax.xml.xpath.XPathFactory;
import java.rmi.registry.Registry;
import java.util.*;
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
    public static RegistryObject<Item> PLASTER_AND_BANDAGE = modItems.register("plaster_and_bandage", () -> new PlasterAndBandageItem(new Item.Properties().tab(TabHandler.ITEMS)));

    public static RegistryObject<Item> MOSQUITO_AMBER = modItems.register("amber_mosquito", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));
    public static RegistryObject<Item> APHID_AMBER = modItems.register("amber_aphid", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));

    public static RegistryObject<Item> SEA_LAMPREY = modItems.register("sea_lamprey", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));

    public static RegistryObject<Item> ENCASED_FAUNA_FOSSIL = modItems.register("encased_fauna_item", () -> new EncasedFaunaFossilBlockItem(ModBlocks.ENCASED_FAUNA_FOSSIL.get(), new Item.Properties().tab(TabHandler.FOSSILS)));

//    public static RegistryObject<Item> DEFAULT_BONE = modItems.register("missing_bone", () -> new Item(new Item.Properties()));
    //FOODS
    public static final FoodProperties SHARK_MEAT_RAW_PROP = new FoodProperties.Builder().meat().nutrition(5).saturationMod(0.6F).build();
    public static final FoodProperties SHARK_MEAT_COOKED_PROP = new FoodProperties.Builder().meat().nutrition(10).saturationMod(1.2F).build();
    public static final FoodProperties CRAB_MEAT_RAW_PROP = new FoodProperties.Builder().meat().nutrition(1).saturationMod(0.3F).build();
    public static final FoodProperties CRAB_MEAT_COOKED_PROP = new FoodProperties.Builder().meat().nutrition(5).saturationMod(0.6F).build();
    public static final FoodProperties GOAT_RAW_PROP = new FoodProperties.Builder().meat().nutrition(3).saturationMod(0.3F).build();
    public static final FoodProperties GOAT_COOKED_PROP = new FoodProperties.Builder().meat().nutrition(6).saturationMod(10.3F).build();

    public static final FoodProperties CHILEAN_SEA_BASS_PROPERTIES /*why from chile in particular*/ = new FoodProperties.Builder().meat().nutrition(10).saturationMod(1.0f).build();
    public static final FoodProperties FUN_FRIES_PROPERTIES = new FoodProperties.Builder().nutrition(4).saturationMod(2.0f).build();
    public static final FoodProperties OILED_POTATO_STRIPS_PROPERTIES = new FoodProperties.Builder().nutrition(1).saturationMod(0.0f).build();

    public static final FoodProperties WILD_ONION_PROPERTIES = new FoodProperties.Builder().nutrition(3).saturationMod(0.3f).build();
    public static final FoodProperties WILD_POTATO_PROPERTIES = new FoodProperties.Builder().nutrition(1).saturationMod(0.1f).build();
    public static final FoodProperties WILD_POTATO_COOKED_PROPERTIES = new FoodProperties.Builder().nutrition(6).saturationMod(0.6f).build();
    public static final FoodProperties RHAMNUS_BERRIES_PROPERTIES = new FoodProperties.Builder().nutrition(3).saturationMod(0.3f).build();
    public static final FoodProperties WEST_INDIAN_LILAC_BERRIES_PROPERTIES = new FoodProperties.Builder().nutrition(1).saturationMod(0.1f).effect(() -> new MobEffectInstance(MobEffects.POISON, 1400, 1), 1.0f).build();

    public static final RegistryObject<Item> SHARK_MEAT_RAW = modItems.register("raw_shark_meat", () -> new Item(new Item.Properties().tab(TabHandler.FOODS).food(SHARK_MEAT_RAW_PROP)));
    public static final RegistryObject<Item> SHARK_MEAT_COOKED = modItems.register("cooked_shark_meat", () -> new Item(new Item.Properties().tab(TabHandler.FOODS).food(SHARK_MEAT_COOKED_PROP)));
    public static final RegistryObject<Item> CRAB_MEAT_RAW = modItems.register("raw_crab_meat", () -> new Item(new Item.Properties().tab(TabHandler.FOODS).food(CRAB_MEAT_RAW_PROP)));
    public static final RegistryObject<Item> CRAB_MEAT_COOKED = modItems.register("cooked_crab_meat", () -> new Item(new Item.Properties().tab(TabHandler.FOODS).food(CRAB_MEAT_COOKED_PROP)));
    public static final RegistryObject<Item> CHILEAN_SEA_BASS = modItems.register("chilean_sea_bass", () -> new Item(new Item.Properties().tab(TabHandler.FOODS).food(CHILEAN_SEA_BASS_PROPERTIES)));
    public static final RegistryObject<Item> OILED_POTATO_STRIPS = modItems.register("oiled_potato_strips", () -> new Item(new Item.Properties().tab(TabHandler.FOODS).food(OILED_POTATO_STRIPS_PROPERTIES)));
    public static final RegistryObject<Item> FUN_FRIES = modItems.register("fun_fries", () -> new Item(new Item.Properties().tab(TabHandler.FOODS).food(FUN_FRIES_PROPERTIES)));
    public static final RegistryObject<Item> WILD_POTATO = modItems.register("wild_potato", () -> new Item(new Item.Properties().tab(TabHandler.FOODS).food(WILD_POTATO_PROPERTIES)));
    public static final RegistryObject<Item> WILD_POTATO_COOKED = modItems.register("wild_potato_cooked", () -> new Item(new Item.Properties().tab(TabHandler.FOODS).food(WILD_POTATO_COOKED_PROPERTIES)));
    public static final RegistryObject<Item> RHAMNUS_BERRIES = modItems.register("rhamnus_berries", () -> new Item(new Item.Properties().tab(TabHandler.FOODS).food(RHAMNUS_BERRIES_PROPERTIES)));
    public static final RegistryObject<Item> WEST_INDIAN_LILAC_BERRIES = modItems.register("west_indian_lilac_berries", () -> new Item(new Item.Properties().tab(TabHandler.FOODS).food(WEST_INDIAN_LILAC_BERRIES_PROPERTIES)));



    //todo: check if these are real or if I was having a stroke
    public static final RegistryObject<Item> GOAT_RAW = modItems.register("raw_goat_meat", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(GOAT_RAW_PROP)));
    public static final RegistryObject<Item> GOAT_COOKED = modItems.register("cooked_goat_meat", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(GOAT_COOKED_PROP)));


    public static final RegistryObject<SwarmItem> PLANKTON = modBlockItems.register("plankton", () -> new SwarmItem(ModBlocks.PLANKTON_SWARM.get(), new Item.Properties().tab(TabHandler.ITEMS)));
    public static final RegistryObject<SwarmItem> KRILL = modBlockItems.register("krill", () -> new SwarmItem(ModBlocks.KRILL_SWARM.get(), new Item.Properties().tab(TabHandler.ITEMS)));

    public static final RegistryObject<RecordItem> JURASSICRAFT_THEME_DISC = modBlockItems.register("disc_jurassicraft_theme", () -> new RecordItem(101/*dont ask*/, () -> SoundHandler.JURASSICRAFT_THEME, new Item.Properties().tab(TabHandler.ITEMS), 4740));
    public static final RegistryObject<RecordItem> TROODONS_AND_RAPTORS_DISC = modBlockItems.register("disc_troodons_and_raptors", () -> new RecordItem(102, () -> SoundHandler.TROODONS_AND_RAPTORS, new Item.Properties().tab(TabHandler.ITEMS), 1760));
    public static final RegistryObject<RecordItem> DONT_MOVE_A_MUSCLE_DISC = modBlockItems.register("disc_dont_move_a_muscle", () -> new RecordItem(103, () -> SoundHandler.DONT_MOVE_A_MUSCLE, new Item.Properties().tab(TabHandler.ITEMS), 2040));

//    public static final RegistryObject<DNAItem> DNA = modItems.register("dna", () -> new DNAItem(new Item.Properties().tab(TabHandler.ITEMS)));
    public static final RegistryObject<PlantDNAItem> PLANT_DNA = modItems.register("plant_dna", () -> new PlantDNAItem(PlantHandler.EMPTY, new Item.Properties().tab(TabHandler.ITEMS)));

    public static final RegistryObject<StorageDiscItem> STORAGE_DISC = modItems.register("storage_disc", () -> new StorageDiscItem(new Item.Properties().tab(TabHandler.ITEMS)));

    public static final RegistryObject<BlockItem> GYPSUM_BRICKS = registerBlockItem("gypsum_bricks", ModBlocks.GYPSUM_BRICKS);

//    public static final RegistryObject<ActionFigureItem> DISPLAY_BLOCK = modItems.register("display_block_item", () -> new ActionFigureItem(new Item.Properties().tab(TabHandler.DECORATIONS)));

    public static final RegistryObject<Item> FROZEN_LEECH_ITEM = modItems.register("frozen_leech", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));

    public static final RegistryObject<Item> EMPTY_TEST_TUBE = modItems.register("empty_test_tube", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));

    public static final RegistryObject<Item> DNA_NUCLEOTIDES = modItems.register("dna_base_material", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));

//    public static final RegistryObject<SoftTissueItem> SOFT_TISSUE = modItems.register("soft_tissue");

//    public static final RegistryObject<DinosaurEggItem> DINO_EGG = modItems.register("none_egg_item", () -> new DinosaurEggItem(new Item.Properties().tab(TabHandler.DNA), Dinosaur.EMPTY));

    public static final RegistryObject<FaunaFossilBlockItem> FAUNA_FOSSIL_BLOCK = modBlockItems.register("fauna_fossil_block_item", () -> new FaunaFossilBlockItem(ModBlocks.FAUNA_FOSSIL.get(), new Item.Properties().tab(TabHandler.FOSSILS)));

    public static final RegistryObject<IncubatorEnvironmentItem> PEAT_MOSS_BLOCK = modBlockItems.register("peat_moss", () -> new IncubatorEnvironmentItem(ModBlocks.PEAT_MOSS.get(), new Item.Properties().tab(TabHandler.BLOCKS)));

    //PLANTS
    public static final RegistryObject<PlantCallusItem> PLANT_CALLUS = modItems.register("plant_callus", () -> new PlantCallusItem(new Item.Properties().tab(TabHandler.PLANTS)));
    public static final RegistryObject<Item> LIQUID_AGAR = modItems.register("liquid_agar", () -> new Item(new Item.Properties().tab(TabHandler.PLANTS)));
    public static final RegistryObject<ItemNameBlockItem> WILD_ONION = modBlockItems.register("wild_onion", () -> new ItemNameBlockItem(ModBlocks.WILD_ONION.get(), new Item.Properties().tab(TabHandler.PLANTS).food(WILD_ONION_PROPERTIES)));
    public static final RegistryObject<ItemNameBlockItem> WILD_POTATO_SEEDS = modBlockItems.register("wild_potato_seeds", () -> new ItemNameBlockItem(ModBlocks.WILD_POTATO_PLANT.get(), new Item.Properties().tab(TabHandler.PLANTS)));
    public static final RegistryObject<ItemNameBlockItem> RHAMNUS_SEEDS = modBlockItems.register("rhamnus_salicifolius_seeds", () -> new ItemNameBlockItem(ModBlocks.RHAMNUS_SALICIFOLIUS_PLANT.get(), new Item.Properties().tab(TabHandler.PLANTS)));

    //ITEMS
    public static final RegistryObject<Item> PETRI_DISH = modItems.register("petri_dish", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));
    public static final RegistryObject<Item> PETRI_DISH_AGAR = modItems.register("petri_dish_agar", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));
    public static final RegistryObject<Item> PLANT_CELLS = modItems.register("plant_cells", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));
    public static final RegistryObject<Item> PLANT_CELLS_PETRI_DISH = modItems.register("plant_cells_petri_dish", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));
    public static final RegistryObject<EmptySyringeItem> EMPTY_SYRINGE = modItems.register("empty_syringe", () -> new EmptySyringeItem(new Item.Properties().tab(TabHandler.ITEMS)));

    public static final RegistryObject<Item> IRON_BLADES = modItems.register("iron_blades", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));
    public static final RegistryObject<Item> IRON_ROD = modItems.register("iron_rod", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));

    public static final RegistryObject<Item> DISC_DRIVE = modItems.register("disc_reader", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));
    public static final RegistryObject<Item> COMPUTER_SCREEN = modItems.register("computer_screen", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));
    public static final RegistryObject<Item> KEYBOARD = modItems.register("keyboard", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));
    public static final RegistryObject<Item> LASER = modItems.register("laser", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));
    public static final RegistryObject<Item> BASIC_CIRCUIT = modItems.register("basic_circuit", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));
    public static final RegistryObject<Item> ADVANCED_CIRCUIT = modItems.register("advanced_circuit", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));

    public static final RegistryObject<Item> GYPSUM_POWDER = modItems.register("gypsum_powder", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));
    public static final RegistryObject<Item> DNA_ANALYZER = modItems.register("dna_analyzer", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));
    public static final RegistryObject<Item> LUNCH_BOX = modItems.register("lunch_box", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));
    public static final RegistryObject<Item> STAMP_SET = modItems.register("stamp_set", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));

    public static final RegistryObject<Item> CAR_CHASSIS = modItems.register("car_chassis", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));
    public static final RegistryObject<Item> ENGINE_SYSTEM = modItems.register("engine_system", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));
    public static final RegistryObject<Item> CAR_SEATS = modItems.register("car_seats", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));
    public static final RegistryObject<Item> CAR_TIRE = modItems.register("car_tire", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));
    public static final RegistryObject<Item> CAR_WINDSCREEN = modItems.register("car_windscreen", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));
    public static final RegistryObject<Item> UNFINISHED_CAR = modItems.register("unfinished_car", () -> new Item(new Item.Properties().tab(TabHandler.ITEMS)));

    public static final RegistryObject<UseOnEntityItem> GROWTH_SERUM = modItems.register("growth_serum", () -> new UseOnEntityItem(new Item.Properties().tab(TabHandler.ITEMS), (interaction) -> {
        if(interaction.getPlayer().getLevel().isClientSide)
            return InteractionResult.PASS;
        if (interaction.getTarget() instanceof DinosaurEntity dinosaur) {
            if (!dinosaur.isCarcass()) {
                dinosaur.increaseGrowthSpeed();
                interaction.getStack().shrink(1);
                if (!interaction.getPlayer().isCreative()) {
                    interaction.getPlayer().getInventory().add(new ItemStack(ModItems.EMPTY_SYRINGE.get()));
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }));
    public static final RegistryObject<Item> BREEDING_WAND = modItems.register("breeding_wand", () -> new UseOnEntityItem(new Item.Properties().tab(TabHandler.ITEMS), interaction -> {
        if(interaction.getPlayer().getLevel().isClientSide)
            return InteractionResult.PASS;
        ItemStack stack = interaction.getPlayer().getItemInHand(interaction.getHand());
        CompoundTag nbt = stack.getOrCreateTagElement("wand_info");
        Entity entity = interaction.getPlayer().getLevel().getEntity(nbt.getInt("dino_id"));
        if (interaction.getTarget() instanceof DinosaurEntity) {
            if (nbt.contains("dino_id", 99)) {
                if (entity instanceof DinosaurEntity && ((DinosaurEntity) entity).isMale() != ((DinosaurEntity) interaction.getTarget()).isMale() && !((DinosaurEntity) interaction.getTarget()).getDinosaur().isHybrid) {
                    ((DinosaurEntity) entity).breed((DinosaurEntity) interaction.getTarget());
                    ((DinosaurEntity) interaction.getTarget()).breed((DinosaurEntity) entity);
                } else if (entity != interaction.getTarget()) {
                    nbt.remove("dino_id");
                }
            } else {
                nbt.putInt("dino_id", interaction.getTarget().getId());
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }));

    //CREATIVE
    public static final RegistryObject<Item> BIRTHING_WAND = modItems.register("birthing_wand", () -> new UseOnEntityItem(new Item.Properties().tab(TabHandler.CREATIVE), interaction -> {
        if(interaction.getPlayer().getLevel().isClientSide)
            return InteractionResult.PASS;
        if(interaction.getTarget() instanceof DinosaurEntity) {
            DinosaurEntity dino = ((DinosaurEntity)interaction.getTarget());
            if (dino.isPregnant() && !dino.getDinosaur().isHybrid) {
                ((DinosaurEntity) interaction.getTarget()).giveBirth();
                return InteractionResult.SUCCESS;
            } else {
                interaction.getPlayer().displayClientMessage(Component.translatable("dinosaur.birthingwand." + (dino.isMale() ? "male" : "not_pregnant")), true);
            }
        }
        return InteractionResult.FAIL;
    }));
    public static final RegistryObject<Item> PREGNANCY_TEST = modItems.register("pregnancy_test", () -> new UseOnEntityItem(new Item.Properties().tab(TabHandler.CREATIVE), (interaction) -> {
        if(interaction.getPlayer().getLevel().isClientSide){
            return InteractionResult.PASS;
        }
        if(interaction.getTarget() instanceof DinosaurEntity) {//why was this the only one to have a remote check and even then it did it wrong
            DinosaurEntity dino = ((DinosaurEntity)interaction.getTarget());
            interaction.getPlayer().displayClientMessage(Component.translatable("dinosaur.pregnancytest." + (dino.isMale() ? "male" : dino.isPregnant() ? "pregnant" : "not_pregnant")), true);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }));




    //DECORATIONS
    public static final RegistryObject<Item> AMBER_KEYCHAIN = modItems.register("amber_keychain", () -> new Item(new Item.Properties().tab(TabHandler.DECORATIONS)));
    public static final RegistryObject<Item> AMBER_CANE = modItems.register("amber_cane", () -> new Item(new Item.Properties().tab(TabHandler.DECORATIONS)));
    public static final RegistryObject<Item> MR_DNA_KEYCHAIN = modItems.register("mr_dna_keychain", () -> new Item(new Item.Properties().tab(TabHandler.DECORATIONS)));








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

    public static final HashMap<Dinosaur, RegistryObject<DinosaurEggItem>> dinoEggs = new LinkedHashMap<>();
    public static final HashMap<Dinosaur, RegistryObject<HatchedEggItem>> hatchedDinoEggs = new LinkedHashMap<>();





    public static HashMap<Dinosaur, LinkedHashMap<String, RegistryObject<Item>>> BONES = new HashMap<>();
    public static HashMap<Dinosaur, LinkedHashMap<String, RegistryObject<Item>>> FRESH_BONES = new HashMap<>();
    public static ArrayList<RegistryObject<Item>> ALL_BONES = new ArrayList<>();
    public static HashMap<Dinosaur, RegistryObject<DNAItem>> DINOSAUR_DNA = new HashMap<>();
    public static HashMap<Dinosaur, RegistryObject<SoftTissueItem>> SOFT_TISSUE = new HashMap<>();
    public static HashMap<Dinosaur, RegistryObject<Item>> MEATS = new HashMap<>();
    public static HashMap<Dinosaur, RegistryObject<Item>> STEAKS = new HashMap<>();
    public static HashMap<DyeColor, RegistryObject<CultivatorItem>> CULTIVATORS = new HashMap<>();
    public static HashMap<Dinosaur, RegistryObject<ActionFigureItem>> ACTION_FIGURES = new HashMap<>();
    public static HashMap<Dinosaur, RegistryObject<ActionFigureItem>> FOSSIL_SKELETONS = new HashMap<>();
    public static HashMap<Dinosaur, RegistryObject<ActionFigureItem>> FRESH_SKELETONS = new HashMap<>();

    public static ArrayList<Supplier<Item>> ALL_MEATS = new ArrayList<>();
    public static ArrayList<String> USED_IDS = new ArrayList<>();
    public static HashMap<Plant, RegistryObject<Item>> PLANT_DNAS = new HashMap<>();
    public static HashMap<Dinosaur, RegistryObject<SyringeItem>> SYRINGES = new HashMap<>();

    public static void register(IEventBus bus) {

        //make sure all the dinosaurs are registered :D
//        DinosaurHandler.doDinosInit();

        //automatically register all the blocks :)

        for(RegistryObject<Block>/*auto*/ a : ModBlocks.modBlocks.getEntries()){
//            System.out.println(a.getId());
            ResourceLocation location = a.getId();
            if(compare(location)){
                continue;
            }
//            String name = correct(location.getPath());
//            String name = location.getPath().contains("block") ? location.getPath().replace("block", "item") : location.getPath().concat("_item");
//            System.out.println(name);

            modBlocks.add(registerBlockItem(location.getPath(), a));
        }


        for(Dinosaur a : Dinosaur.DINOSAUR_IDS.keySet()){

            String dinoName = a.getName();

            String formattedName = dinoName.toLowerCase(Locale.ROOT).replaceAll(" ", "_");

            String path = dinoName.toLowerCase(Locale.ROOT).replaceAll(" ", "_");

            if(a == Dinosaur.EMPTY)
                continue;

            if(!a.givesDirectBirth()) {

                RegistryObject<DinosaurEggItem> egg = modItems.register("egg/egg_" + formattedName, () -> new DinosaurEggItem(new Item.Properties(), a));
                dinoEggs.put(a, egg);

            }
            
            RegistryObject<HatchedEggItem> hatchedEgg = modItems.register("hatched_egg/egg_" + formattedName, () -> new HatchedEggItem(new Item.Properties(), a));
            RegistryObject<DNAItem> dinoDna = modItems.register("dna/dna_" + formattedName, () -> new DNAItem(new Item.Properties(), a));
            RegistryObject<SoftTissueItem> softTissue = modItems.register("soft_tissue/soft_tissue_" + formattedName, () -> new SoftTissueItem(new Item.Properties().tab(TabHandler.DNA), a));
            RegistryObject<SyringeItem> dinoSyringe = modItems.register("syringe/syringe_" + formattedName, () -> new SyringeItem(new Item.Properties().tab(TabHandler.DNA), a));

            //Action figures
            RegistryObject<ActionFigureItem> actionFigure = modItems.register("action_figure/action_figure_" + formattedName, () -> new ActionFigureItem(new Item.Properties().tab(TabHandler.DECORATIONS), a, false, true));
            RegistryObject<ActionFigureItem> freshSkeleton = modItems.register("skeleton/fresh/skeleton_fresh_" + formattedName, () -> new ActionFigureItem(new Item.Properties().tab(TabHandler.DECORATIONS), a, true, true));
            RegistryObject<ActionFigureItem> fossilSkeleton = modItems.register("skeleton/fossil/skeleton_fossil_" + formattedName, () -> new ActionFigureItem(new Item.Properties().tab(TabHandler.DECORATIONS), a, true, false));



            SOFT_TISSUE.put(a, softTissue);
            DINOSAUR_DNA.put(a, dinoDna);
            SYRINGES.put(a, dinoSyringe);
            hatchedDinoEggs.put(a, hatchedEgg);

            ACTION_FIGURES.put(a, actionFigure);
            FRESH_SKELETONS.put(a, freshSkeleton);
            FOSSIL_SKELETONS.put(a, fossilSkeleton);


            //Register other dinosaur-dependent items
            registerFossilBonesForDino(a);
            registerFreshBonesForDino(a);
            registerMeatsForDino(a);

        }

        for(Plant p : PlantHandler.getPrehistoricPlantsAndTrees()){
            String name = p.getFormattedName();

            name = "dna/plants/dna_".concat(name);

            PLANT_DNAS.put(p, modItems.register(name, () -> new PlantDNAItem(p, new Item.Properties())));


        }

        for(DyeColor d : DyeColor.values()){
            String name = "cultivate/cultivate_bottom_" + d.getName();
            System.out.println(name);

            CULTIVATORS.put(d, modItems.register(name, () -> new CultivatorItem(new Item.Properties().tab(TabHandler.BLOCKS), d)));
        }

        modItems.register(bus);

//        System.out.println("Registering blocks");




//        modBlockItems.register(bus);
    }


    private static String correct(String path) {//wtf - gamma
        return path;
    }

    private static boolean compare(ResourceLocation location) {
        return location.getPath().equals("display_block") || location.getPath().equals("gypsum_bricks")
                || location.getPath().equals("peat_moss")
                || location.getPath().equals("cultivator_bottom") || location.getPath().equals("cultivator_tob")
                || location.getPath().equals("cultivate_bottom") || location.getPath().equals("cultivate_top")
                || modBlockItems.getEntries().contains(location);
    }


    @Nullable
    public static RegistryObject<Item> registerSingleBone(String boneName, Supplier<Item> sup, Dinosaur dino, boolean fresh){
//        BONES.put(dino, modItems.register(dino.getName().toUpperCase(Locale.ROOT).concat(":").concat(boneName), sup));//this is going to error. Need arrayList of bones, this will do for now
        if(dino == DinosaurHandler.BLUE || dino == DinosaurHandler.CHARLIE || dino == DinosaurHandler.DELTA || dino == DinosaurHandler.ECHO){
            return null;
        }
        String formattedDinoName = dino.getName().toLowerCase().replaceAll(" ", "_");
        String id = "/" + formattedDinoName + "_" + boneName;
        if(fresh){
            id = "fresh_bones".concat(id);
        }else{
            id = "bones".concat(id);
        }
//        if(( (boneName.equals("skull") && !skullExemptDinos.contains(formattedDinoName) ) ||( boneName.equals("tooth") && teethDinos.contains(formattedDinoName)) ) && !fresh ){
//            id = "fossil/" + boneName + "_" + formattedDinoName;
//        }



        try {
            if(USED_IDS.contains(id))
                return null;
            USED_IDS.add(id);
            RegistryObject<Item> item = modItems.register(id, sup);
            ALL_BONES.add(item);
            return item;
        }catch(IllegalArgumentException e){
            Jurassicworldreborn.getLogger().error("GOT YEETED BY " + e);
//            e.printStackTrace();
        }

        return null;
    }

    public static void registerFossilBonesForDino(Dinosaur dinosaur){

        if(dinosaur.isHybrid || dinosaur == Dinosaur.EMPTY || dinosaur.getBones() == null)//make sure the dinosaur isn't a hybrid
            return;



        LinkedHashMap<String, RegistryObject<Item>> DINO_BONES = new LinkedHashMap<>();
        for(String s : dinosaur.getBones()){
            RegistryObject<Item> item = registerSingleBone(s, () -> new FossilItem(new Item.Properties().tab(TabHandler.FOSSILS), s, false, dinosaur), dinosaur, false);
            if(item != null)
                DINO_BONES.put(s, item);
        }
        BONES.put(dinosaur, DINO_BONES);
    }

    public static void registerFreshBonesForDino(Dinosaur dino){

        LinkedHashMap<String, RegistryObject<Item>> fresh_bones = new LinkedHashMap<>();
        if(dino.getBones() == null){
            return;
        }
        for(String s : dino.getBones()){
            RegistryObject<Item> item = registerSingleBone(s, () -> new FossilItem(new Item.Properties().tab(TabHandler.FOSSILS), s, true, dino), dino, true);
            if(item != null)
                fresh_bones.put(s, item);
        }

        FRESH_BONES.put(dino, fresh_bones);
    }


    public static RegistryObject<Item> registerSingleRawMeat(Supplier<Item> sup, Dinosaur dino){
        return modItems.register("meat/meat_" + dino.getName().toLowerCase(Locale.ROOT).replaceAll(" ", "_"), sup);

    }

    public static RegistryObject<Item> registerSingleSteak(Supplier<Item> sup, Dinosaur dino){
        return modItems.register("meat/steak_" + dino.getName().toLowerCase().replaceAll(" ", "_"), sup);
    }

    public static void registerMeatsForDino(Dinosaur dino){
        List<MobEffectInstance> cookedEffects = dino.applyMeatEffect(new ArrayList<>(), true);
        FoodProperties.Builder cookedProperties = new FoodProperties.Builder().nutrition(8).meat();
        for(MobEffectInstance i : cookedEffects){
            cookedProperties.effect(() -> i, 0.9f);
        }

        List<MobEffectInstance> rawEffects = dino.applyMeatEffect(new ArrayList<>(), false);
        FoodProperties.Builder rawProperties = new FoodProperties.Builder().nutrition(3).meat();
        for(MobEffectInstance i : rawEffects){
            rawProperties.effect(() -> i, 0.9f);
        }
        RegistryObject<Item> rawMeat = registerSingleRawMeat(() -> new DinosaurMeatItem(new Item.Properties().food(rawProperties.build()).tab(TabHandler.FOODS), false, dino), dino);
        RegistryObject<Item> steak = registerSingleSteak(() -> new DinosaurMeatItem(new Item.Properties().food(cookedProperties.build()).tab(TabHandler.FOODS), true, dino), dino);
        MEATS.put(dino, rawMeat);
        STEAKS.put(dino, steak);
        ALL_MEATS.add(rawMeat);
        ALL_MEATS.add(steak);
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
