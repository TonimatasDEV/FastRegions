package dev.tonimatas.fastregions.platform;

import dev.tonimatas.fastregions.FastRegions;
import dev.tonimatas.fastregions.platform.services.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public String getModVersion() {
        Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer(FastRegions.MOD_ID);
        return modContainer.map(container -> container.getMetadata().getVersion().getFriendlyString()).orElse(null);
    }

    @Override
    public boolean hasPermission(Player player, String permission) {
        return player.hasPermissions(3); // TODO
    }
}
