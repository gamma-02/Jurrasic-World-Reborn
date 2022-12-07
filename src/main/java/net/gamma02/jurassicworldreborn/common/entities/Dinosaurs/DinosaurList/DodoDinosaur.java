package net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurList;


import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.*;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Diet;
import net.gamma02.jurassicworldreborn.common.util.TimePeriod;
import java.util.ArrayList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class DodoDinosaur extends Dinosaur
{
    public static final double SPEED = 0.3F;
    public DodoDinosaur()
    {
        super();

        this.setName("Dodo");
        this.setDinosaurType(DinosaurType.SCARED);
        this.setDinosaurClass(DodoEntity.class);
        this.setTimePeriod(TimePeriod.NEOGENE);
        this.setEggColorMale(0xA2996E, 0x545338);
        this.setEggColorFemale(0x908B80, 0x665C51);
        this.setHealth(4, 16);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setStrength(1, 5);
        this.setMaximumAge(fromDays(20));
        this.setEyeHeight(0.35F, 0.95F);
        this.setSizeX(0.25F, 0.5F);
        this.setSizeY(0.35F, 0.95F);
        this.setDiet((Diet.HERBIVORE.get()));
        this.setBones("skull", "leg_bones", "neck_vertebrae", "pelvis", "ribcage", "shoulder");
        this.setHeadCubeName("Head");
        this.setScale(0.8F, 0.2F);
        this.setAttackBias(5);
        this.setImprintable(true);
        this.setStorage(6);
        this.setBreeding(false, 2, 6, 20, false, true);
        String[][] recipe =     {{ "", "", "neck_vertebrae", "skull"},
                                { "leg_bones","pelvis", "ribcage", "shoulder"}};
        this.setRecipe(recipe);
        this.enableSkeleton();
        ArrayList<ResourceKey<Biome>> biomeList = new ArrayList<>();
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.SAVANNA));
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS));
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST));
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.MESA));
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.SNOWY));
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.CONIFEROUS));
        this.setSpawn(1, biomeList);
    }
}
