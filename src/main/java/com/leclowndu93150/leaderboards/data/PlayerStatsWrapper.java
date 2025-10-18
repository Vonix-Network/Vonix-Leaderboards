package com.leclowndu93150.leaderboards.data;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerStatsCounter;

import java.util.UUID;

public class PlayerStatsWrapper {
    public final UUID uuid;
    public final String name;
    public final ServerStatsCounter stats;
    public final MinecraftServer server;
    private final ServerPlayer onlinePlayer;

    public PlayerStatsWrapper(ServerPlayer player) {
        this.uuid = player.getUUID();
        this.name = player.getGameProfile().getName();
        this.stats = player.getStats();
        this.server = player.server;
        this.onlinePlayer = player;
    }

    public PlayerStatsWrapper(UUID uuid, GameProfile profile, ServerStatsCounter stats, MinecraftServer server) {
        this.uuid = uuid;
        this.name = profile.getName();
        this.stats = stats;
        this.server = server;
        this.onlinePlayer = null;
    }

    public UUID getUUID() {
        return uuid;
    }

    public GameProfile getGameProfile() {
        if (onlinePlayer != null) {
            return onlinePlayer.getGameProfile();
        }
        return new GameProfile(uuid, name);
    }

    public ServerStatsCounter getStats() {
        return stats;
    }

    public boolean isOnline() {
        return onlinePlayer != null;
    }

    public ServerPlayer getOnlinePlayer() {
        return onlinePlayer;
    }
}
