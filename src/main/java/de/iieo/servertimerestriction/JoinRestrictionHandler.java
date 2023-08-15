package de.iieo.servertimerestriction;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class JoinRestrictionHandler {
    private final ServerTimeRestriction plugin;
    private final List<UUID> allowedPlayers;
    private final List<UUID> disableWhenPlayersOnline;
    private final JoinTimeCollection joinTimeCollection;
    private final boolean allowOps;

    public JoinRestrictionHandler(ServerTimeRestriction plugin,List<UUID> allowedPlayers, List<UUID> disableWhenPlayersOnline, JoinTimeCollection joinTimeCollection, boolean allowOps) {
        this.allowedPlayers = allowedPlayers;
        this.disableWhenPlayersOnline = disableWhenPlayersOnline;
        this.joinTimeCollection = joinTimeCollection;
        this.allowOps = allowOps;
        this.plugin = plugin;
    }


    public Component getKickMessage() {
        return Component.text(joinTimeCollection.getKickMessage()).color(TextColor.color(0xFF0000));
    }

    public boolean isNotAllowedToJoin(Player player) {
        if (joinTimeCollection.isAllowedToJoin() || (player.isOp() && allowOps) || allowedPlayers.contains(player.getUniqueId()) || disableWhenPlayersOnline.contains(player.getUniqueId())) {
            return false;
        }
        return Bukkit.getOnlinePlayers().stream().map(Player::getUniqueId).noneMatch(disableWhenPlayersOnline::contains);
    }

    public void addDisableWhenOnlinePlayer(UUID playerUUID) {
        disableWhenPlayersOnline.add(playerUUID);
        plugin.getConfig().set("restrictions.disableWhenPlayerIsOnline", disableWhenPlayersOnline.stream().map(UUID::toString).toArray(String[]::new));
        plugin.saveConfig();
    }

    public void removeDisableWhenOnlinePlayer(UUID playerUUID) {
        disableWhenPlayersOnline.remove(playerUUID);
        plugin.getConfig().set("restrictions.disableWhenPlayerIsOnline", disableWhenPlayersOnline.stream().map(UUID::toString).toArray(String[]::new));
        plugin.saveConfig();
    }

    public void removeAllowedPlayer(UUID playerUUID) {
        allowedPlayers.remove(playerUUID);
        plugin.getConfig().set("restrictions.allowedPlayers", allowedPlayers.stream().map(UUID::toString).toArray(String[]::new));
        plugin.saveConfig();
    }

    public void addAllowedPlayer(UUID playerUUID) {
        allowedPlayers.add(playerUUID);
        plugin.getConfig().set("restrictions.allowedPlayers", allowedPlayers.stream().map(UUID::toString).toArray(String[]::new));
        plugin.saveConfig();
    }
}
