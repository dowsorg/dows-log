package org.dows.log.core;


import com.github.shyiko.mysql.binlog.event.TableMapEventData;

public class MysqlTable {
    private long id;
    private String database;
    private String table;

    public MysqlTable(TableMapEventData data) {
        this.id = data.getTableId();
        this.database = data.getDatabase();
        this.table = data.getTable();
    }

    public long getId() {
        return id;
    }

    public String getDatabase() {
        return database;
    }

    public String getTable() {
        return table;
    }
}
