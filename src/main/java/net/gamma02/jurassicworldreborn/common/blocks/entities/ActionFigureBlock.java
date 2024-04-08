package net.gamma02.jurassicworldreborn.common.blocks.entities;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.common.blocks.entities.Embryoncis.EmbryonicMachine.EmbryonicMachineBlock;
import net.gamma02.jurassicworldreborn.common.blocks.entities.Embryoncis.EmbryonicMachine.EmbryonicMachineBlockEntity;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurHandler;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.gamma02.jurassicworldreborn.common.items.misc.ActionFigureItem;
import net.gamma02.jurassicworldreborn.common.util.NbtBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ActionFigureBlock extends Block implements EntityBlock
{
    public ActionFigureBlock(Properties p_49795_) {
        super(p_49795_);
        Jurassicworldreborn.setRenderType(this, RenderType.cutout());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ActionFigureBlockEntity(pPos, pState);
    }


    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return getBounds(pLevel, pPos);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return true;
    }



    private static VoxelShape getBounds(BlockGetter world, BlockPos pos) {
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof ActionFigureBlockEntity displayEntity) {
            DinosaurEntity dinoEntity = displayEntity.getEntity();
            if(dinoEntity == null)
                return Shapes.empty();
            Dinosaur dinosaur = dinoEntity.getDinosaur();
            if (!displayEntity.isSkeleton()) {
                Dinosaur metadata = dinosaur;
                float width = Mth.clamp(metadata.getAdultSizeX() * 0.25F, 0.1F, 1.0F);
                float height = Mth.clamp(metadata.getAdultSizeY() * 0.25F, 0.1F, 1.0F);
                float halfWidth = width / 2.0F;
                width *= 16;//converts these values from 1.12 system to 1.18 system - gamma
                height *= 16;
                halfWidth *= 16;
                return Block.box(8 - halfWidth, 0, 8 - halfWidth, halfWidth + 8, height, halfWidth + 8);
            }else{
                return Block.box(0.01, 0.01, 0.01, 15.99, 15.99, 15.99);
            }
        }
        return Shapes.empty();
    }


    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return super.canSurvive(pState, pLevel, pPos) && canBlockStay(pLevel, pPos);
    }


    @Override
    public void onNeighborChange(BlockState state, LevelReader world, BlockPos pos, BlockPos fromPos) {
        super.onNeighborChange(state, world, pos, fromPos);
        this.checkAndDropBlock((Level)world, pos, world.getBlockState(pos));
    }


    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        super.randomTick(pState, pLevel, pPos, pRandom);
        this.checkAndDropBlock(pLevel, pPos, pState);
    }

    private void checkAndDropBlock(Level world, BlockPos pos, BlockState state) {
        if (!canBlockStay(world, pos)) {
//            this.dropBlockAsItem(world, pos, state, 0);
            dropResources(state, world, pos);

            world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        }
    }

    private static boolean canBlockStay(LevelReader world, BlockPos pos) {
        return world.getBlockState(pos.below()).getMaterial().isSolid();
    }



    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return getItemFromTile(getTile((Level)level, pos));
    }

    public ActionFigureBlockEntity getTile(Level world, BlockPos pos) {return (ActionFigureBlockEntity) world.getBlockEntity(pos);}


    private static ItemStack getItemFromTile(ActionFigureBlockEntity tile) {
        byte variant = tile.getVariant();
        if(tile.isSkeleton()) {
            variant = tile.isMale() ? (byte)1 : (byte)2;
        }
//        String metadata = ActionFigureItem.getDinosaur(EntityHandler.getDinosaurId(tile.getEntity().getDinosaur()), variant, tile.isSkeleton());
        Dinosaur dino = tile.getEntity().getDinosaur();
        boolean skeleton = tile.isSkeleton();


        ActionFigureItem item;
        if(skeleton){
            if(tile.isFossile())
                item = ModItems.FOSSIL_SKELETONS.get(dino).get();
            else
                item = ModItems.FRESH_SKELETONS.get(dino).get();
        }
        item = ModItems.ACTION_FIGURES.get(dino).get();



        ItemStack stack = new ItemStack(item);
        CompoundTag nbt = new NbtBuilder().putByte("Gender",  (byte) (tile.isMale() ? 1 : 2)).build();
        return stack;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> drops = new ArrayList<>(1);

        ActionFigureBlockEntity tile = ((ActionFigureBlockEntity)builder.getLevel().getBlockEntity(new BlockPos(builder.getParameter(LootContextParams.ORIGIN))));//if this errors, run for your life - gamma

        if (tile != null) {
            drops.add(getItemFromTile(tile));
        }

        return drops;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return (pLevel1, pPos, pState1, pBlockEntity) -> {
            if(pLevel1.getBlockEntity(pPos) instanceof ActionFigureBlockEntity actionFigure){
                actionFigure.tick(pLevel1, pPos, pState1, actionFigure);
            }
        };
    }
}
