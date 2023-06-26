package net.gamma02.jurassicworldreborn.common.entities;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.common.entities.animal.CrabEntity;
import net.gamma02.jurassicworldreborn.common.entities.animal.SharkEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;

import static net.gamma02.jurassicworldreborn.common.entities.ModEntities.CRAB_ENTITY_TYPE;
import static net.gamma02.jurassicworldreborn.common.entities.ModEntities.SHARK_ENTITY_TYPE;

@Mod.EventBusSubscriber(modid = Jurassicworldreborn.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventListener {

    @SubscribeEvent
    public static void registerAttributes(final EntityAttributeCreationEvent event){//lord if this works I swear to god.
        HashMap<EntityType<?>, AttributeSupplier.Builder> typeMap = new HashMap<>();
        ModEntities.MOD_ENTITY_TYPES.getEntries().forEach((type) -> {//what the fuck is this abomination that I have created - gamma
            if(type.get().getBaseClass().isAssignableFrom(LivingEntity.class) && !(type.get() == CRAB_ENTITY_TYPE.get() || type.get() == SHARK_ENTITY_TYPE.get())) {
                AttributeSupplier.Builder supplier = DinosaurEntity.createAttributes();
//                try {
//                    ;if(!(type.get() == CRAB_ENTITY_TYPE.get() || type.get() == SHARK_ENTITY_TYPE.get())) {
//                        event.put(type, DinosaurEntity.createAttributes().build());
//                    }
//                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
//                    throw new RuntimeException(e);
//                }
                if (supplier != null) {
                    event.put(((EntityType<? extends LivingEntity>) type.get()), supplier.build());
                }
            }
        });
        event.put(CRAB_ENTITY_TYPE.get(), CrabEntity.createAttributes().build());
        event.put(ModEntities.SHARK_ENTITY_TYPE.get(), SharkEntity.createAttributes().build());

    }
}
