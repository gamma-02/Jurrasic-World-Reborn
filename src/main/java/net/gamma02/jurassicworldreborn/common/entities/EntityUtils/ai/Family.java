package net.gamma02.jurassicworldreborn.common.entities.EntityUtils.ai;

import com.mojang.math.Constants;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurHandler;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.GrowthStage;
import net.gamma02.jurassicworldreborn.common.util.BlockPosUtil;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityLookup;

import java.util.*;

public class Family {
    private UUID head;
    private final Set<UUID> parents = new HashSet<>();
    private final Set<UUID> children = new HashSet<>();
    private BlockPos home;
    private int stayHome;

    public Family(UUID... parents) {
        this.head = parents[0];
        Collections.addAll(this.parents, parents);
    }

    public Family(Set<UUID> parents, Set<UUID> children, BlockPos home) {
        this.parents.addAll(parents);
        this.children.addAll(children);
        this.home = home;
    }

    public boolean update(DinosaurEntity entity) {
        if (this.home == null || entity.distanceToSqr(BlockPosUtil.blockPosToVec(this.home)) > 4096) {
            this.home = BlockPosUtil.vecToBlockPos(entity.position());
        }
        Level world = entity.level;
        double centerX = 0.0;
        double centerZ = 0.0;
        Set<UUID> remove = new HashSet<>();
        Set<DinosaurEntity> members = new HashSet<>();
        for (UUID parent : this.parents) {
            DinosaurEntity parentEntity = this.get(world, parent);
            if (parentEntity == null || parentEntity.isDeadOrDying() || parentEntity.isCarcass()) {
                remove.add(parent);
            } else {
                centerX += parentEntity.getX();
                centerZ += parentEntity.getZ();
                members.add(parentEntity);
                parentEntity.family = this;
            }
        }
        for (UUID child : this.children) {
            DinosaurEntity childEntity = this.get(world, child);
            if (childEntity == null || childEntity.isDeadOrDying() || childEntity.isCarcass() || childEntity.getAgePercentage() > 50) {
                remove.add(child);
            } else {
                members.add(childEntity);
                childEntity.family = this;
            }
        }
        this.parents.removeAll(remove);
        this.children.removeAll(remove);
        if (this.parents.isEmpty()) {
            return true;
        }
        if ((remove.size() > 0 && !this.parents.contains(this.head)) || this.head == null) {
            this.head = this.parents.iterator().next();
        }
        centerX /= this.parents.size();
        centerZ /= this.parents.size();
        if (this.stayHome > 0) {
            this.stayHome--;
            centerX = this.home.getX();
            centerZ = this.home.getZ();
        } else {
            centerX = (this.home.getX() / 2) + (centerX / 2);
            centerZ = (this.home.getZ() / 2) + (centerZ / 2);
        }
        double centerDistance = entity.distanceToSqr(centerX, entity.getY(), centerZ);
        RandomSource random = entity.getRandom();
        if (random.nextDouble() * centerDistance > 128) {
            for (DinosaurEntity member : members) {
                if (member.getAttackTarget() == null && member.getNavigation().isDone()) {
                    int travelX = (int) (centerX + random.nextInt(4) - 2);
                    int travelZ = (int) (centerZ + random.nextInt(4) - 2);
                    int travelY = world.getHeight();
                    member.getNavigation().moveTo(travelX, travelY, travelZ, 0.8);
                }
            }
        }
        if (entity.getRandom().nextInt(50) == 0 && (entity.getDinosaur().shouldBreedAroundOffspring() || this.children.isEmpty())) {
            DinosaurEntity father = null;
            DinosaurEntity mother = null;
            for (DinosaurEntity member : members) {
                if (this.parents.contains(member.getUUID())) {
                    if (!member.shouldSleep() && member.getBreedCooldown() <= 0 && !member.isBreeding() && member.getHealth() >= member.getMaxHealth() && member.getGrowthStage() == GrowthStage.ADULT) {
                        if (member.isMale()) {
                            father = member;
                        } else {
                            mother = member;
                        }
                    }
                }
            }
            if (father != null && mother != null) {
                if (father.distanceToSqr(mother) < 128) {
                    father.getNavigation().moveTo(mother, 1.0);
                    mother.getNavigation().moveTo(father, 1.0);
                    father.breed(mother);
                    mother.breed(father);
                }
            }
        }
        return false;
    }

    private DinosaurEntity get(Level world, UUID uuid) {
        if(!world.isClientSide) {
            Entity entity = ((ServerLevel) world).getEntity(uuid);
            if (entity instanceof DinosaurEntity e) {
                return e;
            }
        }
        return null;
    }

    public void addChild(UUID child) {
        this.children.add(child);
    }

    public UUID getHead() {
        return this.head;
    }

    public void writeToNBT(CompoundTag familyTag) {
        ListTag children = new ListTag();
        for (UUID child : this.children) {
            CompoundTag childTag = new CompoundTag();
            childTag.putUUID("UUID", child);
            children.add(childTag);
        }
        familyTag.put("Children", children);
        ListTag parents = new ListTag();
        for (UUID parent : this.parents) {
            CompoundTag parentTag = new CompoundTag();
            parentTag.putUUID("UUID", parent);
            parents.add(parentTag);
        }
        familyTag.put("Parents", parents);
        if (this.home != null) {
            familyTag.putLong("Home", this.home.asLong());
        }
        familyTag.putInt("StayHome", this.stayHome);
    }

    public static Family readFromNBT(CompoundTag familyTag) {
        Set<UUID> children = new HashSet<>();
        Set<UUID> parents = new HashSet<>();
        ListTag parentsList = familyTag.getList("Parents", Tag.TAG_COMPOUND);
        ListTag childrenList = familyTag.getList("Children", Tag.TAG_COMPOUND);
        for (int i = 0; i < parentsList.size(); i++) {
            CompoundTag parentTag = parentsList.getCompound(i);
            parents.add(parentTag.getUUID("UUID"));
        }
        for (int i = 0; i < childrenList.size(); i++) {
            CompoundTag childTag = childrenList.getCompound(i);
            children.add(childTag.getUUID("UUID"));
        }
        BlockPos home = null;
        if (familyTag.contains("Home")) {
            home = BlockPos.of(familyTag.getLong("Home"));
        }
        Family family = new Family(parents, children, home);
        family.stayHome = familyTag.getInt("StayHome");
        return family;
    }

    public void setHome(BlockPos position, int stay) {
        this.home = position;
        this.stayHome = stay;
    }
}
