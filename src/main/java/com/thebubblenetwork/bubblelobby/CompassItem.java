package com.thebubblenetwork.bubblelobby;

import com.thebubblenetwork.api.framework.util.mc.items.ItemStackBuilder;
import com.thebubblenetwork.api.global.type.ServerType;

public class CompassItem {
    private int number;
    private ItemStackBuilder item;
    private ServerType type;

    public CompassItem(int number, ServerType type, ItemStackBuilder item) {
        this.number = number;
        this.type = type;
        this.item = item;
    }

    public int getNumber() {
        return number;
    }

    public ItemStackBuilder getItem() {
        return item;
    }

    public ServerType getType() {
        return type;
    }
}
