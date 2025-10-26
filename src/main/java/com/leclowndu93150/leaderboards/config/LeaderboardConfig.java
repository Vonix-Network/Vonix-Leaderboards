package com.leclowndu93150.leaderboards.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class LeaderboardConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    // API Settings
    public static final ModConfigSpec.BooleanValue ENABLE_API;
    public static final ModConfigSpec.IntValue API_PORT;
    public static final ModConfigSpec.BooleanValue API_CORS_ENABLED;
    public static final ModConfigSpec.ConfigValue<String> API_ALLOWED_ORIGINS;

    static {
        BUILDER.push("API Settings");
        
        ENABLE_API = BUILDER
                .comment("Enable the HTTP API server for external access to leaderboard data")
                .define("enableApi", true);
        
        API_PORT = BUILDER
                .comment("Port for the API server to listen on")
                .defineInRange("apiPort", 8080, 1024, 65535);
        
        API_CORS_ENABLED = BUILDER
                .comment("Enable CORS (Cross-Origin Resource Sharing) headers")
                .define("corsEnabled", true);
        
        API_ALLOWED_ORIGINS = BUILDER
                .comment("Allowed origins for CORS. Use '*' for all origins or specify domains separated by commas")
                .define("allowedOrigins", "*");
        
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
