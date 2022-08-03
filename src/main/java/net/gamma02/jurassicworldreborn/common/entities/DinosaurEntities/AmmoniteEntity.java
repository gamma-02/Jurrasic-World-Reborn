package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import mod.reborn.server.entity.SwimmingDinosaurEntity;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.world.level.Level;

public class AmmoniteEntity extends SwimmingDinosaurEntity {

    public AmmoniteEntity (Level world)
    {
        super(world);
        this.target(EntitySquid.class);
    }
}
