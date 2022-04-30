package com.yevgen.logcollection.model;

import org.springframework.boot.logging.LogLevel;

import java.time.LocalDateTime;

public class Log {

    public final static int LOG_COLUMNS_COUNT = 3;

    private final LocalDateTime dateTime;
    private final LogLevel level;
    private final String message;

    public Log(LocalDateTime dateTime, String level, String message) {
        this.dateTime = dateTime;
        this.level = LogLevel.valueOf(level);
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public LogLevel getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Log{" +
                "dateTime=" + dateTime +
                ", level=" + level +
                ", message='" + message + '\'' +
                '}';
    }
}
