package net.gamma02.jurassicworldreborn.common.blocks.parkBlocks;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TourRailModel extends AnimatedGeoModel<TourRailBlockEntity> {
    public TourRailModel() {
    }

    @Override
    public ResourceLocation getModelResource(TourRailBlockEntity tourRailBlockEntity) {
        return Jurassicworldreborn.resource("geo/" + tourRailBlockEntity.getDirection().modelName + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TourRailBlockEntity tourRailBlockEntity) {
        return Jurassicworldreborn.resource("textures/blocks/" + tourRailBlockEntity.getDirection().modelName + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(TourRailBlockEntity tourRailBlockEntity) {
        return Jurassicworldreborn.resource("animations/" + tourRailBlockEntity.getDirection().modelName + ".animation.json");
    }
}
