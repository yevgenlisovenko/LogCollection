package com.yevgen.logcollectionmaster.service;

import com.yevgen.logcollectionmaster.model.Log;
import com.yevgen.logcollectionmaster.model.request.FilterRequest;

import java.util.List;

public interface ILogCollectionService {

    List<Log> getLogs(List<String> servers, String filename, Integer limit, FilterRequest filterRequest);

}
