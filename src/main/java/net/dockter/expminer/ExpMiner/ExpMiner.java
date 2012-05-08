package net.dockter.expminer.ExpMiner;

import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.permission.Permission;

public class ExpMiner extends JavaPlugin {

	public final Logger log = Logger.getLogger("Minecraft");
	public static Permission permissionHandler;
	protected final ExpMinerBlockListener blockListener = new ExpMinerBlockListener(this);
	Config config;
	protected String[] world;
	protected int[] gain,gainPerso;
	protected int[] block,blockPerso,blockIDPerso;
	protected boolean diamond;
	protected String diamondMsg;
	protected String version;
	protected boolean perm;

	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		setupPermissions();
		
		pm.registerEvents(blockListener, this);

		log.info("ExpMiner 1.6 enabled !");
		config = new Config(this);
		config.configCheck();
	}

	public void onDisable() {
		log.info("ExpMiner disabled");
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		if (!sender.getName().equals("CONSOLE")){
			if ((sender.hasPermission("expminer.reload"))
					|| (sender.isOp())
					|| (getServer().getPluginManager().getPlugin("Permissions") != null && (permissionHandler
							.has((Player) sender, "expminer.reload"))))
				if (commandLabel.equalsIgnoreCase("ExpMiner")) {
					toggleExpMiner((Player) sender);
					return true;
				}
		}else{
			config.configCheck();
			log.info("Plugin ExpMiner reloaded 1.6");
			return true;
		}
		return false;
	}

	public void toggleExpMiner(Player player) {
		config.configCheck();
		player.sendMessage("Plugin ExpMiner reloaded 1.6");
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
		permissionHandler = rsp.getProvider();
		return permissionHandler != null;
	}
}