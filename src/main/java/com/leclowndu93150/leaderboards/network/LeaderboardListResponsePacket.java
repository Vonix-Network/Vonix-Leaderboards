package com.leclowndu93150.leaderboards.network;

import com.leclowndu93150.leaderboards.data.Leaderboard;
import com.leclowndu93150.leaderboards.gui.LeaderboardListScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.leclowndu93150.leaderboards.Leaderboards.MODID;

public record LeaderboardListResponsePacket(Map<ResourceLocation, Component> leaderboards) implements CustomPacketPayload {
    public static final Type<LeaderboardListResponsePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "leaderboard_list_response"));
    public static final StreamCodec<RegistryFriendlyByteBuf, LeaderboardListResponsePacket> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public LeaderboardListResponsePacket decode(RegistryFriendlyByteBuf buf) {
            int size = buf.readVarInt();
            Map<ResourceLocation, Component> map = new LinkedHashMap<>();
            for (int i = 0; i < size; i++) {
                ResourceLocation id = buf.readResourceLocation();
                Component title = ComponentSerialization.STREAM_CODEC.decode(buf);
                map.put(id, title);
            }
            return new LeaderboardListResponsePacket(map);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, LeaderboardListResponsePacket packet) {
            buf.writeVarInt(packet.leaderboards.size());
            for (Map.Entry<ResourceLocation, Component> entry : packet.leaderboards.entrySet()) {
                buf.writeResourceLocation(entry.getKey());
                ComponentSerialization.STREAM_CODEC.encode(buf, entry.getValue());
            }
        }
    };

    public static LeaderboardListResponsePacket fromLeaderboards(Map<ResourceLocation, Leaderboard> leaderboards) {
        Map<ResourceLocation, Component> map = new LinkedHashMap<>();
        for (Map.Entry<ResourceLocation, Leaderboard> entry : leaderboards.entrySet()) {
            map.put(entry.getKey(), entry.getValue().getTitle());
        }
        return new LeaderboardListResponsePacket(map);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(LeaderboardListResponsePacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft.getInstance().execute(() -> {
                new LeaderboardListScreen(packet.leaderboards).openGui();
            });
        });
    }
}
