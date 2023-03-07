package net.gamma02.jurassicworldreborn.common.items;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.items.misc.SwarmItem;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.lwjgl.system.CallbackI;

import java.util.HashMap;
import java.util.function.Supplier;

public class ModItems {
    public static void init(){

    }

    public static DeferredRegister<Item> modItems = DeferredRegister.create(ForgeRegistries.ITEMS, Jurassicworldreborn.modid);

    public static RegistryObject<Item> ARAUCARIA_SAPLING = modItems.register("araucaria_sapling", () -> new BlockItem(ModBlocks.AraucariaSapling.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static RegistryObject<Item> GINKGO_SAPLING = modItems.register("ginkgo_sapling", () -> new BlockItem(ModBlocks.GinkgoSapling.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static RegistryObject<Item> CALAMITES_SAPLING = modItems.register("calamites_sapling", () -> new BlockItem(ModBlocks.CalamitesSapling.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static RegistryObject<Item> PHEONIX_SAPLING = modItems.register("pheonix_sapling", () -> new BlockItem(ModBlocks.PheonixSapling.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static RegistryObject<Item> PSARONIUS_SAPLING = modItems.register("psarons_sapling", () -> new BlockItem(ModBlocks.PsaroniusSapling.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static RegistryObject<Item> PLASTER_AND_BANDAGE = modItems.register("plaster_and_bandage", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static RegistryObject<Item> MOSQUITO_AMBER = modItems.register("mosquito_amber", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static RegistryObject<Item> APHID_AMBER = modItems.register("aphid_amber", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static RegistryObject<Item> SEA_LAMPREY = modItems.register("sea_lamprey", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static RegistryObject<Item> ENCASED_FAUNA_FOSSIL = modItems.register("encased_fauna_item", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

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


    public static final RegistryObject<SwarmItem> PLANKTON = modItems.register("plankton", () -> new SwarmItem(ModBlocks.PLANKTON_SWARM.get(), new Item.Properties()));
    public static final RegistryObject<SwarmItem> KRILL = modItems.register("krill", () -> new SwarmItem(ModBlocks.KRILL_SWARM.get(), new Item.Properties()));



    public static HashMap<String, RegistryObject<Item>> BONES = new HashMap<>();
    public static HashMap<String, RegistryObject<Item>> MEATS = new HashMap<>();
    public static HashMap<String, RegistryObject<Item>> STEAKS = new HashMap<>();




    public static void registerBone(String name, Supplier<Item> sup, String dino){
        BONES.put(dino.indexOf(':') >= 0 ? dino  : "jurassicworldreborn:" + dino, modItems.register(name, sup));
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


}
