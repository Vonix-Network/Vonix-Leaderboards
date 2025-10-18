package com.leclowndu93150.leaderboards;

import com.leclowndu93150.leaderboards.data.Leaderboard;
import com.leclowndu93150.leaderboards.data.PlayerDataTracker;
import com.leclowndu93150.leaderboards.integration.FTBQuestsIntegration;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

public class LeaderboardRegistry {
    public static final Map<ResourceLocation, Leaderboard> LEADERBOARDS = new LinkedHashMap<>();

    public static void register() {
        LEADERBOARDS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "deaths"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "deaths"),
                        Component.translatable("leaderboard.leaderboards.deaths"),
                        Stats.CUSTOM.get(Stats.DEATHS),
                        false
                )
        );

        LEADERBOARDS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "mob_kills"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "mob_kills"),
                        Component.translatable("leaderboard.leaderboards.mob_kills"),
                        Stats.CUSTOM.get(Stats.MOB_KILLS),
                        false
                )
        );

        LEADERBOARDS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "player_kills"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "player_kills"),
                        Component.translatable("leaderboard.leaderboards.player_kills"),
                        Stats.CUSTOM.get(Stats.PLAYER_KILLS),
                        false
                )
        );

        LEADERBOARDS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "time_played"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "time_played"),
                        Component.translatable("leaderboard.leaderboards.time_played"),
                        Stats.CUSTOM.get(Stats.PLAY_TIME),
                        false,
                        Leaderboard.FromStat.TIME
                )
        );

        LEADERBOARDS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "jumps"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "jumps"),
                        Component.translatable("leaderboard.leaderboards.jumps"),
                        Stats.CUSTOM.get(Stats.JUMP),
                        false
                )
        );

        LEADERBOARDS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "distance_walked"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "distance_walked"),
                        Component.translatable("leaderboard.leaderboards.distance_walked"),
                        Stats.CUSTOM.get(Stats.WALK_ONE_CM),
                        false,
                        Leaderboard.FromStat.DISTANCE
                )
        );

        LEADERBOARDS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "distance_sprinted"),
                new Leaderboard.FromStat(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "distance_sprinted"),
                        Component.translatable("leaderboard.leaderboards.distance_sprinted"),
                        Stats.CUSTOM.get(Stats.SPRINT_ONE_CM),
                        false,
                        Leaderboard.FromStat.DISTANCE
                )
        );

        LEADERBOARDS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "deaths_per_hour"),
                new Leaderboard(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "deaths_per_hour"),
                        Component.translatable("leaderboard.leaderboards.deaths_per_hour"),
                        player -> {
                            double dph = getDPH(player);
                            return Component.literal(dph < 0D ? "-" : String.format("%.2f", dph));
                        },
                        Comparator.comparingDouble(LeaderboardRegistry::getDPH).reversed(),
                        player -> getDPH(player) >= 0D
                )
        );

        LEADERBOARDS.put(
                ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "last_seen"),
                new Leaderboard(
                        ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "last_seen"),
                        Component.translatable("leaderboard.leaderboards.last_seen"),
                        player -> {
                            if (player.server.getPlayerList().getPlayer(player.getUUID()) != null) {
                                return Component.translatable("gui.online").withStyle(ChatFormatting.GREEN);
                            } else {
                                long lastSeen = PlayerDataTracker.getLastSeen(player.getUUID());
                                if (lastSeen == 0L) {
                                    return Component.literal("-");
                                }
                                long worldTime = player.server.overworld().getGameTime();
                                int time = (int) (worldTime - lastSeen);
                                return Leaderboard.FromStat.TIME.apply(time);
                            }
                        },
                        Comparator.comparingLong(p -> {
                            if (p.server.getPlayerList().getPlayer(p.getUUID()) != null) {
                                return 0L;
                            }
                            return p.server.overworld().getGameTime() - PlayerDataTracker.getLastSeen(p.getUUID());
                        }),
                        player -> PlayerDataTracker.getLastSeen(player.getUUID()) != 0L || player.server.getPlayerList().getPlayer(player.getUUID()) != null
                )
        );

        if (FTBQuestsIntegration.isAvailable()) {
            LEADERBOARDS.put(
                    ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "quest_completions"),
                    new Leaderboard(
                            ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "quest_completions"),
                            Component.translatable("leaderboard.leaderboards.quest_completions"),
                            player -> Component.literal(String.valueOf(FTBQuestsIntegration.getPlayerQuestCompletions(player))),
                            Comparator.comparingInt(FTBQuestsIntegration::getPlayerQuestCompletions).reversed(),
                            player -> FTBQuestsIntegration.getPlayerQuestCompletions(player) > 0
                    )
            );

            LEADERBOARDS.put(
                    ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "quest_completion_percentage"),
                    new Leaderboard(
                            ResourceLocation.fromNamespaceAndPath(Leaderboards.MODID, "quest_completion_percentage"),
                            Component.translatable("leaderboard.leaderboards.quest_completion_percentage"),
                            player -> {
                                double percentage = FTBQuestsIntegration.getQuestCompletionPercentage(player);
                                return Component.literal(String.format("%.1f%%", percentage));
                            },
                            Comparator.comparingDouble(FTBQuestsIntegration::getQuestCompletionPercentage).reversed(),
                            player -> FTBQuestsIntegration.getPlayerQuestCompletions(player) > 0
                    )
            );
        }

        VanillaStatsRegistry.register();
    }

    private static double getDPH(com.leclowndu93150.leaderboards.data.PlayerStatsWrapper player) {
        int playTime = player.getStats().getValue(Stats.CUSTOM.get(Stats.PLAY_TIME));
        if (playTime > 0) {
            double hours = playTime / 72000D;
            if (hours >= 1D) {
                return (double) player.getStats().getValue(Stats.CUSTOM.get(Stats.DEATHS)) / hours;
            }
        }
        return -1D;
    }
}
