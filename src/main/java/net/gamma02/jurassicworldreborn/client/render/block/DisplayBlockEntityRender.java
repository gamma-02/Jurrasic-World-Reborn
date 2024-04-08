package net.gamma02.jurassicworldreborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3d;
import com.mojang.math.Vector3f;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.common.blocks.entities.ActionFigureBlockEntity;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurHandler;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.AnimationState;

public class DisplayBlockEntityRender implements BlockEntityRenderer<ActionFigureBlockEntity> {
    public static final int ACTION_FIGURE_VARIANT = 0;

    public DisplayBlockEntityRender(){
        super();
    }


    @Override
    public void render(ActionFigureBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, @NotNull MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
//        pPoseStack.translate(pBlockEntity.getBlockPos().getX(), pBlockEntity.getBlockPos().getY(), pBlockEntity.getBlockPos().getZ());

//        pPoseStack.mulPose(Quaternion.fromXYZDegrees(new Vector3f(0.0f, (float)pBlockEntity.getRot(), 0.0f)));

        if(pBlockEntity.peekEntity() == null) {
            pPoseStack.popPose();
            return;
        }


        pPoseStack.translate(0.5, pBlockEntity.getEntity().getDinosaur().getOffsetY(), 0.5);



        float scale = pBlockEntity.getEntity().isSkeleton() ? 1.0f : 0.15f;
        pPoseStack.scale(scale, scale, scale);

        pPoseStack.mulPose(Vector3f.YP.rotationDegrees(pBlockEntity.getRot()));





        DinosaurEntity entity = pBlockEntity.getEntity();
        if (entity != null) {
            entity.setAnimation(EntityAnimation.IDLE.get());
//            entity.setSkeletonVariant((byte) 0);
//            Entity cam = Minecraft.getInstance();
//            cam.getPos
//            Camera cam = Minecraft.getInstance().gameRenderer.getMainCamera();
//            Vec3 pos = cam.getPosition();
//            float f = Mth.lerp(pPartialTick, entity.yRotO, entity.getYRot());


            Minecraft.getInstance().getEntityRenderDispatcher().render(entity, 0, 0, 0, 0, 0f, pPoseStack, pBufferSource, pPackedLight);
        }
        pPoseStack.popPose();

    }

}
