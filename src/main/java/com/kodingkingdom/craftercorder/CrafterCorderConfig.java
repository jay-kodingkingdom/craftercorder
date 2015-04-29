package com.kodingkingdom.craftercorder;

import java.io.File;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public enum CrafterCorderConfig {

	CORDLIST("craftercorder.cordlist")
	;
	
	public final String config;
	public HashMap<UUID,Map.Entry<String,String>> cordList;
		
	private CrafterCorderConfig(String Config){
		config=Config;}
		
	public static void loadConfig(){
		CORDLIST.cordList = new HashMap<UUID,Map.Entry<String,String>>();
		CrafterCorderPlugin plugin = CrafterCorderPlugin.getPlugin();
		try{
			Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "config.yml"));
		
			try{
				for (String cordEntry : config.getStringList(CORDLIST.config)){
					String[] cord=cordEntry.split("~");
					if (cord.length!=3)throw new IllegalStateException();
					CORDLIST.cordList.put(
							UUID.fromString(cord[0]),
							new AbstractMap.SimpleEntry<String,String>(
								cord[1],
								cord[2]));}			
				plugin.getLogger().info("Config successfully loaded");}
			catch(Exception e){
				config.set("craftercorder.ERROR", true);
				throw e;}}
		catch(Exception e){
			plugin.getLogger().severe("Could not load config!");
			plugin.getLogger().severe("ERROR MESSAGE: "+e.getMessage());
			e.printStackTrace();}}}
