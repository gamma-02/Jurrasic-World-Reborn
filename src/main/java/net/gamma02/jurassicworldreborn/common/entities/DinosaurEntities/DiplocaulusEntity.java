package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import net.gamma02.jurassicworldreborn.common.entities.SwimmingDinosaurEntity;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.level.Level;

public class DiplocaulusEntity extends SwimmingDinosaurEntity {

    public DiplocaulusEntity(Level world) {
        super(world);
        this.target(AlvarezsaurusEntity.class, BeelzebufoEntity.class, Squid.class, CompsognathusEntity.class, LeptictidiumEntity.class);
    }
}
