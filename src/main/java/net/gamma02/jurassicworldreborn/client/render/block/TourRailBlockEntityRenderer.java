package net.gamma02.jurassicworldreborn.client.render.block;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.gamma02.jurassicworldreborn.common.blocks.parkBlocks.TourRailBlock;
import net.gamma02.jurassicworldreborn.common.blocks.parkBlocks.TourRailBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.parkBlocks.TourRailModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
import software.bernie.geckolib3.util.EModelRenderCycle;

public class TourRailBlockEntityRenderer extends GeoBlockRenderer<TourRailBlockEntity> {
    public TourRailBlockEntityRenderer(BlockEntityRendererProvider.Context rendererProvider) {
        super(rendererProvider, new TourRailModel());
    }


    @Override
    public void render(TourRailBlockEntity tile, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        TourRailBlock.EnumRailDirection railDirection = tile.getDirection();



        GeoModel model = this.modelProvider.getModel(this.modelProvider.getModelResource(tile));

        //noinspection removal - I damn well know that I will have to rework this now leave me alone
        this.modelProvider.setLivingAnimations(tile, this.getInstanceId(tile));

        this.dispatchedMat = poseStack.last().pose().copy();

        this.setCurrentModelRenderCycle(EModelRenderCycle.INITIAL);

        poseStack.pushPose();

        //here be dragons: Weird Ass Constants
        if(railDirection.isAscending()){

            Vector3f scale = switch (railDirection.getFacing()){
                case EAST, WEST -> new Vector3f(0.7f, 1f, 1f);
                case SOUTH, NORTH -> new Vector3f(1f, 1f, 0.7f);
                default -> new Vector3f(1f, 1f, 1f);
            };

            poseStack.scale(scale.x(), scale.y(), scale.z());


            double correction = 0.15;
            double negativeCorrection = 0.125;
            if(railDirection.getFacing() == Direction.WEST)
                poseStack.translate(correction + negativeCorrection, 0, 0);
            if(railDirection.getFacing() == Direction.NORTH)
                poseStack.translate(0, 0, correction + negativeCorrection);
            if(railDirection.getFacing() == Direction.EAST)
                poseStack.translate(correction, 0, 0);
            if(railDirection.getFacing() == Direction.SOUTH)
                poseStack.translate(0, 0, correction);
        }

        poseStack.translate(0.0, 0.01, 0.0);

        poseStack.translate(0.5, 0.0, 0.5);

        poseStack.mulPose(Vector3f.YP.rotationDegrees(((float) railDirection.rotation)));//rotate the rail in the right direction -- I needed access to the TourRailBlockTileEntity which is why I copied from GeckoLib but I would have had to anyway so

        RenderSystem.setShaderTexture(0, this.getTextureLocation(tile));
        Color renderColor = this.getRenderColor(tile, partialTick, poseStack, bufferSource, (VertexConsumer)null, packedLight);
        RenderType renderType = this.getRenderType(tile, partialTick, poseStack, bufferSource, (VertexConsumer)null, packedLight, this.getTextureLocation(tile));
        this.render(model, tile, partialTick, renderType, poseStack, bufferSource, (VertexConsumer)null, packedLight, OverlayTexture.NO_OVERLAY, (float)renderColor.getRed() / 255.0F, (float)renderColor.getGreen() / 255.0F, (float)renderColor.getBlue() / 255.0F, (float)renderColor.getAlpha() / 255.0F);

        poseStack.popPose();
    }
}
