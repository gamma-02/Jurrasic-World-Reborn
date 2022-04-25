package net.gamma02.jurrasicworldreborn.common.blocks.fossil;

import net.gamma02.jurrasicworldreborn.common.util.TimePeriod;
import net.minecraft.core.BlockPos;

public interface FossilBlock {

    boolean mustBandage();

    //override this if you want to make a bandageable fossil block
    default EncasedFossil getEncasedFossil(){
        return null;
    }

    default TimePeriod getTimePeriod(BlockPos pos){
        return TimePeriod.byYValue(pos.getY());
    }
}
