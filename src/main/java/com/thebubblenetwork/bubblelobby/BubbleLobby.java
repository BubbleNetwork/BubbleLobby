package com.thebubblenetwork.bubblelobby;

import com.bubblenetwork.api.framework.plugin.BubblePlugin;
import org.bukkit.plugin.java.JavaPlugin;

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

    public void onEnable() {
        setInstance(this);
    }

    public void onDisable() {
        setInstance(null);
    }
}
