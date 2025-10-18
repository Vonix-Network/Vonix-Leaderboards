package com.leclowndu93150.leaderboards.client;

import com.leclowndu93150.leaderboards.network.RequestLeaderboardListPacket;
import net.neoforged.neoforge.network.PacketDistributor;

public class LeaderboardsClient {
    public static void openLeaderboardsList() {
        PacketDistributor.sendToServer(new RequestLeaderboardListPacket());
    }
}
