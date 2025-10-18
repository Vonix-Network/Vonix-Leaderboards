package com.leclowndu93150.leaderboards.network;

import com.leclowndu93150.leaderboards.LeaderboardRegistry;
import com.leclowndu93150.leaderboards.VanillaStatsRegistry;
import com.leclowndu93150.leaderboards.data.Leaderboard;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.leclowndu93150.leaderboards.Leaderboards.MODID;

public record RequestLeaderboardPacket(ResourceLocation id) implements CustomPacketPayload {
    public static final Type<RequestLeaderboardPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "request_leaderboard"));
    public static final StreamCodec<RegistryFriendlyByteBuf, RequestLeaderboardPacket> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public RequestLeaderboardPacket decode(RegistryFriendlyByteBuf buf) {
            return new RequestLeaderboardPacket(buf.readResourceLocation());
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, RequestLeaderboardPacket packet) {
            buf.writeResourceLocation(packet.id);
        }
    };

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(RequestLeaderboardPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer serverPlayer) {
                Leaderboard leaderboard = LeaderboardRegistry.LEADERBOARDS.get(packet.id);
                if (leaderboard == null) {
                    leaderboard = VanillaStatsRegistry.VANILLA_STATS.get(packet.id);
                }
                if (leaderboard != null) {
                    PacketDistributor.sendToPlayer(serverPlayer, new LeaderboardResponsePacket(serverPlayer, leaderboard));
                }
            }
        });
    }
}
