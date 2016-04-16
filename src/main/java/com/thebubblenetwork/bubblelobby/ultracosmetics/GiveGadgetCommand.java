package com.thebubblenetwork.bubblelobby.ultracosmetics;

import com.thebubblenetwork.api.framework.BubbleNetwork;
import com.thebubblenetwork.api.framework.player.BukkitBubblePlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.logging.Level;

public class GiveGadgetCommand extends Command{
    private static GiveGadgetCommand command;

    public static void register(){
        if(command != null)unregister();
        command = new GiveGadgetCommand();
        Field commandMap;
        try {
            commandMap = SimplePluginManager.class.getDeclaredField("commandMap");
            commandMap.setAccessible(true);
        } catch (NoSuchFieldException e) {
            BubbleNetwork.getInstance().getLogger().log(Level.WARNING, "Could not setup UC command");
            return;
        }
        CommandMap map;
        try {
            map = (CommandMap) commandMap.get(Bukkit.getServer().getPluginManager());
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
            //Cannot happen
        }
        command.register(map);
        map.register("_",command);
    }

    public static void unregister(){
        Field commandMap;
        Field knownCommands;
        try {
            commandMap = SimplePluginManager.class.getDeclaredField("commandMap");
            knownCommands = SimpleCommandMap.class.getDeclaredField("knownCommands");
            commandMap.setAccessible(true);
            knownCommands.setAccessible(true);
        } catch (NoSuchFieldException e) {
            BubbleNetwork.getInstance().getLogger().log(Level.WARNING, "Could not unregister UC command");
            return;
        }
        CommandMap map;
        Map<String,Command> commands;
        try {
            map = (CommandMap) commandMap.get(Bukkit.getServer().getPluginManager());
            commands = (Map<String, Command>) commandMap.get(null);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(e);
            //Cannot happen
        }
        command.unregister(map);
        commands.values().remove(command);
        command = null;
    }

    public GiveGadgetCommand() {
        super("givegadget");
    }

    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        BukkitBubblePlayer player = BukkitBubblePlayer.getObject(Bukkit.getPlayer(strings[0]).getUniqueId());
        String gadget = strings[1].replace("ultracosmetics.","");
        String split[] = gadget.split("\\.");
        if(player.getHubItemUsable(gadget)){
            int random = BubbleNetwork.getRandom().nextInt(100)+ 100;
            player.getPlayer().sendMessage(BubbleNetwork.getPrefix() + "You already this! Giving " + ChatColor.BLUE + random + ChatColor.RESET + " Tokens");
            player.setTokens(player.getTokens() + random);
        }
        else{
            player.setHubItemUsable(gadget, true);
        }
        return false;
    }
}
