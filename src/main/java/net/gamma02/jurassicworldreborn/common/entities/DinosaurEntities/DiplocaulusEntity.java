package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import mod.reborn.server.entity.SwimmingDinosaurEntity;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.world.level.Level;

public class DiplocaulusEntity extends SwimmingDinosaurEntity {

    public DiplocaulusEntity(Level world) {
        super(world);
        this.target(AlvarezsaurusEntity.class, BeelzebufoEntity.class, EntitySquid.class, CompsognathusEntity.class, LeptictidiumEntity.class);
    }
}
