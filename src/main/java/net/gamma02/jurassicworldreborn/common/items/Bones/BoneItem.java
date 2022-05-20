package net.gamma02.jurassicworldreborn.common.items.Bones;

import net.gamma02.jurassicworldreborn.common.entities.Dinosaurs.Bone;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BoneItem extends Item {

    private final Bone.BoneGroup groupOwner;
    private final Bone shape;

    public BoneItem(Properties pProperties, Bone member, Bone.BoneGroup groupOwner) {
        super(pProperties);
        this.groupOwner = groupOwner;
        this.shape = member;
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack pStack) {
        String translatedDinoless = new TranslatableComponent("item." + this.shape.toString() + "name").getString();

        String translated = translatedDinoless.replaceAll("\\{dino}", this.groupOwner.getOwner().getName());

        return new TextComponent(translated);
    }

    public Bone getShape(){
        return shape;
    }

    public Bone.BoneGroup getOwner(){
        return this.groupOwner;
    }


}
