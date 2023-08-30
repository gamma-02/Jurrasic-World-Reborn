package net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurList;


import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.*;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Diet;
import net.gamma02.jurassicworldreborn.common.util.TimePeriod;
import java.util.ArrayList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

public class QuetzalDinosaur extends Dinosaur
{
    public static final double SPEED = 0.4F;
    public QuetzalDinosaur()
    {
        super();

        this.setName("Quetzalcoatlus");
        this.setDinosaurType(DinosaurType.AGGRESSIVE);
        this.setDinosaurClass(QuetzalEntity.class);
        this.setTimePeriod(TimePeriod.CRETACEOUS);
        this.setEggColorMale(0x8d8f8b, 0x1c2d40);
        this.setEggColorFemale(0x8d8f8b, 0x2e201a);
        this.setHealth(10, 30);
        this.setStrength(5, 10);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setMaximumAge(fromDays(40));
        this.setEyeHeight(0.45F, 1.6F);
        this.setSizeX(0.6F, 4.5F);
        this.setSizeY(1.2F, 5.0F);
        this.setStorage(27);
        this.setDiet((Diet.CARNIVORE.get()));
        this.setBones("skull", "ribcage", "leg_bones", "neck_vertebrae", "pelvis", "tail_vertebrae", "wing_bones");
        this.setHeadCubeName("Head");
        this.setRotationAngle(50, 10);
        this.setScale(2.4F, 0.15F);
        this.setAttackBias(1200);
        this.shouldDefendOffspring();
        this.setBreeding(false, 1, 4, 80, false, true);
        this.enableSkeleton();
        this.setAvianAnimal(true);
        String[][] recipe = {
                {"", "", "", "neck_vertebrae", "skull"},
                {"tail_vertebrae", "pelvis", "ribcage","",""},
                {"", "leg_bones", "", "wing_bones", ""}};
        this.setRecipe(recipe);
        ArrayList<ResourceKey<Biome>> biomeList = new ArrayList<>();
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
