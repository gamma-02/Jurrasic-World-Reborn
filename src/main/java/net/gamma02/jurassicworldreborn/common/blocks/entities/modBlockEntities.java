package net.gamma02.jurassicworldreborn.common.blocks.entities;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.gamma02.jurassicworldreborn.common.blocks.entities.cleaner.CleanerBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.cleaner.CleanerMenu;
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

public class modBlockEntities<T extends BlockEntity> {

    public static DeferredRegister<BlockEntityType<?>> modBlockEntities = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, modid);

    public static RegistryObject<BlockEntityType<CleanerBlockEntity>> CLEANING_STATION = modBlockEntities.register("cleaning_station", () -> BlockEntityType.Builder.of(CleanerBlockEntity::new, ModBlocks.CLEANER_BLOCK.get()).build(null));
    public static RegistryObject<BlockEntityType<EncasedFossilBlockEntity>> FOSSIL = modBlockEntities.register("encased_fossil", () -> BlockEntityType.Builder.of(EncasedFossilBlockEntity::new, ModBlocks.FAUNA_FOSSIL.get(), ModBlocks.DEEPSLATE_FAUNA_FOSSIL.get(), ModBlocks.ENCASED_FAUNA_FOSSIL.get()).build(null));



    public static class modScreenTypes{

        public static HashMap<ResourceLocation, ModMenuSupplier<?>> modMenuSupplier = new HashMap<>();
        public static DeferredRegister<MenuType<?>> modScreenTypes = DeferredRegister.create(ForgeRegistries.CONTAINERS, modid);

        public static RegistryObject<MenuType<CleanerMenu>> CleanerScreenType = modScreenTypes.register("cleaner_screen_type", () -> new MenuType<>(CleanerMenu::new));

        public interface ModMenuSupplier< T extends AbstractContainerMenu>{

            T create(int id, Inventory inv, BlockEntity entity);
        }

    }
}
