package krelox.morebows.data;

import krelox.morebows.MoreBows;
import krelox.morebows.item.CustomBowItem;
import krelox.morebows.item.MoreBowsItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

public class MoreBowsRecipeProvider extends RecipeProvider {

    public MoreBowsRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        bowItem(consumer, MoreBowsItems.STONE_BOW, Tags.Items.RODS_WOODEN, Tags.Items.STONE);
        bowItem(consumer, MoreBowsItems.IRON_BOW, Tags.Items.INGOTS_IRON, Tags.Items.INGOTS_IRON);
        bowItem(consumer, MoreBowsItems.GOLD_BOW, Tags.Items.INGOTS_GOLD, Tags.Items.INGOTS_GOLD);
        bowItem(consumer, MoreBowsItems.DIAMOND_BOW, Tags.Items.INGOTS_IRON, Tags.Items.GEMS_DIAMOND);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MoreBowsItems.FROST_BOW.get())
                .define('|', Items.SNOWBALL).define('#', Ingredient.of(Items.ICE, Items.PACKED_ICE, Items.BLUE_ICE))
                .define('B', MoreBowsItems.IRON_BOW.get()).define('S', Tags.Items.STRING)
                .pattern(" #S")
                .pattern("|BS")
                .pattern(" #S")
                .unlockedBy("has_item", has(MoreBowsItems.IRON_BOW.get()))
                .save(consumer, MoreBowsItems.FROST_BOW.getId());

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, MoreBowsItems.MULTI_BOW.get())
                .define('|', Tags.Items.INGOTS_IRON).define('#', MoreBowsItems.IRON_BOW.get()).define('S', Tags.Items.STRING)
                .pattern(" #S")
                .pattern("| S")
                .pattern(" #S")
                .unlockedBy("has_item", has(MoreBowsItems.IRON_BOW.get()))
                .save(consumer, MoreBowsItems.MULTI_BOW.getId());

        bowUpgradeItem(consumer, MoreBowsItems.ENDER_BOW, Ingredient.of(Items.ENDER_EYE), Tags.Items.INGOTS_GOLD, Tags.Items.ENDER_PEARLS);
        bowUpgradeItem(consumer, MoreBowsItems.FLAME_BOW, Ingredient.of(Tags.Items.INGOTS_GOLD), Tags.Items.NETHERRACK, Tags.Items.RODS_BLAZE);
    }

    protected static void bowItem(Consumer<FinishedRecipe> consumer, RegistryObject<CustomBowItem> result,
                                  TagKey<Item> stick, TagKey<Item> material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get())
                .define('|', stick).define('#', material)
                .define('B', Tags.Items.TOOLS_BOWS).define('S', Tags.Items.STRING)
                .pattern(" #S")
                .pattern("|BS")
                .pattern(" #S")
                .unlockedBy("has_item", has(Items.BOW))
                .save(consumer, result.getId());
    }

    protected static void bowUpgradeItem(Consumer<FinishedRecipe> consumer, RegistryObject<CustomBowItem> result, Ingredient mat1, TagKey<Item> mat2, TagKey<Item> mat3) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, result.get())
                .define('|', mat1).define('#', mat2)
                .define('B', MoreBowsItems.IRON_BOW.get()).define('S', mat3)
                .pattern("#S")
                .pattern("|B")
                .pattern("#S")
                .unlockedBy("has_item", has(MoreBowsItems.IRON_BOW.get()))
                .save(consumer, result.getId());
    }
}
