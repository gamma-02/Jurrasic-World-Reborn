package net.gamma02.jurassicworldreborn.common.blocks;

import net.gamma02.jurassicworldreborn.common.CommonRegistries;
import net.gamma02.jurassicworldreborn.common.blocks.ancientplants.*;
import net.gamma02.jurassicworldreborn.common.blocks.ancientplants.moss.AncientMossCarpet;
import net.gamma02.jurassicworldreborn.common.blocks.ancientplants.moss.PeatBlock;
import net.gamma02.jurassicworldreborn.common.blocks.entities.ActionFigureBlock;
import net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNACombinatorHybridizer.DNACombinatorHybridizerBlock;
import net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNAExtractor.DNAExtractorBlock;
import net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNASequencer.DNASequencerBlock;
import net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNASynthesizer.DNASynthesizerBlock;
import net.gamma02.jurassicworldreborn.common.blocks.entities.Embryoncis.EmbryoCalcificationMachine.EmbryoCalcificationMachineBlock;
import net.gamma02.jurassicworldreborn.common.blocks.entities.Embryoncis.EmbryonicMachine.EmbryonicMachineBlock;
import net.gamma02.jurassicworldreborn.common.blocks.entities.bugcrate.BugCrate;
import net.gamma02.jurassicworldreborn.common.blocks.entities.cleaner.CleanerBlock;
import net.gamma02.jurassicworldreborn.common.blocks.entities.cultivator.CultivatorBottomBlock;
import net.gamma02.jurassicworldreborn.common.blocks.entities.cultivator.CultivatorTopBlock;
import net.gamma02.jurassicworldreborn.common.blocks.entities.feeder.FeederBlock;
import net.gamma02.jurassicworldreborn.common.blocks.entities.fence.ElectricFenceBaseBlock;
import net.gamma02.jurassicworldreborn.common.blocks.entities.fence.ElectricFencePoleBlock;
import net.gamma02.jurassicworldreborn.common.blocks.entities.fence.ElectricFenceWireBlock;
import net.gamma02.jurassicworldreborn.common.blocks.entities.fence.FenceType;
import net.gamma02.jurassicworldreborn.common.blocks.entities.grinder.FossilGrinderBlock;
import net.gamma02.jurassicworldreborn.common.blocks.entities.incubator.IncubatorBlock;
import net.gamma02.jurassicworldreborn.common.blocks.entities.paleobale.PaleoBaleBlock;
import net.gamma02.jurassicworldreborn.common.blocks.fossil.*;
import net.gamma02.jurassicworldreborn.common.blocks.parkBlocks.TourRailBlock;
import net.gamma02.jurassicworldreborn.common.blocks.wood.ModSaplingBlock;
import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.minecraft.world.level.block.BaseCoralPlantBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {


    public static DeferredRegister<Block> modBlocks = DeferredRegister.create(ForgeRegistries.BLOCKS, "jurassicworldreborn");

    public static RegistryObject<ModSaplingBlock> AraucariaSapling = modBlocks.register("araucaria_sapling", () -> new ModSaplingBlock(() -> CommonRegistries.AraucariaTreeFeature, BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));

    public static RegistryObject<ModSaplingBlock> GinkgoSapling = modBlocks.register("ginkgo_sapling", () -> new ModSaplingBlock(() -> CommonRegistries.GinkgoTreeFeature, BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));

    public static RegistryObject<ModSaplingBlock> CalamitesSapling = modBlocks.register("calamites_sapling", () -> new ModSaplingBlock(() -> CommonRegistries.CalamitesTreeFeature, BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));

    public static RegistryObject<ModSaplingBlock> PhoenixSapling = modBlocks.register("phoenix_sapling", () -> new ModSaplingBlock(() -> CommonRegistries.PhoenixTreeFeature, BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));

    public static RegistryObject<ModSaplingBlock> PsaroniusSapling = modBlocks.register("psaronius_sapling", () -> new ModSaplingBlock(() -> CommonRegistries.PsaroniusTreeFeature, BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));

    public static RegistryObject<FloraFossil> FLORA_FOSSIL = modBlocks.register("flora_fossil", () -> new FloraFossil(BlockBehaviour.Properties.of(Material.STONE)));
    public static RegistryObject<FloraFossil> DEEPSLATE_FLORA_FOSSIL = modBlocks.register("deepslate_flora_fossil", () -> new FloraFossil(BlockBehaviour.Properties.of(Material.STONE)));

    public static RegistryObject<FaunaFossil> FAUNA_FOSSIL = modBlocks.register("fauna_fossil", () -> new FaunaFossil(BlockBehaviour.Properties.of(Material.STONE)));

    public static RegistryObject<FaunaFossil> DEEPSLATE_FAUNA_FOSSIL = modBlocks.register("deepslate_fauna_fossil", () -> new FaunaFossil(BlockBehaviour.Properties.of(Material.STONE)));

    public static RegistryObject<Block> ENCASED_FAUNA_FOSSIL = modBlocks.register("encased_fauna_fossil", () -> new EncasedFaunaFossilBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(5.0F, 0.0F)));//creeper go boom

    public static RegistryObject<Block> AMBER_ORE = modBlocks.register("amber_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops()));

    public static RegistryObject<Block> ICE_SHARD_ORE = modBlocks.register("ice_shard_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops()));

    public static RegistryObject<Block> DEEPSLATE_AMBER_ORE = modBlocks.register("deepslate_amber_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops()));

    public static RegistryObject<Block> DEEPSLATE_ICE_SHARD_ORE = modBlocks.register("deepslate_ice_shard_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops()));

    public static RegistryObject<CleanerBlock> CLEANER_BLOCK = modBlocks.register("cleaning_station", () -> new CleanerBlock(BlockBehaviour.Properties.of(Material.STONE)));

    public static RegistryObject<FossilGrinderBlock> FOSSIL_GRINDER = modBlocks.register("fossil_grinder", () -> new FossilGrinderBlock(defaultMachine()));

    public static RegistryObject<DNASequencerBlock> DNA_SEQUENCER = modBlocks.register("dna_sequencer", () -> new DNASequencerBlock(defaultMachine()));

    public static RegistryObject<DNASynthesizerBlock> DNA_SYNTHESIZER = modBlocks.register("dna_synthesizer", () -> new DNASynthesizerBlock(defaultMachine()));
    public static RegistryObject<EmbryoCalcificationMachineBlock> CALCIFICATION_MACHINE = modBlocks.register("embryo_calcification_machine", () -> new EmbryoCalcificationMachineBlock(BlockBehaviour.Properties.of(Material.STONE)));


    public static RegistryObject<EmbryonicMachineBlock> EMBRYONIC_MACHINE = modBlocks.register("embryonic_machine", () -> new EmbryonicMachineBlock(defaultMachine()));

    public static RegistryObject<IncubatorBlock> INCUBATOR_MACHINE = modBlocks.register("incubator", () -> new IncubatorBlock(BlockBehaviour.Properties.of(Material.METAL).strength(2.1f)));//hehe

    public static RegistryObject<DNAExtractorBlock> DNA_EXTRACTOR = modBlocks.register("dna_extractor", () -> new DNAExtractorBlock(defaultMachine()));

    public static RegistryObject<DNACombinatorHybridizerBlock> DNA_COMBINER = modBlocks.register("dna_combinator_hybridizer", () -> new DNACombinatorHybridizerBlock(defaultMachine()));

    public static RegistryObject<GypsumStoneBlock> GYPSUM_STONE = modBlocks.register("gypsum_stone", () -> new GypsumStoneBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.5f).explosionResistance(1.5f)));

    public static RegistryObject<Block> GYPSUM_COBBLESTONE = modBlocks.register("gypsum_cobblestone", () -> new Block(defaultStone()));

    public static RegistryObject<Block> GYPSUM_BRICKS = modBlocks.register("gypsum_bricks", () -> new Block(defaultStone().strength(2.0f)));

    public static final RegistryObject<Block> REINFORCED_STONE = modBlocks.register("reinforced_stone", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(2.0F)));
    public static final RegistryObject<Block> REINFORCED_BRICKS = modBlocks.register("reinforced_bricks", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(3.0F)));

    public static RegistryObject<CultivatorTopBlock> CULTIVATOR_TOP = modBlocks.register("cultivate_top", () -> new CultivatorTopBlock(defaultMachine()));

    public static RegistryObject<CultivatorBottomBlock> CULTIVATOR_BOTTOM = modBlocks.register("cultivator_bottom", () -> new CultivatorBottomBlock(defaultMachine()));

    public static RegistryObject<ClearGlassBlock> CLEAR_GLASS = modBlocks.register("clear_glass", () -> new ClearGlassBlock(BlockBehaviour.Properties.of(Material.GLASS).strength(0.2F)));

    public static RegistryObject<FossilizedTrackwayBlock> FOSSILIZED_TRACKWAY = modBlocks.register("fossilized_trackway", () -> new FossilizedTrackwayBlock(defaultStone()));

    public static RegistryObject<NestFossilBlock> NEST_FOSSIL = modBlocks.register("nest_fossil", () -> new NestFossilBlock(false, defaultStone()));
    public static RegistryObject<NestFossilBlock> ENCASED_NEST_FOSSIL = modBlocks.register("encased_nest_fossil", () -> new NestFossilBlock(true, defaultMachine()));

    //plants
    public static RegistryObject<ImplimentedAncientPlant> SMALL_ROYAL_FERN = modBlocks.register("small_royal_fern", ImplimentedAncientPlant::new);
    public static RegistryObject<ImplimentedAncientPlant> SMALL_CHAIN_FERN = modBlocks.register("small_chain_fern", ImplimentedAncientPlant::new);

    public static RegistryObject<ImplimentedAncientPlant> SMALL_CYCAD = modBlocks.register("small_cycad", ImplimentedAncientPlant::new);

    public static RegistryObject<ImplimentedAncientPlant> CYCADEOIDEA = modBlocks.register("cycadeoidea", ImplimentedAncientPlant::new);
    public static RegistryObject<AncientPlantBlock> CRY_PANSY = modBlocks.register("cry_pansy", AncientPlantBlock::new);

    public static RegistryObject<ImplimentedAncientPlant> SCALY_TREE_FERN = modBlocks.register("scaly_tree_fern", () -> new ImplimentedAncientPlant(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission()));

    public static RegistryObject<ImplimentedAncientPlant> ZAMITES = modBlocks.register("zamites", () -> new ImplimentedAncientPlant(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission()));

    public static RegistryObject<ImplimentedAncientPlant> DICKSONIA = modBlocks.register("dicksonia", () -> new ImplimentedAncientPlant(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission()));
    public static RegistryObject<ImplimentedAncientPlant> DICROIDIUM_ZUBERI = modBlocks.register("dicroidium_zuberi", () -> new ImplimentedAncientPlant(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission()));
    public static RegistryObject<AncientCrop> AJUGINUCULA_SMITHII = modBlocks.register("ajuginucula_smithii", () -> new SevenStageAncientCrop(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission()));
    public static RegistryObject<AncientCrop> WILD_ONION = modBlocks.register("wild_onion_plant", () -> new AncientCrop(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission()));
    public static RegistryObject<ImplimentedAncientPlant> GRACILARIA = modBlocks.register("gracilaria", () -> new ImplimentedAncientPlant(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission()));
    public static RegistryObject<ImplimentedAncientPlant> DICTYOPHYLLUM = modBlocks.register("dictyophyllum", () -> new ImplimentedAncientPlant(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission()));
    public static RegistryObject<ImplimentedAncientPlant> WEST_INDIAN_LILAC = modBlocks.register("west_indian_lilac", () -> new ImplimentedAncientPlant(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission()));
    public static RegistryObject<ImplimentedAncientPlant> SERENNA_VERIFORMANS = modBlocks.register("serenna_veriformans", () -> new ImplimentedAncientPlant(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission()));
    public static RegistryObject<SmallPlantBlock> LADINIA_SIMPLEX = modBlocks.register("ladinia_simplex", () -> new SmallPlantBlock(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission()));
    public static RegistryObject<ImplimentedAncientPlant> ORONTIUM_MACKII = modBlocks.register("orontium_mackii", () -> new ImplimentedAncientPlant(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission()));
    public static RegistryObject<ImplimentedAncientPlant> UMALTOLEPIS = modBlocks.register("umaltolepis", () -> new ImplimentedAncientPlant(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission()));
    public static RegistryObject<ImplimentedAncientPlant> LIRIODENDRITES = modBlocks.register("liriodendrites", () -> new ImplimentedAncientPlant(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission()));
    public static RegistryObject<ImplimentedAncientPlant> RAPHAELIA = modBlocks.register("raphaelia", () -> new ImplimentedAncientPlant(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission()));
    public static RegistryObject<ImplimentedAncientPlant> ENCEPHALARTOS = modBlocks.register("encephalartos", () -> new ImplimentedAncientPlant(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission()));
    public static RegistryObject<AncientCrop> WILD_POTATO_PLANT = modBlocks.register("wild_potato_plant", () -> new AncientCrop(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission()));
    public static RegistryObject<AncientCrop> RHAMNUS_SALICIFOLIUS_PLANT = modBlocks.register("rhamnus_salicifolius", () -> new SevenStageAncientCrop(BlockBehaviour.Properties.of(Material.REPLACEABLE_PLANT).noCollission()));
    public static RegistryObject<SmallPlantBlock> CINNAMON_FERN = modBlocks.register("cinnamon_fern", SmallPlantBlock::new);
    public static RegistryObject<SmallPlantBlock> BRISTLE_FERN = modBlocks.register("bristle_fern", SmallPlantBlock::new);
    public static RegistryObject<DoublePlantBlock> TEMPSKYA = modBlocks.register("tempskya", () -> new DoublePlantBlock(BlockBehaviour.Properties.of(Material.PLANT).dynamicShape()));
    public static RegistryObject<SmallPlantBlock> WOOLLY_STALKED_BEGONIA = modBlocks.register("woolly_stalked_begonia", SmallPlantBlock::new);
    public static RegistryObject<SmallPlantBlock> LARGESTIPULE_LEATHER_ROOT = modBlocks.register("largestipule_leather_root", SmallPlantBlock::new);
    public static RegistryObject<DoublePlantBlock> RHACOPHYTON = modBlocks.register("rhacophyton", DoublePlantBlock::new);
    public static RegistryObject<DoublePlantBlock> GRAMINIDITES_BAMBUSOIDES = modBlocks.register("graminidites_bambusoides", DoublePlantBlock::new);
    public static RegistryObject<DoublePlantBlock> HELICONIA = modBlocks.register("heliconia", DoublePlantBlock::new);
    public static RegistryObject<PeatBlock> PEAT = modBlocks.register("peat", () -> new PeatBlock(defaultMoss().randomTicks()));
    public static RegistryObject<Block> PEAT_MOSS = modBlocks.register("peat_moss", () -> new Block(defaultMoss().randomTicks()));
    public static RegistryObject<AncientMossCarpet> MOSS = modBlocks.register("moss", AncientMossCarpet::new);

    public static RegistryObject<BaseCoralPlantBlock> DEAD_ENALLHELIA = modBlocks.register("dead_enallhelia", () -> new BaseCoralPlantBlock(defaultDeadCoral()));
    public static RegistryObject<BaseCoralPlantBlock> DEAD_AULOPORA = modBlocks.register("dead_aulopora", () -> new BaseCoralPlantBlock(defaultDeadCoral()));
    public static RegistryObject<BaseCoralPlantBlock> DEAD_CLADOCHONUS = modBlocks.register("dead_cladochonus", () -> new BaseCoralPlantBlock(defaultDeadCoral()));
    public static RegistryObject<BaseCoralPlantBlock> DEAD_LITHOSTROTION = modBlocks.register("dead_lithostrotion", () -> new BaseCoralPlantBlock(defaultDeadCoral()));
    public static RegistryObject<BaseCoralPlantBlock> DEAD_STYLOPHYLLOPSIS = modBlocks.register("dead_stylophyllopsis", () -> new BaseCoralPlantBlock(defaultDeadCoral()));
    public static RegistryObject<BaseCoralPlantBlock> DEAD_HIPPURITES_RADIOSUS = modBlocks.register("dead_hippurites_radiosus", () -> new BaseCoralPlantBlock(defaultDeadCoral()));
    public static RegistryObject<AncientCoralBlock> ENALLHELIA = modBlocks.register("enallhelia", () -> new AncientCoralBlock(DEAD_ENALLHELIA.get(), defaultAncientCoral()));
    public static RegistryObject<AncientCoralBlock> AULOPORA = modBlocks.register("aulopora", () -> new AncientCoralBlock(DEAD_AULOPORA.get(), defaultAncientCoral()));
    public static RegistryObject<AncientCoralBlock> CLADOCHONUS = modBlocks.register("cladochonus", () -> new AncientCoralBlock(DEAD_CLADOCHONUS.get(), defaultAncientCoral()));
    public static RegistryObject<AncientCoralBlock> LITHOSTROTION = modBlocks.register("lithostrotion", () -> new AncientCoralBlock(DEAD_LITHOSTROTION.get(), defaultAncientCoral()));
    public static RegistryObject<AncientCoralBlock> STYLOPHYLLOPSIS = modBlocks.register("stylophyllopsis", () -> new AncientCoralBlock(DEAD_STYLOPHYLLOPSIS.get(), defaultAncientCoral()));
    public static RegistryObject<AncientCoralBlock> HIPPURITES_RADIOSUS = modBlocks.register("hippurites_radiosus", () -> new AncientCoralBlock(DEAD_HIPPURITES_RADIOSUS.get(), defaultAncientCoral()));

    public static RegistryObject<FeederBlock> FEEDER = modBlocks.register("feeder", () -> new FeederBlock(defaultMachine()));
    public static RegistryObject<BugCrate> BUG_CRATE = modBlocks.register("bug_crate", BugCrate::new);

    public static RegistryObject<SwarmBlock> KRILL_SWARM = modBlocks.register("krill_swarm", () -> new SwarmBlock(ModItems.KRILL, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().randomTicks()));
    public static RegistryObject<SwarmBlock> PLANKTON_SWARM = modBlocks.register("plankton_swarm", () -> new SwarmBlock(ModItems.PLANKTON, BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().randomTicks()));

    public static RegistryObject<TourRailBlock> TOUR_RAIL = modBlocks.register("tour_rail", () -> new TourRailBlock(BlockBehaviour.Properties.of(Material.HEAVY_METAL).requiresCorrectToolForDrops().strength(1), TourRailBlock.SpeedType.NONE));

    public static RegistryObject<PaleoBaleBlock> PALEO_BALE_CYCADEOIDEA = modBlocks.register("paleo_bale_cycadeoidea", () -> new PaleoBaleBlock(PaleoBaleBlock.Variant.CYCADEOIDEA, BlockBehaviour.Properties.of(Material.GRASS).sound(SoundType.GRASS)));
    public static RegistryObject<PaleoBaleBlock> PALEO_BALE_CYCAD = modBlocks.register("paleo_bale_cycad", () -> new PaleoBaleBlock(PaleoBaleBlock.Variant.CYCAD, BlockBehaviour.Properties.of(Material.GRASS).sound(SoundType.GRASS)));
    public static RegistryObject<PaleoBaleBlock> PALEO_BALE_FERN = modBlocks.register("paleo_bale_fern", () -> new PaleoBaleBlock(PaleoBaleBlock.Variant.FERN, BlockBehaviour.Properties.of(Material.GRASS).sound(SoundType.GRASS)));
    public static RegistryObject<PaleoBaleBlock> PALEO_BALE_LEAVES = modBlocks.register("paleo_bale_leaves", () -> new PaleoBaleBlock(PaleoBaleBlock.Variant.LEAVES, BlockBehaviour.Properties.of(Material.GRASS).sound(SoundType.GRASS)));
    public static RegistryObject<PaleoBaleBlock> PALEO_BALE_OTHER = modBlocks.register("paleo_bale_other", () -> new PaleoBaleBlock(PaleoBaleBlock.Variant.OTHER, BlockBehaviour.Properties.of(Material.GRASS).sound(SoundType.GRASS)));
    public static RegistryObject<ElectricFenceBaseBlock> LOW_SECURITY_FENCE_BASE = modBlocks.register("low_security_fence_base", () -> new ElectricFenceBaseBlock(FenceType.LOW, defaultMachine()));
    public static RegistryObject<ElectricFenceBaseBlock> MED_SECURITY_FENCE_BASE = modBlocks.register("med_security_fence_base", () -> new ElectricFenceBaseBlock(FenceType.MED, defaultMachine()));
    public static RegistryObject<ElectricFenceBaseBlock> HIGH_SECURITY_FENCE_BASE = modBlocks.register("high_security_fence_base", () -> new ElectricFenceBaseBlock(FenceType.HIGH, defaultMachine()));

    public static RegistryObject<ElectricFenceWireBlock> LOW_SECURITY_FENCE_WIRE = modBlocks.register("low_security_fence_wire", () -> new ElectricFenceWireBlock(FenceType.LOW, defaultMachine()));
    public static RegistryObject<ElectricFenceWireBlock> MED_SECURITY_FENCE_WIRE = modBlocks.register("med_security_fence_wire", () -> new ElectricFenceWireBlock(FenceType.MED, defaultMachine()));
    public static RegistryObject<ElectricFenceWireBlock> HIGH_SECURITY_FENCE_WIRE = modBlocks.register("high_security_fence_wire", () -> new ElectricFenceWireBlock(FenceType.HIGH, defaultMachine()));

    public static RegistryObject<ElectricFencePoleBlock> LOW_SECURITY_FENCE_POLE = modBlocks.register("low_security_fence_pole", () -> new ElectricFencePoleBlock(FenceType.LOW, defaultMachine()));
    public static RegistryObject<ElectricFencePoleBlock> MED_SECURITY_FENCE_POLE = modBlocks.register("med_security_fence_pole", () -> new ElectricFencePoleBlock(FenceType.MED, defaultMachine()));
    public static RegistryObject<ElectricFencePoleBlock> HIGH_SECURITY_FENCE_POLE = modBlocks.register("high_security_fence_pole", () -> new ElectricFencePoleBlock(FenceType.HIGH, defaultMachine()));

    public static RegistryObject<ActionFigureBlock> DISPLAY_BLOCK = modBlocks.register("display_block", () -> new ActionFigureBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(0.0F, 0.0F).randomTicks().sound(SoundType.WOOD)));











    public static void register(IEventBus bus){
        modBlocks.register(bus);
        //today I learned that DeferredRegister#getEntries() is a method
        //that's gonna be useful for items
        //really useful
        //-gamma
    }

    public static BlockBehaviour.Properties defaultMachine(){
        return BlockBehaviour.Properties.of(Material.STONE).strength(2.0F);
    }

    public static BlockBehaviour.Properties defaultStone(){
        return BlockBehaviour.Properties.of(Material.STONE).strength(1.5f).explosionResistance(1.5f).requiresCorrectToolForDrops();
    }

    public static BlockBehaviour.Properties defaultPlant(){
        return BlockBehaviour.Properties.of(Material.PLANT).instabreak().randomTicks();
    }

    public static BlockBehaviour.Properties defaultMoss() {
        return BlockBehaviour.Properties.of(Material.MOSS, MaterialColor.COLOR_GREEN).strength(0.1F).sound(SoundType.MOSS);
    }

    public static BlockBehaviour.Properties defaultAncientCoral() {
        return BlockBehaviour.Properties.of(Material.WATER_PLANT, MaterialColor.COLOR_BLUE).noCollission().instabreak().sound(SoundType.WET_GRASS);
    }
    public static BlockBehaviour.Properties defaultDeadCoral(){
        return BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_GRAY).requiresCorrectToolForDrops().noCollission().instabreak();
    }
}
