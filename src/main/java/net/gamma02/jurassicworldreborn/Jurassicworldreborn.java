package net.gamma02.jurassicworldreborn;

import net.gamma02.jurassicworldreborn.client.render.RenderingHandler;
import net.gamma02.jurassicworldreborn.client.screens.CleanerScreen;
import net.gamma02.jurassicworldreborn.client.screens.DNACombinatorHybridizerScreen;
import net.gamma02.jurassicworldreborn.client.screens.ModScreens;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.CommonRegistries;
import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNACombinatorHybridizer.DNACombinatorHybridizerBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNACombinatorHybridizer.DNACombinatorHybridizerMenu;
import net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities;
import net.gamma02.jurassicworldreborn.common.blocks.entities.cleaner.CleanerBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.cleaner.CleanerMenu;
import net.gamma02.jurassicworldreborn.common.blocks.wood.DynamicWoodTypeRegistry;
import net.gamma02.jurassicworldreborn.common.entities.EventListener;
import net.gamma02.jurassicworldreborn.common.entities.ModEntities;
import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.gamma02.jurassicworldreborn.common.network.Network;
import net.gamma02.jurassicworldreborn.common.recipies.cleaner.CleaningRecipie;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

import static net.gamma02.jurassicworldreborn.common.CommonRegistries.modFeatures;
import static net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities.modScreenTypes.modScreenTypes;
import static net.gamma02.jurassicworldreborn.common.recipies.cleaner.CleaningRecipie.CLEANING_RECIPE_TYPE;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("jurassicworldreborn")
@Mod.EventBusSubscriber(modid = Jurassicworldreborn.MODID)
public class Jurassicworldreborn {


    public static String modid = "jurassicworldreborn";

    public static final String MODID = "jurassicworldreborn";

    public static ResourceLocation resource(String resource){
        return new ResourceLocation(modid, resource);
    }

    public static ProfilerFiller profilerFiller = null;

    public static final HashMap<Block, RenderType> renderlayers = new HashMap<>();

    public static void setRenderType(Block block, RenderType rendertype){
        renderlayers.put(block, rendertype);
    }

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public static Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> CONFIGURED_ARAUCARIA;
    public static Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> CONFIGURED_GINKGO;

    public static Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> CONFIGURED_CALAMITES;

    public static Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> CONFIGURED_PHOENIX;

    public static Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> CONFIGURED_PSARONIUS;






    public static IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

    public Jurassicworldreborn() {


        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(EventListener::finalizeSetup);

        MinecraftForge.EVENT_BUS.addListener(this::postClientTickEvent);

        MinecraftForge.EVENT_BUS.addListener(this::onLevelLoadEvent);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);


        // Register wood types and get DynamicWoodTypeRegistry setup and running
        try {
            ClassLoader.getSystemClassLoader().loadClass("net.gamma02.jurassicworldreborn.common.CommonRegistries");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


        //that would be the reason for having no crab entities-
        ModEntities.MOD_ENTITY_TYPES.register(modEventBus);

//        FMLJavaModLoadingContext.get()

        DynamicWoodTypeRegistry.register(modEventBus);

        modFeatures.register(modEventBus);

        ModBlocks.register(modEventBus);

        ModItems.register(modEventBus);

        ModBlockEntities.modBlockEntities.register(modEventBus);

        modScreenTypes.register(modEventBus);

        Network.init();


//        HashMap<String, JsonObject> jsonMap = DynamicWoodTypeRegistry.getJsonBlockStateModelDefinitions();

//        Gson json = new GsonBuilder().setPrettyPrinting().create();
//        for(var s : jsonMap.keySet()){
//            try(BufferedWriter writer = new BufferedWriter(new FileWriter( "C:\\Users\\gamma\\OneDrive\\Documents\\C++ automation io\\WoodTypeOutput\\" + s + ".json"))){
//                String prettyOutput = json.toJson(jsonMap.get(s));
//                writer.write(prettyOutput);
//                writer.close();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }











//        CONFIGURED_ARAUCARIA = FeatureUtils.register("jurassicworldreborn:configured_araucria_feature", AraucariaTreeFeature, NoneFeatureConfiguration.INSTANCE);
//        CONFIGURED_GINKGO = FeatureUtils.register("jurassicworldreborn:configured_ginkgo_feature", GinkgoTreeFeature, NoneFeatureConfiguration.INSTANCE);
//        CONFIGURED_CALAMITES = FeatureUtils.register("jurassicworldreborn:configured_calamites_feature", CalamitesTreeFeature, NoneFeatureConfiguration.INSTANCE);
//        CONFIGURED_PHOENIX = FeatureUtils.register("jurassicworldreborn:configured_phoenix_feature", PhoenixTreeFeature, NoneFeatureConfiguration.INSTANCE);
//        CONFIGURED_PSARONIUS = FeatureUtils.register("jurassicworldreborn:configured_psaronius_feature", PsaroniusTreeFeature, NoneFeatureConfiguration.INSTANCE);

        FMLJavaModLoadingContext.get().getModEventBus().register(this.getClass());

//????????? maybe this will work???????????

    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT);

    }


    public void onLevelLoadEvent(LevelEvent.Load evt){
//        if(evt.getLevel().dimensionType() == evt.getLevel().registryAccess().registry(Registry.DIMENSION_TYPE_REGISTRY).get().get(BuiltinDimensionTypes.OVERWORLD)){
//            profilerFiller = ((Level) evt.getLevel()).getProfiler();
//        }
    }





    public void clientSetup(final FMLClientSetupEvent evt){

//        profilerFiller = Minecraft.getInstance().getProfiler();

        SoundHandler.init();
        //wood type rendering
        evt.enqueueWork(() -> {

            Sheets.addWoodType(CommonRegistries.AraucariaType);
            Sheets.addWoodType(CommonRegistries.CalamitesType);
            Sheets.addWoodType(CommonRegistries.GinkgoType);
            Sheets.addWoodType(CommonRegistries.PhoenixType);
            Sheets.addWoodType(CommonRegistries.PsaroniusType);
        });

        SoundHandler.registrer.register(FMLJavaModLoadingContext.get().getModEventBus());


        //Binding screens to types
        MenuScreens.<CleanerMenu, CleanerScreen>register(ModBlockEntities.modScreenTypes.CleanerMenuType.get(), CleanerScreen::new);
        MenuScreens.<DNACombinatorHybridizerMenu, DNACombinatorHybridizerScreen>register(ModBlockEntities.modScreenTypes.COMBINATOR_MENU_TYPE.get(), DNACombinatorHybridizerScreen::new);

        ModScreens.<CleanerBlockEntity, CleanerMenu, CleanerScreen>register(ModBlockEntities.CLEANING_STATION.get(), CleanerScreen::new);
        ModScreens.<DNACombinatorHybridizerBlockEntity, DNACombinatorHybridizerMenu, DNACombinatorHybridizerScreen>register(ModBlockEntities.DNA_COMBINATOR_HYBRIDIZER.get(), DNACombinatorHybridizerScreen::new);




        for (Block b:
             renderlayers.keySet()) {
            //noinspection removal
            ItemBlockRenderTypes.setRenderLayer(b, renderlayers.get(b));
        }

        FMLJavaModLoadingContext.get().getModEventBus().register(RenderingHandler.class);


    }

    public static Logger getLogger(){
        return LOGGER;
    }

    @SubscribeEvent
    public static void registerRecipeSerializers(RegisterEvent event) {

//        System.out.println("Registered Serializers");

        // Vanilla has a registry for recipe types, but it does not actively use this registry.
        // While this makes registering your recipe type an optional step, I recommend
        // registering it anyway to allow other mods to discover your custom recipe types.
//        Registry.register(Registry.RECIPE_TYPE, new ResourceLocation(modid, "cleaning_recipe_type"), CLEANING_RECIPE_TYPE);


        event.register(ForgeRegistries.Keys.RECIPE_SERIALIZERS, (helper) ->{
            ((RegisterEvent.RegisterHelper<RecipeSerializer<?>>)helper).register(new ResourceLocation(modid, "cleaning_recipe_serializer"), CleaningRecipie.INSTANCE);
        });
        // Register the recipe serializer. This handles from json, from packet, and to packet.
        event.register(ForgeRegistries.Keys.RECIPE_TYPES, (helper) ->{
            helper.register(new ResourceLocation(modid, "cleaning_recipe_type"), CLEANING_RECIPE_TYPE);
        });

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
//        profilerFiller = event.getServer().getProfiler();
    }

//    public void onLevelLoadEvent(LevelLoad)
    public static Boolean never(BlockState p_50779_, BlockGetter p_50780_, BlockPos p_50781_, EntityType<?> p_50782_) {
        return (boolean)false;
    }
    public static boolean never(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
        return false;
    }

    public static void debugHook(){
        System.out.println("Debugging!");


    }

    public void postClientTickEvent(TickEvent.ClientTickEvent evt){
//        System.out.println("AAAAAAAAAAAAAAAAA");
//        DistExecutor.safeCallWhenOn(Dist.CLIENT, SafeClientMethods::addClientTick);
    }





    //Start animation debugging code!
    public static ArrayList<String> erroredIdentifiers = new ArrayList<>();

    public static void checkCubeId(String id) {
//        if(erroredIdentifiers.contains(id)){
//            Jurassicworldreborn.getLogger().error("Animating errored cube ID! ID: " + id);
//        }

    }







}
