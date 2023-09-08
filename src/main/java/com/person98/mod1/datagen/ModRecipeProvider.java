package com.person98.mod1.datagen;

import com.person98.mod1.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CHICKEN_BOOTS, 1)
                .pattern("   ")
                .pattern("R R")
                .pattern("R R")
                .input('R', ModItems.CHICKEN)
                .criterion(hasItem(ModItems.CHICKEN), conditionsFromItem(ModItems.CHICKEN))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.CHICKEN_BOOTS)));
    }
}