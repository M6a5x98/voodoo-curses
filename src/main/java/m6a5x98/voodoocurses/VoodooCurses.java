package m6a5x98.voodoocurses;

import m6a5x98.voodoocurses.block.ModBlocks;
import m6a5x98.voodoocurses.item.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VoodooCurses implements ModInitializer {
	public static final String MOD_ID = "voodoo-curses";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	@Override
	public void onInitialize() {
		ModItems.registerItems();
		ModBlocks.registerBlocks();

		LootTableEvents.MODIFY.register((resourceManager, lootManager, identifier, fabricLootSupplierBuilder, lootTableSetter) -> {
			if (new Identifier("blocks/grass").equals(identifier)) {
				LootPool.Builder cloverPool = LootPool.builder()
						.rolls(ConstantLootNumberProvider.create(1))
						.conditionally(RandomChanceLootCondition.builder(0.1f))
						.with(ItemEntry.builder(ModItems.CLOVER))
						.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
				fabricLootSupplierBuilder.pool(cloverPool);
			}
		});
	}
}