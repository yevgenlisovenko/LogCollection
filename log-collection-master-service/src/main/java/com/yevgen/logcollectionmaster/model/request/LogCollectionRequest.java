package com.yevgen.logcollectionmaster.model.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

public class LogCollectionRequest {

    private List<String> servers;

    @NotBlank(message = "Log file name cannot be null or blank")
    @Length(max = 255)
    private String filename;

    @Min(1)
    private Integer limit;

    private FilterRequest filterRequest;

    public List<String> getServers() {
        return servers;
    }

    public void setServers(List<String> servers) {
        this.servers = servers;
    }

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
                "servers=" + servers +
                ", filename='" + filename + '\'' +
                ", limit=" + limit +
                ", filterRequest=" + filterRequest +
                '}';
    }
}
