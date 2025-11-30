package dev.tonimatas.fastregions.region;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.storage.WorldData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RegionManager {
    public static Map<String, Map<String, Region>> regions = new HashMap<>();
    
    public static boolean cancelEvent(Player player, LevelAccessor level, BlockPos pos, RegionFlag flag) {
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
    
    public static Collection<Region> getRegions(LevelAccessor level) {
        return regions.get(((WorldData) level.getLevelData()).getLevelName()).values();
    }
}
