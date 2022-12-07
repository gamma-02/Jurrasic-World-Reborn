package net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurList;

import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.*;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Diet;
import net.gamma02.jurassicworldreborn.common.util.TimePeriod;
import net.minecraft.resources.ResourceKey;
import java.util.ArrayList;

import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

public class AlvarezsaurusDinosaur extends Dinosaur {
    public static final double SPEED = 0.4F;
    public AlvarezsaurusDinosaur(){
        super();
        this.setName("Alvarezsaurus");
        this.setDinosaurType(DinosaurType.SCARED);
        this.setDinosaurClass(AlvarezsaurusEntity.class);
        this.setTimePeriod(TimePeriod.CRETACEOUS);
        this.setEggColorMale(0xC1946C, 0xE39B48);
        this.setEggColorFemale(0xBD8D62, 0xACACAC);
        this.setHealth(4, 10);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setStrength(2, 4);
        this.setMaximumAge(fromDays(40));
        this.setEyeHeight(0.225F, 1.3F);
        this.setSizeX(0.3F, 0.5F);
        this.setSizeY(0.3F, 0.6F);
        this.setStorage(27);
        this.setDiet((Diet.INCARNIVORE.get()));
        this.setBones("claw", "arm_bones", "foot_bones","leg_bones", "neck_vertebrae", "pelvis", "ribcage", "shoulder", "skull", "tail_vertebrae");
        this.setHeadCubeName("Upper Head");
        this.setScale(0.5F, 0.125F);
        this.setBreeding(false, 2, 6, 20, false, false);
        this.setImprintable(true);
        this.setFlee(true);
        this.setMaxHerdSize(12);
        this.setDefendOwner(true);
        String[][] recipe = {
                {"", "pelvis", "","",""},
                {"tail_vertebrae", "ribcage", "shoulder", "neck_vertebrae", "skull"},
                {"leg_bones", "leg_bones", "arm_bones", "arm_bones", ""}};
        this.setRecipe(recipe);
        this.enableSkeleton();
        ArrayList<ResourceKey<Biome>> biomeList = new ArrayList<>();
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.PLAINS));
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.SANDY));
        biomeList.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.SAVANNA));
        this.setSpawn(1, biomeList);
    }
}
