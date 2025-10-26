package com.leclowndu93150.leaderboards.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leclowndu93150.leaderboards.LeaderboardRegistry;
import com.leclowndu93150.leaderboards.data.Leaderboard;
import com.leclowndu93150.leaderboards.data.PlayerStatsWrapper;
import com.mojang.authlib.GameProfile;
import com.mojang.logging.LogUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.world.level.storage.LevelResource;

import java.io.File;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class LeaderboardApiServer {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static LeaderboardApiServer instance;
    
    private final HttpServer server;
    private final MinecraftServer mcServer;
    private final int port;
    private final boolean corsEnabled;
    private final String allowedOrigins;
    
    private LeaderboardApiServer(MinecraftServer mcServer, int port, boolean corsEnabled, String allowedOrigins) throws IOException {
        this.mcServer = mcServer;
        this.port = port;
        this.corsEnabled = corsEnabled;
        this.allowedOrigins = allowedOrigins;
        this.server = HttpServer.create(new InetSocketAddress(port), 0);
        
        // Set up endpoints
        server.createContext("/api/leaderboards", this::handleListLeaderboards);
        server.createContext("/api/leaderboard/", this::handleGetLeaderboard);
        server.createContext("/api/player/", this::handleGetPlayer);
        
        server.setExecutor(Executors.newFixedThreadPool(4));
        LOGGER.info("Leaderboard API Server initialized on port {}", port);
    }
    
    public static void start(MinecraftServer mcServer, int port, boolean corsEnabled, String allowedOrigins) {
        if (instance != null) {
            LOGGER.warn("API Server already running, stopping previous instance");
            stop();
        }
        
        try {
            instance = new LeaderboardApiServer(mcServer, port, corsEnabled, allowedOrigins);
            instance.server.start();
            LOGGER.info("Leaderboard API Server started successfully on port {} (CORS: {})", port, corsEnabled ? "enabled" : "disabled");
        } catch (IOException e) {
            LOGGER.error("Failed to start Leaderboard API Server", e);
        }
    }
    
    public static void stop() {
        if (instance != null) {
            instance.server.stop(0);
            instance = null;
            LOGGER.info("Leaderboard API Server stopped");
        }
    }
    
    private void handleListLeaderboards(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            sendResponse(exchange, 405, "{\"error\": \"Method not allowed\"}");
            return;
        }
        
        List<LeaderboardInfo> leaderboards = LeaderboardRegistry.LEADERBOARDS.entrySet().stream()
                .map(entry -> new LeaderboardInfo(
                        entry.getKey().toString(),
                        entry.getValue().getTitle().getString()
                ))
                .collect(Collectors.toList());
        
        String json = GSON.toJson(Map.of("leaderboards", leaderboards));
        sendResponse(exchange, 200, json);
    }
    
    private void handleGetPlayer(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            sendResponse(exchange, 405, "{\"error\": \"Method not allowed\"}");
            return;
        }
        
        String path = exchange.getRequestURI().getPath();
        String playerName = path.substring("/api/player/".length());
        
        if (playerName.isEmpty()) {
            sendResponse(exchange, 400, "{\"error\": \"Player name required\"}");
            return;
        }
        
        // Get all players
        List<PlayerStatsWrapper> allPlayers = getAllPlayers();
        
        // Find the specific player
        PlayerStatsWrapper targetPlayer = allPlayers.stream()
                .filter(p -> p.name.equalsIgnoreCase(playerName))
                .findFirst()
                .orElse(null);
        
        if (targetPlayer == null) {
            sendResponse(exchange, 404, "{\"error\": \"Player not found\"}");
            return;
        }
        
        // Build player stats across all leaderboards
        List<PlayerLeaderboardEntry> leaderboardStats = new ArrayList<>();
        
        for (Map.Entry<ResourceLocation, Leaderboard> entry : LeaderboardRegistry.LEADERBOARDS.entrySet()) {
            Leaderboard leaderboard = entry.getValue();
            
            // Check if player has valid data for this leaderboard
            if (!leaderboard.hasValidValue(targetPlayer)) {
                continue;
            }
            
            // Get all players for this leaderboard and sort
            List<PlayerStatsWrapper> validPlayers = allPlayers.stream()
                    .filter(leaderboard::hasValidValue)
                    .sorted(leaderboard.getComparator())
                    .collect(Collectors.toList());
            
            // Find player's rank
            int rank = -1;
            for (int i = 0; i < validPlayers.size(); i++) {
                if (validPlayers.get(i).uuid.equals(targetPlayer.uuid)) {
                    rank = i + 1;
                    break;
                }
            }
            
            if (rank > 0) {
                leaderboardStats.add(new PlayerLeaderboardEntry(
                        entry.getKey().toString(),
                        leaderboard.getTitle().getString(),
                        rank,
                        validPlayers.size(),
                        leaderboard.createValue(targetPlayer).getString()
                ));
            }
        }
        
        PlayerStatsResponse response = new PlayerStatsResponse(
                targetPlayer.name,
                targetPlayer.uuid.toString(),
                targetPlayer.isOnline(),
                leaderboardStats
        );
        
        String json = GSON.toJson(response);
        sendResponse(exchange, 200, json);
    }
    
    private void handleGetLeaderboard(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            sendResponse(exchange, 405, "{\"error\": \"Method not allowed\"}");
            return;
        }
        
        String path = exchange.getRequestURI().getPath();
        String leaderboardId = path.substring("/api/leaderboard/".length());
        
        if (leaderboardId.isEmpty()) {
            sendResponse(exchange, 400, "{\"error\": \"Leaderboard ID required\"}");
            return;
        }
        
        ResourceLocation id;
        try {
            id = ResourceLocation.parse(leaderboardId);
        } catch (Exception e) {
            sendResponse(exchange, 400, "{\"error\": \"Invalid leaderboard ID format\"}");
            return;
        }
        
        Leaderboard leaderboard = LeaderboardRegistry.LEADERBOARDS.get(id);
        if (leaderboard == null) {
            sendResponse(exchange, 404, "{\"error\": \"Leaderboard not found\"}");
            return;
        }
        
        // Get player data
        List<PlayerStatsWrapper> players = getAllPlayers();
        
        // Filter and sort players
        List<PlayerStatsWrapper> validPlayers = players.stream()
                .filter(leaderboard::hasValidValue)
                .sorted(leaderboard.getComparator())
                .collect(Collectors.toList());
        
        // Build response
        List<LeaderboardEntry> entries = new ArrayList<>();
        for (int i = 0; i < validPlayers.size(); i++) {
            PlayerStatsWrapper player = validPlayers.get(i);
            entries.add(new LeaderboardEntry(
                    i + 1,
                    player.name,
                    player.uuid.toString(),
                    leaderboard.createValue(player).getString()
            ));
        }
        
        LeaderboardResponse response = new LeaderboardResponse(
                leaderboardId,
                leaderboard.getTitle().getString(),
                entries
        );
        
        String json = GSON.toJson(response);
        sendResponse(exchange, 200, json);
    }
    
    private List<PlayerStatsWrapper> getAllPlayers() {
        List<PlayerStatsWrapper> players = new ArrayList<>();
        GameProfileCache profileCache = mcServer.getProfileCache();
        
        if (profileCache == null) {
            return players;
        }
        
        // Get all stored player stats
        File statsDir = mcServer.getWorldPath(LevelResource.PLAYER_STATS_DIR).toFile();
        String[] statsFiles = statsDir.list();
        
        if (statsFiles != null) {
            for (String fileName : statsFiles) {
                if (fileName.endsWith(".json")) {
                    try {
                        UUID uuid = UUID.fromString(fileName.replace(".json", ""));
                        Optional<GameProfile> profile = profileCache.get(uuid);
                        
                        if (profile.isPresent()) {
                            File statsFile = new File(statsDir, fileName);
                            ServerStatsCounter stats = new ServerStatsCounter(mcServer, statsFile);
                            
                            players.add(new PlayerStatsWrapper(uuid, profile.get(), stats, mcServer));
                        }
                    } catch (Exception e) {
                        // Skip invalid files
                    }
                }
            }
        }
        
        return players;
    }
    
    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        // Set content type
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        
        // Add CORS headers if enabled
        if (corsEnabled) {
            exchange.getResponseHeaders().set("Access-Control-Allow-Origin", allowedOrigins);
            exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, OPTIONS");
            exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
        }
        
        byte[] bytes = response.getBytes();
        exchange.sendResponseHeaders(statusCode, bytes.length);
        
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
    
    // Inner classes for JSON serialization
    private static class LeaderboardInfo {
        private final String id;
        private final String name;
        
        public LeaderboardInfo(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }
    
    private static class LeaderboardEntry {
        private final int rank;
        private final String playerName;
        private final String playerUuid;
        private final String value;
        
        public LeaderboardEntry(int rank, String playerName, String playerUuid, String value) {
            this.rank = rank;
            this.playerName = playerName;
            this.playerUuid = playerUuid;
            this.value = value;
        }
    }
    
    private static class LeaderboardResponse {
        private final String id;
        private final String name;
        private final List<LeaderboardEntry> entries;
        
        public LeaderboardResponse(String id, String name, List<LeaderboardEntry> entries) {
            this.id = id;
            this.name = name;
            this.entries = entries;
        }
    }
    
    private static class PlayerLeaderboardEntry {
        private final String leaderboardId;
        private final String leaderboardName;
        private final int rank;
        private final int totalPlayers;
        private final String value;
        
        public PlayerLeaderboardEntry(String leaderboardId, String leaderboardName, int rank, int totalPlayers, String value) {
            this.leaderboardId = leaderboardId;
            this.leaderboardName = leaderboardName;
            this.rank = rank;
            this.totalPlayers = totalPlayers;
            this.value = value;
        }
    }
    
    private static class PlayerStatsResponse {
        private final String playerName;
        private final String playerUuid;
        private final boolean online;
        private final List<PlayerLeaderboardEntry> leaderboards;
        
        public PlayerStatsResponse(String playerName, String playerUuid, boolean online, List<PlayerLeaderboardEntry> leaderboards) {
            this.playerName = playerName;
            this.playerUuid = playerUuid;
            this.online = online;
            this.leaderboards = leaderboards;
        }
    }
}
