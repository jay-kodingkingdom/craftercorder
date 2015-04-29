package com.kodingkingdom.craftercorder;
import java.util.logging.Level;

import net.md_5.bungee.api.plugin.Plugin;

public class CrafterCorderPlugin extends Plugin{
		
	CrafterCorder crafterCorder;
	
	@Override
    public void onEnable(){
    	crafterCorder=new CrafterCorder(this);
    	crafterCorder.Live();} 
    @Override
    public void onDisable(){crafterCorder.Die();}
    
    static CrafterCorderPlugin instance=null;
    public CrafterCorderPlugin(){
    	super();
    	instance=this;}
    public static CrafterCorderPlugin getPlugin(){
    	return instance;}
    public static void say(String msg){
    	instance.getLogger().log(Level.INFO//Level.FINE
    			, msg);}
    public static void debug(String msg){
    	instance.getLogger().log(Level.INFO//Level.FINE
    			, msg);}}