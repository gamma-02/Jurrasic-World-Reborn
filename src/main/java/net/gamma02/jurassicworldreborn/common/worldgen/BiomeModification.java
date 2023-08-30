package net.gamma02.jurassicworldreborn.common.worldgen;

import com.mojang.serialization.Codec;
import net.gamma02.jurassicworldreborn.common.CommonRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static net.gamma02.jurassicworldreborn.common.CommonRegistries.*;
import static net.gamma02.jurassicworldreborn.common.CommonRegistries.PLACED_SMALL_PETRIFIED_TREE;

public record BiomeModification(HolderSet<Biome> biomes, Holder<PlacedFeature> feature) implements BiomeModifier {



    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {

        if(phase == Phase.ADD) {
            if (!biome.is(BiomeTags.IS_NETHER) && !biome.is(BiomeTags.IS_END)) {
                builder.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ICE_SHARD_ORE_PLACEMENT);
                builder.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FAUNA_FOSSIL_PLACEMENT);
                builder.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FLORA_FOSSIL_PLACEMENT);
                builder.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, AMBER_ORE_PLACEMENT);
                if (!biome.is(Tags.Biomes.IS_VOID)) {
                    builder.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PLACED_LARGE_PETRIFIED_TREE);
                    builder.getGenerationSettings().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, PLACED_SMALL_PETRIFIED_TREE);
                }


            }
        }
    }

    public Codec<? extends BiomeModifier> codec()
    {
        return FEATURES.get();
    }
}
