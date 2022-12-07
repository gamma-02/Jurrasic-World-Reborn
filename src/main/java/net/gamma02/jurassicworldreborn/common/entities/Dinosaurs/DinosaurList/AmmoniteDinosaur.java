package net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurList;

import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.AmmoniteEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Diet;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.FoodType;
import net.gamma02.jurassicworldreborn.common.util.TimePeriod;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.ArrayList;

public class AmmoniteDinosaur extends Dinosaur {
    public static final double SPEED = 0.3F;
    public AmmoniteDinosaur() {
        super();
        this.setName("Ammonite");
        this.setDinosaurType(DinosaurType.SCARED);
        this.setDinosaurClass(AmmoniteEntity.class);
        this.setTimePeriod(TimePeriod.TRIASSIC);
        this.setEggColorMale(0xE4936B, 0xBC5312);
        this.setEggColorFemale(0xE4936B, 0xB86D1B);
        this.setHealth(4, 26);
        this.setSpeed((SPEED -0.25), SPEED);
        this.setStorage(27);
        this.setStrength(2, 4);
        this.setMaximumAge(fromDays(45));
        this.setMarineAnimal(true);
        this.setEyeHeight(0.45F, 1.6F);
        this.setSizeX(0.3F, 1.4F);
        this.setSizeY(0.5F, 1.8F);
        this.setMarineAnimal(true);
        this.setBirthType(BirthType.LIVE_BIRTH);
        this.setDiet(Diet.PISCIVORE.get().withModule(new Diet.DietModule(FoodType.FILTER)));
        this.setBones("perisphinctes_beak", "shell_cover");
        this.setHeadCubeName("Head");
        this.setScale(1.0F, 0.3F);
        this.setBreeding(true, 2, 10, 20, false, false);
        this.setImprintable(false);
        this.setOffset(0, 0.2f, 0);
        String[][] recipe = {
                { "shell_cover","perisphinctes_beak"}};
        this.setRecipe(recipe);
        this.enableSkeleton();
        ArrayList<ResourceKey<Biome>> biomeList = new ArrayList<ResourceKey<Biome>>();
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.OCEAN));
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.WATER));
        this.setSpawn(1, biomeList);
    }
}
