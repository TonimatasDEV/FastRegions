package dev.tonimatas.fastregions.region;

import dev.tonimatas.fastregions.util.PermissionUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class RegionEvents {
    public static boolean cancelBlockEvent(Player player, Level level, BlockPos pos, RegionFlag flag) {
        if (level.isClientSide()) return false;

        Region result = RegionManager.getRegion(level, pos);

        if (result != null) {
            if (result.has(flag)) {
                if (player != null) {
                    return !PermissionUtils.hasRegionBypass(player, result.getName());
                } else {
                    return true;
                }
            }
        }
        
        return false;
    }
}
