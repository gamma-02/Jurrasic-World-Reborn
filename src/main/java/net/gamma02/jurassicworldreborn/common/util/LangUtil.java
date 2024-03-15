package net.gamma02.jurassicworldreborn.common.util;

import net.gamma02.jurassicworldreborn.Jurassicworldreborn;
import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Dinosaur;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.Locale;
import java.util.function.Supplier;

/**
 * This is different than LangUtils!!!!!!! that is OLD CODE, built to work with raw string translation!<br>
 * <b>THIS</b> is built to work with the updated TranslatableContents/MutableComponent system! <br>
 * <b><i>USE THIS INSTEAD!!!!!!!!!</i></b>
 */
public class LangUtil {

    public static final String LORE = "lore.%s.name";


    public static Component getDinoName(Dinosaur dino){
        return Component.translatable(getEntityKey(dino.getName().replace(" ", "_").toLowerCase(Locale.ENGLISH)));
    }

    public static MutableComponent replaceInKey(Supplier<String> replacement, String replaceTarget, String key){
        return Component.literal(Component.translatable(key).getString().replace(replaceTarget, replacement.get()) );
    }

    public static Component replaceWithDinosaurName(Dinosaur name, String key){
        return replaceInKey(name::getName, "{dinosaur}", key);
    }
    public static MutableComponent replaceWithDinoName(Dinosaur name, String key){
        return replaceInKey(name::getName, "{dino}", key);
    }


    public static String getEntityKey(String key){
        return "entity." + Jurassicworldreborn.modid + "." + key;
    }

    public static Component getFormattedQuality(int quality){
        return quality == -1 ? Component.literal("??").withStyle(ChatFormatting.OBFUSCATED) : Component.literal(Integer.toString(quality));
    }

    public static Component getFormattedGenetics(String genetics) {
        return genetics.isEmpty() ? Component.literal("???").withStyle(ChatFormatting.OBFUSCATED) : Component.literal(genetics);
    }

}
