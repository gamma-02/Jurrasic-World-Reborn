package net.gamma02.jurassicworldreborn.client.render.block;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNAExtractor.DNAExtractorBlockEntity;
import net.gamma02.jurassicworldreborn.common.util.block.ModdedModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class DNAExtractorRenderer extends GeoBlockRenderer<DNAExtractorBlockEntity> implements BlockEntityRendererProvider<BlockEntity> {
    public DNAExtractorRenderer(BlockEntityRendererProvider.Context ctx) {
        super(ctx, new ModdedModel<>(Jurassicworldreborn.resource("geo/dna_extractor.geo.json"), Jurassicworldreborn.resource("textures/blocks/dna_extractor.png"), Jurassicworldreborn.resource("animations/dna_extractor.animation.json")));
    }


    @Override
    public @NotNull BlockEntityRenderer<BlockEntity> create(@NotNull Context pContext) {
        return new DNAExtractorRenderer(pContext);
    }
}
