package net.gamma02.jurrasicworldreborn;

import net.gamma02.jurrasicworldreborn.common.CommonRegistries;
import net.gamma02.jurrasicworldreborn.common.blocks.ModBlocks;
import net.gamma02.jurrasicworldreborn.common.blocks.wood.DynamicWoodTypeRegistry;
import net.gamma02.jurrasicworldreborn.common.items.ModItems;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.common.MinecraftForge;
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

import static net.gamma02.jurrasicworldreborn.common.CommonRegistries.*;
import static net.minecraft.data.worldgen.features.OreFeatures.ORE_COAL_TARGET_LIST;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("jurrasicworldreborn")
public class Jurrasicworldreborn {


    public static String modid = "jurrasicworldreborn";

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

    public static Holder<ConfiguredFeature<OreConfiguration, ?>> CONFIGURED_FLORA_FOSSIL;

    public static Holder<PlacedFeature> FLORA_FOSSIL_PLACEMENT;



    public static IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

    public Jurrasicworldreborn() {

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







//        CONFIGURED_ARAUCARIA = FeatureUtils.register("jurrasicworldreborn:configured_araucria_feature", AraucariaTreeFeature, NoneFeatureConfiguration.INSTANCE);
//        CONFIGURED_GINKGO = FeatureUtils.register("jurrasicworldreborn:configured_ginkgo_feature", GinkgoTreeFeature, NoneFeatureConfiguration.INSTANCE);
//        CONFIGURED_CALAMITES = FeatureUtils.register("jurrasicworldreborn:configured_calamites_feature", CalamitesTreeFeature, NoneFeatureConfiguration.INSTANCE);
//        CONFIGURED_PHEONIX = FeatureUtils.register("jurrasicworldreborn:configured_pheonix_feature", PheonixTreeFeature, NoneFeatureConfiguration.INSTANCE);
//        CONFIGURED_PSARONIUS = FeatureUtils.register("jurrasicworldreborn:configured_psaronius_feature", PsaroniusTreeFeature, NoneFeatureConfiguration.INSTANCE);
        CONFIGURED_FLORA_FOSSIL = FeatureUtils.register("flora_fossil_configured", FLORA_FOSSIL_ORE.get(), new OreConfiguration(ORE_COAL_TARGET_LIST, 17));
        FLORA_FOSSIL_PLACEMENT = PlacementUtils.register("ore_coal_upper", OreFeatures.ORE_COAL, commonOrePlacement(15, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64))));
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

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("jurrasicworldreborn", "helloworld", () -> {
            LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
    }

    private void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.messageSupplier().get()).
                collect(Collectors.toList()));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }


    private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }


}
