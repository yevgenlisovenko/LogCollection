package com.yevgen.logcollection.model.request;

public class FilterRequest {

    private FilterType filterType;
    private String filterString;

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
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
                "filterType=" + filterType +
                ", filterString='" + filterString + '\'' +
                '}';
    }
}
