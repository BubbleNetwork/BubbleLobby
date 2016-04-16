package com.thebubblenetwork.bubblelobby.menus.reward;

import com.thebubblenetwork.api.framework.player.BukkitBubblePlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * The Bubble Network 2016
 * BubbleLobby
 * 16/04/2016 {16:27}
 * Created April 2016
 */
public class RewardItem {
    private String name, blankname, denymsg, description[];
    private long time;

    public RewardItem(String name,String blankname, long time,String denymsg, String[] description) {
        this.name = name;
        this.blankname = blankname;
        this.denymsg = denymsg;
        this.time = time;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getBlankname() {
        return blankname;
    }

    public String getDenymsg() {
        return denymsg;
    }

    public long getTime() {
        return time;
    }

    public String[] getDescription() {
        return description;
    }

    public void use(BukkitBubblePlayer player){
        player.useReward(getBlankname());
    }

    public boolean hasPermission(BukkitBubblePlayer player){
        return player.hasReward(getBlankname());
    }

    public boolean canUseReward(BukkitBubblePlayer player){
        return player.canUseReward(getBlankname(), getTime());
    }

    public void giveReward(BukkitBubblePlayer bubblePlayer, Player player){
        player.sendMessage(ChatColor.BLUE + "You redeemed your " + ChatColor.AQUA + getName() + " Reward");
    }
}
