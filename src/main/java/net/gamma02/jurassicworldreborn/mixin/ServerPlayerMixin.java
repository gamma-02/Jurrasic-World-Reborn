package net.gamma02.jurassicworldreborn.mixin;

import com.mojang.authlib.GameProfile;
import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {

    public ProfilerFiller profiler = null;

    public ServerPlayerMixin(Level pLevel, BlockPos pPos, float pYRot, GameProfile pGameProfile, @Nullable ProfilePublicKey pProfilePublicKey) {
        super(pLevel, pPos, pYRot, pGameProfile, pProfilePublicKey);
    }

    @Shadow public abstract boolean isSpectator();

    @Shadow public ServerGamePacketListenerImpl connection;

    @Shadow private float lastSentHealth;

    @Shadow private int lastSentFood;

    @Shadow private boolean lastFoodSaturationZero;

    @Shadow private float lastRecordedHealthAndAbsorption;

    @Shadow protected abstract void updateScoreForCriteria(ObjectiveCriteria pCriteria, int pPoints);

    @Shadow private int lastRecordedFoodLevel;

    @Shadow private int lastRecordedAirLevel;

    @Shadow private int lastRecordedArmor;

    @Shadow private int lastRecordedExperience;

    @Shadow private int lastRecordedLevel;

    @Shadow private int lastSentExp;

    @Shadow public abstract ServerLevel getLevel();

    @Shadow public abstract Entity getCamera();

    @Shadow @Final public ServerPlayerGameMode gameMode;

    @Shadow private int spawnInvulnerableTime;

    @Shadow public abstract void setCamera(@Nullable Entity pEntityToSpectate);

    @Shadow @javax.annotation.Nullable private Entity camera;

    @Shadow @javax.annotation.Nullable private Vec3 levitationStartPos;

    @Shadow public abstract void trackStartFallingPosition();

    @Shadow public abstract void trackEnteredOrExitedLavaOnVehicle();

    @Shadow @Final private PlayerAdvancements advancements;

    @Shadow private int levitationStartTime;

    @Shadow public int containerCounter;

    @Shadow @Final public MinecraftServer server;

    /**
     * @author
     * @reason
     */
//    @Overwrite
//    public void doTick() {
//        ProfilerFiller pro = this.getLevel().getProfiler();
//        pro.push("player:doTick");
//        try {
//            if (!this.isSpectator() || !this.touchingUnloadedChunk()) {
//                this.getLevel().getProfiler().push("superTick");
//                super.tick();
//                pro.pop();
//            }
//            pro.push("inventorySend");
//            for(int i = 0; i < this.getInventory().getContainerSize(); ++i) {
//                ItemStack itemstack = this.getInventory().getItem(i);
//                if (itemstack.getItem().isComplex()) {
//                    Packet<?> packet = ((ComplexItem)itemstack.getItem()).getUpdatePacket(itemstack, this.level, this);
//                    if (packet != null) {
//                        this.connection.send(packet);
//                    }
//                }
//            }
//            pro.pop();
//            pro.push("updatingStuff");
//            if (this.getHealth() != this.lastSentHealth || this.lastSentFood != this.foodData.getFoodLevel() || this.foodData.getSaturationLevel() == 0.0F != this.lastFoodSaturationZero) {
//                this.connection.send(new ClientboundSetHealthPacket(this.getHealth(), this.foodData.getFoodLevel(), this.foodData.getSaturationLevel()));
//                this.lastSentHealth = this.getHealth();
//                this.lastSentFood = this.foodData.getFoodLevel();
//                this.lastFoodSaturationZero = this.foodData.getSaturationLevel() == 0.0F;
//            }
//
//            if (this.getHealth() + this.getAbsorptionAmount() != this.lastRecordedHealthAndAbsorption) {
//                this.lastRecordedHealthAndAbsorption = this.getHealth() + this.getAbsorptionAmount();
//                this.updateScoreForCriteria(ObjectiveCriteria.HEALTH, Mth.ceil(this.lastRecordedHealthAndAbsorption));
//            }
//
//            if (this.foodData.getFoodLevel() != this.lastRecordedFoodLevel) {
//                this.lastRecordedFoodLevel = this.foodData.getFoodLevel();
//                this.updateScoreForCriteria(ObjectiveCriteria.FOOD, Mth.ceil((float)this.lastRecordedFoodLevel));
//            }
//
//            if (this.getAirSupply() != this.lastRecordedAirLevel) {
//                this.lastRecordedAirLevel = this.getAirSupply();
//                this.updateScoreForCriteria(ObjectiveCriteria.AIR, Mth.ceil((float)this.lastRecordedAirLevel));
//            }
//
//            if (this.getArmorValue() != this.lastRecordedArmor) {
//                this.lastRecordedArmor = this.getArmorValue();
//                this.updateScoreForCriteria(ObjectiveCriteria.ARMOR, Mth.ceil((float)this.lastRecordedArmor));
//            }
//
//            if (this.totalExperience != this.lastRecordedExperience) {
//                this.lastRecordedExperience = this.totalExperience;
//                this.updateScoreForCriteria(ObjectiveCriteria.EXPERIENCE, Mth.ceil((float)this.lastRecordedExperience));
//            }
//
//            if (this.experienceLevel != this.lastRecordedLevel) {
//                this.lastRecordedLevel = this.experienceLevel;
//                this.updateScoreForCriteria(ObjectiveCriteria.LEVEL, Mth.ceil((float)this.lastRecordedLevel));
//            }
//
//            if (this.totalExperience != this.lastSentExp) {
//                this.lastSentExp = this.totalExperience;
//                this.connection.send(new ClientboundSetExperiencePacket(this.experienceProgress, this.totalExperience, this.experienceLevel));
//            }
//
//            if (this.tickCount % 20 == 0) {
//
//                CriteriaTriggers.LOCATION.trigger((ServerPlayer) this.getCamera());
//            }
//            pro.pop();
//
//        } catch (Throwable throwable) {
//            CrashReport crashreport = CrashReport.forThrowable(throwable, "Ticking player");
//            CrashReportCategory crashreportcategory = crashreport.addCategory("Player being ticked");
//            this.fillCrashReportCategory(crashreportcategory);
//            throw new ReportedException(crashreport);
//        }
//        pro.pop();
//    }
    //tick
    @Overwrite
    public void tick() {
        ProfilerFiller pro = this.level.getProfiler();
        Jurassicworldreborn.profilerFiller = pro;
        pro.push("tick");
        pro.push("gamemodeTick");
        this.gameMode.tick();
        --this.spawnInvulnerableTime;
        if (this.invulnerableTime > 0) {
            --this.invulnerableTime;
        }
        pro.pop();



        pro.push("container");
        pro.push("brodcastChanges");//Agh Why This Should Be Null When Its Not Open
        this.containerMenu.broadcastChanges();
        pro.pop();
        pro.push("closeMenu");
        if (!this.level.isClientSide && !this.containerMenu.stillValid(this)) {
            this.closeContainer();
            this.containerMenu = this.inventoryMenu;
        }
        pro.pop();
        pro.pop();
        pro.push("cameraStuff");
        Entity entity = this.getCamera();
        if (entity != this) {
            if (entity.isAlive()) {
                this.absMoveTo(entity.getX(), entity.getY(), entity.getZ(), entity.getYRot(), entity.getXRot());
                this.getLevel().getChunkSource().move((ServerPlayer) this.getCamera());
                if (this.wantsToStopRiding()) {
                    this.setCamera(this);
                }
            } else {
                this.setCamera(this);
            }
        }
        pro.pop();

        pro.push("levitationChecks");
        CriteriaTriggers.TICK.trigger((ServerPlayer) this.getCamera());
        if (this.levitationStartPos != null) {
            CriteriaTriggers.LEVITATION.trigger((ServerPlayer) this.getCamera(), this.levitationStartPos, this.tickCount - this.levitationStartTime);
        }
        pro.pop();

        pro.push("startFallingPos");
        this.trackStartFallingPosition();
        pro.pop();

        pro.push("lavaOnVehicle");
        this.trackEnteredOrExitedLavaOnVehicle();
        pro.pop();

        pro.push("flushAdvancements");
        this.advancements.flushDirty((ServerPlayer) this.getCamera());
        pro.pop();

        pro.pop();
        Jurassicworldreborn.profilerFiller = null;
    }
}
