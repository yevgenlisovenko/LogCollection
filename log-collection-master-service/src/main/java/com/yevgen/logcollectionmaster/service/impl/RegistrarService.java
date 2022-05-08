package com.yevgen.logcollectionmaster.service.impl;

import com.yevgen.logcollectionmaster.model.Server;
import com.yevgen.logcollectionmaster.repository.ServerRepository;
import com.yevgen.logcollectionmaster.service.IRegistrarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegistrarService implements IRegistrarService {

    @Autowired
    private ServerRepository serverRepository;

    @Override
    public void register(Server server) {
        serverRepository.save(server);
    }

    @Override
    public void unregister(Server server) {
        serverRepository.delete(server);
    }

    @Override
    public List<Server> getServers() {
        List<Server> servers = new ArrayList<>();
        serverRepository.findAll().forEach(servers::add);
        return servers;
    }
}
