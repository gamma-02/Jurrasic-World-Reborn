package net.gamma02.jurassicworldreborn.common.blocks.machines;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.gamma02.jurassicworldreborn.common.blocks.fossil.FossilBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.machines.cleaner.CleanerBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.machines.cleaner.CleanerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.gamma02.jurassicworldreborn.Jurassicworldreborn.modid;

public class modBlockEntities<T extends BlockEntity> {

    public static DeferredRegister<BlockEntityType<?>> modBlockEntities = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, modid);

    public static RegistryObject<BlockEntityType<?>> CLEANING_STATION = modBlockEntities.register("cleaning_station", () -> BlockEntityType.Builder.of(CleanerBlockEntity::new, ModBlocks.CLEANER_BLOCK.get()).build(null));

    public static RegistryObject<BlockEntityType<?>> FOSSIL_BLOCK_ENTITY = modBlockEntities.register("fossil_entity", () -> BlockEntityType.Builder.of(FossilBlockEntity::new, ModBlocks.FAUNA_FOSSIL.get(), ModBlocks.DEEPSLATE_FAUNA_FOSSIL.get(), ModBlocks.FLORA_FOSSIL.get(), ModBlocks.DEEPSLATE_FLORA_FOSSIL.get()).build(null));

    public static class modScreenTypes{
        public static DeferredRegister<MenuType<?>> modScreenTypes = DeferredRegister.create(ForgeRegistries.CONTAINERS, modid);

        public static RegistryObject<MenuType<CleanerMenu>> CleanerScreenType = modScreenTypes.register("cleaner_screen_type", () -> new MenuType<>(CleanerMenu::new));

    }
}
