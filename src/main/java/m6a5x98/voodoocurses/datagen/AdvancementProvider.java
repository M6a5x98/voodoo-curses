package m6a5x98.voodoocurses.datagen;

import m6a5x98.voodoocurses.VoodooCurses;
import m6a5x98.voodoocurses.block.ModBlocks;
import m6a5x98.voodoocurses.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.ImpossibleCriterion;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class AdvancementProvider extends FabricAdvancementProvider {
    public AdvancementProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {
        Advancement root = Advancement.Builder.create().display(
                ModItems.VOODOO_DOLL,
                Text.translatable("advancements.voodoo-curses.voodoo-doll.name"),
                Text.translatable("advancements.voodoo-curses.voodoo-doll.description"),
                new Identifier(VoodooCurses.MOD_ID, "textures/block/profaned_redstone_block.png"),
                AdvancementFrame.GOAL,
                true,
                true,
                false
        )
                .criterion("got_doll", InventoryChangedCriterion.Conditions.items(ModItems.VOODOO_DOLL))
                .build(consumer, VoodooCurses.MOD_ID + "/root");

        Advancement pathOfGood = Advancement.Builder.create()
                .parent(root)
                .display(
                ModItems.BLESSING_PIN,
                Text.translatable("advancements.voodoo-curses.path-of-good.name"),
                Text.translatable("advancements.voodoo-curses.path-of-good.description"),
                null,
                AdvancementFrame.TASK,
                true,
                true,
                false
        )
                .criterion("got_pin", InventoryChangedCriterion.Conditions.items(ModItems.BLESSING_PIN))
                .build(consumer, VoodooCurses.MOD_ID + "/path_of_good");

        Advancement pathOfBad = Advancement.Builder.create()
                .parent(root)
                .display(
                        ModItems.CURSING_PIN,
                        Text.translatable("advancements.voodoo-curses.path-of-bad.name"),
                        Text.translatable("advancements.voodoo-curses.path-of-bad.description"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("got_pin", InventoryChangedCriterion.Conditions.items(ModItems.CURSING_PIN))
                .build(consumer, VoodooCurses.MOD_ID + "/path_of_bad");

        Advancement firstBlessing = Advancement.Builder.create()
                .parent(pathOfGood)
                .display(
                        ModBlocks.PURIFIED_LAPIS_BLOCK,
                        Text.translatable("advancements.voodoo-curses.first-blessing.name"),
                        Text.translatable("advancements.voodoo-curses.first-blessing.description"),
                        null,
                        AdvancementFrame.GOAL,
                        true,
                        true,
                        false
                )
                .criterion("impossible", new ImpossibleCriterion.Conditions())
                .build(consumer, VoodooCurses.MOD_ID + "/first_blessing");

        Advancement firstCurse = Advancement.Builder.create()
                .parent(pathOfBad)
                .display(
                        ModBlocks.PROFANED_REDSTONE_BLOCK,
                        Text.translatable("advancements.voodoo-curses.first-curse.name"),
                        Text.translatable("advancements.voodoo-curses.first-curse.description"),
                        null,
                        AdvancementFrame.GOAL,
                        true,
                        true,
                        false
                )
                .criterion("impossible", new ImpossibleCriterion.Conditions())
                .build(consumer, VoodooCurses.MOD_ID + "/first_curse");

        Advancement.Builder.create()
                .parent(pathOfGood)
                .display(
                        ModBlocks.COMPACTED_NETHERITE_BLOCK,
                        Text.translatable("advancements.voodoo-curses.ultimate-blessing.name"),
                        Text.translatable("advancements.voodoo-curses.ultimate-blessing.description", Text.translatable("curse.voodoo-curses.4")),
                        null,
                        AdvancementFrame.GOAL,
                        true,
                        true,
                        true
                )
                .criterion("impossible", new ImpossibleCriterion.Conditions())
                .build(consumer, VoodooCurses.MOD_ID + "/ultimate_blessing");

        Advancement.Builder.create()
                .parent(pathOfBad)
                .display(
                        ModBlocks.COMPACTED_NETHERITE_BLOCK,
                        Text.translatable("advancements.voodoo-curses.ultimate-curse.name"),
                        Text.translatable("advancements.voodoo-curses.ultimate-curse.description", Text.translatable("curse.voodoo-curses.8")),
                        null,
                        AdvancementFrame.GOAL,
                        true,
                        true,
                        true
                )
                .criterion("impossible", new ImpossibleCriterion.Conditions())
                .build(consumer, VoodooCurses.MOD_ID + "/ultimate_curse");

        Advancement.Builder.create()
                .parent(root)
                .display(
                        ModItems.CLOVER,
                        Text.translatable("advancements.voodoo-curses.lucky-flower.name"),
                        Text.translatable("advancements.voodoo-curses.lucky-flower.description"),
                        null,
                        AdvancementFrame.GOAL,
                        true,
                        true,
                        false
                )
                .criterion("got_clover", InventoryChangedCriterion.Conditions.items(ModItems.CLOVER))
                .build(consumer, VoodooCurses.MOD_ID + "/lucky_flower");
    }
}
