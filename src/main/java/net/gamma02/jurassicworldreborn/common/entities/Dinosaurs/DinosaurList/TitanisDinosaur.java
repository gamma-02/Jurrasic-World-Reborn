package net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurList;

import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.TitanisEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Diet;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.SleepTime;
import net.gamma02.jurassicworldreborn.common.util.TimePeriod;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;

public class TitanisDinosaur extends Dinosaur {
    public static final double SPEED = 0.40F;
    public TitanisDinosaur() {
        super();

        this.setName("Titanis");
        this.setDinosaurClass(TitanisEntity.class);
        this.setDinosaurType(DinosaurType.AGGRESSIVE);
        this.setTimePeriod(TimePeriod.PALEOGENE);
        this.setEggColorMale(0xe5e1d3, 0x58598d);
        this.setEggColorFemale(0xe5e1d3, 0x7b7262);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setAttackSpeed(1.95);
        this.setHealth(6, 30);
        this.setStrength(4, 15);
        this.setMaximumAge(this.fromDays(45));
        this.setEyeHeight(0.45F, 1.7F);
        this.setSizeX(0.5F, 2.3F);
        this.setSizeY(0.5F, 2.5F);
        this.setStorage(20);
        this.setDiet(Diet.CARNIVORE.get());
        this.setSleepTime(SleepTime.DIURNAL);
        this.setBones("skull", "leg_bones", "ribcage", "neck_vertebrae", "pelvis", "shoulder");
        this.setHeadCubeName("Head");
        this.setScale(1.6F, 0.2F);
        this.setImprintable(true);
        this.setDefendOwner(true);
        this.setMaxHerdSize(40);
        this.setAttackBias(600.0);
        this.setCanClimb(true);
        this.setBreeding(false,2, 4, 30, false, true);
        this.setJumpHeight(2);
        String[][] recipe =     {
                { "", "pelvis", "ribcage","neck_vertebrae", "skull"},
                { "leg_bones", "", "leg_bones", "shoulder", ""}};
        this.setRecipe(recipe);
        doSkeletonCheck();
        ArrayList<ResourceKey<Biome>> biomeList = new ArrayList<>();
        TagKey<Biome>[] tags = (new TagKey[]{BiomeTags.IS_SAVANNA, Tags.Biomes.IS_CONIFEROUS, Tags.Biomes.IS_PLAINS, BiomeTags.IS_FOREST, BiomeTags.IS_BADLANDS, Tags.Biomes.IS_SNOWY, Tags.Biomes.IS_COLD});
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

    protected void doSkeletonCheck(){
        this.enableSkeleton();
    }
}