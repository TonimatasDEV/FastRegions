package dev.tonimatas.fastregions.region;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

import java.util.List;

public class Region {
    private final BoundingBox box;
    private final List<RegionFlag> flags;
    private int priority;

    public Region(BoundingBox box, List<RegionFlag> flags) {
        this(box, flags, 1);
    }
    
    public Region(BoundingBox box, List<RegionFlag> flags, int priority) {
        this.box = box;
        this.flags = flags;
        this.priority = priority;
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
    }
    
    public boolean has(RegionFlag flag) {
        return flags.contains(flag);
    }
}
