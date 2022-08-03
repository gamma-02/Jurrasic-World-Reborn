package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import mod.reborn.RebornMod;
import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import com.github.alexthe666.citadel.animation.Animation;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.entity.EntityCreature;

import net.minecraft.world.level.Level;
import scala.tools.nsc.doc.model.Class;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.EntityDataAccessor;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import java.util.Locale;

public class MammothEntity extends DinosaurEntity {
    private static final EntityDataAccessor<Integer> VARIANT= EntityDataManager.createKey(MammothEntity.class, DataSerializers.VARINT);

    public MammothEntity(Level world) {
        super(world);
        this.setVariant(this.getRNG().nextInt(5));
    }

    public SoundEvent getSoundForAnimation(Animation animation) {
        switch (EntityAnimation.getAnimation(animation)) {
            case SPEAK:
                return SoundHandler.MAMMOTH_LIVING;
            case CALLING:
                return SoundHandler.MAMMOTH_ALARM_CALL;
            case MATING:
                return SoundHandler.MAMMOTH_MATE_CALL;
            case DYING:
                return SoundHandler.MAMMOTH_DEATH;
            case INJURED:
                return SoundHandler.MAMMOTH_HURT;
            case BEGGING:
                return SoundHandler.MAMMOTH_THREAT;
            default:
                return null;
        }
    }

    public void entityInit() {
        super.entityInit();
        this.dataManager.register(VARIANT, 0);
    }

    public void writeEntityToNBT(NBTTagCompound tagCompound) {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setInteger("Variant", this.getVariant());
    }

    public void readEntityFromNBT(NBTTagCompound tagCompound) {
        super.readEntityFromNBT(tagCompound);
        this.setVariant(tagCompound.getInteger("Variant"));
    }

    public void setVariant(int value){
        this.dataManager.set(VARIANT, value);
    }

    public int getVariant(){
        return this.dataManager.get(VARIANT);
    }

    public ResourceLocation getTexture(){
        switch(getVariant()){
            case 0: default: return texture("blond");
            case 1: return texture("brunette");
            case 2: return texture("dark");
            case 3: return texture("red");
            case 4: return texture("white");
        }
    }
    private ResourceLocation texture(String variant){
        String formattedName = this.dinosaur.getName().toLowerCase(Locale.ENGLISH).replaceAll(" ", "_");
        String baseTextures = "textures/entities/" + formattedName + "/";
        String texture = baseTextures + formattedName;
        return isMale()?new ResourceLocation(RebornMod.MODID, texture + "_male_" + "adult" + "_" + variant + ".png"):new ResourceLocation(RebornMod.MODID, texture + "_female_" + "adult" + "_" + variant +".png");
    }
}