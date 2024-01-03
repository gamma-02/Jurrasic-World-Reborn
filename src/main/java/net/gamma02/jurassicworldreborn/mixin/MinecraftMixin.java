package net.gamma02.jurassicworldreborn.mixin;

import net.gamma02.jurassicworldreborn.client.render.RenderingHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.event.IModBusEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Redirect(method = "<init>", at = @At(value = "INVOKE", ordinal = 3, target = "Lnet/minecraftforge/fml/ModLoader;postEvent(Lnet/minecraftforge/eventbus/api/Event;)V"))
    public <T extends Event & IModBusEvent> void mm(ModLoader instance, T e){

        instance.postEvent(e);
        if(e instanceof EntityRenderersEvent.RegisterRenderers evt){
            RenderingHandler.registerEntityRendersEvent(evt);
        }
    }
}
