package net.gamma02.jurassicworldreborn.common.blocks.wood;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class ModSaplingBlock extends BushBlock implements BonemealableBlock {

    public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
    private final Supplier<Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>>> feature;

    protected static final VoxelShape SAPLING_SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);


    public ModSaplingBlock(Supplier<Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>>> feature, Properties properties) {
        super(properties);
        this.feature = feature;

        this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, 0));

        Jurassicworldreborn.setRenderType(this, RenderType.cutout());
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter p_50897_, BlockPos p_50898_, BlockState p_50899_, boolean p_50900_) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level world, RandomSource rand, @NotNull BlockPos pos, @NotNull BlockState state) {
        return (double)rand.nextFloat() < 0.45f;
    }

    public void randomTick(@NotNull BlockState state, ServerLevel world, BlockPos pos, @NotNull RandomSource rand) {
        if (world.getMaxLocalRawBrightness(pos.above()) >= 9 && rand.nextInt(7) == 0) {
            if (!world.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light <- lmaoooo hahahahaaaa
            this.advanceTree(world, pos, state, rand);
        }

    }

    public void advanceTree(ServerLevel world, BlockPos pos, BlockState state, RandomSource rand) {
        if (state.getValue(STAGE) == 0) {
            world.setBlock(pos, state.cycle(STAGE), 4);
        } else {
            if (net.minecraftforge.event.ForgeEventFactory.blockGrowFeature( world, rand, pos, this.feature.get()).getResult().equals(Event.Result.DENY)) return;
            this.feature.get().get().place( world, world.getChunkSource().getGenerator(), rand, pos);
        }

    }

    @Override
    public void performBonemeal(@NotNull ServerLevel world, @NotNull RandomSource rand, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        this.advanceTree(world, pos, state, rand);
    }


    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_56001_) {
        p_56001_.add(STAGE);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SAPLING_SHAPE;
    }
}
