package dev.tonimatas.fastregions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dev.tonimatas.fastregions.platform.Services;
import dev.tonimatas.fastregions.region.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class FastRegions {
    public static final String MOD_ID = "fastregions";
    public static final Logger LOGGER = LoggerFactory.getLogger("FastRegions");
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final Type GSON_TYPE = new TypeToken<Map<String, ArrayList<Region>>>() {}.getType();

    public static void init() {
        String platform = Services.PLATFORM.getPlatformName();
        String version = Services.PLATFORM.getModVersion();
        LOGGER.info("FastRegions {} {} has been initialized!", version, platform);
    }
}
