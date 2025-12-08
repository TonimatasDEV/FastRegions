package dev.tonimatas.fastregions.region;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.server.level.ServerPlayer;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public enum RegionFlag {
    BLOCK_BREAK(true),
    BLOCK_INTERACT(true);

    private final boolean hasAllowedList;
    
    RegionFlag(boolean hasAllowedList) {
        this.hasAllowedList = hasAllowedList;
    }

    public boolean hasAllowedList() {
        return this.hasAllowedList;
    }

    public static CompletableFuture<Suggestions> getCommandFlagsSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) return builder.buildFuture();
        String[] regionNameList = Arrays.stream(RegionFlag.values()).map(RegionFlag::toString).toArray(String[]::new);
        return SharedSuggestionProvider.suggest(regionNameList, builder);
    }
}
