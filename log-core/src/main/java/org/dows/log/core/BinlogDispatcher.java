package org.dows.log.core;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import lombok.extern.slf4j.Slf4j;
import org.dows.log.config.TableFilter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class BinlogDispatcher implements BinaryLogClient.EventListener {
    private Map<Long, MysqlTable> tableNameMap = new HashMap<>();
    private Map<String, List<ListenerContainer>> listenerMap = new HashMap<>();

    private TableFilter tableFilter;

    public void addListener(String database, String table, List<ListenerContainer> listeners, TableFilter tableFilter) {
        String key = database + "." + table;
        this.listenerMap.put(key, listeners);
        this.tableFilter = tableFilter;
    }

    @Override
    public void onEvent(Event event) {
        EventHeaderV4 header = event.getHeader();
        EventType eventType = header.getEventType();
        if (eventType == EventType.TABLE_MAP) {
            MysqlTable table = new MysqlTable(event.getData());
            String key = table.getDatabase() + "." + table.getTable();
            if (!filterData(key)) {
                return;
            }
            if (this.listenerMap.containsKey(key)) {
                tableNameMap.put(table.getId(), table);
            }
        } else if (eventType == EventType.EXT_UPDATE_ROWS) {
            UpdateRowsEventData data = event.getData();
            if (!tableNameMap.containsKey(data.getTableId())) {
                return;
            }
            dispatchEvent(data);
        } else if (eventType == EventType.EXT_WRITE_ROWS) {
            WriteRowsEventData data = event.getData();
            if (!tableNameMap.containsKey(data.getTableId())) {
                return;
            }
            dispatchEvent(data);
        } else if (eventType == EventType.EXT_DELETE_ROWS) {
            DeleteRowsEventData data = event.getData();
            if (!tableNameMap.containsKey(data.getTableId())) {
                return;
            }
            dispatchEvent(data);
        }
    }

    private void dispatchEvent(UpdateRowsEventData data) {
        MysqlTable table = tableNameMap.get(data.getTableId());
        String key = table.getDatabase() + "." + table.getTable();

        List<ListenerContainer> containers = listenerMap.get(key);
        List<Map.Entry<Serializable[], Serializable[]>> rows = data.getRows();
        containers.forEach(c -> c.invokeUpdate(rows));
    }

    private void dispatchEvent(DeleteRowsEventData data) {
        MysqlTable table = tableNameMap.get(data.getTableId());
        String key = table.getDatabase() + "." + table.getTable();

        List<ListenerContainer> containers = listenerMap.get(key);
        List<Serializable[]> rows = data.getRows();
        containers.forEach(c -> c.invokeDelete(rows));
    }

    private void dispatchEvent(WriteRowsEventData data) {
        MysqlTable table = tableNameMap.get(data.getTableId());
        String key = table.getDatabase() + "." + table.getTable();

        List<ListenerContainer> containers = listenerMap.get(key);
        List<Serializable[]> rows = data.getRows();
        containers.forEach(c -> c.invokeInsert(rows));
    }


    /**
     * 过滤表
     *
     * @param tableName
     * @return
     */
    private boolean filterData(String tableName) {
        log.info("filter current tableName : {}", tableName);
        if (tableName == null) {
            return false;
        }
        String database = tableName.split(",")[0];
        String allTable = database + ".*";
        List includes = tableFilter.getIncludes();
        List excludes = tableFilter.getExcludes();
        if (excludes.size() == 0) {
            return true;
        }
        if (excludes.contains(allTable)) {
            return false;
        }
        if (excludes.contains(tableName)) {
            return false;
        }
        if (includes.size() == 0) {
            return true;
        }
        if (includes.contains(allTable)) {
            return true;
        }
        if (includes.contains(tableName)) {
            return true;
        }
        return true;
    }
}
