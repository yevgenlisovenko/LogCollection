package com.yevgen.logcollection.filtering.filters;

import com.yevgen.logcollection.model.Log;

import java.util.function.Predicate;

public class Contains implements Predicate<Log> {

    private String filter;

    public Contains(String filter) {
        this.filter = filter;
    }

    @Override
    public boolean test(Log log) {
        return log.getMessage().contains(filter);
    }

}
