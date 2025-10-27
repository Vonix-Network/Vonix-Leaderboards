package com.leclowndu93150.leaderboards.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class LeaderboardConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.BooleanValue ENABLE_API;
    public static final ModConfigSpec.IntValue API_PORT;
    public static final ModConfigSpec.ConfigValue<String> API_KEY;

    static {
        BUILDER.push("API Settings");
        
        ENABLE_API = BUILDER
                .comment("Enable the HTTP API server for external access to leaderboard data")
                .define("enableApi", true);
        
        API_PORT = BUILDER
                .comment("Port for the API server to listen on")
                .defineInRange("apiPort", 8080, 1024, 65535);
        
        API_KEY = BUILDER
                .comment("API key for authentication. Leave empty to disable authentication (not recommended for public servers).",
                         "Clients must send this key in the X-API-Key header.")
                .define("apiKey", "");
        
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
