package net.gamma02.jurassicworldreborn.client;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.client.render.RenderingHandler;
import net.gamma02.jurassicworldreborn.client.render.block.DisplayBlockRendererWithoutLevel;
import net.gamma02.jurassicworldreborn.client.screens.*;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.CommonRegistries;
import net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNACombinatorHybridizer.DNACombinatorHybridizerBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNACombinatorHybridizer.DNACombinatorHybridizerMenu;
import net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities;
import net.gamma02.jurassicworldreborn.common.blocks.entities.cleaner.CleanerBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.cleaner.CleanerMenu;
import net.gamma02.jurassicworldreborn.common.network.Network;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.HashMap;

@Mod.EventBusSubscriber(modid = Jurassicworldreborn.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class JurassicClient {
    public static DisplayBlockRendererWithoutLevel displayBlockRendererWithoutLevel;

    @SubscribeEvent
    public void clientTickEvent(TickEvent.ClientTickEvent evt){
        Level level = Minecraft.getInstance().level;

        HashMap<BlockPos, Int2ObjectArrayMap<ItemStack>> newSlotMap = new HashMap<>();

        Network.slotMap.forEach(((blockPos, map) -> {
            if(level != null) {
                if (level.getBlockEntity(blockPos) != null) {
                    newSlotMap.put(blockPos, map);
                }
            }
        }));

        Network.slotMap = newSlotMap;
    }



    public static void clientSetup(final FMLClientSetupEvent evt){

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
        MenuScreens.<CleanerMenu, CleanerScreen>register(ModBlockEntities.ModScreenTypes.CleanerMenuType.get(), CleanerScreen::new);
        MenuScreens.<DNACombinatorHybridizerMenu, DNACombinatorHybridizerScreen>register(ModBlockEntities.ModScreenTypes.COMBINATOR_MENU_TYPE.get(), DNACombinatorHybridizerScreen::new);
        MenuScreens.register(ModBlockEntities.ModScreenTypes.FOSSIL_GRINDER_MENU_TYPE.get(), FossilGrinderScreen::new);
        MenuScreens.register(ModBlockEntities.ModScreenTypes.DNA_SEQUENCER_MENU_TYPE.get(), DNASequencerScreen::new);
        MenuScreens.register(ModBlockEntities.ModScreenTypes.DNA_EXTRACTOR_MENU_TYPE.get(), DNAExtractorScreen::new);
        MenuScreens.register(ModBlockEntities.ModScreenTypes.DNA_SYNTHESIZER_MENU_TYPE.get(), DNASynthesizerScreen::new);
        MenuScreens.register(ModBlockEntities.ModScreenTypes.INCUBATOR_MENU_TYPE.get(), IncubatorScreen::new);
        MenuScreens.register(ModBlockEntities.ModScreenTypes.EMBRYONIC_MACHINE_MENU_TYPE.get(), EmbryronicMachineScreen::new);
        MenuScreens.register(ModBlockEntities.ModScreenTypes.EMBRYO_CALCIFICATION_MACHINE_MENU_TYPE.get(), EmbryoCalcificationMachineScreen::new);

        ModScreens.<CleanerBlockEntity, CleanerMenu, CleanerScreen>register(ModBlockEntities.CLEANING_STATION.get(), CleanerScreen::new);
        ModScreens.<DNACombinatorHybridizerBlockEntity, DNACombinatorHybridizerMenu, DNACombinatorHybridizerScreen>register(ModBlockEntities.DNA_COMBINATOR_HYBRIDIZER.get(), DNACombinatorHybridizerScreen::new);

//        ModScreens.<FossilGrinderBlockEntity, FossilGrinderMenu, FossilGrinderScreen>register(ModBlockEntities.FOSSIL_GRINDER_BLOCK_ENTITY.get(), FossilGrinderScreen::new);




        for (Block b:
                Jurassicworldreborn.renderlayers.keySet()) {
            //noinspection removal
            ItemBlockRenderTypes.setRenderLayer(b, Jurassicworldreborn.renderlayers.get(b));
        }

        FMLJavaModLoadingContext.get().getModEventBus().register(RenderingHandler.class);


    }



}
