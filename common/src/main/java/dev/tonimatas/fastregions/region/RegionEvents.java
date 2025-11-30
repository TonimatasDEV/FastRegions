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

        Region result = null;
        
        for (Region region : RegionManager.getRegions(level).values()) {
            if (!region.contains(pos)) continue;
            
            if (result == null || result.getPriority() < region.getPriority()) {
                result = region;
            }
        }

        return result != null && result.has(flag);
    }
}
