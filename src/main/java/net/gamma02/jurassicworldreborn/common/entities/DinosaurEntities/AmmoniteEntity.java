package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import net.gamma02.jurassicworldreborn.common.entities.SwimmingDinosaurEntity;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.level.Level;

public class AmmoniteEntity extends SwimmingDinosaurEntity {

    public AmmoniteEntity (Level world)
    {
        super(world);
        this.target(Squid.class);
    }
}
