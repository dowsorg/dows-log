package org.dows.log.core.thread;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import org.dows.log.config.LogProperties;
import org.dows.log.core.BinlogDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class BinlogListenerThread implements Runnable {
    private LogProperties logProperties;
    private BinlogDispatcher listener;

    private Logger logger = LoggerFactory.getLogger(BinlogListenerThread.class);

    public BinlogListenerThread(LogProperties logProperties, BinlogDispatcher listener) {
        this.logProperties = logProperties;
        this.listener = listener;
    }

    @Override
    public void run() {
        BinaryLogClient client = new BinaryLogClient(logProperties.getHost(),
                logProperties.getPort(), logProperties.getUsername(), logProperties.getPassword());

        client.registerEventListener(listener);

        try {
            client.connect();
        } catch (IOException e) {
            logger.error("{}:{}监听器错误", logProperties.getHost(), logProperties.getPort(), e);
        }
    }
}
