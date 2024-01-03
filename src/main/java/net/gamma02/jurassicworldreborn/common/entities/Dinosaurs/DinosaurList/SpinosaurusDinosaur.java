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

public class SpinosaurusDinosaur extends Dinosaur
{
    public static final double SPEED = 0.4F;
    public SpinosaurusDinosaur()
    {
        super();

        this.setName("Spinosaurus");
        this.setDinosaurType(DinosaurType.AGGRESSIVE);
        this.setDinosaurClass(SpinosaurusEntity.class);
        this.setTimePeriod(TimePeriod.CRETACEOUS);
        this.setEggColorMale(0x656f76, 0x34a1e9);
        this.setEggColorFemale(0x786c66, 0x7b463b);
        this.setHealth(10, 60);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setStrength(10, 20);
        this.setMaximumAge(fromDays(55));
        this.setEyeHeight(0.6F, 3.8F);
        this.setSizeX(0.2F, 2.7F);
        this.setSizeY(0.3F, 4.4F);
        this.setStorage(54);
        this.setDiet((Diet.PCARNIVORE.get()));
        this.setBones("skull", "tooth", "arm_bones", "claw", "foot_bones", "leg_bones", "neck_vertebrae", "ribcage", "shoulder", "tail_vertebrae", "pelvis");
        this.setHeadCubeName("Head");
        this.setScale(1.53F, 0.1F);
        this.setAttackBias(900);
        this.shouldDefendOffspring();
        this.setBreeding(false, 2, 6, 40, false, true);
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
