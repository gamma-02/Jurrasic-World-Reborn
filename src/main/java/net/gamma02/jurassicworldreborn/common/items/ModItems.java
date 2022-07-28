package net.gamma02.jurassicworldreborn.common.items;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.world.entity.EntityType;
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

    public static HashMap<String, RegistryObject<Item>> BONES = new HashMap<>();
    public static HashMap<String, RegistryObject<Item>> MEATS = new HashMap<>();

    public static void registerBone(String name, Supplier<Item> sup, String dino){
        BONES.put(dino.indexOf(':') >= 0 ? dino  : "jurassicworldreborn:" + dino, modItems.register(name, sup));
    }
    public static void registerMeat(String name, Supplier<Item> sup, String dino){
        MEATS.put(dino.indexOf(':') >= 0 ? dino  : "jurassicworldreborn:" + dino, modItems.register(name, sup));
    }

    public static Item getBoneForDinosaur(EntityType<DinosaurEntity> type){
        return BONES.get(type.toString()).orElse(DEFAULT_BONE.get());
    }
    public static Item getMeatForDinosaur(EntityType<DinosaurEntity> type){
        return MEATS.get(type.toString()).orElse(DEFAULT_BONE.get());
    }


}
