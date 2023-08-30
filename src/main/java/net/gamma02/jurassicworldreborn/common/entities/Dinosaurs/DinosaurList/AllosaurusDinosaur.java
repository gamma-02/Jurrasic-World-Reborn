package net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurList;


import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.AllosaurusEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Diet;
import net.gamma02.jurassicworldreborn.common.util.TimePeriod;
import net.minecraft.resources.ResourceKey;
import java.util.ArrayList;

import net.minecraft.world.level.biome.Biome;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

public class AllosaurusDinosaur extends Dinosaur {
    public static final double SPEED = 0.42F;
    public AllosaurusDinosaur() {
        super();

        this.setName("Allosaurus");
        this.setDinosaurType(DinosaurType.AGGRESSIVE);
        this.setDinosaurClass(AllosaurusEntity.class);
        this.setTimePeriod(TimePeriod.JURASSIC);
        this.setEggColorMale(0xbaa262, 0x753227);
        this.setEggColorFemale(0x3b4046, 0x877e73);
        this.setHealth(10, 50);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setAttackSpeed(1.1);
        this.setStrength(1, 20);
        this.setMaximumAge(fromDays(45));
        this.setEyeHeight(0.45F, 1.6F);
        this.setSizeX(0.2F, 2.5F);
        this.setSizeY(0.3F, 4F);
        this.setDiet(Diet.CARNIVORE.get());
        this.setBones("leg_bones", "neck_vertebrae", "arm_bones", "claw", "foot_bones", "leg_bones", "pelvis", "shoulder", "tooth", "ribcage", "skull", "tail_vertebrae");
        this.setHeadCubeName("Head");
        this.setBreeding(false, 2, 4, 40, false, true);
        this.setScale(1.3F, 0.1F);
        this.shouldDefendOffspring();
        String[][] recipe = {{"", "", "","neck_vertebrae","skull"},
                {"tail_vertebrae", "pelvis", "ribcage","shoulder","tooth"},
                {"", "leg_bones", "", "arm_bones", "claw"},
                {"", "foot_bones", "", "", ""}};
        this.setRecipe(recipe);
        doSkeletonCheck();
        ArrayList<ResourceKey<Biome>> biomeList = new ArrayList<ResourceKey<Biome>>();
        Stream<TagKey<Biome>> tags = Arrays.stream(new TagKey[]{ Tags.Biomes.IS_PLAINS, BiomeTags.IS_SAVANNA, BiomeTags.IS_FOREST});
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

    protected void doSkeletonCheck(){
        this.enableSkeleton();
    }
}
