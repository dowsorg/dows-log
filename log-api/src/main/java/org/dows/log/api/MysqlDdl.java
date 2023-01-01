package org.dows.log.api;

import lombok.Data;

import java.util.List;

/**
 * {
 * "database": {
 * "name": "test",
 * "tables": [
 * {
 * "table": {
 * "name": "",
 * "comment": "",
 * "columns": [
 * {
 * "name": "",
 * "type": "",
 * "length": "",
 * "default": "",
 * "comment": "",
 * "unique": false,
 * "index": {
 * "flag": false,
 * "seq": 1
 * },
 * "pk": {
 * "flag": false,
 * "strategy": "auto_inc"
 * }
 * }
 * ]
 * }
 * }
 * ]
 * }
 * }
 */
@Data
public class MysqlDdl {

    private String name;
    private List<Table> tables;

    @Data
    public static class Table {
        private String name;
        private String comment;
        private List<Column> columns;
    }

    @Data
    public static class Column {
        private String name;
        private String type;
        private String comment;
        private String defaultValue;
        private String length;
        private Boolean unique = false;
        private Index index;
        private PrimaryKey pk;
    }

    @Data
    public static class Index {
        private Boolean flag = false;
        private Integer seq = 0;
    }

    @Data
    public static class PrimaryKey {
        private Boolean flag = false;
        private String strategy;
    }
}
