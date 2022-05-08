package com.yevgen.logcollectionmaster.model.serverTypes;

import org.springframework.boot.logging.LogLevel;

import java.time.LocalDateTime;

public class ServerLog {

    private LocalDateTime dateTime;
    private LogLevel level;
    private String message;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ServerLog{" +
                "dateTime=" + dateTime +
                ", level=" + level +
                ", message='" + message + '\'' +
                '}';
    }
}
