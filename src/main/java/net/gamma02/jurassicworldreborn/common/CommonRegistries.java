package net.gamma02.jurassicworldreborn.common;


import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities;
import net.gamma02.jurassicworldreborn.common.blocks.entities.cleaner.CleanerMenu;
import net.gamma02.jurassicworldreborn.common.blocks.wood.DynamicWoodTypeRegistry;
import net.gamma02.jurassicworldreborn.common.worldgen.OreVeinFeature;
import net.gamma02.jurassicworldreborn.common.worldgen.tree.*;
import net.gamma02.jurassicworldreborn.common.worldgen.tree.petrified.PetrifiedTreeConfig;
import net.gamma02.jurassicworldreborn.common.worldgen.tree.petrified.PetrifiedTreeGenerator;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.gamma02.jurassicworldreborn.Jurassicworldreborn.modid;
import static net.gamma02.jurassicworldreborn.Jurassicworldreborn.resource;
import static net.minecraft.data.worldgen.features.OreFeatures.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonRegistries {
    //create the wood type for the ancient wood, there are a few.........
    public static WoodType AraucariaType = WoodType.register(WoodType.create("araucaria"));
    public static WoodType CalamitesType = WoodType.register(WoodType.create("calamites"));
    public static WoodType GinkgoType = WoodType.register(WoodType.create("ginkgo"));
    public static WoodType PhoenixType = WoodType.register(WoodType.create("phoenix"));
    public static WoodType PsaroniusType = WoodType.register(WoodType.create("psaronius"));

    public static ArrayList<WoodType> modWoodTypes = new ArrayList<>(List.of(AraucariaType, CalamitesType, GinkgoType, PhoenixType, PsaroniusType));


    public static DeferredRegister<Feature<?>> modFeatures = DeferredRegister.create(ForgeRegistries.FEATURES, modid);

    public static Feature<OreConfiguration> FLORA_FOSSIL_ORE = new OreVeinFeature(OreConfiguration.CODEC);

    public static Feature<PetrifiedTreeConfig> PETRIFIED_TREE_GENERATOR = new PetrifiedTreeGenerator(PetrifiedTreeConfig.CODEC);

    public static RegistryObject<Feature<NoneFeatureConfiguration>> AraucariaTreeFeature = modFeatures.register("araucaria_tree_feature", () -> new AraucariaTreeGenerator(NoneFeatureConfiguration.CODEC));

    public static RegistryObject<Feature<NoneFeatureConfiguration>> GinkgoTreeFeature = modFeatures.register("ginkgo_tree_feature", () -> new GinkgoTreeGenerator(NoneFeatureConfiguration.CODEC));

    public static RegistryObject<Feature<NoneFeatureConfiguration>> CalamitesTreeFeature = modFeatures.register("calamites_tree_feature", () -> new CalamitesTreeGenerator(NoneFeatureConfiguration.CODEC));

    public static RegistryObject<Feature<NoneFeatureConfiguration>> PhoenixTreeFeature = modFeatures.register("phoenix_tree_feature", () -> new PhoenixTreeGenerator(NoneFeatureConfiguration.CODEC));

    public static RegistryObject<Feature<NoneFeatureConfiguration>> PsaroniusTreeFeature = modFeatures.register("psaronius_tree_feature", () -> new PsaroniusTreeGenerator(NoneFeatureConfiguration.CODEC));

    public static Holder<ConfiguredFeature<OreConfiguration, ?>> CONFIGURED_FLORA_FOSSIL;

    public static Holder<PlacedFeature> FLORA_FOSSIL_PLACEMENT;

    public static List<OreConfiguration.TargetBlockState> ORE_FAUNA_FOSSIL_LIST;

    public static List<OreConfiguration.TargetBlockState> ORE_AMBER_LIST;

    public static List<OreConfiguration.TargetBlockState> ORE_ICE_SHARD_LIST;




    public static Holder<ConfiguredFeature<OreConfiguration, ?>> CONFIGURED_FAUNA_FOSSIL;

    public static Holder<PlacedFeature> FAUNA_FOSSIL_PLACEMENT;

    public static Holder<PlacedFeature> AMBER_ORE_PLACEMENT;

    public static Holder<PlacedFeature> ICE_SHARD_ORE_PLACEMENT;

    public static Holder<ConfiguredFeature<OreConfiguration, ?>> CONFIGURED_AMBER_ORE;

    public static Holder<ConfiguredFeature<OreConfiguration, ?>> CONFIGURED_ICE_SHARD_ORE;

    public static Holder<ConfiguredFeature<PetrifiedTreeConfig, ?>> CONFIGURED_SMALL_PETRIFIED_TREE;

    public static Holder<ConfiguredFeature<PetrifiedTreeConfig, ?>> CONFIGURED_LARGE_PETRIFIED_TREE;

    public static Holder<PlacedFeature> PLACED_LARGE_PETRIFIED_TREE;
    public static Holder<PlacedFeature> PLACED_SMALL_PETRIFIED_TREE;


    @SubscribeEvent
    public static void blockRegisteringEvent(final RegistryEvent.Register<Feature<?>> evt){
        FLORA_FOSSIL_ORE.setRegistryName(resource("flora_fossil_ore"));
        PETRIFIED_TREE_GENERATOR.setRegistryName(resource("petrified_tree_generation_feature"));
        evt.getRegistry().registerAll(FLORA_FOSSIL_ORE, PETRIFIED_TREE_GENERATOR);
    }

    @SubscribeEvent
    public static void commonsetupevent(final FMLCommonSetupEvent evt){
        ConfiguredFeatureRegistries.init();

        //do Json processing for dynamic wood registry models
//        JsonOutputGenerator.doJsonProcessing(DynamicWoodTypeRegistry.getJsonBlockStateModelDefinitions());


        ModBlockEntities.modScreenTypes.modMenuSupplier.put(ModBlockEntities.modScreenTypes.CleanerScreenType.getId(), CleanerMenu::new);

    }











    public static void init(){
        if(DynamicWoodTypeRegistry.woodTypes != null && new HashSet<>(DynamicWoodTypeRegistry.woodTypes).containsAll(modWoodTypes)){
            return;
        }
        DynamicWoodTypeRegistry.addWoodType(GinkgoType, MaterialColor.RAW_IRON, MaterialColor.PODZOL);
        DynamicWoodTypeRegistry.addWoodType(AraucariaType, MaterialColor.TERRACOTTA_LIGHT_GRAY, MaterialColor.STONE);
        DynamicWoodTypeRegistry.addWoodType(CalamitesType, MaterialColor.TERRACOTTA_LIGHT_GREEN, MaterialColor.TERRACOTTA_LIGHT_GREEN);
        DynamicWoodTypeRegistry.addWoodType(PhoenixType, MaterialColor.TERRACOTTA_WHITE, MaterialColor.TERRACOTTA_LIGHT_GRAY);
        DynamicWoodTypeRegistry.addWoodType(PsaroniusType, MaterialColor.TERRACOTTA_GREEN, MaterialColor.TERRACOTTA_GREEN);
    }

    public static class ConfiguredFeatureRegistries{

        public static void init(){

            System.out.println("registering placment and configured");

            ORE_FAUNA_FOSSIL_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.FAUNA_FOSSIL.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_FAUNA_FOSSIL.get().defaultBlockState()));
            ORE_AMBER_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.AMBER_ORE.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_AMBER_ORE.get().defaultBlockState()));
            ORE_ICE_SHARD_LIST = List.of(OreConfiguration.target(STONE_ORE_REPLACEABLES, ModBlocks.AMBER_ORE.get().defaultBlockState()), OreConfiguration.target(DEEPSLATE_ORE_REPLACEABLES, ModBlocks.DEEPSLATE_AMBER_ORE.get().defaultBlockState()));


            CONFIGURED_FLORA_FOSSIL = FeatureUtils.register(modid + ":flora_fossil_configured", FLORA_FOSSIL_ORE, new OreConfiguration(ORE_COAL_TARGET_LIST, 17));
            FLORA_FOSSIL_PLACEMENT = PlacementUtils.register(modid + ":placement_flora_fossil", CONFIGURED_FLORA_FOSSIL, commonOrePlacement(5, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64))));
            CONFIGURED_FAUNA_FOSSIL = FeatureUtils.register(modid + ":fauna_fossil_configured", Feature.ORE, new OreConfiguration(ORE_FAUNA_FOSSIL_LIST, 3));
            FAUNA_FOSSIL_PLACEMENT = PlacementUtils.register(modid + ":placement_fauna_fossil", CONFIGURED_FAUNA_FOSSIL, commonOrePlacement(3, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64))));

            CONFIGURED_AMBER_ORE = FeatureUtils.register(modid + ":amber_ore_configured", Feature.ORE, new OreConfiguration(ORE_AMBER_LIST, 3));
            CONFIGURED_ICE_SHARD_ORE = FeatureUtils.register(modid + ":ice_shard_ore_configured", Feature.ORE, new OreConfiguration(ORE_ICE_SHARD_LIST, 3));
            AMBER_ORE_PLACEMENT = PlacementUtils.register(modid + ":placement_amber_ore", CONFIGURED_AMBER_ORE, commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64))));
            ICE_SHARD_ORE_PLACEMENT = PlacementUtils.register(modid + ":placement_ice_shard_ore", CONFIGURED_ICE_SHARD_ORE, commonOrePlacement(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64))));

            CONFIGURED_SMALL_PETRIFIED_TREE = FeatureUtils.register(modid + ":configured_large_petrified_tree", PETRIFIED_TREE_GENERATOR, new PetrifiedTreeConfig(10, 0.2F));
            CONFIGURED_LARGE_PETRIFIED_TREE = FeatureUtils.register(modid + ":configured_small_petrified_tree", PETRIFIED_TREE_GENERATOR, new PetrifiedTreeConfig(15, 0.1F));
            PLACED_LARGE_PETRIFIED_TREE = PlacementUtils.register(modid + ":placed_small_petrified_tree", CONFIGURED_LARGE_PETRIFIED_TREE, chancedOrePlacment(HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64))));
            PLACED_SMALL_PETRIFIED_TREE = PlacementUtils.register(modid + ":placed_large_petrified_tree", CONFIGURED_SMALL_PETRIFIED_TREE, chancedOrePlacment(HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(64))));

        }

        private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
            return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
        }

        private static List<PlacementModifier> chancedOrePlacment(PlacementModifier p_195348_){
            return List.of(InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
        }

        private static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
            return orePlacement(CountPlacement.of(p_195344_), p_195345_);
        }
    }
    //code adapted from Charm
    public static void addBlocksToBlockEntity(BlockEntityType<?> type, Block... blocks) {
        addBlocksToBlockEntity(type, List.of(blocks));
    }
    public static void addBlocksToBlockEntity(BlockEntityType<?> type, List<Block> blocks) {
        Set<Block> typeBlocks = type.validBlocks;
        List<Block> mutable = new ArrayList<>(typeBlocks);

        for (Block block : blocks) {
            if (!mutable.contains(block))
                mutable.add(block);
        }

        type.validBlocks = new HashSet<>(mutable);
    }

}
