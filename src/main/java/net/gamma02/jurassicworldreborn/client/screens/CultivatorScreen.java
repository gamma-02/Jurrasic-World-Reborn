package net.gamma02.jurassicworldreborn.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.common.blocks.entities.cultivator.CultivatorBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.cultivator.CultivatorMenu;
import net.gamma02.jurassicworldreborn.common.network.Network;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CultivatorScreen extends AbstractContainerScreen<CultivatorMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Jurassicworldreborn.MODID, "textures/gui/cultivator.png");
    private static final ResourceLocation NUTRIENTS_TEXTURE = new ResourceLocation(Jurassicworldreborn.MODID, "textures/gui/cultivator_nutrients.png");






    public CultivatorScreen(CultivatorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);

        this.imageWidth = 352;
        this.imageHeight = 188;
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        boolean flag = super.mouseClicked(pMouseX, pMouseY, pButton);
        this.dragTemperatureSlider(pMouseX, pMouseY);
        return flag;
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelY -= 3;
        this.inventoryLabelY += 24;
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        boolean flag = super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
        this.dragTemperatureSlider(pMouseX, pMouseY);
        return flag;
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);


        this.font.draw(pPoseStack, Component.translatable("cultivator.proximates"), this.leftPos + 190, this.topPos + 48, 4210752);
        this.font.draw(pPoseStack, Component.translatable("cultivator.minerals"), this.leftPos + 190, this.topPos + 74, 4210752);
        this.font.draw(pPoseStack, Component.translatable("cultivator.vitamins"), this.leftPos + 190, this.topPos + 100, 4210752);
        this.font.draw(pPoseStack, Component.translatable("cultivator.lipids"), this.leftPos + 190, this.topPos + 126, 4210752);

        this.renderTooltip(pPoseStack, pMouseX, pMouseY);

    }





    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);


        //draw main container background

        this.blit(pPoseStack, this.width / 2 - this.getXSize() / 2, this.height / 2 - this.getYSize() / 2, 0, 0, 176, 188);

        this.blit(pPoseStack, this.leftPos + 48, this.topPos + 18, 0, 188, 42, 67 - this.getScaled(this.menu.getField(0), 2000, 67));

        this.drawTemperatureSlider(pPoseStack);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, NUTRIENTS_TEXTURE);

        this.blit(pPoseStack, this.width / 2 + 1, this.height / 2 - this.getYSize() / 2, 0, 0, 176, 166);

        int maxNutrients = CultivatorBlockEntity.MAX_NUTRIENTS;

        int nutrientsX = this.leftPos + 190;

        this.blit(pPoseStack, nutrientsX, this.topPos + 56, 0, 166, this.getScaled(this.menu.getField(2), maxNutrients, 150), 9);
        this.blit(pPoseStack, nutrientsX, this.topPos + 82, 0, 175, this.getScaled(this.menu.getField(3), maxNutrients, 150), 9);
        this.blit(pPoseStack, nutrientsX, this.topPos + 108, 0, 184, this.getScaled(this.menu.getField(4), maxNutrients, 150), 9);
        this.blit(pPoseStack, nutrientsX, this.topPos + 134, 0, 193, this.getScaled(this.menu.getField(1), maxNutrients, 150), 9);



    }


    /**
     * <table>
     *     <thread>
     *         <tr>
     *             <th scope="col">Index</th>
     *             <th scope="col">Field</th>
     *         </tr>
     *     </thread>
     *     <tbody>
     *         <tr>
     *             <th scope="row">0</th>
     *             <th scope="row">Fluid level</th>
     *         </tr>
     *         <tr>
     *             <th scope="row">1</th>
     *             <th scope="row">Lipids</th>
     *         </tr>
     *         <tr>
     *             <th scope="row">2</th>
     *             <th scope="row">Proximates</th>
     *         </tr>
     *         <tr>
     *             <th scope="row">3</th>
     *             <th scope="row">Minerals</th>
     *         </tr>
     *         <tr>
     *             <th scope="row">4</th>
     *             <th scope="row">Vitamins</th>
     *         </tr>
     *         <tr>
     *             <th scope="row">5</th>
     *             <th scope="row">Temperature</th>
     *         </tr>
     *         <tr>
     *             <th scope="row">6</th>
     *             <th scope="row">Process time</th>
     *         </tr>
     *         <tr>
     *             <th scope="row">7</th>
     *             <th scope="row">X</th>
     *         </tr>
     *         <tr>
     *             <th scope="row">8</th>
     *             <th scope="row">Y</th>
     *         </tr>
     *         <tr>
     *             <th scope="row">9</th>
     *             <th scope="row">Z</th>
     *         </tr>
     *     </tbody>
     * </table>
     */
    private void drawTemperatureSlider(PoseStack poseStack) {
        int screenX = this.leftPos;
        int screenY = this.topPos;

        int x = screenX + 59;
        int y = screenY + 88;

        int temperature = this.menu.getField(5) * 20 / 100;

        this.blit(poseStack, x + temperature, y, 176, 0, 3, 5);
    }

    /**
     * <table>
     *     <thread>
     *         <tr>
     *             <th scope="col">Index</th>
     *             <th scope="col">Field</th>
     *         </tr>
     *     </thread>
     *     <tbody>
     *         <tr>
     *             <th scope="row">0</th>
     *             <th scope="row">Fluid level</th>
     *         </tr>
     *         <tr>
     *             <th scope="row">1</th>
     *             <th scope="row">Lipids</th>
     *         </tr>
     *         <tr>
     *             <th scope="row">2</th>
     *             <th scope="row">Proximates</th>
     *         </tr>
     *         <tr>
     *             <th scope="row">3</th>
     *             <th scope="row">Minerals</th>
     *         </tr>
     *         <tr>
     *             <th scope="row">4</th>
     *             <th scope="row">Vitamins</th>
     *         </tr>
     *         <tr>
     *             <th scope="row">5</th>
     *             <th scope="row">Temperature</th>
     *         </tr>
     *         <tr>
     *             <th scope="row">6</th>
     *             <th scope="row">Process time</th>
     *         </tr>
     *         <tr>
     *             <th scope="row">7</th>
     *             <th scope="row">X</th>
     *         </tr>
     *         <tr>
     *             <th scope="row">8</th>
     *             <th scope="row">Y</th>
     *         </tr>
     *         <tr>
     *             <th scope="row">9</th>
     *             <th scope="row">Z</th>
     *         </tr>
     *     </tbody>
     * </table>
     */
    private void dragTemperatureSlider(double mouseX, double mouseY) {
        if(this.minecraft == null)
            return;
        if(this.minecraft.level == null)
            return;

        int screenX = this.leftPos;
        int screenY = this.topPos;

        int x = screenX + 59;
        int y = screenY + 88;

        if (mouseX > x && mouseY > y && mouseX < x + 21 && mouseY < y + 5) {
            int mouseTemperature = (int) (mouseX - x + 1) * 4;
            if (mouseTemperature != this.menu.getField(5)) {
                this.menu.setField(5, mouseTemperature);
                Network.setIncubatorTemperature(this.menu.getBlockPos(), 0, mouseTemperature, this.minecraft.level.dimension());
            }
        }
    }

    private int getScaled(int value, int maxValue, int scale) {
        return maxValue != 0 && value != 0 ? value * scale / maxValue : 0;
    }
}
