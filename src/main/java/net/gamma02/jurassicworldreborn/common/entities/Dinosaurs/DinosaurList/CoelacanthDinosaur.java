package net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurList;

import java.util.ArrayList;

import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.*;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Diet;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.FoodType;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.MovementType;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.SleepTime;
import net.gamma02.jurassicworldreborn.common.util.TimePeriod;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.biome.Biome;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.entity.player.Player;


public class CoelacanthDinosaur extends Dinosaur {
    public static final double SPEED = 0.4F;
    public CoelacanthDinosaur() {
        super();

        this.setName("Coelacanth");
        this.setDinosaurType(DinosaurType.SCARED);
        this.setDinosaurClass(CoelacanthEntity.class);
        this.setDinosaurType(DinosaurType.PASSIVE);
        this.setTimePeriod(TimePeriod.DEVONIAN);
        this.setEggColorMale(0x707B94, 0x3B4963);
        this.setEggColorFemale(0x7C775E, 0x4D4A3B);
        this.setHealth(4, 10);
        this.setFlee(true);
        this.setSpeed((SPEED -0.35), SPEED);
        this.setAttackSpeed(1.5);
        this.setStrength(0.5, 3);
        this.setMaximumAge(this.fromDays(30));
        this.setEyeHeight(0.35F, 1.8F);
        this.setSizeX(0.1F, 1.0F);
        this.setSizeY(0.1F, 1.0F);
        this.setDiet(Diet.PISCIVORE.get().withModule(new Diet.DietModule(FoodType.FILTER)));
        this.setSleepTime(SleepTime.NO_SLEEP);
        this.setBirthType(BirthType.LIVE_BIRTH);
        this.setBones("anal_fin", "caudal_fin", "first_dorsal_fin", "pectoral_fin_bones", "pelvic_fin_bones", "second_dorsal_fin", "skull", "spine", "teeth");
        this.setHeadCubeName("Head");
        this.setScale(1.8F, 0.22F);
        this.setMaxHerdSize(1);
        this.setOffset(0.0F, 1.1F, -0.2F);
        this.setAttackBias(100.0);
        this.setMarineAnimal(true);
        this.setMovementType(MovementType.DEEP_WATER);
        this.setBreeding(true, 1, 3, 20, true, false);
        this.setRandomFlock(false);
        String[][] recipe =     {{"", "second_dorsal_fin", "first_dorsal_fin", ""},
                {"caudal_fin", "spine", "pectoral_fin_bones","skull"},
                {"anal_fin","","pelvic_fin_bones","teeth"}};
        this.setRecipe(recipe);
        
        ArrayList<ResourceKey<Biome>> biomeList = new ArrayList<>();
        Stream<TagKey<Biome>> tags = Arrays.stream(new TagKey[]{Tags.Biomes.IS_WATER, BiomeTags.IS_OCEAN, BiomeTags.IS_RIVER});
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

    @Override
    public void applyMeatEffect(Player player, boolean cooked){
        if (!cooked){
            player.addEffect(new MobEffectInstance(MobEffects.POISON, 400, 1));
        }
        player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 1));
    }
}
