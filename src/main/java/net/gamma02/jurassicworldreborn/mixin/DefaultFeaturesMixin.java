package net.gamma02.jurassicworldreborn.mixin;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BiomeDefaultFeatures.class)
public class DefaultFeaturesMixin {

    @Inject(method = "addDefaultOres(Lnet/minecraft/world/level/biome/BiomeGenerationSettings$Builder;Z)V", at = @At(value = "HEAD"))
    private static void addDeafultOresMixin(BiomeGenerationSettings.Builder builder, boolean bool, CallbackInfo ci){
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Jurassicworldreborn.FLORA_FOSSIL_PLACEMENT);
    }
}
