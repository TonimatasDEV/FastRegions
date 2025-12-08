package dev.tonimatas.fastregions.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import dev.tonimatas.fastregions.region.Region;
import dev.tonimatas.fastregions.region.RegionFlag;
import dev.tonimatas.fastregions.region.RegionManager;
import dev.tonimatas.fastregions.util.LevelUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegionCommand {
    private static final Map<String, BlockPos> POS1 = new HashMap<>();
    private static final Map<String, BlockPos> POS2 = new HashMap<>();

    public RegionCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("region")
                .then(Commands.literal("create")
                        .then(Commands.argument("name", StringArgumentType.word())
                                .executes(this::regionCreate)))
                .then(Commands.literal("expand-vert").executes((this::regionExpandVert)))
                .then(Commands.literal("remove")
                        .then(Commands.argument("region", StringArgumentType.word())
                                .suggests(RegionManager::getCommandRegionSuggestions)
                                .executes(this::removeRegion)))
                .then(Commands.literal("pos1")
                        .executes((this::regionPos1)))
                .then(Commands.literal("pos2")
                        .executes((this::regionPos2)))
                .then(Commands.literal("priority")
                        .then(Commands.argument("region", StringArgumentType.word())
                                .suggests(RegionManager::getCommandRegionSuggestions)
                                .then(Commands.argument("value", IntegerArgumentType.integer(1, Integer.MAX_VALUE))
                                        .executes(this::regionPriority))))
                .then(Commands.literal("flags")
                        .then(Commands.argument("region", StringArgumentType.word())
                                .suggests(RegionManager::getCommandRegionSuggestions)
                                .then(Commands.literal("add")
                                        .then(Commands.argument("flag", StringArgumentType.word())
                                                .suggests(RegionFlag::getCommandFlagsSuggestions) // TODO: Improve
                                                .executes(this::addRegionFlag)))
                                .then(Commands.literal("remove")
                                        .then(Commands.argument("flag", StringArgumentType.word())
                                                .suggests(RegionFlag::getCommandFlagsSuggestions) // TODO: Improve
                                                .executes(this::removeRegionFlag)))
                                .then(Commands.literal("allow-list")
                                        .then(Commands.argument("flag", StringArgumentType.word())
                                                .suggests(RegionFlag::getCommandFlagsSuggestions)
                                                .then(Commands.literal("add")
                                                        .then(Commands.argument("id", StringArgumentType.word())
                                                                .suggests(null) // TODO: Implement
                                                                .executes(null))) // TODO: Implement
                                                .then(Commands.literal("remove")
                                                        .then(Commands.argument("id", StringArgumentType.word())
                                                                .suggests(null) // TODO: Implement
                                                                .executes(null))) // TODO: Implement
                                        )
                                )
                        )
                )
        );
    }

    private int regionPos1(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        ServerPlayer player = source.getPlayer();

        if (player != null) {
            BlockPos pos = player.getOnPos();
            POS1.put(player.getName().getString(), pos);
            source.sendSuccess(() -> Component.translatable("key.fastregions.pos.success", 1, pos.getX(), pos.getY(), pos.getZ()), false);
            return 1;
        } else {
            source.sendFailure(Component.translatable("key.fastregions.error.players_only"));
            return -1;
        }
    }

    private int regionPos2(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        ServerPlayer player = source.getPlayer();

        if (player != null) {
            BlockPos pos = player.getOnPos();
            POS2.put(player.getName().getString(), pos);
            source.sendSuccess(() -> Component.translatable("key.fastregions.pos.success", 2, pos.getX(), pos.getY(), pos.getZ()), false);
            return 1;
        } else {
            source.sendFailure(Component.translatable("key.fastregions.error.players_only"));
            return -1;
        }
    }

    private int regionCreate(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        ServerPlayer player = source.getPlayer();
        String name = context.getArgument("name", String.class);

        if (player != null) {
            BlockPos pos1 = POS1.get(player.getName().getString());
            BlockPos pos2 = POS2.get(player.getName().getString());

            if (pos1 == null || pos2 == null) {
                source.sendFailure(Component.translatable("key.fastregions.create.error.missing"));
                return -1;
            }

            int minX = Math.min(pos1.getX(), pos2.getX());
            int maxX = Math.max(pos1.getX(), pos2.getX());
            int minY = Math.min(pos1.getY(), pos2.getY());
            int maxY = Math.max(pos1.getY(), pos2.getY());
            int minZ = Math.min(pos1.getZ(), pos2.getZ());
            int maxZ = Math.max(pos1.getZ(), pos2.getZ());

            Region region = new Region(new BoundingBox(minX, minY, minZ, maxX, maxY, maxZ), new ArrayList<>());

            if (RegionManager.addRegion(player.level(), name, region)) {
                POS1.remove(player.getName().getString());
                POS2.remove(player.getName().getString());
                RegionManager.saveRegions();
                source.sendSuccess(() -> Component.translatable("key.fastregions.create.success", name, LevelUtils.getName(player.level())), true);
                return 1;
            } else {
                source.sendFailure(Component.translatable("key.fastregions.create.error.used_name"));
                return -1;
            }
        } else {
            source.sendFailure(Component.translatable("key.fastregions.error.players_only"));
            return -1;
        }
    }
    
    private int removeRegion(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        ServerPlayer player = source.getPlayer();
        String regionName = context.getArgument("region", String.class);

        if (player != null) {
            Region region = RegionManager.getRegion(player.level(), regionName);

            if (region == null) {
                source.sendFailure(Component.translatable("key.fastregions.error.unknown_region", regionName));
                return -1;
            }

            RegionManager.removeRegion(source.getLevel(), regionName);
            source.sendSuccess(() -> Component.translatable("key.fastregions.remove.success", regionName), true);
            return 1;
        } else {
            source.sendFailure(Component.translatable("key.fastregions.error.players_only"));
            return -1;
        }
    }

    private int regionPriority(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        ServerPlayer player = source.getPlayer();
        String regionName = context.getArgument("region", String.class);
        int value = context.getArgument("value", Integer.class);

        if (player != null) {
            Region region = RegionManager.getRegion(player.level(), regionName);

            if (region == null) {
                source.sendFailure(Component.translatable("key.fastregions.error.unknown_region", regionName));
                return -1;
            }

            int oldValue = region.getPriority();

            region.setPriority(value);
            RegionManager.saveRegions();
            source.sendSuccess(() -> Component.translatable("key.fastregions.priority.success", regionName, oldValue, value), true);

            return 1;
        } else {
            source.sendFailure(Component.translatable("key.fastregions.error.players_only"));
            return -1;
        }
    }

    private int regionExpandVert(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        ServerPlayer player = source.getPlayer();
        ServerLevel level = source.getLevel();

        if (player != null) {
            BlockPos pos1 = POS1.get(player.getName().getString());
            BlockPos pos2 = POS2.get(player.getName().getString());

            if (pos1 == null || pos2 == null) {
                source.sendFailure(Component.translatable("key.fastregions.create.error.missing"));
                return -1;
            }

            BlockPos maxY = pos1.atY(level.getMaxBuildHeight());
            BlockPos minY = pos2.atY(level.getMinBuildHeight());

            POS1.put(player.getName().getString(), maxY);
            POS2.put(player.getName().getString(), minY);

            source.sendSuccess(() -> Component.translatable("key.fastregions.expand_vert.success", minY.getY(), maxY.getY()), false);
            return 1;
        } else {
            source.sendFailure(Component.translatable("key.fastregions.error.players_only"));
            return -1;
        }
    }
    
    public int addRegionFlag(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        ServerPlayer player = source.getPlayer();
        ServerLevel level = source.getLevel();
        String regionName = context.getArgument("region", String.class);
        String flagName = context.getArgument("flag", String.class);
        Region region = RegionManager.getRegion(level, regionName);

        if (player != null) {
            if (region != null) {
                RegionFlag flag = RegionFlag.valueOf(flagName);

                if (!region.has(flag)) {
                    region.addFlag(flag);
                    source.sendSuccess(() -> Component.translatable("key.fastregions.flags.add.success", flagName, regionName), false);
                    return 1;
                } else {
                    source.sendFailure(Component.translatable("key.fastregions.flags.add.error", regionName, flagName));
                    return -1;
                }
            } else {
                source.sendFailure(Component.translatable("key.fastregions.error.unknown_region", regionName));
                return -1;
            }
        } else {
            source.sendFailure(Component.translatable("key.fastregions.error.players_only"));
            return -1;
        }
    }
    
    public int removeRegionFlag(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        ServerPlayer player = source.getPlayer();
        ServerLevel level = source.getLevel();
        String regionName = context.getArgument("region", String.class);
        String flagName = context.getArgument("flag", String.class);
        Region region = RegionManager.getRegion(level, regionName);

        if (player != null) {
            if (region != null) {
                RegionFlag flag = RegionFlag.valueOf(flagName);
                
                if (region.has(flag)) {
                    region.removeFlag(flag);
                    source.sendSuccess(() -> Component.translatable("key.fastregions.flags.remove.success", flagName, regionName), false);
                    return 1;
                } else {
                    source.sendFailure(Component.translatable("key.fastregions.flags.remove.error", regionName, flagName));
                    return -1;
                }
            } else {
                source.sendFailure(Component.translatable("key.fastregions.error.unknown_region", regionName));
                return -1;
            }
        } else {
            source.sendFailure(Component.translatable("key.fastregions.error.players_only"));
            return -1;
        }
    }

    // TODO: Region flags allowed-lists
}
