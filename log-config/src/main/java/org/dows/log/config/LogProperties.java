package org.dows.log.config;

import lombok.Data;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

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
    @NestedConfigurationProperty
    private TableFilter filter;
}
