package com.thebubblenetwork.bubblelobby;

import com.thebubblenetwork.api.framework.messages.Messages;
import com.thebubblenetwork.api.framework.plugin.BubblePlugin;
import com.thebubblenetwork.api.framework.util.mc.scoreboard.BubbleBoardAPI;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;

/**
 * Copyright Statement
 * ----------------------
 * Copyright (C) The Bubble Network, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Wrote by Jacob Evans <jacobevansminor@gmail.com>, 01 2016
 *
 *
 * Class information
 * ---------------------
 * Package: com.thebubblenetwork.bubblelobby
 * Date-created: 10/01/2016 18:02
 * Project: BubbleLobby
*/

public class BubbleLobby extends BubblePlugin {
    private static BubbleLobby instance;

    public static BubbleLobby getInstance() {
        return instance;
    }

    protected static void setInstance(BubbleLobby instance) {
        BubbleLobby.instance = instance;
    }

    private LobbyListener listener;

    public void onEnable() {
        setInstance(this);

        listener = new LobbyListener();

        registerListener(listener);

        new BukkitRunnable(){
            int current = 0;
            boolean title = false;
            final String[] strings = new String[]{
                    ChatColor.AQUA + ">" + ChatColor.BLUE + ChatColor.BOLD.toString() + "BubbleNetwork" + ChatColor.AQUA + "<",
                    ChatColor.AQUA + ">>" + ChatColor.BLUE + ChatColor.BOLD.toString() + "BubbleNetwork" + ChatColor.AQUA + "<<",
                    ChatColor.AQUA + ">>>" + ChatColor.BLUE + ChatColor.BOLD.toString() + "BubbleNetwork" + ChatColor.AQUA + "<<<",
                    ChatColor.AQUA + ">>" + ChatColor.BLUE + ChatColor.BOLD.toString() + "BubbleNetwork" + ChatColor.AQUA + "<<",
                    ChatColor.AQUA + ">" + ChatColor.WHITE + ChatColor.BOLD.toString() + "BubbleNetwork" + ChatColor.AQUA + "<",
                    ChatColor.AQUA + ">>" + ChatColor.WHITE + ChatColor.BOLD.toString() + "BubbleNetwork" + ChatColor.AQUA + "<<",
                    ChatColor.AQUA + ">>>" + ChatColor.WHITE + ChatColor.BOLD.toString() + "BubbleNetwork" + ChatColor.AQUA + "<<<",
                    ChatColor.AQUA + ">>" + ChatColor.WHITE + ChatColor.BOLD.toString() + "BubbleNetwork" + ChatColor.AQUA + "<<",
            };
            public void run() {
                Messages.broadcastMessageAction(strings[current]);
                current++;
                if(current == strings.length)current = 0;

                title =! title;
            }
        }.runTaskTimer(this,5L,5L);
    }


    public void onDisable() {
        setInstance(null);
        listener = null;
    }
}