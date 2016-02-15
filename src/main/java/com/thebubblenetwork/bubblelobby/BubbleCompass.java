package com.thebubblenetwork.bubblelobby;

import com.thebubblenetwork.api.framework.util.mc.menu.Menu;
import com.thebubblenetwork.api.framework.util.mc.menu.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class BubbleCompass extends Menu {

    private static int getSize(Set<CompassItem> items){
        int currentsize = 0;
        for(CompassItem item:items){
            if(item.getNumber() > currentsize)currentsize = item.getNumber();
        }
        return MenuManager.getRoundedInventorySize(currentsize);
    }

    private Set<CompassItem> items;

    public BubbleCompass(Set<CompassItem> items) {
        super(ChatColor.AQUA + "BubbleNetwork", getSize(items));
        this.items = items;
        update();
    }

    public CompassItem getItem(int i){
        for(CompassItem item:getItems()){
            if(item.getNumber() == i)return item;
        }
        return null;
    }

    public Set<CompassItem> getItems() {
        return items;
    }

    public void setItems(Set<CompassItem> items) {
        this.items = items;
        inventory = Bukkit.createInventory(this,getSize(items),ChatColor.AQUA + "BubbleNetwork");
        update();
    }

    @Override
    public void click(Player player, ClickType clickType, int i, ItemStack itemStack) {
        CompassItem item = getItem(i);
        if(item != null){
            //TODO - Server sending
            player.sendMessage(item.getType().getName());
        }
    }

    @Override
    public ItemStack[] generate() {
        ItemStack[] stacks = new ItemStack[getInventory().getSize()];
        CompassItem item;
        for(int i = 0;i < getInventory().getSize();i++){
            item = getItem(i);
            stacks[i] = item == null ? null : item.getItem().build();
        }
        return stacks;
    }
}