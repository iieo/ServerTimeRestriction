package de.iieo.servertimerestriction;

import java.time.LocalTime;

public class JoinTime {
    private final LocalTime start;
    private final LocalTime end;

    public JoinTime(LocalTime start, LocalTime end) {
        this.start = start;
        this.end = end;
    }

    public LocalTime getStart() {
        return start;
    }


    public LocalTime getEnd() {
        return end;
    }
}
