package dev.tonimatas.fastregions.region;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import dev.tonimatas.fastregions.region.allowlist.AllowedListTypes;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.server.level.ServerPlayer;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public enum RegionFlag {
    BLOCK_BREAK(AllowedListTypes.BLOCKS),
    BLOCK_INTERACT(AllowedListTypes.BLOCKS),
    BLOCK_PLACE(AllowedListTypes.BLOCKS),
    INVINCIBLE_PLAYERS,
    PVP,
    EXPLOSION,
    ENTITY_DAMAGE(AllowedListTypes.ENTITIES),
    USE_ITEMS(AllowedListTypes.ITEMS), // TODO: Implement
    LIGHTNING_BOLTS,
    TOUCH_DRIPLEAF,
    ROTATE_ITEM_FRAME,
    DESTROY_ITEM_FRAME,
    DESTROY_PAINTING,
    LEAF_DECAY,
    GRASS_SPREAD,
    MYCELIUM_SPREAD,
    CROP_GROWTH, // TODO: AllowList?
    VINE_GROWTH,
    SNOW_MELT,
    SNOW_FALL,
    ICE_FORM,
    ICE_MELT,
    FROSTED_ICE_FORM,
    FROSTED_ICE_MELT,
    FLOWING_FLUIDS, // TODO: AllowList
    SNOWGOLEM_TRAILS,
    ITEM_PICKUP, // TODO: AllowList?
    FIREWORK_DAMAGE,
    WITHER_DAMAGE, // TODO: EFFECT_DAMAGE with AllowList?
    CORAL_DIE,
    VEHICLE_INTERACT, // TODO: AllowList?
    RIDE_ENTITY, // TODO: AllowList?
    VEHICLE_DESTROY; // TODO: AllowList?

    private final AllowedListTypes allowedListType;

    RegionFlag() {
        this(null);
    }

    RegionFlag(AllowedListTypes allowedListType) {
        this.allowedListType = allowedListType;
    }

    public static RegionFlag getFlag(String flagName) {
        try {
            return RegionFlag.valueOf(flagName);
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }

    public static CompletableFuture<Suggestions> getCommandFlagsSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        String[] args = context.getLastChild().getInput().split(" ");
        boolean addedInRegion = !args[3].equalsIgnoreCase("add");
        ServerPlayer player = context.getSource().getPlayer();
        Region region = RegionManager.getRegion(context.getSource().getLevel(), args[2]);

        if (player == null || region == null) return builder.buildFuture();

        String[] flagsNames = Arrays.stream(RegionFlag.values())
                .filter(regionFlag -> region.has(regionFlag, false) == addedInRegion)
                .map(RegionFlag::toString)
                .toArray(String[]::new);

        return SharedSuggestionProvider.suggest(flagsNames, builder);
    }

    public boolean hasAllowedList() {
        return this.allowedListType != null;
    }

    public AllowedListTypes allowedListType() {
        return this.allowedListType;
    }
}
