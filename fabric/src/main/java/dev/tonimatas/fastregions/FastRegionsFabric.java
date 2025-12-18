package dev.tonimatas.fastregions;

import dev.tonimatas.fastregions.commands.RegionCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class FastRegionsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        FastRegions.init();

        CommandRegistrationCallback.EVENT.register((commandDispatcher, commandBuildContext, commandSelection) ->  {
            new RegionCommand(commandDispatcher);
        });
    }
}
