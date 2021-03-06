package com.gmail.flintintoe.command;

import com.gmail.flintintoe.SimpleSidebar;
import com.gmail.flintintoe.message.Messenger;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Handles the command "/adminsidebar".
 *
 * @since v0.8.0_pre1
 */
public class Admin implements CommandExecutor {
    private Messenger messenger;
    private Output output;

    private Permission permission;

    public Admin(SimpleSidebar plugin) {
        messenger = plugin.getMessenger();
        output = plugin.getCommandOutput();

        permission = plugin.getPermission();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (args.length == 0) {
            messenger.send(sender, 4);
            return true;
        }

        if (args.length == 1) {
            if (args[0].equals("reload") && permission.has(sender, "simplesidebaradmin.reload")) {
                output.reloadPlugin(sender);
                return true;
            } else {
                messenger.send(sender,4);
                return true;
            }
        }

        Player target = Bukkit.getPlayer(args[1]);

        if (target == null) {
            messenger.sendError(sender, "Could not find target player");
            return true;
        }

        if (args.length == 2) {
            switch (args[0]) {
                case "check":
                    if (permission.has(sender, "simplesidebaradmin.check")) {
                        output.ofInfo(sender, target);
                    } else {
                        messenger.send(sender, 2);
                    }
                    return true;
                case "remove":
                    if (permission.has(sender, "simplesidebaradmin.set")) {
                        output.ofRemove(sender, target);
                    } else {
                        messenger.send(sender, 3);
                    }
                    return true;
                default:
                    messenger.send(sender, 1);
                    return true;
            }

        } else if (args.length == 3) {
            if (args[0].equals("set")) {
                if (permission.has(sender, "simplesidebaradmin.set")) {
                    output.ofQuery(sender, target, args[2]);
                } else {
                    messenger.send(sender,3);
                }
                return true;
            } else {
                messenger.send(sender, 1);
                return true;
            }

        } else {
            messenger.send(sender, 3);
            return true;
        }
    }
}