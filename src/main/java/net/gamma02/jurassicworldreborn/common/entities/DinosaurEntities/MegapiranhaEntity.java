package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import mod.reborn.server.entity.animal.GoatEntity;
import net.gamma02.jurassicworldreborn.common.entities.SwimmingDinosaurEntity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class MegapiranhaEntity extends SwimmingDinosaurEntity
{
    public MegapiranhaEntity(Level world)
    {
        super(world);
        this.target(AlligatorGarEntity.class, CoelacanthEntity.class, BeelzebufoEntity.class, CrassigyrinusEntity.class, DiplocaulusEntity.class, DodoEntity.class, GuanlongEntity.class, LeptictidiumEntity.class, LeaellynasauraEntity.class, HypsilophodonEntity.class, MicroceratusEntity.class, MicroraptorEntity.class, MussaurusEntity.class, OthnieliaEntity.class, OviraptorEntity.class, ProtoceratopsEntity.class, SegisaurusEntity.class, TroodonEntity.class, Player.class
, Squid.class, Player.class
, Animal.class, Villager.class, EntityMob.class, GoatEntity.class);
    }
}
