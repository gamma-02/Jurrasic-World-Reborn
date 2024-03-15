package net.gamma02.jurassicworldreborn.common.util;

import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.minecraft.world.item.Item;

import javax.annotation.Nullable;

public class ItemsUtil {

    public static Item getFreshDinosaurBone(Dinosaur dino, String bone){
        return ModItems.FRESH_BONES.get(dino).get(bone).get();
    }

    @Nullable
    public static Item getFossilDinosaurBone(Dinosaur dino, String bone){
        if(dino.isHybrid)
            return null;
        return ModItems.BONES.get(dino).get(bone).get();
    }

    public static Item getMeatForDinosaur(Dinosaur dino){
        return ModItems.MEATS.get(dino).get();
    }

    public static Item getSteakForDinosaur(Dinosaur dino){
        return ModItems.STEAKS.get(dino).get();
    }
}
