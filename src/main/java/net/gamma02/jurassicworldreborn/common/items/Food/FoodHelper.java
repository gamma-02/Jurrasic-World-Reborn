package net.gamma02.jurassicworldreborn.common.items.Food;

import com.google.common.collect.Sets;
import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.common.CommonRegistries;
import net.gamma02.jurassicworldreborn.common.blocks.wood.DynamicWoodTypeRegistry;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Diet;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.FoodType;
import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.Level;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FoodHelper {
    private static final Map<FoodType, List<FoodKey>> FOOD_TYPES = new EnumMap<>(FoodType.class);
    private static final List<FoodKey> FOODS = new LinkedList<>();
    private static final Map<FoodKey, Integer> HEAL_AMOUNTS = new HashMap<>();
    private static final Map<FoodKey, FoodEffect[]> FOOD_EFFECTS = new HashMap<>();

    public static void init() {
        registerFood(LeavesBlock.class, FoodType.PLANT, 2000);
        registerFood(TallGrassBlock.class, FoodType.PLANT, 1000);
        registerFood(Blocks.WHEAT, FoodType.PLANT, 2000);
        registerFood(Blocks.MELON, FoodType.PLANT, 3000);
        registerFood(Blocks.SUGAR_CANE, FoodType.PLANT, 1000);
        registerFood(SaplingBlock.class, FoodType.PLANT, 1000);
        registerFood(Blocks.PUMPKIN, FoodType.PLANT, 3000);
        registerFood(Blocks.CARROTS, FoodType.PLANT, 2000);
        registerFood(Blocks.POTATOES, FoodType.PLANT, 2000);
        registerFood(Blocks.HAY_BLOCK, FoodType.PLANT, 5000);
        registerFood(Blocks.LILY_PAD, FoodType.PLANT, 500);
        registerFood(FlowerBlock.class, FoodType.PLANT, 500);
        registerFood(SeagrassBlock.class, FoodType.PLANT, 500);
        registerFood(TallFlowerBlock.class, FoodType.PLANT, 2000);
        registerFood(TallSeagrassBlock.class, FoodType.PLANT, 2000);
        registerFood(Blocks.BROWN_MUSHROOM, FoodType.PLANT, 250);
        registerFood(Blocks.RED_MUSHROOM, FoodType.PLANT, 250);

//        registerFood(ItemHandler.COCKROACHES, FoodType.INSECT, 250); TODO: Rest of the food
//        registerFood(ItemHandler.CRICKETS, FoodType.INSECT, 250);
//        registerFood(ItemHandler.MEALWORM_BEETLES, FoodType.INSECT, 250);
//
//        registerFood(ItemHandler.KRILL, FoodType.FILTER, 250);
//        registerFood(ItemHandler.PLANKTON, FoodType.FILTER, 250);
//
//        registerFood(BlockHandler.PALEO_BALE_CYCADEOIDEA, FoodType.PLANT, 5000);
//        registerFood(BlockHandler.PALEO_BALE_CYCAD, FoodType.PLANT, 5000);
//        registerFood(BlockHandler.PALEO_BALE_FERN, FoodType.PLANT, 5000);
//        registerFood(BlockHandler.PALEO_BALE_LEAVES, FoodType.PLANT, 5000);
//        registerFood(BlockHandler.PALEO_BALE_OTHER, FoodType.PLANT, 5000);
//
//        for (Plant plant : PlantHandler.getPlants()) {
//            registerFood(plant.getBlock(), FoodType.PLANT, plant.getHealAmount(), plant.getEffects());
//        }

        for (WoodType type : DynamicWoodTypeRegistry.woodTypes) {
            registerFood(DynamicWoodTypeRegistry.getProductFromWoodType(type, DynamicWoodTypeRegistry.ProductType.LEAVES), FoodType.PLANT, 2000);
            registerFood(DynamicWoodTypeRegistry.getSaplingForType(type), FoodType.PLANT, 1000);
        }

        registerFood(Items.WHEAT, FoodType.PLANT, 1000);
        registerFood(Items.WHEAT_SEEDS, FoodType.PLANT, 100);
        registerFood(Items.MELON_SEEDS, FoodType.PLANT, 100);
        registerFood(Items.PUMPKIN_SEEDS, FoodType.PLANT, 100);
        registerFood(Items.BEETROOT_SEEDS, FoodType.PLANT, 100);



        registerFoodAuto(Items.SALMON, FoodType.FISH);
        registerFoodAuto(Items.COOKED_SALMON, FoodType.FISH);
        registerFoodAuto(Items.COD, FoodType.FISH);
        registerFoodAuto(Items.COOKED_COD, FoodType.FISH);

        registerFoodAuto( Items.TROPICAL_FISH, FoodType.FISH);

        for(var item : ModItems.MEATS.values()){
            registerFoodAuto(item.get(), FoodType.MEAT);
        }
        for(var item : ModItems.STEAKS.values()){
            registerFoodAuto(item.get(), FoodType.MEAT);
        }


        registerFoodAuto(ModItems.GOAT_RAW.get(), FoodType.MEAT);
        registerFoodAuto(ModItems.GOAT_COOKED.get(), FoodType.MEAT);
        registerFoodAuto(ModItems.SHARK_MEAT_RAW.get(), FoodType.FISH);
        registerFoodAuto(ModItems.SHARK_MEAT_COOKED.get(), FoodType.FISH);
        registerFoodAuto(ModItems.CRAB_MEAT_COOKED.get(), FoodType.FISH);
        registerFoodAuto(ModItems.CRAB_MEAT_RAW.get(), FoodType.FISH);

    //TODO: listen to item registration to do this on the fly instead of by looping through the entire registry

//        for (Item item : Item.REGISTRY) {

//            if (item instanceof ItemFood) {
//                ItemFood food = (ItemFood) item;
//                registerFoodAuto(food, food.isWolfsFavoriteMeat() ? FoodType.MEAT : FoodType.PLANT);
//            }
//        }
    }

    private static void registerFood(Class<? extends Block> blockClass, FoodType plant, int i) {
        registerFood(new FoodKey(blockClass), plant, i);

    }

    public static void registerFoodAuto(Item food, FoodType foodType, FoodEffect... effects) {
        registerFood(new FoodKey(food), foodType, food.getFoodProperties(new ItemStack(food), null).getNutrition() * 650, effects);
    }

    public static void registerFood(Item food, FoodType foodType, int healAmount, FoodEffect... effects) {
        registerFood(new FoodKey(food), foodType, healAmount, effects);
    }

    private static void registerFood(FoodKey food, FoodType foodType, int healAmount, FoodEffect... effects) {
        if (!FOODS.contains(food)) {
            if( food == null || food.hashCode() == 0 ) {
                Jurassicworldreborn.getLogger().log(Level.ERROR, "Something tried to register an invalid food!");
                return;
            }

            List<FoodKey> foodsForType = FOOD_TYPES.get(foodType);

            if (foodsForType == null) {
                foodsForType = new ArrayList<>();
            }

            foodsForType.add(food);

            FOODS.add(food);
            FOOD_TYPES.put(foodType, foodsForType);
            HEAL_AMOUNTS.put(food, healAmount);
            FOOD_EFFECTS.put(food, effects);
        }
    }

    public static void registerFood(Block food, FoodType foodType, int foodAmount, FoodEffect... effects) {
        registerFood(new FoodKey(food), foodType, foodAmount, effects);
    }

    public static List<FoodKey> getFoodType(FoodType type) {
        return FOOD_TYPES.get(type);
    }

    public static List<Item> getFoodItems(FoodType type) {
        return getValidItemList(FOOD_TYPES.get(type));
    }

    private static List<Item> getValidItemList(List<FoodKey> keys) {
        List<Item> items = keys.stream().filter((foodKey -> !foodKey.isGeneral)).map(key -> {
            if( key.item != null ) {
                return key.item;
            } else if( key.block != null ) {
                Item itm = Item.byBlock(key.block);
                if( itm != Items.AIR ) {
                    return itm;
                }
            }

            return null;
        } ).filter(Objects::nonNull).collect(Collectors.toList());
        //get the general item, used for class-refrenced keys.
       List<List<Item>> generalItems = keys.stream().filter((foodKey -> foodKey.isGeneral)).map(key -> {
            if(key.BlockClass != null){
                return Item.BY_BLOCK.keySet().stream().filter(block -> block.getClass().isAssignableFrom(key.BlockClass))
                        .map(blk -> {
                            Item itm = Item.BY_BLOCK.get(blk);
                            if(itm != null && itm != Items.AIR)
                                return itm;
                            return null;
                        }).collect(Collectors.toList());
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
       List<Item> unwrapped = new ArrayList<>();
       for(List<Item> item : generalItems){
           for(Item i : item){
               unwrapped.add(i);
           }
       }
       if(items.addAll(unwrapped)){
           return items;
       }
       Jurassicworldreborn.getLogger().log(Level.DEBUG, "failed to generalize from block's classes");
       return null;
    }

    private static FoodType getFoodType(FoodKey key) {
        for (FoodType foodType : FoodType.values()) {
            if (getFoodType(foodType).contains(key)) {
                return foodType;
            }
        }

        return null;
    }

    public static FoodType getFoodType(Item item) {
        return getFoodType(new FoodKey(item));
    }

    public static FoodType getFoodType(Block block) {
        return getFoodType(new FoodKey(block));
    }

    public static boolean isFoodType(Item item, FoodType foodType) {
        return getFoodType(foodType).contains(new FoodKey(item));
    }

    public static boolean isEdible(DinosaurEntity entity, Diet diet, Item item) {
        return item != null && getEdibleFoods(entity, diet).contains(new FoodKey(item));
    }

    public static boolean isEdible(DinosaurEntity entity, Diet diet, Block block) {
        return block != null && getEdibleFoods(entity, diet).contains(new FoodKey(block));
    }

    public static HashSet<Item> getEdibleFoodItems(DinosaurEntity entity, Diet diet) {
        return Sets.newHashSet(getValidItemList(getEdibleFoods(entity, diet)));
    }

    public static List<FoodKey> getEdibleFoods(DinosaurEntity entity, Diet diet) {
        List<FoodKey> possibleItems = new ArrayList<>();
        for (Diet.DietModule module : diet.getModules()) {
            if (module.applies(entity)) {
                possibleItems.addAll(getFoodType(module.getFoodType()));
            }
        }
        return possibleItems;
    }

    public static int getHealAmount(Item item) {
        return HEAL_AMOUNTS.getOrDefault(new FoodKey(item), 0);
    }

    public static void applyEatEffects(DinosaurEntity entity, Item item) {
        FoodEffect[] effects = FOOD_EFFECTS.get(new FoodKey(item));

        if (effects != null) {
            for (FoodEffect effect : effects) {
                if (entity.getRandom().nextInt(100) <= effect.chance) {
                    entity.addEffect(new MobEffectInstance(effect.effect));
                }
            }
        }
    }

    public static boolean isFood(Item item) {
        return FOODS.contains(new FoodKey(item));
    }

    public static boolean isFood(ItemStack item) {
        return FOODS.contains(new FoodKey(item.getItem()));
    }

    public static boolean isFood(ItemEntity item) {//overriding go brrrrr
        return FOODS.contains(new FoodKey(item.getItem().getItem()));
    }

    public static class FoodEffect {
        public MobEffect effect;
        public int chance;

        public FoodEffect(MobEffect effect, int chance) {
            this.effect = effect;
            this.chance = chance;
        }
    }

    static class FoodKey {

        public boolean isGeneral = false; //this specifies if this key covers more than one Item/Block
        final Item item;
        final Block block;

        final Class<? extends Block> BlockClass;

        FoodKey(Item item) {
            this.item = item;
            this.block = null;
            this.BlockClass = null;
        }

        FoodKey(Block block) {
            Item blkItm = Item.byBlock(block);
            this.item = blkItm != Items.AIR ? blkItm : null;
            this.block = blkItm == Items.AIR ? block : null;
            this.BlockClass = null;
        }

        FoodKey(Class<? extends Block> blockClass){
            this.BlockClass = blockClass;
            this.isGeneral = true;
            this.block = null;
            this.item = null;
        }

        @Override
        public boolean equals(Object obj) {
            return this.hashCode() == obj.hashCode();
        }

        @Override
        public int hashCode() {
            if(!this.isGeneral) {
                return this.item != null && this.item != Items.AIR ? this.item.hashCode() : this.block != null && this.block != Blocks.AIR ? this.block.hashCode() : 0;
            }else{
                return BlockClass.hashCode()+1;
            }
        }
    }
}
