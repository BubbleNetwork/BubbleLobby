package com.thebubblenetwork.bubblelobby.menus.lobbyselector;

import com.thebubblenetwork.api.framework.util.mc.items.ItemStackBuilder;
import com.thebubblenetwork.api.global.bubblepackets.messaging.messages.response.ServerListResponse;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The Bubble Network 2016
 * BubbleLobby
 * 21/02/2016 {15:38}
 * Created February 2016
 */
public class LobbyItem {
    private ServerListResponse.EncapsulatedServer server;

    public LobbyItem(ServerListResponse.EncapsulatedServer server) {
        this.server = server;
    }

    public int getOnline() {
        return server.isConnect() ? server.getPlayercount() : 0;
    }

    public boolean isOnline() {
        return server.isConnect();
    }

    public int getId() {
        return server.getId();
    }

    public ItemStack getItem() {
        ItemStackBuilder builder = new ItemStackBuilder(Material.WOOL);
        builder.withName(ChatColor.AQUA + "Lobby #" + getId());
        DyeColor color;
        List<String> lore;
        if (isOnline()) {
            color = DyeColor.GREEN;
            lore = Arrays.asList(ChatColor.BLUE + "There are currently " + ChatColor.AQUA + String.valueOf(getOnline()) + ChatColor.BLUE + " people online in this hub", ChatColor.GREEN + "Click to join");
        } else {
            color = DyeColor.RED;
            lore = Collections.singletonList(ChatColor.RED + "This hub is currently offline");
        }
        builder.withColor(color);
        builder.withLore(lore);
        return builder.build();
    }
}
