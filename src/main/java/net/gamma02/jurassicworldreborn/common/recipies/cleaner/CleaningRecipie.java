package net.gamma02.jurassicworldreborn.common.recipies.cleaner;

import com.google.gson.JsonObject;
import net.gamma02.jurassicworldreborn.common.blocks.entities.cleaner.CleanerBlockEntity;
import net.gamma02.jurassicworldreborn.common.items.Bones.BoneItem;
import net.gamma02.jurassicworldreborn.common.items.Bones.DynamicBoneRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.*;

import static net.gamma02.jurassicworldreborn.Jurassicworldreborn.resource;

public class CleaningRecipie implements Recipe<CleanerBlockEntity> {

    public static final RecipeType<CleaningRecipie> CLEANING_RECIPE_TYPE = new CleaningRecipieType();

    public static Serializer INSTANCE = new Serializer();

    ResourceLocation id;
    ItemStack input;


    public CleaningRecipie(ResourceLocation id, ItemStack input){
        this.id = id;
        this.input = input;
    }

    @Override
    public boolean matches(CleanerBlockEntity pContainer, Level pLevel) {
        return (pContainer.getItem(0).getItem() == this.input.getItem() && input.getCount() <= pContainer.getItem(0).getCount() || this.input == pContainer.getItem(0)) && pContainer.getFluidAmount() >= 1000;
    }

    @Override
    @Nonnull
    public ItemStack assemble(CleanerBlockEntity pContainer) {
        pContainer.getItem(0).setCount(pContainer.getItem(0).getCount() - this.input.getCount());

        Collection<ArrayList<RegistryObject<BoneItem>>> tempList = DynamicBoneRegistry.BoneMap.values();

        List<ArrayList<RegistryObject<BoneItem>>> listt = new ArrayList<>(tempList.stream().toList());

        Collections.shuffle(listt);

        Collections.shuffle(listt.get(0));

        return listt.get(0).get(0).get().getDefaultInstance();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
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
            this.setRegistryName(resource("cleaning_recipe_serializer"));
        }


        @Override
        public CleaningRecipie fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack input = CraftingHelper.getItemStack(pSerializedRecipe.getAsJsonObject("input"), false);

            return new CleaningRecipie(pRecipeId, input);
        }

        @Nullable
        @Override
        public CleaningRecipie fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            ItemStack input = pBuffer.readItem();

            return new CleaningRecipie(pRecipeId, input);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, CleaningRecipie pRecipe) {

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
