package net.gamma02.jurassicworldreborn;

import net.gamma02.jurassicworldreborn.common.CommonRegistries;
import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.gamma02.jurassicworldreborn.common.blocks.wood.DynamicWoodTypeRegistry;
import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.gamma02.jurassicworldreborn.common.util.JsonOutputGenerator;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

import static net.gamma02.jurassicworldreborn.common.CommonRegistries.*;
import static net.minecraft.data.worldgen.features.OreFeatures.*;

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
        JsonOutputGenerator.doJsonProcessing();


        // Register the client setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register wood types and get DynamicWoodTypeRegistry setup and running
        CommonRegistries.init();

        // Register the DynamicWoodTypeRegistry
        DynamicWoodTypeRegistry.register(modEventBus);

        modFeatures.register(modEventBus);

        ModBlocks.register(modEventBus);

        ModItems.modItems.register(modEventBus);









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
        evt.enqueueWork(() -> {
            Sheets.addWoodType(CommonRegistries.AraucariaType);
            Sheets.addWoodType(CommonRegistries.CalamitesType);
            Sheets.addWoodType(CommonRegistries.GinkgoType);
            Sheets.addWoodType(CommonRegistries.PhoenixType);
            Sheets.addWoodType(CommonRegistries.PsaroniusType);
        });
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
            evt.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PLACED_LARGE_PETRIFIED_TREE);
            evt.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PLACED_SMALL_PETRIFIED_TREE);


        }
    }





}
