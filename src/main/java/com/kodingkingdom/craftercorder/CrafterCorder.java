package com.kodingkingdom.craftercorder;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;


public class CrafterCorder extends Command implements Listener{	
		
	CrafterCorderPlugin plugin;
	ReentrantReadWriteLock lock = new ReentrantReadWriteLock (); 

	public void Live(){
		CrafterCorderConfig.loadConfig();
		plugin.getProxy().getPluginManager().registerListener(plugin, this);
		plugin.getProxy().getPluginManager().registerCommand(plugin, this);}
	public void Die(){}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onFindServer(ServerConnectEvent e){				
		lock.readLock().lock();;try{
			Map.Entry<String, String> cordServers = CrafterCorderConfig.CORDLIST.cordList.get(e.getPlayer().getUniqueId()); 
			if (cordServers != null){
				CrafterCorderPlugin.say(e.getPlayer().getName()+": Found entry");
				ServerInfo mainServer = ProxyServer.getInstance().getServerInfo(cordServers.getKey());
				ServerInfo fallBackServer = ProxyServer.getInstance().getServerInfo(cordServers.getValue());
				
				if (canConnect(mainServer)){
					CrafterCorderPlugin.say(e.getPlayer().getName()+": Connect to main");
					if (mainServer.getName().equals(e.getTarget().getName())) CrafterCorderPlugin.say(e.getPlayer().getName()+": Already connecting to main");
					else e.setTarget(mainServer);}
				else {
					CrafterCorderPlugin.say(e.getPlayer().getName()+": Connect to fallback");
					if (fallBackServer.getName().equals(e.getTarget().getName())) CrafterCorderPlugin.say(e.getPlayer().getName()+": Already connecting to fallback");
					else e.setTarget(fallBackServer);}}
			else{
				CrafterCorderPlugin.say(e.getPlayer().getName()+": Not finalist");
		        e.getPlayer().disconnect("You are not a finalist of the Cyberport Youth Coding Jam 1000!");}}finally{lock.readLock().unlock();}}
	private boolean canConnect(ServerInfo server){return true;/*
		try {
		   SocketAddress addr=server.getAddress();
		   Socket s = new Socket();
		   s.connect(addr);
		   s.close();
		   return true;}
		catch (IOException ex) {
		   return false;}*/
		}
	
	CrafterCorder(CrafterCorderPlugin Plugin){
		super("corder-reload");
		plugin=Plugin;}
	@Override
	public void execute(CommandSender arg0, String[] arg1) {
		lock.writeLock().lock();try{
			CrafterCorderConfig.loadConfig();
			arg0.sendMessage("Finished reloading CrafterCorder config.yml");}finally{lock.writeLock().unlock();}{}}}

