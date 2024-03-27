package net.gamma02.jurassicworldreborn.mixin;

import com.google.common.base.Suppliers;
import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.minecraft.core.NonNullList;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

@Mixin(AbstractContainerMenu.class)
public abstract class ContainerMenuMixin {

    @Shadow @Final public NonNullList<Slot> slots;

    @Shadow protected abstract void triggerSlotListeners(int pSlotIndex, ItemStack pStack, Supplier<ItemStack> pSupplier);

    @Shadow protected abstract void synchronizeSlotToRemote(int pSlotIndex, ItemStack pStack, Supplier<ItemStack> pSupplier);

    @Shadow protected abstract void synchronizeCarriedToRemote();

    @Shadow @Final private List<DataSlot> dataSlots;

    @Shadow protected abstract void updateDataSlotListeners(int pSlotIndex, int pValue);

    @Shadow protected abstract void synchronizeDataSlotToRemote(int pSlotIndex, int pValue);

    @Shadow public abstract NonNullList<ItemStack> getItems();

    /**
     * @author who do you think would write this unholy mess
     * @reason Minecraft's code was causing server TPS to drop to ~1.75 and MASSIVE client FPS lag on opening inventories. This optimizes it a LOT reducing it to less than 1 ms.<br> God I hate Mojang sometimes.
     */
    //@author: gamma_02 lol
    @Overwrite
    public void broadcastChanges() {
        ProfilerFiller pro = Jurassicworldreborn.profilerFiller;
//        ProfilerFiller pro = null;
        if(pro != null) {
//            pro.push("broadcastMenu");
//        pro.popPush();
//                pro.push("syncLoop(size " + this.slots.size() + ")");
                    int i = 0;
//                    pro.push("getList");
            LinkedList<ItemStack> slotList = new LinkedList<>(this.getItems());//cursednes - a LinkedList is faster* than an ArrayList
//                    pro.popPush("realLoop");
//                    pro.push("loopInit");
                    for(ItemStack itemstack : slotList) {
                        if(i >= slotList.size())
                            continue;
//                        if( i == 0)
//                            pro.pop();
//                        pro.push("syncLoop:" + i);
//                            pro.push("getItem");
//                                var itemstack = slotVar.getItem();

//                            pro.pop();


//                            pro.push("memoizeCopy");
                                Supplier<ItemStack> supplier = Suppliers.memoize(itemstack::copy);
//                            pro.popPush("triggerListeners");
                                this.triggerSlotListeners(i, itemstack, supplier);
//                            pro.popPush("syncSlot");
                                this.synchronizeSlotToRemote(i, itemstack, supplier);
//                            pro.pop();
//                        pro.pop();

                        i++;
                    }
//                    pro.pop();
//                pro.pop();
//            for (int i = 0; i < this.slots.size(); ++i) {
//                pro.push("syncLoop:" + i);
//                pro.push("getSlotStack");
//                ItemStack itemstack = this.slots.get(i).getItem();
//                pro.popPush("memoizeCopy");
//                Supplier<ItemStack> supplier = Suppliers.memoize(itemstack::copy);
//                pro.popPush("triggerListeners");
//                this.triggerSlotListeners(i, itemstack, supplier);
//                pro.popPush("syncSlot");
//                this.synchronizeSlotToRemote(i, itemstack, supplier);
//                pro.pop();
//                pro.pop();
//            }



//                pro.push("syncToRemote");
                    this.synchronizeCarriedToRemote();
//                pro.pop();

//                pro.push("updateSlotListenersAndSync");
                    for (int j = 0; j < this.dataSlots.size(); ++j) {
                        DataSlot dataslot = this.dataSlots.get(j);
                        int k = dataslot.get();
                        if (dataslot.checkAndClearUpdateFlag()) {
                            this.updateDataSlotListeners(j, k);
                        }

                        this.synchronizeDataSlotToRemote(j, k);
                    }
//                pro.pop();
//            pro.pop();
        }else{
            for (int i = 0; i < this.slots.size(); i++) {
                ItemStack itemstack = this.slots.get(i).getItem();
                Supplier<ItemStack> supplier = Suppliers.memoize(itemstack::copy);
                this.triggerSlotListeners(i, itemstack, supplier);
                this.synchronizeSlotToRemote(i, itemstack, supplier);
            }

            this.synchronizeCarriedToRemote();

            for (int j = 0; j < this.dataSlots.size(); j++) {
                DataSlot dataslot = this.dataSlots.get(j);
                int k = dataslot.get();
                if (dataslot.checkAndClearUpdateFlag()) {
                    this.updateDataSlotListeners(j, k);
                }

                this.synchronizeDataSlotToRemote(j, k);
            }
        }

    }
}
