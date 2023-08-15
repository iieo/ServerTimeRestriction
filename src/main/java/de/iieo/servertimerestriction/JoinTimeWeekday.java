package de.iieo.servertimerestriction;

import java.util.ArrayList;

public class JoinTimeWeekday {
    private final ArrayList<JoinTime> joinTimes;
    private final int weekdayIndex;
    private boolean isEnabled;

    public JoinTimeWeekday(int weekdayIndex) {
        this.isEnabled = false;
        this.weekdayIndex = weekdayIndex;
        this.joinTimes = new ArrayList<>();
    }

    public void addJoinTime(JoinTime joinTime) {
        joinTimes.add(joinTime);
    }


    public ArrayList<JoinTime> getJoinTimes() {
        return joinTimes;
    }


    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public int getWeekdayIndex() {
        return weekdayIndex;
    }

}
