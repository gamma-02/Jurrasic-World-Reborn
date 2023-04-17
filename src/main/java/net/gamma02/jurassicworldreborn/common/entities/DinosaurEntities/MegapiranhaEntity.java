package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import net.gamma02.jurassicworldreborn.common.entities.SwimmingDinosaurEntity;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;

public class MegapiranhaEntity extends SwimmingDinosaurEntity
{
    public MegapiranhaEntity(Level world, EntityType<MegapiranhaEntity> type)
    {
        super(world, type);
        this.target(AlligatorGarEntity.class, CoelacanthEntity.class, BeelzebufoEntity.class, CrassigyrinusEntity.class, DiplocaulusEntity.class, DodoEntity.class, GuanlongEntity.class, LeptictidiumEntity.class, LeaellynasauraEntity.class, HypsilophodonEntity.class, MicroceratusEntity.class, MicroraptorEntity.class, MussaurusEntity.class, OthnieliaEntity.class, OviraptorEntity.class, ProtoceratopsEntity.class, SegisaurusEntity.class, TroodonEntity.class, Player.class
, Squid.class, Player.class
, Animal.class, Villager.class, Mob.class, Goat.class);
    }
}
