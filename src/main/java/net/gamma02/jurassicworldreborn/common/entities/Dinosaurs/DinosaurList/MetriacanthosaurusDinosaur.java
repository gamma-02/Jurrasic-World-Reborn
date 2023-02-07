package net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurList;

import java.util.ArrayList;

import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.*;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Diet;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.FoodType;
import net.gamma02.jurassicworldreborn.common.util.TimePeriod;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class MetriacanthosaurusDinosaur extends Dinosaur
{
    public static final double SPEED = 0.4F;
    public MetriacanthosaurusDinosaur()
    {
        super();

        this.setName("Metriacanthosaurus");
        this.setDinosaurType(DinosaurType.AGGRESSIVE);
        this.setDinosaurClass(MetriacanthosaurusEntity.class);
        this.setTimePeriod(TimePeriod.JURASSIC);
        this.setEggColorMale(0xB05E1C, 0xE7DB27);
        this.setEggColorFemale(0xB5985E, 0x60451C);
        this.setHealth(10, 40);
        this.setStrength(5, 20);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setMaximumAge(fromDays(40));
        this.setEyeHeight(0.35F, 1.75F);
        this.setSizeX(0.15F, 1.15F);
        this.setSizeY(0.25F, 1.95F);
        this.setStorage(27);
        this.setDiet((Diet.CARNIVORE.get()));
        this.setBones("arm_bones", "foot_bones", "leg_bones", "neck_vertebrae", "pelvis", "ribcage", "shoulder", "skull", "tail_vertebrae", "tooth");
        this.setHeadCubeName("Head");
        this.setScale(1.2F, 0.15F);
        this.shouldDefendOffspring();
        this.setAttackBias(120);
        this.setBreeding(false, 2, 4, 70, false, true);
        String[][] recipe = {
                {"", "", "","neck_vertebrae","skull"},
                {"tail_vertebrae", "pelvis", "ribcage","shoulder","tooth"},
                {"", "", "leg_bones", "arm_bones", ""},
                {"", "", "foot_bones", "", ""}};
        this.setRecipe(recipe);
        this.enableSkeleton();
        ArrayList<ResourceKey<Biome>> biomeList = new ArrayList<>();
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.SAVANNA));
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST));
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS));
        this.setSpawn(1, biomeList);
    }
}
