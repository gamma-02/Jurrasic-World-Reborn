package net.gamma02.jurassicworldreborn.common.items.misc;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.gamma02.jurassicworldreborn.common.blocks.entities.cultivator.CultivatorBlock;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class CultivatorItem extends ItemNameBlockItem {
    private final DyeColor color;

    public CultivatorItem(Properties pProperties, DyeColor color1) {
        super(ModBlocks.CULTIVATOR_BOTTOM.get(), pProperties);
        color = color1;
    }


    @Override
    public void fillItemCategory(CreativeModeTab pGroup, NonNullList<ItemStack> pItems) {
        if(this.allowedIn(pGroup)){
            pItems.add(this.getDefaultInstance());
        }
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext pContext, BlockState pState) {
        return pContext.getLevel().setBlock(pContext.getClickedPos(), pState.setValue(CultivatorBlock.COLOR, this.color), 11);
    }
}
