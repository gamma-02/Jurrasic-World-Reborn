package net.gamma02.jurassicworldreborn.common.blocks.machines;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.gamma02.jurassicworldreborn.common.blocks.machines.cleaner.CleanerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class modBlockEntities<T extends BlockEntity> {

    public static DeferredRegister<BlockEntityType<?>> modBlockEntities = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Jurassicworldreborn.modid);

    public static RegistryObject<BlockEntityType<?>> CLEANING_STATION = modBlockEntities.register("cleaning_station", () -> BlockEntityType.Builder.of(CleanerBlockEntity::new, ModBlocks.CLEANER_BLOCK.get()).build(null));


}
