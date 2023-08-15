package de.iieo.servertimerestriction;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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
            String playerName = args[1];
            Player player = commandSender.getServer().getPlayer(playerName);
            if (player == null) {
                commandSender.sendMessage("§cPlayer not found");
                return true;
            }
            if (args[0].equalsIgnoreCase("addAllowed")) {
                joinRestrictionHandler.addAllowedPlayer(player);
                commandSender.sendMessage("§aAdded player to allowed players");
                return true;
            } else if (args[0].equalsIgnoreCase("removeAllowed")) {
                joinRestrictionHandler.removeAllowedPlayer(player);
                commandSender.sendMessage("§aRemoved player from allowed players");
                return true;
            } else if (args[0].equalsIgnoreCase("addDisableWhenOnline")) {
                joinRestrictionHandler.addDisableWhenOnlinePlayer(player);
                commandSender.sendMessage("§aAdded player to disable when online players");
                return true;
            } else if (args[0].equalsIgnoreCase("removeDisableWhenOnline")) {
                joinRestrictionHandler.removeDisableWhenOnlinePlayer(player);
                commandSender.sendMessage("§aRemoved player from disable when online players");
                return true;
            }
        }
        return false;
    }
}
