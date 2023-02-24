package org.dows.log.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
@ConfigurationProperties(prefix = "log.binlog")
public class LogConfig {
    private List<LogProperties> hosts;

    public List<LogProperties> getHosts() {
        return hosts;
    }

    public void setHosts(List<LogProperties> hosts) {
        this.hosts = hosts;
    }

    public Optional<LogProperties> getByName(String name) {
        return hosts.stream().filter(v -> name.equals(v.getHost()))
                .findAny();
    }

    public LogProperties getByNameAndThrow(String name) {
        return getByName(name).orElseThrow(() -> new RuntimeException("未配置名为 "+name+" 的 binlog 连接信息"));
    }
}
