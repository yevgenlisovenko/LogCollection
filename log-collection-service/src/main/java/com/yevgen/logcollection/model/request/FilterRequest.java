package com.yevgen.logcollection.model.request;

public class FilterRequest {

    private FilterType type;
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
