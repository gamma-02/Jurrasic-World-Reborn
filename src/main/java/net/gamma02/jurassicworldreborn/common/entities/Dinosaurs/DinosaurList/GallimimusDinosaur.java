package net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurList;

import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.*;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Diet;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.FoodType;
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

public class GallimimusDinosaur extends Dinosaur {
    public static final double SPEED = 0.41F;
    public GallimimusDinosaur() {
        super();

        this.setName("Gallimimus");
        this.setDinosaurClass(GallimimusEntity.class);
        this.setDinosaurType(DinosaurType.SCARED);
        this.setTimePeriod(TimePeriod.CRETACEOUS);
        this.setEggColorMale(0xD5BA86, 0xD16918);
        this.setEggColorFemale(0xCCBA94, 0xAB733D);
        this.setHealth(6, 30);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setStrength(1, 5);
        this.setMaximumAge(this.fromDays(35));
        this.setEyeHeight(0.58F, 2.7F);
        this.setSizeX(0.2F, 1.2F);
        this.setSizeY(0.35F, 2.45F);
        this.setStorage(27);
        this.setDiet(Diet.HERBIVORE.get().withModule(new Diet.DietModule(FoodType.INSECT).withCondition(entity -> entity.getAgePercentage() < 25)));
        this.setBones("arm_bones", "foot_bones", "leg_bones", "neck_vertebrae", "pelvis", "ribcage", "shoulder", "skull", "tail_vertebrae");
        this.setHeadCubeName("Head Base");
        this.setScale(1.05F, 0.1F);
        this.setImprintable(true);
        this.setFlee(true);
        this.setFlockSpeed(1.35F);
        this.setBreeding(false, 2, 6, 20, false, true);
        this.setJumpHeight(3);
        String[][] recipe =
                {{"", "", "", "neck_vertebrae", "skull"},
                {"tail_vertebrae", "pelvis", "ribcage","shoulder",""},
                {"", "leg_bones", "leg_bones", "arm_bones", ""},
                {"", "foot_bones", "foot_bones", "", ""}};
        this.setRecipe(recipe);
        
        ArrayList<ResourceKey<Biome>> biomeList = new ArrayList<>();
//        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS));
//        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.DRY));
        Stream<TagKey<Biome>> tags = Arrays.stream(new TagKey[]{Tags.Biomes.IS_PLAINS, Tags.Biomes.IS_DRY_OVERWORLD});
        ArrayList<Biome> allBiomes = new ArrayList<>(ForgeRegistries.BIOMES.getValues());

        for (Biome biome:
                allBiomes) {
            var key = ForgeRegistries.BIOMES.getResourceKey(biome);
            if(key.isPresent() && tags.anyMatch((tag) -> ForgeRegistries.BIOMES.tags().getTag(tag).contains(biome))){
                biomeList.add(key.get());
            }
        }
        this.setSpawn(1, biomeList);
        this.enableSkeleton();
    }
}
