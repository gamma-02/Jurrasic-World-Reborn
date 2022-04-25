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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Random;

public class ModSaplingBlock extends BushBlock implements BonemealableBlock {

    public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
    private final RegistryObject<Feature<NoneFeatureConfiguration>> feature;

    public ModSaplingBlock(RegistryObject<Feature<NoneFeatureConfiguration>> feature, Properties properties) {
        super(properties);
        this.feature = feature;

        this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, 0));
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter p_50897_, BlockPos p_50898_, BlockState p_50899_, boolean p_50900_) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level world, Random rand, BlockPos pos, BlockState state) {
        return (double)world.random.nextFloat() < 0.45D;
    }

    public void randomTick(@NotNull BlockState state, ServerLevel world, BlockPos pos, @NotNull Random rand) {
        if (world.getMaxLocalRawBrightness(pos.above()) >= 9 && rand.nextInt(7) == 0) {
            if (!world.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light <- lmaoooo hahahahaaaa
            this.advanceTree(world, pos, state, rand);
        }

    }

    public void advanceTree(ServerLevel world, BlockPos pos, BlockState state, Random rand) {
        if (state.getValue(STAGE) == 0) {
            world.setBlock(pos, state.cycle(STAGE), 4);
        } else {
            if (!net.minecraftforge.event.ForgeEventFactory.saplingGrowTree(world, rand, pos)) return;
            this.feature.get().place(NoneFeatureConfiguration.INSTANCE, world, world.getChunkSource().getGenerator(), rand, pos);
        }

    }

    @Override
    public void performBonemeal(@NotNull ServerLevel world, @NotNull Random rand, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        this.advanceTree(world, pos, state, rand);
    }


    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_56001_) {
        p_56001_.add(STAGE);
    }
}
