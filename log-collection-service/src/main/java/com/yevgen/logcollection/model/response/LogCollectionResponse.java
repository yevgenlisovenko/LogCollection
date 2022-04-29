package com.yevgen.logcollection.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yevgen.logcollection.model.Log;

import java.util.Arrays;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogCollectionResponse {

    private final List<Log> logs;

    public LogCollectionResponse(List<Log> logs) {
        this.logs = logs;
    }

    public List<Log> getLogs() {
        return logs;
    }

    @Override
    public String toString() {
        return "LogCollectionResponse{" +
                "logs=" + logs +
                '}';
    }
}
