package net.gamma02.jurassicworldreborn.common.blocks.entities;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.gamma02.jurassicworldreborn.common.blocks.entities.cleaner.CleanerBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.cleaner.CleanerMenu;
import net.gamma02.jurassicworldreborn.common.blocks.entities.fence.ElectricFenceBaseBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.fence.ElectricFencePoleBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.fence.ElectricFenceWireBlockEntity;
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

import javax.swing.*;
import java.util.HashMap;

import static net.gamma02.jurassicworldreborn.Jurassicworldreborn.modid;

public class modBlockEntities<T extends BlockEntity> {

    public static DeferredRegister<BlockEntityType<?>> modBlockEntities = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, modid);

    public static RegistryObject<BlockEntityType<CleanerBlockEntity>> CLEANING_STATION = modBlockEntities.register("cleaning_station", () -> BlockEntityType.Builder.of(CleanerBlockEntity::new, ModBlocks.CLEANER_BLOCK.get()).build(null));
    public static RegistryObject<BlockEntityType<EncasedFossilBlockEntity>> FOSSIL = modBlockEntities.register("encased_fossil", () -> BlockEntityType.Builder.of(EncasedFossilBlockEntity::new, ModBlocks.FAUNA_FOSSIL.get(), ModBlocks.DEEPSLATE_FAUNA_FOSSIL.get(), ModBlocks.ENCASED_FAUNA_FOSSIL.get()).build(null));

    public static RegistryObject<BlockEntityType<TourRailBlockEntity>> TOUR_RAIL_BLOCK_ENTITY = modBlockEntities.register("tour_rail_block_entity", () -> BlockEntityType.Builder.of(TourRailBlockEntity::new, ModBlocks.TOUR_RAIL.get()).build(null));

    public static RegistryObject<BlockEntityType<ElectricFenceBaseBlockEntity>> BASE_FENCE_BLOCK_ENTITY = modBlockEntities.register("base_fence_block_entity", () -> BlockEntityType.Builder.of(ElectricFenceBaseBlockEntity::new, ModBlocks.LOW_SECURITY_FENCE_BASE.get(), ModBlocks.HIGH_SECURITY_FENCE_BASE.get(), ModBlocks.MED_SECURITY_FENCE_BASE.get()).build(null));

    public static RegistryObject<BlockEntityType<ElectricFenceWireBlockEntity>> WIRE_FENCE_BLOCK_ENTITY = modBlockEntities.register("wire_fence_block_entity", () -> BlockEntityType.Builder.of(ElectricFenceWireBlockEntity::new, ModBlocks.LOW_SECURITY_FENCE_WIRE.get(), ModBlocks.HIGH_SECURITY_FENCE_WIRE.get(), ModBlocks.MED_SECURITY_FENCE_WIRE.get()).build(null));

    public static RegistryObject<BlockEntityType<ElectricFencePoleBlockEntity>> POLE_FENCE_BLOCK_ENTITY = modBlockEntities.register("pole_fence_block_entity", () -> BlockEntityType.Builder.of(ElectricFencePoleBlockEntity::new, ModBlocks.LOW_SECURITY_FENCE_POLE.get(), ModBlocks.MED_SECURITY_FENCE_POLE.get(), ModBlocks.HIGH_SECURITY_FENCE_POLE.get()).build(null));

    public static RegistryObject<BlockEntityType<ActionFigureBlockEntity>> DISPLAY_BLOCK_ENTITY = modBlockEntities.register("display_block_entity", () -> BlockEntityType.Builder.of(ActionFigureBlockEntity::new, ModBlocks.DISPLAY_BLOCK.get()).build(null));

    public static class modScreenTypes{

        public static HashMap<ResourceLocation, ModMenuSupplier<?>> modMenuSupplier = new HashMap<>();
        public static DeferredRegister<MenuType<?>> modScreenTypes = DeferredRegister.create(ForgeRegistries.CONTAINERS, modid);

        public static RegistryObject<MenuType<CleanerMenu>> CleanerScreenType = modScreenTypes.register("cleaner_screen_type", () -> new MenuType<>(CleanerMenu::new));

        public interface ModMenuSupplier< T extends AbstractContainerMenu>{

            T create(int id, Inventory inv, BlockEntity entity);
        }

    }
}
