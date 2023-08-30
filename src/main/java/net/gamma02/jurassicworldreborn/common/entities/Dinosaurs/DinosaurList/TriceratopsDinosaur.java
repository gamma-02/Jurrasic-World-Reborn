package net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurList;

import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.*;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Diet;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.SleepTime;
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

public class TriceratopsDinosaur extends Dinosaur {
    public static final double SPEED = 0.35F;
    public TriceratopsDinosaur() {
        super();

        this.setName("Triceratops");
        this.setDinosaurClass(TriceratopsEntity.class);
        this.setDinosaurType(DinosaurType.NEUTRAL);
        this.setTimePeriod(TimePeriod.CRETACEOUS);
        this.setEggColorMale(0x404138, 0x1C1C1C);
        this.setEggColorFemale(0x8F7B76, 0x73676A);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setAttackSpeed(1.3);
        this.setHealth(10, 70);
        this.setStrength(5, 20);
        this.setMaximumAge(this.fromDays(45));
        this.setEyeHeight(0.45F, 1.8F);
        this.setSizeX(0.15F, 2.5F);
        this.setSizeY(0.3F, 3.0F);
        this.setStorage(36);
        this.setDiet(Diet.HERBIVORE.get());
        this.setBones("front_leg_bones", "hind_leg_bones", "horn", "neck_vertebrae", "pelvis", "ribcage", "shoulder", "skull", "tail_vertebrae", "tooth");
        this.setHeadCubeName("Head");
        this.setScale(1.35F, 0.15F);
        this.setOffset(0.0F, 0.45F, 0.0F);
        this.setImprintable(true);
        this.setDefendOwner(true);
        this.setAttackBias(400.0);
        this.setBreeding(false, 2, 6, 48, false, true);
        String[][] recipe = {
                {"", "", "","","horn"},
                {"tail_vertebrae", "pelvis", "ribcage","neck_vertebrae","skull"},
                {"hind_leg_bones", "hind_leg_bones", "", "shoulder", "tooth"},
                {"", "", "", "front_leg_bones", "front_leg_bones"}};
        this.setRecipe(recipe);
        
        ArrayList<ResourceKey<Biome>> biomeList = new ArrayList<>();
        Stream<TagKey<Biome>> tags = Arrays.stream(new TagKey[]{Tags.Biomes.IS_PLAINS, BiomeTags.IS_FOREST, Tags.Biomes.IS_SPARSE});
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
