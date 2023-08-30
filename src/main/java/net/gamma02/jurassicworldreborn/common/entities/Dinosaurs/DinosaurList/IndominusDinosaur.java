package net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurList;

import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.*;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Diet;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.GrowthStage;
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

public class IndominusDinosaur extends Dinosaur implements Hybrid
{
    private ResourceLocation overlayTexture;

    public static final double SPEED = 0.4F;

    public IndominusDinosaur()
    {
        super();

        this.setName("Indominus");
        this.setDinosaurType(DinosaurType.AGGRESSIVE);
        this.setDinosaurClass(IndominusEntity.class);
        this.setTimePeriod(TimePeriod.NONE); //TODO, it's a hybrid, what do you do here?
        this.setEggColorMale(0x252627, 0x293336);
        this.setEggColorFemale(0xBEBABB, 0x95949A);
        this.setHealth(16, 95);
        this.setSpeed((SPEED -0.05), SPEED);
        this.setStrength(5, 40);
        this.setMaximumAge(fromDays(30));
        this.setAttackSpeed(3);
        this.setEyeHeight(0.55F, 5.4F);
        this.setSizeX(0.2F, 3.5F);
        this.setSizeY(0.4F, 6.0F);
        this.setStorage(54);
        this.setDiet((Diet.CARNIVORE.get()));
        this.setBones("arm_bones", "foot_bones", "leg_bones", "neck_vertebrae", "claw", "pelvis", "ribcage", "shoulder", "skull", "tail_vertebrae", "tooth");
        this.setHeadCubeName("Head");
        this.setScale(3.0F, 0.1F);
        this.setAttackBias(4000);
        this.setBreeding(false, 0, 0, 999, false, false);
        this.setHybrid();
        String[][] recipe = {
                {"", "", "","neck_vertebrae","skull"},
                {"tail_vertebrae", "pelvis", "ribcage","shoulder","tooth"},
                {"", "leg_bones", "", "arm_bones", "claw"},
                {"", "foot_bones", "", "", ""}};
        this.setRecipe(recipe);
        this.enableSkeleton();

        this.overlayTexture = new ResourceLocation(getDinosaurTexture(""));

    }

    @Override
    public Class<? extends Dinosaur>[] getDinosaurs()
    {
        return new Class[] { TyrannosaurusDinosaur.class, VelociraptorDinosaur.class, GiganotosaurusDinosaur.class, RugopsDinosaur.class, MajungasaurusDinosaur.class, CarnotaurusDinosaur.class, TherizinosaurusDinosaur.class };
    }

    public ResourceLocation getCamoTexture(GrowthStage stage)
    {
        return overlayTexture;
    }//where this used - gamma_02
}
