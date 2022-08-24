package net.gamma02.jurassicworldreborn.common.entities;

import com.github.alexthe666.citadel.animation.LegSolver;

public final class LegSolverBiped extends LegSolver {
    public final Leg left, right;

    public LegSolverBiped(float forward, float side, float range) {
        super(new Leg(forward, side, range, false), new Leg(forward, -side, range, false));
        this.left = this.legs[0];
        this.right = this.legs[1];
    }
}
