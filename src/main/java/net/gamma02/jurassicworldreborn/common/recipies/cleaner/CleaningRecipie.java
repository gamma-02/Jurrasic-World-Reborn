package net.gamma02.jurassicworldreborn.common.recipies.cleaner;

import com.google.gson.JsonObject;
import net.gamma02.jurassicworldreborn.common.blocks.entities.cleaner.CleanerBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

import static net.gamma02.jurassicworldreborn.Jurassicworldreborn.resource;

public class CleaningRecipie implements Recipe<CleanerBlockEntity> {

    public static final RecipeType<CleaningRecipie> CLEANING_RECIPE_TYPE = new CleaningRecipieType();

    public static Serializer INSTANCE = new Serializer();

    ResourceLocation id;
    ItemStack input;

    ItemStack output;


    public CleaningRecipie(ResourceLocation id, ItemStack input, ItemStack output){
        this.id = id;
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean matches(CleanerBlockEntity pContainer, Level pLevel) {
        ItemStack recipeItem = pContainer.getItem(0);
        return ((recipeItem.getItem() == input.getItem() || input.equals(recipeItem)) && recipeItem.getCount() >= input.getCount()) && pContainer.getFluidAmount() > 0;
    }

    @Override
    @Nonnull
    public ItemStack assemble(CleanerBlockEntity pContainer) {
//        pContainer.getItem(0).setCount(pContainer.getItem(0).getCount() - this.input.getCount());
//
//        Collection<ArrayList<RegistryObject<BoneItem>>> tempList = DynamicBoneRegistry.BoneMap.values();
//
//        List<ArrayList<RegistryObject<BoneItem>>> listt = new ArrayList<>(tempList.stream().toList());
//
//        Collections.shuffle(listt);
//
//        Collections.shuffle(listt.get(0));
//
//        return listt.get(0).get(0).get().getDefaultInstance();
        return this.output;
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

    private static class Serializer implements RecipeSerializer<CleaningRecipie>{

        Serializer(){
        }


        @Override
        public CleaningRecipie fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack input = CraftingHelper.getItemStack(pSerializedRecipe.getAsJsonObject("input"), false);
            ItemStack output = CraftingHelper.getItemStack(pSerializedRecipe.getAsJsonObject("output"), false);

            return new CleaningRecipie(pRecipeId, input, output);
        }

        @Nullable
        @Override
        public CleaningRecipie fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            ItemStack input = pBuffer.readItem();
            ItemStack output = pBuffer.readItem();

            return new CleaningRecipie(pRecipeId, input, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, CleaningRecipie pRecipe) {

            pBuffer.writeItemStack(pRecipe.input, false);
            pBuffer.writeItemStack(pRecipe.output, false);
        }

    }

    public static class CleaningRecipieType implements RecipeType<CleaningRecipie>{
        @Override
        public String toString(){
            return resource("cleaning_recipe_type").toString();
        }
    }
}
