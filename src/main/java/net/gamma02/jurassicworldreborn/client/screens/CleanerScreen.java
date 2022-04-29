package net.gamma02.jurassicworldreborn.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.gamma02.jurassicworldreborn.common.blocks.machines.cleaner.CleanerBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.machines.cleaner.CleanerMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.entity.BlockEntity;

import static net.gamma02.jurassicworldreborn.Jurassicworldreborn.resource;

public class CleanerScreen extends AbstractContainerScreen<CleanerMenu> {

    private static final ResourceLocation TEXTURE = resource("textures/gui/cleaning_station.png");
    private static final int progressBarXOffset = 176;
    private static final int progressBarYOffset = 14;

    private static final int fluidBarXOffset = 177;
    private static final int fluidBarYOffset = 81;//move this DOWN




    public CleanerScreen(CleanerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
//        BlockEntity tempOwner = pPlayerInventory.player.getCommandSenderWorld().getBlockEntity( pPlayerInventory.player.getCommandSenderWorld().clip(new ClipContext( pPlayerInventory.player.getLookAngle(), pPlayerInventory.player.getLookAngle().add(5, 5, 5), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, pPlayerInventory.player)).getBlockPos());
//        pMenu.setInstance(tempOwner instanceof CleanerBlockEntity ? (CleanerBlockEntity) tempOwner : null);
    }

    public CleanerScreen(CleanerMenu menu, Inventory inv, Component title, CleanerBlockEntity entity){
        super(menu, inv, title);
        menu.setInstance(entity);

    }


    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(pPoseStack, i, j, 0, 0, 175, 165);
        boolean isInstanceNull = menu.isInstanceNull();

        //render progress bar
        if(!isInstanceNull && this.menu.isCleaning()){
            int progress = menu.getProgress();
            this.blit(pPoseStack, 78, 34, progressBarXOffset, progressBarYOffset, progress, 16/*or 17 if it doesn't work thats why*/);
        }

        //render fluid amount
        if(!isInstanceNull && this.menu.getAmountOfFluid() > 0){
            int fluidHeight = this.menu.getAmountOfFluid()/49;
            this.blit(pPoseStack, 47, 68, fluidBarXOffset, fluidBarYOffset, 4, fluidHeight);
        }

    }
}
