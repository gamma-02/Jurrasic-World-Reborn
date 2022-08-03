package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import mod.reborn.server.entity.SwimmingDinosaurEntity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.world.level.Level;

public class CrassigyrinusEntity extends SwimmingDinosaurEntity {

    public CrassigyrinusEntity(Level world) {
        super(world);
        this.target(AlligatorGarEntity.class, BeelzebufoEntity.class, DiplocaulusEntity.class, MegapiranhaEntity.class, EntityAnimal.class);
    }
}
