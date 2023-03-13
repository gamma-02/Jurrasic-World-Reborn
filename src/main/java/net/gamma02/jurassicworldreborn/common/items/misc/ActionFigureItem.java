package net.gamma02.jurassicworldreborn.common.items.misc;

import net.gamma02.jurassicworldreborn.common.blocks.ModBlocks;
import net.gamma02.jurassicworldreborn.common.blocks.entities.ActionFigureBlockEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.util.NbtBuilder;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import org.spongepowered.asm.mixin.MixinEnvironment;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ActionFigureItem extends Item {
    public ActionFigureItem(Properties properties) {
        super(properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        super.initializeClient(consumer);


    }
//    @SideOnly(Side.CLIENT)
//    public void initModels(Collection<Dinosaur> dinos, RenderingHandler renderer) {
//        for (Dinosaur dino : dinos) {
//            int dex = EntityHandler.getDinosaurId(dino);
//            String dinoName = dino.getName().toLowerCase(Locale.ENGLISH).replaceAll(" ", "_");
//            renderer.registerItemRenderer(this, getMetadata(dex, 0, false), "action_figure/action_figure_" + dinoName);
//            renderer.registerItemRenderer(this, getMetadata(dex, 1, false), "action_figure/action_figure_" + dinoName);
//            renderer.registerItemRenderer(this, getMetadata(dex, 2, false), "action_figure/action_figure_" + dinoName);
//            if(!dino.isHybrid) {
//                renderer.registerItemRenderer(this, getMetadata(dex, 1, true), "skeleton/fossil/skeleton_fossil_" + dinoName);
//            }
//            renderer.registerItemRenderer(this, getMetadata(dex, 2, true), "skeleton/fresh/skeleton_fresh_" + dinoName);
//        }
//    }

//    @Override
//    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
//        pos = pos.offset(side);
//        ItemStack stack = player.getHeldItem(hand);
//        if (!player.world.isRemote && player.canPlayerEdit(pos, side, stack)) {
//            Block block = BlockHandler.DISPLAY_BLOCK;
//
//            if (block.canPlaceBlockAt(world, pos)) {
//                IBlockState state = block.getDefaultState();
//                world.setBlockState(pos, block.getStateForPlacement(world, pos, side, hitX, hitY, hitZ, 0, player));
//                block.onBlockPlacedBy(world, pos, state, player, stack);
//
//                int mode = this.getVariant(stack);
//
//                DisplayBlockEntity tile = (DisplayBlockEntity) world.getTileEntity(pos);
//
//                if (tile != null) {
//                    tile.setDinosaur(this.getDinosaurID(stack), mode > 0 ? mode == 1 : world.rand.nextBoolean(), this.isSkeleton(stack));
//                    tile.setRot(180 - (int) player.getRotationYawHead());
//                    world.notifyBlockUpdate(pos, state, state, 0);
//                    tile.markDirty();
//                    if (!player.capabilities.isCreativeMode) {
//                        stack.shrink(1);
//                    }
//                }
//            }
//        }
//
//        return EnumActionResult.SUCCESS;
//    }
//
//    @Override
//    public InteractionResult useOn(UseOnContext ctx) {
//        BlockPos pos = ctx.getClickedPos().relative(ctx.getClickedFace());
//        Player player = ctx.getPlayer();
//        BlockPlaceContext bctx = new BlockPlaceContext(ctx);
//        ItemStack stack = player.getItemInHand(ctx.getHand());
//        if (!player.level.isClientSide && bctx.canPlace()) {
//            Block block = ModBlocks.DISPLAY_BLOCK.get();
//
//            if () {
//
//                int mode = this.getVariant(stack);
//
////                DisplayBlockEntity tile = (DisplayBlockEntity) world.getTileEntity(pos);
//
//                if (tile != null) {
//                    tile.setDinosaur(this.getDinosaurID(stack), mode > 0 ? mode == 1 : world.rand.nextBoolean(), this.isSkeleton(stack));
//                    tile.setRot(180 - (int) player.getRotationYawHead());
////                    world.notifyBlockUpdate(pos, state, state, 0);
//                    tile.markDirty();
//                    if (!player.capabilities.isCreativeMode) {
//                        stack.shrink(1);
//                    }
//                }
//            }
//        }
//
//        return EnumActionResult.SUCCESS;
//    }


    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        InteractionResult interactionresult = this.place(new BlockPlaceContext(pContext));
        if (!interactionresult.consumesAction() && this.isEdible()) {
            InteractionResult interactionresult1 = this.use(pContext.getLevel(), pContext.getPlayer(), pContext.getHand()).getResult();
            return interactionresult1 == InteractionResult.CONSUME ? InteractionResult.CONSUME_PARTIAL : interactionresult1;
        } else {
            return interactionresult;
        }
    }

    public InteractionResult place(BlockPlaceContext pContext) {
        if (!pContext.canPlace()) {
            return InteractionResult.FAIL;
        } else {
            BlockPlaceContext blockplacecontext = pContext;
            if (blockplacecontext == null) {
                return InteractionResult.FAIL;
            } else {
                BlockState blockstate = this.getPlacementState(blockplacecontext);
                if (blockstate == null) {
                    return InteractionResult.FAIL;
                } else if (!this.placeBlock(blockplacecontext, blockstate)) {
                    return InteractionResult.FAIL;
                } else {
                    BlockPos blockpos = blockplacecontext.getClickedPos();
                    Level level = blockplacecontext.getLevel();
                    Player player = blockplacecontext.getPlayer();
                    ItemStack itemstack = blockplacecontext.getItemInHand();
                    BlockState blockstate1 = level.getBlockState(blockpos);
                    if (blockstate1.is(blockstate.getBlock())) {
                        blockstate1 = this.updateBlockStateFromTag(blockpos, level, itemstack, blockstate1);
                        this.updateCustomBlockEntityTag(blockpos, level, player, itemstack, blockstate1);
                        blockstate1.getBlock().setPlacedBy(level, blockpos, blockstate1, player, itemstack);
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, blockpos, itemstack);
                        }
                    }

                    level.gameEvent(player, GameEvent.BLOCK_PLACE, blockpos);
                    SoundType soundtype = blockstate1.getSoundType(level, blockpos, pContext.getPlayer());
                    level.playSound(player, blockpos, blockstate1.getSoundType().getPlaceSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    if(level.getBlockEntity(blockpos) instanceof ActionFigureBlockEntity entity) {
                        int mode = this.getVariant(itemstack);
                        entity.setDinosaur(this.getDinosaurID(itemstack), mode > 0 ? mode == 1 : level.random.nextBoolean(), this.isSkeleton(itemstack));
                        entity.setRot(180 - (int) player.yHeadRot);
                        entity.setChanged();
                    }
                    if (player == null || !player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }

                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
        }
    }

    @Nullable
    protected BlockState getPlacementState(BlockPlaceContext pContext) {
        BlockState blockstate = ModBlocks.DISPLAY_BLOCK.get().getStateForPlacement(pContext);
        return blockstate != null && this.canPlace(pContext, blockstate) ? blockstate : null;
    }
    protected boolean canPlace(BlockPlaceContext pContext, BlockState pState) {
        Player player = pContext.getPlayer();
        CollisionContext collisioncontext = player == null ? CollisionContext.empty() : CollisionContext.of(player);
        return (pState.canSurvive(pContext.getLevel(), pContext.getClickedPos())) && pContext.getLevel().isUnobstructed(pState, pContext.getClickedPos(), collisioncontext);
    }
    protected boolean placeBlock(BlockPlaceContext pContext, BlockState pState) {
        return pContext.getLevel().setBlock(pContext.getClickedPos(), pState, 11);
    }
    private BlockState updateBlockStateFromTag(BlockPos pPos, Level pLevel, ItemStack pStack, BlockState pState) {
        BlockState blockstate = pState;
        CompoundTag compoundtag = pStack.getTag();
        if (compoundtag != null) {
            CompoundTag compoundtag1 = compoundtag.getCompound("BlockStateTag");
            StateDefinition<Block, BlockState> statedefinition = pState.getBlock().getStateDefinition();

            for(String s : compoundtag1.getAllKeys()) {
                Property<?> property = statedefinition.getProperty(s);
                if (property != null) {
                    String s1 = compoundtag1.get(s).getAsString();
                    blockstate = updateState(blockstate, property, s1);
                }
            }
        }

        if (blockstate != pState) {
            pLevel.setBlock(pPos, blockstate, 2);
        }

        return blockstate;
    }

    private static <T extends Comparable<T>> BlockState updateState(BlockState pState, Property<T> pProperty, String pValueIdentifier) {
        return pProperty.getValue(pValueIdentifier).map((p_40592_) -> {
            return pState.setValue(pProperty, p_40592_);
        }).orElse(pState);
    }
    public static boolean updateCustomBlockEntityTag(Level pLevel, @Nullable Player pPlayer, BlockPos pPos, ItemStack pStack) {
        MinecraftServer minecraftserver = pLevel.getServer();
        if (minecraftserver == null) {
            return false;
        } else {
            CompoundTag compoundtag = getBlockEntityData(pStack);
            if (compoundtag != null) {
                BlockEntity blockentity = pLevel.getBlockEntity(pPos);
                if (blockentity != null) {
                    if (!pLevel.isClientSide && blockentity.onlyOpCanSetNbt() && (pPlayer == null || !pPlayer.canUseGameMasterBlocks())) {
                        return false;
                    }

                    CompoundTag compoundtag1 = blockentity.saveWithoutMetadata();
                    CompoundTag compoundtag2 = compoundtag1.copy();
                    compoundtag1.merge(compoundtag);
                    if (!compoundtag1.equals(compoundtag2)) {
                        blockentity.load(compoundtag1);
                        blockentity.setChanged();
                        return true;
                    }
                }
            }

            return false;
        }
    }

    @Nullable
    public static CompoundTag getBlockEntityData(ItemStack p_186337_) {
        return p_186337_.getTagElement("BlockEntityTag");
    }

    protected boolean updateCustomBlockEntityTag(BlockPos pPos, Level pLevel, @Nullable Player pPlayer, ItemStack pStack, BlockState pState) {
        return updateCustomBlockEntityTag(pLevel, pPlayer, pPos, pStack);
    }

//    @Override todo:langutils
//    public Component getName(ItemStack stack) {
//        String dinoName = LangUtils.getDinoName(this.getDinosaur(stack));
//        if (!this.isSkeleton(stack)) {
//            return LangUtils.translate("item.action_figure.name").replace("{dino}", dinoName);
//        }
//        return LangUtils.translate("item.skeleton." + (this.getVariant(stack) == 1 ? "fossil" : "fresh") + ".name").replace("{dino}", dinoName);
//    }

    public Dinosaur getDinosaur(ItemStack stack) {
        return Dinosaur.getDinosaurByName(getDinosaurID(stack));
    }

//    @Override todo:p  port this
//    @OnlyIn(Dist.CLIENT)
//    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subtypes) {
//        List<Dinosaur> dinosaurs = new LinkedList<>(EntityHandler.getDinosaurs().values());
//
//        Collections.sort(dinosaurs);
//        if(this.getCreativeTabs().contains(tab)) {
//            for (Dinosaur dinosaur : dinosaurs) {
//                if (dinosaur.shouldRegister()) {
//                    subtypes.add(new ItemStack(this, 1, getMetadata(EntityHandler.getDinosaurId(dinosaur), 0, false)));
//                    if (!dinosaur.isHybrid)
//                        subtypes.add(new ItemStack(this, 1, getMetadata(EntityHandler.getDinosaurId(dinosaur), 1, true)));
//                    subtypes.add(new ItemStack(this, 1, getMetadata(EntityHandler.getDinosaurId(dinosaur), 2, true)));
//                }
//            }
//        }
//    }

    public static String DINOSAUR_ID_TAG = "DinosaurID";

    public static String VARIANT_TAG = "Variant";

    public static String SKELETON_TAG = "IsSkeleton";



    public static String getDinosaurID(ItemStack stack) {
        return stack.getTag().getString("DinosaurID");
    }

    public static byte getVariant(ItemStack stack) {
        return (byte) (stack.getTag().getByte("Variant") >> (byte)(1 & 7));
    }

    public static boolean isSkeleton(ItemStack stack) {
        return (stack.getTag().getBoolean("IsSkeleton"));
    }

    public static void setDinosaurID(ItemStack stack, String dinosaurID) {
        stack.getTag().putString(DINOSAUR_ID_TAG, dinosaurID);
    }

    public static void setVariant(ItemStack stack, byte variant) {
        stack.getTag().putByte(VARIANT_TAG, variant);
    }
    public static void setSkeleton(ItemStack stack, boolean skeleton){
        stack.getTag().putBoolean(SKELETON_TAG, skeleton);
    }

    public static ItemStack setAll(ItemStack stack, String dino, byte variant, boolean skeleton){
        setDinosaurID(stack, dino);
        setVariant(stack, variant);
        setSkeleton(stack, skeleton);
        return stack;
    }

    public int changeMode(ItemStack stack) {
        String dinosaur = this.getDinosaurID(stack);
        boolean skeleton = this.isSkeleton(stack);

        int mode = this.getVariant(stack) + 1;
        mode %= 3;

//        stack.setItemDamage(getMetadata(dinosaur, mode, skeleton));

        return mode;
    }

    @Override
    public Component getDescription() {
        return super.getDescription();
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack basic = new ItemStack(this);

        basic.setTag(new NbtBuilder(basic.getTag()).putString(DINOSAUR_ID_TAG, Dinosaur.DINOS.get(0).getName()).putBoolean(SKELETON_TAG, false).putByte(VARIANT_TAG, (byte)0 ).build());


        return basic;
    }

    //    @Override todo: langutils, lore adder
//    @OnlyIn(Dist.CLIENT)
//    public void addInformation(ItemStack stack, World world, List<String> lore, ITooltipFlag tooltipFlag) {
//        if (!this.isSkeleton(stack)) {
//            lore.add(TextFormatting.BLUE + I18n.format("lore.change_gender.name"));
//        }
//    }
//
//    @Override
//    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
//        ItemStack stack = player.getHeldItem(hand);
//        if (!this.isSkeleton(stack)) {
//            int mode = this.changeMode(stack);
//            if (world.isRemote) {
//                player.sendMessage(new TextComponentString(LangUtils.translate(LangUtils.GENDER_CHANGE.get("actionfigure")).replace("{mode}", LangUtils.getGenderMode(mode))));
//            }
//            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
//        }
//        return new ActionResult<>(EnumActionResult.PASS, stack);
//    }
}
