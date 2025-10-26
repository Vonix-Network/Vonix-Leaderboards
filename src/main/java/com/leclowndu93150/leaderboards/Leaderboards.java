package com.leclowndu93150.leaderboards;

import com.leclowndu93150.leaderboards.api.LeaderboardApiServer;
import com.leclowndu93150.leaderboards.config.LeaderboardConfig;
import com.leclowndu93150.leaderboards.data.PlayerDataTracker;
import com.leclowndu93150.leaderboards.network.*;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.slf4j.Logger;

@Mod(Leaderboards.MODID)
public class Leaderboards {
    public static final String MODID = "leaderboards";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Leaderboards(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.SERVER, LeaderboardConfig.SPEC);
        
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::registerPayloads);
        
        NeoForge.EVENT_BUS.addListener(this::onServerStarted);
        NeoForge.EVENT_BUS.addListener(this::onServerStopping);
        NeoForge.EVENT_BUS.addListener(this::onPlayerLogin);
        NeoForge.EVENT_BUS.addListener(this::onPlayerLogout);
        
        if (FMLEnvironment.dist == Dist.CLIENT) {
            com.leclowndu93150.leaderboards.client.LeaderboardsClientEvents.init();
        }
    }

    private void registerPayloads(final RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(RequestLeaderboardListPacket.TYPE, RequestLeaderboardListPacket.STREAM_CODEC, RequestLeaderboardListPacket::handle);
        registrar.playToClient(LeaderboardListResponsePacket.TYPE, LeaderboardListResponsePacket.STREAM_CODEC, LeaderboardListResponsePacket::handle);
        registrar.playToServer(RequestLeaderboardPacket.TYPE, RequestLeaderboardPacket.STREAM_CODEC, RequestLeaderboardPacket::handle);
        registrar.playToClient(LeaderboardResponsePacket.TYPE, LeaderboardResponsePacket.STREAM_CODEC, LeaderboardResponsePacket::handle);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LeaderboardRegistry.register();
        LOGGER.info("Leaderboards registered");
    }

    private void onServerStarted(ServerStartedEvent event) {
        PlayerDataTracker tracker = PlayerDataTracker.get(event.getServer().overworld());
        PlayerDataTracker.setInstance(tracker);
        
        // Start the API server if enabled
        if (LeaderboardConfig.ENABLE_API.get()) {
            int port = LeaderboardConfig.API_PORT.get();
            boolean corsEnabled = LeaderboardConfig.API_CORS_ENABLED.get();
            String allowedOrigins = LeaderboardConfig.API_ALLOWED_ORIGINS.get();
            LeaderboardApiServer.start(event.getServer(), port, corsEnabled, allowedOrigins);
        } else {
            LOGGER.info("Leaderboard API server is disabled in config");
        }
    }
    
    private void onServerStopping(ServerStoppingEvent event) {
        LeaderboardApiServer.stop();
    }

    private void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity().level().isClientSide) return;
        PlayerDataTracker tracker = PlayerDataTracker.get(event.getEntity().getServer().overworld());
        tracker.updateLastSeen(event.getEntity().getUUID(), event.getEntity().getServer().overworld().getGameTime());
    }

    private void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity().level().isClientSide) return;
        PlayerDataTracker tracker = PlayerDataTracker.get(event.getEntity().getServer().overworld());
        tracker.updateLastSeen(event.getEntity().getUUID(), event.getEntity().getServer().overworld().getGameTime());
    }
}
