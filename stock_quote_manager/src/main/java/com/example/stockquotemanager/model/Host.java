package com.example.stockquotemanager.model;

public class Host {

    private String host;
    private Integer port;
    private String endpoint;

    public Host(String host, Integer port, String endpoint) {
        this.host = host;
        this.port = port;
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

}
