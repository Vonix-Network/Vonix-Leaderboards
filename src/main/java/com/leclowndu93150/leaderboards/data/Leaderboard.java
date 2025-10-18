package com.leclowndu93150.leaderboards.data;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;

public class Leaderboard {
    public final ResourceLocation id;
    private final Component title;
    private final Function<ServerPlayer, Component> playerToValue;
    private final Comparator<ServerPlayer> comparator;
    private final Predicate<ServerPlayer> validValue;

    public Leaderboard(ResourceLocation id, Component title, Function<ServerPlayer, Component> valueFunction, Comparator<ServerPlayer> comparator, Predicate<ServerPlayer> validValue) {
        this.id = id;
        this.title = title;
        this.playerToValue = valueFunction;
        this.comparator = comparator.thenComparing((p1, p2) -> p1.getGameProfile().getName().compareToIgnoreCase(p2.getGameProfile().getName()));
        this.validValue = validValue;
    }

    public Component getTitle() {
        return title;
    }

    public Comparator<ServerPlayer> getComparator() {
        return comparator;
    }

    public Component createValue(ServerPlayer player) {
        return playerToValue.apply(player);
    }

    public boolean hasValidValue(ServerPlayer player) {
        return validValue.test(player);
    }

    public static class FromStat extends Leaderboard {
        public static final IntFunction<Component> DEFAULT = value -> Component.literal(value <= 0 ? "0" : Integer.toString(value));
        public static final IntFunction<Component> TIME = value -> {
            if (value <= 0) return Component.literal("0");
            int hours = (int) (value / 72000D + 0.5D);
            int ticks = value % 24000;
            int seconds = ticks / 20;
            int minutes = seconds / 60;
            seconds = seconds % 60;
            int displayHours = minutes / 60;
            minutes = minutes % 60;
            return Component.literal(String.format("[%dh] %02d:%02d:%02d", hours, displayHours, minutes, seconds));
        };
        public static final IntFunction<Component> DISTANCE = value -> {
            if (value <= 0) return Component.literal("0");
            double blocks = value / 100.0;
            if (blocks >= 1000) {
                return Component.literal(String.format("%.2f km", blocks / 1000.0));
            }
            return Component.literal(String.format("%.1f m", blocks));
        };

        public FromStat(ResourceLocation id, Component title, Stat<?> stat, boolean ascending, IntFunction<Component> valueFormatter) {
            super(id, title,
                    player -> valueFormatter.apply(player.getStats().getValue(stat)),
                    (p1, p2) -> {
                        int result = Integer.compare(p1.getStats().getValue(stat), p2.getStats().getValue(stat));
                        return ascending ? result : -result;
                    },
                    player -> player.getStats().getValue(stat) > 0
            );
        }

        public FromStat(ResourceLocation id, Component title, Stat<?> stat, boolean ascending) {
            this(id, title, stat, ascending, DEFAULT);
        }
    }
}
