package de.iieo.servertimerestriction;

public class WeekdayUtil {

    public static int getWeekdayIndex(String weekday) {
        return switch (weekday.toLowerCase()) {
            case "monday" -> 0;
            case "tuesday" -> 1;
            case "wednesday" -> 2;
            case "thursday" -> 3;
            case "friday" -> 4;
            case "saturday" -> 5;
            case "sunday" -> 6;
            default -> -1;
        };
    }

    public static String getWeekdayByIndex(int weekdayIndex) {
        return switch (weekdayIndex) {
            case 0 -> "monday";
            case 1 -> "tuesday";
            case 2 -> "wednesday";
            case 3 -> "thursday";
            case 4 -> "friday";
            case 5 -> "saturday";
            case 6 -> "sunday";
            default -> "unknown";
        };
    }
}
