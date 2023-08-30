package net.gamma02.jurassicworldreborn.common.blocks.entities.cleaner;

import net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities;
import net.gamma02.jurassicworldreborn.common.recipies.cleaner.CleaningRecipie;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class CleanerBlockEntity extends BlockEntity implements BlockEntityTicker<CleanerBlockEntity>, Container, IFluidTank, MenuProvider {

    @Nullable CleaningRecipie currentRecipe;
    private int progress = 0;
    FluidStack fluid = FluidStack.EMPTY;
    private NonNullList<ItemStack> inventory = NonNullList.withSize(8, ItemStack.EMPTY);

    public CleanerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CLEANING_STATION.get(), pos, state);
    }



    @Override
    @ParametersAreNonnullByDefault
    public void tick(Level world, BlockPos pos, BlockState state, CleanerBlockEntity instance) {
        if(progress >= 200){
            this.addItem(this.currentRecipe.assemble(this));
            this.currentRecipe = null;
            progress = 0;
        }else if(this.isCleaning()){
            progress++;
            this.fluid.setAmount(this.getFluidAmount() - 2);

        }
        if(this.currentRecipe == null) {
            for (CleaningRecipie recipie : world.getRecipeManager().getAllRecipesFor(CleaningRecipie.CLEANING_RECIPE_TYPE)) {
                if (recipie.matches(this, world) && this.hasSpace()) {
                    this.currentRecipe = recipie;
                    progress = 0;
                }
            }
        }
        if(this.getFluidAmount() > 1000 && this.getItem(1).is(Items.WATER_BUCKET)){
            this.setItem(1, Items.BUCKET.getDefaultInstance());
        }

    }

    public boolean isCleaning(){
        return this.currentRecipe != null;
    }

    public boolean hasSpace() {
        for(var i = 2; i < this.inventory.size(); i++) {
            if (this.inventory.get(i) == ItemStack.EMPTY) {
                return true;
            }
        }
        return false;
    }

    public int getProgress(){
        return this.progress;
    }

    @Override
    public int getContainerSize() {
        return 8;
    }

    @Override
    public boolean isEmpty() {
        return this.inventory.stream().allMatch((stack) -> stack == ItemStack.EMPTY);
    }

    @Override
    public @NotNull ItemStack getItem(int index) {
        return this.inventory.get(index);
    }

    @Override
    public @NotNull ItemStack removeItem(int index, int amount) {
        return ContainerHelper.removeItem(this.inventory, index, amount);
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int index) {
        return this.inventory.remove(index);
    }

    @Override
    public void setItem(int index, @Nonnull ItemStack stack) {
        this.inventory.set(index, stack);
    }

    public boolean addItem(ItemStack stack){
        for(var i = 2; i < this.inventory.size(); i++){
            if(this.inventory.get(i) == ItemStack.EMPTY){
                this.setItem(i, stack);
                return true;
            }
        }
        return false;

    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        this.inventory.clear();
    }

    @Nonnull
    @Override
    public FluidStack getFluid() {
        return this.fluid;
    }

    @Override
    public int getFluidAmount() {
        return fluid.getAmount();
    }

    @Override
    public int getCapacity() {
        return 2000;
    }

    @Override
    public boolean isFluidValid(FluidStack stack) {
        return stack.getFluid() == Fluids.WATER;
    }

    @Override
    public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
        return 0;
    }

    @Nonnull
    @Override
    public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
        if (resource.isEmpty() || !resource.isFluidEqual(fluid)) {
            return FluidStack.EMPTY;
        }
        return drain(resource.getAmount(), action);
    }

    @Nonnull
    @Override
    public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
        int drained = maxDrain;
        if (fluid.getAmount() < drained) {
            drained = fluid.getAmount();
        }
        FluidStack stack = new FluidStack(fluid, drained);
        if (action.execute() && drained > 0) {
            fluid.shrink(drained);
        }
        return stack;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new CleanerMenu(id, inv, this);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return MutableComponent.create(new TranslatableContents("block.jurassicworldreborn.cleaner_block_name"));
    }
}
