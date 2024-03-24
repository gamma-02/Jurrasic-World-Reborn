package net.gamma02.jurassicworldreborn.mixin;

import net.gamma02.jurassicworldreborn.common.util.ItemStackCreativeModeTabSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.searchtree.SearchRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {


//    @Inject(method = "handleUpdateTags", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;populateSearchTree(Lnet/minecraft/client/searchtree/SearchRegistry$Key;Ljava/util/List;)V", shift = At.Shift.NONE))
//    void injectBeforeAddTags(ClientboundUpdateTagsPacket pPacket, CallbackInfo ci){
//
//
//
//    }

    @Redirect(method = "handleUpdateTags", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;populateSearchTree(Lnet/minecraft/client/searchtree/SearchRegistry$Key;Ljava/util/List;)V", ordinal = 0 ))
    void redirectPopulateTags(Minecraft instance, SearchRegistry.Key<ItemStack> searchTreeKey, List<ItemStack> stacks){

        jurassicworldreborn$fillItemStacks(CreativeModeTab.TAB_SEARCH, (NonNullList<ItemStack>) stacks);

        instance.populateSearchTree(searchTreeKey, stacks);

    }

    @Unique
    private static void jurassicworldreborn$fillItemStacks(CreativeModeTab instance, NonNullList<ItemStack> itemStacks) {


        NonNullList<ItemStack> stacksForTab = ItemStackCreativeModeTabSystem.getItemStacksForTab(instance);

        if(!stacksForTab.isEmpty()){
            itemStacks.addAll(stacksForTab);
        }

    }
}
