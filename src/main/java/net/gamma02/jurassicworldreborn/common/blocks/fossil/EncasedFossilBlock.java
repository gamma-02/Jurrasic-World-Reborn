package net.gamma02.jurassicworldreborn.common.blocks.fossil;

import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.common.TierSortingRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EncasedFossilBlock extends BaseEntityBlock {
    protected EncasedFossilBlock(Properties p_49224_) {
        super(p_49224_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new FossilBlockEntity(pPos, pState);
    }


    @Override
    public void playerDestroy(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState, @Nullable BlockEntity pBlockEntity, ItemStack pTool) {

        if(pTool.getItem() instanceof PickaxeItem i && TierSortingRegistry.isCorrectTierForDrops(i.getTier(), pState) && !pLevel.isClientSide() && pLevel.getBlockEntity(pPos) instanceof FossilBlockEntity b){
            ItemStack stack = new ItemStack(ModItems.ENCASED_FAUNA_FOSSIL.get());
            stack.getOrCreateTag().putInt("TimePeriod", b.yValue);
            pLevel.addFreshEntity(new ItemEntity(pLevel, pPos.getX() + 0.5d, pPos.getY() + 0.5d, pPos.getZ() + 0.5d, stack));
        }

        super.playerDestroy(pLevel, pPlayer, pPos, pState, pBlockEntity, pTool);
    }

    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if(pLevel.getBlockEntity(pPos) instanceof FossilBlockEntity e){
            e.yValue = pPos.getY();
        }
        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }
}
