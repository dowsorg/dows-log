package org.dows.log.api;

import lombok.Data;

import java.util.List;

@Data
public class MysqlServer {
    private DatabaseHost databaseHost;
    private List<MysqlDdl> mysqlDdls;
}
