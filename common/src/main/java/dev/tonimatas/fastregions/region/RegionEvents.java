package dev.tonimatas.fastregions.region;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class RegionEvents {
    public static boolean cancelBlockEvent(Player player, Level level, BlockPos pos, RegionFlag flag) {
        if (level.isClientSide()) return false;

        if (player != null) {
            // TODO: Do permission check
        }

        Region result = RegionManager.getRegion(level, pos);
        return result != null && result.has(flag);
    }
}
