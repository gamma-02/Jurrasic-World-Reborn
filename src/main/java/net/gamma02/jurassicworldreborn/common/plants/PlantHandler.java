package net.gamma02.jurassicworldreborn.common.plants;

import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.minecraft.resources.ResourceLocation;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class PlantHandler {
    public static final Plant AJUGINUCULA_SMITHII = new AjuginuculaSmithiiPlant();
    public static final Plant SMALL_ROYAL_FERN = new SmallRoyalFernPlant();
    public static final Plant CALAMITES = new CalamitesPlant();
    public static final Plant SMALL_CHAIN_FERN = new SmallChainFernPlant();
    public static final Plant SMALL_CYCAD = new SmallCycadPlant();
    public static final Plant GINKGO = new GinkgoPlant();
    public static final Plant CYCADEOIDEA = new BennettitaleanCycadeoideaPlant();
    public static final Plant CRY_PANSY = new CryPansyPlant();
    public static final Plant SCALY_TREE_FERN = new ScalyTreeFernPlant();
    public static final Plant ZAMITES = new ZamitesPlant();
    public static final Plant DICKSONIA = new DicksoniaPlant();
    public static final Plant WILD_ONION = new WildOnionPlant();
    public static final Plant DICROIDIUM_ZUBERI = new DicroidiumZuberiPlant();
    public static final Plant DICTYOPHYLLUM = new DictyophyllumPlant();
    public static final Plant WEST_INDIAN_LILAC = new WestIndianLilacPlant();
    public static final Plant SERENNA_VERIFORMANS = new SerennaVeriformansPlant();
    public static final Plant LADINIA_SIMPLEX = new LadiniaSimplexPlant();
    public static final Plant ORONTIUM_MACKII = new OrontiumMackiiPlant();
    public static final Plant UMALTOLEPIS = new UmaltolepisPlant();
    public static final Plant LIRIODENDRITES = new LiriodendritesPlant();
    public static final Plant RAPHAELIA = new RaphaeliaPlant();
    public static final Plant ENCEPHALARTOS = new EncephalartosPlant();
    public static final Plant PSARONIUS = new PsaroniusPlant();

    public static final Plant PHOENIX = new PhoenixPlant();
    public static final Plant WILD_POTATO = new WildPotatoPlant();
    public static final Plant ARAUCARIA = new AraucariaPlant();
    public static final Plant BRISTLE_FERN = new BristleFernPlant();
    public static final Plant CINNAMON_FERN = new CinnamonFernPlant();
    public static final Plant TEMPSKYA = new TempskyaPlant();
    public static final Plant WOOLLY_STALKED_BEGONIA = new WoollyStalkedBegoniaPlant();
    public static final Plant LARGESTIPULE_LEATHER_ROOT = new LargestipuleLeatherRootPlant();
    public static final Plant RHACOPHYTON = new RhacophytonPlant();
    public static final Plant GRAMINIDITES_BAMBUSOIDES = new GraminiditesBambusoidesPlant();
    public static final Plant ENALLHELIA = new EnallheliaPlant();
    public static final Plant AULOPORA = new AuloporaPlant();
    public static final Plant CLADOCHONUS = new CladochonusPlant();
    public static final Plant LITHOSTROTION = new LithostrotionPlant();
    public static final Plant STYLOPHYLLOPSIS = new StylophyllopsisPlant();
    public static final Plant HIPPURITES_RADIOSUS = new HippuritesRadiosusPlant();
    public static final Plant HELICONIA = new HeliconiaPlant();
    public static final Plant RHAMNUS_SALIFOCIFUS = new RhamnusSalifocifusPlant();

    public static final Plant EMPTY = new Plant.EmptyPlant();

//    private static final List<Plant> PLANTS = new LinkedList<>();
    private static final LinkedList<Plant> PLANTS = new LinkedList<>();

    public static final Object2IntLinkedOpenHashMap<ResourceLocation> RESOURCE_LOCATION_MAP = new Object2IntLinkedOpenHashMap<>();
    public static void init() {
        registerPlant(AJUGINUCULA_SMITHII);
        registerPlant(SMALL_ROYAL_FERN);
        registerPlant(CALAMITES);
        registerPlant(SMALL_CHAIN_FERN);
        registerPlant(SMALL_CYCAD);
        registerPlant(GINKGO);
        registerPlant(CYCADEOIDEA);
        registerPlant(CRY_PANSY);
        registerPlant(SCALY_TREE_FERN);
        registerPlant(ZAMITES);
        registerPlant(DICKSONIA);
        registerPlant(WILD_ONION);
        registerPlant(DICROIDIUM_ZUBERI);
        registerPlant(DICTYOPHYLLUM);
        registerPlant(WEST_INDIAN_LILAC);
        registerPlant(SERENNA_VERIFORMANS);
        registerPlant(LADINIA_SIMPLEX);
        registerPlant(ORONTIUM_MACKII);
        registerPlant(UMALTOLEPIS);
        registerPlant(LIRIODENDRITES);
        registerPlant(RAPHAELIA);
        registerPlant(ENCEPHALARTOS);
        registerPlant(PSARONIUS);
        registerPlant(PHOENIX);
        registerPlant(WILD_POTATO);
        registerPlant(ARAUCARIA);
        registerPlant(CINNAMON_FERN);
        registerPlant(BRISTLE_FERN);
        registerPlant(TEMPSKYA);
        registerPlant(WOOLLY_STALKED_BEGONIA);
        registerPlant(LARGESTIPULE_LEATHER_ROOT);
        registerPlant(RHACOPHYTON);
        registerPlant(GRAMINIDITES_BAMBUSOIDES);
        registerPlant(ENALLHELIA);
        registerPlant(AULOPORA);
        registerPlant(CLADOCHONUS);
        registerPlant(LITHOSTROTION);
        registerPlant(STYLOPHYLLOPSIS);
        registerPlant(HIPPURITES_RADIOSUS);
        registerPlant(HELICONIA);
        registerPlant(RHAMNUS_SALIFOCIFUS);
        registerPlant(EMPTY);
    }

    public static Plant getPlantById(int id) {
        if (id >= PLANTS.size() || id < 0) {
            return null;
        }
        Plant plant = PLANTS.get(id);
        if(plant == null){
            return EMPTY;
        }


        return plant;
    }



    public static Plant getPlantById(ResourceLocation plant){
        if(RESOURCE_LOCATION_MAP.containsKey(plant)){
            int id = RESOURCE_LOCATION_MAP.getInt(plant);
            return getPlantById(id);//hand the return to the function that is meant for int ids
        }

        return EMPTY;
    }

    public static ResourceLocation getPlantId(Plant plant) {
        return new ResourceLocation(Jurassicworldreborn.modid, plant.getName().toLowerCase(Locale.ROOT).replace(' ', '_'));
    }

    public static List<Plant> getPlants() {
        return PLANTS;
    }

    public static void registerPlant(Plant plant) {
        if (!PLANTS.contains(plant)) {
            PLANTS.add(plant);
            int id = PLANTS.indexOf(plant);
            RESOURCE_LOCATION_MAP.put(new ResourceLocation(Jurassicworldreborn.modid, plant.getName().toLowerCase(Locale.ROOT).replace(' ', '_')), id);
        }

    }

    public static List<Plant> getPrehistoricPlantsAndTrees() {
        List<Plant> prehistoricPlants = new LinkedList<>();
        for (Plant plant : PLANTS) {
            if (plant.shouldRegister() && plant.isPrehistoric()) {
                prehistoricPlants.add(plant);
            }
        }
        return prehistoricPlants;
    }

    public static List<Plant> getPrehistoricPlants() {
        List<Plant> prehistoricPlants = new LinkedList<>();
        for (Plant plant : PLANTS) {
            if (plant.shouldRegister() && plant.isPrehistoric() && !plant.isTree()) {
                prehistoricPlants.add(plant);
            }
        }
        return prehistoricPlants;
    }
}
