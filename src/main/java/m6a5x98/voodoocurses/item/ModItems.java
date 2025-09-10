package m6a5x98.voodoocurses.item;

import m6a5x98.voodoocurses.VoodooCurses;
import m6a5x98.voodoocurses.block.ModBlocks;
import m6a5x98.voodoocurses.item.items.BlessingPinItem;
import m6a5x98.voodoocurses.item.items.CursingPinItem;
import m6a5x98.voodoocurses.item.items.VoodooDollItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {
    public static Item VOODOO_DOLL = register(
            new Identifier(VoodooCurses.MOD_ID, "voodoo_doll"),
                new VoodooDollItem(new Item.Settings().maxCount(1))
    );
    public static Item BLESSING_PIN = register(
            new Identifier(VoodooCurses.MOD_ID, "blessing_pin"),
                new BlessingPinItem(new Item.Settings().maxCount(1))
    );
    public static Item CURSING_PIN = register(
            new Identifier(VoodooCurses.MOD_ID, "cursing_pin"),
                new CursingPinItem(new Item.Settings().maxCount(1))
    );
    public static Item CLOVER = register(
            new Identifier(VoodooCurses.MOD_ID, "clover"),
                new Item(new Item.Settings()
                        .maxCount(64)
                        .rarity(Rarity.UNCOMMON)
                )
    );
    public static Item VOODOO_RITUAL_TEMPLATE = register(
            new Identifier(VoodooCurses.MOD_ID, "voodoo_ritual_template"),
            new Item(new Item.Settings())
    );

    public static void registerItems() {
        final RegistryKey<ItemGroup> ITEM_GROUP_KEY = RegistryKey.of(
                Registries.ITEM_GROUP.getKey(),
                new Identifier(VoodooCurses.MOD_ID, "main_item_group")
        );
        Registry.register(
                Registries.ITEM_GROUP,
                ITEM_GROUP_KEY,
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(VOODOO_DOLL))
                    .displayName(Text.translatable("itemGroup.voodoo-curses.main"))
                    .entries(((displayContext, entries) -> {
                        entries.add(VOODOO_DOLL);
                        entries.add(CURSING_PIN);
                        entries.add(BLESSING_PIN);
                        entries.add(CLOVER);
                        entries.add(VOODOO_RITUAL_TEMPLATE);
                        entries.add(ModBlocks.PROFANED_REDSTONE_BLOCK);
                        entries.add(ModBlocks.PURIFIED_LAPIS_BLOCK);
                        entries.add(ModBlocks.COMPACTED_NETHERITE_BLOCK);
                    }))
                    .build()
        );
        ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP_KEY);
    }

    public static Item register(Identifier id, Item item) {
        return Registry.register(Registries.ITEM, id, item);
    }
}
