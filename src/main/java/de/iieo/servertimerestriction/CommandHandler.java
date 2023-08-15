package de.iieo.servertimerestriction;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class CommandHandler implements CommandExecutor {

    private final JoinRestrictionHandler joinRestrictionHandler;

    public CommandHandler(JoinRestrictionHandler joinRestrictionHandler) {
        this.joinRestrictionHandler = joinRestrictionHandler;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(args.length == 0){
            commandSender.sendMessage("§c/str addAllowed [player]");
            commandSender.sendMessage("§c/str removeAllowed [player]");
            commandSender.sendMessage("§c/str addDisableWhenOnline [player]");
            commandSender.sendMessage("§c/str removeDisableWhenOnline [player]");
            return true;
        } else if (args.length == 2) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[1]);
            UUID playerUUID = player.getUniqueId();
            if (args[0].equalsIgnoreCase("addAllowed")) {
                joinRestrictionHandler.addAllowedPlayer(playerUUID);
                commandSender.sendMessage("§aAdded player to allowed players");
                return true;
            } else if (args[0].equalsIgnoreCase("removeAllowed")) {
                joinRestrictionHandler.removeAllowedPlayer(playerUUID);
                commandSender.sendMessage("§aRemoved player from allowed players");
                return true;
            } else if (args[0].equalsIgnoreCase("addDisableWhenOnline")) {
                joinRestrictionHandler.addDisableWhenOnlinePlayer(playerUUID);
                commandSender.sendMessage("§aAdded player to disable when online players");
                return true;
            } else if (args[0].equalsIgnoreCase("removeDisableWhenOnline")) {
                joinRestrictionHandler.removeDisableWhenOnlinePlayer(playerUUID);
                commandSender.sendMessage("§aRemoved player from disable when online players");
                return true;
            }
        }
        return false;
    }
}
