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

public class LambeosaurusDinosaur extends Dinosaur
{
    public static final double SPEED = 0.41F;
    public LambeosaurusDinosaur()
    {
        super();

        this.setName("Lambeosaurus");
        this.setDinosaurType(DinosaurType.PASSIVE);
        this.setDinosaurClass(LambeosaurusEntity.class);
        this.setTimePeriod(TimePeriod.CRETACEOUS);
        this.setEggColorMale(0x82947A, 0x2F3129);
        this.setEggColorFemale(0x898969, 0x464C3A);
        this.setHealth(10, 60);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setStrength(5, 20);
        this.setMaximumAge(fromDays(50));
        this.setEyeHeight(0.5F, 3.45F);
        this.setSizeX(0.2F, 2.8F);
        this.setSizeY(0.3F, 4.25F);
        this.setStorage(45);
        this.setDiet((Diet.HERBIVORE.get()));
        this.setBones("cheek_teeth", "front_leg_bones", "hind_leg_bones", "neck_vertebrae", "shoulder", "pelvis", "ribcage", "skull", "tail_vertebrae");
        this.setHeadCubeName("Head");
        this.setScale(2.5F, 0.1F);
        this.setOffset(0.0F, 0.775F, 0.0F);
        this.setAttackBias(-50);
        this.setImprintable(true);
        this.setDefendOwner(true);
        this.setFlockSpeed(1.5F);
        this.setBreeding(false, 2, 6, 40, false, true);
        String[][] recipe = {
                {"tail_vertebrae", "pelvis", "ribcage","neck_vertebrae","skull"},
                {"hind_leg_bones", "", "", "shoulder", "cheek_teeth"},
                {"", "", "", "", "front_leg_bones"}};
        this.setRecipe(recipe);
        this.enableSkeleton();
        ArrayList<ResourceKey<Biome>> biomeList = new ArrayList<>();
        Stream<TagKey<Biome>> tags = Arrays.stream(new TagKey[]{BiomeTags.IS_SAVANNA, Tags.Biomes.IS_PLAINS, BiomeTags.IS_BADLANDS, Tags.Biomes.IS_SANDY});
        ArrayList<Biome> allBiomes = new ArrayList<>(ForgeRegistries.BIOMES.getValues());

        for (Biome biome :
                allBiomes) {
            var key = ForgeRegistries.BIOMES.getResourceKey(biome);
            if (key.isPresent() && tags.anyMatch((tag) -> ForgeRegistries.BIOMES.tags().getTag(tag).contains(biome))) {
                biomeList.add(key.get());
            }
        }
        this.setSpawn(1, biomeList);
        this.setSpawn(1, biomeList);
    }
}