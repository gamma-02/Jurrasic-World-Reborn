package net.gamma02.jurassicworldreborn.mixin.accessors;

import net.minecraft.core.NonNullList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(NonNullList.class)
public interface NonNullListAccessor {

    @Accessor(value = "list")
    List<?> getList();

}
