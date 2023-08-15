package de.iieo.servertimerestriction;

import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class JoinTimeCollection {
    private final JoinTimeWeekday[] joinTimes;


    public JoinTimeCollection() {
        joinTimes = new JoinTimeWeekday[7];
        for (int i = 0; i < 7; i++) {
            joinTimes[i] = new JoinTimeWeekday(i);
        }
    }

    public void addJoinTime(int weekdayIndex, JoinTime joinTime) {
        if (weekdayIndex < 0 || weekdayIndex > 6) {
            return;
        }
        joinTimes[weekdayIndex].addJoinTime(joinTime);
    }

    public boolean isAllowedToJoin() {
        int weekdayIndex = LocalDateTime.now().getDayOfWeek().getValue() - 1;
        LocalTime currentTime = LocalTime.now();

        JoinTimeWeekday joinTimeWeekday = joinTimes[weekdayIndex];

        if (!joinTimeWeekday.isEnabled()) {
            return true;
        }
        for (JoinTime joinTime : joinTimeWeekday.getJoinTimes()) {
            if (currentTime.isAfter(joinTime.getStart()) && currentTime.isBefore(joinTime.getEnd()) ||
                    (joinTime.getStart().isAfter(joinTime.getEnd()) && currentTime.isAfter(joinTime.getStart()))) {
                return true;
            }
        }
        return false;
    }

    public String getKickMessage() {
        int weekdayIndex = LocalDateTime.now().getDayOfWeek().getValue() - 1;

        JoinTimeWeekday joinTimeWeekday = joinTimes[weekdayIndex];

        if (!joinTimeWeekday.isEnabled()) {
            return null;
        }

        if (joinTimeWeekday.getJoinTimes().isEmpty()) {
            return "You are not allowed to join today";
        }

        List<String> joinTimeStrings = new ArrayList<>();
        for (JoinTime joinTime : joinTimeWeekday.getJoinTimes()) {
            joinTimeStrings.add(joinTime.getStart() + " and " + joinTime.getEnd());
        }

        String weekdayName = WeekdayUtil.getWeekdayByIndex(joinTimeWeekday.getWeekdayIndex());
        return "Only allowed to join between " + String.join(", ", joinTimeStrings) + " on " + weekdayName + "s";
    }


    public JoinTimeWeekday[] getJoinTimes() {
        return joinTimes;
    }
}
