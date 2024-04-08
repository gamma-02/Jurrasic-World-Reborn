package net.gamma02.jurassicworldreborn.common.blocks.entities.cleaner;

import net.gamma02.jurassicworldreborn.common.blocks.entities.MachineBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities;
import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.gamma02.jurassicworldreborn.common.network.Network;
import net.gamma02.jurassicworldreborn.common.recipies.cleaner.CleaningRecipie;
import net.gamma02.jurassicworldreborn.common.util.api.CleanableItem;
import net.gamma02.jurassicworldreborn.common.util.networking.BlockUpdateUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class CleanerBlockEntity extends MachineBlockEntity<CleanerBlockEntity> implements IFluidTank {

    @Nullable CleaningRecipie currentRecipe;
    private boolean usingCleaningRecipe = true;
    private int progress = 0;
    FluidStack fluid = FluidStack.EMPTY;
    private NonNullList<ItemStack> inventory = NonNullList.withSize(8, ItemStack.EMPTY);

    public final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int pIndex) {
            if(pIndex == 0){
                return CleanerBlockEntity.this.getFluidAmount();
            }else if(pIndex == 1){
                return CleanerBlockEntity.this.getProgress();
            }else{
                return 0;
            }
        }

        @Override
        public void set(int pIndex, int pValue) {
            if(pIndex == 0){
                CleanerBlockEntity.this.fluid.setAmount(pValue);
            }else if(pIndex == 1){
                CleanerBlockEntity.this.progress = pValue;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    public CleanerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CLEANING_STATION.get(), pos, state);


        Network.ENTITIES.add(this);
    }



    @Override
    @ParametersAreNonnullByDefault
    public void tick(Level world, BlockPos pos, BlockState state, CleanerBlockEntity instance) {
        super.tick(world, pos, state, instance);
        ItemStack input = this.getItem(0);

        if(progress >= 200 && this.currentRecipe != null){

            this.addItem(this.currentRecipe.assemble(this));
            this.currentRecipe = null;

            progress = 0;
            this.getItem(0).setCount(this.getItem(0).getCount() - 1);

        } else if (progress >= 200 && input.getItem() == ModItems.ENCASED_FAUNA_FOSSIL.get()) {

            ItemStack output = ((CleanableItem) input.getItem()).getCleanedItem(input, world.getRandom());
            input.shrink(1);
            this.addItem(output);
            progress = 0;


        }

        if (this.isCleaning()) {

            progress++;
            this.fluid.setAmount(this.getFluidAmount() - 2);

        }

        this.usingCleaningRecipe = true;

        if (input.getItem() == ModItems.ENCASED_FAUNA_FOSSIL.get()) {
            this.usingCleaningRecipe = false;


        }

        if(this.getItem(0) == ItemStack.EMPTY){
            this.progress = 0;
        }

        if(this.currentRecipe == null && this.usingCleaningRecipe) {
            for (CleaningRecipie recipie : world.getRecipeManager().getAllRecipesFor(CleaningRecipie.CLEANING_RECIPE_TYPE)) {
                if (recipie.matches(this, world) && this.hasSpace()) {
                    this.currentRecipe = recipie;
                    progress = 0;
                }
            }
        }


        if(this.getFluidAmount() <= 0 && this.getItem(1).is(Items.WATER_BUCKET)){
            this.fluid = new FluidStack(Fluids.WATER, 1000);
            this.setItem(1, Items.BUCKET.getDefaultInstance());
        }

    }

    public boolean isCleaning(){
        return ((this.getItem(0).getItem() instanceof CleanableItem) && this.hasSpace() && this.fluid.getAmount() != 0) || this.currentRecipe != null;
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

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.inventory = nonNullList;
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



    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new CleanerMenu(i, inventory, this, this.dataAccess);
    }


    @Override
    protected Component getDefaultName() {
        return MutableComponent.create(new TranslatableContents("block.jurassicworldreborn.cleaner_block_name"));
    }

    @Override
    public Tag getMachineData() {
        CompoundTag pTag = new CompoundTag();
        pTag.putInt("Progress", this.progress);
        CompoundTag fluid = new CompoundTag();
        this.fluid.writeToNBT(fluid);
        pTag.put("Fluid", fluid);
        ContainerHelper.saveAllItems(pTag, this.inventory, true);
        return pTag;
    }

    @Override
    public void readMachineData(Tag machineData) {
        if(machineData instanceof CompoundTag pTag) {
            int progress = pTag.getInt("Progress");
            var fluid = FluidStack.loadFluidStackFromNBT(pTag.getCompound("Fluid"));
            NonNullList<ItemStack> inventory = NonNullList.withSize(8, ItemStack.EMPTY);
            ContainerHelper.loadAllItems(pTag, inventory);
            this.progress = progress;
            this.fluid = fluid;
            this.inventory = inventory;
        }
    }

    @Override
    public boolean canProcess(ItemStack... inputs) {
        return false;
    }

    @Override
    public @NotNull List<ItemStack> processItem(ItemStack... inputs) {
        return null;
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        return new int[0];
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, ItemStack itemStack, @org.jetbrains.annotations.Nullable Direction direction) {
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction) {
        return false;
    }
}
