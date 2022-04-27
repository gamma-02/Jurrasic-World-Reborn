package net.gamma02.jurassicworldreborn.common.worldgen.tree.petrified;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class PetrifiedTreeConfig implements FeatureConfiguration {

    public static final Codec<PetrifiedTreeConfig> CODEC = RecordCodecBuilder.create( (instance) ->{
        return instance.group(Codec.intRange(0, 20).fieldOf("size").forGetter((o) ->{
                return o.size;
                }), Codec.floatRange(0, 1).fieldOf("chance").forGetter((o) ->{
                    return o.chance;
        })).apply(instance, PetrifiedTreeConfig::new);
    });



    public final int size;
    public final float chance; // Chance of petrified tree feature generating on an attept

    public PetrifiedTreeConfig( int Size, float chance) {
        this.chance = chance;
        this.size = Size;
    }

}
