package m6a5x98.voodoocurses.item.items;

import m6a5x98.voodoocurses.VoodooCursesUtils;
import m6a5x98.voodoocurses.item.ModItems;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static m6a5x98.voodoocurses.VoodooCursesUtils.FIRST_CURSE_AT;

public class VoodooDollItem extends Item {
    public VoodooDollItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (stack.getOrCreateNbt().containsUuid("LinkedTo")) return ActionResult.FAIL;
        if (!entity.isPlayer()) return ActionResult.FAIL;
        if (!user.getWorld().isClient) {
            ItemStack result = stack.copy();
            NbtCompound nbt = result.getOrCreateNbt();
            nbt.putUuid("LinkedTo", entity.getUuid());
            result.setNbt(nbt);
            user.setStackInHand(hand, result);
            return ActionResult.SUCCESS;
        } else {
            user.getWorld().playSound(user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT, SoundCategory.PLAYERS, 3.0f, 0.5f, false);
            Random random = Random.create();
            for (int i = 0; i < 30; i++) {
                user.getWorld().addImportantParticle(
                        ParticleTypes.TOTEM_OF_UNDYING,
                        true,
                        user.getX() + (random.nextBetween(-500, 500) / 1000d),
                        user.getY() + (random.nextBetween(-500, 500) / 1000d) + 1d,
                        user.getZ() + (random.nextBetween(-500, 500) / 1000d),
                        (random.nextBetween(-500, 500) / 1000d),
                        (random.nextBetween(-500, 500) / 1000d),
                        (random.nextBetween(-500, 500) / 1000d)
                );
            }
            return ActionResult.PASS;
        }
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.getNbt() != null && stack.getNbt().containsUuid("LinkedTo");
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getWorld().isClient) {
            if (
                    (user.getOffHandStack().isOf(ModItems.CURSING_PIN) || user.getOffHandStack().isOf(ModItems.BLESSING_PIN))
                    && (user.getOffHandStack().getOrCreateNbt().contains("Curse") || user.getOffHandStack().getOrCreateNbt().contains("Blessing"))
                    && !user.getStackInHand(hand).getOrCreateNbt().contains("CurseOrBlessing", NbtElement.INT_TYPE)
            ) {
                ItemStack result = user.getStackInHand(hand).copy();
                result.getOrCreateNbt().putInt("CurseOrBlessing", user.getOffHandStack().getOrCreateNbt().getInt(
                        user.getOffHandStack().isOf(ModItems.CURSING_PIN)
                                ? "Curse"
                                :"Blessing"
                    )
                );
                user.setStackInHand(hand, result);
                user.setStackInHand(Hand.OFF_HAND, user.getAbilities().creativeMode ? user.getOffHandStack() :ItemStack.EMPTY);
                return TypedActionResult.success(result, true);
            }
        } else if (
                user.getWorld().isClient &&
                (user.getOffHandStack().isOf(ModItems.CURSING_PIN) || user.getOffHandStack().isOf(ModItems.BLESSING_PIN))
                        && (user.getOffHandStack().getOrCreateNbt().contains("Curse") || user.getOffHandStack().getOrCreateNbt().contains("Blessing"))
                        && !user.getStackInHand(hand).getOrCreateNbt().contains("CurseOrBlessing", NbtElement.INT_TYPE)
        ) {
            user.getWorld().playSound(
                    user.getX(),
                    user.getY(),
                    user.getZ(),
                    SoundEvents.BLOCK_CONDUIT_ACTIVATE,
                    SoundCategory.PLAYERS,
                    3.0f,
                    0.7f,
                    false
            );
            return TypedActionResult.pass(user.getStackInHand(hand));
        } else {
            return TypedActionResult.fail(user.getStackInHand(hand));
        }
            return TypedActionResult.pass(user.getStackInHand(hand));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (
                !entity.isPlayer()
                || world.isClient
                || !stack.getOrCreateNbt().containsUuid("LinkedTo")
                || !stack.getOrCreateNbt().contains("CurseOrBlessing", NbtElement.INT_TYPE)
        ) {
        } else {
            VoodooCursesUtils.applyCurseOrBlessing(
                    world.getPlayerByUuid(stack.getOrCreateNbt().getUuid("LinkedTo")),
                    stack.getOrCreateNbt().getInt("CurseOrBlessing"),
                    (PlayerEntity) entity
            );
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.getOrCreateNbt().getInt("CurseOrBlessing") == 0) return;
        tooltip.add(
                Text.translatable(
                        "curse.voodoo-curses." + stack.getOrCreateNbt().getInt("CurseOrBlessing"),
                        Text.translatable(
                                "tooltip.voodoo-curses." + (stack.getOrCreateNbt().getInt("CurseOrBlessing") < FIRST_CURSE_AT
                                        ? "blessing"
                                        : "curse"
                                )
                        )
                ).setStyle(
                        Style.EMPTY.withColor(
                                stack.getOrCreateNbt().getInt("CurseOrBlessing") < FIRST_CURSE_AT ? 53760 : 16711680
                        )
                )
        );
    }
}
