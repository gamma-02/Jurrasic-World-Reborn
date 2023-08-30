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

public class StegosaurusDinosaur extends Dinosaur{
    public static final double SPEED = 0.35F;
    public StegosaurusDinosaur() {
        super();

        this.setName("Stegosaurus");
        this.setDinosaurType(DinosaurType.NEUTRAL);
        this.setDinosaurClass(StegosaurusEntity.class);
        this.setTimePeriod(TimePeriod.JURASSIC);
        this.setEggColorMale(0xBABF83, 0x75964E);
        this.setEggColorFemale(0xC8BC9A, 0x827D54);
        this.setHealth(10, 50);
        this.setStrength(12, 30);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setMaximumAge(fromDays(50));
        this.setEyeHeight(0.26F, 2.2F);
        this.setSizeX(0.2F, 4.0F);
        this.setSizeY(0.3F, 4.8F);
        this.setStorage(36);
        this.setDiet(Diet.HERBIVORE.get());
        this.setBones("front_leg_bones", "hind_leg_bones", "neck", "pelvis", "ribcage", "shoulder", "skull", "tail", "thagomizer", "tooth");
        this.setHeadCubeName("Head");
        this.setScale(2.55F, 0.15F);
        this.setOffset(0.0F, 0.775F, 0.0F);
        this.setAttackBias(350);
        this.shouldDefendOffspring();
        this.setImprintable(true);
        this.setDefendOwner(true);
        this.setBreeding(false, 2, 6, 20, false, true);
        String[][] recipe = {
                {"tail", "pelvis", "ribcage","neck","skull"},
                {"thagomizer", "hind_leg_bones", "hind_leg_bones", "shoulder", "tooth"},
                {"", "", "", "front_leg_bones", "front_leg_bones"}};
        this.setRecipe(recipe);
        this.enableSkeleton();
        ArrayList<ResourceKey<Biome>> biomeList = new ArrayList<>();
//        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.SAVANNA));
//        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS));
//        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST));
//        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.MESA));
//        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.CONIFEROUS));
        Stream<TagKey<Biome>> tags = Arrays.stream(new TagKey[]{BiomeTags.IS_SAVANNA, Tags.Biomes.IS_CONIFEROUS, BiomeTags.IS_MOUNTAIN, Tags.Biomes.IS_PLAINS, BiomeTags.IS_FOREST, BiomeTags.IS_BADLANDS});
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

