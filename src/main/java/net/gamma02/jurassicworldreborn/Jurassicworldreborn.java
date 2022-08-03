package net.gamma02.jurassicworldreborn;

import net.gamma02.jurassicworldreborn.client.screens.CleanerScreen;
import net.gamma02.jurassicworldreborn.client.screens.ModScreens;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.CommonRegistries;
import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.gamma02.jurassicworldreborn.common.blocks.entities.cleaner.CleanerBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.cleaner.CleanerMenu;
import net.gamma02.jurassicworldreborn.common.blocks.entities.modBlockEntities;
import net.gamma02.jurassicworldreborn.common.blocks.wood.DynamicWoodTypeRegistry;
import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.gamma02.jurassicworldreborn.common.network.Network;
import net.gamma02.jurassicworldreborn.common.recipies.cleaner.CleaningRecipie;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.gamma02.jurassicworldreborn.common.CommonRegistries.*;
import static net.gamma02.jurassicworldreborn.common.recipies.cleaner.CleaningRecipie.CLEANING_RECIPE_TYPE;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("jurassicworldreborn")
public class Jurassicworldreborn {


    public static String modid = "jurassicworldreborn";

    public static ResourceLocation resource(String resource){
        return new ResourceLocation(modid, resource);
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

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register wood types and get DynamicWoodTypeRegistry setup and running
        CommonRegistries.init();

        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(RecipeSerializer.class, Jurassicworldreborn::registerRecipeSerializers);



        // Register the DynamicWoodTypeRegistry
        DynamicWoodTypeRegistry.register(modEventBus);

        modFeatures.register(modEventBus);

        ModBlocks.register(modEventBus);

        ModItems.modItems.register(modEventBus);

        modBlockEntities.modBlockEntities.register(modEventBus);

        modBlockEntities.modScreenTypes.modScreenTypes.register(modEventBus);

        Network.init();









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
        MenuScreens.<CleanerMenu, CleanerScreen>register(modBlockEntities.modScreenTypes.CleanerScreenType.get(), CleanerScreen::new);

        ModScreens.<CleanerBlockEntity, CleanerMenu, CleanerScreen>register(modBlockEntities.CLEANING_STATION.get(), CleanerScreen::new);


    }

    public static void registerRecipeSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {

        // Vanilla has a registry for recipe types, but it does not actively use this registry.
        // While this makes registering your recipe type an optional step, I recommend
        // registering it anyway to allow other mods to discover your custom recipe types.
        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(CLEANING_RECIPE_TYPE.toString()), CLEANING_RECIPE_TYPE);

        // Register the recipe serializer. This handles from json, from packet, and to packet.
        event.getRegistry().register(CleaningRecipie.INSTANCE);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
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
