package com.yevgen.logcollectionmaster.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Objects;

@RedisHash("log-collection-server")
public class Server implements Serializable {

    @Id
    private String url;
    private String host;
    private String port;

    public Server(String host, String port) {
        this.host = host;
        this.port = port;
        initUrl();
    }

    private void initUrl() {
        url = port == null || port.isBlank() ? host : host + ":" + port;
    }

    public String getUrl() {
        if (url == null) {
            initUrl();
        }
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Server server = (Server) o;
        return getUrl().equals(server.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUrl());
    }

    @Override
    public String toString() {
        return "Server{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
