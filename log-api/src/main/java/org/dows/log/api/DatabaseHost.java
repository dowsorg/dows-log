package org.dows.log.api;

import lombok.Data;

@Data
public class DatabaseHost {
    private String host;
    private String port;
    private String username;
    private String password;
}
