package org.dows.log.core.thread;

import org.dows.log.config.LogProperties;
import org.dows.log.core.BinlogDispatcher;
import org.dows.log.core.ListenerContainer;
import org.dows.log.core.MysqlListener;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class BinlogThreadStarter {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //设计失误 其实只需要一个connection  已经写成这样了 就先不改了
    private Map<String, Connection> connectionPool = new HashMap<>();
    private Connection getConnection(LogProperties logProperties) throws SQLException {
        String key = logProperties.getHost() + ":" + logProperties.getPort();
        Connection connection = connectionPool.get(key);

        if (connection == null) {
            String url =  "jdbc:mysql://" + key +
                    "/INFORMATION_SCHEMA?useUnicode=true&characterEncoding=UTF-8&useSSL=false";

            connection = DriverManager.getConnection(url, logProperties.getUsername(), logProperties.getPassword());
            connectionPool.put(key, connection);
        }

        return connection;
    }

    private void releaseConnection() {
        for (Map.Entry<String, Connection> entry : connectionPool.entrySet()) {
            try {
                entry.getValue().close();
            } catch (SQLException e) {
                //不用管
            }
        }

        connectionPool.clear();
    }

    public void runThread(LogProperties logProperties, List<MysqlListener> listeners) {
        Map<String, List<MysqlListener>> map = listeners.stream()
                .collect(Collectors.groupingBy(l -> l.getDatabase() + ":" + l.getTable()));

        BinlogDispatcher logListener = new BinlogDispatcher();

        map.forEach((k, v) -> {
            String[] arr = k.split(":");
            String[] columns = getColumns(logProperties, arr[0], arr[1]);

            List<ListenerContainer> containers = v.stream()
                    .map(l -> new ListenerContainer(l.getEntityClass(), l.getListener(), columns, logProperties.getTimeOffset()))
                    .collect(Collectors.toList());

            logListener.addListener(arr[0], arr[1], containers,logProperties.getFilter());
        });

        new Thread(new BinlogListenerThread(logProperties, logListener)).start();

        releaseConnection();
    }

    private String[] getColumns(LogProperties logProperties, String db, String table) {
        try {
            Connection connection = getConnection(logProperties);
            Statement statement = connection.createStatement();

            String sql = "select COLUMN_NAME from INFORMATION_SCHEMA.COLUMNS where TABLE_SCHEMA='"
                    + db + "' and TABLE_NAME='" + table + "' order by ORDINAL_POSITION asc;";

            ResultSet resultSet = statement.executeQuery(sql);
            List<String> buf = new ArrayList<>();

            while (resultSet.next()) {
                buf.add(resultSet.getString(1));
            }

            return buf.toArray(new String[0]);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
