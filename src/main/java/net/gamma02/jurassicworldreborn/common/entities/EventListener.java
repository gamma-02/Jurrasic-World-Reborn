package net.gamma02.jurassicworldreborn.common.entities;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

@Mod.EventBusSubscriber(modid = Jurassicworldreborn.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventListener {

    @SubscribeEvent
    public static void registerAttributes(final EntityAttributeCreationEvent event){//lord if this works I swear to god.
//        HashMap<EntityType<?>, AttributeSupplier.Builder> typeMap = new HashMap<>();
        ModEntities.MOD_ENTITY_TYPES.getEntries().forEach((type) -> {//what the fuck is this abomination that I have created - gamma
            if(type.get().getBaseClass().isAssignableFrom(LivingEntity.class)) {
                AttributeSupplier.Builder supplier;
                try {
                    supplier = ((AttributeSupplier.Builder) ((EntityType<? extends LivingEntity>) type.get()).getBaseClass().getMethod("createAttributes", null).invoke(null, null));
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
                if (supplier != null) {
                    event.put(((EntityType<? extends LivingEntity>) type.get()), supplier.build());
                }
            }
        });
    }
}
