package dev.tonimatas.fastregions.region;

import dev.tonimatas.fastregions.FastRegions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Region {
    private final BoundingBox box;
    private final List<RegionFlag> flags;
    private final Map<RegionFlag, AllowedList> allowedLists;
    private int priority;

    public Region(BoundingBox box, List<RegionFlag> flags) {
        this(box, flags, 1);
    }
    
    public Region(BoundingBox box, List<RegionFlag> flags, int priority) {
        this.box = box;
        this.flags = flags;
        this.priority = priority;
        this.allowedLists = new HashMap<>();
    }
    
    public boolean contains(BlockPos pos) {
        return box.isInside(pos);
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
        if (flag.hasAllowedList()) {
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
