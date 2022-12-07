package net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurList;

import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.*;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Diet;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.MovementType;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.SleepTime;
import net.gamma02.jurassicworldreborn.common.util.TimePeriod;
import java.util.ArrayList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class MawsoniaDinosaur extends Dinosaur {

	public static final double SPEED = 0.4F;
    public MawsoniaDinosaur()
    {
        super();

        this.setName("Mawsonia");
        this.setDinosaurClass(MawsoniaEntity.class);
        this.setDinosaurType(DinosaurType.AGGRESSIVE);
        this.setTimePeriod(TimePeriod.CRETACEOUS);
        this.setEggColorMale(0x707B94, 0x3B4963);
        this.setEggColorFemale(0x7C775E, 0x4D4A3B);
        this.setHealth(6, 40);
        this.setFlee(true);
        this.setSpeed((SPEED -0.35), SPEED);
        this.setAttackSpeed(1.5);
        this.setStrength(0.5, 3);
        this.setMaximumAge(this.fromDays(30));
        this.setEyeHeight(0.35F, 1.8F);
        this.setSizeX(0.3F, 2.5F);
        this.setSizeY(0.2F, 1.8F);
        this.setDiet((Diet.PCARNIVORE.get()));
        this.setSleepTime(SleepTime.NO_SLEEP);
        this.setBirthType(BirthType.LIVE_BIRTH);
        this.setBones("anal_fin", "caudal_fin", "first_dorsal_fin", "pectoral_fin_bones", "pelvic_fin_bones", "second_dorsal_fin", "skull", "spine", "teeth");
        this.setHeadCubeName("Main head");
        this.setScale(1.8F, 0.1F);
        this.setMaxHerdSize(4);
        this.setOffset(0.0F, 1.0F, -0.2F);
        this.setAttackBias(100.0);
        this.setMarineAnimal(true);
        this.setMovementType(MovementType.DEEP_WATER);
        this.setBreeding(true, 2, 4, 15, true, false);
        this.setRandomFlock(true);
        String[][] recipe =     {{"", "second_dorsal_fin", "first_dorsal_fin", ""},
                {"caudal_fin", "spine", "pectoral_fin_bones","skull"},
                {"anal_fin","","pelvic_fin_bones","teeth"}};
        this.setRecipe(recipe);

        ArrayList<ResourceKey<Biome>> biomeList = new ArrayList<>();
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.OCEAN));
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.WATER));
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.RIVER));
        this.setSpawn(1, biomeList);
        this.enableSkeleton();
    }



    @Override
    public void applyMeatEffect(Player player, boolean cooked) {
        if (!cooked) {
            player.addEffect(new MobEffectInstance(MobEffects.POISON, 400, 1));
        }
        player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 1));
    }
}
