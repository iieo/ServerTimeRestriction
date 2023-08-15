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

    public void checkPlayerJoin(Player player) {
        if (!isAllowedToJoin(player)) {
            player.kick(Component.text(joinTimeCollection.getKickMessage()).color(TextColor.color(0xFF0000)));
        }
    }

    public boolean isAllowedToJoin(Player player) {
        if (joinTimeCollection.isAllowedToJoin() || (player.isOp() && allowOps) || allowedPlayers.contains(player.getUniqueId())) {
            return true;
        }
        return Bukkit.getOnlinePlayers().stream().map(Player::getUniqueId).anyMatch(disableWhenPlayersOnline::contains);
    }

    public void addDisableWhenOnlinePlayer(Player player) {
        disableWhenPlayersOnline.add(player.getUniqueId());
        plugin.getConfig().set("disableWhenPlayerIsOnline", disableWhenPlayersOnline.stream().map(UUID::toString));
        plugin.saveConfig();
    }

    public void removeDisableWhenOnlinePlayer(Player player) {
        disableWhenPlayersOnline.remove(player.getUniqueId());
        plugin.getConfig().set("disableWhenPlayerIsOnline", disableWhenPlayersOnline.stream().map(UUID::toString));
        plugin.saveConfig();
    }

    public void removeAllowedPlayer(Player player) {
        allowedPlayers.remove(player.getUniqueId());
        plugin.getConfig().set("allowedPlayers", allowedPlayers.stream().map(UUID::toString));
        plugin.saveConfig();
    }

    public void addAllowedPlayer(Player player) {
        allowedPlayers.add(player.getUniqueId());
        plugin.getConfig().set("allowedPlayers", allowedPlayers.stream().map(UUID::toString));
        plugin.saveConfig();
    }
}
