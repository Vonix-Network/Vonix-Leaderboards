package com.leclowndu93150.leaderboards.gui;

import com.leclowndu93150.leaderboards.network.RequestLeaderboardPacket;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.SimpleTextButton;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.ui.misc.ButtonListBaseScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Map;

public class VanillaStatsListScreen extends ButtonListBaseScreen {
    private final Map<ResourceLocation, Component> vanillaStats;

    public VanillaStatsListScreen(Map<ResourceLocation, Component> vanillaStats) {
        this.vanillaStats = vanillaStats;
        setTitle(Component.translatable("leaderboard.leaderboards.vanilla_stats"));
        setHasSearchBox(true);
    }

    @Override
    public void addButtons(Panel panel) {
        for (Map.Entry<ResourceLocation, Component> entry : vanillaStats.entrySet()) {
            panel.add(new SimpleTextButton(panel, entry.getValue(), Icon.empty()) {
                @Override
                public void onClicked(MouseButton button) {
                    playClickSound();
                    PacketDistributor.sendToServer(new RequestLeaderboardPacket(entry.getKey()));
                }
            });
        }
    }
}
