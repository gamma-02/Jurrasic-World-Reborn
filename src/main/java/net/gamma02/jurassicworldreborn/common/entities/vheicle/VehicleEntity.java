package net.gamma02.jurassicworldreborn.common.entities.vheicle;

public class VehicleEntity {//todo

    public enum Speed {

        // The modifiers ARE hardcoded. If you want to change them, please talk to me
        // first. The tyre mark code relies on the modifiers being how they are
        SLOW(0.5f), MEDIUM(1f), FAST(2f);

        public final float modifier;

        Speed(float modifier) {
            this.modifier = modifier;
        }
    }

}
