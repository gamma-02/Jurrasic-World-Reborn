package net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurList;

import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.*;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Diet;
import net.gamma02.jurassicworldreborn.common.util.TimePeriod;
import java.util.ArrayList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;


public class ProceratosaurusDinosaur extends Dinosaur {
    public static final double SPEED = 0.3F;
    public ProceratosaurusDinosaur(){
        super();

        this.setName("Proceratosaurus");
        this.setDinosaurType(DinosaurType.NEUTRAL);
        this.setDinosaurClass(ProceratosaurusEntity.class);
        this.setTimePeriod(TimePeriod.JURASSIC);
        this.setEggColorMale(0xD8D189, 0xB0554A);
        this.setEggColorFemale(0xD8D189, 0x939393);
        this.setHealth(10, 40);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setStorage(27);
        this.setStrength(3, 20);
        this.setMaximumAge(fromDays(45));
        this.setEyeHeight(0.45F, 1.6F);
        this.setSizeX(0.15F, 1.4F);
        this.setSizeY(0.2F, 1.8F);
        this.setDiet(Diet.CARNIVORE.get());
        this.setBones("ribcage", "tooth", "shoulder", "skull", "neck_vertebrae", "leg_bones", "pelvis", "ribcage", "arm_bones", "shoulder", "tail_vertebrae");
        this.setHeadCubeName("Head");
        this.setScale(0.9F, 0.1F);
        this.shouldDefendOffspring();
        this.setMaxHerdSize(7);
        this.setAttackBias(120);
        this.setImprintable(true);
        this.setDefendOwner(true);
        this.setBreeding(false, 2, 6, 20, false, true);
        String[][] recipe = {
                {"", "", "","neck_vertebrae","skull"},
                {"tail_vertebrae", "pelvis", "ribcage","shoulder","tooth"},
                {"", "", "leg_bones", "arm_bones", ""}};
        this.setRecipe(recipe);
        this.enableSkeleton();

        ArrayList<ResourceKey<Biome>> biomeList = new ArrayList<>();
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.CONIFEROUS));
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.SPARSE));
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST));
        this.setSpawn(1, biomeList);
    }
}
