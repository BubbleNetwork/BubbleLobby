package com.thebubblenetwork.bubblelobby;

import com.thebubblenetwork.api.framework.BubbleNetwork;
import com.thebubblenetwork.api.global.file.DownloadUtil;
import com.thebubblenetwork.api.global.file.FileUTIL;
import com.thebubblenetwork.api.global.file.SSLUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

public class CosmeticsManager {
    private static final String PluginURL = "";
    private BubbleNetwork network;
    private final File file;
    private final File jar;
    private JavaPlugin cosmetics = null;

    public CosmeticsManager(BubbleNetwork network){
        this.network = network;
        file = new File(BubbleLobby.getInstance().getLoader().getJar().getParent(),"UltraCosmetics");
        jar = new File(file,"UltraCosmetics.jar");
    }

    public void download(){
        if(!file.isDirectory())file.delete();
        if(!file.exists())file.mkdir();
        try {
            SSLUtil.allowAnySSL();
        } catch (Exception e) {
            network.getLogger().log(Level.WARNING,"Could not allow any SSL",e);
        }
        try {
            DownloadUtil.download(jar,PluginURL);
        } catch (Exception e) {
            network.getLogger().log(Level.WARNING,"Could not download UltraCosmetics");
        }
    }

    public void load(){
        if(cosmetics != null)throw new IllegalArgumentException("Already loaded");
        if(!jar.exists())throw new IllegalArgumentException("Jar doesn't exist");
        cosmetics = network.getPlugman().load(jar);
    }

    public void enable(){
        if(cosmetics == null || !cosmetics.isEnabled())throw new IllegalArgumentException("Not loaded");
        network.getPlugman().enable(cosmetics);
    }

    public void disable(){
        if(cosmetics == null || !cosmetics.isEnabled())throw new IllegalArgumentException("Not enabled");
        network.getPlugman().disable(cosmetics);
    }

    public void unload(){
        if(cosmetics == null || cosmetics.isEnabled())throw new IllegalArgumentException("Not loaded");
        network.getPlugman().unload(cosmetics);
        cosmetics = null;
    }

    public void clearUp(){
        if(cosmetics != null)throw new IllegalArgumentException("Must be unloaded");
        FileUTIL.deleteDir(file);
    }

    public Unsafe unsafe(){
        return new Unsafe() {
            public CosmeticsHook create() {
                if(cosmetics == null || !cosmetics.isEnabled())throw new IllegalArgumentException("Can only hook when enabled");
                return new CosmeticsHook();
            }
        };
    }

    interface Unsafe{
        CosmeticsHook create();
    }
}
