package com.leclowndu93150.leaderboards.network;

import com.leclowndu93150.leaderboards.LeaderboardRegistry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.leclowndu93150.leaderboards.Leaderboards.MODID;

public record RequestLeaderboardListPacket() implements CustomPacketPayload {
    public static final Type<RequestLeaderboardListPacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "request_leaderboard_list"));
    public static final StreamCodec<RegistryFriendlyByteBuf, RequestLeaderboardListPacket> STREAM_CODEC = StreamCodec.unit(new RequestLeaderboardListPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(RequestLeaderboardListPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer serverPlayer) {
                PacketDistributor.sendToPlayer(serverPlayer, LeaderboardListResponsePacket.fromLeaderboards(LeaderboardRegistry.LEADERBOARDS));
            }
        });
    }
}
