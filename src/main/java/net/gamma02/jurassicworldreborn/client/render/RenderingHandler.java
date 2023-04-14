package net.gamma02.jurassicworldreborn.client.render;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.client.model.AnimatableModel;
import net.gamma02.jurassicworldreborn.client.render.entity.DinosaurRenderInfo;
import net.gamma02.jurassicworldreborn.client.render.entity.DinosaurRenderer;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimator;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.entity.*;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.AchillobatorEntity;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurHandler;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.GrowthStage;
import net.gamma02.jurassicworldreborn.common.entities.ModEntities;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashMap;

import static net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurHandler.*;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Jurassicworldreborn.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RenderingHandler{

    public static ArrayList<EntityAnimator<? extends LivingEntity>> ANIMATORS = new ArrayList<>();



    @SubscribeEvent
    public void registerEntityRendersEvent(final EntityRenderersEvent.RegisterRenderers event){

        //Registration for the Achillobator. Did this first, all of the others should follow this template of sorts.
        DinosaurRenderInfo AchillobatorInfo = new DinosaurRenderInfo(ACHILLOBATOR, new AchillobatorAnimator());
        event.registerEntityRenderer(ModEntities.ACHILLOBATOR_ENTITY_TYPE.get(), (ctx) ->
                new DinosaurRenderer(ctx,
                        helper.getDefaultModelFromDinosaur(AchillobatorInfo),
                        0.5f,
                        AchillobatorInfo));

    }




    static class helper{
        static void doEntityRegistration(final EntityRenderersEvent.RegisterRenderers event){
            //OviraptorEntity registration: auto generated
            DinosaurRenderInfo OviraptorInfo = new DinosaurRenderInfo(OVIRAPTOR, new OviraptorAnimator());
            event.registerEntityRenderer(ModEntities.OVIRAPTOR_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(OviraptorInfo),
                            0.5f,
                            OviraptorInfo));

//DeinotheriumEntity registration: auto generated
            DinosaurRenderInfo DeinotheriumInfo = new DinosaurRenderInfo(DEINOTHERIUM, new DeinotheriumAnimator());
            event.registerEntityRenderer(ModEntities.DEINOTHERIUM_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(DeinotheriumInfo),
                            0.5f,
                            DeinotheriumInfo));

//MicroraptorEntity registration: auto generated
            DinosaurRenderInfo MicroraptorInfo = new DinosaurRenderInfo(MICRORAPTOR, new MicroraptorAnimator());
            event.registerEntityRenderer(ModEntities.MICRORAPTOR_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(MicroraptorInfo),
                            0.5f,
                            MicroraptorInfo));

//MammothEntity registration: auto generated
            DinosaurRenderInfo MammothInfo = new DinosaurRenderInfo(MAMMOTH, new MammothAnimator());
            event.registerEntityRenderer(ModEntities.MAMMOTH_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(MammothInfo),
                            0.5f,
                            MammothInfo));

//DodoEntity registration: auto generated
            DinosaurRenderInfo DodoInfo = new DinosaurRenderInfo(DODO, new DodoAnimator());
            event.registerEntityRenderer(ModEntities.DODO_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(DodoInfo),
                            0.5f,
                            DodoInfo));

//ZhenyuanopterusEntity registration: auto generated
            DinosaurRenderInfo ZhenyuanopterusInfo = new DinosaurRenderInfo(ZHENYUANOPTERUS, new ZhenyuanopterusAnimator());
            event.registerEntityRenderer(ModEntities.ZHENYUANOPTERUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(ZhenyuanopterusInfo),
                            0.5f,
                            ZhenyuanopterusInfo));

//PostosuchusEntity registration: auto generated
            DinosaurRenderInfo PostosuchusInfo = new DinosaurRenderInfo(POSTOSUCHUS, new PostosuchusAnimator());
            event.registerEntityRenderer(ModEntities.POSTOSUCHUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(PostosuchusInfo),
                            0.5f,
                            PostosuchusInfo));

//IndoraptorEntity registration: auto generated
            DinosaurRenderInfo IndoraptorInfo = new DinosaurRenderInfo(INDORAPTOR, new IndoraptorAnimator());
            event.registerEntityRenderer(ModEntities.INDORAPTOR_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(IndoraptorInfo),
                            0.5f,
                            IndoraptorInfo));

//OthnieliaEntity registration: auto generated
            DinosaurRenderInfo OthnieliaInfo = new DinosaurRenderInfo(OTHNIELIA, new OthnieliaAnimator());
            event.registerEntityRenderer(ModEntities.OTHNIELIA_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(OthnieliaInfo),
                            0.5f,
                            OthnieliaInfo));

//PteranodonEntity registration: auto generated
            DinosaurRenderInfo PteranodonInfo = new DinosaurRenderInfo(PTERANODON, new PteranodonAnimator());
            event.registerEntityRenderer(ModEntities.PTERANODON_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(PteranodonInfo),
                            0.5f,
                            PteranodonInfo));

//IndominusEntity registration: auto generated
            DinosaurRenderInfo IndominusInfo = new DinosaurRenderInfo(INDOMINUS, new IndominusAnimator());
            event.registerEntityRenderer(ModEntities.INDOMINUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(IndominusInfo),
                            0.5f,
                            IndominusInfo));

//AnkylosaurusEntity registration: auto generated
            DinosaurRenderInfo AnkylosaurusInfo = new DinosaurRenderInfo(ANKYLOSAURUS, new AnkylosaurusAnimator());
            event.registerEntityRenderer(ModEntities.ANKYLOSAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(AnkylosaurusInfo),
                            0.5f,
                            AnkylosaurusInfo));

//ArsinoitheriumEntity registration: auto generated
            DinosaurRenderInfo ArsinoitheriumInfo = new DinosaurRenderInfo(ARSINOITHERIUM, new ArsinoitheriumAnimator());
            event.registerEntityRenderer(ModEntities.ARSINOITHERIUM_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(ArsinoitheriumInfo),
                            0.5f,
                            ArsinoitheriumInfo));

//CrassigyrinusEntity registration: auto generated
            DinosaurRenderInfo CrassigyrinusInfo = new DinosaurRenderInfo(CRASSIGYRINUS, new CrassigyrinusAnimator());
            event.registerEntityRenderer(ModEntities.CRASSIGYRINUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(CrassigyrinusInfo),
                            0.5f,
                            CrassigyrinusInfo));

//AmmoniteEntity registration: auto generated
            DinosaurRenderInfo AmmoniteInfo = new DinosaurRenderInfo(AMMONITE, new AmmoniteAnimator());
            event.registerEntityRenderer(ModEntities.AMMONITE_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(AmmoniteInfo),
                            0.5f,
                            AmmoniteInfo));

//ProceratosaurusEntity registration: auto generated
            DinosaurRenderInfo ProceratosaurusInfo = new DinosaurRenderInfo(PROCERATOSAURUS, new ProceratosaurusAnimator());
            event.registerEntityRenderer(ModEntities.PROCERATOSAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(ProceratosaurusInfo),
                            0.5f,
                            ProceratosaurusInfo));

//ApatosaurusEntity registration: auto generated
            DinosaurRenderInfo ApatosaurusInfo = new DinosaurRenderInfo(APATOSAURUS, new ApatosaurusAnimator());
            event.registerEntityRenderer(ModEntities.APATOSAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(ApatosaurusInfo),
                            0.5f,
                            ApatosaurusInfo));

//CarnotaurusEntity registration: auto generated
            DinosaurRenderInfo CarnotaurusInfo = new DinosaurRenderInfo(CARNOTAURUS, new CarnotaurusAnimator());
            event.registerEntityRenderer(ModEntities.CARNOTAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(CarnotaurusInfo),
                            0.5f,
                            CarnotaurusInfo));

//DunkleosteusEntity registration: auto generated
            DinosaurRenderInfo DunkleosteusInfo = new DinosaurRenderInfo(DUNKLEOSTEUS, new DunkleosteusAnimator());
            event.registerEntityRenderer(ModEntities.DUNKLEOSTEUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(DunkleosteusInfo),
                            0.5f,
                            DunkleosteusInfo));

//TyrannosaurusEntity registration: auto generated
            DinosaurRenderInfo TyrannosaurusInfo = new DinosaurRenderInfo(TYRANNOSAURUS, new TyrannosaurusAnimator());
            event.registerEntityRenderer(ModEntities.TYRANNOSAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(TyrannosaurusInfo),
                            0.5f,
                            TyrannosaurusInfo));

//RaphusrexEntity registration: auto generated
            DinosaurRenderInfo RaphusrexInfo = new DinosaurRenderInfo(RAPHUSREX, new RaphusrexAnimator());
            event.registerEntityRenderer(ModEntities.RAPHUSREX_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(RaphusrexInfo),
                            0.5f,
                            RaphusrexInfo));

//ChasmosaurusEntity registration: auto generated
            DinosaurRenderInfo ChasmosaurusInfo = new DinosaurRenderInfo(CHASMOSAURUS, new ChasmosaurusAnimator());
            event.registerEntityRenderer(ModEntities.CHASMOSAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(ChasmosaurusInfo),
                            0.5f,
                            ChasmosaurusInfo));

//MetriacanthosaurusEntity registration: auto generated
            DinosaurRenderInfo MetriacanthosaurusInfo = new DinosaurRenderInfo(METRIACANTHOSAURUS, new MetriacanthosaurusAnimator());
            event.registerEntityRenderer(ModEntities.METRIACANTHOSAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(MetriacanthosaurusInfo),
                            0.5f,
                            MetriacanthosaurusInfo));

//TroodonEntity registration: auto generated
            DinosaurRenderInfo TroodonInfo = new DinosaurRenderInfo(TROODON, new TroodonAnimator());
            event.registerEntityRenderer(ModEntities.TROODON_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(TroodonInfo),
                            0.5f,
                            TroodonInfo));

//HerrerasaurusEntity registration: auto generated
            DinosaurRenderInfo HerrerasaurusInfo = new DinosaurRenderInfo(HERRERASAURUS, new HerrerasaurusAnimator());
            event.registerEntityRenderer(ModEntities.HERRERASAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(HerrerasaurusInfo),
                            0.5f,
                            HerrerasaurusInfo));

//BaryonyxEntity registration: auto generated
            DinosaurRenderInfo BaryonyxInfo = new DinosaurRenderInfo(BARYONYX, new BaryonyxAnimator());
            event.registerEntityRenderer(ModEntities.BARYONYX_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(BaryonyxInfo),
                            0.5f,
                            BaryonyxInfo));

//BeelzebufoEntity registration: auto generated
            DinosaurRenderInfo BeelzebufoInfo = new DinosaurRenderInfo(BEELZEBUFO, new BeelzebufoAnimator());
            event.registerEntityRenderer(ModEntities.BEELZEBUFO_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(BeelzebufoInfo),
                            0.5f,
                            BeelzebufoInfo));

//VelociraptorBlueEntity registration: auto generated
            DinosaurRenderInfo VelociraptorBlueInfo = new DinosaurRenderInfo(VELOCIRAPTOR, new VelociraptorBlueAnimator());
            event.registerEntityRenderer(ModEntities.VELOCIRAPTORBLUE_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(VelociraptorBlueInfo),
                            0.5f,
                            VelociraptorBlueInfo));

//VelociraptorEchoEntity registration: auto generated
            DinosaurRenderInfo VelociraptorEchoInfo = new DinosaurRenderInfo(VELOCIRAPTOR, new VelociraptorEchoAnimator());
            event.registerEntityRenderer(ModEntities.VELOCIRAPTORECHO_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(VelociraptorEchoInfo),
                            0.5f,
                            VelociraptorEchoInfo));

//SinoceratopsEntity registration: auto generated
            DinosaurRenderInfo SinoceratopsInfo = new DinosaurRenderInfo(SINOCERATOPS, new SinoceratopsAnimator());
            event.registerEntityRenderer(ModEntities.SINOCERATOPS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(SinoceratopsInfo),
                            0.5f,
                            SinoceratopsInfo));

//ParasaurolophusEntity registration: auto generated
            DinosaurRenderInfo ParasaurolophusInfo = new DinosaurRenderInfo(PARASAUROLOPHUS, new ParasaurolophusAnimator());
            event.registerEntityRenderer(ModEntities.PARASAUROLOPHUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(ParasaurolophusInfo),
                            0.5f,
                            ParasaurolophusInfo));

//MamenchisaurusEntity registration: auto generated
            DinosaurRenderInfo MamenchisaurusInfo = new DinosaurRenderInfo(MAMENCHISAURUS, new MamenchisaurusAnimator());
            event.registerEntityRenderer(ModEntities.MAMENCHISAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(MamenchisaurusInfo),
                            0.5f,
                            MamenchisaurusInfo));

//DimorphodonEntity registration: auto generated
            DinosaurRenderInfo DimorphodonInfo = new DinosaurRenderInfo(DIMORPHODON, new DimorphodonAnimator());
            event.registerEntityRenderer(ModEntities.DIMORPHODON_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(DimorphodonInfo),
                            0.5f,
                            DimorphodonInfo));

//AllosaurusEntity registration: auto generated
            DinosaurRenderInfo AllosaurusInfo = new DinosaurRenderInfo(ALLOSAURUS, new AllosaurusAnimator());
            event.registerEntityRenderer(ModEntities.ALLOSAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(AllosaurusInfo),
                            0.5f,
                            AllosaurusInfo));

//MosasaurusEntity registration: auto generated
            DinosaurRenderInfo MosasaurusInfo = new DinosaurRenderInfo(MOSASAURUS, new MosasaurusAnimator());
            event.registerEntityRenderer(ModEntities.MOSASAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(MosasaurusInfo),
                            0.5f,
                            MosasaurusInfo));

//MawsoniaEntity registration: auto generated
            DinosaurRenderInfo MawsoniaInfo = new DinosaurRenderInfo(MAWSONIA, new MawsoniaAnimator());
            event.registerEntityRenderer(ModEntities.MAWSONIA_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(MawsoniaInfo),
                            0.5f,
                            MawsoniaInfo));

//VelociraptorDeltaEntity registration: auto generated
            DinosaurRenderInfo VelociraptorDeltaInfo = new DinosaurRenderInfo(VELOCIRAPTOR, new VelociraptorDeltaAnimator());
            event.registerEntityRenderer(ModEntities.VELOCIRAPTORDELTA_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(VelociraptorDeltaInfo),
                            0.5f,
                            VelociraptorDeltaInfo));

//AlvarezsaurusEntity registration: auto generated
            DinosaurRenderInfo AlvarezsaurusInfo = new DinosaurRenderInfo(ALVAREZSAURUS, new AlvarezsaurusAnimator());
            event.registerEntityRenderer(ModEntities.ALVAREZSAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(AlvarezsaurusInfo),
                            0.5f,
                            AlvarezsaurusInfo));

//RugopsEntity registration: auto generated
            DinosaurRenderInfo RugopsInfo = new DinosaurRenderInfo(RUGOPS, new RugopsAnimator());
            event.registerEntityRenderer(ModEntities.RUGOPS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(RugopsInfo),
                            0.5f,
                            RugopsInfo));

//CearadactylusEntity registration: auto generated
            DinosaurRenderInfo CearadactylusInfo = new DinosaurRenderInfo(CEARADACTYLUS, new CearadactylusAnimator());
            event.registerEntityRenderer(ModEntities.CEARADACTYLUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(CearadactylusInfo),
                            0.5f,
                            CearadactylusInfo));

//CorythosaurusEntity registration: auto generated
            DinosaurRenderInfo CorythosaurusInfo = new DinosaurRenderInfo(CORYTHOSAURUS, new CorythosaurusAnimator());
            event.registerEntityRenderer(ModEntities.CORYTHOSAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(CorythosaurusInfo),
                            0.5f,
                            CorythosaurusInfo));

//CompsognathusEntity registration: auto generated
            DinosaurRenderInfo CompsognathusInfo = new DinosaurRenderInfo(COMPSOGNATHUS, new CompsognathusAnimator());
            event.registerEntityRenderer(ModEntities.COMPSOGNATHUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(CompsognathusInfo),
                            0.5f,
                            CompsognathusInfo));

//LudodactylusEntity registration: auto generated
            DinosaurRenderInfo LudodactylusInfo = new DinosaurRenderInfo(LUDODACTYLUS, new LudodactylusAnimator());
            event.registerEntityRenderer(ModEntities.LUDODACTYLUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(LudodactylusInfo),
                            0.5f,
                            LudodactylusInfo));

//LeaellynasauraEntity registration: auto generated
            DinosaurRenderInfo LeaellynasauraInfo = new DinosaurRenderInfo(LEAELLYNASAURA, new LeaellynasauraAnimator());
            event.registerEntityRenderer(ModEntities.LEAELLYNASAURA_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(LeaellynasauraInfo),
                            0.5f,
                            LeaellynasauraInfo));

//MoganopterusEntity registration: auto generated
            DinosaurRenderInfo MoganopterusInfo = new DinosaurRenderInfo(MOGANOPTERUS, new MoganopterusAnimator());
            event.registerEntityRenderer(ModEntities.MOGANOPTERUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(MoganopterusInfo),
                            0.5f,
                            MoganopterusInfo));

//SuchomimusEntity registration: auto generated
            DinosaurRenderInfo SuchomimusInfo = new DinosaurRenderInfo(SUCHOMIMUS, new SuchomimusAnimator());
            event.registerEntityRenderer(ModEntities.SUCHOMIMUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(SuchomimusInfo),
                            0.5f,
                            SuchomimusInfo));

//MajungasaurusEntity registration: auto generated
            DinosaurRenderInfo MajungasaurusInfo = new DinosaurRenderInfo(MAJUNGASAURUS, new MajungasaurusAnimator());
            event.registerEntityRenderer(ModEntities.MAJUNGASAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(MajungasaurusInfo),
                            0.5f,
                            MajungasaurusInfo));

//ProtoceratopsEntity registration: auto generated
            DinosaurRenderInfo ProtoceratopsInfo = new DinosaurRenderInfo(PROTOCERATOPS, new ProtoceratopsAnimator());
            event.registerEntityRenderer(ModEntities.PROTOCERATOPS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(ProtoceratopsInfo),
                            0.5f,
                            ProtoceratopsInfo));

//TitanisEntity registration: auto generated
            DinosaurRenderInfo TitanisInfo = new DinosaurRenderInfo(TITANIS, new TitanisAnimator());
            event.registerEntityRenderer(ModEntities.TITANIS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(TitanisInfo),
                            0.5f,
                            TitanisInfo));

//CoelacanthEntity registration: auto generated
            DinosaurRenderInfo CoelacanthInfo = new DinosaurRenderInfo(COELACANTH, new CoelacanthAnimator());
            event.registerEntityRenderer(ModEntities.COELACANTH_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(CoelacanthInfo),
                            0.5f,
                            CoelacanthInfo));

//GallimimusEntity registration: auto generated
            DinosaurRenderInfo GallimimusInfo = new DinosaurRenderInfo(GALLIMIMUS, new GallimimusAnimator());
            event.registerEntityRenderer(ModEntities.GALLIMIMUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(GallimimusInfo),
                            0.5f,
                            GallimimusInfo));

//CeratosaurusEntity registration: auto generated
            DinosaurRenderInfo CeratosaurusInfo = new DinosaurRenderInfo(CERATOSAURUS, new CeratosaurusAnimator());
            event.registerEntityRenderer(ModEntities.CERATOSAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(CeratosaurusInfo),
                            0.5f,
                            CeratosaurusInfo));

//VelociraptorCharlieEntity registration: auto generated
            DinosaurRenderInfo VelociraptorCharlieInfo = new DinosaurRenderInfo(VELOCIRAPTOR, new VelociraptorCharlieAnimator());
            event.registerEntityRenderer(ModEntities.VELOCIRAPTORCHARLIE_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(VelociraptorCharlieInfo),
                            0.5f,
                            VelociraptorCharlieInfo));

//SpinosaurusEntity registration: auto generated
            DinosaurRenderInfo SpinosaurusInfo = new DinosaurRenderInfo(SPINOSAURUS, new SpinosaurusAnimator());
            event.registerEntityRenderer(ModEntities.SPINOSAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(SpinosaurusInfo),
                            0.5f,
                            SpinosaurusInfo));

//PachycephalosaurusEntity registration: auto generated
            DinosaurRenderInfo PachycephalosaurusInfo = new DinosaurRenderInfo(PACHYCEPHALOSAURUS, new PachycephalosaurusAnimator());
            event.registerEntityRenderer(ModEntities.PACHYCEPHALOSAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(PachycephalosaurusInfo),
                            0.5f,
                            PachycephalosaurusInfo));

//QuetzalEntity registration: auto generated
            DinosaurRenderInfo QuetzalInfo = new DinosaurRenderInfo(QUETZAL, new QuetzalAnimator());
            event.registerEntityRenderer(ModEntities.QUETZAL_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(QuetzalInfo),
                            0.5f,
                            QuetzalInfo));

//CarcharodontosaurusEntity registration: auto generated
            DinosaurRenderInfo CarcharodontosaurusInfo = new DinosaurRenderInfo(CARCHARODONTOSAURUS, new CarcharodontosaurusAnimator());
            event.registerEntityRenderer(ModEntities.CARCHARODONTOSAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(CarcharodontosaurusInfo),
                            0.5f,
                            CarcharodontosaurusInfo));

//TylosaurusEntity registration: auto generated
            DinosaurRenderInfo TylosaurusInfo = new DinosaurRenderInfo(TYLOSAURUS, new TylosaurusAnimator());
            event.registerEntityRenderer(ModEntities.TYLOSAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(TylosaurusInfo),
                            0.5f,
                            TylosaurusInfo));

//OrnithomimusEntity registration: auto generated
            DinosaurRenderInfo OrnithomimusInfo = new DinosaurRenderInfo(ORNITHOMIMUS, new OrnithomimusAnimator());
            event.registerEntityRenderer(ModEntities.ORNITHOMIMUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(OrnithomimusInfo),
                            0.5f,
                            OrnithomimusInfo));

//MegapiranhaEntity registration: auto generated
            DinosaurRenderInfo MegapiranhaInfo = new DinosaurRenderInfo(MEGAPIRANHA, new MegapiranhaAnimator());
            event.registerEntityRenderer(ModEntities.MEGAPIRANHA_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(MegapiranhaInfo),
                            0.5f,
                            MegapiranhaInfo));

//DiplodocusEntity registration: auto generated
            DinosaurRenderInfo DiplodocusInfo = new DinosaurRenderInfo(DIPLODOCUS, new DiplodocusAnimator());
            event.registerEntityRenderer(ModEntities.DIPLODOCUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(DiplodocusInfo),
                            0.5f,
                            DiplodocusInfo));

//DilophosaurusEntity registration: auto generated
            DinosaurRenderInfo DilophosaurusInfo = new DinosaurRenderInfo(DILOPHOSAURUS, new DilophosaurusAnimator());
            event.registerEntityRenderer(ModEntities.DILOPHOSAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(DilophosaurusInfo),
                            0.5f,
                            DilophosaurusInfo));

//StyracosaurusEntity registration: auto generated
            DinosaurRenderInfo StyracosaurusInfo = new DinosaurRenderInfo(STYRACOSAURUS, new StyracosaurusAnimator());
            event.registerEntityRenderer(ModEntities.STYRACOSAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(StyracosaurusInfo),
                            0.5f,
                            StyracosaurusInfo));

//GuanlongEntity registration: auto generated
            DinosaurRenderInfo GuanlongInfo = new DinosaurRenderInfo(GUANLONG, new GuanlongAnimator());
            event.registerEntityRenderer(ModEntities.GUANLONG_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(GuanlongInfo),
                            0.5f,
                            GuanlongInfo));

//CamarasaurusEntity registration: auto generated
            DinosaurRenderInfo CamarasaurusInfo = new DinosaurRenderInfo(CAMARASAURUS, new CamarasaurusAnimator());
            event.registerEntityRenderer(ModEntities.CAMARASAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(CamarasaurusInfo),
                            0.5f,
                            CamarasaurusInfo));

//HyaenodonEntity registration: auto generated
            DinosaurRenderInfo HyaenodonInfo = new DinosaurRenderInfo(HYAENODON, new HyaenodonAnimator());
            event.registerEntityRenderer(ModEntities.HYAENODON_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(HyaenodonInfo),
                            0.5f,
                            HyaenodonInfo));

//CoelurusEntity registration: auto generated
            DinosaurRenderInfo CoelurusInfo = new DinosaurRenderInfo(COELURUS, new CoelurusAnimator());
            event.registerEntityRenderer(ModEntities.COELURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(CoelurusInfo),
                            0.5f,
                            CoelurusInfo));

//DiplocaulusEntity registration: auto generated
            DinosaurRenderInfo DiplocaulusInfo = new DinosaurRenderInfo(DIPLOCAULUS, new DiplocaulusAnimator());
            event.registerEntityRenderer(ModEntities.DIPLOCAULUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(DiplocaulusInfo),
                            0.5f,
                            DiplocaulusInfo));

//DreadnoughtusEntity registration: auto generated
            DinosaurRenderInfo DreadnoughtusInfo = new DinosaurRenderInfo(DREADNOUGHTUS, new DreadnoughtusAnimator());
            event.registerEntityRenderer(ModEntities.DREADNOUGHTUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(DreadnoughtusInfo),
                            0.5f,
                            DreadnoughtusInfo));

//EdmontosaurusEntity registration: auto generated
            DinosaurRenderInfo EdmontosaurusInfo = new DinosaurRenderInfo(EDMONTOSAURUS, new EdmontosaurusAnimator());
            event.registerEntityRenderer(ModEntities.EDMONTOSAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(EdmontosaurusInfo),
                            0.5f,
                            EdmontosaurusInfo));

//StegosaurusEntity registration: auto generated
            DinosaurRenderInfo StegosaurusInfo = new DinosaurRenderInfo(STEGOSAURUS, new StegosaurusAnimator());
            event.registerEntityRenderer(ModEntities.STEGOSAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(StegosaurusInfo),
                            0.5f,
                            StegosaurusInfo));

//SpinoraptorEntity registration: auto generated
            DinosaurRenderInfo SpinoraptorInfo = new DinosaurRenderInfo(SPINORAPTOR, new SpinoraptorAnimator());
            event.registerEntityRenderer(ModEntities.SPINORAPTOR_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(SpinoraptorInfo),
                            0.5f,
                            SpinoraptorInfo));

//AchillobatorEntity registration: auto generated
            DinosaurRenderInfo AchillobatorInfo = new DinosaurRenderInfo(ACHILLOBATOR, new AchillobatorAnimator());
            event.registerEntityRenderer(ModEntities.ACHILLOBATOR_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(AchillobatorInfo),
                            0.5f,
                            AchillobatorInfo));

//ChilesaurusEntity registration: auto generated
            DinosaurRenderInfo ChilesaurusInfo = new DinosaurRenderInfo(CHILESAURUS, new ChilesaurusAnimator());
            event.registerEntityRenderer(ModEntities.CHILESAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(ChilesaurusInfo),
                            0.5f,
                            ChilesaurusInfo));

//MegatheriumEntity registration: auto generated
            DinosaurRenderInfo MegatheriumInfo = new DinosaurRenderInfo(MEGATHERIUM, new MegatheriumAnimator());
            event.registerEntityRenderer(ModEntities.MEGATHERIUM_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(MegatheriumInfo),
                            0.5f,
                            MegatheriumInfo));

//SegisaurusEntity registration: auto generated
            DinosaurRenderInfo SegisaurusInfo = new DinosaurRenderInfo(SEGISAURUS, new SegisaurusAnimator());
            event.registerEntityRenderer(ModEntities.SEGISAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(SegisaurusInfo),
                            0.5f,
                            SegisaurusInfo));

//AnkylodocusEntity registration: auto generated
            DinosaurRenderInfo AnkylodocusInfo = new DinosaurRenderInfo(ANKYLODOCUS, new AnkylodocusAnimator());
            event.registerEntityRenderer(ModEntities.ANKYLODOCUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(AnkylodocusInfo),
                            0.5f,
                            AnkylodocusInfo));

//BrachiosaurusEntity registration: auto generated
            DinosaurRenderInfo BrachiosaurusInfo = new DinosaurRenderInfo(BRACHIOSAURUS, new BrachiosaurusAnimator());
            event.registerEntityRenderer(ModEntities.BRACHIOSAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(BrachiosaurusInfo),
                            0.5f,
                            BrachiosaurusInfo));

//SmilodonEntity registration: auto generated
            DinosaurRenderInfo SmilodonInfo = new DinosaurRenderInfo(SMILODON, new SmilodonAnimator());
            event.registerEntityRenderer(ModEntities.SMILODON_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(SmilodonInfo),
                            0.5f,
                            SmilodonInfo));

//MicroceratusEntity registration: auto generated
            DinosaurRenderInfo MicroceratusInfo = new DinosaurRenderInfo(MICROCERATUS, new MicroceratusAnimator());
            event.registerEntityRenderer(ModEntities.MICROCERATUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(MicroceratusInfo),
                            0.5f,
                            MicroceratusInfo));

//LeptictidiumEntity registration: auto generated
            DinosaurRenderInfo LeptictidiumInfo = new DinosaurRenderInfo(LEPTICTIDIUM, new LeptictidiumAnimator());
            event.registerEntityRenderer(ModEntities.LEPTICTIDIUM_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(LeptictidiumInfo),
                            0.5f,
                            LeptictidiumInfo));

//HypsilophodonEntity registration: auto generated
            DinosaurRenderInfo HypsilophodonInfo = new DinosaurRenderInfo(HYPSILOPHODON, new HypsilophodonAnimator());
            event.registerEntityRenderer(ModEntities.HYPSILOPHODON_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(HypsilophodonInfo),
                            0.5f,
                            HypsilophodonInfo));

//TherizinosaurusEntity registration: auto generated
            DinosaurRenderInfo TherizinosaurusInfo = new DinosaurRenderInfo(THERIZINOSAURUS, new TherizinosaurusAnimator());
            event.registerEntityRenderer(ModEntities.THERIZINOSAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(TherizinosaurusInfo),
                            0.5f,
                            TherizinosaurusInfo));

//VelociraptorEntity registration: auto generated
            DinosaurRenderInfo VelociraptorInfo = new DinosaurRenderInfo(VELOCIRAPTOR, new VelociraptorAnimator());
            event.registerEntityRenderer(ModEntities.VELOCIRAPTOR_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(VelociraptorInfo),
                            0.5f,
                            VelociraptorInfo));

//MussaurusEntity registration: auto generated
            DinosaurRenderInfo MussaurusInfo = new DinosaurRenderInfo(MUSSAURUS, new MussaurusAnimator());
            event.registerEntityRenderer(ModEntities.MUSSAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(MussaurusInfo),
                            0.5f,
                            MussaurusInfo));

//TriceratopsEntity registration: auto generated
            DinosaurRenderInfo TriceratopsInfo = new DinosaurRenderInfo(TRICERATOPS, new TriceratopsAnimator());
            event.registerEntityRenderer(ModEntities.TRICERATOPS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(TriceratopsInfo),
                            0.5f,
                            TriceratopsInfo));

//GiganotosaurusEntity registration: auto generated
            DinosaurRenderInfo GiganotosaurusInfo = new DinosaurRenderInfo(GIGANOTOSAURUS, new GiganotosaurusAnimator());
            event.registerEntityRenderer(ModEntities.GIGANOTOSAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(GiganotosaurusInfo),
                            0.5f,
                            GiganotosaurusInfo));

//TropeognathusEntity registration: auto generated
            DinosaurRenderInfo TropeognathusInfo = new DinosaurRenderInfo(TROPEOGNATHUS, new TropeognathusAnimator());
            event.registerEntityRenderer(ModEntities.TROPEOGNATHUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(TropeognathusInfo),
                            0.5f,
                            TropeognathusInfo));

//LambeosaurusEntity registration: auto generated
            DinosaurRenderInfo LambeosaurusInfo = new DinosaurRenderInfo(LAMBEOSAURUS, new LambeosaurusAnimator());
            event.registerEntityRenderer(ModEntities.LAMBEOSAURUS_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(LambeosaurusInfo),
                            0.5f,
                            LambeosaurusInfo));

//AlligatorGarEntity registration: auto generated
            DinosaurRenderInfo AlligatorGarInfo = new DinosaurRenderInfo(ALLIGATORGAR, new AlligatorGarAnimator());
            event.registerEntityRenderer(ModEntities.ALLIGATORGAR_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(AlligatorGarInfo),
                            0.5f,
                            AlligatorGarInfo));

//ElasmotheriumEntity registration: auto generated
            DinosaurRenderInfo ElasmotheriumInfo = new DinosaurRenderInfo(ELASMOTHERIUM, new ElasmotheriumAnimator());
            event.registerEntityRenderer(ModEntities.ELASMOTHERIUM_ENTITY_TYPE.get(), (ctx) ->
                    new DinosaurRenderer(ctx,
                            getDefaultModelFromDinosaur(ElasmotheriumInfo),
                            0.5f,
                            ElasmotheriumInfo));
        }
        static AnimatableModel getDefaultModelFromDinosaur(DinosaurRenderInfo info){
            return new AnimatableModel(info.getDinosaur().getModelContainer(GrowthStage.ADULT), info.getModelAnimator(GrowthStage.ADULT));
        }
    }


}

