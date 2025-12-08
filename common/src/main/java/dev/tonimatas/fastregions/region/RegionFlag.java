package dev.tonimatas.fastregions.region;

public enum RegionFlag {
    BLOCK_BREAK(true),
    BLOCK_INTERACT(true);

    private final boolean hasAllowedList;
    
    RegionFlag(boolean hasAllowedList) {
        this.hasAllowedList = hasAllowedList;
    }

    public boolean hasAllowedList() {
        return this.hasAllowedList;
    }
}
