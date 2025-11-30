package dev.tonimatas.fastregions.commands;

import com.mojang.brigadier.CommandDispatcher;
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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegionCommand {
    private static final Map<String, BlockPos> POS1 = new HashMap<>();
    private static final Map<String, BlockPos> POS2 = new HashMap<>();
    
    public RegionCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("region")
                .then(Commands.literal("create").then(Commands.argument("name", StringArgumentType.word()).executes(this::regionCreate)))
                //.then(Commands.literal("expandvert").executes((this::regionExpandVert))) TODO
                .then(Commands.literal("pos1").executes((this::regionPos1)))
                .then(Commands.literal("pos2").executes((this::regionPos2)))
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
            
            Region region = new Region(new BoundingBox(minX, minY, minZ, maxX, maxY, maxZ), List.of(RegionFlag.CAN_BREAK_BLOCK));
            
            if (RegionManager.addRegion(player.level(), name, region)) {

                POS1.remove(player.getName().getString());
                POS2.remove(player.getName().getString());
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

    /* TODO: Region expand vert
    private int regionExpandVert(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        ServerPlayer player = source.getPlayer();

        if (player != null) {
            player.sendSystemMessage(Component.literal("Expand vert"));
            return 1;
        } else {
            source.sendFailure(Component.translatable("key.fastregions.error.players_only"));
            return -1;
        }
    }*/
    
    // TODO: Region flags
}
