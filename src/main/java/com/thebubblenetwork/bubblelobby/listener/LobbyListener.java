package com.thebubblenetwork.bubblelobby.listener;

import com.thebubblenetwork.api.framework.BukkitBubblePlayer;
import com.thebubblenetwork.api.framework.util.mc.items.ItemStackBuilder;
import com.thebubblenetwork.api.global.player.BubblePlayer;
import com.thebubblenetwork.bubblelobby.BubbleLobby;
import com.thebubblenetwork.bubblelobby.scoreboard.LobbyScoreboard;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Team;

import java.util.UUID;

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
 * Date-created: 10/01/2016 19:58
 * Project: BubbleLobby
 */
public class LobbyListener implements Listener {

    protected static final int COMPASSSLOT = 0, COSMETICSLOT = 8;

    private ItemStackBuilder compass = new ItemStackBuilder(Material.COMPASS).withAmount(1).withName(ChatColor.AQUA + "Compass").withLore(ChatColor.GRAY + "Click to open up the server-menu!");
    private ItemStackBuilder cosmetics = new ItemStackBuilder(Material.BLAZE_POWDER).withName(ChatColor.AQUA + "Cosmetics").withLore(ChatColor.GRAY + "Click to open the cosmetics menu");

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        final LobbyScoreboard scoreboard = LobbyScoreboard.createBoard(p);
        p.setGameMode(GameMode.ADVENTURE);
        p.setLevel(0);
        p.setFoodLevel(20);
        p.setHealth(20);
        p.getInventory().setArmorContents(new ItemStack[4]);
        p.getInventory().setContents(generateInventory());
        p.teleport(BubbleLobby.getInstance().getSpawn().toLocation(BubbleLobby.getInstance().getW()));
        final UUID u = p.getUniqueId();
        new Thread(){
            @Override
            public void run() {
                if(p.isOnline()) {
                    BukkitBubblePlayer player = BukkitBubblePlayer.getObject(u);
                    if (player != null) {
                        for(LobbyScoreboard other:LobbyScoreboard.getBoards()){
                            other.applyRank(player.getRank(),p);
                        }
                    }
                    for(BubblePlayer other:BukkitBubblePlayer.getPlayerObjectMap().values()){
                        scoreboard.applyRank(other.getRank(),(Player)other.getPlayer());
                    }
                }
            }
        }.start();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        if(e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        LobbyScoreboard.removeBoard(p.getUniqueId());
        new Thread(){
            @Override
            public void run(){
                for(LobbyScoreboard other:LobbyScoreboard.getBoards()){
                    for(Team t:other.getObject().getBoard().getTeams()){
                        t.removePlayer(p);
                    }
                }
            }
        }.start();
    }

    public ItemStack[] generateInventory() {
        ItemStack[] is = new ItemStack[4 * 9];
        is[COMPASSSLOT] = compass.build();
        is[COSMETICSLOT] = cosmetics.build();
        return is;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockDamage(BlockDamageEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockCanBuild(BlockCanBuildEvent e) {
        e.setBuildable(false);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlaceMultiple(BlockMultiPlaceEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryClick(InventoryClickEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onItemDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onItemPickup(PlayerPickupItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerClickCrate(PlayerInteractEvent e){
        Block b;
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK && (b = e.getClickedBlock()) != null && b.getType() == Material.TRAPPED_CHEST && b.getState() instanceof Chest){
            Player p = e.getPlayer();
            BubbleLobby.getInstance().getManager().unsafe().create().openCrate(p);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteractMedium(PlayerInteractEvent e) {
        int slot = e.getPlayer().getInventory().getHeldItemSlot();
        if (e.getAction() != Action.LEFT_CLICK_AIR && e.getAction() != Action.LEFT_CLICK_BLOCK) {
            if (slot == COMPASSSLOT) {
                BubbleLobby.getInstance().getCompass().show(e.getPlayer());
            }
            else if(slot == COSMETICSLOT){
                BubbleLobby.getInstance().getManager().unsafe().create().openMenu(e.getPlayer());
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerFoodLevelChange(FoodLevelChangeEvent e) {
        e.setFoodLevel(20);
    }
}
