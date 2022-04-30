package com.yevgen.logcollection.filtering;

import com.yevgen.logcollection.filtering.filters.*;
import com.yevgen.logcollection.model.Log;
import com.yevgen.logcollection.model.request.FilterRequest;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class FilterFactory {

    public Predicate<Log> createFilter(FilterRequest filterRequest) {
        switch (filterRequest.getType())
        {
            case StartsWith:
                return new StartsWith(filterRequest.getFilterString());
            case EndsWith:
                return new EndsWith(filterRequest.getFilterString());
            case Contains:
                return new Contains(filterRequest.getFilterString());
            case Regex:
                return new Regex(filterRequest.getFilterString());
            case Level:
                return new IsLevel(filterRequest.getFilterString());
            default:
                throw new IllegalArgumentException("Unknown filter type");
        }
    }

}
