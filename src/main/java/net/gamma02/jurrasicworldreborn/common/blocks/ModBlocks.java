package net.gamma02.jurrasicworldreborn.common.blocks;

import net.gamma02.jurrasicworldreborn.common.CommonRegistries;
import net.gamma02.jurrasicworldreborn.common.blocks.fossil.FloraFossil;
import net.gamma02.jurrasicworldreborn.common.blocks.wood.ModSaplingBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {


    public static DeferredRegister<Block> modBlocks = DeferredRegister.create(ForgeRegistries.BLOCKS, "jurrasicworldreborn");

    public static RegistryObject<ModSaplingBlock> AraucariaSapling = modBlocks.register("araucria_sapling", () -> new ModSaplingBlock(CommonRegistries.AraucariaTreeFeature, BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT)));

    public static RegistryObject<ModSaplingBlock> GinkgoSapling = modBlocks.register("ginkgo_sapling", () -> new ModSaplingBlock(CommonRegistries.GinkgoTreeFeature, BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT)));

    public static RegistryObject<ModSaplingBlock> CalamitesSapling = modBlocks.register("calamites_sapling", () -> new ModSaplingBlock(CommonRegistries.CalamitesTreeFeature, BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT)));

    public static RegistryObject<ModSaplingBlock> PheonixSapling = modBlocks.register("pheonix_sapling", () -> new ModSaplingBlock(CommonRegistries.PhoenixTreeFeature, BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT)));

    public static RegistryObject<ModSaplingBlock> PsaroniusSapling = modBlocks.register("psaronius_sapling", () -> new ModSaplingBlock(CommonRegistries.PsaroniusTreeFeature, BlockBehaviour.Properties.of(Material.REPLACEABLE_FIREPROOF_PLANT)));

    public static RegistryObject<FloraFossil> FLORA_FOSSIL = modBlocks.register("flora_fossil", () -> new FloraFossil(BlockBehaviour.Properties.of(Material.STONE)));
    public static RegistryObject<FloraFossil> DEEPSLATE_FLORA_FOSSIL = modBlocks.register("deepslate_flora_fossil", () -> new FloraFossil(BlockBehaviour.Properties.of(Material.STONE)));


    public static void register(IEventBus bus){
        modBlocks.register(bus);
    }


}
