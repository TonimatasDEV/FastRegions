package dev.tonimatas.fastregions;

import dev.tonimatas.fastregions.platform.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FastRegions {
    public static final String MOD_ID = "fastregions";
    public static final Logger LOGGER = LoggerFactory.getLogger("Fast Regions");

    public static void init() {
        String platform = Services.PLATFORM.getPlatformName();
        String version = Services.PLATFORM.getModVersion();
        LOGGER.info("Fast Regions {} {} has been initialized!", version, platform);
    }
}
