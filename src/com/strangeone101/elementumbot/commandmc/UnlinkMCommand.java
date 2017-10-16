package com.strangeone101.elementumbot.commandmc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.strangeone101.elementumbot.AlterEgoPlugin;
import com.strangeone101.elementumbot.command.LinkCommand;
import com.strangeone101.elementumbot.config.ConfigManager;

import de.btobastian.javacord.entities.User;

public class UnlinkMCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can run this command!");
			return true;
		}
		if (!sender.hasPermission("alterego.command.unlink")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission to run this command!");
			return true;
		}
		if (!LinkCommand.links.containsValue(((Player)sender).getUniqueId()) || (!sender.hasPermission("alterego.command.unlink.others") && args.length != 0)) {
			sender.sendMessage(AlterEgoPlugin.PREFIX + ChatColor.RED + " Cannot unlink an account that is not already linked!");
			return true;
		}
		
		if (sender.hasPermission("alterego.command.unlink.others") && args.length > 0) {
			if (Bukkit.getOfflinePlayer(args[0]) != null) {
				if (LinkCommand.links.containsValue(Bukkit.getOfflinePlayer(args[0]).getUniqueId())) {
					for (String key : LinkCommand.links.keySet()) {
						if (LinkCommand.links.get(key).equals(Bukkit.getOfflinePlayer(args[0]).getUniqueId())) {
							User user = AlterEgoPlugin.API.getCachedUserById(key);
							user.sendMessage("Your account has been unlinked to MC user " + Bukkit.getOfflinePlayer(args[0]).getName());
							AlterEgoPlugin.INSTANCE.getLogger().info("Discord user " + user.getName() + "(" + user.getMentionTag() + ") unlinked with MC user " + Bukkit.getOfflinePlayer(LinkCommand.links.get(user.getId())).getName());
							LinkCommand.links.remove(key);
							break;
						}
					}
					ConfigManager.save();
					sender.sendMessage(AlterEgoPlugin.PREFIX + " User " + ChatColor.YELLOW + Bukkit.getOfflinePlayer(args[0]).getName() + ChatColor.GREEN + " has had their account unlinked!");
				}
			} else {
				sender.sendMessage(AlterEgoPlugin.PREFIX + ChatColor.RED + " User not found!");
			}
		} else {
			if (LinkCommand.links.containsValue(((Player)sender).getUniqueId())) {
				for (String key : LinkCommand.links.keySet()) {
					if (LinkCommand.links.get(key).equals(((Player)sender).getUniqueId())) {
						User user = AlterEgoPlugin.API.getCachedUserById(key);
						user.sendMessage("Your account has been unlinked to MC user " + sender.getName());
						AlterEgoPlugin.INSTANCE.getLogger().info("Discord user " + user.getName() + "(" + user.getMentionTag() + ") unlinked with MC user " + Bukkit.getOfflinePlayer(LinkCommand.links.get(user.getId())).getName());
						LinkCommand.links.remove(key);
						break;
					}
				}
				ConfigManager.save();
				sender.sendMessage(AlterEgoPlugin.PREFIX + " Account unlinked successfully!");
			}
		}
		return true;
	}

}
