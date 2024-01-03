package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurHandler;
import net.gamma02.jurassicworldreborn.common.entities.SwimmingDinosaurEntity;import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;

public class CrassigyrinusEntity extends SwimmingDinosaurEntity {

    public CrassigyrinusEntity(Level world, EntityType<CrassigyrinusEntity> type) {
        super(world, type, DinosaurHandler.CRASSIGYRINUS);
        this.target(AlligatorGarEntity.class, BeelzebufoEntity.class, DiplocaulusEntity.class, MegapiranhaEntity.class, Animal.class);
    }
}

