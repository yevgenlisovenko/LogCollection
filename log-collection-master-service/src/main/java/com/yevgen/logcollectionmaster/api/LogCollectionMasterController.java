package com.yevgen.logcollectionmaster.api;

import com.yevgen.logcollectionmaster.model.Log;
import com.yevgen.logcollectionmaster.model.request.LogCollectionRequest;
import com.yevgen.logcollectionmaster.model.response.LogCollectionResponse;
import com.yevgen.logcollectionmaster.service.ILogCollectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/")
public class LogCollectionMasterController {

    private static Logger logger = LoggerFactory.getLogger(LogCollectionMasterController.class);

    @Autowired
    private ILogCollectionService logCollectionService;

    @PostMapping("/v1/get-logs")
    public ResponseEntity<LogCollectionResponse> getLogs(
            @Valid @RequestBody LogCollectionRequest request) {

        logger.info("{}", request);

        List<Log> logs = logCollectionService.getLogs(
                request.getServers(),
                request.getFilename(),
                request.getLimit(),
                request.getFilter());

        LogCollectionResponse response = new LogCollectionResponse(logs);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
