package dev.tonimatas.fastregions.region;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public record AllowedList(List<String> allowed) {
    public void add(String id) {
        this.allowed.add(id);
    }

    public void remove(String id) {
        this.allowed.remove(id);
    }
    
    public boolean contains(String id) {
        return this.allowed.contains(id);
    }
    
    public static AllowedList empty() {
        return new AllowedList(new ArrayList<>());
    }
    
    public enum Type {
        BLOCKS,
        ENTITIES,
        NONE;
        
        public static CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
            ServerLevel level = context.getSource().getLevel();
            ServerPlayer player = context.getSource().getPlayer();
            
            if (player != null) {
                // TODO
                return SharedSuggestionProvider.suggest(List.of(), builder);
            } else {
                return builder.buildFuture();
            }
        }
    }
}
