package daredevil.ExpMiner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
//import org.bukkit.event.block.BlockListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

// Updated by Dockter 3/6/12

public class ExpMinerBlockListener implements Listener {
	public static ExpMiner plugin;
	private static HashMap<Player, Integer> combotBreakDiamand = new HashMap<Player, Integer>();
	private static HashMap<Player, Integer> temp;
	protected static HashMap<Integer, HashMap<Player, Integer>> blocBreak = new HashMap<Integer, HashMap<Player, Integer>>();
	private static HashMap<Integer, HashMap<Player, Integer>> blocPlace = new HashMap<Integer, HashMap<Player, Integer>>();
	private static Integer x1, x2, y1, y2, z1, z2, b1, b;
	private static Player p1, p;
	private static int idSave,idPerso;
	protected static int[] blockID = { 1, 2, 12, 13, 14, 15, 16, 17, 21, 24,
			49, 52, 56, 73, 82, 87, 88 };
	protected static boolean ferOr = true;


	public ExpMinerBlockListener(ExpMiner instance) {
		plugin = instance;
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {

		if (x1 != null) {
			x2 = x1;
			y2 = y1;
			z2 = z1;
			p = p1;
			b = b1;
		}

		x1 = event.getBlock().getX();
		y1 = event.getBlock().getY();
		z1 = event.getBlock().getZ();
		b1 = event.getBlock().getTypeId();
		p1 = event.getPlayer();

		if (b != null) {

			if (b == 3)

				b = 2;

			if (b == 74)
				b = 73;

			idSave = Arrays.binarySearch(blockID, b);

			idPerso = -1;
			for (int i =0;i<plugin.blockIDPerso.length;i++)
				if (b== plugin.blockIDPerso[i]){
					idPerso = i;
					break;
				}
					
			
		}
		if (b != null){
			if ((idSave >= 0)
					&& (event.getPlayer().getWorld().getBlockAt(x2, y2, z2)
							.getTypeId() != b)) {
				if ((p.hasPermission("expminer.use"))
						|| (!plugin.perm)
						|| (plugin.getServer().getPluginManager().getPlugin(
								"Permissions") != null && (ExpMiner.permissionHandler
								.has(p, "expminer.use")))) {

					boolean test = false;

					for (int i = 0; i < plugin.world.length; i++)
						if (p.getLocation().getWorld().getName().trim().equals(
								plugin.world[i].trim()))
							test = true;

					if (test) {
						if (blocPlace.containsKey(b)) {

							if (blocPlace.get(b).containsKey(p)) {
								if (blocPlace.get(b).get(p) > 1)
									blocPlace.get(b).put(p,
											blocPlace.get(b).get(p) - 1);
								else
									blocPlace.get(b).remove(p);
								ferOr = false;
							}
						}
						if (plugin.diamond) {
							if ((b1 == 56) && (ferOr)) {
								if (combotBreakDiamand.containsKey(p1))
									combotBreakDiamand.put(p1,
											combotBreakDiamand.get(p1) + 1);
								else
									combotBreakDiamand.put(p1, 1);

							} else if (combotBreakDiamand.containsKey(p1)) {
								
									plugin.log.info(plugin.diamondMsg);

								String diamondMsgaff =	plugin.diamondMsg.replaceAll("(&([a-f0-9]))","\u00A7$2");
								 diamondMsgaff =diamondMsgaff.replace("<nb>", combotBreakDiamand
												.get(p1)
												+ "");

								 diamondMsgaff = diamondMsgaff
										.replace("<player>", p1.getDisplayName()
												+ "");
								plugin.getServer().broadcastMessage( diamondMsgaff);
								combotBreakDiamand.remove(p1);
							}
						}

						if (!blocBreak.containsKey(b)) {
							temp = new HashMap<Player, Integer>();
							blocBreak.put(b, temp);

						}

						if (ferOr) {
							if (blocBreak.get(b).containsKey(p))
								blocBreak.get(b).put(p,
										blocBreak.get(b).get(p) + 1);
							else
								blocBreak.get(b).put(p, 1);
							if (blocBreak.get(b).get(p) == plugin.block[idSave]) {

								p.giveExp(plugin.gain[idSave]);

								blocBreak.get(b).put(p, 0);
							}
						}
						ferOr = true;
					}
				}
			}
			if ((idPerso >= 0)
					&& (event.getPlayer().getWorld().getBlockAt(x2, y2, z2)
							.getTypeId() != b)) {
				if ((p.hasPermission("expminer.use"))
						|| (!plugin.perm)
						|| (plugin.getServer().getPluginManager().getPlugin(
								"Permissions") != null && (ExpMiner.permissionHandler
								.has(p, "expminer.use")))) {

					boolean test = false;

					for (int i = 0; i < plugin.world.length; i++)
						if (p.getLocation().getWorld().getName().trim().equals(
								plugin.world[i].trim()))
							test = true;

					if (test) {
						if (blocPlace.containsKey(b)) {

							if (blocPlace.get(b).containsKey(p)) {
								if (blocPlace.get(b).get(p) > 1)
									blocPlace.get(b).put(p,
											blocPlace.get(b).get(p) - 1);
								else
									blocPlace.get(b).remove(p);
								ferOr = false;
							}
						}
						if (plugin.diamond) {
							if ((b1 == 56) && (ferOr)) {
								if (combotBreakDiamand.containsKey(p1))
									combotBreakDiamand.put(p1,
											combotBreakDiamand.get(p1) + 1);
								else
									combotBreakDiamand.put(p1, 1);

							} else if (combotBreakDiamand.containsKey(p1)) {
								
									plugin.log.info(plugin.diamondMsg);

								String diamondMsgaff =	plugin.diamondMsg.replaceAll("(&([a-f0-9]))","\u00A7$2");
								 diamondMsgaff =diamondMsgaff.replace("<nb>", combotBreakDiamand
												.get(p1)
												+ "");

								 diamondMsgaff = diamondMsgaff
										.replace("<player>", p1.getDisplayName()
												+ "");
								plugin.getServer().broadcastMessage( diamondMsgaff);
								combotBreakDiamand.remove(p1);
							}
						}

						if (!blocBreak.containsKey(b)) {
							temp = new HashMap<Player, Integer>();
							blocBreak.put(b, temp);

						}

						if (ferOr) {
							if (blocBreak.get(b).containsKey(p))
								blocBreak.get(b).put(p,
										blocBreak.get(b).get(p) + 1);
							else
								blocBreak.get(b).put(p, 1);
							if (blocBreak.get(b).get(p) == plugin.blockPerso[idPerso]) {

								p.giveExp(plugin.gainPerso[idPerso]);

								blocBreak.get(b).put(p, 0);
							}
						}
						ferOr = true;
					}
				}
			}	
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {

		Player p = event.getPlayer();
		int b = event.getBlock().getTypeId();
		idSave = Arrays.binarySearch(blockID, b);
		idPerso = -1;
		for (int i =0;i<plugin.blockIDPerso.length;i++)
			if (b== plugin.blockIDPerso[i]){
				idPerso = i;
				break;
			}
		if (idSave >= 0) {
			boolean test = false;

			for (int i = 0; i < plugin.world.length; i++)
				if (p.getLocation().getWorld().getName().trim().equals(
						plugin.world[i].trim()))
					test = true;

			if ((test) && (b != 1) && (b != 2) && (b != 3) && (b != 12)
					&& (b != 24) && (b != 13)) {
				if (!blocPlace.containsKey(b)) {
					temp = new HashMap<Player, Integer>();
					blocPlace.put(b, temp);
				}
				if (blocPlace.get(b).containsKey(p))
					blocPlace.get(b).put(p, blocPlace.get(b).get(p) + 1);
				else
					blocPlace.get(b).put(p, 1);
			}
		}
		if (idPerso >= 0) {
			boolean test = false;

			for (int i = 0; i < plugin.world.length; i++)
				if (p.getLocation().getWorld().getName().trim().equals(
						plugin.world[i].trim()))
					test = true;

			if ((test) && (b != 1) && (b != 2) && (b != 3) && (b != 12)
					&& (b != 24) && (b != 13)) {
				if (!blocPlace.containsKey(b)) {
					temp = new HashMap<Player, Integer>();
					blocPlace.put(b, temp);
				}
				if (blocPlace.get(b).containsKey(p))
					blocPlace.get(b).put(p, blocPlace.get(b).get(p) + 1);
				else
					blocPlace.get(b).put(p, 1);
			}
		}
	}
}