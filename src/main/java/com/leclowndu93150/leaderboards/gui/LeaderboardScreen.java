package com.leclowndu93150.leaderboards.gui;

import com.leclowndu93150.leaderboards.data.LeaderboardValue;
import dev.ftb.mods.ftblibrary.ui.*;
import dev.ftb.mods.ftblibrary.ui.misc.ButtonListBaseScreen;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;

import java.util.List;

public class LeaderboardScreen extends ButtonListBaseScreen {
    private final List<LeaderboardValue> leaderboard;
    private int rankSize;
    private int usernameSize;
    private int valueSize;
    private int totalWidth;

    private class LeaderboardEntry extends Widget {
        private final LeaderboardValue value;
        private final String rank;

        public LeaderboardEntry(Panel panel, LeaderboardValue v) {
            super(panel);
            value = v;
            rank = value.color + "#" + String.format("%0" + String.valueOf(leaderboard.size()).length() + "d", v.rank);

            Theme theme = getGui().getTheme();
            rankSize = Math.max(rankSize, theme.getStringWidth(rank) + 4);
            usernameSize = Math.max(usernameSize, theme.getStringWidth(v.username) + 8);
            valueSize = Math.max(valueSize, theme.getStringWidth(value.value.getString()) + 8);

            setWidth(rankSize + usernameSize + valueSize);
            setHeight(14);
        }

        @Override
        public void addMouseOverText(TooltipList list) {
        }

        @Override
        public void draw(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
            WidgetType type = value.color == ChatFormatting.DARK_GRAY ? WidgetType.DISABLED : WidgetType.mouseOver(isMouseOver());
            int textY = y + (h - theme.getFontHeight() + 1) / 2;
            
            theme.drawButton(graphics, x, y, rankSize, h, type);
            theme.drawString(graphics, rank, x + 2, textY, Theme.SHADOW);

            theme.drawButton(graphics, x + rankSize, y, usernameSize, h, type);
            theme.drawString(graphics, value.color + value.username, x + 4 + rankSize, textY, Theme.SHADOW);

            int remainingWidth = w - rankSize - usernameSize;
            theme.drawButton(graphics, x + rankSize + usernameSize, y, remainingWidth, h, type);
            String formattedText = value.value.getString();
            theme.drawString(graphics, value.color + formattedText, x + rankSize + usernameSize + remainingWidth - theme.getStringWidth(formattedText) - 4, textY, Theme.SHADOW);
        }
    }

    public LeaderboardScreen(Component title, List<LeaderboardValue> leaderboard) {
        setTitle(Component.literal(I18n.get("sidebar_button.leaderboards.leaderboards") + " > " + title.getString()));
        setHasSearchBox(true);
        this.leaderboard = leaderboard;
    }
    
    @Override
    public void alignWidgets() {
        if (totalWidth > 0) {
            int padding = 40;
            int scrollbarWidth = 16;
            int gutterSize = 6;
            int maxWidth = Math.min(totalWidth + padding, 400);
            int height = Math.min(leaderboard.size() * 15 + 40, 300);
            
            setWidth(maxWidth + scrollbarWidth + gutterSize * 3);
            setHeight(height + (hasSearchBox() ? 30 : 0));
        }
        super.alignWidgets();
    }
    
    private boolean hasSearchBox() {
        return true;
    }

    @Override
    public void addButtons(Panel panel) {
        int i = 0;
        rankSize = 0;
        usernameSize = 0;
        valueSize = 0;

        for (LeaderboardValue value : leaderboard) {
            value.rank = ++i;
            panel.add(new LeaderboardEntry(panel, value));
        }
        
        totalWidth = rankSize + usernameSize + valueSize;
        setBorder(3, 1, 1);
    }

    @Override
    public String getFilterText(Widget widget) {
        return ((LeaderboardEntry) widget).value.username.toLowerCase();
    }
}
