package daredevil.ExpMiner;

import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class ExpMiner extends JavaPlugin {

	public final Logger log = Logger.getLogger("Minecraft");
	public static PermissionHandler permissionHandler;
	protected final ExpMinerBlockListener blockListener = new ExpMinerBlockListener(
			this);
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

		pm.addPermission(new Permission("expminer.use"));
		pm.addPermission(new Permission("expminer.reload"));
		// pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener,	Event.Priority.Normal, this);
		// pm.registerEvent(Event.Type.BLOCK_PLACE, blockListener,	Event.Priority.Normal, this);
		pm.registerEvents(new ExpMinerBlockListener(this), this);

		log.info("ExpMiner 1.7 beta enabled ! (dockter build)");
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

	private void setupPermissions() {
		if (permissionHandler != null) {
			return;
		}

		Plugin permissionsPlugin = this.getServer().getPluginManager()
				.getPlugin("Permissions");
		Plugin permissionsPlugin2 = this.getServer().getPluginManager()
				.getPlugin("PermissionsBukkit");

		if ((permissionsPlugin == null) && (permissionsPlugin2 == null)) {
			log.info("Permission system not detected, defaulting to OP");

		}
		if (permissionsPlugin == null) {

			return;
		}

		permissionHandler = ((Permissions) permissionsPlugin).getHandler();
		log.info("Found and will use plugin "
				+ ((Permissions) permissionsPlugin).getDescription()
						.getFullName());
	}
}