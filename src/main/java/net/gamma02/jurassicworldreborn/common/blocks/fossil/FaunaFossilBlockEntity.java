package net.gamma02.jurassicworldreborn.common.blocks.fossil;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FaunaFossilBlockEntity extends BlockEntity {


    private Dinosaur dinosaur = Dinosaur.EMPTY;

    public FaunaFossilBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.FAUNA_FOSSIL_BLOCK_ENTITY.get(), pPos, pBlockState);


    }

    public void setDinosaur(Dinosaur newDino){
        if(dinosaur == Dinosaur.EMPTY){
            this.dinosaur = newDino;
        }else{
            Jurassicworldreborn.getLogger().debug("Dinosaur tried to be set in a non-empty fossil!");
        }
    }

    public void updateDinosaur(){
        FaunaFossil.setDinosaurFromPos(this.getBlockPos(), this.getLevel(), this.getLevel().getRandom());
    }

    public Dinosaur getDinosaur(){
//        FaunaFossil.setDinosaurFromPos(this.getBlockPos(), this.getLevel(), this.getLevel().getRandom());
        return this.dinosaur;
    }


    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        pTag.putString("Dinosaur", dinosaur.getName());

    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        this.dinosaur = Dinosaur.getDinosaurByName(pTag.getString("Dinosaur"));
    }
}
