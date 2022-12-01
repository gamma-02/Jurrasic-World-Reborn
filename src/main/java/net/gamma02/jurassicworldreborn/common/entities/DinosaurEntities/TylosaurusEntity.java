package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import net.gamma02.jurassicworldreborn.common.entities.SwimmingDinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.ai.WaterLeapAI;
import net.gamma02.jurassicworldreborn.common.entities.animal.GoatEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;

public class TylosaurusEntity extends SwimmingDinosaurEntity
{
    public TylosaurusEntity(Level world, EntityType<TylosaurusEntity> type)
    {
        super(world, type);
        this.target(CoelacanthEntity.class, MegapiranhaEntity.class, Squid.class, Player.class
, Animal.class, Villager.class, Mob.class, GoatEntity.class);
        this.addTask(0, new WaterLeapAI/*WaterYeetAI*/(this, 12, 1.2F));
    }
}
