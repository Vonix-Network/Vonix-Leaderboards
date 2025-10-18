package com.leclowndu93150.leaderboards.client;

import com.leclowndu93150.leaderboards.Leaderboards;
import dev.architectury.event.EventResult;
import dev.ftb.mods.ftblibrary.ui.CustomClickEvent;

public class LeaderboardsClientEvents {
    public static void init() {
        CustomClickEvent.EVENT.register(event -> {
            if (event.id().getNamespace().equals(Leaderboards.MODID) && "open_leaderboards".equals(event.id().getPath())) {
                LeaderboardsClient.openLeaderboardsList();
                return EventResult.interruptFalse();
            }
            return EventResult.pass();
        });
    }
}
