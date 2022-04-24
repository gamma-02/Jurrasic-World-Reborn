package net.gamma02.jurrasicworldreborn.common.blocks.wood;

import net.gamma02.jurrasicworldreborn.Jurrasicworldreborn;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class DynamicWoodTypeRegistry {

    /*
    Indexes of wood products:
    0 - log
    1 - stripped log
    2 - wood
    3 - stripped wood
    4 - plank
    5 - slab
    6 - fence
    7 - fence gate
    8 - door
    9 - trapdoor
    10 - pressure plate
    11 - button
    12 - standing sign
    13 - wall sign
     */

    public static HashMap<WoodType, List<RegistryObject<Block>>> DynamicWoodTypes = new HashMap<>();

    static DeferredRegister<Block> woodTypeRegister = DeferredRegister.create(ForgeRegistries.BLOCKS, Jurrasicworldreborn.modid);



    public static void addWoodType(WoodType type, MaterialColor woodColor, MaterialColor barkColor){
        ArrayList<RegistryObject<Block>> typeList = new ArrayList<>();
        for(ProductType b : ProductType.values()){
            typeList.add(woodTypeRegister.register(type.name() + "_" + b.productName, () -> (b != ProductType.SIGN && b != ProductType.WALL_SIGN ? b.getBlock.apply(new Pair<>(woodColor, barkColor)) : b == ProductType.SIGN ? /* START SIGN */ new StandingSignBlock(BlockBehaviour.Properties.of(Material.WOOD, woodColor).noCollission().strength(1.0F).sound(SoundType.WOOD), type) /* END SIGN */ : /* WALL SIGN */ new WallSignBlock(BlockBehaviour.Properties.of(Material.WOOD, woodColor).noCollission().strength(1.0F).sound(SoundType.WOOD), type) /* END WALL SIGN */)));
            System.out.println("WOOD PRODUCT " + b.productName + " REGISTERED FOR " + type.name());
        }
        DynamicWoodTypes.put(type, typeList);
    }

    public static Block getProductFromWoodType(WoodType type, ProductType product){
        return DynamicWoodTypes.get(type).get(product.index).get();
    }


    public static void register(IEventBus bus){
        System.out.println("REGISTERING WOOD BLOCKS");
        woodTypeRegister.register(bus);
    }

    public enum ProductType {
        LOG("log", 0, (color) ->{
            return log(color.getB(), color.getA());
        }),
        STRIPPED_LOG("stripped_log", 1, (color) ->{
            return log(color.getA(), color.getA());
        }),
        WOOD("wood", 2, (color) ->{
            return new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, color.getB()).strength(2.0F).sound(SoundType.WOOD));
        }),
        STRIPPED_WOOD("stripped_wood", 3, (color) -> {
            return new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, color.getA()).strength(2.0F).sound(SoundType.WOOD));
        }),
        PLANKS("planks", 4, (color) ->{
            return new Block(BlockBehaviour.Properties.of(Material.WOOD, color.getA()).strength(2.0F).sound(SoundType.WOOD));
        }),
        SLAB("slab", 5, (color) -> {
            return new SlabBlock(BlockBehaviour.Properties.of(Material.WOOD, color.getA()).strength(2.0F, 3.0F).sound(SoundType.WOOD));
        }),
        FENCE("fence", 6, (color) -> {
            return new FenceBlock(BlockBehaviour.Properties.of(Material.WOOD, color.getA()).strength(2.0F, 3.0F).sound(SoundType.WOOD));
        }),
        FENCEGATE("fence_gate", 7, (color) -> {
            return new FenceGateBlock(BlockBehaviour.Properties.of(Material.WOOD, color.getA()).strength(2.0F, 3.0F).sound(SoundType.WOOD));
        }),
        DOOR("door", 8, (color) -> {
            return new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD, color.getA()).strength(3.0F).sound(SoundType.WOOD).noOcclusion());
        }),
        TRAPDOOR("trap_door", 9, (color) -> {
            return new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD, color.getA()).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn((a, b, c, d) -> {
                return false;
            }));
        }),
        PRESSURE_PLATE("pressure_plate", 10, (color) -> {
            return  new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of(Material.WOOD, color.getA()).noCollission().strength(0.5F).sound(SoundType.WOOD));
        }),
        BUTTON("button", 11, (color) -> {
            return new WoodButtonBlock(BlockBehaviour.Properties.of(Material.DECORATION).noCollission().strength(0.5F).sound(SoundType.WOOD));
        }),
        SIGN("sign", 12, null),
        WALL_SIGN("wall_sign", 13, null),
        LEAVES("leaves", 14, (color) -> {
            return new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(ProductType::ocelotOrParrot).isSuffocating(ProductType::no).isViewBlocking(ProductType::no));
        });

        private static boolean no(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
            return false;
        }

        private final String productName;
        private final int index;
        private final Function<Pair<MaterialColor, MaterialColor>, Block> getBlock;
        ProductType(String productName, int index, Function<Pair<MaterialColor, MaterialColor>, Block> getBlock){
            this.productName = productName;

            this.index = index;

            this.getBlock = getBlock;

        }


        public String getProductName(){
            return productName;
        }

        public int getIndex(){
            return index;
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


}
