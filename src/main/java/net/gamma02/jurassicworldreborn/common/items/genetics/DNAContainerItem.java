package net.gamma02.jurassicworldreborn.common.items.genetics;

import net.gamma02.jurassicworldreborn.common.genetics.GeneticsHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class DNAContainerItem extends Item {
    public DNAContainerItem(Properties pProperties) {
        super(pProperties);
    }

    public int getContainerId(ItemStack stack) {//todo:what
        return 0;
    }

    public static int getDNAQuality(Player player, ItemStack stack) {
        int quality = player.isCreative() ? 100 : 0;

        CompoundTag nbt = stack.getTag();

        if (nbt == null) {
            nbt = new CompoundTag();
        }

        if (nbt.contains("DNAQuality")) {
            quality = nbt.getInt("DNAQuality");
        } else {
            nbt.putInt("DNAQuality", quality);
        }

        stack.setTag(nbt);

        return quality;
    }

    public static String getGeneticCode(Player player, ItemStack stack) {
        CompoundTag nbt = stack.getTag();

        String genetics = GeneticsHelper.randomGenetics(player.level.random);

        if (nbt == null) {
            nbt = new CompoundTag();
        }

        if (nbt.contains("Genetics")) {
            genetics = nbt.getString("Genetics");
        } else {
            nbt.putString("Genetics", genetics);
        }

        stack.setTag(nbt);

        return genetics;
    }

//    @Override
//    public void addInformation(ItemStack stack, World worldIn, List<String> lore, ITooltipFlag flagIn) {
//      /* TODO: this.getDNAQuality(stack., stack); */  int quality = 100;
//        TextFormatting colour;
//
//        if (quality > 75) {
//            colour = TextFormatting.GREEN;
//        } else if (quality > 50) {
//            colour = TextFormatting.YELLOW;
//        } else if (quality > 25) {
//            colour = TextFormatting.GOLD;
//        } else {
//            colour = TextFormatting.RED;
//        }
//        lore.add(colour + new LangHelper("lore.dna_quality.name").withProperty("quality", quality + "").build());
//        lore.add(TextFormatting.BLUE + new LangHelper("lore.genetic_code.name").withProperty("code", this.getGeneticCode(player, stack)).build());
//    }
}
