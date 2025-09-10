package m6a5x98.voodoocurses;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class VoodooCursesUtils {
    public static final int FIRST_CURSE_AT = 5;
    public static final int NONE = 0;
    public static final int HASTE = 1;
    public static final int SPEED = 2;
    public static final int LUCK = 3;
    public static final int FULL_BEACON = 4;
    public static final int NO_ARMOR = 5;
    public static final int NO_ENCHANTS = 6;
    public static final int SLOWNESS = 7;
    public static final int CONSTANT_DAMAGE = 8;

    public static void applyCurseOrBlessing(@Nullable PlayerEntity target, int curseOrBlessing, PlayerEntity from) {
        if (target == null) return;
        switch (curseOrBlessing) {
            case HASTE -> target.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 10));
            case SPEED -> target.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10));
            case LUCK -> target.addStatusEffect(new StatusEffectInstance(StatusEffects.LUCK, 10));
            case FULL_BEACON -> {
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 10));
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10, 2));
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 10, 2));
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 10, 2));
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 10, 2));
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 10, 2));
                grantUltimateCurseOrBlessingAdvancements(curseOrBlessing, target);
            }
            case NO_ARMOR -> {
                boolean hasArmor = false;
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    if (slot.getType() == EquipmentSlot.Type.ARMOR) {
                        ItemStack stack = target.getEquippedStack(slot);
                        if (!stack.isEmpty() && stack.getItem() != Items.AIR) {
                            hasArmor = true;
                        }
                    }
                }

                if (hasArmor) {
                    DamageSource damageSource = new DamageSource(
                            target.getWorld().getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).getEntry(ModDamageTypes.VOODOO_NO_ARMOR).get()
                    );
                    target.damage(damageSource, 1.0f);
                }
            }
            case NO_ENCHANTS -> {
                boolean hasArmor = false;
                boolean hasEnchants = false;
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    if (slot.getType() == EquipmentSlot.Type.ARMOR) {
                        ItemStack stack = target.getEquippedStack(slot);
                        if (!stack.isEmpty()) {
                            hasArmor = true;
                            if (stack.hasEnchantments()) {
                                hasEnchants = true;
                            }
                        }
                    }
                }

                if (hasArmor && hasEnchants) {
                    DamageSource damageSource = new DamageSource(
                            target.getWorld().getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).getEntry(ModDamageTypes.VOODOO_NO_ENCHANTS).get()
                    );
                    target.damage(damageSource, 1.0f);
                }
            }
            case SLOWNESS -> target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 10));
            case CONSTANT_DAMAGE -> {
                DamageSource damageSource = new DamageSource(
                        target.getWorld().getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).getEntry(ModDamageTypes.VOODOO_CONSTANT_DAMAGE).get()
                );
                target.animateDamage(target.getDamageTiltYaw() * 10);
                if (!(target.getHealth() - 1 <= 0)) target.damage(damageSource, 1);
                grantUltimateCurseOrBlessingAdvancements(curseOrBlessing, target);
            }
            case NONE -> {
                return;
            }
            default -> {
                return;
            }
        }
        grantFirstCurseOrBlessingAdvancements(curseOrBlessing, from);
    }

    private static void grantFirstCurseOrBlessingAdvancements(int curse, PlayerEntity user) {
        VoodooCursesUtils.grantAdvancement(
                (ServerPlayerEntity) user,
                user.getWorld().getServer(),
                new Identifier(VoodooCurses.MOD_ID + "/first_" + (curse < FIRST_CURSE_AT
                        ? "blessing"
                        : "curse"
                    )
                )
        );
    }

    private static void grantUltimateCurseOrBlessingAdvancements(int curse, PlayerEntity user) {
        VoodooCursesUtils.grantAdvancement(
                (ServerPlayerEntity) user,
                user.getWorld().getServer(),
                new Identifier(VoodooCurses.MOD_ID + "/ultimate_" + (curse < FIRST_CURSE_AT
                        ? "blessing"
                        : "curse"
                    )
                )
        );
    }

    public static void grantAdvancement(ServerPlayerEntity player, MinecraftServer server, Identifier id) {
        Advancement advancement = server.getAdvancementLoader().get(id);
        if (advancement != null) {
            AdvancementProgress progress = player.getAdvancementTracker().getProgress(advancement);
            for (String criterion : progress.getUnobtainedCriteria()) {
                player.getAdvancementTracker().grantCriterion(advancement, criterion);
            }
        }
    }
}
