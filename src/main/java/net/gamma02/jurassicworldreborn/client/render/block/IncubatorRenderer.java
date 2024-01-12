package net.gamma02.jurassicworldreborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.common.blocks.entities.incubator.IncubatorBlockEntity;
import net.gamma02.jurassicworldreborn.common.util.block.ModdedModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class IncubatorRenderer extends GeoBlockRenderer<IncubatorBlockEntity> implements BlockEntityRendererProvider{
    public IncubatorRenderer(BlockEntityRendererProvider.Context rendererProvider) {
        super(rendererProvider, new ModdedModel<>(Jurassicworldreborn.resource("geo/incubator.geo.json"), Jurassicworldreborn.resource("textures/blocks/incubator.png"), Jurassicworldreborn.resource("animations/incubator.animation.json")));
    }

    @Override
    public BlockEntityRenderer create(Context pContext) {
        return new IncubatorRenderer(pContext);
    }

    @Override
    public void render(IncubatorBlockEntity tile, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {



        super.render(tile, partialTick, poseStack, bufferSource, packedLight);
    }
}
