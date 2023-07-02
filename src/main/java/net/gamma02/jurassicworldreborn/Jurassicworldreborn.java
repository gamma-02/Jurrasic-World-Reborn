package net.gamma02.jurassicworldreborn;

import net.gamma02.jurassicworldreborn.client.screens.CleanerScreen;
import net.gamma02.jurassicworldreborn.client.screens.ModScreens;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.CommonRegistries;
import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities;
import net.gamma02.jurassicworldreborn.common.blocks.entities.cleaner.CleanerBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.cleaner.CleanerMenu;
import net.gamma02.jurassicworldreborn.common.blocks.wood.DynamicWoodTypeRegistry;
import net.gamma02.jurassicworldreborn.common.entities.EventListener;
import net.gamma02.jurassicworldreborn.common.entities.ModEntities;
import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.gamma02.jurassicworldreborn.common.network.Network;
import net.gamma02.jurassicworldreborn.common.recipies.cleaner.CleaningRecipie;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

import static net.gamma02.jurassicworldreborn.common.CommonRegistries.*;
import static net.gamma02.jurassicworldreborn.common.recipies.cleaner.CleaningRecipie.CLEANING_RECIPE_TYPE;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("jurassicworldreborn")
public class Jurassicworldreborn {


    public static String modid = "jurassicworldreborn";

    public static final String MODID = "jurassicworldreborn";

    public static ResourceLocation resource(String resource){
        return new ResourceLocation(modid, resource);
    }

    public static final HashMap<Block, RenderType> renderlayers = new HashMap<>();

    public static void setRenderType(Block block, RenderType rendertype){
        renderlayers.put(block, rendertype);
    }

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public static Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> CONFIGURED_ARAUCARIA;
    public static Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> CONFIGURED_GINKGO;

    public static Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> CONFIGURED_CALAMITES;

    public static Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> CONFIGURED_PHEONIX;

    public static Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> CONFIGURED_PSARONIUS;





    public static IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

    public Jurassicworldreborn() {


        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(EventListener::finalizeSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register wood types and get DynamicWoodTypeRegistry setup and running
        CommonRegistries.init();



        //that would be the reason for having no crab entities-
        ModEntities.MOD_ENTITY_TYPES.register(modEventBus);

        DynamicWoodTypeRegistry.register(modEventBus);

        modFeatures.register(modEventBus);

        ModBlocks.register(modEventBus);

        ModItems.register(modEventBus);

        ModBlockEntities.modBlockEntities.register(modEventBus);

        ModBlockEntities.modScreenTypes.modScreenTypes.register(modEventBus);




        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(RecipeSerializer.class, Jurassicworldreborn::registerRecipeSerializers);



        // Register the DynamicWoodTypeRegistry


        Network.init();


//        HashMap<String, JsonObject> jsonMap = DynamicWoodTypeRegistry.getJsonBlockStateModelDefinitions();

//        Gson json = new GsonBuilder().setPrettyPrinting().create();
//        for(var s : jsonMap.keySet()){
//            try(BufferedWriter writer = new BufferedWriter(new FileWriter( "C:\\Users\\gamma\\OneDrive\\Documents\\C++ automation io\\WoodTypeOutput\\" + s + ".json"))){
//                String prettyOutput = json.toJson(jsonMap.get(s));
//                writer.write(prettyOutput);
//                writer.close();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }











//        CONFIGURED_ARAUCARIA = FeatureUtils.register("jurassicworldreborn:configured_araucria_feature", AraucariaTreeFeature, NoneFeatureConfiguration.INSTANCE);
//        CONFIGURED_GINKGO = FeatureUtils.register("jurassicworldreborn:configured_ginkgo_feature", GinkgoTreeFeature, NoneFeatureConfiguration.INSTANCE);
//        CONFIGURED_CALAMITES = FeatureUtils.register("jurassicworldreborn:configured_calamites_feature", CalamitesTreeFeature, NoneFeatureConfiguration.INSTANCE);
//        CONFIGURED_PHEONIX = FeatureUtils.register("jurassicworldreborn:configured_pheonix_feature", PheonixTreeFeature, NoneFeatureConfiguration.INSTANCE);
//        CONFIGURED_PSARONIUS = FeatureUtils.register("jurassicworldreborn:configured_psaronius_feature", PsaroniusTreeFeature, NoneFeatureConfiguration.INSTANCE);

//????????? maybe this will work???????????

    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());

    }



    public void clientSetup(final FMLClientSetupEvent evt){

        SoundHandler.init();
        //wood type rendering
        evt.enqueueWork(() -> {

            Sheets.addWoodType(CommonRegistries.AraucariaType);
            Sheets.addWoodType(CommonRegistries.CalamitesType);
            Sheets.addWoodType(CommonRegistries.GinkgoType);
            Sheets.addWoodType(CommonRegistries.PhoenixType);
            Sheets.addWoodType(CommonRegistries.PsaroniusType);
        });

        SoundHandler.registrer.register(FMLJavaModLoadingContext.get().getModEventBus());


        //Binding screens to types
        MenuScreens.<CleanerMenu, CleanerScreen>register(ModBlockEntities.modScreenTypes.CleanerScreenType.get(), CleanerScreen::new);

        ModScreens.<CleanerBlockEntity, CleanerMenu, CleanerScreen>register(ModBlockEntities.CLEANING_STATION.get(), CleanerScreen::new);


        for (Block b:
             renderlayers.keySet()) {
            ItemBlockRenderTypes.setRenderLayer(b, renderlayers.get(b));
        }


    }

    public static Logger getLogger(){
        return LOGGER;
    }

    public static void registerRecipeSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {

        // Vanilla has a registry for recipe types, but it does not actively use this registry.
        // While this makes registering your recipe type an optional step, I recommend
        // registering it anyway to allow other mods to discover your custom recipe types.
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(modid, "cleaning_recipe_type"), CLEANING_RECIPE_TYPE);

        // Register the recipe serializer. This handles from json, from packet, and to packet.
        event.getRegistry().register(CleaningRecipie.INSTANCE);

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
    public static Boolean never(BlockState p_50779_, BlockGetter p_50780_, BlockPos p_50781_, EntityType<?> p_50782_) {
        return (boolean)false;
    }
    public static boolean never(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
        return false;
    }

    @SubscribeEvent
    public void onBiomeLoad(BiomeLoadingEvent evt){
        if(evt.getCategory() != Biome.BiomeCategory.NETHER && evt.getCategory() != Biome.BiomeCategory.THEEND){
            evt.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ICE_SHARD_ORE_PLACEMENT);
            evt.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FAUNA_FOSSIL_PLACEMENT);
            evt.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FLORA_FOSSIL_PLACEMENT);
            evt.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AMBER_ORE_PLACEMENT);
            if(evt.getCategory() != Biome.BiomeCategory.NONE) {
                evt.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PLACED_LARGE_PETRIFIED_TREE);
                evt.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PLACED_SMALL_PETRIFIED_TREE);
            }


        }
    }





}
