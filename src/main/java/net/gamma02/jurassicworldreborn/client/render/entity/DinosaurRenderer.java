package net.gamma02.jurassicworldreborn.client.render.entity;

import com.github.alexthe666.citadel.client.model.basic.BasicEntityModel;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.gamma02.jurassicworldreborn.client.model.AnimatableModel;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimator;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities.*;//yooo it did that automatically :) - gamma
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.GrowthStage;
import net.gamma02.jurassicworldreborn.common.util.EntityColorTint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.MixinEnvironment;

import java.awt.*;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class DinosaurRenderer extends LivingEntityRenderer<DinosaurEntity, BasicEntityModel<DinosaurEntity>> {
    public Dinosaur dinosaur;
//    public DinosaurRenderInfo.Context renderInfo;

    public DinosaurRenderInfo renderInfo;


    public Random random;

    Color tint = new Color(0, 0, 0, 0);




    public DinosaurRenderer(EntityRendererProvider.Context renderInfo, BasicEntityModel<DinosaurEntity> model, float shadowSize) {
        super(renderInfo, model, shadowSize);
//        this.dinosaur = renderInfo.getDinosaur();

        this.random = new Random();
//        this.renderInfo = renderInfo;
        this.addLayer(new LayerEyelid(this));
    }

    /**
     * This sets the renderInfo of the render to the dino and animation.
     * !!!CALL THIS RIGHT AFTER REGISTERING IT!!!
     * @param dino The Dinosaur object this class should render
     * @param anim The animator that should be used for the dino
     */
    public void setDinosaur(Dinosaur dino, EntityAnimator<?> anim){
        this.renderInfo = new DinosaurRenderInfo(dino, anim);

    }



    @Override
    public void scale(DinosaurEntity entity, PoseStack stack, float partialTick) {
//        float scaleModifier = entity.getAttributes().getScaleModifier();todo: where can I get at this now?
        float scale = (float) entity.interpolate(this.dinosaur.getScaleInfant(), this.dinosaur.getScaleAdult()) /* * scaleModifier*/;
        this.shadowRadius = scale * this.shadowRadius;


        stack.translate(this.dinosaur.getOffsetX() * scale, this.dinosaur.getOffsetY() * scale, this.dinosaur.getOffsetZ() * scale);

        String name = String.valueOf(entity.getCustomName());
        switch (name) {
            case "Kashmoney360"://wow, they never got one lmao - gamma
            case "JTGhawk137":
                stack.scale(0.1F, scale, scale);
                break;
            case "Gegy":
                stack.scale(scale, 0.01F, scale);
                break;
            case "Destruction":
                stack.scale(scale * 1.2f, scale * 1.2f, scale * 1.2f);
                break;
            case "Wyn":
                stack.scale(scale * 1.3f, scale * 1.3f, scale * 1.3f);
                break;
            case "Notch":
                stack.scale(scale * 2, scale * 2, scale * 2);
                break;
            case "jglrxavpok":
                stack.scale(scale, scale, scale * -1);
                break;
            case "Vitiate":
                int color = Color.HSBtoRGB((entity.level.getGameTime() % 1000) / 100f, 1f, 1f);
                this.tint = new Color((color & 0xFF) / 255f, ((color >> 8) & 0xFF) / 255f, ((color >> 16) & 0xFF) / 255f, 0.5f/*can tweak this later*/);
                break;
            case "VPFbGsfp5QR3WsLXM4JBDJXMG":
                stack.scale(scale * random.nextInt(69), scale * random.nextInt(69), scale * random.nextInt(69));
                break;
            case "WIDE":
                stack.scale(scale * 5, scale * 0.5f, scale * 0.5f);
                break;
            default:
                stack.scale(scale, scale, scale);
                break;
        }
    }

//    @Override
//    public void doRender(final DinosaurEntity entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
//        this.mainModel = this.renderInfo.getModel(entity.getGrowthStage(), (byte) entity.getSkeletonVariant());
//        super.doRender(entity, x, y, z, entityYaw, partialTicks);
//    }

    @Override
    public void render(DinosaurEntity entity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        this.model =  this.renderInfo.getModel(entity.getGrowthStage(), (byte) entity.getSkeletonVariant());
        EntityColorTint.setColor(this.tint);
        super.render(entity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(DinosaurEntity entity) {
        GrowthStage growthStage = entity.getGrowthStage();
        if (!this.dinosaur.doesSupportGrowthStage(growthStage)) {
            growthStage = GrowthStage.ADULT;
        }
        if(!entity.isSkeleton()) {
            if (entity instanceof MammothEntity) {//why do you check every time? just nest ifs. - gamma
                return ((MammothEntity) entity).getTexture();
            }
            if (entity instanceof CompsognathusEntity) {
                return ((CompsognathusEntity) entity).getTexture();
            }
            if (entity instanceof AllosaurusEntity) {
                return ((AllosaurusEntity) entity).getTexture();
            }
            if (entity instanceof ParasaurolophusEntity) {
                return ((ParasaurolophusEntity) entity).getTexture();
            }
            if (entity instanceof DeinotheriumEntity) {
                return ((DeinotheriumEntity) entity).getTexture();
            }
            if (entity instanceof SmilodonEntity) {
                return ((SmilodonEntity) entity).getTexture();
            }
            if (entity instanceof TitanisEntity) {
                return ((TitanisEntity) entity).getTexture();
            }
            if (entity instanceof CeratosaurusEntity) {
                return ((CeratosaurusEntity) entity).getTexture();
            }
            if (entity instanceof SpinoraptorEntity) {
                return ((SpinoraptorEntity) entity).getTexture();
            }
            if (entity instanceof IndoraptorEntity) {
                return ((IndoraptorEntity) entity).getTexture();
            }
            if (entity instanceof BaryonyxEntity) {
                return ((BaryonyxEntity) entity).getTexture();
            }
            if (entity instanceof DiplodocusEntity) {
                return ((DiplodocusEntity) entity).getTexture();
            }
            if (entity instanceof AnkylodocusEntity) {
                return ((AnkylodocusEntity) entity).getTexture();
            }
            if (entity instanceof CamarasaurusEntity) {
                return ((CamarasaurusEntity) entity).getTexture();
            }
            if (entity instanceof BrachiosaurusEntity) {
                return ((BrachiosaurusEntity) entity).getTexture();
            }
            if (entity instanceof AnkylosaurusEntity) {
                return ((AnkylosaurusEntity) entity).getTexture();
            }
            if (entity instanceof ApatosaurusEntity) {
                return ((ApatosaurusEntity) entity).getTexture();
            }
        }


        return entity.isMale() ? this.dinosaur.getMaleTexture(growthStage) : this.dinosaur.getFemaleTexture(growthStage);
    }

//    @Override
//    protected void applyRotations(DinosaurEntity entity, float p_77043_2_, float p_77043_3_, float partialTicks) {
//        GlStateManager.rotate(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
//    }

//    @Override
//    protected void setupRotations(DinosaurEntity pEntityLiving, PoseStack pMatrixStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
//        pMatrixStack.
//    }

    @OnlyIn(Dist.CLIENT)
    public class LayerEyelid extends RenderLayer<DinosaurEntity, BasicEntityModel<DinosaurEntity>> {
        private final RenderLayerParent<DinosaurEntity, BasicEntityModel<DinosaurEntity>> renderer;

        public LayerEyelid(RenderLayerParent<DinosaurEntity, BasicEntityModel<DinosaurEntity>> pRenderer) {
            super(pRenderer);
            this.renderer = pRenderer;
        }


//        @Override
//        public void render(DinosaurEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float age, float yaw, float pitch, float scale) {
//            if (!entity.isInvisible()) {
//                if (entity.areEyelidsClosed()) {
//                    ResourceLocation texture = this.renderer.dinosaur.getEyelidTexture(entity); // why don't you use the dinosaur that's being rendered? seems better... - gamma
//                    if (texture != null) {
//                        ITextureObject textureObject = Minecraft.getMinecraft().getTextureManager().getTexture(texture);
//                        if (textureObject != TextureUtil.MISSING_TEXTURE) {
//                            this.renderer.bindTexture(texture);// - what does this do and what's the modern equivalent? - gamma
                                /*
                                what this does is weird and stupid:
                                it gets the eyelid texture and then renders the entity *again*...
                                with only the eyelid texture.
                                this only works because the eyelid textures are blank except for the eyelids
                                for example:
                                 */
                                /**
                                 * <a href="https://raw.githubusercontent.com/Interactive-Modding/JurassicWorldRebornMod/master/src/main/resources/assets/rebornmod/textures/entities/ankylodocus/ankylodocus_female_adult_eyelid.png">eyelids.png</a>
                                 */
                                /*
                                   needless to say, this is dumb. I'm going to leave a to-do here for the future to fix this.
                                 */
//
//                            this.renderer.getMainModel().render(entity, limbSwing, limbSwingAmount, age, yaw, pitch, scale);
//                            this.renderer.setLightmap(entity); //TODO: Make sure this works this.renderer.setLightmap(entity, partialTicks); | legacy todo - gamma
//                        }
//                    }
//                }
//            }
//        }



//        @Override
//        public boolean shouldCombineTextures() {
//            return true;
//        }

        @Override
        public void render(PoseStack matrixStack, MultiBufferSource buffer, int pPackedLight, DinosaurEntity entity, float pLimbSwing, float pLimbSwingAmount, float pPartialTicks, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
            if(!entity.isInvisible() && entity.areEyelidsClosed() && this.renderer instanceof DinosaurRenderer dinoRenderer){
                ResourceLocation texture = entity.getDinosaur().getEyelidTexture(entity);
                if(texture != null){
                    AbstractTexture textureObject = Minecraft.getInstance().getTextureManager().getTexture(texture);
                    if(textureObject != MissingTextureAtlasSprite.getTexture()) {//don't know if this will work. might cause issues. - gamma
                        //todo: eyelids lmao
                    }
                }
            }
        }
    }
}