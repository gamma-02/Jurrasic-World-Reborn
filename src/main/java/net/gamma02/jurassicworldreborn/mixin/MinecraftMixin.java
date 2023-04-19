package net.gamma02.jurassicworldreborn.mixin;

import net.gamma02.jurassicworldreborn.client.JurassicClient;
import net.gamma02.jurassicworldreborn.client.render.block.DisplayBlockRendererWithoutLevel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DisplayBlockRendererWithoutLevel.class)
public class MinecraftMixin {
    private static boolean executed = false;
    @Inject(method="<init>", at = @At("RETURN"))
    private static void initDisplayBlockRendererMixin(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet, CallbackInfo ci){
        if(!executed) {
            JurassicClient.displayBlockRendererWithoutLevel = new DisplayBlockRendererWithoutLevel(pBlockEntityRenderDispatcher, pEntityModelSet);
            executed = true;
        }

    }
}
