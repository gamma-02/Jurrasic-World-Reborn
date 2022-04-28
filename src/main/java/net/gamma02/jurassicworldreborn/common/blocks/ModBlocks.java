package net.gamma02.jurassicworldreborn.common.blocks;

import net.gamma02.jurassicworldreborn.common.CommonRegistries;
import net.gamma02.jurassicworldreborn.common.blocks.fossil.FaunaFossil;
import net.gamma02.jurassicworldreborn.common.blocks.fossil.FloraFossil;
import net.gamma02.jurassicworldreborn.common.blocks.machines.cleaner.CleanerBlock;
import net.gamma02.jurassicworldreborn.common.blocks.wood.ModSaplingBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.lwjgl.system.CallbackI;

public class ModBlocks {


    public static DeferredRegister<Block> modBlocks = DeferredRegister.create(ForgeRegistries.BLOCKS, "jurassicworldreborn");

    public static RegistryObject<ModSaplingBlock> AraucariaSapling = modBlocks.register("araucria_sapling", () -> new ModSaplingBlock(CommonRegistries.AraucariaTreeFeature, BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT)));

    public static RegistryObject<ModSaplingBlock> GinkgoSapling = modBlocks.register("ginkgo_sapling", () -> new ModSaplingBlock(CommonRegistries.GinkgoTreeFeature, BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT)));

    public static RegistryObject<ModSaplingBlock> CalamitesSapling = modBlocks.register("calamites_sapling", () -> new ModSaplingBlock(CommonRegistries.CalamitesTreeFeature, BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT)));

    public static RegistryObject<ModSaplingBlock> PheonixSapling = modBlocks.register("pheonix_sapling", () -> new ModSaplingBlock(CommonRegistries.PhoenixTreeFeature, BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT)));

    public static RegistryObject<ModSaplingBlock> PsaroniusSapling = modBlocks.register("psaronius_sapling", () -> new ModSaplingBlock(CommonRegistries.PsaroniusTreeFeature, BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT)));

    public static RegistryObject<FloraFossil> FLORA_FOSSIL = modBlocks.register("flora_fossil", () -> new FloraFossil(BlockBehaviour.Properties.of(Material.STONE)));
    public static RegistryObject<FloraFossil> DEEPSLATE_FLORA_FOSSIL = modBlocks.register("deepslate_flora_fossil", () -> new FloraFossil(BlockBehaviour.Properties.of(Material.STONE)));

    public static RegistryObject<FaunaFossil> FAUNA_FOSSIL = modBlocks.register("fauna_fossil", () -> new FaunaFossil(BlockBehaviour.Properties.of(Material.STONE)));

    public static RegistryObject<FaunaFossil> DEEPSLATE_FAUNA_FOSSIL = modBlocks.register("deepslate_fauna_fossil", () -> new FaunaFossil(BlockBehaviour.Properties.of(Material.STONE)));

    public static RegistryObject<Block> ENCASED_FAUNA_FOSSIL = modBlocks.register("encased_fauna_fossil", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(5.0F, 0.0F)));//creeper go boom

    public static RegistryObject<Block> AMBER_ORE = modBlocks.register("amber_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops()));

    public static RegistryObject<Block> ICE_SHARD_ORE = modBlocks.register("ice_shard_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops()));

    public static RegistryObject<Block> DEEPSLATE_AMBER_ORE = modBlocks.register("deepslate_amber_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops()));

    public static RegistryObject<Block> DEEPSLATE_ICE_SHARD_ORE = modBlocks.register("deepslate_ice_shard_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops()));

    public static RegistryObject<CleanerBlock> CLEANER_BLOCK = modBlocks.register("cleaner_block", () -> new CleanerBlock(BlockBehaviour.Properties.of(Material.STONE)));



    public static void register(IEventBus bus){
        modBlocks.register(bus);
    }


}
