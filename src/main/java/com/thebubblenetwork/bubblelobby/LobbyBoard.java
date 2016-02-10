package com.thebubblenetwork.bubblelobby;

import com.google.common.collect.ImmutableMap;
import com.thebubblenetwork.api.framework.BubbleNetwork;
import com.thebubblenetwork.api.framework.BukkitBubblePlayer;
import com.thebubblenetwork.api.framework.util.mc.chat.ChatColorAppend;
import com.thebubblenetwork.api.framework.util.mc.scoreboard.*;
import com.thebubblenetwork.api.framework.util.mc.scoreboard.util.BoardModuleBuilder;
import com.thebubblenetwork.api.global.player.BubblePlayer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LobbyBoard extends SingleBubbleBoard{
    public static final String
    TITLE1 = ChatColor.BLUE + "[" + ChatColor.AQUA + ChatColor.BOLD.toString() + "BubbleNetwork" + ChatColor.BLUE + "]",
    TITLE2 = ChatColor.BLUE + "[" + ChatColor.DARK_AQUA + ChatColor.BOLD.toString() + "BubbleNetwork" + ChatColor.BLUE + "]";

    protected static Map<UUID,LobbyBoard> map = new HashMap<UUID, LobbyBoard>();

    public static BoardPreset PRESET = new BoardPreset("Lobby",
            new BoardModuleBuilder("Nickname",16).withDisplay(ChatColor.BLUE + ChatColor.BOLD.toString() + "You").build(),
            new BoardModuleBuilder("NicknameValue",15).withRandomDisplay().build(),
            new BoardModuleBuilder("Spacer1",14).withRandomDisplay().build(),
            new BoardModuleBuilder("Rank",13).withDisplay(ChatColor.BLUE + ChatColor.BOLD.toString() + "Rank").build(),
            new BoardModuleBuilder("RankValue",12).withRandomDisplay().build(),
            new BoardModuleBuilder("Spacer2",11).withRandomDisplay().build(),
            new BoardModuleBuilder("Token",10).withDisplay(ChatColor.BLUE + ChatColor.BOLD.toString() + "Tokens").build(),
            new BoardModuleBuilder("TokenValue",9).withRandomDisplay().build(),
            new BoardModuleBuilder("Spacer3",8).withRandomDisplay().build(),
            new BoardModuleBuilder("Crystal",7).withDisplay(ChatColor.BLUE + ChatColor.BOLD.toString() + "Crystals").build(),
            new BoardModuleBuilder("CrystalValue",6).withRandomDisplay().build(),
            new BoardModuleBuilder("Spacer4",5).withRandomDisplay().build(),
            new BoardModuleBuilder("Hub",4).withDisplay(ChatColor.BLUE + ChatColor.BOLD.toString() + "Hub").build(),
            new BoardModuleBuilder("HubValue",3).withRandomDisplay().build(),
            new BoardModuleBuilder("Spacer5",2).withRandomDisplay().build(),
            new BoardModuleBuilder("SiteValue",1).withDisplay("thebubblenetwork").build()
    ) {
        public void onEnable(BubbleBoardAPI bubbleBoardAPI) {
            SingleBubbleBoard board = (SingleBubbleBoard)bubbleBoardAPI;
            Player p = Bukkit.getPlayer(board.getName());
            BubblePlayer<Player> player = BukkitBubblePlayer.getObject(p.getUniqueId());
            board.getScore(this,getModule("NicknameValue")).getTeam().setSuffix(ChatColorAppend.wipe(player.getNickName()));
            board.getScore(this,getModule("RankValue")).getTeam().setSuffix(player.getRank().isDefault() ? "None ": player.getRank().getName());
            board.getScore(this,getModule("TokenValue")).getTeam().setSuffix(String.valueOf(player.getTokens()));
            board.getScore(this,getModule("CrystalValue")).getTeam().setSuffix(String.valueOf(player.getCrystals()));
            board.getScore(this,getModule("HubValue")).getTeam().setSuffix(BubbleNetwork.getInstance().getType().getPrefix() + String.valueOf(BubbleNetwork.getInstance().getId()));
            Team t = board.getScore(this,getModule("SiteValue")).getTeam();
            t.setPrefix(ChatColor.GRAY + "play.");
            t.setSuffix(".com");
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
        map.put(p.getUniqueId(),board);
        return board;
    }

    private LobbyBoard(Player p) {
        super(p, new ImmutableMap.Builder<DisplaySlot,String>().put(DisplaySlot.SIDEBAR,TITLE1).build());
        enable(PRESET);
        p.setScoreboard(getObject().getBoard());
    }
}
