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

public class ProtoceratopsDinosaur extends Dinosaur
{
    public static final double SPEED = 0.4F;
    public ProtoceratopsDinosaur()
    {
        super();

        this.setName("Protoceratops");
        this.setDinosaurType(DinosaurType.SCARED);
        this.setDinosaurClass(ProtoceratopsEntity.class);
        this.setTimePeriod(TimePeriod.CRETACEOUS);
        this.setEggColorMale(0xFDCEB5, 0xFBC073);
        this.setEggColorFemale(0xEBCC98, 0xAA804E);
        this.setHealth(4, 20);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setStrength(5, 10);
        this.setMaximumAge(fromDays(40));
        this.setEyeHeight(0.35F, 0.85F);
        this.setSizeX(0.15F, 1.0F);
        this.setSizeY(0.2F, 1.25F);
        this.setStorage(9);
        this.setDiet((Diet.HERBIVORE.get()));
        this.setBones("skull", "teeth", "tail_vertebrae", "shoulder", "pelvis", "ribcage", "front_leg_bones", "hind_leg_bones", "shoulder");
        this.setHeadCubeName("Head");
        this.setAttackBias(1);
        this.setScale(1.2F, 0.15F);
        this.setImprintable(true);
        this.setDefendOwner(true);
        this.setBreeding(false, 2, 4, 40, false, true);
        String[][] recipe = {
                {"tail_vertebrae", "pelvis", "ribcage","","skull"},
                {"hind_leg_bones", "hind_leg_bones", "", "shoulder", "teeth"},
                {"", "", "", "front_leg_bones", "front_leg_bones"}};
        this.setRecipe(recipe);
        this.enableSkeleton();
        ArrayList<ResourceKey<Biome>> biomeList = new ArrayList<>();
        Stream<TagKey<Biome>> tags = Arrays.stream(new TagKey[]{ Tags.Biomes.IS_PLAINS, Tags.Biomes.IS_DRY, Tags.Biomes.IS_CONIFEROUS});
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
