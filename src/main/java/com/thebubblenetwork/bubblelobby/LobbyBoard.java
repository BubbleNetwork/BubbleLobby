package com.thebubblenetwork.bubblelobby;

import com.google.common.collect.ImmutableMap;
import com.thebubblenetwork.api.framework.util.mc.scoreboard.BoardPreset;
import com.thebubblenetwork.api.framework.util.mc.scoreboard.BubbleBoardAPI;
import com.thebubblenetwork.api.framework.util.mc.scoreboard.SingleBubbleBoard;
import com.thebubblenetwork.api.framework.util.mc.scoreboard.util.BoardModuleBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LobbyBoard extends SingleBubbleBoard{
    public static final String
    TITLE1 = ChatColor.AQUA + ChatColor.BOLD.toString() + "BUBBLE",
    TITLE2 = ChatColor.DARK_AQUA + ChatColor.BOLD.toString() + "BUBBLE";

    private static Map<UUID,LobbyBoard> map = new HashMap<UUID, LobbyBoard>();

    public static BoardPreset PRESET = new BoardPreset("Lobby",
            new BoardModuleBuilder("Spacer1",16).withRandomDisplay().build(),
            new BoardModuleBuilder("You",15).withDisplay(ChatColor.BLUE + "You").build(),
            new BoardModuleBuilder("Nickname",14).withDisplay(ChatColor.GRAY + " Name: " + ChatColor.RESET).build(),
            new BoardModuleBuilder("Rank",13).withDisplay(ChatColor.GRAY + " Rank: " + ChatColor.RESET).build()) {
        public void onEnable(BubbleBoardAPI bubbleBoardAPI) {

        }
    };

    public static LobbyBoard getBoard(UUID u){
        return map.get(u);
    }

    public static LobbyBoard removeBoard(UUID u){
        return map.remove(u);
    }

    public static LobbyBoard createBoard(Player p){
        LobbyBoard board = new LobbyBoard(p);
        return board;
    }

    public LobbyBoard(Player p) {
        super(p, new ImmutableMap.Builder<DisplaySlot,String>().put(DisplaySlot.SIDEBAR,TITLE1).build());
    }
}
