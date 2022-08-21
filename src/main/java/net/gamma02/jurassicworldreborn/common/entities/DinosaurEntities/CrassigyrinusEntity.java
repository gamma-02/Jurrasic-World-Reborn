package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import net.gamma02.jurassicworldreborn.common.entities.SwimmingDinosaurEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

public class CrassigyrinusEntity extends SwimmingDinosaurEntity {

    public CrassigyrinusEntity(Level world) {
        super(world);
        this.target(AlligatorGarEntity.class, BeelzebufoEntity.class, DiplocaulusEntity.class, MegapiranhaEntity.class, Animal.class);
    }
}
