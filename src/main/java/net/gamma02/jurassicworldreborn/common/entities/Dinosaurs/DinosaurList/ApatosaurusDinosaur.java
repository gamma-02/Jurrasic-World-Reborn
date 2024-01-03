package net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurList;


import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.*;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Diet;
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

import java.util.ArrayList;

public class ApatosaurusDinosaur extends Dinosaur
{
    public static final double SPEED = 0.25F;
    public ApatosaurusDinosaur()
    {
        super();
        this.setName("Apatosaurus");
        this.setDinosaurType(DinosaurType.PASSIVE);
        this.setDinosaurClass(ApatosaurusEntity.class);
        this.setTimePeriod(TimePeriod.JURASSIC);
        this.setEggColorMale(0x565b4b, 0x7a925c);
        this.setEggColorFemale(0x31313f, 0x222238);
        this.setHealth(10, 80);
        this.setStrength(5, 20);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setMaximumAge(fromDays(80));
        this.setEyeHeight(0.7F, 6.6F);
        this.setSizeX(0.5F, 6.3F);
        this.setSizeY(0.6F, 6.6F);
        this.setStorage(54);
        this.setDiet((Diet.HERBIVORE.get()));
        this.setBones("front_leg_bones", "hind_leg_bones", "neck_vertebrae", "pelvis", "ribcage", "shoulder", "skull", "tail_vertebrae", "tooth");
        this.setHeadCubeName("Head");
        this.setScale(1.45F, 0.1F);
        this.setBreeding(false, 4, 8, 70, false, true);
        this.setImprintable(true);
        this.setMaxHerdSize(6);
        this.setOffset(0.0F, 0.0F, 0.1F);
        String[][] recipe =     {{"", "", "", "", "skull"},
                {"", "", "", "neck_vertebrae","tooth"},
                {"tail_vertebrae","pelvis","ribcage","shoulder",""},
                {"","hind_leg_bones","hind_leg_bones","front_leg_bones","front_leg_bones"}};
        this.setRecipe(recipe);
        this.enableSkeleton();
        ArrayList<ResourceKey<Biome>> biomeList = new ArrayList<>();
        TagKey<Biome>[] tags = (new TagKey[]{ Tags.Biomes.IS_PLAINS, BiomeTags.IS_SAVANNA});
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
