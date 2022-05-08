package com.yevgen.logcollectionmaster.model;

import org.springframework.boot.logging.LogLevel;

import java.time.LocalDateTime;

public class Log {

    private final LocalDateTime dateTime;
    private final String server;
    private final LogLevel level;
    private final String message;

    public Log(LocalDateTime dateTime, String server, LogLevel level, String message) {
        this.dateTime = dateTime;
        this.server = server;
        this.level = level;
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getServer() {
        return server;
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
                ", server='" + server + '\'' +
                ", level=" + level +
                ", message='" + message + '\'' +
                '}';
    }
}
