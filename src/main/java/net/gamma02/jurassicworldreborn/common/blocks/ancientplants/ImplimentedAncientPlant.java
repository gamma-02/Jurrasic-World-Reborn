package net.gamma02.jurassicworldreborn.common.blocks.ancientplants;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ImplimentedAncientPlant extends AncientPlantBlock{
    //this class impliments a bounding box in plants.
    //some things may have been lost in the porting process such as:
//    (0.1F, 0.0F, 0.1F, 0.9F, 0.8F, 0.9F) being turned into (2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D).
    //this may become an issue.
    private final VoxelShape BOUNDS;

    public ImplimentedAncientPlant(){
        super();
        this.BOUNDS = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
    }

    public ImplimentedAncientPlant(VoxelShape bounds){
        super();
        this.BOUNDS = bounds;
    }

    public ImplimentedAncientPlant(BlockBehaviour.Properties properties){
        super(properties);
        this.BOUNDS = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
    }

    public ImplimentedAncientPlant(BlockBehaviour.Properties properties, VoxelShape bounds){
        super(properties);
        this.BOUNDS = bounds;
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return BOUNDS;
    }

}
