package dev.tonimatas.fastregions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.tonimatas.fastregions.platform.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FastRegions {
    public static final String MOD_ID = "fastregions";
    public static final Logger LOGGER = LoggerFactory.getLogger("Fast Regions");
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void init() {
        String platform = Services.PLATFORM.getPlatformName();
        String version = Services.PLATFORM.getModVersion();
        LOGGER.info("Fast Regions {} {} has been initialized!", version, platform);
    }
}
