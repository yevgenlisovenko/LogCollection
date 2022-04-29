package com.yevgen.logcollection.filtering.filters;

import com.yevgen.logcollection.model.Log;

import java.util.function.Predicate;

public class StartsWith implements Predicate<Log> {

    private String filter;

    public StartsWith(String filter) {
        this.filter = filter;
    }

    @Override
    public boolean test(Log log) {
        return log.getMessage().startsWith(filter);
    }

}
