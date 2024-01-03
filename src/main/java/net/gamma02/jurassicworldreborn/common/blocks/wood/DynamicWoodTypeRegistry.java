package net.gamma02.jurassicworldreborn.common.blocks.wood;

import com.google.gson.JsonObject;
import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.common.CommonRegistries;
import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.gamma02.jurassicworldreborn.common.items.TabHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import oshi.util.tuples.Pair;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DynamicWoodTypeRegistry {

    /**
    Indexes of wood products:<br>
    <ul>
    0 - log<br>
    1 - stripped log<br>
    2 - wood<br>
    3 - stripped wood<br>
    4 - plank<br>
    5 - slab<br>
    6 - fence<br>
    7 - fence gate<br>
    8 - door<br>
    9 - trapdoor<br>
    10 - pressure plate<br>
    11 - button<br>
    12 - standing sign<br>
    13 - wall sign<br>
    14 - leaves<br>
    15 - petrified logs<br>
    16 - stairs<br>
    17 - saplings<br>
     </ul>
     */

    public static HashMap<WoodType, List<RegistryObject<Block>>> DynamicWoodTypes = new HashMap<>();
    public static List<WoodType> woodTypes;

    private static HashMap<String, JsonObject> jsonFileMap = new HashMap<>();



    static DeferredRegister<Block> woodTypeRegister = DeferredRegister.create(ForgeRegistries.BLOCKS, Jurassicworldreborn.modid);



    public static void addWoodType(WoodType type, MaterialColor woodColor, MaterialColor barkColor){
//        ModItems.init();
        ArrayList<RegistryObject<Block>> typeList = new ArrayList<>();
        for(ProductType b : ProductType.values()){
            var auto = woodTypeRegister.register(type.name() + "_" + b.productName, () -> (b != ProductType.SIGN && b != ProductType.WALL_SIGN && b != ProductType.STAIRS ? b.getBlock.apply(new Pair<>(woodColor, barkColor)) : b == ProductType.SIGN ? /* START SIGN */ new StandingSignBlock(BlockBehaviour.Properties.of(Material.WOOD, woodColor).noCollission().strength(1.0F).sound(SoundType.WOOD), type) /* END SIGN */ : b == ProductType.WALL_SIGN ? /* WALL SIGN */ new WallSignBlock(BlockBehaviour.Properties.of(Material.WOOD, woodColor).noCollission().strength(1.0F).sound(SoundType.WOOD), type) /* END WALL SIGN */ : /* STAIRS */ new StairBlock(() -> DynamicWoodTypeRegistry.getProductFromWoodType(type, ProductType.PLANKS).defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS))));
//            ModItems.modBlockItems.register(type.name() + "_" + b.getProductName(), () -> b.signItemFunction != null ? b.signItemFunction.apply(getProductFromWoodType(type, ProductType.SIGN), getProductFromWoodType(type, ProductType.WALL_SIGN)) : b.itemFunction.apply(auto.get()));
            typeList.add(auto);
        }
        DynamicWoodTypes.put(type, typeList);
    }

    public static Block getProductFromWoodType(WoodType type, ProductType product){
        return DynamicWoodTypes.get(type).get(product.index).get();
    }

    public static ArrayList<Block> getProductOfType(ProductType product){
        ArrayList<Block> products = new ArrayList<>();
        for(WoodType type : DynamicWoodTypes.keySet()) {
            products.add(DynamicWoodTypes.get(type).get(product.index).get());
        }
        return products;
    }





    public static void register(IEventBus bus){
        System.out.println("REGISTERING WOOD BLOCKS");
        woodTypeRegister.register(bus);
        woodTypes = new ArrayList<>(DynamicWoodTypeRegistry.DynamicWoodTypes.keySet());
        jsonFileMap = getJsonBlockStateModelDefinitionsFix();

    }

    public static HashMap<String, JsonObject> getJsonBlockStateModelDefinitions(){
        return jsonFileMap;
    }

    public static ModSaplingBlock getSaplingForType(WoodType type){
        //i was an idiot and yeah I have to hardcode this, not redoing other registry stuff...
        if (type.equals(CommonRegistries.AraucariaType)) {
            return ModBlocks.AraucariaSapling.get();
        } else if (type.equals(CommonRegistries.CalamitesType)) {
            return ModBlocks.CalamitesSapling.get();
        } else if (type.equals(CommonRegistries.PsaroniusType)) {
            return ModBlocks.PsaroniusSapling.get();
        } else if (type.equals(CommonRegistries.GinkgoType)) {
            return ModBlocks.GinkgoSapling.get();
        } else if (type.equals(CommonRegistries.PhoenixType)) {
            return ModBlocks.PhoenixSapling.get();
        }
        return null;
    }
    public static List<ModSaplingBlock> getSaplings(){
        ArrayList<ModSaplingBlock> saplings = new ArrayList<>();
        for(WoodType type : DynamicWoodTypes.keySet()){
            saplings.add(getSaplingForType(type));
        }
        return saplings;
    }

    private static HashMap<String, JsonObject> getJsonBlockStateModelDefinitionsFix(){
        HashMap<JsonObject, String> objectNameMap = new HashMap<JsonObject, String>();
        for(WoodType type : woodTypes) {
            HashMap<ProductType, ArrayList<String>> productNames = new HashMap<>();
            for(ProductType product : ProductType.values()) {

                ArrayList<String> productNames1 = new ArrayList<>(List.of(""));

                for (Property<?> prop : product.properties) {


                    if (prop instanceof EnumProperty prop1) {
                        ArrayList<String> names = new ArrayList<>();
                        if (prop1.getPossibleValues().stream().anyMatch((e) -> Arrays.stream(Direction.Axis.values()).anyMatch((a) -> a == e))) {
                            for(Direction.Axis axis : ((Property<Direction.Axis>) prop1).getPossibleValues()){
                                productNames1.forEach((str) -> names.add(str + (str.equals("") ? "" : ",") + ((Property<Direction.Axis>) prop1).getName() + "=" + axis.getName()));
                            }
                        }else if(prop1.getPossibleValues().stream().anyMatch((e) -> Arrays.stream(SlabType.values()).anyMatch((a) -> a == e))){
                            for(SlabType slabType : ((Property<SlabType>) prop1).getPossibleValues()){
                                productNames1.forEach((str) -> names.add(str + (str.equals("") ? "" : ",") + ((Property<SlabType>) prop1).getName() + "=" + slabType.toString()));
                            }
                        }else if(prop1.getPossibleValues().stream().anyMatch((e) -> Arrays.stream(DoorHingeSide.values()).anyMatch((a) -> a == e))){
                            for(DoorHingeSide side : ((EnumProperty<DoorHingeSide>) prop1).getPossibleValues()){
                                productNames1.forEach((str) -> names.add(str + (str.equals("") ? "" : ",") + ((EnumProperty<DoorHingeSide>) prop1).getName() + "=" + side.toString()));
                            }
                        }else if(prop1.getPossibleValues().stream().anyMatch((e) -> Arrays.stream(DoorHingeSide.values()).anyMatch((a) -> a == e))){
                            for(DoorHingeSide side : ((EnumProperty<DoorHingeSide>) prop1).getPossibleValues()){
                                productNames1.forEach((str) -> names.add(str + (str.equals("") ? "" : ",") + ((EnumProperty<DoorHingeSide>) prop1).getName() + "=" + side.toString()));
                            }
                        }else if(prop1.getPossibleValues().stream().anyMatch((e) -> Arrays.stream(DoubleBlockHalf.values()).anyMatch((a) -> a == e))){
                            for(DoubleBlockHalf half : ((EnumProperty<DoubleBlockHalf>) prop1).getPossibleValues()){
                                productNames1.forEach((str) -> names.add(str + (str.equals("") ? "" : ",") + ((EnumProperty<DoubleBlockHalf>) prop1).getName() + "=" + half.toString()));
                            }
                        }else if(prop1.getPossibleValues().stream().anyMatch((e) -> Arrays.stream(AttachFace.values()).anyMatch((a) -> a == e))){
                            for(AttachFace face : ((EnumProperty<AttachFace>) prop1).getPossibleValues()){
                                productNames1.forEach((str) -> names.add(str + (str.equals("") ? "" : ",") + ((EnumProperty<AttachFace>) prop1).getName() + "=" + face.toString()));
                            }
                        }else if(prop1.getPossibleValues().stream().anyMatch((e) -> Arrays.stream(Direction.values()).anyMatch((a) -> a == e))){
                            for(Direction dir : ((DirectionProperty) prop1).getPossibleValues()){
                                productNames1.forEach((str) -> names.add(str + (str.equals("") ? "" : ",") + ( prop1).getName() + "=" + dir.getName()));
                            }
                        }else{
                            for(Enum e : ((Collection<Enum>)prop1.getPossibleValues())){
                                productNames1.forEach((str) -> names.add(str + (str.equals("") ? "" : ",") + prop1.getName() + "=" + e.toString()));
                            }
                        }


                        productNames1 = new ArrayList<>(names);
                    }else if(prop instanceof BooleanProperty prop1){
                        ArrayList<String> names = new ArrayList<>();
                        for(boolean b : prop1.getPossibleValues()) {
                            for (String str : productNames1) {
                                names.add(str + (str.equals("") ? "" : ",") + prop1.getName() + "=" + b);
                            }
                        }
                        productNames1 = new ArrayList<>(names);
                    }else if(prop instanceof IntegerProperty prop1){
                        ArrayList<String> names = new ArrayList<>();
                        for(int b : prop1.getPossibleValues()) {
                            for (String str : productNames1) {
                                names.add(str + (str.equals("") ? "" : ",") + prop1.getName() + "=" + String.valueOf(b));
                            }
                        }
                        productNames1 = new ArrayList<>(names);
                    }

                }
                productNames.put(product, productNames1);
            }


            for(ProductType p : productNames.keySet() ){
                JsonObject root = new JsonObject();
                JsonObject variants = new JsonObject();
                for(String s : productNames.get(p)){
                    JsonObject temp = new JsonObject();
                    temp.addProperty("model", "model_" + s);
                    temp.addProperty("y_maybe", "y_" + s);
                    variants.add(s, temp);
                }

                root.add("variants", variants);
                root.addProperty("name", type.name() + "_" + p.productName);
                objectNameMap.put(root, type.name() + "_" + p.productName);
            }



        }
        HashMap<String, JsonObject> realMap = new HashMap<>();
        for(JsonObject j : objectNameMap.keySet()){
            var string = objectNameMap.get(j);
            realMap.put(string, j);
        }
        return realMap;
    }


    public enum ProductType {
        LOG("log", 0, (color) ->{
            return log(color.getB(), color.getA());
        }, new ArrayList<Property<?>>(Collections.singleton(RotatedPillarBlock.AXIS)), (block) -> new BlockItem(block,  new Item.Properties().tab(TabHandler.BLOCKS))),
        STRIPPED_LOG("stripped_log", 1, (color) ->{
            return log(color.getA(), color.getA());
        }, new ArrayList<Property<?>>(Collections.singleton(RotatedPillarBlock.AXIS)), (block) -> new BlockItem(block,  new Item.Properties().tab(TabHandler.BLOCKS))),
        WOOD("wood", 2, (color) ->{
            return new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, color.getB()).strength(2.0F).sound(SoundType.WOOD));
        }, new ArrayList<Property<?>>(Collections.singleton(RotatedPillarBlock.AXIS)), (block) -> new BlockItem(block,  new Item.Properties().tab(TabHandler.BLOCKS))),
        STRIPPED_WOOD("stripped_wood", 3, (color) -> {
            return new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, color.getA()).strength(2.0F).sound(SoundType.WOOD));
        }, new ArrayList<Property<?>>(Collections.singleton(RotatedPillarBlock.AXIS)), (block) -> new BlockItem(block,  new Item.Properties().tab(TabHandler.BLOCKS))),
        PLANKS("planks", 4, (color) ->{
            return new Block(BlockBehaviour.Properties.of(Material.WOOD, color.getA()).strength(2.0F).sound(SoundType.WOOD));
        }, new ArrayList<>(Blocks.OAK_PLANKS.getStateDefinition().getProperties()), (block) -> new BlockItem(block,  new Item.Properties().tab(TabHandler.BLOCKS))),
        SLAB("slab", 5, (color) -> {
            return new SlabBlock(BlockBehaviour.Properties.of(Material.WOOD, color.getA()).strength(2.0F, 3.0F).sound(SoundType.WOOD));
        }, new ArrayList<>(Blocks.OAK_SLAB.getStateDefinition().getProperties()), (block) -> new BlockItem(block,  new Item.Properties().tab(TabHandler.BLOCKS))),
        FENCE("fence", 6, (color) -> {
            return new FenceBlock(BlockBehaviour.Properties.of(Material.WOOD, color.getA()).strength(2.0F, 3.0F).sound(SoundType.WOOD));
        }, new ArrayList<>(Blocks.OAK_FENCE.getStateDefinition().getProperties()), (block) -> new BlockItem(block,  new Item.Properties().tab(TabHandler.BLOCKS))),
        FENCEGATE("fence_gate", 7, (color) -> {
            return new FenceGateBlock(BlockBehaviour.Properties.of(Material.WOOD, color.getA()).strength(2.0F, 3.0F).sound(SoundType.WOOD));
        }, new ArrayList<>(Blocks.OAK_FENCE_GATE.getStateDefinition().getProperties()), (block) -> new BlockItem(block,  new Item.Properties().tab(TabHandler.BLOCKS))),
        DOOR("door", 8, (color) -> {
            return new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD, color.getA()).strength(3.0F).sound(SoundType.WOOD).noOcclusion());
        }, new ArrayList<>(Blocks.OAK_DOOR.getStateDefinition().getProperties()), (block) -> new DoubleHighBlockItem(block,  new Item.Properties().tab(TabHandler.BLOCKS))),
        TRAPDOOR("trap_door", 9, (color) -> {
            return new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD, color.getA()).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn((a, b, c, d) -> {
                return false;
            }));
        }, new ArrayList<>(Blocks.OAK_TRAPDOOR.getStateDefinition().getProperties()), (block) -> new BlockItem(block,  new Item.Properties().tab(TabHandler.BLOCKS))),
        PRESSURE_PLATE("pressure_plate", 10, (color) -> {
            return new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of(Material.WOOD, color.getA()).noCollission().strength(0.5F).sound(SoundType.WOOD));
        }, new ArrayList<>(Blocks.OAK_PRESSURE_PLATE.getStateDefinition().getProperties()), (block) -> new BlockItem(block,  new Item.Properties().tab(TabHandler.BLOCKS))),
        BUTTON("button", 11, (color) -> {
            return new WoodButtonBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.WOOD));
        }, new ArrayList<>(Blocks.OAK_BUTTON.getStateDefinition().getProperties()), (block) -> new BlockItem(block,  new Item.Properties().tab(TabHandler.BLOCKS))),
        SIGN("sign", 12, null, new ArrayList<>(Blocks.OAK_SIGN.getStateDefinition().getProperties()), (standing, wall) -> new SignItem(new Item.Properties().tab(TabHandler.BLOCKS), standing, wall)),
        WALL_SIGN("wall_sign", 13, null, new ArrayList<>(Blocks.OAK_WALL_SIGN.getStateDefinition().getProperties()), (standing, wall) -> null),
        LEAVES("leaves", 14, (color) -> {
            return new AncientLeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(ProductType::ocelotOrParrot).isSuffocating(ProductType::falsePredicate).isViewBlocking(ProductType::falsePredicate));
        }, new ArrayList<Property<?>>(Blocks.OAK_LEAVES.getStateDefinition().getProperties()), (block) -> new BlockItem(block, new Item.Properties().tab(TabHandler.BLOCKS))),
        PETRIFIED_LOG("petrified_log", 15, (color) -> {
            return log(color.getB(), color.getA());
        }, new ArrayList<Property<?>>(Collections.singleton(RotatedPillarBlock.AXIS)), (block) -> new BlockItem(block, new Item.Properties().tab(TabHandler.BLOCKS))),
        STAIRS("stairs", 16, null, new ArrayList<>(Blocks.OAK_STAIRS.getStateDefinition().getProperties()), (block) -> new BlockItem(block, new Item.Properties().tab(TabHandler.BLOCKS)));

        private static boolean falsePredicate(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
            return false;
        }

        private final String productName;

        public final Function<Block, Item> itemFunction;
        public final BiFunction<Block, Block, Item> signItemFunction;
        private final int index;
        private final Function<Pair<MaterialColor, MaterialColor>, Block> getBlock;

        public final ArrayList<Property<?>> properties;

        ProductType(String productName, int index, Function<Pair<MaterialColor, MaterialColor>, Block> getBlock, List<Property<?>> properties, Function<Block, Item> getItem){
            this.productName = productName;

            this.index = index;

            this.getBlock = getBlock;
            this.properties = new ArrayList<>(properties);
            this.itemFunction = getItem;
            this.signItemFunction = null;
        }
        ProductType(String productName, int index, Function<Pair<MaterialColor, MaterialColor>, Block> getBlock, List<Property<?>> properties, BiFunction<Block, Block, Item> getItem){
            this.productName = productName;

            this.index = index;

            this.getBlock = getBlock;
            this.properties = new ArrayList<>(properties);
            this.signItemFunction = getItem;
            this.itemFunction = null;

        }


        public String getProductName(){
            return productName;
        }

        public int getIndex(){
            return index;
        }

        public static ProductType[] vals(){
            return values();
        }

        public Function<Pair<MaterialColor, MaterialColor>, Block> getFunction(){
            return getBlock;
        }

        ProductType byIndex(int index){
            for(ProductType b : values()){
                if(b.getIndex() == index){
                    return b;
                }
            }
            return null;
        }

        ProductType byProductName(String name){
            for(ProductType b : values()){
                if(b.getProductName().equals(name)){
                    return b;
                }
            }
            return null;
        }


        private static Boolean ocelotOrParrot(BlockState p_50822_, BlockGetter p_50823_, BlockPos p_50824_, EntityType<?> p_50825_) {
            return p_50825_ == EntityType.OCELOT || p_50825_ == EntityType.PARROT;
        }


    }

    private static RotatedPillarBlock log(MaterialColor p_50789_, MaterialColor p_50790_) {
        return new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, (state) -> {
            return state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? p_50789_ : p_50790_;
        }).strength(2.0F).sound(SoundType.WOOD));
    }

    public static List<Block> getProductsFromProductTypes(ProductType... types){
        ArrayList<Block> blocks = new ArrayList<>();

        for(var type : types){
            blocks.addAll(getProductOfType(type));
        }

        return blocks;
    }


}
