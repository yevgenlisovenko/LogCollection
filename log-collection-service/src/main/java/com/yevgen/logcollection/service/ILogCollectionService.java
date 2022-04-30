package com.yevgen.logcollection.service;

import com.yevgen.logcollection.model.Log;
import com.yevgen.logcollection.model.request.FilterRequest;

import java.util.List;

public interface ILogCollectionService {

    List<Log> getLogs(String filename, Integer limit, FilterRequest filterRequest);

}
