package m6a5x98.voodoocurses.block;

import m6a5x98.voodoocurses.VoodooCurses;
import m6a5x98.voodoocurses.item.items.BlessingPinItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {
    public static Block PROFANED_REDSTONE_BLOCK = registerBlock(
            "profaned_redstone_block",
            new RedstoneBlock(AbstractBlock.Settings.copy(Blocks.REDSTONE_BLOCK))
    );
    public static Block PURIFIED_LAPIS_BLOCK = registerBlock(
            "purified_lapis_block",
            new Block(AbstractBlock.Settings.copy(Blocks.LAPIS_BLOCK))
    );
    public static Block COMPACTED_NETHERITE_BLOCK = registerBlock(
            "compacted_netherite_block",
            new Block(AbstractBlock.Settings.copy(Blocks.NETHERITE_BLOCK))
    );

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(VoodooCurses.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(VoodooCurses.MOD_ID, name), new BlockItem(block, new Item.Settings()));
    }

    public static void registerBlocks() {}
}
