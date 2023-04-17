package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.LegSolver;
import com.github.alexthe666.citadel.animation.LegSolverQuadruped;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;

public class MamenchisaurusEntity extends DinosaurEntity {

    private static boolean isKingSet = false;
    private boolean isKing = false;
    private MamenchisaurusEntity king = null;
    private int stepCount = 0;

    public LegSolverQuadruped legSolver;

    public MamenchisaurusEntity(Level world, EntityType<MamenchisaurusEntity> type) {
        super(world, type);
        this.addTask(1, new HurtByTargetGoal(this));

    }

    @Override
    protected LegSolver createLegSolver() {
        return this.legSolver = new LegSolverQuadruped(2.4F, 0.7F, 0.9F, 0.4F, 1.0F);
    }

    @Override
    public void aiStep() {
        double distance2 = 18.0D;
        Entity entityFound2 = null;
        double d4 = -1.0D;
//        for (Entity currE : this.level.loadedEntityList) { //for goodness' sake how much do you iterate through ALL OF THE EXISTING ENTITIES??????
//            if (currE instanceof MamenchisaurusEntity) {
//                double d5 = currE.getDistanceSq(this.getX(), this.posY, this.posZ);
//                if ((d5 < distance2 * distance2) && (d4 == -1.0D || d5 < d4)) {
//                    d4 = d5;
//                    entityFound2 = currE;
//                }
//            }
//        }
        for(Entity e : this.level.getEntitiesOfClass(MamenchisaurusEntity.class, this.getBoundingBox().inflate(distance2*distance2))){
            if(e.distanceTo(this) < distance2 * distance2){
                entityFound2 = e;
                break;
            }
        }
        if (entityFound2 != null) {
            if (!isKingSet) {
                king = ((MamenchisaurusEntity) entityFound2);
                king.isKing = true;
                isKingSet = true;
            }
        }
        if(king == null){
            isKingSet = false;
        }
        if(!isKing && isKingSet) {
            moveControl.setWantedPosition(king.getX(), king.getY(), king.getZ(), 1.0D);
        }
        super.aiStep();
    }
    @Override
    public SoundEvent getSoundForAnimation(Animation animation) {
        switch (EntityAnimation.getAnimation(animation)) {
            case SPEAK:
                return SoundHandler.MAMENCHISAURUS_LIVING;
            case CALLING:
                return SoundHandler.MAMENCHISAURUS_CALLING;
            case DYING:
                return SoundHandler.MAMENCHISAURUS_DEATH;
            case BEGGING:
                return SoundHandler.MAMENCHISAURUS_THREAT;
            case INJURED:
                return SoundHandler.MAMENCHISAURUS_HURT;
            case MATING:
                return SoundHandler.MAMENCHISAURUS_MATING;
            case WALKING:
                return SoundHandler.STOMP;
            default:
                return null;
        }
    }
}
