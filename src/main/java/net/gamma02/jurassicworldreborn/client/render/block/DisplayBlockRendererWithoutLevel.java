package net.gamma02.jurassicworldreborn.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.gamma02.jurassicworldreborn.common.blocks.entities.ActionFigureBlockEntity;
import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.gamma02.jurassicworldreborn.common.items.misc.ActionFigureItem;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.checkerframework.checker.units.qual.A;

import java.util.Random;

public class DisplayBlockRendererWithoutLevel extends BlockEntityWithoutLevelRenderer {
    protected final BlockEntityRenderDispatcher blockEntityRenderDispatcher;

    public DisplayBlockRendererWithoutLevel(BlockEntityRenderDispatcher pBlockEntityRenderDispatcher, EntityModelSet pEntityModelSet) {
        super(pBlockEntityRenderDispatcher, pEntityModelSet);
        this.blockEntityRenderDispatcher = pBlockEntityRenderDispatcher;

    }

    @Override
    public void renderByItem(ItemStack pStack, ItemTransforms.TransformType pTransformType, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        Item item = pStack.getItem();
        if (item != null) {
            BlockEntity blockentity = null;
            if(item == ModItems.DISPLAY_BLOCK.get()){
                ActionFigureBlockEntity actionFigureBlockEntity = new ActionFigureBlockEntity(BlockPos.ZERO, ModBlocks.DISPLAY_BLOCK.get().defaultBlockState());
                actionFigureBlockEntity.setDinosaur(ActionFigureItem.getDinosaurID(pStack), ActionFigureItem.getVariant(pStack) > 0 ? ActionFigureItem.getVariant(pStack) == 1 : new Random().nextBoolean(), ActionFigureItem.isSkeleton(pStack));
                blockentity = actionFigureBlockEntity;
            }


            if(blockentity != null){
                this.blockEntityRenderDispatcher.renderItem(blockentity, pPoseStack, pBuffer, pPackedLight, pPackedOverlay);
            }
        }

    }

}
