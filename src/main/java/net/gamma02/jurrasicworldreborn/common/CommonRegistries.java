package net.gamma02.jurrasicworldreborn.common;


import net.gamma02.jurrasicworldreborn.common.blocks.wood.DynamicWoodTypeRegistry;
import net.gamma02.jurrasicworldreborn.common.worldgen.OreVeinFeature;
import net.gamma02.jurrasicworldreborn.common.worldgen.tree.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

import static net.gamma02.jurrasicworldreborn.Jurrasicworldreborn.modid;
import static net.minecraft.data.worldgen.features.OreFeatures.DEEPSLATE_ORE_REPLACEABLES;
import static net.minecraft.data.worldgen.features.OreFeatures.STONE_ORE_REPLACEABLES;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonRegistries {
    //create the wood type for the ancient wood, there are a few.........
    public static WoodType AraucariaType = WoodType.register(WoodType.create("araucaria"));
    public static WoodType CalamitesType = WoodType.register(WoodType.create("calamites"));
    public static WoodType GinkgoType = WoodType.register(WoodType.create("ginkgo"));
    public static WoodType PhoenixType = WoodType.register(WoodType.create("phoenix"));
    public static WoodType PsaroniusType = WoodType.register(WoodType.create("psaronius"));


    public static DeferredRegister<Feature<?>> modFeatures = DeferredRegister.create(ForgeRegistries.FEATURES, modid);

    public static RegistryObject<Feature<OreConfiguration>> FLORA_FOSSIL_ORE = modFeatures.register("flora_fossil_ore", () -> new OreVeinFeature(OreConfiguration.CODEC));

    public static RegistryObject<Feature<NoneFeatureConfiguration>> AraucariaTreeFeature = modFeatures.register("araucaria_tree_feature", () -> new AraucariaTreeGenerator(NoneFeatureConfiguration.CODEC));

    public static RegistryObject<Feature<NoneFeatureConfiguration>> GinkgoTreeFeature = modFeatures.register("ginkgo_tree_feature", () -> new GinkgoTreeGenerator(NoneFeatureConfiguration.CODEC));

    public static RegistryObject<Feature<NoneFeatureConfiguration>> CalamitesTreeFeature = modFeatures.register("calamites_tree_feature", () -> new CalamitesTreeGenerator(NoneFeatureConfiguration.CODEC));

    public static RegistryObject<Feature<NoneFeatureConfiguration>> PhoenixTreeFeature = modFeatures.register("phoenix_tree_feature", () -> new PhoenixTreeGenerator(NoneFeatureConfiguration.CODEC));

    public static RegistryObject<Feature<NoneFeatureConfiguration>> PsaroniusTreeFeature = modFeatures.register("psaronius_tree_feature", () -> new PsaroniusTreeGenerator(NoneFeatureConfiguration.CODEC));








    public static void init(){
        DynamicWoodTypeRegistry.addWoodType(GinkgoType, MaterialColor.RAW_IRON, MaterialColor.PODZOL);
        DynamicWoodTypeRegistry.addWoodType(AraucariaType, MaterialColor.TERRACOTTA_LIGHT_GRAY, MaterialColor.STONE);
        DynamicWoodTypeRegistry.addWoodType(CalamitesType, MaterialColor.TERRACOTTA_LIGHT_GREEN, MaterialColor.TERRACOTTA_LIGHT_GREEN);
        DynamicWoodTypeRegistry.addWoodType(PhoenixType, MaterialColor.TERRACOTTA_WHITE, MaterialColor.TERRACOTTA_LIGHT_GRAY);
        DynamicWoodTypeRegistry.addWoodType(PsaroniusType, MaterialColor.TERRACOTTA_GREEN, MaterialColor.TERRACOTTA_GREEN);
    }

}
