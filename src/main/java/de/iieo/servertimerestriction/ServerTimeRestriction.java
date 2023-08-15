package de.iieo.servertimerestriction;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class ServerTimeRestriction extends JavaPlugin {
    @Override
    public void onEnable() {

        this.saveDefaultConfig();

        JoinRestrictionHandler joinRestrictionHandler = loadRestrictionHandlerFromConfig();

        startKickCheckScheduler(joinRestrictionHandler);

        Bukkit.getPluginManager().registerEvents(new JoinListener(joinRestrictionHandler), this);
        Objects.requireNonNull(this.getCommand("str")).setExecutor(new CommandHandler(joinRestrictionHandler));
    }

    private JoinRestrictionHandler loadRestrictionHandlerFromConfig() {
        JoinTimeCollection joinTimeCollection = new JoinTimeCollection();
        String configPrefix = "restrictions.";
        for (int i = 0; i < 7; i++) {
            String weekdayName = WeekdayUtil.getWeekdayByIndex(i);
            String restrictionPrefix = configPrefix + weekdayName + ".";
            if (!this.getConfig().contains(configPrefix + weekdayName)) {
                this.getConfig().set(restrictionPrefix + "enabled", false);
                this.getConfig().set(restrictionPrefix + "joinTimes", List.of("00_00-23_59"));
                this.saveDefaultConfig();
            }
            boolean enabled = this.getConfig().getBoolean(restrictionPrefix + "enabled");

            if (enabled) {
                List<String> joinTimeStrings = this.getConfig().getStringList(restrictionPrefix + "joinTimes");

                joinTimeCollection.getJoinTimes()[i].setEnabled(true);

                for (String joinTimeString : joinTimeStrings) {
                    addJoinTime(joinTimeCollection, i, joinTimeString);
                }
            }
        }
        List<UUID> allowedPlayers = new ArrayList<>();
        for (String s : this.getConfig().getStringList(configPrefix +"allowedPlayers")) {
            UUID uuid = UUID.fromString(s);
            allowedPlayers.add(uuid);
        }

        List<UUID> disableWhenPlayerIsOnline = new ArrayList<>();
        for (String s : this.getConfig().getStringList(configPrefix +"disableWhenPlayerIsOnline")) {
            UUID uuid = UUID.fromString(s);
            disableWhenPlayerIsOnline.add(uuid);
        }

        boolean allowOps = this.getConfig().getBoolean(configPrefix +"allowOps");

        return new JoinRestrictionHandler(this,allowedPlayers, disableWhenPlayerIsOnline, joinTimeCollection, allowOps);
    }

    private void addJoinTime(JoinTimeCollection joinTimeCollection, int weekdayIndex, String joinTimeString) {
        String[] split = joinTimeString.split("-");
        String start = split[0];
        String end = split[1];
        String pattern = "HH_mm";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalTime startTime = LocalTime.parse(start, formatter);
        LocalTime endTime = LocalTime.parse(end, formatter);

        if (endTime.equals(LocalTime.of(0, 0))) {
            endTime = LocalTime.of(23, 59);
        }

        JoinTime joinTime = new JoinTime(startTime, endTime);
        joinTimeCollection.addJoinTime(weekdayIndex, joinTime);

        if (startTime.isAfter(endTime)) {
            JoinTime joinTimeDayAfter = new JoinTime(LocalTime.of(0, 0), endTime);
            joinTimeCollection.addJoinTime((weekdayIndex + 1) % 7, joinTimeDayAfter);
        }
    }


    private void startKickCheckScheduler(JoinRestrictionHandler joinRestrictionHandler) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if(joinRestrictionHandler.isNotAllowedToJoin(player)){
                    player.kick(joinRestrictionHandler.getKickMessage());
                }
            }
        }, 0, 20 * 120);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
