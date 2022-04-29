package net.gamma02.jurassicworldreborn.common.recipies.cleaner;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.gamma02.jurassicworldreborn.common.blocks.machines.cleaner.CleanerBlockEntity;
import net.gamma02.jurassicworldreborn.common.util.TimePeriod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static net.gamma02.jurassicworldreborn.Jurassicworldreborn.resource;

public class CleaningRecipie implements Recipe<CleanerBlockEntity> {

    public static final RecipeType<CleaningRecipie> CLEANING_RECIPE_TYPE = new CleaningRecipieType();

    public static Serializer INSTANCE = new Serializer();

    ResourceLocation id;
    ItemStack input;
    List<ItemStack> output;

    public CleaningRecipie(ResourceLocation id, ItemStack input, List<ItemStack> output){
        this.id = id;
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean matches(CleanerBlockEntity pContainer, Level pLevel) {
//        return (pContainer.getItem(0).getItem() == this.input.getItem() && input.getCount() <= pContainer.getItem(0).getCount() || this.input == pContainer.getItem(0)) && pContainer.getFluidAmount() >= 1000;
        if(pContainer.getItem(0).getItem() == this.input.getItem() && TimePeriod.fromNbt(pContainer.getItem(0).getTag()) != null && TimePeriod.fromNbt(this.input.getTag()) != null && TimePeriod.fromNbt(pContainer.getItem(0).getTag()) == TimePeriod.fromNbt(this.input.getTag()) &&
        pContainer.getItem(0).getCount() >= this.input.getCount()){
            if(pContainer.getFluidAmount() > 0){
                return true;
            }
        }
        return false;
    }

    @Override
    @Nonnull
    public ItemStack assemble(CleanerBlockEntity pContainer) {
        pContainer.getItem(0).setCount(pContainer.getItem(0).getCount() - this.input.getCount());
        Collections.shuffle(this.output);
        return output.get(0);
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        Collections.shuffle(this.output);
        return output.get(0);
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return CLEANING_RECIPE_TYPE;
    }

    private static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<CleaningRecipie>{

        Serializer(){
            this.setRegistryName(resource("cleaner_recipie_serializer"));
        }


        @Override
        public CleaningRecipie fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack input = CraftingHelper.getItemStack(pSerializedRecipe.getAsJsonObject("input"), true);
            List<ItemStack> output = new ArrayList<>();
            for(JsonElement e : pSerializedRecipe.getAsJsonArray("output")){
                output.add(CraftingHelper.getItemStack(e.getAsJsonObject(), true));
            }
            return new CleaningRecipie(pRecipeId, input, output);
        }

        @Nullable
        @Override
        public CleaningRecipie fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            ItemStack input = pBuffer.readItem();
            int outputLength = pBuffer.readInt();
            List<ItemStack> outputs = new ArrayList<>();
            for(int i = 0; i < outputLength; i++){
                outputs.add(pBuffer.readItem());
            }
            return new CleaningRecipie(pRecipeId, input, outputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, CleaningRecipie pRecipe) {
            for(ItemStack stack : pRecipe.output){
                pBuffer.writeItemStack(stack, false);
            }
            pBuffer.writeInt(pRecipe.output.size());
            pBuffer.writeItemStack(pRecipe.input, false);
        }
    }

    public static class CleaningRecipieType implements RecipeType<CleaningRecipie>{
        @Override
        public String toString(){
            return resource("cleaning_recipe_type").toString();
        }
    }
}
