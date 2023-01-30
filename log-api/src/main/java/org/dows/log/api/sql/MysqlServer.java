package org.dows.log.api.sql;

import lombok.Data;

import java.util.List;

@Data
public class MysqlServer {
    private DatabaseHost databaseHost;
    private List<MysqlDdl> mysqlDdls;
}
