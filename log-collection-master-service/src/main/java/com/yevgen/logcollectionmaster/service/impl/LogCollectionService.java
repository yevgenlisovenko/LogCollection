package com.yevgen.logcollectionmaster.service.impl;

import com.yevgen.logcollectionmaster.exception.LogCollectionMasterException;
import com.yevgen.logcollectionmaster.model.Log;
import com.yevgen.logcollectionmaster.model.Server;
import com.yevgen.logcollectionmaster.model.serverTypes.ServerLog;
import com.yevgen.logcollectionmaster.model.request.FilterRequest;
import com.yevgen.logcollectionmaster.model.serverTypes.ServerLogCollectionRequest;
import com.yevgen.logcollectionmaster.model.serverTypes.ServerLogCollectionResponse;
import com.yevgen.logcollectionmaster.repository.ServerRepository;
import com.yevgen.logcollectionmaster.service.ILogCollectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LogCollectionService implements ILogCollectionService {

    private static Logger logger = LoggerFactory.getLogger(LogCollectionService.class);

    @Value("${log-collection.server.path}")
    private String logCollectionServerPath;

    @Autowired
    private ServerRepository serverRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Log> getLogs(List<String> servers, String filename, Integer limit, FilterRequest filterRequest) {

        List<Server> registeredServers = getRegisteredServers();
        ServerLogCollectionRequest logCollectionRequest = new ServerLogCollectionRequest(filename, limit, filterRequest);

        PriorityQueue<Log> priorityQueue = new PriorityQueue<>(
                (l1, l2) -> l2.getDateTime().compareTo(l1.getDateTime()));

        servers.stream()
                .map(server -> {
                    String[] strings = server.split("\\:");
                    return new Server(strings[0], strings.length > 1 ? strings[1] : null);
                })
                .forEach(server -> {
                    if (registeredServers.contains(server)) {
                        List<ServerLog> serverLogs = getServerLogs(server, logCollectionRequest);
                        if (serverLogs != null) {
                            serverLogs.forEach(sl -> {
                                Log log = new Log(sl.getDateTime(), server.getUrl(), sl.getLevel(), sl.getMessage());
                                priorityQueue.add(log);
                                if (limit != null && priorityQueue.size() > limit) {  // remove older logs if limit reached
                                    priorityQueue.poll();
                                }
                            });
                        }
                    }
                    else {
                        logger.warn("{} is not registered", server);
                    }
                }
        );

        List<Log> logs = priorityQueue.stream().collect(Collectors.toList());
        logs.sort((l1, l2) -> l2.getDateTime().compareTo(l1.getDateTime()));

        return logs;
    }

    private List<Server> getRegisteredServers() {
        List<Server> registeredServers = new ArrayList<>();
        serverRepository.findAll().forEach(registeredServers::add);
        return registeredServers;
    }

    private List<ServerLog> getServerLogs(Server server, ServerLogCollectionRequest logCollectionRequest) {
        List<ServerLog> serverLogs = null;

        try {
            String url = String.format(logCollectionServerPath, server.getUrl());

            ResponseEntity<ServerLogCollectionResponse> response =
                    restTemplate.postForEntity(url, logCollectionRequest, ServerLogCollectionResponse.class);
            serverLogs = response.getBody().getLogs();
        }
        catch (Exception e) {
            throw new LogCollectionMasterException(String.format("Error getting logs from %s", server), e);
        }

        return serverLogs;
    }

}
