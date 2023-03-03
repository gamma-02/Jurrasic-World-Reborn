package net.gamma02.jurassicworldreborn.common.blocks.fossil;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class NestFossilBlock extends Block implements EncasedFossil {
    boolean encased = false;
    public NestFossilBlock(boolean encased, Properties p_49795_) {
        super(p_49795_);
        this.encased = encased;
    }

//todo: variant

    @Override
    public boolean mustBandage() {
        return false;
    }

    @Override
    public BlockState getEncasedFossil() {
        return ModBlocks.ENCASED_NEST_FOSSIL.get().defaultBlockState();//todo:variant
    }
}
