package net.gamma02.jurassicworldreborn.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.common.blocks.entities.DNABlocks.DNACombinatorHybridizer.DNACombinatorHybridizerMenu;
import net.gamma02.jurassicworldreborn.common.network.Network;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DNACombinatorHybridizerScreen extends AbstractContainerScreen<DNACombinatorHybridizerMenu> {
    private static final ResourceLocation hybridizerTexture = Jurassicworldreborn.resource("textures/gui/dna_hybridizer.png");
    private static final ResourceLocation combinatorTexture = Jurassicworldreborn.resource("textures/gui/dna_combinator.png");

    private final Inventory playerInventory;
//    private DNACombinatorHybridizerBlockEntity inventory;
    private DNACombinatorHybridizerMenu menu;

    private Button switchModeButton;



    public DNACombinatorHybridizerScreen(DNACombinatorHybridizerMenu menu, Inventory playerInv, Component title) {
        super(menu, playerInv, title);
        this.playerInventory = playerInv;
//        this.inventory = menu;
        this.menu = menu;
    }

    public DNACombinatorHybridizerScreen(DNACombinatorHybridizerMenu dnaCombinatorHybridizerMenu, Inventory inventory, Component component, BlockEntity entity) {
        this(dnaCombinatorHybridizerMenu, inventory, component);
    }


//    @Override
//    public void initGui() {
//        super.initGui();
//
//
//    }

    @Override
    protected void init() {
        super.init();
        int xSize = this.leftPos;
        int ySize = this.topPos;



        this.switchModeButton = this.addRenderableWidget(new Button(xSize+128, ySize+64, 30, 12, Component.literal("<->"), (w) ->{
            BlockPos entityPos = new BlockPos(menu.getField(3), menu.getField(4), menu.getField(5));

            boolean mode = !this.menu.getMode();
            this.menu.updateSlots(mode);
            this.menu.setMode(mode);

            Network.switchHybridizerCombinerMode(mode, entityPos, playerInventory.player.getLevel().dimension());
        }));
    }

//    @Override
//    public void actionPerformed(GuiButton button) {
//
//    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int id) {
//        if (id == 0) {
//        }
        return super.mouseClicked(pMouseX, pMouseY, id);
    }



//    @Override
//    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float pPartialTick) {
//        this.drawDefaultBackground();
//        this.renderBackground(pPoseStack);
//        super.drawScreen(mouseX, mouseY, partialTicks);
//        super.render(pPoseStack, mouseX, mouseY, pPartialTick);
//        this.renderTooltip(pPoseStack, mouseX, mouseY);
//    }

    //    @Override
//    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
////        String name = this.menu.getDisplayName().getUnformattedText();
//        this.fontRenderer.drawString(name, this.xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 4, 4210752);
//        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
//    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        boolean isCombinator = this.menu.getMode();
        this.menu.updateSlots(isCombinator);

        RenderSystem.setShaderTexture(0, !isCombinator ? hybridizerTexture : combinatorTexture);

//        this.mc.getTextureManager().bindTexture();
        int xSize = this.leftPos;
        int ySize = this.topPos;

        int centerX = (this.width - xSize) / 2;
        int centerY = (this.height - ySize) / 2;
        this.blit(pPoseStack, xSize, ySize, 0, 0, 176, 166);

        int progress = this.getProgress(!isCombinator ? 27 : 24);

        if (!isCombinator) {
            this.blit(pPoseStack, centerX + 86, centerY + 25, 176, 0, 4, progress);
        } else {
            this.blit(pPoseStack, centerX + 93, centerY + 30, 176, 0, 8, progress);
        }

        this.switchModeButton.renderButton(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    private int getProgress(int scale) {
        int j = this.menu.getField(0);
        int k = this.menu.getField(1);
        return k != 0 && j != 0 ? j * scale / k : 0;
    }
}