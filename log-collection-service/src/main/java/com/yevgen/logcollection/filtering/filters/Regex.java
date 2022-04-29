package com.yevgen.logcollection.filtering.filters;

import com.yevgen.logcollection.model.Log;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Regex implements Predicate<Log> {

    private Pattern pattern;

    public Regex(String filter) {
        Pattern pattern = Pattern.compile(filter);
    }

    @Override
    public boolean test(Log log) {
        return pattern.matcher(log.getMessage()).find();
    }

}
