package dev.tonimatas.fastregions.platform;

import dev.tonimatas.fastregions.FastRegions;
import dev.tonimatas.fastregions.platform.services.IPlatformHelper;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.loading.LoadingModList;

public class NeoForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public String getModVersion() {
        return LoadingModList.get().getModFileById(FastRegions.MOD_ID).versionString();
    }

    @Override
    public boolean hasPermission(Player player, String permission) {
        return player.hasPermissions(3); // TODO
    }
}
