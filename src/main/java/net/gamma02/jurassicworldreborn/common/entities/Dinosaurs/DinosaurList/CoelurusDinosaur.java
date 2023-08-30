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

public class CoelurusDinosaur extends Dinosaur
{
    public static final double SPEED = 0.35F;
    public CoelurusDinosaur()
    {
        super();
        this.setName("Coelurus");
        this.setDinosaurType(DinosaurType.AGGRESSIVE);
        this.setDinosaurClass(CoelurusEntity.class);
        this.setTimePeriod(TimePeriod.JURASSIC);
        this.setEggColorMale(0x9c7219, 0x382508);
        this.setEggColorFemale(0x7D734A, 0x484A3D);
        this.setHealth(4, 16);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setStrength(3, 6);
        this.setMaximumAge(fromDays(20));
        this.setEyeHeight(0.2F, 0.5F);
        this.setSizeX(0.1F, 0.6F);
        this.setSizeY(0.25F, 0.95F);
        this.setStorage(9);
        this.setAttackBias(90);
        this.setDiet((Diet.CARNIVORE.get()));
        this.setBones("ribcage", "arm_bones", "claw", "foot_bones", "leg_bones", "neck_vertebrae", "pelvis", "shoulder", "skull", "tail_vertebrae", "tooth");
        this.setHeadCubeName("Head");
        this.setScale(0.85F, 0.1F);
        this.setImprintable(true);
        this.setDefendOwner(true);
        this.setBreeding(false, 2, 4, 20, false, true);
        String[][] recipe =     {{"", "", "", "neck_vertebrae", "skull"},
                {"tail_vertebrae", "pelvis", "ribcage","shoulder","tooth"},
                {"leg_bones", "leg_bones", "", "", "arm_bones"}};
        this.setRecipe(recipe);
        this.enableSkeleton();
        ArrayList<ResourceKey<Biome>> biomeList = new ArrayList<>();
        Stream<TagKey<Biome>> tags = Arrays.stream(new TagKey[]{Tags.Biomes.IS_PLAINS, BiomeTags.IS_FOREST, BiomeTags.IS_SAVANNA, BiomeTags.IS_BADLANDS, Tags.Biomes.IS_SANDY});
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
