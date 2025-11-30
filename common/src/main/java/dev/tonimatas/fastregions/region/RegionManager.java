package dev.tonimatas.fastregions.region;

import dev.tonimatas.fastregions.FastRegionsCommon;
import dev.tonimatas.fastregions.util.LevelUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class RegionManager {
    private static final Map<String, Map<String, Region>> regions = new HashMap<>();
    
    public static boolean cancelEvent(Player player, Level level, BlockPos pos, RegionFlag flag) {
        if (level.isClientSide()) return false;
         
        if (player != null) {
            // TODO: Do permission check
        }
        
        for (Region region : getRegions(level).values()) {
            if (region.contains(pos) && region.has(flag)) {
                return true;
            }
        }
        
        return false;
    }

    public static void loadRegions(MinecraftServer server) {
        server.getAllLevels().forEach(level -> {
            regions.putIfAbsent(LevelUtils.getName(level), new HashMap<>());
            FastRegionsCommon.LOGGER.info(LevelUtils.getName(level));
        });
        FastRegionsCommon.LOGGER.info("Loaded {} regions", 1);
    }
    
    @Nullable
    public static Region getRegion(Level level, String name) {
        return getRegions(level).get(name);
    }

    public static boolean addRegion(Level level, String name, Region region) {
        if (getRegion(level, name) != null) return false;
        regions.get(LevelUtils.getName(level)).put(name, region);
        return true;
    }
    
    public static Map<String, Region> getRegions(Level level) {
        return regions.getOrDefault(LevelUtils.getName(level), Map.of());
    }
}
