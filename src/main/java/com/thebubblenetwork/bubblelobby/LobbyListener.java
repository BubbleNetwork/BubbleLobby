package com.thebubblenetwork.bubblelobby;

import com.thebubblenetwork.api.framework.util.mc.items.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

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
 * Date-created: 10/01/2016 19:58
 * Project: BubbleLobby
 */
public class LobbyListener implements Listener{

    public LobbyListener(){
        BubbleLobby.getInstance().registerListener(this);
    }

    protected static final String
    COMPASSNAME = ChatColor.BLUE + ">" + ChatColor.AQUA + ChatColor.BOLD.toString() + "Compass" + ChatColor.BLUE + "<";

    protected static final int
    COMPASSSLOT = 0;

    private ItemStackBuilder compass = new ItemStackBuilder(Material.COMPASS)
            .withAmount(1)
            .withName(COMPASSNAME)
            .withLore(ChatColor.GRAY + "Right-click to open up the menu!",ChatColor.GRAY + "You can access any gamemode");


    public ItemStackBuilder getCompass() {
        return compass;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        p.setGameMode(GameMode.ADVENTURE);
        p.setLevel(0);
        p.setFoodLevel(20);
        p.setHealth(20);
        p.getInventory().setArmorContents(new ItemStack[4]);
        p.getInventory().setContents(generateInventory());
    }

    public ItemStack[] generateInventory(){
        ItemStack[] is = new ItemStack[4*9];
        is[COMPASSSLOT] = compass.build();
        return is;
    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockCanBuild(BlockCanBuildEvent e){
        e.setBuildable(false);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlaceMultiple(BlockMultiPlaceEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player)e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteractMedium(PlayerInteractEvent e){
        int slot = e.getPlayer().getInventory().getHeldItemSlot();
    }

    @EventHandler
    public void onPlayerFoodLevelChange(FoodLevelChangeEvent e){
        e.setFoodLevel(20);
    }
}
