package net.gamma02.jurassicworldreborn.mixin;

import net.gamma02.jurassicworldreborn.common.CommonRegistries;
import net.gamma02.jurassicworldreborn.common.blocks.wood.DynamicWoodTypeRegistry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;

@Mixin(BlockEntityType.Builder.class)
public class BlockEntityTypeMixin<T extends BlockEntity> {

    @Inject(method = "of", at = @At("HEAD"))
    private static <T extends BlockEntity> void ofMixin(BlockEntityType.BlockEntitySupplier<? extends T> pFactory, Block[] pValidBlocks, CallbackInfoReturnable<BlockEntityType.Builder<T>> cir){

        if(Arrays.stream(pValidBlocks).anyMatch((b) -> b == Blocks.OAK_SIGN || b == Blocks.DARK_OAK_WALL_SIGN)){

            Block[] newValidBlocks = new Block[pValidBlocks.length + 10/*This is the amount of tree types we have in the mod*/];
            int a = 0;
            for (; a < pValidBlocks.length; a++) {
                newValidBlocks[a] = pValidBlocks[a];
            }

            for(int i = 0; i < CommonRegistries.modWoodTypes.size(); i++){
                for(int j = 0; j < 2; j++ ){
                    newValidBlocks[a+i+j]= DynamicWoodTypeRegistry.getProductFromWoodType(CommonRegistries.modWoodTypes.get(i), j == 1 ? DynamicWoodTypeRegistry.ProductType.SIGN : DynamicWoodTypeRegistry.ProductType.WALL_SIGN);
                }
            }
            pValidBlocks = newValidBlocks;
        }

    }
}
