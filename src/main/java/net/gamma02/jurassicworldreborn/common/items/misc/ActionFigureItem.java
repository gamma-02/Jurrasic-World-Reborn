package net.gamma02.jurassicworldreborn.common.items.misc;

import net.minecraft.world.item.Item;
import net.minecraftforge.client.IItemRenderProperties;

import java.util.function.Consumer;

public class ActionFigureItem extends Item {
    public ActionFigureItem(Properties properties) {
        super(properties);
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        super.initializeClient(consumer);


    }
}
