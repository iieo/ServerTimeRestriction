package de.iieo.servertimerestriction;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class JoinListener implements Listener {
    private final JoinRestrictionHandler joinRestrictionHandler;

    public JoinListener(JoinRestrictionHandler joinRestrictionHandler) {
        this.joinRestrictionHandler = joinRestrictionHandler;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        if(joinRestrictionHandler.isNotAllowedToJoin(e.getPlayer())){
            e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            e.kickMessage(joinRestrictionHandler.getKickMessage());
        }

    }

}
