package com.leclowndu93150.leaderboards.integration;

import com.leclowndu93150.leaderboards.data.PlayerStatsWrapper;
import dev.ftb.mods.ftbquests.api.FTBQuestsAPI;
import dev.ftb.mods.ftbquests.quest.ServerQuestFile;
import dev.ftb.mods.ftbquests.quest.TeamData;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.fml.ModList;

public class FTBQuestsIntegration {
    private static final String FTBQUESTS_MOD_ID = "ftbquests";

    public static boolean isAvailable() {
        return ModList.get().isLoaded(FTBQUESTS_MOD_ID);
    }

    public static int getPlayerQuestCompletions(PlayerStatsWrapper player) {
        if (!player.isOnline()) {
            return 0;
        }
        return getPlayerQuestCompletionsInternal(player.getOnlinePlayer());
    }

    private static int getPlayerQuestCompletionsInternal(ServerPlayer player) {
        if (!isAvailable()) {
            return 0;
        }

        try {
            ServerQuestFile questFile = (ServerQuestFile) FTBQuestsAPI.api().getQuestFile(false);
            if (questFile == null) {
                return 0;
            }

            TeamData teamData = questFile.getOrCreateTeamData(player);
            if (teamData == null) {
                return 0;
            }

            int[] completedCount = {0};
            questFile.forAllQuests(quest -> {
                if (teamData.isCompleted(quest)) {
                    completedCount[0]++;
                }
            });

            return completedCount[0];
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getTotalQuestCount() {
        if (!isAvailable()) {
            return 0;
        }

        try {
            ServerQuestFile questFile = (ServerQuestFile) FTBQuestsAPI.api().getQuestFile(false);
            if (questFile == null) {
                return 0;
            }

            int[] totalCount = {0};
            questFile.forAllQuests(quest -> totalCount[0]++);
            return totalCount[0];
        } catch (Exception e) {
            return 0;
        }
    }

    public static double getQuestCompletionPercentage(PlayerStatsWrapper player) {
        int completed = getPlayerQuestCompletions(player);
        int total = getTotalQuestCount();

        if (total == 0) {
            return 0.0;
        }

        return (double) completed / total * 100.0;
    }
}
