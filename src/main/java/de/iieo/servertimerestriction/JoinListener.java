package de.iieo.servertimerestriction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    private final JoinRestrictionHandler joinRestrictionHandler;

    public JoinListener(JoinRestrictionHandler joinRestrictionHandler) {
        this.joinRestrictionHandler = joinRestrictionHandler;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        joinRestrictionHandler.checkPlayerJoin(e.getPlayer());
    }

}
