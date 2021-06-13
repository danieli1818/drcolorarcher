package drcolorarcher.subcommands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import com.danieli1818.drminigames.common.BlockInformation;

import drcolorarcher.BlockPointsInformation;
import drcolorarcher.DRColorArcher;

public class AddCommands {

	private DRColorArcher arenaLogic;

	public AddCommands(DRColorArcher arenaLogic) {
		this.arenaLogic = arenaLogic;
	}

	public boolean commands(Player player, String subCommand, String[] args) {

		if (subCommand == null) {
			helpCommand(player, 1);
			return true;
		} else if (subCommand.equalsIgnoreCase("block")) {
			if (args.length < 3 || args.length > 4) {
				player.sendMessage(
						"Invalid Syntax! Correct Syntax is: /drminigames command [ArenaID] add block [TeamID] [Points] {Block}");
				return true;
			}
			try {
				int points = Integer.parseInt(args[2]);
				String block = args.length == 4 ? args[3] : null;
				if (!addBlock(player, args[1], block, points)) {
					return false;
				}
				player.sendMessage("Successfully Add Block!");
			} catch (NumberFormatException e) {
				player.sendMessage("Points should be an integer!");
				return true;
			}
			return true;
		} else if (subCommand.equalsIgnoreCase("teams")) {
			if (args.length < 2) {
				player.sendMessage(
						"Invalid Syntax! Correct Syntax is: /drminigames command [ArenaID] add teams [TeamID1] [TeamID2] [TeamID3] [TeamID4] ...");
				return true;
			}
			for (int i = 1; i < args.length; i++) {
				if (!addTeam(player, args[i])) {
					continue;
				}
			}
			return true;
		} else if (subCommand.equalsIgnoreCase("help")) {
			if (args.length != 2) {
				helpCommand(player, 1);
			} else {
				try {
					int page = Integer.parseInt(args[1]);
					helpCommand(player, page);
				} catch (NumberFormatException e) {
					player.sendMessage("Invalid Syntax! Not Valid Number!");
				}
			}
			return true;
		} else {
			System.out.println("Invalid Command! For Help Type: /drminigames command [ArenaID] add help!");
			return false;
		}
	}

	private boolean addBlock(Player player, String teamID, String block, int points) {

		if (!player.hasPermission("drminigames.drcolorarcher.add.block." + this.arenaLogic.getArenaID())) {
			player.sendMessage("You don't have permission to run this command! (drminigames.drcolorarcher.add.block."
					+ this.arenaLogic.getArenaID() + ")");
			return false;
		}

		if (!this.arenaLogic.containsTeam(teamID)) {
			player.sendMessage("Team " + teamID + " doesn't exist!");
			return false;
		}

		MaterialData data = null;

		if (block == null) {

			ItemStack holdingItem = player.getInventory().getItemInMainHand();

			if (holdingItem == null) {
				player.sendMessage("You didn't type block type nor hold a block in your main hand!");
				return false;
			}

			data = holdingItem.getData();

			if (!data.getItemType().isBlock()) {
				player.sendMessage("You didn't hold a block type item!");
				return false;
			}

		} else {

			String[] materialIDSubID = block.split(":");

			Material material = null;
			if (materialIDSubID.length >= 1) {
				material = Material.matchMaterial(materialIDSubID[0]);

				if (material == null) {
					player.sendMessage("Block not found!");
					return false;
				}

				if (!material.isBlock()) {
					player.sendMessage("Not Valid Block!");
					return false;
				}

				if (material != null && materialIDSubID.length >= 2) {
					try {
						data = new MaterialData(material, Byte.parseByte(materialIDSubID[1]));
					} catch (NumberFormatException e) {
						player.sendMessage("Not Valid SubID!");
						data = new MaterialData(material);
					}
				} else {
					data = new MaterialData(material);
				}
			}

		}

		BlockPointsInformation bpi = new BlockPointsInformation(new BlockInformation(data), points);
		this.arenaLogic.addBlockPointsInformationToTeam(bpi, teamID);
		return true;
	}

	private boolean addTeam(Player player, String name) {
		if (!player.hasPermission("drminigames.drcolorarcher.add.team." + this.arenaLogic.getArenaID())) {
			player.sendMessage("You don't have permission for this command! (" + "drminigames.drcolorarcher.add.team."
					+ this.arenaLogic.getArenaID() + ")");
			return false;
		}
		if (!this.arenaLogic.addTeam(name)) {
			player.sendMessage("Team " + name + " already exists!");
			return false;
		}
		player.sendMessage("Team " + name + " has been added successfully!");
		return true;
	}

	private void helpCommand(Player player, int page) {
		if (page == 1) {
			player.sendMessage("/drminigames command [ArenaID] add help [Page Number] - Show This Page!");
			player.sendMessage(
					"/drminigames command [ArenaID] add block [TeamID] [Points] {Block} - Add Block For Team!");
			player.sendMessage(
					"/drminigames command [ArenaID] add teams [TeamID1] [TeamID2] [TeamID3] [TeamID4] ... - Add Teams.");
		} else {
			player.sendMessage("Not Valid Page Number!");
		}
	}

}
