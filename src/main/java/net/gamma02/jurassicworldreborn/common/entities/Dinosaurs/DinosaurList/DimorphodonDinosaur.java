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
public class DimorphodonDinosaur extends Dinosaur
{
    public static final double SPEED = 0.3F;
    public DimorphodonDinosaur()
    {
        super();

        this.setName("Dimorphodon");
        this.setDinosaurType(DinosaurType.AGGRESSIVE);
        this.setDinosaurClass(DimorphodonEntity.class);
        this.setTimePeriod(TimePeriod.JURASSIC);
        this.setEggColorMale(0xB2AC94, 0x636644);
        this.setEggColorFemale(0xBDB4A9, 0x726B57);
        this.setHealth(6, 12);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setStrength(1, 5);
        this.setMaximumAge(fromDays(35));
        this.setAttackBias(80);
        this.setEyeHeight(0.25F, 0.7F);
        this.setSizeX(0.25F, 0.5F);
        this.setSizeY(0.25F, 0.75F);
        this.setStorage(9);
        this.setDiet((Diet.PCARNIVORE.get()));
        this.setBones("leg_bones", "neck", "ribs_and_spine", "shoulder_blade", "skull", "tail_vertebrae", "teeth", "wing_bones");
        this.setHeadCubeName("Head");
        this.setImprintable(true);
        this.setAvianAnimal(true);
        this.setStorage(12);
        this.setScale(0.5F, 0.15F);
        this.setBreeding(false, 2, 6, 80, false, true);
        String[][] recipe = {
                {"", "", "","neck","skull"},
                {"tail_vertebrae", "ribs_and_spine", "shoulder_blade","","teeth"},
                {"", "leg_bones", "wing_bones", "", ""}};
        this.setRecipe(recipe);
        this.enableSkeleton();
        ArrayList<ResourceKey<Biome>> biomeList = new ArrayList<>();
        TagKey<Biome>[] tags = (new TagKey[]{BiomeTags.IS_SAVANNA, BiomeTags.IS_JUNGLE, BiomeTags.IS_MOUNTAIN, Tags.Biomes.IS_PLAINS, BiomeTags.IS_FOREST, BiomeTags.IS_BADLANDS});
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
