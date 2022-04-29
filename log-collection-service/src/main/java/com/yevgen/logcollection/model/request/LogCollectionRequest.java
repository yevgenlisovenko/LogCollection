package com.yevgen.logcollection.model.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class LogCollectionRequest {

    @NotBlank(message = "Log file name cannot be null or blank")
    @Length(max = 255)
    private String filename;

    @Min(1)
    private Integer limit;

    private FilterRequest filterRequest;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public FilterRequest getFilter() {
        return filterRequest;
    }

    public void setFilter(FilterRequest filterRequest) {
        this.filterRequest = filterRequest;
    }

    @Override
    public String toString() {
        return "LogCollectionRequest{" +
                "filename='" + filename + '\'' +
                ", limit=" + limit +
                ", filter=" + filterRequest +
                '}';
    }
}
