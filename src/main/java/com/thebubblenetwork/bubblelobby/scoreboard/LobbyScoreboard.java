package com.thebubblenetwork.bubblelobby.scoreboard;

import com.google.common.collect.ImmutableMap;
import com.thebubblenetwork.api.framework.BubbleNetwork;
import com.thebubblenetwork.api.framework.player.BukkitBubblePlayer;
import com.thebubblenetwork.api.framework.util.mc.chat.ChatColorAppend;
import com.thebubblenetwork.api.framework.util.mc.scoreboard.api.BoardPreset;
import com.thebubblenetwork.api.framework.util.mc.scoreboard.api.BubbleBoardAPI;
import com.thebubblenetwork.api.framework.util.mc.scoreboard.board.SingleBubbleBoard;
import com.thebubblenetwork.api.framework.util.mc.scoreboard.util.BoardModuleBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Team;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class LobbyScoreboard extends SingleBubbleBoard {
    public static final String TITLE = ChatColor.BLUE + "[" + ChatColor.AQUA + ChatColor.BOLD.toString() + "BubbleNetwork" + ChatColor.BLUE + "]";
    public static BoardPreset PRESET = new BoardPreset("Lobby", new BoardModuleBuilder("Nickname", 12).withDisplay(ChatColor.BLUE + ChatColor.BOLD.toString() + "You").build(), new BoardModuleBuilder("NicknameValue", 11).withRandomDisplay().build(), new BoardModuleBuilder("Spacer1", 10).withRandomDisplay().build(), new BoardModuleBuilder("Rank", 9).withDisplay(ChatColor.BLUE + ChatColor.BOLD.toString() + "Rank").build(), new BoardModuleBuilder("RankValue", 8).withRandomDisplay().build(), new BoardModuleBuilder("Spacer2", 7).withRandomDisplay().build(), new BoardModuleBuilder("Token", 6).withDisplay(ChatColor.BLUE + ChatColor.BOLD.toString() + "Tokens").build(), new BoardModuleBuilder("TokenValue", 5).withRandomDisplay().build(), new BoardModuleBuilder("Spacer4", 4).withRandomDisplay().build(), new BoardModuleBuilder("Lobby", 3).withDisplay(ChatColor.BLUE + ChatColor.BOLD.toString() + "Server").build(), new BoardModuleBuilder("LobbyValue", 2).withRandomDisplay().build(), new BoardModuleBuilder("Spacer5", 1).withRandomDisplay().build(), new BoardModuleBuilder("SiteValue", 0).withDisplay("thebubblenetwork").build()) {
        public void onEnable(BubbleBoardAPI bubbleBoardAPI) {
            SingleBubbleBoard board = (SingleBubbleBoard) bubbleBoardAPI;
            Player p = Bukkit.getPlayer(board.getName());
            BukkitBubblePlayer player = BukkitBubblePlayer.getObject(p.getUniqueId());
            board.getScore(this, getModule("NicknameValue")).getTeam().setSuffix(ChatColorAppend.wipe(player.getNickName()));
            board.getScore(this, getModule("RankValue")).getTeam().setSuffix(player.getRank().isDefault() ? "None " : player.getRank().getName());
            board.getScore(this, getModule("TokenValue")).getTeam().setSuffix(String.valueOf(player.getTokens()));
            board.getScore(this, getModule("LobbyValue")).getTeam().setSuffix(BubbleNetwork.getInstance().getType().getName() + "-" + String.valueOf(BubbleNetwork.getInstance().getId()));
            Team t = board.getScore(this, getModule("SiteValue")).getTeam();
            t.setPrefix(ChatColor.GRAY + "play.");
            t.setSuffix(".com");
        }
    };

    protected static ConcurrentMap<UUID, LobbyScoreboard> map = new ConcurrentHashMap<>();

    public static LobbyScoreboard getBoard(UUID u) {
        return map.get(u);
    }

    public static LobbyScoreboard removeBoard(UUID u) {
        return map.remove(u);
    }

    public static Collection<LobbyScoreboard> getBoards(){
        return map.values();
    }

    public static LobbyScoreboard createBoard(Player p) {
        LobbyScoreboard board = new LobbyScoreboard(p);
        map.put(p.getUniqueId(), board);
        return board;
    }

    private LobbyScoreboard(Player p) {
        super(p, new ImmutableMap.Builder<DisplaySlot, String>().put(DisplaySlot.SIDEBAR, TITLE).build());
        enable(PRESET);
        p.setScoreboard(getObject().getBoard());
    }
}
