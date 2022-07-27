package net.gamma02.jurassicworldreborn.common.entities.EntityUtils.ai;

import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.util.BlockPosUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.*;
import java.util.function.Predicate;

public class Herd implements Iterable<DinosaurEntity> {
    public Set<DinosaurEntity> members = new HashSet<>();
    public DinosaurEntity leader;

    private Vec3 center;

    private float moveX;
    private float moveZ;

    public State state = State.IDLE;
    public int stateTicks;

    private Random random = new Random();

    public Set<LivingEntity> enemies = new HashSet<>();

    public boolean fleeing;

    private Dinosaur herdType;

    private int nextMemberCheck;
    private int failedPathTicks;

    public Herd(DinosaurEntity leader) {
        this.herdType = leader.getDinosaur();
        this.members.add(leader);
        this.leader = leader;
        this.resetStateTicks();
    }

    public void update() {
        if (this.leader == null || this.leader.isCarcass() || this.leader.isDeadOrDying()) {
            this.updateLeader();
        }

        if (this.stateTicks > 0 && this.failedPathTicks < this.members.size() * 2) {
            this.stateTicks--;
        } else {
            if (this.herdType.shouldRandomlyFlock()) {
                this.state = this.state == State.MOVING ? State.IDLE : State.MOVING;
            } else {
                this.state = State.IDLE;
            }

            this.resetStateTicks();
            this.enemies.clear();
            this.fleeing = false;
        }

        if (this.leader != null) {
            if (this.leader.shouldSleep()) {
                this.state = State.IDLE;
                this.resetStateTicks();
            }

            this.center = this.getCenterPosition();

            if (this.enemies.size() > 0) {
                if (this.fleeing) {
                    this.state = State.MOVING;

                    float angle = 0.0F;

                    for (LivingEntity attacker : this.enemies) {
                        angle += Mth.atan2(this.center.z - attacker.getZ(), this.center.x - attacker.getX());
                    }

                    angle /= this.enemies.size();

                    this.moveX = -Mth.cos(angle);
                    this.moveZ = Mth.sin(angle);

                    this.normalizeMovement();
                } else {
                    this.state = State.IDLE;
                }
            } else {
                this.fleeing = false;
            }

            List<DinosaurEntity> remove = new LinkedList<>();

            for (DinosaurEntity entity : this) {
                if (entity.distanceToSqr(this.center.x, this.center.y, this.center.z) > 2048) {
                    remove.add(entity);
                }
            }

            for (DinosaurEntity entity : remove) {
                this.splitHerd(entity);

                if (entity == this.leader) {
                    this.updateLeader();
                }
            }

            if (this.leader == null) {
                return;
            }

            boolean attemptedPath = false;
            int failedPaths = 0;

            for (DinosaurEntity entity : this) {
                if (this.enemies.isEmpty() || this.fleeing) {
                    if (!(entity.getMetabolism().isHungry() || entity.getMetabolism().isThirsty()) && !entity.isImmobile() && !entity.isInWater() && (this.fleeing || entity.getNavigation().isDone()) && (this.state == State.MOVING || this.random.nextInt(50) == 0)) {
                        float entityMoveX = this.moveX * 8.0F;
                        float entityMoveZ = this.moveZ * 8.0F;

                        float centerDistance = (float) Math.abs(entity.distanceToSqr(this.center.x, entity.getY(), this.center.z));

                        if (this.fleeing) {
                            centerDistance *= 4.0F;
                        }

                        if (centerDistance > 0) {
                            entityMoveX += (this.center.x - entity.getX()) / centerDistance;
                            entityMoveZ += (this.center.z - entity.getZ()) / centerDistance;
                        }

                        for (DinosaurEntity other : this) {
                            if (other != entity) {
                                /*TODO:make sure works --- legacy todo, no clue if it works lol*/ float distance = (float) entity.distanceToSqr(other);

                                float separation = (entity.getBbWidth() * 1.5F) + 1.5F;

                                if (distance < separation) {
                                    float scale = distance / separation;
                                    entityMoveX += (entity.getX() - other.getX()) / scale;
                                    entityMoveZ += (entity.getZ() - other.getZ()) / scale;
                                }
                            }
                        }

                        double navigateX = entity.getX() + entityMoveX;
                        double navigateZ = entity.getZ() + entityMoveZ;

                        Dinosaur dinosaur = entity.getDinosaur();
                        double speed = this.state == State.IDLE ? 0.8 : dinosaur.getFlockSpeed();

                        if (this.fleeing) {
                            if (dinosaur.getAttackSpeed() > speed) {
                                speed = dinosaur.getAttackSpeed();
                            }
                        }

                        if (entity.disableHerdingTicks <= 0 && entity.getAttackTarget() == null && (this.members.size() > 1 || this.fleeing)) {
                            BlockPos navigatePos = new BlockPos(navigateX, entity.level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mth.floor(navigateX), Mth.floor(navigateZ)) +1, navigateZ);
                            if (entity.getNavigation().getPath() != null && !entity.getNavigation().getPath().isDone()) {
                                Node finalPoint = entity.getNavigation().getPath().getEndNode();
                                if (navigatePos.distToCenterSqr(finalPoint.x, finalPoint.y, finalPoint.z) < 25) {
                                    continue;
                                }
                            }
                            attemptedPath = true;
                            if (entity.distanceToSqr(BlockPosUtil.blockPosToVec(navigatePos)) > 16 && !entity.isImmobile()) {
                                boolean canMove = entity.getNavigation().moveTo(navigatePos.getX(), navigatePos.getY(), navigatePos.getZ(), speed);
                                if (!canMove) {
                                    failedPaths++;
                                }
                            }
                        }
                    }
                } else if (!this.fleeing && (entity.getAttackTarget() == null || this.random.nextInt(20) == 0) && this.enemies.size() > 0) {
                    if (entity.getAgePercentage() > 50) {
                        int index = this.random.nextInt(this.enemies.size());
                        Iterator<LivingEntity> enemyIterator = this.enemies.iterator();
                        for (int i = 0; i < index; i++) {
                            enemyIterator.next();
                        }
                        entity.setTarget(enemyIterator.next());
                    }
                }
            }

            if (attemptedPath) {
                if (failedPaths > this.members.size() / 4) {
                    this.moveX = -this.moveX + (this.random.nextFloat() - 0.5F) * 0.1F;
                    this.moveZ = -this.moveZ + (this.random.nextFloat() - 0.5F) * 0.1F;
                    this.failedPathTicks++;
                    this.normalizeMovement();
                } else if (this.failedPathTicks > 0) {
                    this.failedPathTicks--;
                }
            }

            List<LivingEntity> invalidEnemies = new LinkedList<>();

            for (LivingEntity enemy : this.enemies) {
                if (enemy.isDeadOrDying() || (enemy instanceof DinosaurEntity && ((DinosaurEntity) enemy).isCarcass()) || (enemy instanceof Player pen && (pen).isCreative()) || enemy.distanceToSqr(this.center.x, this.center.y, this.center.z) > 1024 || this.members.contains(enemy)) {
                    invalidEnemies.add(enemy);
                }
            }

            this.enemies.removeAll(invalidEnemies);

            if (this.fleeing && this.enemies.size() == 0) {
                this.fleeing = false;
                this.state = State.IDLE;
            }

            if (this.state == State.IDLE) {
                this.moveX = 0.0F;
                this.moveZ = 0.0F;
            } else {
                this.moveX += (this.random.nextFloat() - 0.5F) * 0.1F;
                this.moveZ += (this.random.nextFloat() - 0.5F) * 0.1F;

                this.normalizeMovement();
            }

            this.refreshMembers();
        }
    }

    private void splitHerd(DinosaurEntity entity) {
        this.members.remove(entity);

        Herd newHerd = new Herd(entity);
        newHerd.fleeing = this.fleeing;
        newHerd.state = this.state;
        newHerd.enemies = new HashSet<>(this.enemies);
        entity.herd = newHerd;
    }

    private void resetStateTicks() {
        this.stateTicks = this.random.nextInt(this.state == State.MOVING ? 2000 : 4000) + 1000;
        this.failedPathTicks = 0;
    }

    public void refreshMembers() {
        List<DinosaurEntity> remove = new LinkedList<>();

        for (DinosaurEntity entity : this) {
            if (!entity.isAlive() || entity.getMetabolism().isStarving() || entity.getMetabolism().isDehydrated()) {
                remove.add(entity);
            }
        }

        this.members.removeAll(remove);

        if (this.leader.tickCount > this.nextMemberCheck) {
            this.nextMemberCheck = this.leader.tickCount + 20 + this.random.nextInt(20);

            AABB searchBounds = new AABB(this.center.x - 16, this.center.y - 5, this.center.z - 16, this.center.x + 16, this.center.y + 5, this.center.z + 16);

            List<Herd> otherHerds = new LinkedList<>();

            for (Entity e : this.leader.level.getEntities((Entity) null, searchBounds, isDino)) {
                if(e instanceof DinosaurEntity entity) {
                    if (!entity.isCarcass() && !entity.isDeadOrDying() && !(entity.getMetabolism().isStarving() || entity.getMetabolism().isDehydrated())) {
                        Herd otherHerd = entity.herd;
                        if (otherHerd == null || otherHerd.members.size() == 1) {
                            if (this.size() >= this.herdType.getMaxHerdSize()) {
//                                if (this.leader != null && GameRuleHandler.KILL_HERD_OUTCAST.getBoolean(this.leader.world) && this.herdType.getDinosaurType() == Dinosaur.DinosaurType.AGGRESSIVE && !this.enemies.contains(entity)) {
//                                    this.enemies.add(entity);
//                                } TODO: gamerules
                                return;
                            }
                            this.addMember(entity);
                        } else if (otherHerd != this && !otherHerds.contains(otherHerd)) {
                            otherHerds.add(otherHerd);
                        }
                    }
                }
            }

            for (Herd otherHerd : otherHerds) {
                int originalSize = this.size();

                if (otherHerd.size() <= originalSize && otherHerd.size() + originalSize < this.herdType.getMaxHerdSize()) {
                    for (DinosaurEntity member : otherHerd) {
                        this.members.add(member);
                        member.herd = this;
                    }

                    this.enemies.addAll(otherHerd.enemies);

                    this.fleeing |= otherHerd.fleeing;

                    otherHerd.disband();
                } else if (originalSize + 1 > this.herdType.getMaxHerdSize() && this.leader != null) {
//                    if (GameRuleHandler.KILL_HERD_OUTCAST.getBoolean(this.leader.world) && this.herdType.getDinosaurType() == Dinosaur.DinosaurType.AGGRESSIVE) {
//                        for (DinosaurEntity entity : otherHerd)
//                            if (!this.enemies.contains(otherHerd)) {
//                                this.enemies.add(entity);
//                            } else {
//                                return;
//                            }
//                    }todo: more gamerules
                }
            }
        }
    }

    public void updateLeader() {
        if (this.members.size() > 0) {
            this.leader = this.members.iterator().next();
        } else {
            this.leader = null;
        }
    }

    public Vec3 getCenterPosition() {
        if (this.members.size() == 1) {
            return this.leader.position();
        }

        double x = 0.0;
        double z = 0.0;

        int count = 0;

        for (DinosaurEntity member : this.members) {
            if (!member.isCarcass() && !member.isInWater()) {
                x += member.getX();
                z += member.getZ();

                count++;
            }
        }

        if (count > 0) {
            x /= count;
            z /= count;
        } else {
            return this.leader.position();
        }

        return new Vec3(x, this.leader.level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int)Math.round(x), (int)Math.round(z)), z);
    }

    public void addMember(DinosaurEntity entity) {
        Herd oldHerd = entity.herd;

        if (oldHerd != null) {
            oldHerd.members.remove(entity);

            this.enemies.addAll(oldHerd.enemies);
            this.fleeing |= oldHerd.fleeing;

            if (oldHerd.leader == entity) {
                oldHerd.updateLeader();
            }
        }

        entity.herd = this;
        this.members.add(entity);
    }

    public void disband() {
        this.leader = null;
        this.members.clear();
    }

    public int size() {
        return this.members.size();
    }

    @Override
    public Iterator<DinosaurEntity> iterator() {
        return this.members.iterator();
    }

    public void normalizeMovement() {
        float length = (float) Math.sqrt(Math.pow(this.moveX, 2) + Math.pow(this.moveZ, 2));
        this.moveX = this.moveX / length;
        this.moveZ = this.moveZ / length;
    }

    public boolean shouldDefend(List<LivingEntity> entities) {
        return this.getScore(this) + (this.herdType.getAttackBias() * this.members.size()) > this.getScore(entities);
    }

    public double getScore(Iterable<? extends LivingEntity> entities) {
        double score = 0.0F;

        for (LivingEntity entity : entities) {
            if (entity != null && entity.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
                score += entity.getHealth() * entity.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue();
            }
        }

        return score;
    }

    public boolean isBusy() {
        return this.fleeing || this.state == State.MOVING || !this.enemies.isEmpty();
    }

    public enum State {
        MOVING,
        IDLE
    }
    private Predicate<Entity> isDino = (entity -> entity instanceof DinosaurEntity);
        
    
}
