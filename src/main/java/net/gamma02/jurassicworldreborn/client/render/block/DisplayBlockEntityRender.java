package net.gamma02.jurassicworldreborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.gamma02.jurassicworldreborn.common.blocks.entities.ActionFigureBlockEntity;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public class DisplayBlockEntityRender implements BlockEntityRenderer<ActionFigureBlockEntity> {


    @Override
    public void render(ActionFigureBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, @NotNull MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        pPoseStack.translate(pBlockEntity.getBlockPos().getX(), pBlockEntity.getBlockPos().getY(), pBlockEntity.getBlockPos().getZ());

//        pPoseStack.mulPose(Quaternion.fromXYZDegrees(new Vector3f(0.0f, (float)pBlockEntity.getRot(), 0.0f)));
        pPoseStack.mulPose(Vector3f.YP.rotationDegrees(pBlockEntity.getRot()));


        float scale = pBlockEntity.getEntity().isSkeleton() ? 1.0f : 0.15f;
        pPoseStack.scale(scale, scale, scale);

        DinosaurEntity entity = pBlockEntity.getEntity();
        if (entity != null) {
            Minecraft.getInstance().levelRenderer.renderEntity(entity, 0.0D, 0.0D, 0.0D, 0.0F, pPoseStack, pBufferSource);
        }
        pPoseStack.popPose();

    }
}
