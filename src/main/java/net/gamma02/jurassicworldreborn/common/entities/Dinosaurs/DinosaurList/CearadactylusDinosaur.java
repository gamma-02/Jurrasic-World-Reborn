package net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurList;


import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.*;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Diet;
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

public class CearadactylusDinosaur extends Dinosaur
{
    public static final double SPEED = 0.4F;
    public CearadactylusDinosaur()
    {
        super();

        this.setName("Cearadactylus");
        this.setDinosaurType(DinosaurType.AGGRESSIVE);
        this.setDinosaurClass(CearadactylusEntity.class);
        this.setTimePeriod(TimePeriod.CRETACEOUS);
        this.setEggColorMale(0x64A0B3, 0x3B3937);
        this.setEggColorFemale(0xB55252, 0x4C423A);
        this.setHealth(10, 20);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setStrength(1, 10);
        this.setMaximumAge(fromDays(50));
        this.setEyeHeight(0.45F, 1.45F);
        this.setSizeX(0.15F, 1.5F);
        this.setSizeY(0.2F, 1.55F);
        this.setDiet((Diet.CARNIVORE.get()));
        this.setBones("leg_bones", "pelvis", "skull", "neck_vertebrae", "tail_vertebrae", "teeth", "wing_bones", "ribcage");
        this.setHeadCubeName("Head");
        this.setScale(1.0F, 0.1F);
        this.setAvianAnimal(true);
        this.setBreeding(false, 2, 6, 80, false, true);
        this.shouldDefendOffspring();
        String[][] recipe = {
                {"", "", "","neck_vertebrae","skull"},
                {"tail_vertebrae", "pelvis", "ribcage","","teeth"},
                {"", "leg_bones", "", "wing_bones", ""}};
        this.setRecipe(recipe);
        this.enableSkeleton();
        ArrayList<ResourceKey<Biome>> biomeList = new ArrayList<>();
//        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.SAVANNA));
//        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.JUNGLE));
//        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.MOUNTAIN));
//        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS));
//        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST));
//        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.MESA));
        Stream<TagKey<Biome>> tags = Arrays.stream(new TagKey[]{BiomeTags.IS_SAVANNA, BiomeTags.IS_JUNGLE, BiomeTags.IS_MOUNTAIN, Tags.Biomes.IS_PLAINS, BiomeTags.IS_FOREST, BiomeTags.IS_BADLANDS});
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
