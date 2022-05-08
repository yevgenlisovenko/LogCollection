package com.yevgen.logcollectionmaster.model.request;

import javax.validation.constraints.NotNull;

public class FilterRequest {

    private FilterType type;
    @NotNull
    private String filterString;

    public FilterType getType() {
        return type;
    }

    public void setType(FilterType type) {
        this.type = type;
    }

    public String getFilterString() {
        return filterString;
    }

    public void setFilterString(String filterString) {
        this.filterString = filterString;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "type=" + type +
                ", filterString='" + filterString + '\'' +
                '}';
    }
}
