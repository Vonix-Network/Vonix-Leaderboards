package com.leclowndu93150.leaderboards.gui;

import com.leclowndu93150.leaderboards.VanillaStatsRegistry;
import com.leclowndu93150.leaderboards.network.RequestLeaderboardPacket;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.SimpleTextButton;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.ui.misc.ButtonListBaseScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.LinkedHashMap;
import java.util.Map;

public class LeaderboardListScreen extends ButtonListBaseScreen {
    private final Map<ResourceLocation, Component> leaderboards;

    public LeaderboardListScreen(Map<ResourceLocation, Component> leaderboards) {
        this.leaderboards = leaderboards;
        setTitle(Component.translatable("sidebar_button.leaderboards.leaderboards"));
    }

    @Override
    public void addButtons(Panel panel) {
        for (Map.Entry<ResourceLocation, Component> entry : leaderboards.entrySet()) {
            panel.add(new SimpleTextButton(panel, entry.getValue(), Icon.empty()) {
                @Override
                public void onClicked(MouseButton button) {
                    playClickSound();
                    PacketDistributor.sendToServer(new RequestLeaderboardPacket(entry.getKey()));
                }
            });
        }
        
        if (!VanillaStatsRegistry.VANILLA_STATS.isEmpty()) {
            panel.add(new SimpleTextButton(panel, Component.translatable("leaderboard.leaderboards.vanilla_stats"), Icon.empty()) {
                @Override
                public void onClicked(MouseButton button) {
                    playClickSound();
                    Map<ResourceLocation, Component> vanillaStatsMap = new LinkedHashMap<>();
                    VanillaStatsRegistry.VANILLA_STATS.entrySet().stream()
                        .sorted((e1, e2) -> e1.getValue().getTitle().getString().compareToIgnoreCase(e2.getValue().getTitle().getString()))
                        .forEach(entry -> vanillaStatsMap.put(entry.getKey(), entry.getValue().getTitle()));
                    new VanillaStatsListScreen(vanillaStatsMap).openGui();
                }
            });
        }
    }
}
