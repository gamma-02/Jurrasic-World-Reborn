package net.gamma02.jurassicworldreborn.common.blocks.entities.incubator;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.common.blocks.base.BaseMachineBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class IncubatorBlock extends BaseMachineBlock {


    public static DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public static VoxelShape MODEL_SHAPE_NORTH = Stream.of(
            Block.box(0, 0, 0, 16, 4, 16),
            Stream.of(
                    Block.box(1.5, 6, 1.5, 14.5, 20, 14.5),
                    Block.box(0.0, 6, 1.0, 15, 20, 14),
                    Block.box(1.0, 6, 0.0, 14, 20, 15),
                    Block.box(5.5, 10.5, 13.1, 10.5, 16.5, 15.1),
                    Stream.of(
                            Stream.of(
                                    Block.box(2.5, 17, 0, 13.5, 21, 16),
                                    Block.box(0.0, 17, 0.0, 15, 21, 15),
                                    Block.box(1.5, 17, 0.5, 14.5, 21, 15.5),
                                    Block.box(0.5, 17, 1.5, 15.5, 21, 14.5),
                                    Block.box(0, 17, 2.5, 16, 21, 13.5)
                            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
            Stream.of(
            Block.box(13.975, 19, 14.975, 15, 23.025, 16),
            Block.box(14.975, 19, 13.975, 16, 23.025, 15),
            Block.box(14.475, 19, 14.475, 15.5, 23.025, 15.5),
            Block.box(13.975, 19, 13.975, 15, 23.025, 15)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
            Block.box(14.975, 19, 0.0, 16, 23.025, 2.025),
            Block.box(13.975, 19, 0, 15, 23.025, 1.0258),
            Block.box(14.475, 19, 0.5, 15.5, 23.025, 1.5258),
            Block.box(13.975, 19, 0.0, 15, 23.025, 2.025)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
            Block.box(0.0, 19, 0, 2.025, 23.025, 1.0258),
            Block.box(0, 19, 0.0, 1.0258, 23.025, 2.025),
            Block.box(0.5, 19, 0.5, 1.5258, 23.025, 1.5258),
            Block.box(0.0, 19, 0.0, 2.025, 23.025, 2.025)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
            Block.box(0, 19, 13.975, 1.0258, 23.025, 15),
            Block.box(0.0, 19, 14.975, 2.025, 23.025, 16),
            Block.box(0.5, 19, 14.475, 1.5258, 23.025, 15.5),
            Block.box(0.0, 19, 13.975, 2.025, 23.025, 15)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
            Block.box(1.0, 19, 14.5, 14, 23, 16.5),
            Block.box(1.0, 19, -0.5, 14, 23, 1.5),
            Block.box(-0.5, 19, 1.0, 1.5, 23, 14),
            Block.box(14.5, 19, 1.0, 16.5, 23, 14)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Block.box(4.5, 19.5, 16, 11.5, 22.5, 17),
            Stream.of(
            Block.box(2.0, 19, -7.00, 13, 21, 0),
            Block.box(5.5, 20.25, -6.50, 10.5, 21.25, -3.50),
            Block.box(4, 20.955073749564608, -2.9514187779635197, 12, 21.455073749564608, -1.9514187779635197),
            Block.box(4, 21.455073749564608, -2.7514187779635204, 12, 21.955073749564608, -1.7514187779635204),
            Block.box(4, 21.955073749564608, -2.551418777963521, 12, 22.455073749564608, -1.5514187779635211),
            Block.box(4, 22.455073749564608, -2.3264187779635197, 12, 22.955073749564608, -1.3264187779635197),
            Block.box(4, 22.955073749564615, -2.1264187779635204, 12, 23.455073749564615, -1.1264187779635204),
            Block.box(4, 23.455073749564615, -1.9264187779635176, 12, 23.955073749564615, -0.9264187779635176),
            Block.box(4, 23.955073749564615, -1.7264187779635165, 12, 24.455073749564615, -0.7264187779635165)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Block.box(5.0, 22.75, 16.25, 10, 23.25, 16.75),
            Stream.of(
            Block.box(-0.50, 0, 0.0, 16.5, 4, 15),
            Block.box(0.0, 0, -0.50, 15, 4, 16.5),
            Block.box(1.0, 0, -1.00, 14, 4, 17),
            Block.box(-1.00, 0, 1.0, 17, 4, 14)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
            Block.box(0.5, 4, 0.5, 15.5, 6, 15.5),
            Block.box(0, 4, 0.0, 16, 6, 15),
            Block.box(0.0, 4, 0, 15, 6, 16),
            Block.box(1.0, 4, -0.50, 14, 6, 16.5),
            Block.box(-0.50, 4, 1.0, 16.5, 6, 14)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()
                ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape MODEL_SHAPE_WEST = Stream.of(
            Block.box(0, 0, 0, 16, 4, 16),
            Stream.of(
                    Block.box(1.5, 6, 1.5, 14.5, 20, 14.5),
                    Block.box(2, 6, 1.0, 14, 20, 15),
                    Block.box(1, 6, 2.0, 15, 20, 14),
                    Block.box(0.9000, 10.5, 5.5, 2.9000, 16.5, 10.5),
                    Stream.of(
                            Stream.of(
                                    Block.box(0, 17, 2.5, 16, 21, 13.5),
                                    Block.box(1, 17, 1.0, 15, 21, 15),
                                    Block.box(0.5, 17, 1.5, 15.5, 21, 14.5),
                                    Block.box(1.5, 17, 0.5, 14.5, 21, 15.5),
                                    Block.box(2.5, 17, 0, 13.5, 21, 16)
                            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
            Stream.of(
            Block.box(0, 19, 13.975, 1.0250, 23.025, 15),
            Block.box(1, 19, 14.975, 2.0250, 23.025, 16),
            Block.box(0.5, 19, 14.475, 1.5250, 23.025, 15.5),
            Block.box(1, 19, 13.975, 2.0250, 23.025, 15)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
            Block.box(13.975, 19, 14.975, 15, 23.025, 16),
            Block.box(14.975, 19, 13.975, 16, 23.025, 15),
            Block.box(14.475, 19, 14.475, 15.5, 23.025, 15.5),
            Block.box(13.975, 19, 13.975, 15, 23.025, 15)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
            Block.box(14.975, 19, 1.0, 16, 23.025, 2.025),
            Block.box(13.975, 19, 0, 15, 23.025, 1.025),
            Block.box(14.475, 19, 0.5, 15.5, 23.025, 1.525),
            Block.box(13.975, 19, 1.0, 15, 23.025, 2.025)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
            Block.box(1, 19, 0, 2.0250, 23.025, 1.025),
            Block.box(0, 19, 1.0, 1.0250, 23.025, 2.025),
            Block.box(0.5, 19, 0.5, 1.5250, 23.025, 1.525),
            Block.box(1, 19, 1.0, 2.0250, 23.025, 2.025)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
            Block.box(-0.5, 19, 2.0, 1.5, 23, 14),
            Block.box(14.500, 19, 2.0, 16.5, 23, 14),
            Block.box(2, 19, -0.50, 14, 23, 1.5),
            Block.box(2, 19, 14.5, 14, 23, 16.5)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()
).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Block.box(-1, 19.5, 4.5, 0, 22.5, 11.5),
            Stream.of(
            Block.box(16, 19, 3, 23, 21, 13),
            Block.box(19.5, 20.25, 5.5, 22.5, 21.25, 10.5),
            Block.box(17.951418777963518, 20.955073749564608, 4, 18.951418777963518, 21.455073749564608, 11.999999999999995),
            Block.box(17.751418777963515, 21.455073749564608, 4, 18.751418777963515, 21.955073749564608, 11.999999999999995),
            Block.box(17.55141877796352, 21.955073749564608, 4, 18.55141877796352, 22.455073749564608, 11.999999999999995),
            Block.box(17.326418777963518, 22.455073749564608, 4, 18.326418777963518, 22.955073749564608, 11.999999999999995),
            Block.box(17.126418777963515, 22.955073749564615, 4, 18.126418777963515, 23.455073749564615, 11.999999999999995),
            Block.box(16.926418777963512, 23.455073749564615, 4, 17.926418777963512, 23.955073749564615, 11.999999999999995),
            Block.box(16.726418777963513, 23.955073749564615, 4, 17.726418777963513, 24.455073749564615, 11.999999999999995)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()
).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()
).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Block.box(-0.75, 22.75, 6, -0.25, 23.25, 10),
            Stream.of(
            Block.box(1, 0, -0.5, 15, 4, 16.5),
            Block.box(-0.5, 0, 1.0, 16.5, 4, 15),
            Block.box(-1, 0, 2.0, 17, 4, 14),
            Block.box(2, 0, -1, 14, 4, 17)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
            Block.box(0.5, 4, 0.5, 15.5, 6, 15.5),
            Block.box(1, 4, 0, 15, 6, 16),
            Block.box(0, 4, 1.0, 16, 6, 15),
            Block.box(-0.5, 4, 2.0, 16.5, 6, 14),
            Block.box(2, 4, -0.5, 14, 6, 16.5)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()
).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape MODEL_SHAPE_SOUTH = Stream.of(
            Block.box(0, 0, 0, 16.000, 4, 16),
            Stream.of(
                    Block.box(1.50, 6, 1.5, 14.500, 20, 14.5),
                    Block.box(1.00, 6, 2, 15.000, 20, 14),
                    Block.box(2.00, 6, 1, 14.000, 20, 15),
                    Block.box(5.50, 10.5, 0.9, 10.500, 16.5, 2.9),
                    Stream.of(
                            Stream.of(
                                    Block.box(2.50, 17, 0, 13.500, 21, 16),
                                    Block.box(1.00, 17, 1, 15.000, 21, 15),
                                    Block.box(1.50, 17, 0.5, 14.500, 21, 15.5),
                                    Block.box(0.50, 17, 1.5, 15.500, 21, 14.5),
                                    Block.box(0, 17, 2.5, 16.000, 21, 13.5)
                            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
            Stream.of(
            Block.box(1.00, 19, 0, 2.025, 23.025, 1.0258),
            Block.box(0, 19, 1, 1.025, 23.025, 2.025),
            Block.box(0.50, 19, 0.5, 1.525, 23.025, 1.5258),
            Block.box(1.00, 19, 1, 2.025, 23.025, 2.025)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
            Block.box(0, 19, 13.975, 1.025, 23.025, 15),
            Block.box(1.00, 19, 14.975, 2.025, 23.025, 16),
            Block.box(0.50, 19, 14.475, 1.525, 23.025, 15.5),
            Block.box(1.00, 19, 13.975, 2.025, 23.025, 15)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
            Block.box(13.975, 19, 14.975, 15.000, 23.025, 16),
            Block.box(14.975, 19, 13.975, 16.000, 23.025, 15),
            Block.box(14.475, 19, 14.475, 15.500, 23.025, 15.5),
            Block.box(13.975, 19, 13.975, 15.000, 23.025, 15)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
            Block.box(14.975, 19, 1, 16.000, 23.025, 2.025),
            Block.box(13.975, 19, 0, 15.000, 23.025, 1.0258),
            Block.box(14.475, 19, 0.5, 15.500, 23.025, 1.5258),
            Block.box(13.975, 19, 1, 15.000, 23.025, 2.025)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
            Block.box(2.00, 19, -0.5, 14.000, 23, 1.5),
            Block.box(2.00, 19, 14.5, 14.000, 23, 16.5),
            Block.box(14.5, 19, 2, 16.5, 23, 14),
            Block.box(-0.5, 19, 2, 1.5, 23, 14)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()
).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Block.box(4.50, 19.5, -1.00, 11.500, 22.5, 0),
            Stream.of(
            Block.box(3.00, 19, 16, 13.000, 21, 23),
            Block.box(5.50, 20.25, 19.5, 10.500, 21.25, 22.5),
            Block.box(4.0, 20.955073749564608, 17.951418777963514, 12., 21.455073749564608, 18.951418777963514),
            Block.box(4.0, 21.455073749564608, 17.75141877796351, 12., 21.955073749564608, 18.75141877796351),
            Block.box(4.0, 21.955073749564608, 17.551418777963516, 12., 22.455073749564608, 18.551418777963516),
            Block.box(4.0, 22.455073749564608, 17.326418777963514, 12., 22.955073749564608, 18.326418777963514),
            Block.box(4.0, 22.955073749564615, 17.12641877796351, 12., 23.455073749564615, 18.12641877796351),
            Block.box(4.0, 23.455073749564615, 16.92641877796351, 12., 23.955073749564615, 17.92641877796351),
            Block.box(4.0, 23.955073749564615, 16.72641877796351, 12., 24.455073749564615, 17.72641877796351)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()
).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()
).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Block.box(6.00, 22.75, -0.7506, 10.000, 23.25, -0.25055),
            Stream.of(
            Block.box(-0.5, 0, 1, 16.500, 4, 15),
            Block.box(1.00, 0, -0.50, 15.000, 4, 16.5),
            Block.box(2.00, 0, -1.00, 14.000, 4, 17),
            Block.box(-1, 0, 2, 17.000, 4, 14)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
            Block.box(0.50, 4, 0.5, 15.500, 6, 15.5),
            Block.box(0, 4, 1, 16.000, 6, 15),
            Block.box(1.00, 4, 0, 15.000, 6, 16),
            Block.box(2.00, 4, -0.50, 14.000, 6, 16.5),
            Block.box(-0.5, 4, 2, 16.500, 6, 14)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()
).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    public static final VoxelShape MODEL_SHAPE_EAST = Stream.of(
            Block.box(0, 0, 0, 16, 4, 16),
            Stream.of(
                    Block.box(1.5, 6, 1.5, 14.5, 20, 14.5),
                    Block.box(2, 6, 1, 14, 20, 15),
                    Block.box(1, 6, 2, 15, 20, 14),
                    Block.box(13.1, 10.5, 5.5, 15.1, 16.5, 10.5),
                    Stream.of(
                            Stream.of(
                                    Block.box(0, 17, 2.5, 16, 21, 13.5),
                                    Block.box(1, 17, 1, 15, 21, 15),
                                    Block.box(0.5, 17, 1.5, 15.5, 21, 14.5),
                                    Block.box(1.5, 17, 0.5, 14.5, 21, 15.5),
                                    Block.box(2.5, 17, 0, 13.5, 21, 16)
                            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                            Stream.of(
                                    Stream.of(
                                            Block.box(14.975, 19, 1, 16, 23.025, 2.0250),
                                            Block.box(13.975, 19, 0, 15, 23.025, 1.0250),
                                            Block.box(14.475, 19, 0.5, 15.5, 23.025, 1.5250),
                                            Block.box(13.975, 19, 1, 15, 23.025, 2.0250)
                                    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                                    Stream.of(
                                            Block.box(1, 19, 0, 2.0250, 23.025, 1.0250),
                                            Block.box(0, 19, 1, 1.0250, 23.025, 2.0250),
                                            Block.box(0.5, 19, 0.5, 1.5250, 23.025, 1.5250),
                                            Block.box(1, 19, 1, 2.0250, 23.025, 2.0250)
                                    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                                    Stream.of(
                                            Block.box(0, 19, 13.975, 1.0250, 23.025, 15),
                                            Block.box(1, 19, 14.975, 2.0250, 23.025, 16),
                                            Block.box(0.5, 19, 14.475, 1.5250, 23.025, 15.5),
                                            Block.box(1, 19, 13.975, 2.0250, 23.025, 15)
                                    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                                    Stream.of(
                                            Block.box(13.975, 19, 14.975, 15, 23.025, 16),
                                            Block.box(14.975, 19, 13.975, 16, 23.025, 15),
                                            Block.box(14.475, 19, 14.475, 15.5, 23.025, 15.5),
                                            Block.box(13.975, 19, 13.975, 15, 23.025, 15)
                                    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                                    Stream.of(
                                            Block.box(14.5, 19, 2, 16.5, 23, 14),
                                            Block.box(-0.5, 19, 2, 1.5, 23, 14),
                                            Block.box(2, 19, 14.5, 14, 23, 16.5),
                                            Block.box(2, 19, -0.50, 14, 23, 1.5)
                                    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()
                            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
                            Block.box(16, 19.5, 4.5, 17, 22.5, 11.5),
                            Stream.of(
                                    Block.box(-7, 19, 3, 0, 21, 13),
                                    Block.box(-6.5, 20.25, 5.5, -3.5, 21.25, 10.5),
                                    Block.box(-2.951418777963518, 20.955073749564608, 4, -1.951418777963518, 21.455073749564608, 12),
                                    Block.box(-2.751418777963515, 21.455073749564608, 4, -1.751418777963515, 21.955073749564608, 12),
                                    Block.box(-2.5514187779635193, 21.955073749564608, 4, -1.5514187779635193, 22.455073749564608, 12),
                                    Block.box(-2.326418777963518, 22.455073749564608, 4, -1.326418777963518, 22.955073749564608, 12),
                                    Block.box(-2.126418777963515, 22.955073749564615, 4, -1.126418777963515, 23.455073749564615, 12),
                                    Block.box(-1.9264187779635122, 23.455073749564615, 4, -0.9264187779635122, 23.955073749564615, 12),
                                    Block.box(-1.726418777963513, 23.955073749564615, 4, -0.726418777963513, 24.455073749564615, 12)
                            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()
                    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Block.box(16.25, 22.75, 6, 16.75, 23.25, 10),
            Stream.of(
                    Block.box(1, 0, -0.5, 15, 4, 16.5),
                    Block.box(-0.5, 0, 1, 16.5, 4, 15),
                    Block.box(-1, 0, 2, 17, 4, 14),
                    Block.box(2, 0, -1, 14, 4, 17)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),
            Stream.of(
                    Block.box(0.5, 4, 0.5, 15.5, 6, 15.5),
                    Block.box(1, 4, 0, 15, 6, 16),
                    Block.box(0, 4, 1, 16, 6, 15),
                    Block.box(-0.5, 4, 2, 16.5, 6, 14),
                    Block.box(2, 4, -0.5, 14, 6, 16.5)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();


    public IncubatorBlock(Properties p_52591_) {
        super(p_52591_);

        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));

        Jurassicworldreborn.setRenderType(this, RenderType.cutoutMipped());
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new IncubatorBlockEntity(pPos, pState);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(FACING)){
            case EAST -> MODEL_SHAPE_NORTH;
            case WEST -> MODEL_SHAPE_NORTH;
            case NORTH -> MODEL_SHAPE_NORTH;
            case SOUTH -> MODEL_SHAPE_NORTH;
            default -> MODEL_SHAPE_NORTH;

        };
    }
}
