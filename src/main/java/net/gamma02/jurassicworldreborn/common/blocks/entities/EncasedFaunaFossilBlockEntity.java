package net.gamma02.jurassicworldreborn.common.blocks.entities;

import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

//OUTDATED
public class EncasedFaunaFossilBlockEntity extends BlockEntity {

    public static String DINO_KEY = "Dinosaur";


    @Nullable
    protected Dinosaur dino;


    public EncasedFaunaFossilBlockEntity( BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.ENCASED_FAUNA_FOSSIL.get(), pWorldPosition, pBlockState);

    }

    public void setDino(@Nullable Dinosaur dino) {
        this.dino = dino;
    }

    @Nullable
    public Dinosaur getDino() {
        return dino;
    }


    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        pTag.putString(DINO_KEY, dino != null ? dino.getName() : Dinosaur.EMPTY.getName());

    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if(pTag.contains(DINO_KEY))
            this.dino = Dinosaur.getDinosaurByName(pTag.getString(DINO_KEY));
    }

    @Override
    public void saveToItem(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putString(DINO_KEY, dino != null ? dino.toString() : Dinosaur.EMPTY.getName());
        super.saveToItem(stack);
    }

}
