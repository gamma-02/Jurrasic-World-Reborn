package net.gamma02.jurrasicworldreborn.common;


import net.gamma02.jurrasicworldreborn.Jurrasicworldreborn;
import net.gamma02.jurrasicworldreborn.common.blocks.wood.DynamicWoodTypeRegistry;
import net.gamma02.jurrasicworldreborn.common.worldgen.tree.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.gamma02.jurrasicworldreborn.Jurrasicworldreborn.resource;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonRegistries {
    //create the wood type for the ancient wood, there are a few.........
    public static WoodType AraucariaType = WoodType.register(WoodType.create("araucaria"));
    public static WoodType CalamitesType = WoodType.register(WoodType.create("calamites"));
    public static WoodType GinkgoType = WoodType.register(WoodType.create("ginkgo"));
    public static WoodType PhoenixType = WoodType.register(WoodType.create("pheonix"));
    public static WoodType PsaroniusType = WoodType.register(WoodType.create("psaronius"));

    public static Feature<NoneFeatureConfiguration> AraucariaTreeFeature;

    public static Feature<NoneFeatureConfiguration> GinkgoTreeFeature;

    public static Feature<NoneFeatureConfiguration> CalamitesTreeFeature;

    public static Feature<NoneFeatureConfiguration> PheonixTreeFeature;

    public static Feature<NoneFeatureConfiguration> PsaroniusTreeFeature;








    public static void init(){
        DynamicWoodTypeRegistry.addWoodType(GinkgoType, MaterialColor.RAW_IRON, MaterialColor.PODZOL);
        DynamicWoodTypeRegistry.addWoodType(AraucariaType, MaterialColor.TERRACOTTA_LIGHT_GRAY, MaterialColor.STONE);
        DynamicWoodTypeRegistry.addWoodType(CalamitesType, MaterialColor.TERRACOTTA_LIGHT_GREEN, MaterialColor.TERRACOTTA_LIGHT_GREEN);
        DynamicWoodTypeRegistry.addWoodType(PhoenixType, MaterialColor.TERRACOTTA_WHITE, MaterialColor.TERRACOTTA_LIGHT_GRAY);
        DynamicWoodTypeRegistry.addWoodType(PsaroniusType, MaterialColor.TERRACOTTA_GREEN, MaterialColor.TERRACOTTA_GREEN);
    }



    @SubscribeEvent
    public static void RegisterBlocks(final RegistryEvent.Register<Block> event){



    }

    @SubscribeEvent
    public static void RegisterItems(final RegistryEvent.Register<Item> event){



    }

    @SubscribeEvent
    public static void RegisterFeatures(final RegistryEvent.Register<Feature<?>> evt){

        AraucariaTreeFeature.setRegistryName(resource("araucaria_tree_feature"));
        GinkgoTreeFeature.setRegistryName(resource("ginkgo_tree_feature"));
        CalamitesTreeFeature.setRegistryName(resource("calamites_tree_feature"));
        PheonixTreeFeature.setRegistryName(resource("pheonix_tree_feature"));
        PsaroniusTreeFeature.setRegistryName(resource("psaronius_tree_feature"));


        evt.getRegistry().registerAll(AraucariaTreeFeature, GinkgoTreeFeature, CalamitesTreeFeature, PheonixTreeFeature, PsaroniusTreeFeature);




    }

    static{
        AraucariaTreeFeature = new AraucariaTreeGenerator(NoneFeatureConfiguration.CODEC);
        GinkgoTreeFeature = new GinkgoTreeGenerator(NoneFeatureConfiguration.CODEC);
        CalamitesTreeFeature = new CalamitesTreeGenerator(NoneFeatureConfiguration.CODEC);
        PheonixTreeFeature = new PhoenixTreeGenerator(NoneFeatureConfiguration.CODEC);
        PsaroniusTreeFeature = new PsaroniusTreeGenerator(NoneFeatureConfiguration.CODEC);
    }
}
