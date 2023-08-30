package net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurList;


import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.*;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Diet;
import net.gamma02.jurassicworldreborn.common.util.TimePeriod;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ForgeBiomeTagsProvider;
import net.minecraftforge.registries.ForgeRegistries;

public class BaryonyxDinosaur extends Dinosaur
{
    public static final double SPEED = 0.40F;
    public BaryonyxDinosaur()
    {
        super();
        this.setName("Baryonyx");
        this.setDinosaurType(DinosaurType.AGGRESSIVE);
        this.setDinosaurClass(BaryonyxEntity.class);
        this.setTimePeriod(TimePeriod.CRETACEOUS);
        this.setEggColorMale(0x796e20, 0x077d73);
        this.setEggColorFemale(0x8a7f31, 0x463e15);
        this.setHealth(6, 35);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setAttackSpeed(1.6);
        this.setStrength(1, 10);
        this.setMaximumAge(fromDays(55));
        this.setEyeHeight(0.55F, 2.95F);
        this.setSizeX(0.3F, 1.5F);
        this.setSizeY(0.5F, 2.95F);
        this.setDiet(Diet.PCARNIVORE.get());
        this.setBones("arm_bones", "claw", "leg_bones", "neck_vertebrae", "pelvis", "ribcage", "shoulder", "skull", "tail_vertebrae", "tooth", "foot_bones");
        this.setHeadCubeName("Head");
        this.setBreeding(false, 2, 6, 60, false, true);
        this.setScale(1.38F, 0.1F);
        this.shouldDefendOffspring();
        this.setImprintable(true);
        this.setDefendOwner(true);
        String[][] recipe = {
                {"", "", "","neck_vertebrae","skull"},
                {"tail_vertebrae", "pelvis", "ribcage","shoulder","tooth"},
                {"", "leg_bones", "", "arm_bones", ""},
                {"", "foot_bones", "", "", ""}};
        this.setRecipe(recipe);
        this.enableSkeleton();
        ArrayList<ResourceKey<Biome>> biomeList = new ArrayList<>();
//        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS));
//        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST));
//        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.SWAMP));
//        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.RIVER));

        Stream<TagKey<Biome>> tags = Arrays.stream(new TagKey[]{Tags.Biomes.IS_PLAINS, BiomeTags.IS_FOREST, Tags.Biomes.IS_SWAMP, BiomeTags.IS_RIVER});
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
