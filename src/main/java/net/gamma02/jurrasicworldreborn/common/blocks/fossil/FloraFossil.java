package net.gamma02.jurrasicworldreborn.common.blocks.fossil;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class FloraFossil extends Block implements FossilBlock {

    public static IntegerProperty VARIANT = IntegerProperty.create("variant", 0, 3);


    public FloraFossil(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(VARIANT, 0));
    }

    @Override
    public boolean mustBandage() {
        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(VARIANT);
    }
}
