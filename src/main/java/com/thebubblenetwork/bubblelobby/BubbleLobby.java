package com.thebubblenetwork.bubblelobby;

import com.thebubblenetwork.api.framework.BubbleNetwork;
import com.thebubblenetwork.api.framework.plugin.BubblePlugin;
import com.thebubblenetwork.api.framework.util.files.PropertiesFile;
import com.thebubblenetwork.api.framework.util.mc.items.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.text.ParseException;

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
    private static final File CONFIG = new File("lobby.properties");
    private static BubbleLobby instance;

    private static final String
    IDPROPERTY = "server-id";

    public static BubbleLobby getInstance() {
        return instance;
    }

    protected static void setInstance(BubbleLobby instance) {
        BubbleLobby.instance = instance;
    }

    private PropertiesFile file;
    private Integer id;

    public PropertiesFile getProperties() {
        return file;
    }

    public int getId(){
        return id;
    }

    private LobbyListener listener;

    public void onEnable() {
        setInstance(this);
        try {
            file = new PropertiesFile(CONFIG);
            id = getProperties().getNumber(IDPROPERTY).intValue();
        } catch (Exception e) {
            //Automatic Catch Statement
            e.printStackTrace();
            getLogger().severe("Error, Properties file incorrect - restarting");
            new BukkitRunnable() {
                public void run() {
                    Bukkit.shutdown();
                }
            }.runTask(this);
            return;
        }

        listener = new LobbyListener();


        new BukkitRunnable(){
            boolean b = true;
            public void run() {
                ItemStackBuilder compass = listener.getCompass();
                compass.withName(b ? LobbyListener.COMPASSNAME2 : LobbyListener.COMPASSNAME1);
                for(Player p:Bukkit.getOnlinePlayers()){
                    p.getInventory().setItem(LobbyListener.COMPASSSLOT,compass.build());
                }
                b =! b;
            }
        }.runTaskTimer(this,5L,5L);
    }


    public void onDisable() {
        setInstance(null);
        file = null;
        listener = null;
    }
}