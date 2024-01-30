package com.refinedmods.refinedstorage.datageneration;

import com.refinedmods.refinedstorage.RS;
import com.refinedmods.refinedstorage.RSItems;
import com.refinedmods.refinedstorage.item.ProcessorItem;
import com.refinedmods.refinedstorage.util.ColorMap;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

public class RecipeGenerator extends RecipeProvider {
    private static final String GRID_ID = RS.ID + ":grid";

    public RecipeGenerator(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        // Tag + Color -> Colored Block
        RSItems.COLORED_ITEM_TAGS.forEach((tag, map) -> map.forEach((color, item) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, item.get())
            .requires(tag)
            .requires(color.getTag())
            .group(RS.ID)
            .unlockedBy("refinedstorage:controller", InventoryChangeTrigger.TriggerInstance.hasItems(RSItems.CONTROLLER.get(ColorMap.DEFAULT_COLOR).get()))
            .save(output, new ResourceLocation(RS.ID, "coloring_recipes/" + item.getId().getPath()))
        ));

        // Crafting Grid
        RSItems.CRAFTING_GRID.forEach((color, item) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, item.get())
            .requires(RSItems.GRID.get(color).get())
            .requires(RSItems.PROCESSORS.get(ProcessorItem.Type.ADVANCED).get())
            .requires(ItemTags.create(new ResourceLocation(RS.ID, "crafting_tables")))
            .unlockedBy(GRID_ID, InventoryChangeTrigger.TriggerInstance.hasItems(RSItems.GRID.get(ColorMap.DEFAULT_COLOR).get()))
            .save(output, new ResourceLocation(RS.ID, "crafting_grid/" + item.getId().getPath()))
        );

        // Fluid Grid
        RSItems.FLUID_GRID.forEach((color, item) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, item.get())
            .requires(RSItems.GRID.get(color).get())
            .requires(RSItems.PROCESSORS.get(ProcessorItem.Type.ADVANCED).get())
            .requires(Items.BUCKET)
            .unlockedBy(GRID_ID, InventoryChangeTrigger.TriggerInstance.hasItems(RSItems.GRID.get(ColorMap.DEFAULT_COLOR).get()))
            .save(output, new ResourceLocation(RS.ID, "fluid_grid/" + item.getId().getPath()))
        );

        // Pattern Grid
        RSItems.PATTERN_GRID.forEach((color, item) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, item.get())
            .requires(RSItems.GRID.get(color).get())
            .requires(RSItems.PROCESSORS.get(ProcessorItem.Type.ADVANCED).get())
            .requires(RSItems.PATTERN.get())
            .unlockedBy(GRID_ID, InventoryChangeTrigger.TriggerInstance.hasItems(RSItems.GRID.get(ColorMap.DEFAULT_COLOR).get()))
            .save(output, new ResourceLocation(RS.ID, "pattern_grid/" + item.getId().getPath()))
        );
    }
}
