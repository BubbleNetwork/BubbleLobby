package com.thebubblenetwork.bubblelobby;

import com.google.common.collect.ImmutableMap;
import com.thebubblenetwork.api.framework.BubbleNetwork;
import com.thebubblenetwork.api.framework.plugin.BubbleAddon;
import com.thebubblenetwork.api.framework.plugin.BubbleRunnable;
import com.thebubblenetwork.api.framework.util.mc.chat.ChatColorAppend;
import com.thebubblenetwork.api.framework.util.mc.items.ItemStackBuilder;
import com.thebubblenetwork.api.global.bubblepackets.messaging.messages.handshake.JoinableUpdate;
import com.thebubblenetwork.api.global.sql.SQLUtil;
import com.thebubblenetwork.api.global.type.ServerType;
import org.bukkit.Material;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Copyright Statement
 * ----------------------
 * Copyright (C) The Bubble Network, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Wrote by Jacob Evans <jacobevansminor@gmail.com>, 01 2016
 * <p>
 * <p>
 * Class information
 * ---------------------
 * Package: com.thebubblenetwork.bubblelobby
 * Date-created: 10/01/2016 18:02
 * Project: BubbleLobby
 */

public class BubbleLobby extends BubbleAddon {
    public static BubbleLobby getInstance() {
        return instance;
    }

    protected static void setInstance(BubbleLobby instance) {
        BubbleLobby.instance = instance;
    }
    private static BubbleLobby instance;
    private LobbyListener listener;
    private BubbleNetwork network;
    private LobbyCompass compass;

    public void onLoad() {
        network = BubbleNetwork.getInstance();
    }

    public void onEnable() {
        setInstance(this);

        listener = new LobbyListener();
        Set<CompassItem> items = new HashSet<>();

        try {
            if (!SQLUtil.tableExists(network.getConnection(), "compass_items")) {
                getNetwork().logInfo("Creating compass-item DB");
                SQLUtil.createTable(network.getConnection(), "compass_items", new ImmutableMap.Builder<String, Map.Entry<SQLUtil.SQLDataType, Integer>>().put("name", new AbstractMap.SimpleImmutableEntry<>(SQLUtil.SQLDataType.TEXT, 32)).put("lore", new AbstractMap.SimpleImmutableEntry<>(SQLUtil.SQLDataType.TEXT, -1)).put("id", new AbstractMap.SimpleImmutableEntry<>(SQLUtil.SQLDataType.INT, 3)).put("data", new AbstractMap.SimpleImmutableEntry<>(SQLUtil.SQLDataType.INT, 1)).put("servertype", new AbstractMap.SimpleImmutableEntry<>(SQLUtil.SQLDataType.TEXT, 32)).put("slot", new AbstractMap.SimpleImmutableEntry<>(SQLUtil.SQLDataType.INT, 2)).build());
                getNetwork().endSetup("Could not find compass items DB");
            }
            ResultSet set = SQLUtil.query(network.getConnection(), "compass_items", "*", new SQLUtil.Where("1"));
            while (set.next()) {
                String name = ChatColorAppend.translate(set.getString("name"));
                String[] lore = ChatColorAppend.translate(set.getString("lore")).split(",");
                int materialid = set.getInt("id");
                int data = set.getInt("data");
                ServerType type;
                try {
                    type = ServerType.getType(set.getString("servertype"));
                } catch (IllegalArgumentException e) {
                    getNetwork().logSevere(e.getMessage());
                    continue;
                }
                int slot = set.getInt("slot");
                if (slot < -1 || slot > 54) {
                    getNetwork().logSevere("Slot: " + String.valueOf(slot) + " is invalid");
                    continue;
                }
                Material m = Material.getMaterial(materialid);
                if (m == null) {
                    getNetwork().logSevere("Invalid material ID: " + String.valueOf(materialid));
                    continue;
                }
                getNetwork().logInfo("Registering new compassitem with slot: " + String.valueOf(slot));
                items.add(new CompassItem(slot, type, new ItemStackBuilder(m).withName(name).withLore(lore).withData(data)));
            }
            set.close();
        } catch (SQLException | ClassNotFoundException e) {
            getNetwork().logSevere(e.getMessage());
            getNetwork().endSetup("Could not load compass items");
        }

        compass = new LobbyCompass(items);

        registerListener(getListener());


        new BubbleRunnable() {
            public void run() {
                getNetwork().logInfo("Setting joinable...");
                try {
                    getNetwork().getPacketHub().sendMessage(getNetwork().getProxy(), new JoinableUpdate(true));
                } catch (IOException e) {
                    getNetwork().logSevere(e.getMessage());
                    getNetwork().endSetup("Could not set joinable");
                }
            }
        }.runTaskAsynchonrously(this);
    }

    public void onDisable() {
        try {
            getNetwork().getPacketHub().sendMessage(getNetwork().getProxy(), new JoinableUpdate(false));
        } catch (IOException e) {
            getNetwork().logSevere(e.getMessage());
            getNetwork().endSetup("Could not set joinable");
        }
        setInstance(null);
        listener = null;
        network = null;
    }

    public LobbyListener getListener() {
        return listener;
    }

    public LobbyCompass getCompass() {
        return compass;
    }

    public BubbleNetwork getNetwork() {
        return network;
    }

    public int getVersion() {
        return 0;
    }

    public long finishUp() {
        getNetwork().logInfo("Finishing up lobby");
        try {
            getNetwork().getPacketHub().sendMessage(getNetwork().getProxy(), new JoinableUpdate(false));
        } catch (IOException e) {
            getNetwork().logSevere(e.getMessage());
            getNetwork().endSetup("Could not set joinable");
        }
        return 60;
    }

}