package net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurList;


import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.*;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Diet;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.SleepTime;
import net.gamma02.jurassicworldreborn.common.util.TimePeriod;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

public class TropeognathusDinosaur extends Dinosaur
{
    public static final double SPEED = 0.3F;
    public TropeognathusDinosaur()
    {
        super();

        this.setName("Tropeognathus");
        this.setDinosaurType(DinosaurType.NEUTRAL);
        this.setDinosaurClass(TropeognathusEntity.class);
        this.setTimePeriod(TimePeriod.CRETACEOUS);
        this.setEggColorMale(0x4E646B, 0x483141);
        this.setEggColorFemale(0x5C6C71, 0x4D3E4D);
        this.setHealth(10, 25);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setStrength(5, 20);
        this.setMaximumAge(fromDays(50));
        this.setEyeHeight(0.45F, 1.45F);
        this.setSizeX(0.15F, 1.5F);
        this.setSizeY(0.25F, 1.55F);
        this.setStorage(27);
        this.setDiet((Diet.PCARNIVORE.get()));
        this.setBones("leg_bones", "pelvis", "skull", "ribcage", "tail_vertebrae", "teeth", "wing_bones");
        this.setHeadCubeName("Head");
        this.setScale(1.15F, 0.1F);
        this.setAttackBias(650);
        this.shouldDefendOffspring();
        this.setBreeding(false, 0, 6, 20, false, true);
        this.setImprintable(false);
        this.setAvianAnimal(true);
        this.setStorage(12);
        String[][] recipe = {
                {"tail_vertebrae", "pelvis", "ribcage", "skull"},
                {"", "leg_bones", "wing_bones", "", ""}};
        this.setRecipe(recipe);
        this.enableSkeleton();
        ArrayList<ResourceKey<Biome>> biomeList = new ArrayList<>();
//        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.SAVANNA));
//        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.JUNGLE));
//        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.MOUNTAIN));
//        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS));
//        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST));
//        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.MESA));
        Stream<TagKey<Biome>> tags = Arrays.stream(new TagKey[]{Tags.Biomes.IS_PLAINS, BiomeTags.IS_FOREST, BiomeTags.IS_SAVANNA, BiomeTags.IS_MOUNTAIN, BiomeTags.IS_BADLANDS, BiomeTags.IS_JUNGLE});
        ArrayList<Biome> allBiomes = new ArrayList<>(ForgeRegistries.BIOMES.getValues());

        for (Biome biome:
                allBiomes) {
            var key = ForgeRegistries.BIOMES.getResourceKey(biome);
            if(key.isPresent() && tags.anyMatch((tag) -> ForgeRegistries.BIOMES.tags().getTag(tag).contains(biome))){
                biomeList.add(key.get());
            }
        }
        this.setSpawn(1, biomeList);
    }
}
