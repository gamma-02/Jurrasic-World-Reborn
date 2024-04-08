package net.gamma02.jurassicworldreborn.common.blocks.entities.cultivator;

import com.google.common.primitives.Ints;
import net.gamma02.jurassicworldreborn.common.blocks.entities.MachineBlockEntity;
import net.gamma02.jurassicworldreborn.common.blocks.entities.ModBlockEntities;
import net.gamma02.jurassicworldreborn.common.entities.DinosaurEntity;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.gamma02.jurassicworldreborn.common.genetics.DinoDNA;
import net.gamma02.jurassicworldreborn.common.genetics.GeneticsHelper;
import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.gamma02.jurassicworldreborn.common.items.genetics.SyringeItem;
import net.gamma02.jurassicworldreborn.common.util.block.TemperatureControl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CultivatorBlockEntity extends MachineBlockEntity<CultivatorBlockEntity> implements TemperatureControl, IFluidTank {
    private static final int[] INPUTS = new int[] {0, 1, 2, 3};
    private static final int[] OUTPUTS = new int[] {0, 3, 4};
    public static final int MAX_NUTRIENTS = 3000;

    public static final int STACK_PROCESS_TIME = 2000;

    /**
     * <table>
     *     <thread>
     *         <tr>
     *             <th scope="col">Index</th>
     *             <th scope="col">Field</th>
     *         </tr>
     *     </thread>
     *     <tbody>
     *         <tr>
     *             <th scope="row">0</th>
     *             <th scope="row">Fluid level</th>
     *         </tr>
     *         <tr>
     *             <th scope="row">1</th>
     *             <th scope="row">Lipids</th>
     *         </tr>
     *         <tr>
     *             <th scope="row">2</th>
     *             <th scope="row">Proximates</th>
     *         </tr>
     *         <tr>
     *             <th scope="row">3</th>
     *             <th scope="row">Minerals</th>
     *         </tr>
     *         <tr>
     *             <th scope="row">4</th>
     *             <th scope="row">Vitamins</th>
     *         </tr>
     *         <tr>
     *             <th scope="row">5</th>
     *             <th scope="row">Temperature</th>
     *         </tr>
     *         <tr>
     *             <th scope="row">6</th>
     *             <th scope="row">Process time</th>
     *         </tr>
     *         <tr>
     *             <th scope="row">7</th>
     *             <th scope="row">X</th>
     *         </tr>
     *         <tr>
     *             <th scope="row">8</th>
     *             <th scope="row">Y</th>
     *         </tr>
     *         <tr>
     *             <th scope="row">9</th>
     *             <th scope="row">Z</th>
     *         </tr>
     *     </tbody>
     * </table>
     */
    private ContainerData cultivatorBlockData = new ContainerData() {

        @Override
        public int get(int pIndex) {
            return switch (pIndex) {
                case 0 -> CultivatorBlockEntity.this.fluid.getAmount();
                case 1 -> CultivatorBlockEntity.this.lipids;
                case 2 -> CultivatorBlockEntity.this.proximates;
                case 3 -> CultivatorBlockEntity.this.minerals;
                case 4 -> CultivatorBlockEntity.this.vitamins;
                case 5 -> CultivatorBlockEntity.this.temperature;
                case 6 -> CultivatorBlockEntity.this.processTime;
                case 7 -> CultivatorBlockEntity.this.worldPosition.getX();
                case 8 -> CultivatorBlockEntity.this.worldPosition.getY();
                case 9 -> CultivatorBlockEntity.this.worldPosition.getZ();

                default -> 0;
            };
        }

        @Override
        public void set(int pIndex, int pValue) {
            switch (pIndex) {
                case 0 -> CultivatorBlockEntity.this.fluid.setAmount(pValue);
                case 1 -> CultivatorBlockEntity.this.lipids = pValue;
                case 2 -> CultivatorBlockEntity.this.proximates = pValue;
                case 3 -> CultivatorBlockEntity.this.minerals = pValue;
                case 4 -> CultivatorBlockEntity.this.vitamins = pValue;
                case 5 -> CultivatorBlockEntity.this.temperature = pValue;
                case 6 -> CultivatorBlockEntity.this.processTime = pValue;
            };
        }

        public BlockPos getBlockPos(){
            return new BlockPos(this.get(7), this.get(8), this.get(9));
        }

        @Override
        public int getCount() {
            return 10;
        }
    };

    private NonNullList<ItemStack> inventory = NonNullList.withSize(5, ItemStack.EMPTY);
    private FluidStack fluid = FluidStack.EMPTY;

    private int lipids;
    private int proximates;
    private int minerals;
    private int vitamins;
    private int temperature;
    private int processTime;

    private DinosaurEntity dinosaurEntity; //for rendering(we'll see about THAT) - gamma

    public CultivatorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CULTIVATOR_BLOCK_ENTITY_TYPE.get(), pPos, pBlockState);
    }


    @Override
    public Tag getMachineData() {
        CompoundTag machineData = new CompoundTag();
        CompoundTag fluid = new CompoundTag();
        this.fluid.writeToNBT(fluid);
        machineData.put("Fluid", fluid);

        machineData.putInt("Lipids", this.lipids);
        machineData.putInt("Minerals", this.minerals);
        machineData.putInt("Vitamins", this.vitamins);
        machineData.putInt("Proximates", this.proximates);
        machineData.putInt("Temperature", this.temperature);
        machineData.putInt("ProcessTime", this.processTime);

        return machineData;
    }

    @Override
    public void readMachineData(Tag data) {
        if(!(data instanceof CompoundTag machineData))
            return;

        this.fluid = FluidStack.loadFluidStackFromNBT(machineData.getCompound("Fluid"));

        this.lipids = machineData.getInt("Lipids");
        this.proximates = machineData.getInt("Proximates");
        this.minerals = machineData.getInt("Minerals");
        this.vitamins = machineData.getInt("Vitamins");
        this.temperature = machineData.getInt("Temperature");
        this.processTime = machineData.getInt("ProcessTime");
    }

    @Override
    public boolean canProcess(ItemStack... inputs) {

        ItemStack itemstack = inputs[0];

        if(!this.inventory.get(4).isEmpty())
            return false;

        if (itemstack.getItem() instanceof SyringeItem i && this.fluid.getAmount() >= 2000) {
            Dinosaur dino = i.getDinosaur(itemstack);
            if (dino == null) {
                return false;
            }
            Dinosaur meta = dino;
            if (meta.getBirthType() == Dinosaur.BirthType.LIVE_BIRTH) {
                return this.lipids >= meta.getLipids() && this.minerals >= meta.getMinerals() && this.proximates >= meta.getProximates() && this.vitamins >= meta.getVitamins();
            }
        }
        return false;
    }

    @Override
    public @NotNull List<ItemStack> processItem(ItemStack... inputs) {
        ItemStack syringe = inputs[0];
        if(!(syringe.getItem() instanceof SyringeItem syringeItem))
            return List.of(ItemStack.EMPTY);
        Dinosaur dino = syringeItem.getDinosaur(syringe);

        if(dino == null){
            return List.of(ItemStack.EMPTY);
        }

        ItemStack hatchedEgg = ModItems.hatchedDinoEggs.get(dino).get().getDefaultInstance();

        CompoundTag newNbt = new CompoundTag();
        newNbt.putBoolean("Gender", this.temperature > 50);

        DinoDNA dna = DinoDNA.fromStack(syringe);
        if(dna == null)
            dna = new DinoDNA(dino, 100, GeneticsHelper.randomGenetics(this.level.getRandom()));

        dna.writeToNBT(newNbt);


        hatchedEgg.setTag(newNbt);
        this.decrementResources(dino);


        return List.of(hatchedEgg);

    }

    private void decrementResources(Dinosaur dino){

        this.lipids -= dino.getLipids();
        this.minerals -= dino.getMinerals();
        this.vitamins -= dino.getVitamins();
        this.proximates -= dino.getProximates();

        this.fluid.shrink(1000);//remove one bucket of fluid
    }

    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction pSide) {
        return pSide == Direction.DOWN ? OUTPUTS : INPUTS;
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, @NotNull ItemStack pItemStack, @Nullable Direction pDirection) {
        return Ints.asList(pDirection == null ? new int[]{0, 1, 2, 3, 4} : this.getSlotsForFace(pDirection)).contains(pIndex) && this.canPlaceItem(pIndex, pItemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, @NotNull ItemStack pStack, @NotNull Direction pDirection) {
        return Ints.asList(this.getSlotsForFace(pDirection)).contains(pIndex) && this.canPlaceItem(pIndex, pStack);

    }

    @Override
    protected @NotNull NonNullList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    protected void setItems(@NotNull NonNullList<ItemStack> pItemStacks) {
        this.inventory = pItemStacks;
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("container.cultivator");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory) {
        return new CultivatorMenu(pContainerId, this, this.cultivatorBlockData, pInventory);
    }

    @Override
    public int getContainerSize() {
        return 5;
    }


    /**
     * <table>
     *    <thread>
     *        <tr>
     *            <th scope="col">Index</th>
     *            <th scope="col">Stack</th>
     *            <th scope="col">Inventory index</th>
     *        </tr>
     *    </thread>
     *    <tbody>
     *        <tr>
     *            <th>0</th>
     *            <th>Syringe input</th>
     *            <th>0</th>
     *        </tr>
     *        <tr>
     *            <th>1</th>
     *            <th>Food input</th>
     *            <th>1</th>
     *        </tr>
     *        <tr>
     *            <th>2</th>
     *            <th>Water bucket input</th>
     *            <th>2</th>
     *        </tr>
     *        <tr>
     *            <th>3</th>
     *            <th>Bucket output</th>
     *            <th>3</th>
     *        </tr>
     *    </tbody>
     * </table>
     * @param flags
     * @return
     */
    @Override
    public ItemStack[] collectInputs(int... flags) {
        return new ItemStack[]{
                this.inventory.get(0),
                this.inventory.get(1),
                this.inventory.get(2),
                this.inventory.get(3)
        };
    }

    @Override
    public void tick(@NotNull Level world, @NotNull BlockPos pPos, @NotNull BlockState pState, @NotNull CultivatorBlockEntity pBlockEntity) {
        super.tick(world, pPos, pState, pBlockEntity);

        if(world.isClientSide)
            return;




        ItemStack[] inputs = this.collectInputs();

        if (this.processTime >= STACK_PROCESS_TIME && this.canProcess(inputs)) {

            ItemStack output = this.processItem(inputs).get(0);
            this.setItem(0, output);
            this.drain(new FluidStack(Fluids.WATER, 1000), IFluidHandler.FluidAction.EXECUTE);

            this.processTime = 0;

            inputs = this.collectInputs();

        }

        if (this.canProcess(inputs)) {
            this.processTime++;
        } else {
            this.processTime = 0;
        }

        //handle fluids
        if(this.fluid.getAmount() < this.getCapacity() && this.inventory.get(2).getItem() == Items.WATER_BUCKET &&
                (this.inventory.get(3).getCount() < this.inventory.get(3).getMaxStackSize() || this.inventory.get(3).isEmpty())){
            this.fill(new FluidStack(Fluids.WATER, 1000), IFluidHandler.FluidAction.EXECUTE);
            this.decreaseStackSize(2);
            if(this.inventory.get(3).isEmpty()){
                this.inventory.set(3, Items.BUCKET.getDefaultInstance());
            }else{
                this.inventory.get(3).grow(1);
            }
        }

        ItemStack foodItem = this.inventory.get(1);
        if(!foodItem.isEmpty() && (this.proximates < MAX_NUTRIENTS) || (this.minerals < MAX_NUTRIENTS) || (this.vitamins < MAX_NUTRIENTS) || (this.lipids < MAX_NUTRIENTS)){
            this.consumeNutrients();
        }




    }

    private void consumeNutrients() {

        if(this.level == null)
            return;

        ItemStack foodStack = this.inventory.get(1);
        FoodNutrients nutrients = FoodNutrients.get(foodStack.getItem());

        if(nutrients == null)
            return;

        if (foodStack.getItem() instanceof MilkBucketItem) {
            this.inventory.set(1, new ItemStack(Items.BUCKET));
        } else {
            foodStack.shrink(1);


            //girl help what(in refrence to the commended out code below)
            //like actually what was this
            //was that method used to set emptiness?????????????
            //how????
            //so many coders worked on the legacy code that it ended with something actually deranged

//            if (foodStack.getCount() <= 0) {
//                foodStack.isEmpty();
//            }
        }

        RandomSource random = this.level.getRandom();
        if (this.proximates < MAX_NUTRIENTS) {
            this.proximates = Math.min((int) (this.proximates + (800 + random.nextInt(201)) * nutrients.getProximate()), MAX_NUTRIENTS);//in legacy, the first term of the min call was cast to a short... why? the variable type was an int?

        }

        if (this.minerals < MAX_NUTRIENTS) {
            this.minerals = Math.min((int) (this.minerals + (900 + random.nextInt(101)) * nutrients.getMinerals()), MAX_NUTRIENTS);

        }

        if (this.vitamins < MAX_NUTRIENTS) {
            this.vitamins = Math.min( (int) (this.vitamins + (900 + random.nextInt(101)) * nutrients.getVitamins()), MAX_NUTRIENTS );

        }

        if (this.lipids < MAX_NUTRIENTS) {
            this.lipids = Math.min((int) (this.lipids + (980 + random.nextInt(101)) * nutrients.getLipids()), MAX_NUTRIENTS);

        }
    }

    @Override
    public boolean canPlaceItem(int pIndex, @NotNull ItemStack pStack) {

        if (Ints.asList(INPUTS).contains(pIndex)) {
            if ((pIndex == 0 && this.getItem(pIndex).getCount() == 0 && pStack.getItem() instanceof SyringeItem syringe && syringe.getDinosaur(pStack).getBirthType() == Dinosaur.BirthType.LIVE_BIRTH)
                    || (pIndex == 1 && FoodNutrients.NUTRIENTS.containsKey(pStack.getItem()))
                    || (pIndex == 2 && pStack.getItem() == Items.WATER_BUCKET)) {

                return true;
            }
        }

        return false;
    }


    @Override
    public @NotNull ItemStack getItem(int pIndex) {
        return this.inventory.get(pIndex);
    }

    @Nullable
    private DinosaurEntity createEntity(){
        if(!(this.getItem(0).getItem() instanceof SyringeItem item) || (this.level == null))
            return null;

        this.dinosaurEntity = DinosaurEntity.CLASS_TYPE_LIST.get(item.getDinosaur(this.getItem(0)).getDinosaurClass()).get().create(this.level);

        if(dinosaurEntity == null){
            return null;
        }

        this.dinosaurEntity.setMale(this.temperature > 50);
        this.dinosaurEntity.setFullyGrown();
        this.dinosaurEntity.getLegacyAttributes().setScaleModifier(1f);

        return this.dinosaurEntity;
    }

    public int getMaxNutrients() {
        return MAX_NUTRIENTS;
    }

    public int getProximates() {
        return this.proximates;
    }

    public int getMinerals() {
        return this.minerals;
    }

    public int getVitamins() {
        return this.vitamins;
    }

    public int getLipids() {
        return this.lipids;
    }



    //temperatureControl
    @Override
    public void setTemperature(int index, int value) {
        if(index == 0)
            this.temperature = value;
    }

    @Override
    public int getTemperature(int index) {
        if(index == 0)
            return this.temperature;
        return -1;
    }

    @Override
    public int getTemperatureCount() {
        return 1;
    }


    //IFluidTank implimentation
    @Override
    public @NotNull FluidStack getFluid() {
        return this.fluid;
    }

    @Override
    public int getFluidAmount() {
        return this.fluid.getAmount();
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
        int capacity = this.getCapacity();

        if (resource.isEmpty() || !isFluidValid(resource))
        {
            return 0;
        }

        //"""simulate""" means that we are just seeing what would happen if we were to fill
        if (action.simulate())
        {
            if (fluid.isEmpty())
            {
                return Math.min(this.getCapacity(), resource.getAmount());
            }
            if (!fluid.isFluidEqual(resource))
            {
                return 0;
            }
            //returns the max amount the resource can increase the fluidstack by
            return Math.min(capacity - fluid.getAmount(), resource.getAmount());
        }
        if (fluid.isEmpty())
        {
            fluid = new FluidStack(resource, Math.min(capacity, resource.getAmount()));
            this.onContentsChanged();
            return fluid.getAmount();
        }
        if (!fluid.isFluidEqual(resource))
        {
            return 0;
        }
        int filled = capacity - fluid.getAmount();

        if (resource.getAmount() < filled)
        {
            fluid.grow(resource.getAmount());
            filled = resource.getAmount();
        }
        else
        {
            fluid.setAmount(capacity);
        }
        if (filled > 0)
            onContentsChanged();
        return filled;
    }

    //dummy for if/when we want to do something when the fluidstack changes
    protected void onContentsChanged(){}

    @NotNull
    @Override
    public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action)
    {
        if (resource.isEmpty() || !resource.isFluidEqual(this.fluid))
        {
            return FluidStack.EMPTY;
        }
        return drain(resource.getAmount(), action);
    }

    @NotNull
    @Override
    public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action)
    {
        int drained = maxDrain;
        if (this.fluid.getAmount() < drained)
        {
            drained = this.fluid.getAmount();
        }
        FluidStack stack = new FluidStack(this.getFluid(), drained);
        if (action.execute() && drained > 0)
        {
            this.fluid.shrink(drained);
            onContentsChanged();
        }
        return stack;
    }

    public static class FoodNutrients {
        public static final Map<Item, FoodNutrients> NUTRIENTS = new HashMap<>();

        private final double proximate;
        private final double minerals;
        private final double vitamins;
        private final double lipids;
        private final Item food;

        public FoodNutrients(Item food, double foodProximates, double foodMinerals, double foodVitamins, double foodLipids) {
            this.food = food;
            this.proximate = foodProximates;
            this.minerals = foodMinerals;
            this.vitamins = foodVitamins;
            this.lipids = foodLipids;
        }

        public static void register() {
            register(Items.APPLE, 0.060, 0.065, 0.100, 0.010);
            register(Items.POTATO, 0.100, 0.200, 0.160, 0.020);
            register(Items.BREAD, 0.300, 0.400, 0.430, 0.180);
            register(Items.CHICKEN, 0.390, 0.350, 0.280, 0.450);
            register(Items.COOKED_CHICKEN, 0.490, 0.425, 0.335, 0.555);
            register(Items.PORKCHOP, 0.460, 0.310, 0.390, 0.380);
            register(Items.COOKED_PORKCHOP, 0.580, 0.390, 0.490, 0.470);
            register(Items.BEEF, 0.460, 0.310, 0.390, 0.380);
            register(Items.COOKED_BEEF, 0.520, 0.330, 0.410, 0.400);
            register(Items.COD, 0.480, 0.430, 0.140, 0.240);
            register(Items.COOKED_COD, 0.500, 0.450, 0.200, 0.280);
            register(Items.SALMON, 0.480, 0.430, 0.140, 0.240);
            register(Items.COOKED_SALMON, 0.500, 0.450, 0.200, 0.280);
            register(Items.TROPICAL_FISH, 0.480, 0.430, 0.140, 0.240);
            register(Items.MILK_BUCKET, 0.180, 0.260, 0.220, 0.600);
            register(Items.EGG, 0.050, 0.030, 0.050, 0.250);
            register(Items.CARROT, 0.070, 0.170, 0.350, 0.010);
            register(Items.SUGAR, 0.200, 0.010, 0.010, 0.010);
            register(Items.MELON, 0.060, 0.060, 0.060, 0.010);
            register(Items.WHEAT, 0.100, 0.220, 0.100, 0.030);
            register(Items.MUTTON, 0.460, 0.310, 0.390, 0.380);
            register(Items.COOKED_MUTTON, 0.580, 0.390, 0.490, 0.470);
            register(ModItems.GOAT_RAW.get(), 0.460, 0.310, 0.390, 0.380);
            register(ModItems.GOAT_COOKED.get(), 0.580, 0.390, 0.490, 0.470);
            ModItems.MEATS.forEach((dino, item) -> register(item.get(), 0.460, 0.310, 0.390, 0.380) );
            ModItems.STEAKS.forEach((dino, item) -> register(item.get(), 0.580, 0.390, 0.490, 0.470) );

        }

        public static void register(Item item, double foodProximates, double foodMinerals, double foodVitamins, double foodLipids) {
            NUTRIENTS.put(item, new FoodNutrients(item, foodProximates, foodMinerals, foodVitamins, foodLipids));
        }

        public Item getFoodItem() {
            return this.food;
        }

        public double getProximate() {
            return this.proximate;
        }

        public double getMinerals() {
            return this.minerals;
        }

        public double getVitamins() {
            return this.vitamins;
        }

        public double getLipids() {
            return this.lipids;
        }

        public static FoodNutrients get(Item item) {
            return NUTRIENTS.get(item);
        }
    }
}
