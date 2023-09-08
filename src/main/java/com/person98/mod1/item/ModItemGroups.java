package com.person98.mod1.item;

import com.person98.mod1.Mod1;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup RUBY_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Mod1.MOD_ID, "mod1"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.mod1"))
                    .icon(() -> new ItemStack(ModItems.CHICKEN)).entries((displayContext, entries) -> {
                        entries.add(ModItems.CHICKEN);
                        entries.add(ModItems.CHICKEN_BOOTS);

                    }).build());


    public static void registerItemGroups() {
        Mod1.LOGGER.info("Registering Item Groups for " + Mod1.MOD_ID);
    }
}