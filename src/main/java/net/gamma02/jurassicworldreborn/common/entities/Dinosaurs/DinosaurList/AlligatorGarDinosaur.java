package net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurList;

import java.io.IOException;
import java.util.ArrayList;

import com.mojang.blaze3d.shaders.Effect;
import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Diet;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.MovementType;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.SleepTime;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.*;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.FoodType;
import net.gamma02.jurassicworldreborn.common.util.TimePeriod;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;

import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.level.biome.Biome;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

public class AlligatorGarDinosaur extends Dinosaur
{
    public static final double SPEED = 0.4F;
    public AlligatorGarDinosaur()
    {
        super();
        this.setName("Alligator Gar");
        this.setDinosaurType(DinosaurType.PASSIVE);
        this.setDinosaurClass(AlligatorGarEntity.class);
        this.setTimePeriod(TimePeriod.CRETACEOUS);
        this.setEggColorMale(0x707B94, 0x3B4963);
        this.setEggColorFemale(0x7C775E, 0x4D4A3B);
        this.setHealth(2, 10);
        this.setFlee(true);
        this.setSpeed((SPEED -0.15), SPEED);
        this.setAttackSpeed(1.5);
        this.setStrength(0.5, 3);
        this.setMaximumAge(this.fromDays(30));
        this.setEyeHeight(0.35F, 1.2F);
        this.setSizeX(0.1F, 1.3F);
        this.setSizeY(0.02F, .6F);
        this.setDiet(Diet.PISCIVORE.get().withModule(new Diet.DietModule(FoodType.FILTER)));
        this.setSleepTime(SleepTime.NO_SLEEP);
        this.setBones("anal_fin", "dorsal_fin", "pectoral_fin_bones", "pelvic_fin_bones", "scales", "skull", "spine", "tail_fin", "teeth");
        this.setHeadCubeName("Head");
        this.setScale(.95F, 0.15F);
        this.setMaxHerdSize(30);
        this.setOffset(0.0F, .7F, 0F);
        this.setAttackBias(100.0);
        this.setBreeding(true, 2, 12, 40, false, true);
        this.setMarineAnimal(true);
        this.setMovementType(MovementType.NEAR_SURFACE);
        this.setRandomFlock(false);
        this.setImprintable(false);
        this.setBirthType(BirthType.LIVE_BIRTH);
        String[][] recipe = {{"", "dorsal_fin", "", ""},
                {"tail_fin", "spine", "pectoral_fin_bones", "skull"},
                {"anal_fin", "", "pelvic_fin_bones", "teeth"}};
        this.setRecipe(recipe);

        ArrayList<ResourceKey<Biome>> biomeList = new ArrayList<>();
        TagKey<Biome>[] tags = (new TagKey[]{ Tags.Biomes.IS_WATER, BiomeTags.IS_OCEAN, BiomeTags.IS_RIVER});
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
