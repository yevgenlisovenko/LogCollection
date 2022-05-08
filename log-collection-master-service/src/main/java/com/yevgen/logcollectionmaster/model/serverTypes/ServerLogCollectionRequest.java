package com.yevgen.logcollectionmaster.model.serverTypes;

import com.yevgen.logcollectionmaster.model.request.FilterRequest;

public class ServerLogCollectionRequest {

    private final String filename;
    private final Integer limit;

    private final FilterRequest filterRequest;

    public ServerLogCollectionRequest(String filename, Integer limit, FilterRequest filterRequest) {
        this.filename = filename;
        this.limit = limit;
        this.filterRequest = filterRequest;
    }

    public String getFilename() {
        return filename;
    }

    public Integer getLimit() {
        return limit;
    }

    public FilterRequest getFilter() {
        return filterRequest;
    }

    @Override
    public String toString() {
        return "ServerLogCollectionRequest{" +
                "filename='" + filename + '\'' +
                ", limit=" + limit +
                ", filterRequest=" + filterRequest +
                '}';
    }
}
