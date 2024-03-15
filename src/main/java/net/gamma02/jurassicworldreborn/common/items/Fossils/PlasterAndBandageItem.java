package net.gamma02.jurassicworldreborn.common.items.Fossils;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.gamma02.jurassicworldreborn.common.blocks.entities.EncasedFaunaFossilBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.fossil.FaunaFossilBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.fossil.FossilBlock;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class PlasterAndBandageItem extends Item {
    public PlasterAndBandageItem(Properties properties) {
        super(properties);

    }

//    @Override
//    public @NotNull InteractionResult useOn(UseOnContext ctx) {
//        ItemStack stack = ctx.getItemInHand();
//        Player player = ctx.getPlayer();
//        Level world = ctx.getLevel();
////        if (ctx.getLevel().isUnobstructed(pos.offset(side), side, stack)) {
//        BlockState state = world.getBlockState(ctx.getClickedPos());
//
//        Block block = state.getBlock();
//
//        if(player == null){
//            return InteractionResult.PASS;
//        }
//
//        if (block instanceof FossilBlock) {
//            if (!world.isClientSide) {
//
//
//                if (!(world.getBlockEntity(ctx.getClickedPos()) instanceof FaunaFossilBlockEntity faunaFossilEntity)) {
//                    return InteractionResult.PASS;
//                }
//                Dinosaur initialDino = faunaFossilEntity.getDinosaur();
//
//                world.setBlock(ctx.getClickedPos(), ModBlocks.ENCASED_FAUNA_FOSSIL.get().defaultBlockState(), 3);
//                if (world.getBlockEntity(ctx.getClickedPos()) instanceof EncasedFaunaFossilBlockEntity entity) {
//                    entity.setDino(initialDino);
//                }
//
//                if (player.isCreative()) {
//                    stack.shrink(1);
//                }
//
//                return InteractionResult.SUCCESS;
//            }
//
//            return super.useOn(ctx);
////        } else if (block instanceof NestFossilBlock && !((NestFossilBlock) block).encased) {
////            if (!world.isRemote) {
////                world.setBlockState(pos, BlockHandler.ENCASED_NEST_FOSSIL.getDefaultState().withProperty(NestFossilBlock.VARIANT, state.getValue(NestFossilBlock.VARIANT)));
////
////                if (!player.capabilities.isCreativeMode) {
////                    stack.shrink(1);
////                }
////            }
////
////            return InteractionResult.SUCCESS;
////        }
//        }
//
//        return InteractionResult.PASS;
//
//    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext ctx) {
//        ItemStack stack = ctx.getItemInHand();
        Player player = ctx.getPlayer();
        Level world = ctx.getLevel();
//        if (ctx.getLevel().isUnobstructed(pos.offset(side), side, stack)) {
        BlockState state = world.getBlockState(ctx.getClickedPos());

        Block block = state.getBlock();

        if (block instanceof FossilBlock) {
            if (!world.isClientSide) {


                if (!(world.getBlockEntity(ctx.getClickedPos()) instanceof FaunaFossilBlockEntity faunaFossilEntity)) {
                    return InteractionResult.PASS;
                }

                Dinosaur initialDino = faunaFossilEntity.getDinosaur();
                if(initialDino == null){
                    faunaFossilEntity.updateDinosaur();
                    initialDino = faunaFossilEntity.getDinosaur();
                }

                world.setBlock(ctx.getClickedPos(), ModBlocks.ENCASED_FAUNA_FOSSIL.get().defaultBlockState(), 3);
                if (world.getBlockEntity(ctx.getClickedPos()) instanceof EncasedFaunaFossilBlockEntity entity) {
                    entity.setDino(initialDino);
                }

                if (!player.isCreative()) {
                    stack.shrink(1);
                }
            }

            return InteractionResult.SUCCESS;
//        } else if (block instanceof NestFossilBlock && !((NestFossilBlock) block).encased) {
//            if (!world.isRemote) {
//                world.setBlockState(pos, BlockHandler.ENCASED_NEST_FOSSIL.getDefaultState().withProperty(NestFossilBlock.VARIANT, state.getValue(NestFossilBlock.VARIANT)));
//
//                if (!player.capabilities.isCreativeMode) {
//                    stack.shrink(1);
//                }
//            }
//
//            return InteractionResult.SUCCESS;
//        }
        }

        return InteractionResult.PASS;
    }
}



