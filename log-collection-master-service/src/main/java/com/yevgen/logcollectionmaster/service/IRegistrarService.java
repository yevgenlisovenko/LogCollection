package com.yevgen.logcollectionmaster.service;

import com.yevgen.logcollectionmaster.model.Server;

import java.util.List;

public interface IRegistrarService {

    void register(Server server);
    void unregister(Server server);
    List<Server> getServers();

}
