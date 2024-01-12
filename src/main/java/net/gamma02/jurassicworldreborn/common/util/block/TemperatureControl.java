package net.gamma02.jurassicworldreborn.common.util.block;


import net.minecraft.world.Container;

public interface TemperatureControl extends Container {
    void setTemperature(int index, int value);

    int getTemperature(int index);

    int getTemperatureCount();
}
