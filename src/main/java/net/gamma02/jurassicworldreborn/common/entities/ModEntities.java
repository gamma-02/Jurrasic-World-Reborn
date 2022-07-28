package net.gamma02.jurassicworldreborn.common.entities;

import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Bone;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurList.TitanisDinosaur;

import java.util.HashMap;

public class ModEntities {
    public static HashMap<Dinosaur, Bone.BoneGroup> boneMap = new HashMap<>();

    public static void init(){
        TitanisDinosaur test = new TitanisDinosaur();
        boneMap.put(test, new Bone.BoneGroup(test).addBones(Bone.PELVIS, Bone.ARM_BONES, Bone.FOOT_BONES, Bone.CLAW, Bone.LEG_BONES, Bone.NECK_VERTEBRAE, Bone.RIBCAGE, Bone.SHOULDER, Bone.SKULL, Bone.TAIL_VERTEBRAE, Bone.TOOTH));
    }
}
