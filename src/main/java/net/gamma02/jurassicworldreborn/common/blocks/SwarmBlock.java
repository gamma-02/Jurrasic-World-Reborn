package net.gamma02.jurassicworldreborn.common.blocks;

import net.gamma02.jurassicworldreborn.common.items.misc.SwarmItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class SwarmBlock extends Block {

    public static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 0.5D, 16.0D);

    private RegistryObject<SwarmItem> item;

    public SwarmBlock(RegistryObject<SwarmItem> item, Properties properties) {
        super(properties);
        this.item = item;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
        super.onNeighborChange(state, level, pos, neighbor);
        this.checkForDrop((Level)level, pos, state);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader world, BlockPos pPos) {
        return world.getBlockState(pPos.below()).getBlock() == Blocks.WATER;
    }

    private boolean checkForDrop(Level world, BlockPos pos, BlockState state) {
        if (!this.canSurvive(state, world, pos)) {
            world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(this.item.get())));
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), 1);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootContext.Builder pBuilder) {

        return this.getAddationalDrops(pState, super.getDrops(pState, pBuilder));//that was easy, might cause a bug - gamma_02
//        return super.getDrops(pState, pBuilder);
    }

    public List<ItemStack> getAddationalDrops(BlockState state, List<ItemStack> originalDrops) {
        originalDrops.add(new ItemStack(this.item.get()));
        return originalDrops;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random rand) {
        super.randomTick(state, world, pos, rand);
        this.checkForDrop(world, pos, state);
        if (rand.nextInt(10) == 0) {
            ItemEntity item = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.8, pos.getZ() + 0.5, new ItemStack(this.item.get()));
            item.setDeltaMovement((rand.nextFloat() - 0.5F) * 0.5F, 0.2F, (rand.nextFloat() - 0.5F) * 0.5F);
            world.addFreshEntity(item);
        }
    }


    @Override
    public Item asItem() {
        return this.item.get();
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return new ItemStack(this.item.get());
    }
}
