package org.dows.log.boot;

import org.dows.log.LogBinlogConfig;
import org.dows.log.api.BinlogListener;
import org.dows.log.core.MysqlListener;
import org.dows.log.core.thread.BinlogThreadStarter;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class BinlogBeanProcessor implements SmartInitializingSingleton {
    private ApplicationContext context;
    @Autowired
    private LogBinlogConfig logBinlogConfig;

    public BinlogBeanProcessor(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void afterSingletonsInstantiated() {
        Map<String, BinlogListener> beans = context.getBeansOfType(BinlogListener.class);

        Map<String, List<MysqlListener>> listeners = beans.values().stream()
                .map(MysqlListener::new)
                .collect(Collectors.groupingBy(MysqlListener::getHostName));

        listeners.forEach((k, v) -> new BinlogThreadStarter().runThread(logBinlogConfig.getByNameAndThrow(k), v));
    }
}
