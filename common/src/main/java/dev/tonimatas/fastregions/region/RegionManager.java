package dev.tonimatas.fastregions.region;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import dev.tonimatas.fastregions.FastRegions;
import dev.tonimatas.fastregions.util.LevelUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class RegionManager {
    private static final Map<String, Map<String, Region>> regions = new HashMap<>();

    public static void loadRegions(MinecraftServer server) {
        server.getAllLevels().forEach(level -> {
            regions.putIfAbsent(LevelUtils.getName(level), new HashMap<>());
            FastRegions.LOGGER.info(LevelUtils.getName(level));
        });
        // TODO: Load regions
        FastRegions.LOGGER.info("Loaded {} regions", 1);
    }
    
    public static void saveRegions() {
        // TODO
    }
    
    @Nullable
    public static Region getRegion(Level level, String name) {
        return getRegions(level).get(name);
    }

    public static boolean addRegion(Level level, String name, Region region) {
        if (getRegion(level, name) != null) return false;
        regions.get(LevelUtils.getName(level)).put(name, region);
        return true;
    }
    
    public static Map<String, Region> getRegions(Level level) {
        return regions.getOrDefault(LevelUtils.getName(level), Map.of());
    }
    
    public static CompletableFuture<Suggestions> getCommandRegionSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) return builder.buildFuture();
        String[] regionNameList = regions.get(LevelUtils.getName(player.level())).keySet().toArray(new String[0]);
        return SharedSuggestionProvider.suggest(regionNameList, builder);
    }
}
