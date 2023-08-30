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
public class PteranodonDinosaur extends Dinosaur
{
    public static final double SPEED = 0.40F;
    public PteranodonDinosaur()
    {
        super();

        this.setName("Pteranodon");
        this.setDinosaurType(DinosaurType.AGGRESSIVE);
        this.setDinosaurClass(PteranodonEntity.class);
        this.setTimePeriod(TimePeriod.CRETACEOUS);
        this.setEggColorMale(0x57504C, 0x24383F);
        this.setEggColorFemale(0x535F65, 0x56312C);
        this.setHealth(10, 35);
        this.setStrength(5, 20);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setMaximumAge(fromDays(40));
        this.setEyeHeight(0.45F, 1.6F);
        this.setSizeX(0.4F, 2.0F);
        this.setSizeY(0.3F, 1.8F);
        this.setStorage(27);
        this.setDiet((Diet.CARNIVORE.get()));
        this.setBones("leg_bones", "neck_vertebrae", "pelvis", "ribcage", "skull", "tail_vertebrae", "wing_bones");
        this.setHeadCubeName("Head");
        this.setScale(1.2F, 0.1F);
        this.setAttackBias(800);
        this.shouldDefendOffspring();
        this.setImprintable(false);
        this.setAvianAnimal(true);
        this.setBreeding(false, 2, 6, 80, false, true);
        String[][] recipe = {
                {"tail_vertebrae", "pelvis", "ribcage", "skull"},
                {"", "leg_bones", "wing_bones", "", ""}};
        this.setRecipe(recipe);
        this.enableSkeleton();
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
        this.setSpawn(1, biomeList);
    }
}
