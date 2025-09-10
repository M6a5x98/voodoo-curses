package m6a5x98.voodoocurses.item.items;

import m6a5x98.voodoocurses.VoodooCursesUtils;
import m6a5x98.voodoocurses.block.ModBlocks;
import m6a5x98.voodoocurses.item.ModItems;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlessingPinItem extends Item {
    private final Item HasteUpgrade = Items.NETHERITE_PICKAXE;
    private final Item SpeedUpgrade = Items.CAKE;

    public BlessingPinItem(Settings settings) {
        super(settings);
    }

    public ItemStack setBlessing(ItemStack stack, int blessing) {
        ItemStack result = stack.copy();
        result.getOrCreateNbt().putInt("Blessing", blessing);
        return result;
    }

    public int getBlessing(ItemStack stack) {return stack.copy().getOrCreateNbt().getInt("Blessing");}

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.getOrCreateNbt().contains("Blessing", NbtElement.INT_TYPE);
    }

    private boolean canUpgradeWith(ItemStack stack) {
        return stack.isOf(this.HasteUpgrade) || stack.isOf(this.SpeedUpgrade) || stack.isOf(ModItems.CLOVER) || stack.isOf(ModBlocks.COMPACTED_NETHERITE_BLOCK.asItem());
    }

    private ItemStack upgradeWith(ItemStack stack) {
        if (canUpgradeWith(stack)) {
            ItemStack result = new ItemStack(ModItems.BLESSING_PIN);
            result = setBlessing(result,
                    stack.isOf(this.SpeedUpgrade)
                    ? VoodooCursesUtils.SPEED
                    : stack.isOf(this.HasteUpgrade)
                    ? VoodooCursesUtils.HASTE
                    : stack.isOf(ModItems.CLOVER)
                    ? VoodooCursesUtils.LUCK
                    : stack.isOf(ModBlocks.COMPACTED_NETHERITE_BLOCK.asItem())
                    ? VoodooCursesUtils.FULL_BEACON
                    : VoodooCursesUtils.NONE);
            return result;
        } else {
            return stack;
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.getStackInHand(hand).getOrCreateNbt().contains("Blessing", NbtElement.INT_TYPE)) return TypedActionResult.fail(user.getStackInHand(hand));
        if (!world.isClient && canUpgradeWith(user.getOffHandStack())) {
            ItemStack result = upgradeWith(user.getOffHandStack());
            user.setStackInHand(hand, result);
            user.setStackInHand(Hand.OFF_HAND, user.getAbilities().creativeMode ? user.getOffHandStack() :ItemStack.EMPTY);
            return TypedActionResult.success(result, true);
        } else if (world.isClient && canUpgradeWith(user.getOffHandStack())) {
            Random random = Random.create();
            for (int i = 0; i < 30; i++) {
                user.getWorld().addImportantParticle(
                        random.nextBoolean() ? ParticleTypes.SOUL : ParticleTypes.SCULK_SOUL,
                        true,
                        user.getX() + (random.nextBetween(-500, 500) / 1000d),
                        user.getY() + (random.nextBetween(-500, 500) / 1000d) + 1d,
                        user.getZ() + (random.nextBetween(-500, 500) / 1000d),
                        (random.nextBetween(-500, 500) / 1000d),
                        (random.nextBetween(-500, 500) / 1000d),
                        (random.nextBetween(-500, 500) / 1000d)
                );
            }
            return TypedActionResult.pass(user.getStackInHand(hand));
        } else {
            return TypedActionResult.fail(user.getStackInHand(hand));
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(
                Text.translatable(
                        "curse.voodoo-curses." + stack.getOrCreateNbt().getInt("Blessing"),
                                Text.translatable("tooltip.voodoo-curses.blessing")
                        )
                        .setStyle(Style.EMPTY.withColor(53760))
        );
    }
}
