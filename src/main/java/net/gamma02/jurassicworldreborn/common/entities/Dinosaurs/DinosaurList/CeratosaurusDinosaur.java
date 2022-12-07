package net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurList;

import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.*;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Diet;
import net.gamma02.jurassicworldreborn.common.util.TimePeriod;
import java.util.ArrayList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class CeratosaurusDinosaur extends Dinosaur {
    public static final double SPEED = 0.45F;
    public CeratosaurusDinosaur(){
        super();

        this.setName("Ceratosaurus");
        this.setDinosaurType(DinosaurType.NEUTRAL);
        this.setDinosaurClass(CeratosaurusEntity.class);
        this.setTimePeriod(TimePeriod.JURASSIC);
        this.setEggColorMale(0x963a3a, 0x000000);
        this.setEggColorFemale(0xcdb79e, 0x8b3545);
        this.setHealth(10, 45);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setStorage(27);
        this.setAttackSpeed(1.7);
        this.setStrength(1, 22);
        this.setMaximumAge(fromDays(45));
        this.setEyeHeight(0.45F, 1.6F);
        this.setSizeX(0.4F, 2.7F);
        this.setSizeY(0.3F, 3.5F);
        this.setDiet(Diet.CARNIVORE.get());
        this.setBones("skull", "tooth", "arm_bones", "claw", "foot_bones", "leg_bones", "neck_vertebrae", "pelvis", "ribcage", "shoulder", "tail_vertebrae");
        this.setHeadCubeName("Head");
        this.setScale(1.35F, 0.1F);
        this.setBreeding(false, 2, 6, 40, false, true);
        this.setImprintable(true);
        this.setMaxHerdSize(7);
        this.shouldDefendOffspring();
        String[][] recipe = {{"", "", "","neck_vertebrae","skull"},
                {"tail_vertebrae", "pelvis", "ribcage","shoulder","tooth"},
                {"", "leg_bones", "", "arm_bones", "claw"},
                {"", "foot_bones", "", "", ""}};
        this.setRecipe(recipe);
        this.enableSkeleton();
        ArrayList<ResourceKey<Biome>> biomeList = new ArrayList<>();
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.SAVANNA));
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS));
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST));
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.JUNGLE));
        this.setSpawn(1, biomeList);
    }
}
