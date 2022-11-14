package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;

public class VelociraptorBlueEntity extends VelociraptorEntity
{
    public VelociraptorBlueEntity(Level world, EntityType type)
    {
        super(world, type);
        this.target(AlvarezsaurusEntity.class, SpinoraptorEntity.class, TitanisEntity.class, SmilodonEntity.class, MegatheriumEntity.class, ArsinoitheriumEntity.class, BeelzebufoEntity.class, CearadactylusEntity.class, ChilesaurusEntity.class, CoelurusEntity.class, CompsognathusEntity.class, DilophosaurusEntity.class, DimorphodonEntity.class, GallimimusEntity.class, ProceratosaurusEntity.class, DodoEntity.class, HypsilophodonEntity.class, Player.class
, LeaellynasauraEntity.class, LeptictidiumEntity.class, MetriacanthosaurusEntity.class, MicroraptorEntity.class, MussaurusEntity.class, OrnithomimusEntity.class, OthnieliaEntity.class, OviraptorEntity.class, ProtoceratopsEntity.class);
        doesEatEggs(true);
    }
}
