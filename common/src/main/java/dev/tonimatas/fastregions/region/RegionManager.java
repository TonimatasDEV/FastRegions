package dev.tonimatas.fastregions.region;

import dev.tonimatas.fastregions.util.LevelUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RegionManager {
    public static Map<String, Map<String, Region>> regions = new HashMap<>();
    
    public static boolean cancelEvent(Player player, Level level, BlockPos pos, RegionFlag flag) {
        if (level.isClientSide()) return false;
         
        if (player != null) {
            // TODO: Do permission check
        }
        
        for (Region region : getRegions(level)) {
            if (region.contains(pos) && region.has(flag)) {
                return true;
            }
        }
        
        return false;
    }
    
    public static Collection<Region> getRegions(Level level) {
        return regions.getOrDefault(LevelUtils.getName(level), Map.of()).values();
    }
}
