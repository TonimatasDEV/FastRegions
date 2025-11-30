package dev.tonimatas.fastregions.platform;

import dev.tonimatas.fastregions.platform.services.IPlatformHelper;
import net.neoforged.fml.loading.LoadingModList;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public String getModVersion() {
        return LoadingModList.get().getModFileById("fastregions").versionString();
    }
}
