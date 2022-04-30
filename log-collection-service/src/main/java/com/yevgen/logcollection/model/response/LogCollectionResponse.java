package com.yevgen.logcollection.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yevgen.logcollection.model.Log;

import java.util.Arrays;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogCollectionResponse {

    private final int count;
    private final List<Log> logs;

    public LogCollectionResponse(List<Log> logs) {
        this.count = logs == null ? 0 : logs.size();
        this.logs = logs;
    }

    public int getCount() {
        return count;
    }

    public List<Log> getLogs() {
        return logs;
    }

    @Override
    public String toString() {
        return "LogCollectionResponse{" +
                "count=" + count +
                ", logs=" + logs +
                '}';
    }
}
