package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import net.gamma02.jurassicworldreborn.common.entities.SwimmingDinosaurEntity;
import mod.reborn.server.entity.ai.WaterLeapAI;
import mod.reborn.server.entity.animal.GoatEntity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class TylosaurusEntity extends SwimmingDinosaurEntity
{
    public TylosaurusEntity(Level world)
    {
        super(world);
        this.target(CoelacanthEntity.class, MegapiranhaEntity.class, Squid.class, Player.class
, Animal.class, Villager.class, EntityMob.class, GoatEntity.class);
        //this.tasks.addTask(0, new WaterLeapAI(this, 12, 1.2F));
    }
}
