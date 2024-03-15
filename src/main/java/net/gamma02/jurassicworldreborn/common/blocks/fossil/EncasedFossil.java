package net.gamma02.jurassicworldreborn.common.blocks.fossil;

public interface EncasedFossil extends FossilBlock{

    @Override
    default boolean mustBandage() {
        return false;
    }
}
