package org.dows.log.config;

import lombok.Data;

import java.util.List;

@Data
public class LogProperties {
    private String name;
    private String host;
    private int port;
    private String username;
    private String password;
    private long timeOffset;
    private List<String> entityPackages;

    private TableFilter tableFilter;
}
