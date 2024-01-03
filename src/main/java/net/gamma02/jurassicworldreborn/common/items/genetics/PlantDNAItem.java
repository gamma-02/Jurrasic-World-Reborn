package net.gamma02.jurassicworldreborn.common.items.genetics;

import net.gamma02.jurassicworldreborn.common.plants.Plant;
import net.gamma02.jurassicworldreborn.common.plants.PlantHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Locale;

public class PlantDNAItem extends Item {

    public PlantDNAItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Component getName(ItemStack pStack) {
        return Component.literal(Component.translatable(ForgeRegistries.ITEMS.getKey(this).getPath() + ".name").getString().replace("{plant}", Component.translatable("plants." + this.getPlant(pStack).getName().toLowerCase(Locale.ROOT).replaceAll(" ", "_")).getString()));
    }

    public Plant getPlant(ItemStack stack) {
        Plant plant = PlantHandler.getPlantById(ResourceLocation.of(stack.getTag() != null ? stack.getTag().getString("Plant") : PlantHandler.SMALL_ROYAL_FERN.getId().toString(), ':'));

        if (plant == null) {
            plant = PlantHandler.SMALL_ROYAL_FERN;
        }

        return plant;
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

//    @Override
//    public void addInformation(ItemStack stack, World worldIn, List<String> lore, ITooltipFlag flagIn) {
//        int quality = this.getDNAQuality(player, stack);
//
//        TextFormatting formatting;
//
//        if (quality > 75) {
//            formatting = TextFormatting.GREEN;
//        } else if (quality > 50) {
//            formatting = TextFormatting.YELLOW;
//        } else if (quality > 25) {
//            formatting = TextFormatting.GOLD;
//        } else {
//            formatting = TextFormatting.RED;
//        }
//
//        lore.add(formatting + new LangHelper("lore.dna_quality.name").withProperty("quality", quality + "").build());
//    }
}
