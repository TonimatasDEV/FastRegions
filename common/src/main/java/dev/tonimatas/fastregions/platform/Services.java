package dev.tonimatas.fastregions.platform;

import dev.tonimatas.fastregions.FastRegions;
import dev.tonimatas.fastregions.platform.services.IExtensionHelper;
import dev.tonimatas.fastregions.platform.services.IPlatformHelper;

import java.util.ServiceLoader;

public class Services {
    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final IExtensionHelper EXTENSION = load(IExtensionHelper.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz).findFirst().orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        FastRegions.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}
