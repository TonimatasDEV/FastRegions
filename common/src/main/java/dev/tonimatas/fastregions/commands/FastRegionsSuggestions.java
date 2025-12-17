package dev.tonimatas.fastregions.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import dev.tonimatas.fastregions.region.Region;
import dev.tonimatas.fastregions.region.RegionFlag;
import dev.tonimatas.fastregions.region.RegionManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.concurrent.CompletableFuture;

public class FastRegionsSuggestions {
    public static CompletableFuture<Suggestions> getAllowedListSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder, boolean add) {
        CommandSourceStack source = context.getSource();
        ServerPlayer player = source.getPlayer();
        ServerLevel level = source.getLevel();
        String regionName = context.getArgument("region", String.class);
        String flagName = context.getArgument("flag", String.class);
        Region region = RegionManager.getRegion(level, regionName);

        if (player != null) {
            if (region != null) {
                RegionFlag flag = RegionFlag.getFlag(flagName);

                if (flag != null) {
                    if (region.has(flag)) {
                        if (add) {
                            System.out.println(flag.allowedListType().getAddSuggestions(region, flag));
                            flag.allowedListType().getAddSuggestions(region, flag).forEach(builder::suggest);
                        } else {
                            flag.allowedListType().getRemoveSuggestions(region, flag).forEach(builder::suggest);
                        }
                    }
                }
            }
        }
        
        return builder.buildFuture();
    }
}
