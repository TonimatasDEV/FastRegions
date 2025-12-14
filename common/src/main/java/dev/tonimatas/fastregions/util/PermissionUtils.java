package dev.tonimatas.fastregions.util;

import dev.tonimatas.fastregions.platform.Services;
import net.minecraft.world.entity.player.Player;

public class PermissionUtils {
    public static boolean hasRegionBypass(Player player, String regionName) {
        return Services.PLATFORM.hasPermission(player, "fastregions.region.bypass." + regionName);
    }
}
