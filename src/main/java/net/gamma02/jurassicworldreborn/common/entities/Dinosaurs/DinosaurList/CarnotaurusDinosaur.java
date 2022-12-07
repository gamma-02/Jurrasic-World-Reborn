package net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurList;


import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.*;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Diet;
import net.gamma02.jurassicworldreborn.common.util.TimePeriod;
import java.util.ArrayList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class CarnotaurusDinosaur extends Dinosaur
{
    public static final double SPEED = 0.42F;
    public CarnotaurusDinosaur()
    {
        super();

        this.setName("Carnotaurus");
        this.setDinosaurType(DinosaurType.AGGRESSIVE);
        this.setDinosaurClass(CarnotaurusEntity.class);
        this.setTimePeriod(TimePeriod.CRETACEOUS);
        this.setEggColorMale(0x535023, 0x292d0f);
        this.setEggColorFemale(0x964e26, 0x492f11);
        this.setHealth(10, 35);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setStrength(5, 20);
        this.setMaximumAge(fromDays(45));
        this.setEyeHeight(0.4F, 2.4F);
        this.setSizeX(0.45F, 2.35F);
        this.setSizeY(0.6F, 2.9F);
        this.setDiet(Diet.CARNIVORE.get());
        this.setBones("skull", "tooth", "claw", "foot_bones", "neck_vertebrae", "pelvis", "shoulder", "arm_bones", "leg_bones", "ribcage", "tail_vertebrae");
        this.setHeadCubeName("Head");
        this.setScale(1.5F, 0.15F);
        this.setBreeding(false, 2, 4, 40, false, true);
        this.shouldDefendOffspring();

        String[][] recipe = {
                {"", "", "","neck_vertebrae","skull"},
                {"tail_vertebrae", "pelvis", "ribcage","shoulder","tooth"},
                {"", "leg_bones", "", "arm_bones", "claw"},
                {"", "foot_bones", "", "", ""}};
        this.setRecipe(recipe);
        this.enableSkeleton();
        ArrayList<ResourceKey<Biome>> biomeList = new ArrayList<>();
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.SAVANNA));
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS));
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.FOREST));
        this.setSpawn(1, biomeList);
    }
}
