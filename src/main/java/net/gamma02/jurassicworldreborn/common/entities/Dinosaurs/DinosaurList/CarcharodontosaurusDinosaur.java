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

public class CarcharodontosaurusDinosaur extends Dinosaur {
    public static final double SPEED = 0.44F;
    public CarcharodontosaurusDinosaur() {
        super();
        this.setName("Carcharodontosaurus");
        this.setDinosaurType(DinosaurType.AGGRESSIVE);
        this.setDinosaurClass(CarcharodontosaurusEntity.class);
        this.setTimePeriod(TimePeriod.CRETACEOUS);
        this.setEggColorMale(0xd2b994, 0x694d3b);
        this.setEggColorFemale(0x656462, 0x8d734f);
        this.setHealth(10, 70);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setAttackSpeed(1.5);
        this.setStrength(1, 20);
        this.setMaximumAge(fromDays(45));
        this.setEyeHeight(0.45F, 1.6F);
        this.setSizeX(0.2F, 3.0F);
        this.setSizeY(0.15F, 4.2F);
        this.setDiet(Diet.CARNIVORE.get());
        this.setBones("skull", "tooth", "arm_bones", "claw", "foot_bones", "leg_bones", "neck_vertebrae", "pelvis", "shoulder", "tail_vertebrae", "ribcage");
        this.setHeadCubeName("head");
        this.setScale(2.0F, 0.1F);
        this.setBreeding(false, 2, 6, 60, false, true);
        this.shouldDefendOffspring();
        String[][] recipe = {
                {"", "", "","neck_vertebrae","skull"},
                {"tail_vertebrae", "pelvis", "ribcage","shoulder","tooth"},
                {"", "leg_bones", "leg_bones", "arm_bones", ""},
                {"", "foot_bones", "foot_bones", "", ""}};
        this.setRecipe(recipe);
        this.enableSkeleton();
        this.setRecipe(recipe);
        ArrayList<ResourceKey<Biome>> biomeList = new ArrayList<>();
        TagKey<Biome>[] tags = (new TagKey[]{Tags.Biomes.IS_PLAINS, BiomeTags.IS_FOREST, BiomeTags.IS_SAVANNA, Tags.Biomes.IS_DRY});
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
