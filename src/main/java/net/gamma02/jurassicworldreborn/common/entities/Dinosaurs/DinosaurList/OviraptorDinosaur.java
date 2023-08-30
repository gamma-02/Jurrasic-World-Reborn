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

public class OviraptorDinosaur extends Dinosaur
{
    public static final double SPEED = 0.40F;
    public OviraptorDinosaur(){
        super();

        this.setName("Oviraptor");
        this.setDinosaurType(DinosaurType.SCARED);
        this.setDinosaurClass(OviraptorEntity.class);
        this.setTimePeriod(TimePeriod.CRETACEOUS);
        this.setEggColorMale(0xA2A7AE, 0x666E81);
        this.setEggColorFemale(0xDEDAC4, 0x663341);
        this.setHealth(4, 16);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setStorage(9);
        this.setAttackSpeed(0.11);
        this.setStrength(2, 6);
        this.setAttackSpeed(2);
        this.setMaximumAge(fromDays(25));
        this.setEyeHeight(0.32F, 0.95F);
        this.setSizeX(0.25F, 0.6F);
        this.setSizeY(0.32F, 0.95F);
        this.setMaxHerdSize(8);
        this.setDiet(Diet.CARNIVORE.get().withModule(new Diet.DietModule(FoodType.PLANT)).withModule(new Diet.DietModule(FoodType.MEAT).withCondition(entity -> entity instanceof CompsognathusEntity || entity instanceof MicroraptorEntity)));
        this.setSleepTime(SleepTime.DIURNAL);
        this.setBones("arm_bones", "leg_bones", "neck_vertebrae", "pelvis", "ribcage", "shoulder", "skull", "tail_vertebrae");
        this.setHeadCubeName("Upper Head");
        this.setAttackBias(5);
        this.setImprintable(true);
        this.setDefendOwner(true);
        this.setScale(0.65F, 0.15F);
        this.setBreeding(false, 2, 4, 30, false, true);
        String[][] recipe = {
                {"", "","neck_vertebrae","skull"},
                {"tail_vertebrae", "pelvis", "ribcage","shoulder"},
                {"", "leg_bones", "", "arm_bones"}};
        this.setRecipe(recipe);
        this.enableSkeleton();
        ArrayList<ResourceKey<Biome>> biomeList = new ArrayList<>();
        Stream<TagKey<Biome>> tags = Arrays.stream(new TagKey[]{ Tags.Biomes.IS_SANDY, Tags.Biomes.IS_DRY, BiomeTags.IS_SAVANNA});
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
