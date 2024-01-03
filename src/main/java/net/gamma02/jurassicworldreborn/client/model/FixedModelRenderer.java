package net.gamma02.jurassicworldreborn.client.model;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import java.util.Random;


/**
 * HUGE WARNING HERE: Like none of this is meant for modern versions, probably huge errors
 */
public class FixedModelRenderer extends AdvancedModelBox {
    private static final Random RANDOM = new Random();

    private float fixX;
    private float fixY;
    private float fixZ;

    private int displayList;
    private boolean compiled;

    public FixedModelRenderer(AdvancedEntityModel model, String name) {
        super(model, name);
        RANDOM.setSeed((long) name.hashCode() << 16);
        float offsetScale = 0.005F;
        this.fixX = (RANDOM.nextFloat() - 0.5F) * offsetScale;
        this.fixY = (RANDOM.nextFloat() - 0.5F) * offsetScale;
        this.fixZ = (RANDOM.nextFloat() - 0.5F) * offsetScale;
    }

    @Override
    public void render(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);//may or may not work
//        if (this.showModel) {
//            matrixStackIn.pushPose();
//            if (!this.compiled) {
////                this.compileDisplayList(scale);
//            }
//            matrixStackIn.translate(this.offsetX + this.fixX, this.offsetY + this.fixY, this.offsetZ + this.fixZ);
//            matrixStackIn.translate(this.rotationPointX /* scale*/, this.rotationPointY /* scale*/, this.rotationPointZ /* scale*/);
//            this.translateRotate(matrixStackIn);
//
//
//            if (this.scaleX != 1.0F || this.scaleY != 1.0F || this.scaleZ != 1.0F) {
//                matrixStackIn.scale(this.scaleX, this.scaleY, this.scaleZ);
//            }
////            matrixStackIn.callList(this.displayList);
//
//            if (this.childModels != null) {
//                for (BasicModelPart childModel : this.childModels) {
//                    childModel.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
//                }
//            }
//            matrixStackIn.popPose();
//        }

    }

//    private void compileDisplayList(float scale) {
//        this.displayList = ;
//        GL11.glNewList(this.displayList, 4864);
//        BufferBuilder buffer = Tesselator.getInstance().getBuilder();
////        for (TabulaModelRenderUtils.ModelBox box : this.cubeList) {
//////            box.(buffer, scale);
////        }
//        GlStateManager();
//        this.compiled = true;
//    }
}