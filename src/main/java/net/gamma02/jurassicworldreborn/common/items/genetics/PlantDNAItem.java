package net.gamma02.jurassicworldreborn.common.items.genetics;

import net.gamma02.jurassicworldreborn.common.items.ModItems;
import net.gamma02.jurassicworldreborn.common.items.TabHandler;
import net.gamma02.jurassicworldreborn.common.plants.Plant;
import net.gamma02.jurassicworldreborn.common.plants.PlantHandler;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;

public class PlantDNAItem extends Item {

    public final Plant plant;

    public PlantDNAItem(Plant plant, Properties pProperties) {
        super(pProperties);
        this.plant = plant;
    }

    @Override
    public Component getName(ItemStack pStack) {
        return Component.literal(Component.translatable(ForgeRegistries.ITEMS.getKey(this).getPath() + ".name").getString().replace("{plant}", Component.translatable("plants." + this.getPlant(pStack).getName().toLowerCase(Locale.ROOT).replaceAll(" ", "_")).getString()));
    }

    public Plant getPlant(ItemStack stack) {
        if(stack.getItem() == this)
            return this.plant;
        if(stack.getItem() instanceof PlantDNAItem i){
            return i.getPlant(stack);
        }

        return PlantHandler.EMPTY;
    }

    @Override
    public void fillItemCategory(CreativeModeTab pCategory, NonNullList<ItemStack> pItems) {
        if((pCategory == TabHandler.DNA || pCategory == CreativeModeTab.TAB_SEARCH)) {
            if(pItems.stream().anyMatch((stack) -> stack.is(this)))
                return;


            var plantDNA = ModItems.PLANT_DNAS.get(plant);
            if (plantDNA != null) {
                ItemStack defaultDNAItem = plantDNA.get().getDefaultInstance();

                defaultDNAItem.getOrCreateTag().putBoolean("isCreative", true);


                pItems.add(defaultDNAItem);
            }

        }else {

            super.fillItemCategory(pCategory, pItems);
        }
    }



    //INFO: use DNAContainerItem.getDNAQuality()
//    public int getDNAQuality(EntityPlayer player, ItemStack stack) {
//        int quality = player.capabilities.isCreativeMode ? 100 : 0;
//
//        NBTTagCompound nbt = stack.getTagCompound();
//
//        if (nbt == null) {
//            nbt = new NBTTagCompound();
//        }
//
//        if (nbt.hasKey("DNAQuality")) {
//            quality = nbt.getInteger("DNAQuality");
//        } else {
//            nbt.setInteger("DNAQuality", quality);
//        }
//
//        stack.setTagCompound(nbt);
//
//        return quality;
//    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
