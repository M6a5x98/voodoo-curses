package m6a5x98.voodoocurses.datagen;

import m6a5x98.voodoocurses.block.ModBlocks;
import m6a5x98.voodoocurses.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SmithingTransformRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.ItemTags;

import java.util.List;
import java.util.function.Consumer;

public class RecipeProvider extends FabricRecipeProvider {
    public RecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> consumer) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.VOODOO_DOLL)
                .pattern(" F ")
                .pattern("HNH")
                .pattern("WCW")
                .input('F', ItemTags.FLOWERS)
                .input('H', Items.HAY_BLOCK)
                .input('C', ItemTags.COALS)
                .input('W', Items.WHEAT)
                .input('N', Items.NETHERITE_INGOT)
                .criterion(hasItem(Items.NETHERITE_INGOT), conditionsFromItem(Items.NETHERITE_INGOT))
                .criterion(hasItem(Items.HAY_BLOCK), conditionsFromItem(Items.HAY_BLOCK))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CURSING_PIN)
                .pattern("R")
                .pattern("C")
                .pattern("N")
                .input('R', ModBlocks.PROFANED_REDSTONE_BLOCK)
                .input('C', Items.CHAIN)
                .input('N', Items.IRON_NUGGET)
                .criterion(hasItem(ModBlocks.PROFANED_REDSTONE_BLOCK), conditionsFromItem(ModBlocks.PROFANED_REDSTONE_BLOCK))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BLESSING_PIN)
                .pattern("P")
                .pattern("C")
                .pattern("N")
                .input('P', ModBlocks.PURIFIED_LAPIS_BLOCK)
                .input('C', Items.CHAIN)
                .input('N', Items.IRON_NUGGET)
                .criterion(hasItem(ModBlocks.PURIFIED_LAPIS_BLOCK), conditionsFromItem(ModBlocks.PURIFIED_LAPIS_BLOCK))
                .offerTo(consumer);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.VOODOO_RITUAL_TEMPLATE)
                .pattern("DHD")
                .pattern("DHD")
                .pattern("DDD")
                .input('H', Items.HAY_BLOCK)
                .input('D', Items.DIAMOND)
                .criterion(hasItem(Items.HAY_BLOCK), conditionsFromItem(Items.HAY_BLOCK))
                .criterion(hasItem(Items.DIAMOND), conditionsFromItem(Items.DIAMOND))
                .offerTo(consumer);
//        offerSmithingTemplateCopyingRecipe(consumer, ModItems.VOODOO_RITUAL_TEMPLATE, Items.HAY_BLOCK);
        SmithingTransformRecipeJsonBuilder.create(
                Ingredient.ofItems(ModItems.VOODOO_RITUAL_TEMPLATE),
                Ingredient.ofItems(Blocks.REDSTONE_BLOCK),
                Ingredient.fromTag(ItemTags.SOUL_FIRE_BASE_BLOCKS),
                RecipeCategory.MISC,
                ModBlocks.PROFANED_REDSTONE_BLOCK.asItem()
        )
                .criterion(hasItem(ModBlocks.PROFANED_REDSTONE_BLOCK), conditionsFromItem(ModBlocks.PROFANED_REDSTONE_BLOCK))
                .offerTo(consumer, "profaned_redstone_block_from_template");
        offerBlasting(
                consumer,
                List.of(Items.LAPIS_BLOCK),
                RecipeCategory.MISC,
                ModBlocks.PURIFIED_LAPIS_BLOCK,
                30,
                1000,
                "PURIFIED_LAPIS_BLOCK"
        );
        offerCompactingRecipe(
                consumer,
                RecipeCategory.MISC,
                ModBlocks.COMPACTED_NETHERITE_BLOCK,
                Items.NETHERITE_BLOCK
        );
    }
}
