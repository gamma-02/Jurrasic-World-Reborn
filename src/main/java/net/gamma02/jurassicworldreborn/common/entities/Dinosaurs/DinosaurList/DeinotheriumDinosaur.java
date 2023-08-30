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

public class DeinotheriumDinosaur extends Dinosaur {
    public static final double SPEED = 0.3F;
    public DeinotheriumDinosaur() {
        super();

        this.setName("Deinotherium");
        this.setDinosaurType(DinosaurType.NEUTRAL);
        this.setDinosaurClass(DeinotheriumEntity.class);
        this.setTimePeriod(TimePeriod.PALEOGENE);
        this.setEggColorMale(0x625b54, 0x997969);
        this.setEggColorFemale(0x868275, 0xc9bcae);
        this.setHealth(10, 40);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setStorage(27);
        this.setStrength(3, 15);
        this.setMaximumAge(fromDays(45));
        this.setEyeHeight(0.45F, 1.8F);
        this.setSizeX(0.35F, 3F);
        this.setSizeY(0.5F, 3.3F);
        this.setDiet(Diet.HERBIVORE.get());
        this.setBones("skull", "tooth","front_leg_bones", "hind_leg_bones", "tusk", "neck_vertebrae", "pelvis", "ribcage", "shoulder", "tail_vertebrae");
        this.setHeadCubeName("Head");
        this.setScale(2F, 0.45F);
        this.setMammal(true);
        this.setBirthType(BirthType.LIVE_BIRTH);
        this.shouldDefendOffspring();
        this.setAttackBias(400);
        this.setImprintable(true);
        this.setDefendOwner(true);
        this.setBreeding(true, 2, 4, 40, false, true);
        String[][] recipe = {
                {"", "", "","neck_vertebrae","skull"},
                {"tail_vertebrae", "pelvis", "ribcage","tooth","tusk"},
                {"", "hind_leg_bones", "", "front_leg_bones", "shoulder"}};
        this.setRecipe(recipe);
        this.enableSkeleton();
        ArrayList<ResourceKey<Biome>> biomeList = new ArrayList<>();
        Stream<TagKey<Biome>> tags = Arrays.stream(new TagKey[]{BiomeTags.IS_SAVANNA, Tags.Biomes.IS_CONIFEROUS, Tags.Biomes.IS_PLAINS, BiomeTags.IS_FOREST, BiomeTags.IS_BADLANDS, Tags.Biomes.IS_SNOWY});
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
