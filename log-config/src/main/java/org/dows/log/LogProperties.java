package org.dows.log;

import lombok.Data;

@Data
public class LogProperties {
    private String name;
    private String host;
    private int port;
    private String username;
    private String password;
    private long timeOffset;
}
