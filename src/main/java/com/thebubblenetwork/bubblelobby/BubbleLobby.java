package com.thebubblenetwork.bubblelobby;

import com.thebubblenetwork.api.framework.messages.Messages;
import com.thebubblenetwork.api.framework.plugin.BubblePlugin;
import com.thebubblenetwork.api.framework.util.mc.scoreboard.BubbleBoardAPI;
import com.thebubblenetwork.api.framework.util.mc.scoreboard.ObjectiveUpdate;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

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
            boolean run;
            final String[] strings = new String[]{
                    "W",
                    "We",
                    "Wel",
                    "Welc",
                    "Welco",
                    "Welcom",
                    "Welcome ",
                    "Welcome t",
                    "Welcome to",
                    "Welcome to ",
                    "Welcome to B",
                    "Welcome to Bu",
                    "Welcome to Bub",
                    "Welcome to Bubb",
                    "Welcome to Bubbl",
                    "Welcome to Bubble",
                    "Welcome to BubbleN",
                    "Welcome to BubbleNe",
                    "Welcome to BubbleNet",
                    "Welcome to BubbleNetw",
                    "Welcome to BubbleNetwo",
                    "Welcome to BubbleNetwor",
                    "Welcome to BubbleNetwork",
                    "Welcome to §bBubbleNetwork",
                    "Welcome to BubbleNetwork",
                    "Welcome to §bBubbleNetwork",
                    "Welcome to BubbleNetwork",
                    "Welcome to §bBubbleNetwork",
                    "Welcome to BubbleNetwork",
                    "",
                    "",
                    "§bBubbleNetwork",
                    ">§bBubbleNetwork§r<",
                    "->§bBubbleNetwork§r<-",
                    "-->§bBubbleNetwork§r<--",
                    "--->§bBubbleNetwork§r<---",
                    "---->§bBubbleNetwork§r<----",
                    "----->§bBubbleNetwork§r<-----",
                    "---->§bBubbleNetwork§r<----",
                    "--->§bBubbleNetwork§r<---",
                    "->§bBubbleNetwork§r<-",
                    ">§bBubbleNetwork§r<",
                    "§bBubbleNetwork",
                    "",
                    "",
                    "",
                    "",
                    "T_",
                    "Th_",
                    "The_",
                    "The _",
                    "The n_",
                    "The ne_",
                    "The net_",
                    "The netw_",
                    "The netwo_",
                    "The networ_",
                    "The network_",
                    "The network _",
                    "The network o_",
                    "The network of_",
                    "The network of _",
                    "The network of b_",
                    "The network of bu_",
                    "The network of bub_",
                    "The network of bubb_",
                    "The network of bubbl_",
                    "The network of bubbly_",
                    "The network of bubbly _",
                    "The network of bubbly f_",
                    "The network of bubbly fu_",
                    "The network of bubbly fun_",
                    "The network of bubbly fun!",
                    "The network of bubbly §bfun§r!",
                    "The network of bubbly fun!",
                    "The network of bubbly §bfun§r!",
                    "The network of bubbly fun!",
                    "The network of bubbly §bfun§r!",
                    "",
                    "",
                    "§7",
                    "§7p§r_",
                    "§7pl§r_",
                    "§7pla§r_",
                    "§7play§r_",
                    "§7play.§r_",
                    "§7play.t§r_",
                    "§7play.th§r_",
                    "§7play.the§r_",
                    "§7play.theb§r_",
                    "§7play.thebu§r_",
                    "§7play.thebub§r_",
                    "§7play.thebubb§r_",
                    "§7play.thebubbl§r_",
                    "§7play.thebubble§r_",
                    "§7play.thebubblen§r_",
                    "§7play.thebubblene§r_",
                    "§7play.thebubblenet§r_",
                    "§7play.thebubblenetw§r_",
                    "§7play.thebubblenetwo§r_",
                    "§7play.thebubblenetwor§r_",
                    "§7play.thebubblenetwork§r_",
                    "§7play.thebubblenetwork.§r_",
                    "§7play.thebubblenetwork.c§r_",
                    "§7play.thebubblenetwork.co§r_",
                    "§7play.thebubblenetwork.com",
                    "§7play.thebubblenetwork.com",
                    "§6play.thebubblenetwork.com",
                    "§7play.thebubblenetwork.com",
                    "§6play.thebubblenetwork.com",
                    "§7play.thebubblenetwork.com",
                    "§6play.thebubblenetwork.com",
                    "",
                    "",

            };
            public void run() {
                Messages.broadcastMessageAction(strings[current]);
                current++;
                if(current == strings.length)current = 0;
                if(run) {
                    for (LobbyBoard board : LobbyBoard.map.values()) {
                        board.getObject().update(new ObjectiveUpdate(DisplaySlot.SIDEBAR) {
                            @Override
                            public void update(Objective objective) {
                                objective.setDisplayName(title ? LobbyBoard.TITLE1 : LobbyBoard.TITLE2);
                            }
                        });
                    }
                    title = !title;
                }
                run =! run;
            }
        }.runTaskTimer(this,1L,5L);
    }


    public void onDisable() {
        setInstance(null);
        listener = null;
    }
}