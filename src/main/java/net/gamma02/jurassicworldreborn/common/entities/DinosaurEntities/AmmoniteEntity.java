package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurHandler;
import net.gamma02.jurassicworldreborn.common.entities.SwimmingDinosaurEntity;import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;

public class AmmoniteEntity extends SwimmingDinosaurEntity {

    public AmmoniteEntity (Level world, EntityType<AmmoniteEntity> type)
    {
        super(world, type, DinosaurHandler.AMMONITE);
        this.target(Squid.class);
    }
}

