package net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurList;


import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.*;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Diet;
import net.gamma02.jurassicworldreborn.common.util.TimePeriod;
import java.util.ArrayList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class HerrerasaurusDinosaur extends Dinosaur
{
    public static final double SPEED = 0.4F;
    public HerrerasaurusDinosaur()
    {
        super();

        this.setName("Herrerasaurus");
        this.setDinosaurType(DinosaurType.NEUTRAL);
        this.setDinosaurClass(HerrerasaurusEntity.class);
        this.setTimePeriod(TimePeriod.TRIASSIC);
        this.setEggColorMale(0xb22919, 0x71a36d);
        this.setEggColorFemale(0xaa2616, 0x2a150b);
        this.setHealth(10, 36);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setStrength(5, 20);
        this.setMaximumAge(fromDays(45));
        this.setEyeHeight(0.45F, 2.25F);
        this.setSizeX(0.4F, 1.8F);
        this.setSizeY(0.55F, 2.55F);
        this.setStorage(36);
        this.setDiet((Diet.CARNIVORE.get()));
        this.setBones("arm_bones", "foot_bones", "leg_bones", "neck_vertebrae", "pelvis", "ribcage", "shoulder", "skull", "tail_vertebrae", "tooth", "claw");
        this.setHeadCubeName("Head");
        this.setScale(1.0F, 0.15F);
        this.setBreeding(false, 2, 4, 60, false, true);
        this.setAttackBias(300);
        this.setImprintable(true);
        this.setDefendOwner(true);
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
