package net.gamma02.jurassicworldreborn.common.blocks.entities;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNACombinatorHybridizer.DNACombinatorHybridizerBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNACombinatorHybridizer.DNACombinatorHybridizerMenu;
import net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNAExtractor.DNAExtractorBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNAExtractor.DNAExtractorMenu;
import net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNASequencer.DNASequencerBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNASequencer.DNASequencerMenu;
import net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNASynthesizer.DNASynthesizerBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNASynthesizer.DNASynthesizerMenu;
import net.gamma02.jurassicworldreborn.common.blocks.entities.cleaner.CleanerBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.cleaner.CleanerMenu;
import net.gamma02.jurassicworldreborn.common.blocks.entities.fence.ElectricFenceBaseBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.fence.ElectricFencePoleBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.fence.ElectricFenceWireBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.grinder.FossilGrinderBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.grinder.FossilGrinderMenu;
import net.gamma02.jurassicworldreborn.common.blocks.entities.incubator.IncubatorBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.incubator.IncubatorMenu;
import net.gamma02.jurassicworldreborn.common.blocks.fossil.FaunaFossilBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.parkBlocks.TourRailBlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;

import static net.gamma02.jurassicworldreborn.Jurassicworldreborn.modid;

public class ModBlockEntities<T extends BlockEntity> {

    public static DeferredRegister<BlockEntityType<?>> modBlockEntities = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, modid);

    public static RegistryObject<BlockEntityType<CleanerBlockEntity>> CLEANING_STATION = modBlockEntities.register("cleaning_station", () -> BlockEntityType.Builder.of(CleanerBlockEntity::new, ModBlocks.CLEANER_BLOCK.get()).build(null));
    public static RegistryObject<BlockEntityType<EncasedFaunaFossilBlockEntity>> ENCASED_FAUNA_FOSSIL = modBlockEntities.register("encased_fossil", () -> BlockEntityType.Builder.of(EncasedFaunaFossilBlockEntity::new, ModBlocks.FAUNA_FOSSIL.get(), ModBlocks.DEEPSLATE_FAUNA_FOSSIL.get(), ModBlocks.ENCASED_FAUNA_FOSSIL.get()).build(null));

    public static RegistryObject<BlockEntityType<DNACombinatorHybridizerBlockEntity>> DNA_COMBINATOR_HYBRIDIZER = modBlockEntities.register("dna_combinator_hybridizer", () -> BlockEntityType.Builder.of(DNACombinatorHybridizerBlockEntity::new, ModBlocks.DNA_COMBINER.get()).build(null));

    public static RegistryObject<BlockEntityType<TourRailBlockEntity>> TOUR_RAIL_BLOCK_ENTITY = modBlockEntities.register("tour_rail_block_entity", () -> {
        return BlockEntityType.Builder.of(TourRailBlockEntity::new, ModBlocks.TOUR_RAIL.get()).build(null);
    });

    public static RegistryObject<BlockEntityType<FaunaFossilBlockEntity>> FAUNA_FOSSIL_BLOCK_ENTITY = modBlockEntities.register("fauna_fossil_block_entity", () -> BlockEntityType.Builder.of(FaunaFossilBlockEntity::new, ModBlocks.FAUNA_FOSSIL.get(), ModBlocks.DEEPSLATE_FAUNA_FOSSIL.get()).build(null));

    public static RegistryObject<BlockEntityType<DNAExtractorBlockEntity>> DNA_EXTRACTOR_BLOCK_ENTITY = modBlockEntities.register("dna_extractor_block_entity", () -> BlockEntityType.Builder.of(DNAExtractorBlockEntity::new, ModBlocks.DNA_EXTRACTOR.get()).build(null));
    public static RegistryObject<BlockEntityType<ElectricFenceBaseBlockEntity>> BASE_FENCE_BLOCK_ENTITY = modBlockEntities.register("base_fence_block_entity", () -> BlockEntityType.Builder.of(ElectricFenceBaseBlockEntity::new, ModBlocks.LOW_SECURITY_FENCE_BASE.get(), ModBlocks.HIGH_SECURITY_FENCE_BASE.get(), ModBlocks.MED_SECURITY_FENCE_BASE.get()).build(null));

    public static RegistryObject<BlockEntityType<ElectricFenceWireBlockEntity>> WIRE_FENCE_BLOCK_ENTITY = modBlockEntities.register("wire_fence_block_entity", () -> BlockEntityType.Builder.of(ElectricFenceWireBlockEntity::new, ModBlocks.LOW_SECURITY_FENCE_WIRE.get(), ModBlocks.HIGH_SECURITY_FENCE_WIRE.get(), ModBlocks.MED_SECURITY_FENCE_WIRE.get()).build(null));

    public static RegistryObject<BlockEntityType<ElectricFencePoleBlockEntity>> POLE_FENCE_BLOCK_ENTITY = modBlockEntities.register("pole_fence_block_entity", () -> BlockEntityType.Builder.of(ElectricFencePoleBlockEntity::new, ModBlocks.LOW_SECURITY_FENCE_POLE.get(), ModBlocks.MED_SECURITY_FENCE_POLE.get(), ModBlocks.HIGH_SECURITY_FENCE_POLE.get()).build(null));

    public static RegistryObject<BlockEntityType<ActionFigureBlockEntity>> DISPLAY_BLOCK_ENTITY = modBlockEntities.register("display_block_entity", () -> BlockEntityType.Builder.of(ActionFigureBlockEntity::new, ModBlocks.DISPLAY_BLOCK.get()).build(null));

    public static RegistryObject<BlockEntityType<IncubatorBlockEntity>> INCUBATOR_BLOCK_ENTITY = modBlockEntities.register("incubator_block_entity", () -> BlockEntityType.Builder.of(IncubatorBlockEntity::new, ModBlocks.INCUBATOR_MACHINE.get()).build(null));

    public static RegistryObject<BlockEntityType<FossilGrinderBlockEntity>> FOSSIL_GRINDER_BLOCK_ENTITY = modBlockEntities.register("fossil_grinder_block_entity", () -> BlockEntityType.Builder.of(FossilGrinderBlockEntity::new, ModBlocks.FOSSIL_GRINDER.get()).build(null));

    public static RegistryObject<BlockEntityType<DNASequencerBlockEntity>> DNA_SEQUENCER_BLOCK_ENTITY = modBlockEntities.register("dna_sequencer_block_entity", () -> BlockEntityType.Builder.of(DNASequencerBlockEntity::new, ModBlocks.DNA_SEQUENCER.get()).build(null));

    public static RegistryObject<BlockEntityType<DNASynthesizerBlockEntity>> DNA_SYNTHESIZER_BLOCK_ENTITY = modBlockEntities.register("dna_synthesizer_block_entity", () -> BlockEntityType.Builder.of(DNASynthesizerBlockEntity::new, ModBlocks.DNA_SYNTHESIZER.get()).build(null));

    public static class modScreenTypes{

        public static HashMap<ResourceLocation, ModMenuSupplier<?>> modMenuSupplier = new HashMap<>();
        public static DeferredRegister<MenuType<?>> modScreenTypes = DeferredRegister.create(ForgeRegistries.MENU_TYPES, modid);

        public static RegistryObject<MenuType<CleanerMenu>> CleanerMenuType = modScreenTypes.register("cleaner_screen_type", () -> new MenuType<>(CleanerMenu::new));

        public static RegistryObject<MenuType<DNACombinatorHybridizerMenu>> COMBINATOR_MENU_TYPE = modScreenTypes.register("combinator_menu_type", () -> new MenuType<>(DNACombinatorHybridizerMenu::new));

        public static RegistryObject<MenuType<FossilGrinderMenu>> FOSSIL_GRINDER_MENU_TYPE = modScreenTypes.register("fossil_grinder_menu_type", () -> new MenuType<>(FossilGrinderMenu::new));

        public static RegistryObject<MenuType<DNASequencerMenu>> DNA_SEQUENCER_MENU_TYPE = modScreenTypes.register("dna_sequencer_menu_type", () -> new MenuType<>(DNASequencerMenu::new));

        public static RegistryObject<MenuType<DNAExtractorMenu>> DNA_EXTRACTOR_MENU_TYPE = modScreenTypes.register("dna_extractor_menu_type", () -> new MenuType<>(DNAExtractorMenu::new));

        public static RegistryObject<MenuType<DNASynthesizerMenu>> DNA_SYNTHESIZER_MENU_TYPE = modScreenTypes.register("dna_synthesizer_menu_type", () -> new MenuType<>(DNASynthesizerMenu::new));

        public static RegistryObject<MenuType<IncubatorMenu>> INCUBATOR_MENU_TYPE = modScreenTypes.register("incubator_menu_type", () -> new MenuType<>(IncubatorMenu::new));



        public interface ModMenuSupplier< T extends AbstractContainerMenu>{

            T create(int id, Inventory inv, BlockEntity entity);
        }

    }
}
