package net.gamma02.jurassicworldreborn.common.items.misc;

import net.gamma02.jurassicworldreborn.client.JurassicClient;
import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.gamma02.jurassicworldreborn.common.blocks.entities.ActionFigureBlockEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.DinosaurHandler;
import net.gamma02.jurassicworldreborn.common.util.LangUtil;
import net.gamma02.jurassicworldreborn.common.util.NbtBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class ActionFigureItem extends Item {

    //todo: get this to work

    private final Dinosaur dino;
    private final boolean isSkeleton;

    private final boolean fresh;
    //NBT fields: variant, mode

    public ActionFigureItem(Properties properties, Dinosaur dino, boolean isSkeleton, boolean fresh) {
        super(properties);
        this.dino = dino;
        this.isSkeleton = isSkeleton;
        this.fresh = !isSkeleton || fresh;
    }



    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        IClientItemExtensions prop = new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return JurassicClient.displayBlockRendererWithoutLevel;
            }
        };
        consumer.accept(prop);//jankiest shit ive seen please work - gamma
    }

    public boolean isSkeleton(){
        return this.isSkeleton;
    }

    public boolean isSkeleton(ItemStack pStack){
        if(pStack.getItem() == this){
            return this.isSkeleton();
        }else if(pStack.getItem() instanceof ActionFigureItem afi){
            return afi.isSkeleton(pStack);
        }
        return false;//default value
    }

    public Dinosaur getDinosaur(){
        return this.dino;
    }

    public Dinosaur getDinosaur(ItemStack pStack){
        if(pStack.getItem() == this){
            return this.getDinosaur();
        }else if(pStack.getItem() instanceof ActionFigureItem afi){
            return afi.getDinosaur();
        }
        return Dinosaur.EMPTY;
    }

    public int getGender(ItemStack stack){
        CompoundTag tag = stack.getOrCreateTag();

        if(tag.contains("Gender")){
            return getGender(tag.getString("Gender"));
        }
        //init gender if the stack doesn't have one
        tag.putString("Gender", "random");
        stack.setTag(tag);
        return 0;
    }

    public int getGender(String gender){
        if(gender.equals("random"))
            return 0;
        if (gender.equals("male"))
            return 1;
        if(gender.equals("female"))
            return 2;
        return 0;
    }
    public String getGender(int gender){
        if(gender == 0)
            return "random";
        if(gender == 1)
            return "male";
        if(gender == 2)
            return "female";
        return "";
    }

    public int changeGender(ItemStack stack){
        int gender = getGender(stack);

        int newGender = (gender + 1) % 3;

        CompoundTag stackTag = stack.getOrCreateTag();
        stackTag.putString("Gender", getGender(newGender));
        stack.setTag(stackTag);

        return newGender;

    }

    public boolean isFresh() {
        return this.fresh;
    }

    public boolean isFresh(ItemStack stack){
        if(stack.getItem() == this)
            return this.isFresh();
        if( stack.getItem() instanceof ActionFigureItem afi)
            return afi.isFresh(stack);
        return true;
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack pStack) {
        if(this.isSkeleton(pStack))
            return LangUtil.replaceWithDinoName(this.getDinosaur(pStack), "item.jurassicworldreborn.skeleton." + (this.isFresh(pStack) ? "fresh" : "fossil"));

        return LangUtil.replaceWithDinoName(this.getDinosaur(pStack), "item.jurassicworldreborn.action_figure");
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        if(this.isSkeleton)
            return;

        pTooltipComponents.add(Component.translatable("lore.change_gender").withStyle(ChatFormatting.BLUE));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);

        if(this.isSkeleton(stack))
            return new InteractionResultHolder<>(InteractionResult.PASS, stack);

        int gender = this.changeGender(stack);

        if(pLevel.isClientSide)
            pPlayer.displayClientMessage(Component.translatable("actionfigure.genderchange", LangUtil.getGender(gender).getString()), false);

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);

    }
    //yoinked from BlockItem
    protected boolean canPlace(BlockPlaceContext pContext, BlockState pState) {
        Player player = pContext.getPlayer();
        CollisionContext collisioncontext = player == null ? CollisionContext.empty() : CollisionContext.of(player);
        return (pState.canSurvive(pContext.getLevel(), pContext.getClickedPos())) && pContext.getLevel().isUnobstructed(pState, pContext.getClickedPos(), collisioncontext);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
        Level world = context.getLevel();
        if(context.getLevel().isClientSide)
            return InteractionResult.PASS;

        Block block = ModBlocks.DISPLAY_BLOCK.get();
        BlockState state = block.defaultBlockState();

        if(!canPlace(new BlockPlaceContext(context), state))
            return InteractionResult.FAIL;

        BlockState state1 = block.getStateForPlacement(new BlockPlaceContext(context));

        if (state1 == null) {
            return InteractionResult.FAIL;
        }

        world.setBlock(pos, state1, 3);
        block.setPlacedBy(world, pos, state1, context.getPlayer(), stack);
//        block.onPlace(state1, world, pos, Blocks.AIR.defaultBlockState(), false);
        int gender = this.getGender(stack);

        if(!(world.getBlockEntity(pos) instanceof ActionFigureBlockEntity afbe))
            return InteractionResult.PASS;

        afbe.setDinosaur(this.getDinosaur(stack), gender > 0 ? gender == 1 : world.getRandom().nextBoolean(), this.isSkeleton(stack));

        afbe.setRot(180 - (int) Objects.requireNonNull(context.getPlayer()).getYHeadRot());

        world.updateNeighborsAt(pos, block);

        afbe.setChanged();

        if(!context.getPlayer().isCreative())
            stack.shrink(1);

        return InteractionResult.SUCCESS;
    }

    @Override
    public void fillItemCategory(CreativeModeTab pCategory, NonNullList<ItemStack> pItems) {
        if(this.allowedIn(pCategory)){
            ItemStack defaultStack = this.getDefaultInstance();
            CompoundTag tag = defaultStack.getOrCreateTag();
            tag.putString("Gender", "random");
            defaultStack.setTag(tag);

            pItems.add(defaultStack);
        }
    }
}
