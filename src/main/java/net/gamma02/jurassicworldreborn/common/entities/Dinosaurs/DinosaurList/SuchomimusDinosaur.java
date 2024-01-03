package net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurList;

import java.util.ArrayList;

import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.*;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Diet;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.FoodType;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.SleepTime;
import net.gamma02.jurassicworldreborn.common.util.TimePeriod;
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

public class SuchomimusDinosaur extends Dinosaur {
    public static final double SPEED = 0.4F;
    public SuchomimusDinosaur() {
        super();

        this.setName("Suchomimus");
        this.setDinosaurType(DinosaurType.AGGRESSIVE);
        this.setDinosaurClass(SuchomimusEntity.class);
        this.setTimePeriod(TimePeriod.CRETACEOUS);
        this.setEggColorMale(0xCCB674, 0x4A5966);
        this.setEggColorFemale(0xB3BB6D, 0x45676B);
        this.setHealth(10, 45);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setStorage(27);
        this.setStrength(5, 30);
        this.setMaximumAge(fromDays(45));
        this.setEyeHeight(0.45F, 1.6F);
        this.setSizeX(0.2F, 2.0F);
        this.setSizeY(0.4F, 3.2F);
        this.setDiet(Diet.PCARNIVORE.get());
        this.setBones("arm_bones", "claw", "leg_bones", "neck_vertebrae", "pelvis", "ribcage", "shoulder", "skull", "tail_vertebrae", "tooth", "foot_bones");
        this.setHeadCubeName("Head");
        this.setScale(1.5F, 0.1F);
        this.shouldDefendOffspring();
        this.setBreeding(false, 2, 6, 20, false, true);
        this.setImprintable(true);
        String[][] recipe = {
                {"", "", "","neck_vertebrae","skull"},
                {"tail_vertebrae", "pelvis", "ribcage","shoulder","tooth"},
                {"", "", "leg_bones", "arm_bones", "claw"},
                {"", "", "foot_bones", "", ""}};
        this.setRecipe(recipe);
        this.enableSkeleton();
        ArrayList<ResourceKey<Biome>> biomeList = new ArrayList<>();
        TagKey<Biome>[] tags = (new TagKey[]{Tags.Biomes.IS_PLAINS, BiomeTags.IS_RIVER, BiomeTags.IS_SAVANNA, Tags.Biomes.IS_SWAMP});
        ArrayList<Biome> allBiomes = new ArrayList<>(ForgeRegistries.BIOMES.getValues());

        biomeList = new ArrayList<>(allBiomes.stream().filter((biome ->{
            boolean accept = false;
            
            for(var tag : tags){
                if(ForgeRegistries.BIOMES.tags().getTag(tag).contains(biome)){
                    accept = true;
                }
            }
            return accept;
            
        })).map((biome) -> ForgeRegistries.BIOMES.getResourceKey(biome).get()).toList());
        this.setSpawn(1, biomeList);
this.init();
    }
}
