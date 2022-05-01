package com.yevgen.logcollection.model.request;

import javax.validation.constraints.NotNull;

public class FilterRequest {

    private FilterType type;
    @NotNull
    private String filterString;

    public FilterRequest() {
    }

    public FilterRequest(FilterType type, String filterString) {
        this.type = type;
        this.filterString = filterString;
    }

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
