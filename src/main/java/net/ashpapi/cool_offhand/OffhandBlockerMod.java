package net.ashpapi.cool_offhand;

import net.minecraftforge.fml.common.Mod;

@Mod(OffhandBlockerMod.MODID)
public class OffhandBlockerMod {
    public static final String MODID = "offhandblocker";

    public OffhandBlockerMod() {
        // Подписчик событий автоматически регистрируется через аннотацию в OffhandTorchLanternPlaceBlocker
    }
}
