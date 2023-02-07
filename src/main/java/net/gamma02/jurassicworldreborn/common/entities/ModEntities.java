package net.gamma02.jurassicworldreborn.common.entities;

import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Bone;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurList.TitanisDinosaur;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.function.Supplier;

import static net.gamma02.jurassicworldreborn.Jurassicworldreborn.modid;

public class ModEntities {
    public static HashMap<Dinosaur, Bone.BoneGroup> boneMap = new HashMap<>();

    public static DeferredRegister<EntityType<?>> MOD_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, modid);

    public static RegistryObject<EntityType<VenomEntity>> VenomType = MOD_ENTITY_TYPES.<EntityType<VenomEntity>>register("venom_entity", ((Supplier<EntityType<VenomEntity>>)EntityType.Builder.<VenomEntity>of(VenomEntity::new, MobCategory.MISC).build(null)));


    public static void init(){
//        TitanisDinosaur test = new TitanisDinosaur();
//        boneMap.put(test, new Bone.BoneGroup(test).addBones(Bone.PELVIS, Bone.ARM_BONES, Bone.FOOT_BONES, Bone.CLAW, Bone.LEG_BONES, Bone.NECK_VERTEBRAE, Bone.RIBCAGE, Bone.SHOULDER, Bone.SKULL, Bone.TAIL_VERTEBRAE, Bone.TOOTH));
    }
}
