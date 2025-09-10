package m6a5x98.voodoocurses;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModDamageTypes {
    public static final RegistryKey<DamageType> VOODOO_NO_ARMOR =
            RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(VoodooCurses.MOD_ID, "voodoo_no_armor"));
    public static final RegistryKey<DamageType> VOODOO_NO_ENCHANTS =
            RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(VoodooCurses.MOD_ID, "voodoo_no_enchants"));
    public static final RegistryKey<DamageType> VOODOO_CONSTANT_DAMAGE =
            RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(VoodooCurses.MOD_ID, "voodoo_constant_damage"));
}
