package net.gamma02.jurassicworldreborn.common.blocks.entities.feeder;

import io.netty.buffer.ByteBuf;
import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.common.blocks.entities.cleaner.CleanerBlockEntity;
import net.gamma02.jurassicworldreborn.client.render.entity.animation.EntityAnimation;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.Diet;
import net.gamma02.jurassicworldreborn.common.entities.EntityUtils.FoodType;
import net.gamma02.jurassicworldreborn.common.items.Food.FoodHelper;
import net.gamma02.jurassicworldreborn.common.util.networking.Syncable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FeederBlockEntity extends RandomizableContainerBlockEntity implements Syncable, BlockEntityTicker<FeederBlockEntity>, Container {

    public static final int meatSlot = 8; //any slot <= to 8 will be a meat slot
    public static final int plantSlot = 17; //any slot > 8 but <= 17 will be a plant slot

    public AABB feederBoundingBox;
    public int prevOpenAnimation;
    public int openAnimation;
    protected String customName;
    private NonNullList<ItemStack> slots = NonNullList.<ItemStack>withSize(18, ItemStack.EMPTY);
    private int stayOpen;
    private boolean open;
    private DinosaurEntity feeding;
    private int feedingExpire;

    private ArrayList<DinosaurEntity> prospectiveFeeders = new ArrayList<>();



    protected FeederBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.feederBoundingBox = new AABB(pos).inflate(32);
    }








    @Override
    protected NonNullList<ItemStack> getItems() {
        return slots;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> pItemStacks) {
        this.slots = pItemStacks;
    }

    @Override
    protected Component getDefaultName() {
        return (Component) FormattedText.of("Feeder");
    }


    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return null;
    }

    @Override
    public int getContainerSize() {
        return 18;
    }

    @Override
    public NonNullList getSyncFields(NonNullList fields) {
        return null;
    }

    @Override
    public void packetDataHandler(ByteBuf fields) {

    }



    public void tick(Level world, BlockPos pos, BlockState state, FeederBlockEntity instance) {

        this.feederBoundingBox = new AABB(pos).inflate(32);//update the stored bounding box to allow for changes

        this.refreshProspectiveFeeders(pos, state, instance, world);


        this.prevOpenAnimation = this.openAnimation;

        if (this.open && this.openAnimation < 20) {
            this.openAnimation++;
        } else if (!this.open && this.openAnimation > 0) {
            this.openAnimation--;
        }
        if (this.open && this.openAnimation == 19) {
            this.stayOpen = 20;
        }
        if (this.feeding != null && (this.feeding.isCarcass() || this.feeding.isDeadOrDying())) {
            this.feeding = null;
        }
        if (this.feeding != null) {
            if (this.feedingExpire > 0) {
                this.feedingExpire--;
            } else {
                this.feeding = null;
            }
        }

        if (this.open && this.openAnimation == 20) {
            if (this.stayOpen > 0) {
                this.stayOpen--;

                if (this.stayOpen == 10 && this.feeding != null) {
                    int feedSlot = this.getFood(this.feeding);
                    if (feedSlot >= 0) {
                        Random random = new Random();

                        float offsetX = 0.5F;
                        float offsetY = 0.5F;
                        float offsetZ = 0.5F;
                        float motionX = 0.0F;
                        float motionY = 0.0F;
                        float motionZ = 0.0F;

                        switch (this.level.getBlockState(this.getBlockPos()).getValue(FeederBlock.FACING)) {
                            case UP:
                                offsetY = 1.0F;
                                motionY = 1.0F;
                                motionX = random.nextFloat() - 0.5F;
                                motionZ = random.nextFloat() - 0.5F;
                                break;
                            case DOWN:
                                offsetY = -1.0F;
                                break;
                            case NORTH:
                                offsetZ = -1.0F;
                                motionY = 0.5F;
                                motionZ = -0.5F;
                                break;
                            case SOUTH:
                                offsetZ = 1.0F;
                                motionY = 0.5F;
                                motionZ = 0.5F;
                                break;
                            case WEST:
                                offsetX = -1.0F;
                                motionY = 0.5F;
                                motionX = -0.5F;
                                break;
                            case EAST:
                                offsetX = 1.0F;
                                motionY = 0.5F;
                                motionX = 0.5F;
                                break;
                        }

                        ItemStack stack = this.slots.get(feedSlot);
                        boolean shouldContinueDispensing = true;
                        List<ItemEntity> items = world.getEntities(EntityType.ITEM, this.feederBoundingBox, (FoodHelper::isFood));

                        for(ItemEntity i : items){
                            if(this.getItems().stream().anyMatch(itemStack -> i.getItem().getItem() == itemStack.getItem())){
                                shouldContinueDispensing = false;
                            }
                        }

                        if (stack != ItemStack.EMPTY && shouldContinueDispensing) {
                            ItemEntity itemEntity = new ItemEntity(this.level, this.getBlockPos().getX() + offsetX, this.getBlockPos().getY() + offsetY, this.getBlockPos().getZ() + offsetZ, new ItemStack(stack.getItem(), 1));
                            itemEntity.setDefaultPickUpDelay();
                            float motion1X = motionX * 0.3F;
                            float motion1Y = motionY * 0.3F;
                            float motion1Z = motionZ * 0.3F;
                            itemEntity.setDeltaMovement(motion1X, motion1Y, motion1Z);
                            this.level.addFreshEntity(itemEntity);

                            this.removeItem(feedSlot, 1);
                            this.feeding.getNavigation().moveTo(itemEntity.getX() + motion1X, itemEntity.getY() + motion1Y, itemEntity.getZ() + motion1Z, 0.8);
                        }
                    }

                    this.feeding = null;
                }
            } else if (!this.level.isClientSide) {
                this.open = false;
            }
        }
    }

    private void refreshProspectiveFeeders(BlockPos pos, BlockState state, FeederBlockEntity instance, Level world) {
        ArrayList<DinosaurEntity> refreshedList = new ArrayList<>();
        for(DinosaurEntity d : this.prospectiveFeeders){
            if(d.getNavigation().createPath(pos, 1) != null){
                refreshedList.add(d);
            }
        }
        for(Entity e : world.getEntities((Entity)null/*this is to correct for an ambigous method call, so yes it's needed - gamma_02*/, this.feederBoundingBox, (entity -> entity instanceof DinosaurEntity))){
            DinosaurEntity d = ((DinosaurEntity) e);
            if(d.getNavigation().createPath(pos, 1) != null){
                refreshedList.add(d);
            }
        }

        this.prospectiveFeeders = refreshedList;
    }

    public int getFood(DinosaurEntity feeding){
        for(int i = 0; i <= this.plantSlot; i++){
            if(FoodHelper.isEdible(feeding, feeding.getDinosaur().getDiet(), this.getItem(i).getItem())){
                return i;
            }
        }
        return -1;
    }

    public boolean canFeedDinosaur(DinosaurEntity dinosaur) {
        return this.getFoodForDinosaur(dinosaur) != -1;
    }

    private int getFoodForDinosaur(DinosaurEntity dinosaur) {
        int i = 0;
        for (ItemStack stack : this.slots) {
            Dinosaur metadata = dinosaur.getDinosaur();
            if (stack != ItemStack.EMPTY && stack.getCount() > 0 && FoodHelper.isEdible(dinosaur, metadata.getDiet(), stack.getItem())) {
                return i;
            }
            i++;
        }
        return -1;
    }
    //idk why this is here it's the same as the method below but whatever

    @Override
    public boolean canPlaceItem(int pIndex, ItemStack pStack) {
        return isItemValidForSlot(pIndex, pStack);
    }
    // tests if the given ItemStack is valid for a slot
    public boolean isItemValidForSlot(int slotID, ItemStack itemstack) {
        if (isMeatSlot(slotID)){
            if (itemstack != null && (FoodHelper.isFoodType(itemstack.getItem(), FoodType.MEAT) || FoodHelper.isFoodType(itemstack.getItem(), FoodType.FISH) || FoodHelper.isFoodType(itemstack.getItem(), FoodType.INSECT))) {
                return true;
            }
        }else if (isPlantSlot(slotID)){
            if (itemstack != null && FoodHelper.isFoodType(itemstack.getItem(), FoodType.PLANT)) {
                return true;
            }
        }

        return false;
    }


    public DinosaurEntity getFeeding(){
        return this.feeding;
    }



    /**
     *
     * @param slot the slot to be tested
     * @return if the slot is valid for meat/fish/insects
     */
    public static boolean isMeatSlot(int slot){
        return slot <= meatSlot;
    }
    /**
     *
     * @param slot the slot to be tested
     * @return if the slot is valid for plants
     */
    public static boolean isPlantSlot(int slot){
        return slot > meatSlot && slot <= plantSlot;
    }
}
