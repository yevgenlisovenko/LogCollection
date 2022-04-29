package com.yevgen.logcollection.filtering.filters;

import com.yevgen.logcollection.model.Log;
import org.springframework.boot.logging.LogLevel;

import java.util.function.Predicate;

public class IsLevel implements Predicate<Log> {

    private LogLevel expectedLevel;

    public IsLevel(String level) {
        this.expectedLevel = LogLevel.valueOf(level);
    }

    @Override
    public boolean test(Log log) {
        return log.getLevel() == expectedLevel;
    }

}
