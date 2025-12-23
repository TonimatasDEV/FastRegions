package dev.tonimatas.fastregions.region.allowlist;

import dev.tonimatas.fastregions.region.Region;
import dev.tonimatas.fastregions.region.RegionFlag;
import dev.tonimatas.fastregions.region.allowlist.impl.BlocksExtension;
import dev.tonimatas.fastregions.region.allowlist.impl.EntitiesExtension;

import java.util.List;

public enum AllowedListTypes {
    BLOCKS(new BlocksExtension()),
    ENTITIES(new EntitiesExtension());

    private final AllowedListExtension extension;

    AllowedListTypes(AllowedListExtension extension) {
        this.extension = extension;
    }

    public List<String> getIDs() {
        return extension.getIDs();
    }

    public List<String> getAddSuggestions(Region region, RegionFlag flag) {
        return getSuggestions(region, flag, false);
    }

    public List<String> getRemoveSuggestions(Region region, RegionFlag flag) {
        return getSuggestions(region, flag, true);
    }

    private List<String> getSuggestions(Region region, RegionFlag flag, boolean remove) {
        return extension.getIDs().stream().filter((s) -> remove == region.allowedListHas(flag, s)).toList();
    }
}
