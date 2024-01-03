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

public class ChasmosaurusDinosaur extends Dinosaur
{
    public static final double SPEED = 0.35F;
    public ChasmosaurusDinosaur()
    {
        super();

        this.setName("Chasmosaurus");
        this.setDinosaurType(DinosaurType.NEUTRAL);
        this.setDinosaurClass(ChasmosaurusEntity.class);
        this.setTimePeriod(TimePeriod.CRETACEOUS);
        this.setEggColorMale(0xB6B293, 0x85563E);
        this.setEggColorFemale(0xB9B597, 0x323232);
        this.setHealth(10, 40);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setStrength(5, 15);
        this.setMaximumAge(fromDays(40));
        this.setEyeHeight(0.3F, 1.35F);
        this.setSizeX(0.35F, 1.75F);
        this.setSizeY(0.45F, 2.35F);
        this.setStorage(27);
        this.setDiet((Diet.HERBIVORE.get()));
        this.setBones("front_leg_bones", "hind_leg_bones", "tooth", "neck_vertebrae", "pelvis", "ribcage", "shoulder", "skull", "tail_vertebrae");
        this.setHeadCubeName("Head");
        this.setScale(1.55F, 0.1F);
        this.setOffset(0.0F, 0.775F, 0.0F);
        this.setBreeding(false, 2, 6, 40, false, true);
        this.setImprintable(true);
        this.setDefendOwner(true);
        this.shouldDefendOffspring();
        String[][] recipe = {
                {"", "", "","neck_vertebrae","skull"},
                {"tail_vertebrae", "pelvis", "ribcage","shoulder","tooth"},
                {"", "", "hind_leg_bones", "front_leg_bones", ""}};
        this.setRecipe(recipe);
        this.enableSkeleton();
        ArrayList<ResourceKey<Biome>> biomeList = new ArrayList<>();
        TagKey<Biome>[] tags = (new TagKey[]{Tags.Biomes.IS_PLAINS, BiomeTags.IS_BADLANDS, BiomeTags.IS_SAVANNA, Tags.Biomes.IS_CONIFEROUS});
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
