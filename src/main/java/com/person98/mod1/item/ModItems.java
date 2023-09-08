package com.person98.mod1.item;

import com.person98.mod1.Mod1;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item CHICKEN = registerItem("chicken", new Item(new FabricItemSettings().maxCount(1)));

    public static final Item CHICKEN_BOOTS = registerItem("chicken_boots",
            new ArmorItem(ModArmorMaterials.RUBY, ArmorItem.Type.BOOTS, new FabricItemSettings()));
    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        entries.add(CHICKEN);
//        entries.add(CHICKEN_BOOTS);
//        entries.add(CHICKEN_CHESTPLATE);
//        entries.add(CHICKEN_HELMET);
//        entries.add(CHICKEN_LEGGINGS);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Mod1.MOD_ID, name), item);
    }
    public static void registerModItems() {
        Mod1.LOGGER.info("Registering Mod Items for " + Mod1.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }
}