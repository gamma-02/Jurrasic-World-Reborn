package net.gamma02.jurassicworldreborn.common.blocks.fossil;

import net.gamma02.jurassicworldreborn.common.blocks.entities.EncasedFaunaFossilBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EncasedFaunaFossilBlock extends Block implements EncasedFossil, EntityBlock {

    public EncasedFaunaFossilBlock(Properties pProperties) {
        super(pProperties);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new EncasedFaunaFossilBlockEntity(pPos, pState);
    }


}
