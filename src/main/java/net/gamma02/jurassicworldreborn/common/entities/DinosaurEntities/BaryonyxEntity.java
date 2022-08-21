package net.gamma02.jurassicworldreborn.common.entities.DinosaurEntities;

import com.github.alexthe666.citadel.animation.Animation;
import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.client.model.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.client.sounds.SoundHandler;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Locale;

public class BaryonyxEntity extends DinosaurEntity {

    private static final EntityDataAccessor<Integer> VARIANT= SynchedEntityData.defineId(BaryonyxEntity.class, EntityDataSerializers.INT);

    public BaryonyxEntity(Level world) {
        super(world);
        this.target(AchillobatorEntity.class, SpinoraptorEntity.class, TitanisEntity.class, SmilodonEntity.class, MegatheriumEntity.class, ArsinoitheriumEntity.class, SinoceratopsEntity.class, AllosaurusEntity.class, AlvarezsaurusEntity.class, BeelzebufoEntity.class, VelociraptorBlueEntity.class, CarnotaurusEntity.class, CearadactylusEntity.class, CeratosaurusEntity.class, VelociraptorCharlieEntity.class, ChasmosaurusEntity.class, ChilesaurusEntity.class, CoelurusEntity.class, CompsognathusEntity.class, CorythosaurusEntity.class, VelociraptorDeltaEntity.class, DilophosaurusEntity.class, DimorphodonEntity.class, HyaenodonEntity.class, VelociraptorEchoEntity.class, EdmontosaurusEntity.class, GallimimusEntity.class, GiganotosaurusEntity.class, GuanlongEntity.class, HerrerasaurusEntity.class, DodoEntity.class, HypsilophodonEntity.class, Player.class
, LambeosaurusEntity.class, LeaellynasauraEntity.class, LeptictidiumEntity.class, LudodactylusEntity.class, MajungasaurusEntity.class, MammothEntity.class, MetriacanthosaurusEntity.class, MicroceratusEntity.class, MicroraptorEntity.class, MoganopterusEntity.class, MussaurusEntity.class, OrnithomimusEntity.class, OthnieliaEntity.class, OviraptorEntity.class, PachycephalosaurusEntity.class, ParasaurolophusEntity.class, ProceratosaurusEntity.class, PostosuchusEntity.class, ProtoceratopsEntity.class, PteranodonEntity.class, QuetzalEntity.class, RugopsEntity.class, SegisaurusEntity.class, StegosaurusEntity.class, StyracosaurusEntity.class, TherizinosaurusEntity.class, TriceratopsEntity.class, TroodonEntity.class, TropeognathusEntity.class, TyrannosaurusEntity.class, VelociraptorEntity.class, ZhenyuanopterusEntity.class, CoelacanthEntity.class, MegapiranhaEntity.class, AlligatorGarEntity.class, Animal.class, Villager.class);
        doesEatEggs(true);
        this.setVariant(this.getRandom().nextInt(5));
    }

    public SoundEvent getSoundForAnimation(Animation animation)
    {
        switch (EntityAnimation.getAnimation(animation))
        {
            case SPEAK:
                return SoundHandler.BARYONYX_LIVING;
            case DYING:
                return SoundHandler.BARYONYX_DEATH;
            case INJURED:
                return SoundHandler.BARYONYX_HURT;
            case CALLING:
                return SoundHandler.BARYONYX_CALL;
            case ROARING:
                return SoundHandler.BARYONYX_ROAR;
            case BEGGING:
                return SoundHandler.BARYONYX_THREAT;
        }

        return null;
    }

    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("Variant", this.entityData.get(VARIANT));
    }


    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.entityData.set(VARIANT, nbt.getInt("Variant"));
    }

    public void setVariant(int value){
        this.entityData.set(VARIANT, value);
    }

    public int getVariant(){
        return this.entityData.get(VARIANT);
    }

    public ResourceLocation getTexture(){
        switch(getVariant()){
            case 0: default: return texture("chaos");
            case 1: return texture("green");
            case 2: return texture("grim");
            case 3: return texture("jw");
            case 4: return texture("limbo");
        }
    }
    private ResourceLocation texture(String variant){
        String formattedName = this.dinosaur.getName().toLowerCase(Locale.ENGLISH).replaceAll(" ", "_");
        String baseTextures = "textures/entities/" + formattedName + "/";
        String texture = baseTextures + formattedName;
        return isMale()?new ResourceLocation(Jurassicworldreborn.modid, texture + "_male_" + "adult" + "_" + variant + ".png"):new ResourceLocation(Jurassicworldreborn.modid, texture + "_female_" + "adult" + "_" + variant +".png");
    }
}