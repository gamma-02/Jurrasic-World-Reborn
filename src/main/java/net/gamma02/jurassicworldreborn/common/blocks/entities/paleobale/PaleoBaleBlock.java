package net.gamma02.jurassicworldreborn.common.blocks.entities.paleobale;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.gamma02.jurassicworldreborn.common.blocks.wood.DynamicWoodTypeRegistry;
import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;
import java.util.stream.Collectors;

public class PaleoBaleBlock extends RotatedPillarBlock {
    private final Variant variant;
    public PaleoBaleBlock(Variant v, Properties p_55926_) {
        super(p_55926_);
        variant = v;
    }

    public Variant getVariant() {
        return this.variant;
    }

    @Override
    public void fallOn(Level world, BlockState blockState, BlockPos pos, Entity pEntity, float fallDistance) {
        pEntity.causeFallDamage(fallDistance, 0.2F, DamageSource.FALL);
    }

    public enum Variant implements StringRepresentable {
        CYCADEOIDEA(ModBlocks.CYCADEOIDEA.get()),
        CYCAD(ModBlocks.SMALL_CYCAD.get()),
        FERN(ModBlocks.SMALL_CHAIN_FERN.get(), ModBlocks.SMALL_ROYAL_FERN.get(), ModBlocks.RAPHAELIA.get(), ModBlocks.BRISTLE_FERN.get(), ModBlocks.CINNAMON_FERN.get(), ModBlocks.TEMPSKYA.get()),
        LEAVES(DynamicWoodTypeRegistry.getProductOfType(DynamicWoodTypeRegistry.ProductType.LEAVES)),
        OTHER(ModBlocks.AJUGINUCULA_SMITHII.get(), ModBlocks.RHACOPHYTON.get(), ModBlocks.GRAMINIDITES_BAMBUSOIDES.get(), ModBlocks.HELICONIA.get(), ModBlocks.RHAMNUS_SALICIFOLIUS_PLANT.get(), ModBlocks.LARGESTIPULE_LEATHER_ROOT.get(), ModBlocks.RHACOPHYTON.get(), ModBlocks.CRY_PANSY.get(), ModBlocks.DICKSONIA.get(), ModBlocks.DICROIDIUM_ZUBERI.get(), ModBlocks.WILD_ONION.get(), ModBlocks.ZAMITES.get(), ModBlocks.UMALTOLEPIS.get(), ModBlocks.LIRIODENDRITES.get(), ModBlocks.WOOLLY_STALKED_BEGONIA.get());

        private List<Item> ingredients;

        Variant(Block... ingredients) {
            //this is the legacy code... this works with converting the blocks directly to items.
            // That doesn't work in 1.13+ where BlockItems are seperate things from blocks, so this will change.
            // This, however, could cause a bug, but that's not my problem. Good luck, future coders! :) - gamma02
            /*this.ingredients = new Item[ingredients.length];

            for (int i = 0; i < ingredients.length; i++) {
                this.ingredients[i] = Item.getItemFromBlock(ingredients[i]);
            }*/
            ArrayList<Item> list = new ArrayList<>();
            for(Block b : ingredients) {
                if(b.asItem() != null)
                    list.add(b.asItem());
            }

        }

        Variant(List<Block> ingredients) {
            ArrayList<Item> list = new ArrayList<>();
            for(Block b : ingredients) {
                if(b.asItem() != null)
                    list.add(b.asItem());
            }
        }

        Variant(Item... ingredients) {
            this.ingredients = Arrays.stream(ingredients).collect(Collectors.toList());
        }

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ENGLISH);
        }

        @Override
        public String toString() {
            return this.name().toLowerCase(Locale.ENGLISH);
        }

        public Item[] getIngredients() {
            return this.ingredients.toArray(new Item[0]);
        }

        public List<Item> getIngredientsAsList(){
            return this.ingredients;
        }
    }
}
