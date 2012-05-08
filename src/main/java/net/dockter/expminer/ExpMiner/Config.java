package net.dockter.expminer.ExpMiner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
	private static ExpMiner plugin;
	public String directory;
	File file;
	public boolean erreur = false;

	public Config(ExpMiner instance) {
		plugin = instance;
		directory = "plugins" + File.separator
				+ plugin.getDescription().getName();
		file = new File(directory + File.separator + "config.yml");
	}

	public void configCheck() {
		new File(directory).mkdir();

		if (!file.exists()) {
			try {
				file.createNewFile();

				addDefaults();

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {

			loadkeys();
			plugin.blockListener.blocBreak.clear();
		}
	}

	private Boolean readBoolean(String root) {
		YamlConfiguration config = load();
		if (!erreur) {
			try {
				return config.getBoolean(root, true);
			} catch (Exception ex) {
			}
		}
		return false;
	}

	private int readInt(String root) {
		YamlConfiguration config = load();
		if (!erreur) {
			try {
				return config.getInt(root, 0);
			} catch (Exception ex) {
			}
		}
		return 0;
	}

	/*
	 * private Double readDouble(String root) { YamlConfiguration config =
	 * load(); if (!erreur) { try { return config.getDouble(root, 0); } catch
	 * (Exception ex) {
	 * plugin.log.severe("Erreur fichier de configration d'ExpMiner"); erreur =
	 * true; } } return 0.0; }
	 */

	private String readString(String root) {
		YamlConfiguration config = load();
		if (!erreur) {
			try {
				return config.getString(root);
			} catch (Exception ex) {
			}
		}
		return "";
	}

	private String[] readStringArray(String root) {
		YamlConfiguration config = load();
		String[] res = null;
		if (!erreur) {
			try {
				Object[] temp = config.getStringList(root).toArray();
				res = new String[temp.length];
				for (int i = 0; i < temp.length; i++)
					res[i] = temp[i].toString();
			} catch (Exception e) {
			}
		}
		if (res == null) {
			res = new String[2];
			res[0] = "world";
			res[1] = "world_nether";
		}
		return res;
	}

	private YamlConfiguration load() {

		try {
			YamlConfiguration config = new YamlConfiguration();
			config.load(file);
			return config;

		} catch (Exception e) {
			plugin.log.warning(e.getMessage());
		}
		return null;
	}

	private void addDefaults() {
		plugin.log.info("Generating Config File...");
		YamlConfiguration config = load();
		config.set("Version", "1.6");
		ArrayList<String> test = new ArrayList<String>();
		test.add("world");
		test.add("world_nether");
		config.set("World", test);
		config.set("Show_diamond_found", true);
		config.set("Message_diamond_found", "* &b<player> viens de trouver <nb> diamants !");
		config.set("Use_Permissions", false);
		config.set("Nb_stone", 10);
		config.set("Exp_stone_Award", 2);
		config.set("Nb_dirt", 0);
		config.set("Exp_dirt_Award", 0);
		config.set("Nb_sand", 0);
		config.set("Exp_sand_Award", 0);
		config.set("Nb_gravel", 0);
		config.set("Exp_gravel_Award", 0);
		config.set("Nb_gold", 2);
		config.set("Exp_gold_Award", 3);
		config.set("Nb_iron", 3);
		config.set("Exp_iron_Award", 3);
		config.set("Nb_coal", 5);
		config.set("Exp_coal_Award", 2);
		config.set("Nb_log", 10);
		config.set("Exp_log_Award", 2);
		config.set("Nb_lapis", 3);
		config.set("Exp_lapis_Award", 3);
		config.set("Nb_sandstone", 0);
		config.set("Exp_sandstone_Award", 0);
		config.set("Nb_obsidian", 2);
		config.set("Exp_obsidian_Award", 5);
		config.set("Nb_mobSpawner", 1);
		config.set("Exp_mobSpawner_Award", 50);
		config.set("Nb_diamond", 1);
		config.set("Exp_diamond_Award", 5);
		config.set("Nb_redstone", 5);
		config.set("Exp_redstone_Award", 3);
		config.set("Nb_clay", 0);
		config.set("Exp_clay_Award", 0);
		config.set("Nb_netherrack", 30);
		config.set("Exp_netherrack_Award", 1);
		config.set("Nb_soulsand", 0);
		config.set("Exp_soulsand_Award", 0);
		HashMap< String, Object >  	map = new HashMap< String, Object > ();
		HashMap< String, Object >  	map2 = new HashMap< String, Object > ();
		map2.put("Nb",10);
		map2.put("Exp_Award",20);
		map.put("112",map2);
		config.createSection("new_block",map);
		
		try {
			config.save(file);
		} catch (IOException e) {
			plugin.log.warning(e.getMessage());
		}
		loadkeys();
	}

	private void loadkeys() {
		plugin.log.info("Loading Config File...");
	
		plugin.version = readString("Version") + "";
		plugin.perm = readBoolean("Use_Permissions");
		plugin.diamond = readBoolean("Show_diamond_found");
		plugin.diamondMsg = readString("Message_diamond_found");
		plugin.world = readStringArray("World");
		int[] block;
		int[] gain;
		if (plugin.version.equals("1.6")) {

			block = new int[17];
			gain = new int[17];
			String[] string = { "stone", "dirt", "sand", "gravel", "gold",
					"iron", "coal", "log", "lapis", "sandstone", "obsidian",
					"mobSpawner", "diamond", "redstone", "clay", "netherrack",
					"soulsand" };

			for (int i = 0; i < block.length; i++) {
				block[i] = readInt("Nb_" + string[i]);
				gain[i] = readInt("Exp_" + string[i] + "_Award");

			}
			plugin.block = block;
			plugin.gain = gain;
			
			YamlConfiguration config = load();
			ConfigurationSection temp =config.getConfigurationSection ("new_block");
			ConfigurationSection temp2 ;
			Set<String> tes =temp.getKeys(false);
			plugin.blockIDPerso= new int[tes.size()+1];
			plugin.blockPerso= new int[tes.size()+1];
			plugin.gainPerso= new int[tes.size()+1];
			int cpt=0;
			for( String s : tes ){
				temp2 =temp.getConfigurationSection (s);
		
				 
				 temp2.getInt("Exp_Award");
				plugin.gainPerso[cpt] = temp2.getInt("Exp_Award");
				plugin.blockPerso[cpt] = temp2.getInt("Nb");
				plugin.blockIDPerso[cpt] = Integer.valueOf(s);
				cpt++;
			}
			
			

		} else {
			if (plugin.version.equals("1.5")||plugin.version.equals("1.5.3")) {

				block = new int[17];
				gain = new int[17];
				String[] string = { "stone", "dirt", "sand", "gravel", "gold",
						"iron", "coal", "log", "lapis", "sandstone",
						"obsidian", "mobSpawner", "diamond", "redstone",
						"clay", "netherrack", "soulsand" };

				for (int i = 0; i < block.length; i++) {
					block[i] = readInt("Nb_" + string[i]);
					gain[i] = readInt("Exp_" + string[i] + "_Award");

				}
				plugin.block = block;
				plugin.gain = gain;
				majConf();

			} else {
				block = new int[17];
				gain = new int[17];
				String[] string = { "stone", "dirt", "sand", "gravel", "gold",
						"iron", "coal", "log", "lapis", "sandstone",
						"obsidian", "mobSpawner", "diamond", "redstone",
						"clay", "netherrack", "soulsand" };

				for (int i = 0; i < block.length; i++) {
					block[i] = readInt(string[i] + "Num");
					gain[i] = readInt(string[i] + "Exp");

				}

			}
			plugin.block = block;
			plugin.gain = gain;
			majConf();

		}
		erreur = false;
	}

	private void majConf() {
		plugin.log.info("Updating Config File...");
		file.delete();
		try {
			file.createNewFile();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			plugin.log.warning(e1.getMessage());
		}
		YamlConfiguration config = load();
		config.set("Version", "1.6");
		ArrayList<String> test = new ArrayList<String>();
		if (plugin.version.equals("1.5")||plugin.version.equals("1.5.3")||plugin.version.equals("1.5.2")){
			for (int i=0;i<plugin.world.length;i++)
		test.add(plugin.world[i]);
		}else{

			 test = new ArrayList<String>();
			test.add("world");
			test.add("world_nether");
		}
			config.set("World", test);
		config.set("Show_diamond_found", plugin.diamond);
			plugin.diamondMsg="* &b<player> viens de trouver <nb> diamants !";
		config.set("Message_diamond_found", plugin.diamondMsg);
		config.set("Use_Permissions", plugin.perm);

		String[] string = { "stone", "dirt", "sand", "gravel", "gold",
				"iron", "coal", "log", "lapis", "sandstone",
				"obsidian", "mobSpawner", "diamond", "redstone",
				"clay", "netherrack", "soulsand" };

		for (int i = 0; i < plugin.block.length; i++) {
			config.set("Nb_" + string[i], plugin.block[i]);
			config.set("Exp_" + string[i] + "_Award", plugin.gain[i]);
		}

		HashMap< String, Object >  	map = new HashMap< String, Object > ();
		HashMap< String, Object >  	map2 = new HashMap< String, Object > ();
		map2.put("Nb",10);
		map2.put("Exp_Award",20);
		map.put("112",map2);
		config.createSection("new_block",map);
		try {
			config.save(file);
		} catch (IOException e) {
			plugin.log.warning(e.getMessage());
		}
	}
}