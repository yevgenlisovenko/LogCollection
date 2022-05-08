package com.yevgen.logcollectionmaster.model.serverTypes;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServerLogCollectionResponse {

    private int count;
    private List<ServerLog> logs;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ServerLog> getLogs() {
        return logs;
    }

    public void setLogs(List<ServerLog> logs) {
        this.logs = logs;
    }

    @Override
    public String toString() {
        return "ServerLogCollectionResponse{" +
                "count=" + count +
                ", logs=" + logs +
                '}';
    }
}
