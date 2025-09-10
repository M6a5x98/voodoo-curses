package m6a5x98.voodoocurses.datagen;

import m6a5x98.voodoocurses.block.ModBlocks;
import m6a5x98.voodoocurses.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModelProvider extends FabricModelProvider {
    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PROFANED_REDSTONE_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PURIFIED_LAPIS_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.COMPACTED_NETHERITE_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.BLESSING_PIN, Models.GENERATED);
        itemModelGenerator.register(ModItems.CURSING_PIN, Models.GENERATED);
        itemModelGenerator.register(ModItems.CLOVER, Models.GENERATED);
        itemModelGenerator.register(ModItems.VOODOO_RITUAL_TEMPLATE, Models.GENERATED);
    }
}
