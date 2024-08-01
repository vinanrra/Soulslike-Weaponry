package net.soulsweaponry.datagen.recipe;

import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.predicate.item.ItemPredicate;
import net.soulsweaponry.registry.BlockRegistry;
import net.soulsweaponry.registry.ItemRegistry;

import java.util.function.Consumer;

public class BlockRecipes {

    public static void generateRecipes(Consumer<RecipeJsonProvider> consumer) {
        ShapedRecipeJsonBuilder.create(BlockRegistry.ALTAR_BLOCK)
                .input('#', ItemRegistry.IRON_SKULL)
                .input('D', ItemRegistry.MOONSTONE)
                .input('O', Items.OBSIDIAN)
                .pattern(" # ")
                .pattern("DOD")
                .pattern("OOO")
                .criterion("has_item", RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.MOONSTONE).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(BlockRegistry.BLACKSTONE_PEDESTAL)
                .input('X', Items.ENDER_PEARL)
                .input('O', Items.POLISHED_BLACKSTONE_BRICKS)
                .input('I', ItemRegistry.MOONSTONE)
                .input('Y', Items.OBSIDIAN)
                .pattern("I I")
                .pattern("XYX")
                .pattern("OOO")
                .criterion("has_item", RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.MOONSTONE).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(BlockRegistry.CHUNGUS_MONOLITH)
                .input('O', Items.GOLDEN_CARROT)
                .input('D', Items.DEEPSLATE)
                .input('S', Items.STONE)
                .pattern(" S ")
                .pattern("SOS")
                .pattern("SDS")
                .criterion("has_item", RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(Items.GOLDEN_CARROT).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(BlockRegistry.CRACKED_INFUSED_BLACKSTONE)
                .input('#', ItemRegistry.LOST_SOUL)
                .input('C', Items.CRACKED_POLISHED_BLACKSTONE_BRICKS)
                .pattern(" # ")
                .pattern("#C#")
                .pattern(" # ")
                .criterion("has_item", RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.LOST_SOUL).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(BlockRegistry.INFUSED_BLACKSTONE)
                .input('#', ItemRegistry.LOST_SOUL)
                .input('C', Items.POLISHED_BLACKSTONE_BRICKS)
                .pattern(" # ")
                .pattern("#C#")
                .pattern(" # ")
                .criterion("has_item", RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.LOST_SOUL).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(BlockRegistry.MOONSTONE_BLOCK)
                .input('#', ItemRegistry.MOONSTONE)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .criterion("has_item", RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.MOONSTONE).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(BlockRegistry.VERGLAS_BLOCK)
                .input('#', ItemRegistry.VERGLAS)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .criterion("has_item", RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.VERGLAS).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(BlockRegistry.SOULFIRE_STAIN)
                .input('#', ItemRegistry.LOST_SOUL)
                .pattern("##")
                .pattern("##")
                .criterion("has_item", RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.LOST_SOUL).build()))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(BlockRegistry.SOUL_LAMP)
                .input('#', ItemRegistry.LOST_SOUL)
                .input('C', Items.REDSTONE_LAMP)
                .pattern(" # ")
                .pattern("#C#")
                .pattern(" # ")
                .criterion("has_item", RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.LOST_SOUL).build()))
                .offerTo(consumer);

        ShapelessRecipeJsonBuilder.create(BlockRegistry.CRIMSON_OBSIDIAN)
                .input(ItemRegistry.CRIMSON_INGOT)
                .input(Items.OBSIDIAN)
                .criterion("has_item", RecipeProvider.conditionsFromItemPredicates(ItemPredicate.Builder.create()
                        .items(ItemRegistry.DEMON_HEART).build()))
                .offerTo(consumer);
    }
}