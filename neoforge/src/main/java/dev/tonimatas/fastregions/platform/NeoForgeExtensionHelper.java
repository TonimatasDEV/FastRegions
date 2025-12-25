package dev.tonimatas.fastregions.platform;

import dev.tonimatas.fastregions.impl.allowlist.BlocksExtension;
import dev.tonimatas.fastregions.impl.allowlist.EntitiesExtension;
import dev.tonimatas.fastregions.platform.services.IExtensionHelper;
import dev.tonimatas.fastregions.region.allowlist.AllowedListExtension;

public class NeoForgeExtensionHelper implements IExtensionHelper {
    @Override
    public AllowedListExtension blocks() {
        return new BlocksExtension();
    }

    @Override
    public AllowedListExtension entities() {
        return new EntitiesExtension();
    }
}
