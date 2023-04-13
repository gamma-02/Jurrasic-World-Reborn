package net.gamma02.jurassicworldreborn.client.render.entity.animation.entity;

import mod.reborn.server.dinosaur.Dinosaur;
import mod.reborn.server.entity.DinosaurEntity;
import mod.reborn.server.entity.LegSolverBiped;
import mod.reborn.server.entity.LegSolverQuadruped;


import net.minecraft.util.math.MathHelper;

public final class LegArticulator {
    private LegArticulator() {}

    public static void articulateBiped(DinosaurEntity entity, LegSolverBiped legs, AdvancedModelBox body, AdvancedModelBox leftThigh, AdvancedModelBox leftCalf, AdvancedModelBox rightThigh, AdvancedModelBox rightCalf, float rotThigh, float rotCalf, float delta) {
        float heightLeft = legs.left.getHeight(delta);
        float heightRight = legs.right.getHeight(delta);
        if (heightLeft > 0 || heightRight > 0) {
            float sc = LegArticulator.getScale(entity);
            float avg = LegArticulator.avg(heightLeft, heightRight);
            body.rotationPointY += 16 / sc * avg;
            articulateLegPair(sc, heightLeft, heightRight, avg, 0, leftThigh, leftCalf, rightThigh, rightCalf, rotThigh, rotCalf);
        }
    }

    // front legs must be connected to body
    public static void articulateQuadruped(
        DinosaurEntity entity, LegSolverQuadruped legs, AdvancedModelBox body, AdvancedModelBox neck,
        AdvancedModelBox backLeftThigh, AdvancedModelBox backLeftCalf,
        AdvancedModelBox backRightThigh, AdvancedModelBox backRightCalf,
        AdvancedModelBox frontLeftThigh, AdvancedModelBox frontLeftCalf,
        AdvancedModelBox frontRightThigh, AdvancedModelBox frontRightCalf,
        float rotBackThigh, float rotBackCalf,
        float rotFrontThigh, float rotFrontCalf,
        float delta)
    {
        float heightBackLeft = legs.backLeft.getHeight(delta);
        float heightBackRight = legs.backRight.getHeight(delta);
        float heightFrontLeft = legs.frontLeft.getHeight(delta);
        float heightFrontRight = legs.frontRight.getHeight(delta);
        if (heightBackLeft > 0 || heightBackRight > 0 || heightFrontLeft > 0 || heightFrontRight > 0) {
            float sc = LegArticulator.getScale(entity);
            float backAvg = LegArticulator.avg(heightBackLeft, heightBackRight);
            float frontAvg = LegArticulator.avg(heightFrontLeft, heightFrontRight);
            float bodyLength = Math.abs(avg(legs.backLeft.forward, legs.backRight.forward) - avg(legs.frontLeft.forward, legs.frontRight.forward));
            float tilt = (float) (MathHelper.atan2(bodyLength * sc, backAvg - frontAvg) - Math.PI / 2);
            body.rotationPointY += 16 / sc * backAvg;
            body.rotateAngleX += tilt;
            frontLeftThigh.rotateAngleX -= tilt;
            frontRightThigh.rotateAngleX -= tilt;
            neck.rotateAngleX -= tilt;
            LegArticulator.articulateLegPair(sc, heightBackLeft, heightBackRight, backAvg, 0, backLeftThigh, backLeftCalf, backRightThigh, backRightCalf, rotBackThigh, rotBackCalf);
            LegArticulator.articulateLegPair(sc, heightFrontLeft, heightFrontRight, frontAvg, -frontAvg, frontLeftThigh, frontLeftCalf, frontRightThigh, frontRightCalf, rotFrontThigh, rotFrontCalf);
        }
    }

    private static void articulateLegPair(float sc, float heightLeft, float heightRight, float avg, float offsetY, AdvancedModelBox leftThigh, AdvancedModelBox leftCalf, AdvancedModelBox rightThigh, AdvancedModelBox rightCalf, float rotThigh, float rotCalf) {
        float difLeft = Math.max(0, heightRight - heightLeft);
        float difRight = Math.max(0, heightLeft - heightRight);
        leftThigh.rotationPointY += 16 / sc * (Math.max(heightLeft, avg) + offsetY);
        rightThigh.rotationPointY += 16 / sc * (Math.max(heightRight, avg) + offsetY);
        leftThigh.rotateAngleX -= rotThigh * difLeft;
        leftCalf.rotateAngleX += rotCalf * difLeft;
        rightThigh.rotateAngleX -= rotThigh * difRight;
        rightCalf.rotateAngleX += rotCalf * difRight;
    }

    private static float avg(float a, float b) {
        return (a + b) / 2;
    }

    private static float getScale(DinosaurEntity entity) {
        float scaleModifier = entity.getAttributes().getScaleModifier();
        Dinosaur dino = entity.getDinosaur();
        return (float) entity.interpolate(dino.getScaleInfant(), dino.getScaleAdult()) * scaleModifier;
    }
}
