package net.gamma02.jurassicworldreborn.common.blocks.entities.feeder;

import io.netty.buffer.ByteBuf;
import net.gamma02.jurassicworldreborn.common.blocks.entities.cleaner.CleanerBlockEntity;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.util.networking.Syncable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FeederBlockEntity extends RandomizableContainerBlockEntity implements Syncable, BlockEntityTicker<FeederBlockEntity>, Container {

    int meatSlot = 8; //any slot <= to 8 will be a meat slot
    int plantSlot = 17; //any slot > 8 but <= 17 will be a plant slot
    public int prevOpenAnimation;
    public int openAnimation;
    protected String customName;
    private NonNullList<ItemStack> slots = NonNullList.<ItemStack>withSize(18, ItemStack.EMPTY);
    private int stayOpen;
    private boolean open;
    private DinosaurEntity feeding;
    private int feedingExpire;



    protected FeederBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
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

                        switch (this.world.getBlockState(this.pos).getValue(FeederBlock.FACING)) {
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

                        ItemStack stack = (ItemStack)this.slots.get(feedSlot);

                        if (stack != ItemStack.EMPTY) {
                            EntityItem itemEntity = new EntityItem(this.world, this.pos.getX() + offsetX, this.pos.getY() + offsetY, this.pos.getZ() + offsetZ, new ItemStack(stack.getItem(), 1, stack.getItemDamage()));
                            itemEntity.setDefaultPickupDelay();
                            itemEntity.motionX = motionX * 0.3F;
                            itemEntity.motionY = motionY * 0.3F;
                            itemEntity.motionZ = motionZ * 0.3F;
                            this.world.spawnEntity(itemEntity);

                            this.decrStackSize(feedSlot, 1);
                            this.feeding.getNavigator().tryMoveToXYZ(itemEntity.posX + motionX, itemEntity.posY + motionY, itemEntity.posZ + motionZ, 0.8);
                        }
                    }

                    this.feeding = null;
                }
            } else if (!this.world.isRemote) {
                this.setOpen(false);
            }
        }
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

}
