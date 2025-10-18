package com.leclowndu93150.leaderboards.network;

import com.leclowndu93150.leaderboards.data.Leaderboard;
import com.leclowndu93150.leaderboards.data.LeaderboardValue;
import com.leclowndu93150.leaderboards.gui.LeaderboardScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;

import static com.leclowndu93150.leaderboards.Leaderboards.MODID;

public record LeaderboardResponsePacket(Component title, List<LeaderboardValue> values) implements CustomPacketPayload {
    public static final Type<LeaderboardResponsePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(MODID, "leaderboard_response"));
    public static final StreamCodec<RegistryFriendlyByteBuf, LeaderboardResponsePacket> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public LeaderboardResponsePacket decode(RegistryFriendlyByteBuf buf) {
            Component title = ComponentSerialization.STREAM_CODEC.decode(buf);
            int size = buf.readVarInt();
            List<LeaderboardValue> values = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                LeaderboardValue value = new LeaderboardValue();
                value.username = buf.readUtf();
                value.value = ComponentSerialization.STREAM_CODEC.decode(buf);
                value.color = ChatFormatting.getById(buf.readByte());
                values.add(value);
            }
            return new LeaderboardResponsePacket(title, values);
        }

        @Override
        public void encode(RegistryFriendlyByteBuf buf, LeaderboardResponsePacket packet) {
            ComponentSerialization.STREAM_CODEC.encode(buf, packet.title);
            buf.writeVarInt(packet.values.size());
            for (LeaderboardValue value : packet.values) {
                buf.writeUtf(value.username);
                ComponentSerialization.STREAM_CODEC.encode(buf, value.value);
                buf.writeByte(value.color.getId());
            }
        }
    };

    public LeaderboardResponsePacket(ServerPlayer requestingPlayer, Leaderboard leaderboard) {
        this(leaderboard.getTitle(), createValues(requestingPlayer, leaderboard));
    }

    private static List<LeaderboardValue> createValues(ServerPlayer requestingPlayer, Leaderboard leaderboard) {
        List<LeaderboardValue> values = new ArrayList<>();
        List<ServerPlayer> players = new ArrayList<>(requestingPlayer.server.getPlayerList().getPlayers());
        players.sort(leaderboard.getComparator());

        for (int i = 0; i < players.size(); i++) {
            ServerPlayer player = players.get(i);
            LeaderboardValue value = new LeaderboardValue();
            value.username = player.getGameProfile().getName();
            value.value = leaderboard.createValue(player);

            if (player == requestingPlayer) {
                value.color = ChatFormatting.DARK_GREEN;
            } else if (!leaderboard.hasValidValue(player)) {
                value.color = ChatFormatting.DARK_GRAY;
            } else if (i < 3) {
                value.color = ChatFormatting.GOLD;
            } else {
                value.color = ChatFormatting.RESET;
            }

            values.add(value);
        }

        return values;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(LeaderboardResponsePacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft.getInstance().execute(() -> {
                new LeaderboardScreen(packet.title, packet.values).openGui();
            });
        });
    }
}
