package net.gamma02.jurrasicworldreborn.common.blocks.wood;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Random;

public class ModSaplingBlock extends BushBlock implements BonemealableBlock {

    private final Feature<NoneFeatureConfiguration> feature;

    public ModSaplingBlock(Feature<NoneFeatureConfiguration> feature, Properties properties) {
        super(properties);
        this.feature = feature;

        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        this.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.AGE_3);

        this.registerDefaultState(builder.create(Block::defaultBlockState, BlockState::new).any());

    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter p_50897_, BlockPos p_50898_, BlockState p_50899_, boolean p_50900_) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level world, Random rand, BlockPos pos, BlockState state) {
        if(Mth.nextInt(rand, 1, 5) == 1){
            if(state.getValue(BlockStateProperties.AGE_3) == BlockStateProperties.MAX_AGE_3 && world instanceof ServerLevel s){
                this.performBonemeal(s, rand, pos, state);
                return true;
            }
            state.setValue(BlockStateProperties.AGE_3, state.getValue(BlockStateProperties.AGE_3)+1);

        }
        return true;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel world, @NotNull Random p_50894_, @Nonnull BlockPos p_50895_, @Nonnull BlockState p_50896_) {
        this.feature.place(NoneFeatureConfiguration.INSTANCE, world, world.getChunkSource().getGenerator(), p_50894_, p_50895_);
    }
}
