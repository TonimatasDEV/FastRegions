package dev.tonimatas.fastregions.region;

import dev.tonimatas.fastregions.FastRegions;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Region {
    private final String name;
    private final int minX;
    private final int minY;
    private final int minZ;
    private final int maxX;
    private final int maxY;
    private final int maxZ;
    private final List<RegionFlag> flags;
    private final Map<RegionFlag, AllowedList> allowedLists;
    private int priority;

    public Region(String name, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, List<RegionFlag> flags) {
        this(name, minX, minY, minZ, maxX, maxY, maxZ, flags, 1, new HashMap<>());
    }
    
    public Region(String name, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, List<RegionFlag> flags, int priority, Map<RegionFlag, AllowedList> allowedLists) {
        this.name = name;
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
        this.flags = flags;
        this.priority = priority;
        this.allowedLists = allowedLists;
    }

    public String getName() {
        return name;
    }

    public boolean contains(int x, int y, int z) {
        return x >= this.minX && x <= this.maxX && z >= this.minZ && z <= this.maxZ && y >= this.minY && y <= this.maxY;
    }

    public boolean contains(BlockPos pos) {
        return contains(pos.getX(), pos.getY(), pos.getZ());
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int value) {
        this.priority = value;
    }
    
    public void addFlag(RegionFlag flag) {
        flags.add(flag);

        if (flag.hasAllowedList()) {
            allowedLists.put(flag, AllowedList.empty());
        }
    }
    
    public void removeFlag(RegionFlag flag) {
        flags.remove(flag);
        
        if (flag.hasAllowedList()) {
            allowedLists.remove(flag);
        }
    }
    
    public boolean has(RegionFlag flag) {
        return has(flag, true);
    }
    
    @ApiStatus.Internal
    public boolean has(RegionFlag flag, boolean warn) {
        if (flag.hasAllowedList() && warn) {
            FastRegions.LOGGER.warn("Please do not use Region#has for allowed-list flags, instead, use Region#hasFlagWithAllowedList.");
        }

        return flags.contains(flag);
    }

    public boolean hasFlagWithAllowedList(RegionFlag flag, String id) {
        if (!flags.contains(flag)) return false;
        if (!flag.hasAllowedList()) return false;

        return !allowedLists.getOrDefault(flag, AllowedList.empty()).contains(id);
    }
}
