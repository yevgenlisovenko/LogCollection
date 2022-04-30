package com.yevgen.logcollection.api;

import com.yevgen.logcollection.model.Log;
import com.yevgen.logcollection.model.request.LogCollectionRequest;
import com.yevgen.logcollection.model.response.LogCollectionResponse;
import com.yevgen.logcollection.service.ILogCollectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/")
public class LogCollectionController {

    @Autowired
    private ILogCollectionService logCollectionService;

    @PostMapping("/v1/get-logs")
    public ResponseEntity<LogCollectionResponse> getLogs(
            @Valid @RequestBody LogCollectionRequest request) {

        List<Log> logs = logCollectionService.getLogs(
                request.getFilename(),
                request.getLimit(),
                request.getFilter());

        LogCollectionResponse response = new LogCollectionResponse(logs);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // TODO Consider adding endpoint which will return list of available log files

}
