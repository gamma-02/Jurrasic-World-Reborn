package net.gamma02.jurassicworldreborn.common.items.Bones;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Bone;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DynamicBoneRegistry {

    public static HashMap<Bone.BoneGroup, ArrayList<RegistryObject<BoneItem>>> BoneMap = new HashMap<>();

    public static DeferredRegister<Item> BoneDeferredRegister = DeferredRegister.create(ForgeRegistries.ITEMS, Jurassicworldreborn.modid);

//    public static void addBoneGroup(Bone.BoneGroup group){
//        group.registerToHashMap(BoneMap, BoneDeferredRegister);
//    }

}
