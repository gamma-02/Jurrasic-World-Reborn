package net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurList;

import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.*;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Diet;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Hybrid;
import net.gamma02.jurassicworldreborn.common.util.TimePeriod;
import java.util.ArrayList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;


public class SpinoraptorDinosaur extends Dinosaur implements Hybrid
{
    private ResourceLocation texture;
    public static final double SPEED = 0.45F;

    public SpinoraptorDinosaur()
    {
        super();

        this.setName("Spinoraptor");
        this.setDinosaurType(DinosaurType.AGGRESSIVE);
        this.setDinosaurClass(SpinoraptorEntity.class);
        this.setTimePeriod(TimePeriod.NONE); //nothing. you don't do anything. - gamma_02
        this.setEggColorMale(0xa98b2b, 0x191919);
        this.setEggColorFemale(0xbc972e, 0x6f5b25);
        this.setHealth(8, 60);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setAttackSpeed(1.5);
        this.setStrength(2, 25);
        this.setMaximumAge(fromDays(30));
        this.setEyeHeight(0.55F, 5.4F);
        this.setSizeX(0.2F, 2.8F);
        this.setSizeY(0.2F, 4F);
        this.setStorage(54);
        this.setDiet((Diet.CARNIVORE.get()));
        this.setBones("skull", "tooth", "arm_bones", "claw", "foot_bones", "leg_bones", "neck_vertebrae", "ribcage", "shoulder", "tail_vertebrae", "pelvis");
        this.setHeadCubeName("Head");
        this.setScale(1.4F, 0.15F);
        this.setAttackBias(4000);
        this.setBreeding(false, 0, 0, 999, false, false);
        this.setHybrid();
        this.setMaxHerdSize(4);
        this.setJumpHeight(3);
        this.setCanClimb(true);
        this.setDefendOwner(true);
        this.setImprintable(true);
        String[][] recipe = {
                {"", "", "","neck_vertebrae","skull"},
                {"tail_vertebrae", "pelvis", "ribcage","shoulder","tooth"},
                {"", "", "leg_bones", "arm_bones", "claw"},
                {"", "", "foot_bones", "", ""}};
        this.setRecipe(recipe);
        this.enableSkeleton();

    }

    @Override
    public Class<? extends Dinosaur>[] getDinosaurs() {
        return new Class[]{SpinosaurusDinosaur.class, VelociraptorDinosaur.class};
    }
}
