package net.gamma02.jurrasicworldreborn.common.items;

import net.gamma02.jurrasicworldreborn.Jurrasicworldreborn;
import net.gamma02.jurrasicworldreborn.common.blocks.ModBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.antlr.runtime.debug.DebugEventHub;

public class ModItems {

    public static DeferredRegister<Item> modItems = DeferredRegister.create(ForgeRegistries.ITEMS, Jurrasicworldreborn.modid);

    public static RegistryObject<Item> ARAUCARIA_SAPLING = modItems.register("araucaria_sapling", () -> new BlockItem(ModBlocks.AraucariaSapling.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static RegistryObject<Item> GINKGO_SAPLING = modItems.register("ginkgo_sapling", () -> new BlockItem(ModBlocks.GinkgoSapling.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static RegistryObject<Item> CALAMITES_SAPLING = modItems.register("calamites_sapling", () -> new BlockItem(ModBlocks.CalamitesSapling.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static RegistryObject<Item> PHEONIX_SAPLING = modItems.register("pheonix_sapling", () -> new BlockItem(ModBlocks.PheonixSapling.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public static RegistryObject<Item> PSARONIUS_SAPLING = modItems.register("psarons_sapling", () -> new BlockItem(ModBlocks.PsaroniusSapling.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));



}
