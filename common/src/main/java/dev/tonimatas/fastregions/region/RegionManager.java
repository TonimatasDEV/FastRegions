package dev.tonimatas.fastregions.region;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import dev.tonimatas.fastregions.FastRegions;
import dev.tonimatas.fastregions.util.LevelUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelResource;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class RegionManager {
    private static Path PATH;
    private static Map<String, ArrayList<Region>> regions;

    public static void loadRegions(MinecraftServer server) {
        PATH = server.getWorldPath(LevelResource.ROOT).resolve("fastregions.json");
        regions = new HashMap<>();

        if (!Files.isRegularFile(PATH)) {
            try {
                Files.createFile(PATH);
            } catch (IOException e) {
                FastRegions.LOGGER.error("Error creating the FastRegions storage file.");
            }
        }

        try {
            String json = Files.readString(PATH, StandardCharsets.UTF_8);
            Map<String, ArrayList<Region>> jsonRegions = FastRegions.GSON.fromJson(json, FastRegions.GSON_TYPE);

            if (jsonRegions != null) {
                regions = jsonRegions;
            }
        } catch (IOException e) {
            FastRegions.LOGGER.error("Error loading the FastRegions storage file.");
        }

        server.getAllLevels().forEach(level -> regions.putIfAbsent(LevelUtils.getName(level), new ArrayList<>()));
        FastRegions.LOGGER.info("Loaded {} regions", regions.size());
    }

    public static void saveRegions() {
        String json = FastRegions.GSON.toJson(regions);
        Path tempFile = PATH.resolveSibling(PATH.getFileName() + ".tmp");

        try (BufferedWriter writer = Files.newBufferedWriter(tempFile, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
            writer.write(json);
            writer.flush();
            Files.move(tempFile, PATH, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            FastRegions.LOGGER.error("Error saving regions.");
        }
    }

    @Nullable
    public static Region getRegion(Level level, String name) {
        Region result = null;

        for (Region region : getRegions(level)) {
            if (region.getName().equalsIgnoreCase(name)) {
                result = region;
                break;
            }
        }

        return result;
    }

    public static boolean addRegion(Level level, String name, Region region) {
        if (getRegion(level, name) != null) return false;
        regions.get(LevelUtils.getName(level)).add(region);
        return true;
    }

    public static void removeRegion(Level level, String name) {
        Region region = getRegion(level, name);
        regions.get(LevelUtils.getName(level)).remove(region);
    }

    public static List<Region> getRegions(Level level) {
        return regions.getOrDefault(LevelUtils.getName(level), new ArrayList<>());
    }

    public static CompletableFuture<Suggestions> getCommandRegionSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) return builder.buildFuture();
        List<String> regionNameList = regions.get(LevelUtils.getName(player.level())).stream().map(Region::getName).toList();
        return SharedSuggestionProvider.suggest(regionNameList, builder);
    }

    @Nullable
    public static Region getRegion(Level level, BlockPos pos) {
        Region result = null;

        for (Region region : RegionManager.getRegions(level)) {
            if (!region.contains(pos)) continue;

            if (result == null || result.getPriority() < region.getPriority()) {
                result = region;
            }
        }

        return result;
    }
}
