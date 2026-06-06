package dev.tonimatas.fastregions;

import dev.tonimatas.fastregions.commands.RegionCommand;
import dev.tonimatas.fastregions.region.RegionManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class FastRegionsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        FastRegions.init();

        CommandRegistrationCallback.EVENT.register((commandDispatcher, commandBuildContext, commandSelection) ->  {
            new RegionCommand(commandDispatcher);
        });

        ServerLifecycleEvents.SERVER_STARTED.register(RegionManager::loadRegions);
    }
}
