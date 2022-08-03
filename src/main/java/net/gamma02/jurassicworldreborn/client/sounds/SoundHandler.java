package net.gamma02.jurassicworldreborn.client.sounds;

import com.google.common.collect.Lists;
import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.rmi.registry.RegistryHandler;
import java.util.List;

public class SoundHandler {

    public static DeferredRegister<SoundEvent> registrer;


    public static  SoundEvent TROODONS_AND_RAPTORS = create("troodons_and_raptors");
    public static  SoundEvent JURASSICRAFT_THEME = create("jurassicraft_theme");
    public static  SoundEvent DONT_MOVE_A_MUSCLE = create("dont_move_a_muscle");

    public static  SoundEvent STOMP = create("stomp");
    public static  SoundEvent FEEDER = create("feeder");
    public static  SoundEvent CAR_MOVE = create("car_move");

    public static  SoundEvent BRACHIOSAURUS_LIVING = create("brachiosaurus_living");
    public static  SoundEvent BRACHIOSAURUS_HURT = create("brachiosaurus_hurt");
    public static  SoundEvent BRACHIOSAURUS_DEATH = create("brachiosaurus_death");

    public static final SoundEvent DIPLODOCUS_DEATH = create("diplodocus_death");
    public static final SoundEvent DIPLODOCUS_HURT = create("diplodocus_hurt");
    public static final SoundEvent DIPLODOCUS_LIVING = create("diplodocus_living");

    public static  SoundEvent MAMENCHISAURUS_LIVING = create("mamenchisaurus_living");
    public static  SoundEvent MAMENCHISAURUS_CALLING = create("mamenchisaurus_call");
    public static  SoundEvent MAMENCHISAURUS_DEATH = create("mamenchisaurus_death");
    public static  SoundEvent MAMENCHISAURUS_HURT = create("mamenchisaurus_hurt");
    public static  SoundEvent MAMENCHISAURUS_THREAT = create("mamenchisaurus_threat");
    public static  SoundEvent MAMENCHISAURUS_MATING = create("mamenchisaurus_mate_call");

    public static  SoundEvent DREADNOUGHTUS_LIVING = create("dreadnoughtus_living");
    public static  SoundEvent DREADNOUGHTUS_CALLING = create("dreadnoughtus_call");
    public static  SoundEvent DREADNOUGHTUS_DEATH = create("dreadnoughtus_death");
    public static  SoundEvent DREADNOUGHTUS_HURT = create("dreadnoughtus_hurt");
    public static  SoundEvent DREADNOUGHTUS_THREAT = create("dreadnoughtus_threat");
    public static  SoundEvent DREADNOUGHTUS_MATING = create("dreadnoughtus_mate_call");

    public static  SoundEvent INDORAPTOR_LIVING = create("indoraptor_living");
    public static  SoundEvent INDORAPTOR_CALLING = create("indoraptor_call");
    public static  SoundEvent INDORAPTOR_DEATH = create("indoraptor_death");
    public static  SoundEvent INDORAPTOR_HURT = create("indoraptor_hurt");
    public static  SoundEvent INDORAPTOR_THREAT = create("indoraptor_threat");
    public static  SoundEvent INDORAPTOR_MATING = create("indoraptor_mate_call");
    public static  SoundEvent INDORAPTOR_ROAR = create("indoraptor_roar");
    public static  SoundEvent INDORAPTOR_BREATHING = create("indoraptor_breathing");


    public static  SoundEvent SINOCERATOPS_LIVING = create("sinoceratops_living");
    public static  SoundEvent SINOCERATOPS_CALLING = create("sinoceratops_call");
    public static  SoundEvent SINOCERATOPS_DEATH = create("sinoceratops_death");
    public static  SoundEvent SINOCERATOPS_HURT = create("sinoceratops_hurt");
    public static  SoundEvent SINOCERATOPS_THREAT = create("sinoceratops_threat");
    public static  SoundEvent SINOCERATOPS_MATING = create("sinoceratops_mate_call");

    public static  SoundEvent DODO_DEATH = create("dodo_death");
    public static  SoundEvent DODO_HURT = create("dodo_hurt");
    public static  SoundEvent DODO_LIVING = create("dodo_living");

    public static  SoundEvent RUGOPS_DEATH = create("rugops_death");
    public static  SoundEvent RUGOPS_HURT = create("rugops_hurt");
    public static  SoundEvent RUGOPS_LIVING = create("rugops_living");

    public static  SoundEvent HYPSILOPHODON_HURT = create("hypsilophodon_hurt");
    public static  SoundEvent HYPSILOPHODON_LIVING = create("hypsilophodon_living");

    public static  SoundEvent PARASAUROLOPHUS_LIVING = create("parasaurolophus_living");
    public static  SoundEvent PARASAUROLOPHUS_CALL = create("parasaurolophus_call");
    public static  SoundEvent PARASAUROLOPHUS_DEATH = create("parasaurolophus_death");
    public static  SoundEvent PARASAUROLOPHUS_HURT = create("parasaurolophus_hurt");

    public static  SoundEvent TRICERATOPS_LIVING = create("triceratops_living");
    public static  SoundEvent TRICERATOPS_DEATH = create("triceratops_death");
    public static  SoundEvent TRICERATOPS_HURT = create("triceratops_hurt");

    public static  SoundEvent STEGOSAURUS_LIVING = create("stegosaurus_living");
    public static  SoundEvent STEGOSAURUS_HURT = create("stegosaurus_hurt");
    public static  SoundEvent STEGOSAURUS_DEATH = create("stegosaurus_death");

    public static  SoundEvent DILOPHOSAURUS_LIVING = create("dilophosaurus_living");
    public static  SoundEvent DILOPHOSAURUS_SPIT = create("dilophosaurus_spit");
    public static  SoundEvent DILOPHOSAURUS_HURT = create("dilophosaurus_hurt");
    public static  SoundEvent DILOPHOSAURUS_DEATH = create("dilophosaurus_death");

    public static  SoundEvent CARNOTAURUS_LIVING = create("carnotaurus_living");
    public static  SoundEvent CARNOTAURUS_HURT = create("carnotaurus_hurt");
    public static  SoundEvent CARNOTAURUS_DEATH = create("carnotaurus_death");

    public static  SoundEvent GALLIMIMUS_LIVING = create("gallimimus_living");
    public static  SoundEvent GALLIMIMUS_DEATH = create("gallimimus_death");
    public static  SoundEvent GALLIMIMUS_HURT = create("gallimimus_hurt");

    public static  SoundEvent ORNITHOMIMUS_LIVING = create("ornithomimus_living");
    public static  SoundEvent ORNITHOMIMUS_DEATH = create("ornithomimus_death");
    public static  SoundEvent ORNITHOMIMUS_HURT = create("ornithomimus_hurt");

    public static  SoundEvent SPINOSAURUS_LIVING = create("spinosaurus_living");
    public static  SoundEvent SPINOSAURUS_HURT = create("spinosaurus_hurt");
    public static  SoundEvent SPINOSAURUS_DEATH = create("spinosaurus_death");
    public static  SoundEvent SPINOSAURUS_CALL = create("spinosaurus_call");
    public static  SoundEvent SPINOSAURUS_ROAR = create("spinosaurus_roar");
    public static  SoundEvent SPINOSAURUS_BREATHING = create("spinosaurus_breathing");
    public static  SoundEvent SPINOSAURUS_THREAT = create("spinosaurus_threat");

    public static  SoundEvent SMILODON_LIVING = create("smilodon_living");
    public static  SoundEvent SMILODON_HURT = create("smilodon_hurt");
    public static  SoundEvent SMILODON_DEATH = create("smilodon_death");
    public static  SoundEvent SMILODON_ROAR = create("smilodon_roar");
    public static  SoundEvent SMILODON_BREATHING = create("smilodon_breathing");


    public static  SoundEvent STYRACOSAURUS_LIVING = create("styracosaurus_living");
    public static  SoundEvent STYRACOSAURUS_HURT = create("styracosaurus_hurt");
    public static  SoundEvent STYRACOSAURUS_DEATH = create("styracosaurus_death");
    public static  SoundEvent STYRACOSAURUS_CALL = create("styracosaurus_call");
    public static  SoundEvent STYRACOSAURUS_ROAR = create("styracosaurus_roar");
    public static  SoundEvent STYRACOSAURUS_THREAT = create("styracosaurus_threat");

    public static  SoundEvent TROPEOGNATHUS_LIVING = create("tropeognathus_living");
    public static  SoundEvent TROPEOGNATHUS_HURT = create("tropeognathus_hurt");
    public static  SoundEvent TROPEOGNATHUS_DEATH = create("tropeognathus_death");
    public static  SoundEvent TROPEOGNATHUS_ROAR = create("tropeognathus_roar");
    public static  SoundEvent TROPEOGNATHUS_THREAT = create("tropeognathus_threat");

    public static  SoundEvent ZHENYUANOPTERUS_LIVING = create("zhenyuanopterus_living");
    public static  SoundEvent ZHENYUANOPTERUS_HURT = create("zhenyuanopterus_hurt");
    public static  SoundEvent ZHENYUANOPTERUS_DEATH = create("zhenyuanopterus_death");
    public static  SoundEvent ZHENYUANOPTERUS_ROAR = create("zhenyuanopterus_roar");
    public static  SoundEvent ZHENYUANOPTERUS_THREAT = create("zhenyuanopterus_threat");

    public static  SoundEvent MICROCERATUS_LIVING = create("microceratus_living");
    public static  SoundEvent MICROCERATUS_HURT = create("microceratus_hurt");
    public static  SoundEvent MICROCERATUS_DEATH = create("microceratus_death");
    public static  SoundEvent MICROCERATUS_THREAT = create("microceratus_threat");

    public static  SoundEvent METRIACANTHOSAURUS_LIVING = create("metriacanthosaurus_living");
    public static  SoundEvent METRIACANTHOSAURUS_HURT = create("metriacanthosaurus_hurt");
    public static  SoundEvent METRIACANTHOSAURUS_DEATH = create("metriacanthosaurus_death");
    public static  SoundEvent METRIACANTHOSAURUS_CALL = create("metriacanthosaurus_call");
    public static  SoundEvent METRIACANTHOSAURUS_ROAR = create("metriacanthosaurus_roar");
    public static  SoundEvent METRIACANTHOSAURUS_THREAT = create("metriacanthosaurus_threat");

    public static  SoundEvent MAJUNGASAURUS_LIVING = create("majungasaurus_living");
    public static  SoundEvent MAJUNGASAURUS_HURT = create("majungasaurus_hurt");
    public static  SoundEvent MAJUNGASAURUS_DEATH = create("majungasaurus_death");
    public static  SoundEvent MAJUNGASAURUS_CALL = create("majungasaurus_call");
    public static  SoundEvent MAJUNGASAURUS_ROAR = create("majungasaurus_roar");
    public static  SoundEvent MAJUNGASAURUS_BREATHING = create("majungasaurus_breathing");
    public static  SoundEvent MAJUNGASAURUS_THREAT = create("majungasaurus_threat");

    public static  SoundEvent LAMBEOSAURUS_LIVING = create("lambeosaurus_living");
    public static  SoundEvent LAMBEOSAURUS_HURT = create("lambeosaurus_hurt");
    public static  SoundEvent LAMBEOSAURUS_DEATH = create("lambeosaurus_death");
    public static  SoundEvent LAMBEOSAURUS_CALL = create("lambeosaurus_call");
    public static  SoundEvent LAMBEOSAURUS_THREAT = create("lambeosaurus_threat");

    public static  SoundEvent LUDODACTYLUS_LIVING = create("ludodactylus_living");
    public static  SoundEvent LUDODACTYLUS_HURT = create("ludodactylus_hurt");
    public static  SoundEvent LUDODACTYLUS_DEATH = create("ludodactylus_death");
    public static  SoundEvent LUDODACTYLUS_CALL = create("ludodactylus_call");
    public static  SoundEvent LUDODACTYLUS_THREAT = create("ludodactylus_threat");

    public static  SoundEvent LEAELLYNASAURA_LIVING = create("leaellynasaura_living");
    public static  SoundEvent LEAELLYNASAURA_HURT = create("leaellynasaura_hurt");
    public static  SoundEvent LEAELLYNASAURA_DEATH = create("leaellynasaura_death");

    public static  SoundEvent LEPTICTIDIUM_LIVING = create("leptictidium_living");
    public static  SoundEvent LEPTICTIDIUM_HURT = create("leptictidium_hurt");
    public static  SoundEvent LEPTICTIDIUM_DEATH = create("leptictidium_death");

    public static  SoundEvent GIGANOTOSAURUS_LIVING = create("giganotosaurus_living");
    public static  SoundEvent GIGANOTOSAURUS_HURT = create("giganotosaurus_hurt");
    public static  SoundEvent GIGANOTOSAURUS_DEATH = create("giganotosaurus_death");
    public static  SoundEvent GIGANOTOSAURUS_CALL = create("giganotosaurus_call");
    public static  SoundEvent GIGANOTOSAURUS_ROAR = create("giganotosaurus_roar");
    public static  SoundEvent GIGANOTOSAURUS_THREAT = create("giganotosaurus_threat");

    public static  SoundEvent HERRERASAURUS_LIVING = create("herrerasaurus_living");
    public static  SoundEvent HERRERASAURUS_HURT = create("herrerasaurus_hurt");
    public static  SoundEvent HERRERASAURUS_DEATH = create("herrerasaurus_death");
    public static  SoundEvent HERRERASAURUS_CALL = create("herrerasaurus_call");
    public static  SoundEvent HERRERASAURUS_ROAR = create("herrerasaurus_roar");
    public static  SoundEvent HERRERASAURUS_THREAT = create("herrerasaurus_threat");

    public static  SoundEvent EDMONTOSAURUS_LIVING = create("edmontosaurus_living");
    public static  SoundEvent EDMONTOSAURUS_HURT = create("edmontosaurus_hurt");
    public static  SoundEvent EDMONTOSAURUS_DEATH = create("edmontosaurus_death");
    public static  SoundEvent EDMONTOSAURUS_CALL = create("edmontosaurus_call");
    public static  SoundEvent EDMONTOSAURUS_THREAT = create("edmontosaurus_threat");

    public static  SoundEvent DUNKLEOSTEUS_LIVING = create("dunkleosteus_living");
    public static  SoundEvent DUNKLEOSTEUS_HURT = create("dunkleosteus_hurt");
    public static  SoundEvent DUNKLEOSTEUS_DEATH = create("dunkleosteus_death");
    public static  SoundEvent DUNKLEOSTEUS_THREAT = create("dunkleosteus_threat");

    public static  SoundEvent THERIZINOSAURUS_LIVING = create("therizinosaurus_living");
    public static  SoundEvent THERIZINOSAURUS_HURT = create("therizinosaurus_hurt");
    public static  SoundEvent THERIZINOSAURUS_DEATH = create("therizinosaurus_death");
    public static  SoundEvent THERIZINOSAURUS_ROAR = create("therizinosaurus_roar");
    public static  SoundEvent THERIZINOSAURUS_THREAT = create("therizinosaurus_threat");

    public static  SoundEvent BARYONYX_LIVING = create("baryonyx_living");
    public static  SoundEvent BARYONYX_HURT = create("baryonyx_hurt");
    public static  SoundEvent BARYONYX_DEATH = create("baryonyx_death");
    public static  SoundEvent BARYONYX_CALL = create("baryonyx_call");
    public static  SoundEvent BARYONYX_ROAR = create("baryonyx_roar");
    public static  SoundEvent BARYONYX_THREAT = create("baryonyx_threat");

    public static  SoundEvent DIMORPHODON_LIVING = create("dimorphodon_living");
    public static  SoundEvent DIMORPHODON_HURT = create("dimorphodon_hurt");
    public static  SoundEvent DIMORPHODON_DEATH = create("dimorphodon_death");
    public static  SoundEvent DIMORPHODON_CALL = create("dimorphodon_call");
    public static  SoundEvent DIMORPHODON_THREAT = create("dimorphodon_threat");

    public static  SoundEvent COMPSOGNATHUS_LIVING = create("compsognathus_living");
    public static  SoundEvent COMPSOGNATHUS_HURT = create("compsognathus_hurt");
    public static  SoundEvent COMPSOGNATHUS_DEATH = create("compsognathus_death");
    public static  SoundEvent COMPSOGNATHUS_CALL = create("compsognathus_call");
    public static  SoundEvent COMPSOGNATHUS_THREAT = create("compsognathus_threat");

    public static  SoundEvent CORYTHOSAURUS_LIVING = create("corythosaurus_living");
    public static  SoundEvent CORYTHOSAURUS_HURT = create("corythosaurus_hurt");
    public static  SoundEvent CORYTHOSAURUS_DEATH = create("corythosaurus_death");
    public static  SoundEvent CORYTHOSAURUS_THREAT = create("corythosaurus_threat");
    public static  SoundEvent CORYTHOSAURUS_CALL = create("corythosaurus_call");

    public static  SoundEvent CHASMOSAURUS_LIVING = create("chasmosaurus_living");
    public static  SoundEvent CHASMOSAURUS_HURT = create("chasmosaurus_hurt");
    public static  SoundEvent CHASMOSAURUS_DEATH = create("chasmosaurus_death");
    public static  SoundEvent CHASMOSAURUS_THREAT = create("chasmosaurus_threat");

    public static  SoundEvent APATOSAURUS_LIVING = create("apatosaurus_living");
    public static  SoundEvent APATOSAURUS_HURT = create("apatosaurus_hurt");
    public static  SoundEvent APATOSAURUS_DEATH = create("apatosaurus_death");
    public static  SoundEvent APATOSAURUS_CALL = create("apatosaurus_call");
    public static  SoundEvent APATOSAURUS_THREAT = create("apatosaurus_threat");

    public static  SoundEvent CHILESAURUS_LIVING = create("chilesaurus_living");
    public static  SoundEvent CHILESAURUS_HURT = create("chilesaurus_hurt");
    public static  SoundEvent CHILESAURUS_DEATH = create("chilesaurus_death");

    public static  SoundEvent CEARADACTYLUS_LIVING = create("cearadactylus_living");
    public static  SoundEvent CEARADACTYLUS_HURT = create("cearadactylus_hurt");
    public static  SoundEvent CEARADACTYLUS_DEATH = create("cearadactylus_death");
    public static  SoundEvent CEARADACTYLUS_CALL = create("cearadactylus_call");
    public static  SoundEvent CEARADACTYLUS_THREAT = create("cearadactylus_threat");

    public static  SoundEvent PACHYCEPHALOSAURUS_LIVING = create("pachycephalosaurus_living");
    public static  SoundEvent PACHYCEPHALOSAURUS_HURT = create("pachycephalosaurus_hurt");
    public static  SoundEvent PACHYCEPHALOSAURUS_DEATH = create("pachycephalosaurus_death");
    public static  SoundEvent PACHYCEPHALOSAURUS_CALL = create("pachycephalosaurus_call");
    public static  SoundEvent PACHYCEPHALOSAURUS_THREAT = create("pachycephalosaurus_threat");

    public static  SoundEvent PROTOCERATOPS_LIVING = create("protoceratops_living");
    public static  SoundEvent PROTOCERATOPS_HURT = create("protoceratops_hurt");
    public static  SoundEvent PROTOCERATOPS_DEATH = create("protoceratops_death");
    public static  SoundEvent PROTOCERATOPS_THREAT = create("protoceratops_threat");

    public static  SoundEvent MOGANOPTERUS_LIVING = create("moganopterus_living");
    public static  SoundEvent MOGANOPTERUS_HURT = create("moganopterus_hurt");
    public static  SoundEvent MOGANOPTERUS_DEATH = create("moganopterus_death");
    public static  SoundEvent MOGANOPTERUS_THREAT = create("moganopterus_threat");

    public static  SoundEvent COELURUS_LIVING = create("coelurus_living");
    public static  SoundEvent COELURUS_HURT = create("coelurus_hurt");
    public static  SoundEvent COELURUS_DEATH = create("coelurus_death");
    public static  SoundEvent COELURUS_THREAT = create("coelurus_threat");

    public static  SoundEvent SEGISAURUS_LIVING = create("segisaurus_living");
    public static  SoundEvent SEGISAURUS_HURT = create("segisaurus_hurt");
    public static  SoundEvent SEGISAURUS_DEATH = create("segisaurus_death");
    public static  SoundEvent SEGISAURUS_THREAT = create("segisaurus_threat");

    public static  SoundEvent QUETZALCOATLUS_LIVING = create("quetzalcoatlus_living");
    public static  SoundEvent QUETZALCOATLUS_HURT = create("quetzalcoatlus_hurt");
    public static  SoundEvent QUETZALCOATLUS_DEATH = create("quetzalcoatlus_death");
    public static  SoundEvent QUETZALCOATLUS_THREAT = create("quetzalcoatlus_threat");

    public static  SoundEvent OVIRAPTOR_LIVING = create("oviraptor_living");
    public static  SoundEvent OVIRAPTOR_HURT = create("oviraptor_hurt");
    public static  SoundEvent OVIRAPTOR_DEATH = create("oviraptor_death");
    public static  SoundEvent OVIRAPTOR_THREAT = create("oviraptor_threat");

    public static  SoundEvent OTHNIELIA_LIVING = create("othnielia_living");
    public static  SoundEvent OTHNIELIA_HURT = create("othnielia_hurt");
    public static  SoundEvent OTHNIELIA_DEATH = create("othnielia_death");

    public static  SoundEvent CERATOSAURUS_LIVING = create("ceratosaurus_living");
    public static  SoundEvent CERATOSAURUS_HURT = create("ceratosaurus_hurt");
    public static  SoundEvent CERATOSAURUS_DEATH = create("ceratosaurus_death");
    public static  SoundEvent CERATOSAURUS_CALL = create("ceratosaurus_call");
    public static  SoundEvent CERATOSAURUS_ROAR = create("ceratosaurus_roar");
    public static  SoundEvent CERATOSAURUS_BREATHING = create("ceratosaurus_breathing");
    public static  SoundEvent CERATOSAURUS_THREAT = create("ceratosaurus_threat");

    public static  SoundEvent ALVAREZSAURUS_LIVING = create("alvarezsaurus_living");
    public static  SoundEvent ALVAREZSAURUS_HURT = create("alvarezsaurus_hurt");
    public static  SoundEvent ALVAREZSAURUS_DEATH = create("alvarezsaurus_death");
    public static  SoundEvent ALVAREZSAURUS_CALL = create("alvarezsaurus_call");
    public static  SoundEvent ALVAREZSAURUS_THREAT = create("alvarezsaurus_threat");

    public static  SoundEvent TROODON_LIVING = create("troodon_living");
    public static  SoundEvent TROODON_HURT = create("troodon_hurt");
    public static  SoundEvent TROODON_DEATH = create("troodon_death");
    public static  SoundEvent TROODON_CALL = create("troodon_call");
    public static  SoundEvent TROODON_THREAT = create("troodon_threat");

    public static  SoundEvent TITANIS_LIVING = create("titanis_living");
    public static  SoundEvent TITANIS_HURT = create("titanis_hurt");
    public static  SoundEvent TITANIS_DEATH = create("titanis_death");
    public static  SoundEvent TITANIS_THREAT = create("titanis_threat");

    public static  SoundEvent PTERANODON_LIVING = create("pteranodon_living");
    public static  SoundEvent PTERANODON_HURT = create("pteranodon_hurt");
    public static  SoundEvent PTERANODON_DEATH = create("pteranodon_death");
    public static  SoundEvent PTERANODON_CALL = create("pteranodon_call");

    public static  SoundEvent HYAENODON_LIVING = create("hyaenodon_living");
    public static  SoundEvent HYAENODON_HURT = create("hyaenodon_hurt");
    public static  SoundEvent HYAENODON_DEATH = create("hyaenodon_death");
    public static  SoundEvent HYAENODON_CALL = create("hyaenodon_call");

    public static  SoundEvent INDOMINUS_LIVING = create("indominus_living");
    public static  SoundEvent INDOMINUS_DEATH = create("indominus_death");
    public static  SoundEvent INDOMINUS_HURT = create("indominus_hurt");
    public static  SoundEvent INDOMINUS_ROAR = create("indominus_roar");
    public static  SoundEvent INDOMINUS_BREATHING = create("indominus_breathing");

    public static  SoundEvent RAPHUSREX_LIVING = create("raphusrex_living");
    public static  SoundEvent RAPHUSREX_DEATH = create("raphusrex_death");
    public static  SoundEvent RAPHUSREX_HURT = create("raphusrex_hurt");
    public static  SoundEvent RAPHUSREX_ROAR = create("raphusrex_roar");
    public static  SoundEvent RAPHUSREX_BREATHING = create("raphusrex_breathing");

    public static  SoundEvent TYRANNOSAURUS_BREATHING = create("tyrannosaurus_breathing");
    public static  SoundEvent TYRANNOSAURUS_DEATH = create("tyrannosaurus_death");
    public static  SoundEvent TYRANNOSAURUS_HURT = create("tyrannosaurus_hurt");
    public static  SoundEvent TYRANNOSAURUS_ROAR = create("tyrannosaurus_roar");
    public static  SoundEvent TYRANNOSAURUS_LIVING = create("tyrannosaurus_living");

    public static  SoundEvent VELOCIRAPTOR_LIVING = create("velociraptor_living");
    public static  SoundEvent VELOCIRAPTOR_HURT = create("velociraptor_hurt");
    public static  SoundEvent VELOCIRAPTOR_BREATHING = create("velociraptor_breathing");
    public static  SoundEvent VELOCIRAPTOR_CALL = create("velociraptor_call");
    public static  SoundEvent VELOCIRAPTOR_DEATH = create("velociraptor_death");
    public static  SoundEvent VELOCIRAPTOR_ATTACK = create("velociraptor_attack");

    public static  SoundEvent ACHILLOBATOR_LIVING = create("achillobator_living");
    public static  SoundEvent ACHILLOBATOR_HURT = create("achillobator_hurt");
    public static  SoundEvent ACHILLOBATOR_MATE_CALL = create("achillobator_mate_call");
    public static  SoundEvent ACHILLOBATOR_CALL = create("achillobator_call");
    public static  SoundEvent ACHILLOBATOR_DEATH = create("achillobator_death");
    public static  SoundEvent ACHILLOBATOR_ATTACK = create("achillobator_attack");

    public static  SoundEvent SPINORAPTOR_LIVING = create("spinoraptor_living");
    public static  SoundEvent SPINORAPTOR_HURT = create("spinoraptor_hurt");
    public static  SoundEvent SPINORAPTOR_BREATHING = create("spinoraptor_breathing");
    public static  SoundEvent SPINORAPTOR_CALL = create("spinoraptor_call");
    public static  SoundEvent SPINORAPTOR_DEATH = create("spinoraptor_death");
    public static  SoundEvent SPINORAPTOR_ATTACK = create("spinoraptor_attack");


    public static  SoundEvent ANKYLOSAURUS_LIVING = create("ankylosaurus_living");
    public static  SoundEvent ANKYLOSAURUS_HURT = create("ankylosaurus_hurt");
    public static  SoundEvent ANKYLOSAURUS_MATE_CALL = create("ankylosaurus_mate_call");
    public static  SoundEvent ANKYLOSAURUS_CALL = create("ankylosaurus_call");
    public static  SoundEvent ANKYLOSAURUS_DEATH = create("ankylosaurus_death");
    public static  SoundEvent ANKYLOSAURUS_ATTACK = create("ankylosaurus_attack");

    public static  SoundEvent MICRORAPTOR_LIVING = create("microraptor_living");
    public static  SoundEvent MICRORAPTOR_HURT = create("microraptor_hurt");
    public static  SoundEvent MICRORAPTOR_DEATH = create("microraptor_death");
    public static  SoundEvent MICRORAPTOR_ATTACK = create("microraptor_attack");

    public static  SoundEvent MUSSAURUS_LIVING = create("mussaurus_living");
    public static  SoundEvent MUSSAURUS_HURT = create("mussaurus_hurt");
    public static  SoundEvent MUSSAURUS_DEATH = create("mussaurus_death");
    public static  SoundEvent MUSSAURUS_ATTACK = create("mussaurus_attack");
    public static  SoundEvent MUSSAURUS_MATE_CALL = create("mussaurus_mate_call");

    public static  SoundEvent MOSASAURUS_LIVING = create("mosasaurus_living");
    public static  SoundEvent MOSASAURUS_HURT = create("mosasaurus_hurt");
    public static  SoundEvent MOSASAURUS_DEATH = create("mosasaurus_death");
    public static  SoundEvent MOSASAURUS_MATE_CALL = create("mosasaurus_mate_call");
    public static  SoundEvent MOSASAURUS_ATTACK = create("mosasaurus_attack");
    public static  SoundEvent MOSASAURUS_ROAR = create("mosasaurus_roar");

    public static  SoundEvent ALLOSAURUS_DEATH = create("allosaurus_death");
    public static  SoundEvent ALLOSAURUS_FIGHT_FEMALE = create("allosaurus_fight_female");
    public static  SoundEvent ALLOSAURUS_HURT = create("allosaurus_hurt");
    public static  SoundEvent ALLOSAURUS_LIVING = create("allosaurus_living");
    public static  SoundEvent ALLOSAURUS_MATE_CALL = create("allosaurus_mate_call");
    public static  SoundEvent ALLOSAURUS_ROAR = create("allosaurus_roar");
    public static  SoundEvent ALLOSAURUS_THREAT = create("allosaurus_threat");

    public static  SoundEvent BEELZEBUFO_CROAK = create("beelzebufo_croak");
    public static  SoundEvent BEELZEBUFO_HURT = create("beelzebufo_hurt");
    public static  SoundEvent BEELZEBUFO_HURT_THREAT = create("beelzebufo_hurt_threat");
    public static  SoundEvent BEELZEBUFO_LONG_DISTANCE_CROAK = create("beelzebufo_long_distance_croak");
    public static  SoundEvent BEELZEBUFO_THREAT = create("beelzebufo_threat");

    public static  SoundEvent CARCHARODONTOSAURUS_GROWL = create("carcharodontosaurus_growl");
    public static  SoundEvent CARCHARODONTOSAURUS_HISS = create("carcharodontosaurus_hiss");
    public static  SoundEvent CARCHARODONTOSAURUS_HURT = create("carcharodontosaurus_hurt");
    public static  SoundEvent CARCHARODONTOSAURUS_LIVING = create("carcharodontosaurus_living");
    public static  SoundEvent CARCHARODONTOSAURUS_ROAR = create("carcharodontosaurus_roar");

    public static  SoundEvent GUANLONG_DEATH = create("guanlong_death");
    public static  SoundEvent GUANLONG_HURT = create("guanlong_hurt");
    public static  SoundEvent GUANLONG_LIVING = create("guanlong_living");

    public static  SoundEvent PROCERATOSAURUS_ATTACK = create("proceratosaurus_attack");
    public static  SoundEvent PROCERATOSAURUS_DEATH = create("proceratosaurus_death");
    public static  SoundEvent PROCERATOSAURUS_HURT = create("proceratosaurus_hurt");
    public static  SoundEvent PROCERATOSAURUS_LIVING = create("proceratosaurus_living");
    public static  SoundEvent PROCERATOSAURUS_MATE_CALL = create("proceratosaurus_mate_call");
    public static  SoundEvent PROCERATOSAURUS_THREAT = create("proceratosaurus_threat");

    public static  SoundEvent SUCHOMIMUS_DEATH = create("suchomimus_death");
    public static  SoundEvent SUCHOMIMUS_HURT = create("suchomimus_hurt");
    public static  SoundEvent SUCHOMIMUS_LIVING = create("suchomimus_living");
    public static  SoundEvent SUCHOMIMUS_MATE_CALL = create("suchomimus_mate_call");
    public static  SoundEvent SUCHOMIMUS_ROAR = create("suchomimus_roar");

    public static  SoundEvent MAMMOTH_ALARM_CALL = create("mammoth_alarm_call");
    public static  SoundEvent MAMMOTH_DEATH = create("mammoth_death");
    public static  SoundEvent MAMMOTH_HURT = create("mammoth_hurt");
    public static  SoundEvent MAMMOTH_LIVING = create("mammoth_living");
    public static  SoundEvent MAMMOTH_MATE_CALL = create("mammoth_mate_call");
    public static  SoundEvent MAMMOTH_THREAT = create("mammoth_threat");

    public static  SoundEvent ELASMOTHERIUM_DEATH = create("elasmotherium_death");
    public static  SoundEvent ELASMOTHERIUM_HURT = create("elasmotherium_hurt");
    public static  SoundEvent ELASMOTHERIUM_LIVING = create("elasmotherium_living");
    public static  SoundEvent ELASMOTHERIUM_THREAT = create("elasmotherium_threat");

    public static  SoundEvent DEINOTHERIUM_ALARM_CALL = create("deinotherium_alarm_call");
    public static  SoundEvent DEINOTHERIUM_DEATH = create("deinotherium_death");
    public static  SoundEvent DEINOTHERIUM_HURT = create("deinotherium_hurt");
    public static  SoundEvent DEINOTHERIUM_LIVING = create("deinotherium_living");
    public static  SoundEvent DEINOTHERIUM_MATE_CALL = create("deinotherium_mate_call");
    public static  SoundEvent DEINOTHERIUM_THREAT = create("deinotherium_threat");

    public static  SoundEvent ARSINOITHERIUM_DEATH = create("arsinoitherium_death");
    public static  SoundEvent ARSINOITHERIUM_HURT = create("arsinoitherium_hurt");
    public static  SoundEvent ARSINOITHERIUM_LIVING = create("arsinoitherium_living");
    public static  SoundEvent ARSINOITHERIUM_THREAT = create("arsinoitherium_threat");

    public static  SoundEvent MEGATHERIUM_DEATH = create("megatherium_death");
    public static  SoundEvent MEGATHERIUM_HURT = create("megatherium_hurt");
    public static  SoundEvent MEGATHERIUM_LIVING = create("megatherium_living");
    public static  SoundEvent MEGATHERIUM_THREAT = create("megatherium_threat");

    public static  SoundEvent POSTOSUCHUS_ATTACK = create("postosuchus_attack");
    public static  SoundEvent POSTOSUCHUS_CALL = create("postosuchus_call");
    public static  SoundEvent POSTOSUCHUS_DEATH = create("postosuchus_death");

    public static  SoundEvent GOAT_LIVING = create("goat_living");
    public static  SoundEvent GOAT_HURT = create("goat_hurt");
    public static  SoundEvent GOAT_DEATH = create("goat_death");

    public static  SoundEvent FENCE_SHOCK = create("fence_shock");

    public static  SoundEvent FIRE = create("fire");
    public static  SoundEvent EMPTY = create("empty");
    public static  SoundEvent RELOAD = create("reload");

    private static List<SoundEvent> sounds = Lists.newArrayList();
    public static List<RegistryObject<SoundEvent>> toRegister;


    public static SoundEvent create(String soundName) {
        ResourceLocation soundLoc = new ResourceLocation(Jurassicworldreborn.modid, soundName);
        SoundEvent sound = new SoundEvent(soundLoc);
//        RegistryHandler.registerSound(sound, soundName);
        sounds.add(sound);
        toRegister.add(registrer.register(soundName, () -> sound));

        
        return sound;
    }

    public static List<SoundEvent> getSounds()
    {
        return sounds;
    }
}